package Interface;

import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

/**
 * Created by bclapa on 10.03.2017.
 */

public interface BasicImageOperations {
    Mat convertImageToMat(BufferedImage image);
    BufferedImage convertMatToImage(Mat mat);
    Mat binarizeColorImage(Mat colorImage);
    Mat convertToGrayscale(Mat colorImage);
    byte[] getBinaryImageVector(Mat mat);
    double[] getNormalizedBinaryImageVector(Mat mat);
}