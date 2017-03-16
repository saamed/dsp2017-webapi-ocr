import DataStructures.TextLineData;
import Interface.BasicImageOperations;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
    private ImageView sourceImageView;
    @FXML
    private ImageView binarizedImageView;
    @FXML
    private ImageView histogramXAxisImageView;
    @FXML
    private ImageView histogramYAxisImageView;

    BufferedImage sourceImage;
    BufferedImage binarizedImage;
    BufferedImage xAxisHistogramImage;
    BufferedImage yAxisHistogramImage;

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
        sourceImage = ImageIO.read(file);
        Image img = SwingFXUtils.toFXImage(sourceImage,null);
        sourceImageView.setImage(img);
    }

    @FXML
    protected void processButtonClicked(MouseEvent e) throws IOException {

        binarizedImage = imageOperations.binarizeColorImage(sourceImage);

        Image img = SwingFXUtils.toFXImage(binarizedImage, null);
        binarizedImageView.setImage(img);

        xAxisHistogramImage = imageOperations.binarizeImageAndGetXAxisHistogram(sourceImage);

        Image imgHistX = SwingFXUtils.toFXImage(xAxisHistogramImage, null);
        histogramXAxisImageView.setImage(imgHistX);

        List<TextLineData> data = imageOperations.detectTextLines(sourceImage, 0);

        yAxisHistogramImage = imageOperations.detectTextLinesAndGetLinesHistogram(sourceImage, 0);

        Image imgHistY = SwingFXUtils.toFXImage(yAxisHistogramImage, null);
        histogramYAxisImageView.setImage(imgHistY);
    }
}