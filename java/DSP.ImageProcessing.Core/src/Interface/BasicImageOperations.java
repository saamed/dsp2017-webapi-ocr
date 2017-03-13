package Interface;

import java.awt.image.BufferedImage;

/**
 * Created by bclapa on 10.03.2017.
 */
public interface BasicImageOperations {
    BufferedImage convertToGrayscale(BufferedImage imageData);
    BufferedImage binarizeColorImage(BufferedImage imageData);
    BufferedImage binarizeImageAndGetHistogram(BufferedImage imageData);
}
