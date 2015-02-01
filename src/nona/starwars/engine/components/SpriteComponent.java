package nona.starwars.engine.components;

import nona.starwars.engine.core.EntityComponent;
import nona.starwars.engine.core.Time;
import nona.starwars.engine.rendering.Bitmap;
import nona.starwars.engine.rendering.RenderContext;

import java.util.ArrayList;

public class SpriteComponent extends EntityComponent {

    private ArrayList<Bitmap> images;
    private int currentImage = 0;

    private float interval;
    private double lastTime;
    private double now;
    private double passedTime;

    public SpriteComponent(ArrayList<Bitmap> images, float interval) {
        this.images = images;
        this.interval = interval;

        lastTime = Time.getTime();
    }

    @Override
    public void update() {
        now = Time.getTime();
        passedTime += now - lastTime;

        if(passedTime >= interval) {
            passedTime = 0;

            if(currentImage == images.size() - 1) {
                currentImage = 0;
            } else {
                currentImage++;
            }
        }

        lastTime = now;
    }

    @Override
    public void render(RenderContext target) {
        target.draw(images.get(currentImage), getPosition().getX(), getPosition().getY(), 1, 1);
    }

}