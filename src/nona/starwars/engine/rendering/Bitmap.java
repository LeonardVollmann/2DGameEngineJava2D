package nona.starwars.engine.rendering;

import java.util.Arrays;

public class Bitmap {

    private int width;
    private int height;

    private byte[] pixelComponents;

    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixelComponents = new byte[width * height * 4];
    }

    public void draw(Bitmap other, int x, int y) {
        int xmax = width < other.getWidth() ? width : other.getWidth();
        int ymax = height < other.getHeight() ? height : other.getHeight();
        for(int i = x; i < xmax; i++) {
            for(int j = y; j < ymax; j++) {
                int location = i + j * width;
                setComponent(location + 0, getComponent(location + 0));
                setComponent(location + 1, getComponent(location + 1));
                setComponent(location + 2, getComponent(location + 2));
                setComponent(location + 3, getComponent(location + 3));
            }
        }
    }

    public void copy(byte[] destination) {
        for(int i = 0; i < destination.length; i++) {
            destination[i] = getComponent(i);
        }
    }

    public void clear(byte shade) {
        Arrays.fill(pixelComponents, shade);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte[] getPixelComponents() {
        return pixelComponents;
    }

    public byte[] getPixel(int x, int y) {
        return Arrays.copyOfRange(pixelComponents, x + y * width, x + y * width + 3);
    }

    public void setPixel(int x, int y, byte a, byte b, byte g, byte r) {
        pixelComponents[x + y * width + 0] = a;
        pixelComponents[x + y * width + 1] = b;
        pixelComponents[x + y * width + 2] = g;
        pixelComponents[x + y * width + 3] = r;
    }

    public void setPixel(int x, int y, byte[] pixel) {
        setPixel(x, y, pixel[0], pixel[1], pixel[2], pixel[3]);
    }

    public byte getComponent(int location) {
        return pixelComponents[location];
    }

    public void setComponent(int location, byte value) {
        pixelComponents[location] = value;
    }

}