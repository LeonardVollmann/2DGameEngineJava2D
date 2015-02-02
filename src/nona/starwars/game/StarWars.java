package nona.starwars.game;

import nona.starwars.engine.components.SpriteComponent;
import nona.starwars.engine.core.Entity;
import nona.starwars.engine.core.Game;
import nona.starwars.engine.rendering.Bitmap;
import nona.starwars.engine.rendering.RenderContext;

import java.util.ArrayList;

public class StarWars extends Game {

    private ArrayList<Bitmap> bitmaps;

    public StarWars() {
        super();

        windowWidth = 1080;
        windowHeight = 720;
        windowTitle = "Star Wars";

        /*
        bitmaps = new ArrayList<Bitmap>();
        bitmaps.add(new Bitmap(64, 64).randomize());
        bitmaps.add(new Bitmap(64, 64).randomize());
        bitmaps.add(new Bitmap(64, 64).randomize());
        bitmaps.add(new Bitmap(64, 64).randomize());

        add(new Entity().addComponent(new SpriteComponent(bitmaps, 1)));
        */

        add(new Entity().addComponent(new SpriteComponent(new Bitmap("sprite.png"))));
    }

    @Override
    public void input() {
        root.processInputAll();
    }

    @Override
    public void update(float delta) {
        root.updateAll(delta);
    }

    @Override
    public void render(RenderContext target) {
        root.renderAll(target);

//        target.fillRect(0, 0, 1, 1, (byte)0xF1, (byte)0xF1, (byte)0x00, (byte)0xF1);
    }

}