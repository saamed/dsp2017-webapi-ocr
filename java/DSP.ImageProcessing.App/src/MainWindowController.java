import DataStructures.ImageData;
import Interface.BasicImageOperations;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by bclapa on 12.03.2017.
 */
public class MainWindowController {
    @FXML
    private Pane rootNode;
    @FXML
    private Button loadButton;
    @FXML
    private Button processButton;
    @FXML
    private ImageView sourceImage;
    @FXML
    private ImageView binarizedImageView;
    @FXML
    private ImageView histogramImageView;

    BufferedImage image;
    BufferedImage binarizedImage;
    BufferedImage histogramImage;

    BasicImageOperations imageOperations;

    String imagePath;

    public MainWindowController() {
        imageOperations = new DefaultBasicImageOperations();
    }

    @FXML
    protected void loadButtonClicked(MouseEvent e) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Bitmapa (*.bmp)", "*.bmp");

        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(rootNode.getScene().getWindow());
        if (file == null)
            return;

        imagePath = file.toURI().toString();
        image = ImageIO.read(file);
        Image img = SwingFXUtils.toFXImage(image,null);
        sourceImage.setImage(img);
    }

    @FXML
    protected void processButtonClicked(MouseEvent e) throws IOException {
        ImageData imageData = new ImageData();
        imageData.setWidth(image.getWidth());
        imageData.setHeight(image.getHeight());
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

        imageData.setData(data);

        ImageData binarizedImageData = imageOperations.binarizeColorImage(imageData);

        binarizedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        binarizedImage.getRaster().setDataElements(0, 0, image.getWidth(), image.getHeight(), binarizedImageData.getData());
        Image img = SwingFXUtils.toFXImage(binarizedImage, null);
        binarizedImageView.setImage(img);

        ImageData histogramImageData = imageOperations.getBinarizedImageHistogram(imageData);

        histogramImage = new BufferedImage(histogramImageData.getWidth(), histogramImageData.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        WritableRaster raster = histogramImage.getRaster();
        raster.setDataElements(0,0,histogramImageData.getWidth(),histogramImageData.getHeight(),histogramImageData.getData());

        Image imgHist = SwingFXUtils.toFXImage(histogramImage, null);
        histogramImageView.setImage(imgHist);
    }
}