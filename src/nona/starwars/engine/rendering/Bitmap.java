package nona.starwars.engine.rendering;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

    public Bitmap(String fileName) {
        fileName = "/" + fileName;

        BufferedImage image = null;
        try {
            image = ImageIO.read(Bitmap.class.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while loading image: " + fileName);
        }

        this.width = image.getWidth();
        this.height = image.getHeight();
        this.pixelComponents = new byte[width * height * 4];

        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                int index = i + j * width;
                int argb = image.getRGB(i, j);

                byte a = (byte)(0xFF & (argb >> 24));
                byte r = (byte)(0xFF & (argb >> 16));
                byte g = (byte)(0xFF & (argb >> 8));
                byte b = (byte)(0xFF & argb);

                setPixel(i, j, a, b, g, r);
            }
        }
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

    public void copyNearest(Bitmap destination, int destX,
                            int destY, float srcXFloat, float srcYFloat)
    {
        int srcX = (int)(srcXFloat * (getWidth() - 1));
        int srcY = (int)(srcYFloat * (getHeight() - 1));

        int destIndex = (destX + destY * destination.getWidth()) * 4;
        int srcIndex = (srcX + srcY * getWidth()) * 4;

        destination.setComponent(destIndex, pixelComponents[srcIndex]);
        destination.setComponent(destIndex + 1, pixelComponents[srcIndex + 1]);
        destination.setComponent(destIndex + 2, pixelComponents[srcIndex + 2]);
        destination.setComponent(destIndex + 3, pixelComponents[srcIndex + 3]);
    }

    public void clear(byte shade) {
        Arrays.fill(pixelComponents, shade);
    }

    public Bitmap randomize() {
        Random random = new Random();
        random.nextBytes(pixelComponents);

        return this;
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