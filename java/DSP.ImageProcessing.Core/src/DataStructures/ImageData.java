package DataStructures;

public class ImageData{
    int width;
    int height;
    byte[] data;

    public ImageData() {
    }

    public ImageData(int width, int height, byte[] data) {

        this.width = width;
        this.height = height;
        this.data = data;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
