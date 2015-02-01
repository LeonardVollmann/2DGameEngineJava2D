package nona.starwars.engine.rendering;

import java.util.Arrays;
import java.util.Random;

public class Bitmap {

    protected int width;
    protected int height;

    protected byte[] pixelComponents;

    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
        this.pixelComponents = new byte[width * height * 4];
    }

    public void copy(byte[] destination) {
        for(int i = 0; i < destination.length; i++) {
            destination[i] = pixelComponents[i];
        }
    }

    public void copyToBGRArray(byte[] array) {
        for(int i = 0; i < width * height; i++) {
            array[i * 3] = pixelComponents[i * 4 + 1];
            array[i * 3 + 1] = pixelComponents[i * 4 + 2];
            array[i * 3 + 2] = pixelComponents[i * 4 + 3];
        }
    }

    public void clear(byte shade) {
        Arrays.fill(pixelComponents, shade);
    }

    public void randomize() {
        Random random = new Random();
        random.nextBytes(pixelComponents);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setPixel(int x, int y, byte a, byte b, byte g, byte r) {
        int location = (x + y * width) * 4;
        pixelComponents[location + 0] = a;
        pixelComponents[location + 1] = b;
        pixelComponents[location + 2] = g;
        pixelComponents[location + 3] = r;
    }

    public byte getComponent(int location) {
        return pixelComponents[location];
    }

    public void setComponent(int location, byte value) {
        pixelComponents[location] = value;
    }

}