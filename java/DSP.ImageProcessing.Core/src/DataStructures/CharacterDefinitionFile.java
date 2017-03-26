package DataStructures;

/**
 * Created by bclapa on 23.03.2017.
 */
public class CharacterDefinitionFile {
    private String letter;
    private String filePath;

    public CharacterDefinitionFile(String letter, String image) {
        this.letter = letter;
        this.filePath = image;
    }

    public String getLetter() {
        return letter;
    }

    public String getFilePath() {
        return filePath;
    }
}
