import DataStructures.LetterData;
import DataStructures.TextLineData;
import Interface.BasicImageOperations;
import Interface.CharacterRecognitionMechanisms;
import Interface.ImageContentOperations;
import Patterns.DefaultPatternProvider;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.*;
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
    ImageContentOperations imageContentOperations;
    CharacterRecognitionMechanisms characterRecognitionMechanisms;

    String imagePath;

    public MainWindowController() {
        imageOperations = new DefaultBasicImageOperations();
        imageContentOperations = new DefaultImageContentOperations(imageOperations);
        characterRecognitionMechanisms = new DefaultCharacterRecognitionMechanisms(imageOperations, new DefaultPatternProvider(imageOperations));
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

        Mat image = imageOperations.convertImageToMat(sourceImage);
        Mat binarized = imageOperations.binarizeColorImage(image);
        binarizedImage = imageOperations.convertMatToImage(binarized);
        
        Image img = SwingFXUtils.toFXImage(binarizedImage, null);
        binarizedImageView.setImage(img);

        xAxisHistogramImage = imageOperations.convertMatToImage(imageContentOperations.binarizeImageAndGetXAxisHistogram(image));

        Image imgHistX = SwingFXUtils.toFXImage(xAxisHistogramImage, null);
        histogramXAxisImageView.setImage(imgHistX);

        yAxisHistogramImage = imageOperations.convertMatToImage(imageContentOperations.detectTextLinesAndGetLinesHistogram(image, 0));

        List<LetterData[]> letterData =  imageContentOperations.detectLetterLocations(image, 0,0);
        int lineNumber = 3;
        String recognizedTextLine = characterRecognitionMechanisms.recogniseTextLineCharactersUsingCorrelation(letterData.get(lineNumber), binarized);

        Image imgHistY = SwingFXUtils.toFXImage(yAxisHistogramImage, null);
        histogramYAxisImageView.setImage(imgHistY);
    }
}