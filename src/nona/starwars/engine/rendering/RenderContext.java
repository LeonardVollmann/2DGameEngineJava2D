package nona.starwars.engine.rendering;

import nona.starwars.engine.core.Util;

public class RenderContext extends Bitmap {

    public RenderContext(int width, int height) {
        super(width, height);
    }

    public void fillRect(float xCenter, float yCenter, float rectWidth, float rectHeight,
                              byte a, byte b, byte g, byte r) {
        float xStart = xCenter - (rectWidth / 2);
        float yStart = yCenter - (rectHeight / 2);
        float xEnd = xStart + rectWidth;
        float yEnd = yStart + rectHeight;

        Util.clamp(xStart, -1.0f, 1.0f);
        Util.clamp(yStart, -1.0f, 1.0f);
        Util.clamp(xEnd, -1.0f, 1.0f);
        Util.clamp(yEnd, -1.0f, 1.0f);

        xStart = (xStart + 1) / 2 * width;
        yStart = (yStart + 1) / 2 * height;
        xEnd = (xEnd + 1) / 2 * width;
        yEnd = (yEnd + 1) / 2 * height;

        fillRectInternal((int)xStart, (int)yStart, (int)xEnd, (int)yEnd, a, b, g, r);
    }

    public void draw(Bitmap bitmap, int xCenter, int yCenter) {

    }

    private void fillRectInternal(int xStart, int yStart, int xEnd, int yEnd,
                                  byte a, byte b, byte g, byte r) {
        for(int j = yStart; j < yEnd; j++) {
            for (int i = xStart; i < xEnd; i++) {
                setPixel(i, j, a, b, g, r);
            }
        }
    }

}