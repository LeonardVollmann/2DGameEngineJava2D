package nona.starwars.engine.rendering;

import nona.starwars.engine.core.Util;
import nona.starwars.engine.core.Vector2f;

public class RenderContext extends Bitmap {

    public static final int TRANSPARENCY_NONE = 0;
    public static final int TRANSPARENCY_BASIC = 1;
    public static final int TRANSPARENCY_FULL = 2;

    public RenderContext(int width, int height) {
        super(width, height);
    }

	public void drawImage(Bitmap image, Vector2f center, float imageWidth, float imageHeight, int transparency) {
		float xStart = center.getX() - imageWidth / 2.0f;
		float yStart = center.getY() - imageHeight / 2.0f;
		float xEnd = xStart + imageWidth;
		float yEnd = yStart + imageHeight;

		float halfWidth = getWidth() / 2.0f;
		float halfHeight = getHeight() / 2.0f;
        float scale = halfWidth < halfHeight ? halfWidth : halfHeight;
		
		float imageXStart = 0.0f;
		float imageYStart = 0.0f;
		float imageYStep = 1.0f / (((yEnd * scale) + halfHeight) - ((yStart * scale) + halfHeight));
		float imageXStep = 1.0f / (((xEnd * scale) + halfWidth) - ((xStart * scale) + halfWidth)); 
		
		if(yStart < -1.0f) {
            imageYStart = -((yStart + 1.0f) / (yEnd - yStart));
            yStart = 1.0f;
        } else if(yStart > 1.0f) {
            imageYStart = -((yStart + 1.0f) / (yEnd - yStart));
            yStart = 1.0f;
        }

        Util.clamp(xEnd, -1.0f, 1.0f);
        Util.clamp(yEnd, -1.0f, 1.0f);

        xStart = (xStart * scale) + halfWidth;
		yStart = (yStart * scale) + halfHeight;
		xEnd   = (xEnd * scale) + halfWidth;
		yEnd   = (yEnd * scale) + halfHeight;

        switch(transparency) {
            case TRANSPARENCY_NONE:
                drawImageInternal(image, (int)xStart, (int)yStart, (int)xEnd, (int)yEnd,
                        imageXStart, imageYStart, imageXStep, imageYStep);
                break;
            case TRANSPARENCY_BASIC:
                // TODO: Implement
                break;
            case TRANSPARENCY_FULL:
                // TODO: Implement
                break;
            default:
                throw new IllegalArgumentException("Invalid transparency type!");
        }
	}

	public void drawImageInternal(Bitmap image, int xStart, int yStart, int xEnd, int yEnd,
                                  float imageXStart, float imageYStart, float xStep, float yStep) {
        float srcY = imageYStart;
        for (int j = yStart; j < yEnd; j++) {
            float srcX = imageXStart;
            for (int i = xStart; i < xEnd; i++) {
                image.copyNearest(this, i, j, srcX, srcY);
                srcX += xStep;
            }
            srcY += yStep;
        }
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

}
