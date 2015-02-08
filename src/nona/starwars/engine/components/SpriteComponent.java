package nona.starwars.engine.components;

import nona.starwars.engine.core.EntityComponent;
import nona.starwars.engine.rendering.Bitmap;
import nona.starwars.engine.rendering.RenderContext;

import java.util.ArrayList;

public class SpriteComponent extends EntityComponent {

    private ArrayList<Bitmap> images;
    private int currentImage = 0;

    private float interval;
    private float passedTime;

    public SpriteComponent(ArrayList<Bitmap> images, float interval) {
        this.images = images;
        this.interval = interval;
    }

    public SpriteComponent(Bitmap image) {
        this.images = new ArrayList<Bitmap>();
        images.add(image);
        this.interval = Float.MAX_VALUE-10;
    }

    public SpriteComponent(String fileName) {
        this(new Bitmap(fileName));
    }

    @Override
    public void update(float delta) {
        passedTime += delta;

        if(passedTime >= interval) {
            passedTime = 0;

            if(currentImage == images.size() - 1) {
                currentImage = 0;
            } else {
                currentImage++;
            }
        }
    }

    @Override
    public void render(RenderContext target) {
        target.drawImage(images.get(currentImage), getPosition(), getEntity().getAABB().getWidth(), getEntity().getAABB().getHeight(), RenderContext.TRANSPARENCY_NONE);
    }

}