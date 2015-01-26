package nona.starwars.engine.rendering;

public class RenderingEngine {

    private static RenderingEngine renderingEngine = new RenderingEngine();

    public static RenderingEngine getInstance() {
        return renderingEngine;
    }

    public void draw(Bitmap bitmap, Bitmap destination, int x, int y) {
        for(int xx = x; xx < bitmap.getWidth(); xx++) {
            if(xx >= destination.getWidth() || xx < 0) continue;
            for(int yy = y; yy < bitmap.getHeight(); yy++) {
                if(yy >= destination.getHeight() || yy < 0) continue;

                int locationOnDestination = xx + yy * destination.getWidth();
                int locationOnBitmap = (xx - x) + (yy - y) * bitmap.getWidth();

                destination.setComponent(locationOnDestination + 0, bitmap.getComponent(locationOnBitmap + 0));
                destination.setComponent(locationOnDestination + 1, bitmap.getComponent(locationOnBitmap + 1));
                destination.setComponent(locationOnDestination + 2, bitmap.getComponent(locationOnBitmap + 2));
                destination.setComponent(locationOnDestination + 3, bitmap.getComponent(locationOnBitmap + 3));
            }
        }
    }

}