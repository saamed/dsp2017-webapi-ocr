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
    private ImageView histogramImageView;

    BufferedImage sourceImage;
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
        sourceImage = ImageIO.read(file);
        Image img = SwingFXUtils.toFXImage(sourceImage,null);
        sourceImageView.setImage(img);
    }

    @FXML
    protected void processButtonClicked(MouseEvent e) throws IOException {

        binarizedImage = imageOperations.binarizeColorImage(sourceImage);

        Image img = SwingFXUtils.toFXImage(binarizedImage, null);
        binarizedImageView.setImage(img);

        histogramImage = imageOperations.binarizeImageAndGetHistogram(sourceImage);

        Image imgHist = SwingFXUtils.toFXImage(histogramImage, null);
        histogramImageView.setImage(imgHist);
    }
}