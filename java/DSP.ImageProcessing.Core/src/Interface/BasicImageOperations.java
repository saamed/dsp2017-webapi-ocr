package Interface;

import DataStructures.LetterData;
import DataStructures.TextLineData;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by bclapa on 10.03.2017.
 */
public interface BasicImageOperations {
    BufferedImage convertToGrayscale(BufferedImage image);
    BufferedImage binarizeColorImage(BufferedImage image);
    BufferedImage binarizeImageAndGetXAxisHistogram(BufferedImage image);
    BufferedImage detectTextLinesAndGetLinesHistogram(BufferedImage image, int xMinimalValue);
    List<TextLineData> detectTextLines(BufferedImage image, int minimalValue);
    List<LetterData[]> detectLetterLocations(BufferedImage image, int xMinimalValue, int yMinimalValue);
}
