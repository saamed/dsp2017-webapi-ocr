import DataStructures.LetterData;
import DataStructures.TextLineData;
import Interface.BasicImageOperations;
import Interface.ImageContentOperations;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bclapa on 18.03.2017.
 */
// todo zmienić nazwy parametrów
public class DefaultImageContentOperations implements ImageContentOperations {

    private BasicImageOperations imageOperations;

    public DefaultImageContentOperations(BasicImageOperations imageOperations) {
        this.imageOperations = imageOperations;
    }

@Override
    public Mat binarizeImageAndGetXAxisHistogram(Mat colorImage) {

        Mat binarized = imageOperations.binarizeColorImage(colorImage);
        List<Integer> histogramData = getXAxisHistogramForBinarized(binarized);

        Mat histogramImage = new Mat(colorImage.rows(),colorImage.cols(), CvType.CV_8S);

        Scalar blackColor = new Scalar(0);
        histogramImage.setTo(new Scalar(1));

        for (int i =0; i < histogramData.size(); i++){
            Point start = new Point(0,i);
            Point end = new Point(histogramData.get(i), i);

            Imgproc.line(histogramImage,start, end,blackColor);
        }

        return histogramImage;
    }

    @Override
    public Mat detectTextLinesAndGetLinesHistogram(Mat colorImage, int xMinimalValue) {
        List<TextLineData> lines = detectTextLines(colorImage,xMinimalValue);
        Mat matImage = imageOperations.binarizeColorImage(colorImage);
        Mat histogramImage = new Mat(colorImage.rows(),colorImage.cols(), CvType.CV_8S);

        Scalar blackColor = new Scalar(0);
        histogramImage.setTo(new Scalar(1));

        for (int i =0; i < lines.size(); i++){
            TextLineData row = lines.get(i);
            Mat line = matImage.submat(row.getY(), row.getY() + row.getHeight(), 0, matImage.cols());

            List<Integer> yAxisHistogram = getYAxisHistogramData(line);

            for (int j =0; j < yAxisHistogram.size(); j++){
                int height = yAxisHistogram.get(j);

                if (height == 0)
                    continue;

                Point start = new Point(j,row.getY());
                Point end = new Point(j, row.getY()+ height);

                Imgproc.line(histogramImage,start, end,blackColor);
            }
        }

        return histogramImage;
    }

    @Override
    public List<TextLineData> detectTextLines(Mat colorImage, int minimalValue) {
        Mat binarized = imageOperations.binarizeColorImage(colorImage);
        List<Integer> histogramData = getXAxisHistogramForBinarized(binarized);

        List<TextLineData> result = detectTextLines(histogramData,minimalValue);

        return result;
    }

    @Override
    public List<LetterData[]> detectLetterLocations(Mat colorImage, int xMinimalValue, int yMinimalValue){
        List<LetterData[]> result = new ArrayList<>();

        Mat binarized = imageOperations.binarizeColorImage(colorImage);
        List<Integer> histogramData = getXAxisHistogramForBinarized(binarized);

        List<TextLineData> textLines = detectTextLines(histogramData,yMinimalValue);

        for (int i = 0; i < textLines.size(); i++){
            List<LetterData> line = detectLettersInLine(textLines.get(i), binarized, xMinimalValue);

            result.add(line.toArray(new LetterData[0]));
        }

        return result;
    }

    @SuppressWarnings("Duplicates")
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

    @SuppressWarnings("Duplicates")
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
