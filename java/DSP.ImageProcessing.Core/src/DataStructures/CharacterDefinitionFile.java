package DataStructures;

/**
 * Created by bclapa on 23.03.2017.
 */
public class CharacterDefinitionFile {
    private String character;
    private int width;
    private int height;
    private byte[] data;

    public CharacterDefinitionFile(String letter, byte[] data, int width, int height) {
        this.character = letter;
        this.data = data;
        this.width = width;
        this.height = height;
    }

    public String getCharacter() {
        return character;
    }

    public byte[] getData() {
        return data;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
