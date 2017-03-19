package Interface;

import DataStructures.LetterData;
import DataStructures.TextLineData;
import org.opencv.core.Mat;

import java.util.List;

/**
 * Created by bclapa on 18.03.2017.
 */

public interface ImageContentOperations {
    Mat binarizeImageAndGetXAxisHistogram(Mat colorImage);
    Mat detectTextLinesAndGetLinesHistogram(Mat colorImage, int xMinimalValue);
    List<TextLineData> detectTextLines(Mat colorImage, int minimalValue);
    List<LetterData[]> detectLetterLocations(Mat colorImage, int xMinimalValue, int yMinimalValue);
}