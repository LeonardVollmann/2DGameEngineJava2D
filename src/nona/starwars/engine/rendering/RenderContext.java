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

		float halfWidth = width / 2.0f;
		float halfHeight = height / 2.0f;
        float scale = halfHeight < halfWidth ? halfHeight : halfWidth;
		
		float imageXStart = 0.0f;
		float imageYStart = 0.0f;
		float imageYStep = 1.0f / (((yEnd * scale) + halfHeight) - ((yStart * scale) + halfHeight));
		float imageXStep = 1.0f / (((xEnd * scale) + halfWidth) - ((xStart * scale) + halfWidth));

        xStart += 1.0f / ((width - height) / 2.0f);
        xEnd += 1.0f / ((width - height) / 2.0f);

        if(xStart < -1.0f) {
            imageXStart = -((xStart + 1.0f) / (xEnd - xStart));
            xStart = -1.0f;
        } else if(xStart > 1.0f) {
            imageXStart = -((xStart + 1.0f) / (xEnd - xStart));
            xStart = 1.0f;
        }

		if(yStart < -1.0f) {
            imageYStart = -((yStart + 1.0f) / (yEnd - yStart));
            yStart = -1.0f;
        } else if(yStart > 1.0f) {
            imageYStart = -((yStart + 1.0f) / (yEnd - yStart));
            yStart = 1.0f;
        }

        xEnd = Util.clamp(xEnd, -1.0f, 1.0f);
        yEnd = Util.clamp(yEnd, -1.0f, 1.0f);

        xStart = (xStart * scale) + halfWidth;
        yStart = (yStart * scale) + halfHeight;
        xEnd = (xEnd * scale) + halfWidth;
        yEnd = (yEnd * scale) + halfHeight;

        switch(transparency) {
            case TRANSPARENCY_NONE:
                drawImageInternal(image, (int)xStart, (int)yStart, (int)xEnd, (int)yEnd,
                        imageXStart, imageYStart, imageXStep, imageYStep);
                break;
            case TRANSPARENCY_BASIC:
                drawImageBasicTransparencyInternal(image, (int)xStart, (int)yStart, (int)xEnd, (int)yEnd,
                        imageXStart, imageYStart, imageXStep, imageYStep);
                break;
            case TRANSPARENCY_FULL:
                drawImageAlphaBlendedInternal(image, (int)xStart, (int)yStart, (int)xEnd, (int)yEnd,
                        imageXStart, imageYStart, imageXStep, imageYStep);
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

    public void drawImageBasicTransparencyInternal(Bitmap image, int xStart, int yStart, int xEnd, int yEnd,
                                                   float imageXStart, float imageYStart, float xStep, float yStep) {
        float srcY = imageYStart;
        for (int j = yStart; j < yEnd; j++) {
            float srcX = imageXStart;
            for (int i = xStart; i < xEnd; i++) {
                if(image.getNearestComponent(srcX, srcY, 0) > (byte)0) {
                    image.copyNearest(this, i, j, srcX, srcY);
                }
                srcX += xStep;
            }
            srcY += yStep;
        }
    }

    public void drawImageAlphaBlendedInternal(Bitmap image, int xStart, int yStart, int xEnd, int yEnd,
                                  float imageXStart, float imageYStart, float xStep, float yStep) {
        float srcY = imageYStart;
        for (int j = yStart; j < yEnd; j++) {
            float srcX = imageXStart;
            for (int i = xStart; i < xEnd; i++) {
                int a = image.getNearestComponent(srcX, srcY, 0) & 0xFF;
                int b = image.getNearestComponent(srcX, srcY, 1) & 0xFF;
                int g = image.getNearestComponent(srcX, srcY, 2) & 0xFF;
                int r = image.getNearestComponent(srcX, srcY, 3) & 0xFF;

                int thisB = getComponent(i, j, 1) & 0xFF;
                int thisG = getComponent(i, j, 2) & 0xFF;
                int thisR = getComponent(i, j, 3) & 0xFF;

                int thisAmt = 255 - a;
                byte newB = (byte)(thisB * thisAmt + b * a >> 8);
                byte newG = (byte)(thisG * thisAmt + g * a >> 8);
                byte newR = (byte)(thisR * thisAmt + r * a >> 8);

                int index = (i + j * width) * 4;
                setComponent(index + 1, newB);
                setComponent(index + 2, newG);
                setComponent(index + 3, newR);

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
