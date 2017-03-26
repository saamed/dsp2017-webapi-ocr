package Patterns;

import DataStructures.CharacterDefinitionFile;
import DataStructures.CharacterPattern;
import Interface.BasicImageOperations;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bclapa on 26.03.2017.
 */
public class DefaultPatternProvider implements PatternProvider {

    private static List<CharacterPattern> characterPatterns;
    private BasicImageOperations basicImageOperations;

    public DefaultPatternProvider(BasicImageOperations basicImageOperations) {
        this.basicImageOperations = basicImageOperations;

        initialize();
    }

    @Override
    public List<CharacterPattern> getCharacterPatterns() {
        return characterPatterns;
    }

    private void initialize(){

        if (characterPatterns != null && characterPatterns.size() >0)
            return;

        List<CharacterDefinitionFile> characterDefinitionFiles = loadCharacterDefinitions();
        characterPatterns = prepareCharacterPatterns(characterDefinitionFiles);
    }

    private List<CharacterDefinitionFile> loadCharacterDefinitions() {
        List<CharacterDefinitionFile> characterDefinitionFiles = new ArrayList<>();
        for (CharacterDefinitionFile definitionFile : NumberDefinitionFiles.getAll()){
            characterDefinitionFiles.add(definitionFile);
        }

        for (CharacterDefinitionFile definitionFile : CapitalLetterDefinitionFiles.getAll()){
            characterDefinitionFiles.add(definitionFile);
        }
        return characterDefinitionFiles;
    }

    private List<CharacterPattern> prepareCharacterPatterns(List<CharacterDefinitionFile> definitions){
        List<CharacterPattern> characterPatterns = new ArrayList<>();

        for (CharacterDefinitionFile definition: definitions) {
            try {
                characterPatterns.add(preparePattern(definition));
            } catch (IOException e) {
                // todo: logowanie błędów
                continue;
            }
        }

        return characterPatterns;
    }

    private CharacterPattern preparePattern(CharacterDefinitionFile definitionFile) throws IOException {
        BufferedImage image = ImageIO.read(new File(definitionFile.getFilePath()));
        Mat binarizedImage = basicImageOperations.binarizeColorImage(basicImageOperations.convertImageToMat(image));

        double[] imageVector = basicImageOperations.getNormalizedBinaryImageVector(binarizedImage);

        return new CharacterPattern(definitionFile.getLetter(), imageVector, binarizedImage.width(), binarizedImage.height());
    }
}
