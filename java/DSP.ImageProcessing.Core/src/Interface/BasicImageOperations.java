package Interface;

import DataStructures.ImageData;

import java.util.List;

/**
 * Created by bclapa on 10.03.2017.
 */
public interface BasicImageOperations {
    ImageData convertToGrayscale(ImageData imageData);
    ImageData binarizeColorImage(ImageData imageData);
    ImageData getBinarizedImageHistogram(ImageData imageData);
}

