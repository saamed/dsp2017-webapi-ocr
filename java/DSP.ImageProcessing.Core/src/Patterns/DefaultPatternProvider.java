package Patterns;

import DataStructures.CharacterDefinitionFile;
import DataStructures.CharacterPattern;
import Interface.BasicImageOperations;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bclapa on 26.03.2017.
 */
public class DefaultPatternProvider implements PatternProvider {

    private final String resourceKey = "/Resources/data.xml";
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

    private void initialize() {

        if (characterPatterns != null && characterPatterns.size() > 0)
            return;

        List<CharacterDefinitionFile> characterDefinitionFiles = loadCharacterDefinitions();
        characterPatterns = prepareCharacterPatterns(characterDefinitionFiles);
    }

    private List<CharacterDefinitionFile> loadCharacterDefinitions() {
        List<CharacterDefinitionFile> characterDefinitionFiles = new ArrayList<>();

        InputStream resource = System.class.getResourceAsStream(resourceKey);

        SAXBuilder sb = new SAXBuilder();
        try {
            Document document = sb.build(resource);
            Element rootElement = document.getRootElement();
            List<Element> children = rootElement.getChildren();

            for (Element child : children) {
                CharacterDefinitionFile definitionFile = getCharacterDefinitionFile(child);
                characterDefinitionFiles.add(definitionFile);
            }

        } catch (JDOMException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }

        return characterDefinitionFiles;
    }

    private CharacterDefinitionFile getCharacterDefinitionFile(Element child) {
        int width = Integer.parseInt(child.getAttributeValue("width"));
        int height = Integer.parseInt(child.getAttributeValue("height"));
        String character = child.getAttributeValue("name");
        String content = child.getContent(0).getValue();
        byte[] data = processContent(content);

        return new CharacterDefinitionFile(character,data,width,height);
    }

    private List<CharacterPattern> prepareCharacterPatterns(List<CharacterDefinitionFile> definitions) {
        List<CharacterPattern> characterPatterns = new ArrayList<>();

        for (CharacterDefinitionFile definition : definitions) {
            try {
                characterPatterns.add(preparePattern(definition));
            } catch (IOException e) {
                // todo: logowanie błędów
                continue;
            }
        }

        return characterPatterns;
    }

    private byte[] processContent(String content) {
        String[] splitted = content.split(";");
        byte[] result = new byte[splitted.length];

        for (int i = 0; i < splitted.length; i++) {
            String entry = splitted[i];
            result[i] = Byte.parseByte(entry);
        }

        return result;
    }

    private CharacterPattern preparePattern(CharacterDefinitionFile definitionFile) throws IOException {
        Mat binarizedImage = new Mat(definitionFile.getHeight(), definitionFile.getWidth(), CvType.CV_8S);
        binarizedImage.put(0,0, definitionFile.getData());

        double[] imageVector = basicImageOperations.getNormalizedBinaryImageVector(binarizedImage);

        return new CharacterPattern(definitionFile.getCharacter(), imageVector, binarizedImage.width(), binarizedImage.height());
    }
}
