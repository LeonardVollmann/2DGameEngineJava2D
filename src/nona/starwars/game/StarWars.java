package nona.starwars.game;

import nona.starwars.engine.components.SpriteComponent;
import nona.starwars.engine.core.Entity;
import nona.starwars.engine.core.Game;
import nona.starwars.engine.core.Vector2f;
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

        //scene.add(new Entity().addComponent(new SpriteComponent(new Bitmap("sprite.png"))));

        /*QuadTree quadTree = new QuadTree(new AABB(-100, -100, 100, 100), 2);

        quadTree.add(new Entity(new Vector2f(-3, -3), new Vector2f(0, 0)));
        quadTree.add(new Entity(new Vector2f(-3, 0), new Vector2f(0, 3)));
        quadTree.add(new Entity(new Vector2f(-1, -1), new Vector2f(1, 1)));
        quadTree.add(new Entity(new Vector2f(-5, -5), new Vector2f(0, 0)));
        quadTree.add(new Entity(new Vector2f(-2, -3), new Vector2f(-1, -1)));

        quadTree.print();

        HashSet<Entity> set = new HashSet<Entity>();
        quadTree.queryRange(new AABB(-2, -2, 0, 0), set);

        Iterator it = set.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }*/

        scene.add(new Entity(new Vector2f(-0.1f, -0.1f), new Vector2f(0.1f, 0.1f)).addComponent(new SpriteComponent("sprite.png")));
        scene.add(new Entity(new Vector2f(-0.3f, -0.3f), new Vector2f(-0.2f, -0.2f)).addComponent(new SpriteComponent("sprite.png")));
    }

    @Override
    public void update(float delta) {
        scene.update(delta);
    }

    @Override
    public void render(RenderContext target) {
        scene.render(target);
    }

}