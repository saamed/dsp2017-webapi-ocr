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
    void exportSubImage(Mat image, int x, int y, int width, int height, String path);
    Mat loadImage(String path);
    void saveImage(Mat image, String format, String path);
}