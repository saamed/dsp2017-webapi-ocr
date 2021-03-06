package DataStructures;

public class LetterData{
    int x;
    int y;
    int width;
    int height;
    boolean isWhiteSpace;

    public LetterData(int x, int y, int width, int height) {
        this(x, y, width, height, false);
    }

    public LetterData(int x, int y, int width, int height, boolean isWhiteSpace) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isWhiteSpace = isWhiteSpace;
    }

    public boolean isWhiteSpace() {
        return isWhiteSpace;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
