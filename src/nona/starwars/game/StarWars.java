package nona.starwars.game;

import nona.starwars.engine.core.Game;
import nona.starwars.engine.rendering.Bitmap;
import nona.starwars.engine.rendering.RenderContext;

public class StarWars extends Game {

    private Bitmap bitmap;

    public StarWars() {
        super();

        windowWidth = 1080;
        windowHeight = 720;
        windowTitle = "Star Wars";

        bitmap = new Bitmap(64, 64);
        bitmap.randomize();
    }

    @Override
    public void input() {
        root.processInputAll();
    }

    @Override
    public void update() {
        root.updateAll();
    }

    @Override
    public void render(RenderContext target) {
        root.renderAll(target);

//        target.fillRect(0, 0, 1, 1, (byte)0xF1, (byte)0xF1, (byte)0x00, (byte)0xF1);
        target.draw(bitmap, 0, 0, 1, 1);
    }

}