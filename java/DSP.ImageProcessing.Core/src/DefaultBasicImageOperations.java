import DataStructures.LetterData;
import DataStructures.TextLineData;
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
@SuppressWarnings("Duplicates")
public class DefaultBasicImageOperations implements BasicImageOperations {
    public DefaultBasicImageOperations() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    @Override
    public BufferedImage convertToGrayscale(BufferedImage image) {

        Mat src = convertImageToMat(image);
        Mat dst = convertToGrayscale(src);

        return convertMatToImage(dst);
    }

    @Override
    public BufferedImage binarizeColorImage(BufferedImage image) {

        Mat src = convertImageToMat(image);
        Mat dst = binarizeColorImage(src);

        return convertMatToImage(dst);
    }

    @Override
    public BufferedImage binarizeImageAndGetXAxisHistogram(BufferedImage image) {

        Mat binarized = binarizeColorImage(convertImageToMat(image));
        List<Integer> histogramData = getXAxisHistogramForBinarized(binarized);

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

    @Override
    public BufferedImage detectTextLinesAndGetLinesHistogram(BufferedImage image, int xMinimalValue) {
        List<TextLineData> lines = detectTextLines(image,xMinimalValue);
        Mat matImage = binarizeColorImage(convertImageToMat(image));
        BufferedImage histogramImg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = histogramImg.createGraphics();

        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0,0,image.getWidth(),image.getHeight());

        graphics.setPaint(Color.BLACK);

        for (int i = 0; i <lines.size(); i++){
            TextLineData row = lines.get(i);
            Mat line = matImage.submat(row.getY(), row.getY() + row.getHeight(), 0, matImage.cols());

            List<Integer> yAxisHistogram = getYAxisHistogramData(line);

            for (int j =0; j < yAxisHistogram.size(); j++){
                int height = yAxisHistogram.get(j);

                graphics.fillRect(j,row.getY(),1,height);
            }
        }

        return histogramImg;
    }

    @Override
    public List<TextLineData> detectTextLines(BufferedImage image, int minimalValue) {
        Mat binarized = binarizeColorImage(convertImageToMat(image));
        List<Integer> histogramData = getXAxisHistogramForBinarized(binarized);

        List<TextLineData> result = detectTextLines(histogramData,minimalValue);

        return result;
    }

    @Override
    public List<LetterData[]> detectLetterLocations(BufferedImage image, int xMinimalValue, int yMinimalValue){
        List<LetterData[]> result = new ArrayList<>();

        Mat binarized = binarizeColorImage(convertImageToMat(image));
        List<Integer> histogramData = getXAxisHistogramForBinarized(binarized);

        List<TextLineData> textLines = detectTextLines(histogramData,yMinimalValue);

        for (int i = 0; i < textLines.size(); i++){
            List<LetterData> line = detectLettersInLine(textLines.get(i), binarized, xMinimalValue);

            result.add(line.toArray(new LetterData[0]));
        }

        return result;
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

    private List<Integer> getXAxisHistogramForBinarized(Mat mat){
        List<Integer> histogramData = new ArrayList<>();

        int rowCount = mat.rows();
        int colCount = mat.cols();

        for (int i = 0; i < rowCount; i++){
            int count = 0;
            for (int j = 0; j < colCount; j++){
                double[] cell = mat.get(i,j);
                if (cell[0]==0)
                    count++;
            }

            histogramData.add(count);
        }

        return histogramData;
    }

    private List<TextLineData> detectTextLines(List<Integer> histogramData, int minimalValue){
        List<TextLineData> result = new ArrayList<>();

        int start = -1;
        int height = 1;
        for (int i =0; i < histogramData.size(); i++){
            if (histogramData.get(i) > minimalValue){
                if (start == -1)
                    start = i;
                else
                    height++;
            }else{
                if (start>-1 && height >1){
                    TextLineData entry = new TextLineData(start,height);
                    result.add(entry);

                    start = -1;
                    height = 1;
                }
            }
        }

        if (start>-1 && height >1){
            TextLineData entry = new TextLineData(start,height);
            result.add(entry);
        }

        return result;
    }

    private List<LetterData> detectLettersInLine(TextLineData textLineData, Mat image, int minimalValue) {
        List<LetterData> result = new ArrayList<>();

        Mat line = image.submat(textLineData.getY(), textLineData.getY() + textLineData.getHeight(), 0, image.cols());

        List<Integer> yAxisHistogram = getYAxisHistogramData(line);

        int start = -1;
        int width = 1;
        for (int i = 0; i < yAxisHistogram.size(); i++){
            if (yAxisHistogram.get(i) > minimalValue){
                if (start == -1)
                    start = 1;
                else
                    width++;
            }else{
                if (start> -1 && width >1){
                    LetterData entry = new LetterData(start,textLineData.getY(),width, textLineData.getHeight());
                    result.add(entry);

                    start = -1;
                    width = 1;
                }
            }
        }

        return result;
    }

    private List<Integer> getYAxisHistogramData(Mat mat){
        List<Integer> histogramData = new ArrayList<>();

        int rowCount = mat.rows();
        int colCount = mat.cols();

        for (int i = 0; i< colCount;i++){
            int count = 0;
            for (int j = 0; j< rowCount;j++){
                double[] cell = mat.get(j,i);
                if (cell[0]==0)
                    count++;
            }

            histogramData.add(count);
        }

        return histogramData;
    }
}