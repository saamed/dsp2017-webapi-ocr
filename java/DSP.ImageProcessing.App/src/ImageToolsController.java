import Interface.BasicImageOperations;
import Interface.ImageContentOperations;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import org.opencv.core.Mat;

import java.io.File;

/**
 * Created by bclapa on 02.04.2017.
 */
public class ImageToolsController {
    @FXML
    private Pane rootNode;

    String directoryPath;
    BasicImageOperations imageOperations;
    ImageContentOperations imageContentOperations;

    public ImageToolsController() {
        imageOperations = new DefaultBasicImageOperations();
        imageContentOperations = new DefaultImageContentOperations(imageOperations);
    }

    @FXML
    protected void onChooseFolderClicked(MouseEvent e) {
        DirectoryChooser chooser = new DirectoryChooser();

        File file = chooser.showDialog(rootNode.getScene().getWindow());
        if (file == null)
            return;

        directoryPath = file.getPath();
    }

    @FXML
    protected void onProcessClicked(MouseEvent e) {
        binarizeImagesInPath(directoryPath);
    }

    protected void binarizeImagesInPath(String path) {
        File folder = new File(path);

        for (File file : folder.listFiles()) {

            if (file.isDirectory()) {
                binarizeImagesInPath(file.getPath());
                continue;
            }

            Mat img = imageOperations.loadImage(file.getAbsolutePath());
            img = imageOperations.binarizeColorImage(img);
            img = imageContentOperations.trimToContours(img);
            int index = file.getName().lastIndexOf('.');
            String extension = file.getName().substring(index, file.getName().length());

            String filePath = file.getAbsolutePath();

            byte[] data = new byte[img.width() * img.height() * (int)img.elemSize()];

            img.get(0,0,data);
            imageOperations.saveImage(img, extension.replace(".", ""), filePath);
        }
    }
}
