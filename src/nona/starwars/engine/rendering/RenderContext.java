package nona.starwars.engine.rendering;

import nona.starwars.engine.core.Util;

public class RenderContext extends Bitmap {

    public RenderContext(int width, int height) {
        super(width, height);
    }

    public void fillRect(float xCenter, float yCenter, float rectWidth, float rectHeight,
                              byte a, byte b, byte g, byte r) {
        float xStart = xCenter - rectWidth / 2;
        float yStart = yCenter - rectHeight / 2;
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

        for(int j = (int)yStart; j < yEnd; j++) {
            for (int i = (int)xStart; i < xEnd; i++) {
                setPixel(i, j, a, b, g, r);
            }
        }
    }

    public void draw(Bitmap image, float xCenter, float yCenter, float imageWidth, float imageHeight) {
        float xStart = xCenter - imageWidth / 2;
        float yStart = yCenter - imageHeight / 2;
        float xEnd = xStart + imageWidth;
        float yEnd = yStart + imageHeight;

        float halfWidth = getWidth() / 2.0f;
        float halfHeight = getHeight() / 2.0f;
        float scale = halfWidth < halfHeight ? halfWidth : halfHeight;

        float imageXStart = 0.0f;
        float imageYStart = 0.0f;
        float imageYStep  = 1.0f / (((yEnd * scale) + halfHeight) - ((yStart * scale) + halfHeight));
		float imageXStep  = 1.0f / (((xEnd * scale) + halfWidth) - ((xStart * scale) + halfWidth));

        if(xStart < -1.0f) {
            imageXStart = -((xStart + 1.0f)/(xEnd - xStart));
            xStart = -1.0f;
        } else if(xStart > 1.0f) {
            imageXStart = -((xStart + 1.0f)/(xEnd - xStart));
            xStart = 1.0f;
        }

        if(yStart < -1.0f) {
            imageYStart = -((yStart + 1.0f)/(yEnd - yStart));
            yStart = 1.0f;
        } else if(yStart > 1.0f) {
            imageYStart = -((yStart + 1.0f)/(yEnd - yStart));
            yStart = 1.0f;
        }

        Util.clamp(xEnd, -1.0f, 1.0f);
        Util.clamp(yEnd, -1.0f, 1.0f);

        xStart = (xStart * scale) + halfWidth;
		yStart = (yStart * scale) + halfHeight;
		xEnd   = (xEnd * scale) + halfWidth;
		yEnd   = (yEnd * scale) + halfHeight;

        float imageY = imageYStart;
        for(int y = (int)yStart; y < (int)yEnd; y++) {
			float imageX = imageXStart;
			for(int x = (int)xStart; x < (int)xEnd; x++) {
				image.copyNearest(this, x, y, imageX, imageY);
                imageX += imageXStep;
			}
			imageY += imageYStep;
		}
    }

}