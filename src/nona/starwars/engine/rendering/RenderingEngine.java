package nona.starwars.engine.rendering;

public class RenderingEngine {

    private static RenderingEngine renderingEngine = new RenderingEngine();

    public static RenderingEngine getInstance() {
        return renderingEngine;
    }

    public void draw(Bitmap bitmap, Bitmap destination, int x, int y) {
        int xmax = bitmap.getWidth() < destination.getWidth() ? bitmap.getWidth() : destination.getWidth();
        int ymax = bitmap.getHeight() < destination.getHeight() ? bitmap.getHeight() : destination.getHeight();
        int xCount = 0;
        int yCount = 0;
        for(int i = x; i < xmax; i++) {
            xCount++;
            for(int j = y; j < ymax; j++) {
                yCount++;

                int locationOnDestination = i + j * destination.getWidth();
                int locationOnBitmap = xCount + yCount * bitmap.getWidth();

                destination.setComponent(locationOnDestination + 0, bitmap.getComponent(locationOnBitmap + 0));
                destination.setComponent(locationOnDestination + 1, bitmap.getComponent(locationOnBitmap + 1));
                destination.setComponent(locationOnDestination + 2, bitmap.getComponent(locationOnBitmap + 2));
                destination.setComponent(locationOnDestination + 3, bitmap.getComponent(locationOnBitmap + 3));
            }
        }
    }

}