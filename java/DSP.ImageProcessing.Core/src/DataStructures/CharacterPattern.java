package DataStructures;

/**
 * Created by bclapa on 23.03.2017.
 */
public class CharacterPattern {
    private String character;
    private double[] vector;
    private int width;
    private int height;

    public CharacterPattern(String character, double[] vector, int width, int height) {
        this.character = character;
        this.vector = vector;
        this.width = width;
        this.height = height;
    }

    public String getCharacter() {
        return character;
    }

    public double[] getVector() {
        return vector;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
