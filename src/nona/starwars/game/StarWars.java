package nona.starwars.game;

import nona.starwars.engine.components.SpriteComponent;
import nona.starwars.engine.core.Entity;
import nona.starwars.engine.core.Game;
import nona.starwars.engine.core.QuadTree;
import nona.starwars.engine.core.Vector2f;
import nona.starwars.engine.physics.AABB;
import nona.starwars.engine.rendering.Bitmap;
import nona.starwars.engine.rendering.RenderContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class StarWars extends Game {

    private ArrayList<Bitmap> bitmaps;

    public StarWars() {
        super();

        windowWidth = 1080;
        windowHeight = 720;
        windowTitle = "Star Wars";

        add(new Entity().addComponent(new SpriteComponent(new Bitmap("sprite.png"))));

        Entity test = new Entity(-1, -1, 1, 1);
        QuadTree quadTree = new QuadTree(test, new AABB(-100, -100, 100, 100));

        quadTree.add(new Entity(new Vector2f(-3, -3), new Vector2f(0, 0)));
        quadTree.add(new Entity(new Vector2f(-3, 0), new Vector2f(0, 3)));
        quadTree.add(new Entity(new Vector2f(-1, -1), new Vector2f(1, 1)));

        quadTree.print();

        HashSet set = quadTree.queryRange(new AABB(-2, -2, 0, 0));

        Iterator it = set.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
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