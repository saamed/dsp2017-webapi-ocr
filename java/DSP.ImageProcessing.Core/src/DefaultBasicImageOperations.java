import Interface.BasicImageOperations;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by bclapa on 10.03.2017.
 */
public class DefaultBasicImageOperations implements BasicImageOperations {
    public DefaultBasicImageOperations() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }


    @Override
    public Mat convertImageToMat(BufferedImage image){

        int imageType;

        switch (image.getType()){
            case BufferedImage.TYPE_BYTE_BINARY:
                imageType = CvType.CV_8S;
                break;
            case BufferedImage.TYPE_BYTE_GRAY:
                imageType = CvType.CV_8UC1;
                break;
            case BufferedImage.TYPE_3BYTE_BGR:
            default:
                imageType = CvType.CV_8UC3;
                break;
        }

        Mat mat = new Mat(image.getHeight(), image.getWidth(), imageType);

        byte[] data = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();

        mat.put(0,0, data);

        return mat;
    }

    @Override
    public BufferedImage convertMatToImage(Mat mat){
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

    @Override
    public Mat binarizeColorImage(Mat colorImage) {

        Mat gray = convertToGrayscale(colorImage);
        Mat dst = new Mat(colorImage.rows(), colorImage.cols(), CvType.CV_8S);

        Imgproc.threshold(gray, dst,0,255,Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);

        return dst;
    }

    @Override
    public Mat convertToGrayscale(Mat colorImage){
        Mat dst = new Mat(colorImage.rows(), colorImage.cols(), CvType.CV_8UC1);

        if (colorImage.type() == CvType.CV_8UC1)
            return colorImage;

        Imgproc.cvtColor(colorImage, dst, Imgproc.COLOR_RGB2GRAY);

        return dst;
    }

    @Override
    public byte[] getBinaryImageVector(Mat mat) {

        long elemSize = mat.elemSize();
        byte[] data = new byte[mat.rows() * mat.cols() * (int) elemSize];

        mat.get(0,0,data);

        for(int i =0; i < data.length; i++){
            data[i] = (byte)(data[i]+1);
        }

        return data;
    }

    public double[] getNormalizedBinaryImageVector(Mat mat){
        byte[] data = getBinaryImageVector(mat);

        double sum = 0;

        for (int i = 0; i < data.length; i++){
            if (data[i]>0)
                sum++;
        }

        double length = Math.sqrt(sum);

        double[] normalizedVector = new double[data.length];

        for (int i = 0; i <data.length; i++){
            normalizedVector[i] = data[i]/length;
        }

        return normalizedVector;
    }

}