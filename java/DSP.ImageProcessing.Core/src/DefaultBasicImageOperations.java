import Interface.BasicImageOperations;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bclapa on 10.03.2017.
 */
public class DefaultBasicImageOperations implements BasicImageOperations {
    public DefaultBasicImageOperations() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Override
    public BufferedImage convertToGrayscale(BufferedImage imageData) {

        Mat src = convertImageToMat(imageData);
        Mat dst = convertToGrayscale(src);

        return convertMatToImage(dst);
    }

    @Override
    public BufferedImage binarizeColorImage(BufferedImage imageData) {

        Mat src = convertImageToMat(imageData);
        Mat dst = binarizeColorImage(src);

        return convertMatToImage(dst);
    }

    @Override
    public BufferedImage binarizeImageAndGetHistogram(BufferedImage image) {

        Mat binarized = binarizeColorImage(convertImageToMat(image));
        List<Integer> histogramData = new ArrayList<>();

        int rowCount = binarized.rows();
        int colCount = binarized.cols();

        for (int i = 0; i < rowCount; i++){
            int count = 0;
            for (int j = 0; j < colCount; j++){
                double[] cell = binarized.get(i,j);
                if (cell[0]==0)
                    count++;
            }

            histogramData.add(count);
        }

        BufferedImage histogramImg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = histogramImg.createGraphics();

        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0,0,image.getWidth(),image.getHeight());

        graphics.setPaint(Color.BLACK);
        for (int i = 0; i <histogramData.size(); i++){
            graphics.fillRect(0,i,histogramData.get(i),1);
        }

        return histogramImg;
    }

    private Mat binarizeColorImage(Mat src) {

        Mat gray = convertToGrayscale(src);
        Mat dst = new Mat(src.rows(), src.cols(), CvType.CV_8S);

        Imgproc.threshold(gray, dst,0,255,Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);

        return dst;
    }

    private Mat convertToGrayscale(Mat src){
        Mat dst = new Mat(src.rows(), src.cols(), CvType.CV_8UC1);

        Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);

        return dst;
    }

    private Mat convertImageToMat(BufferedImage image){

        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);

        byte[] data = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();

        mat.put(0,0, data);

        return mat;
    }

    private BufferedImage convertMatToImage(Mat mat){
        byte[] data = new byte[mat.rows() * mat.cols() * (int) mat.elemSize()];
        int type = mat.type();

        int imageType;

        if (type == CvType.CV_8S)
            imageType = BufferedImage.TYPE_BYTE_BINARY;
        else if (type == CvType.CV_8UC1)
            imageType = BufferedImage.TYPE_BYTE_GRAY;
        else
            imageType = BufferedImage.TYPE_3BYTE_BGR;

        mat.get(0,0, data);

        BufferedImage image = new BufferedImage(mat.width(), mat.height(), imageType);

        image.getRaster().setDataElements(0,0, image.getWidth(), image.getHeight(),data);

        return image;
    }
}
