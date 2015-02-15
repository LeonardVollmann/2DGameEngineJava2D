package nona.starwars.game;

import nona.starwars.engine.components.PhysicsComponent;
import nona.starwars.engine.components.SpriteComponent;
import nona.starwars.engine.core.Entity;
import nona.starwars.engine.core.Game;
import nona.starwars.engine.core.Vector2f;
import nona.starwars.engine.rendering.Bitmap;
import nona.starwars.engine.rendering.RenderContext;

import java.util.ArrayList;
import java.util.Random;

public class StarWars extends Game {

    private ArrayList<Bitmap> bitmaps;

    public StarWars() {
        super();

        windowWidth = 720;
        windowHeight = 540;
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

        //scene.add(new Entity(new Vector2f(-0.1f, -0.1f), new Vector2f(0.1f, 0.1f)).addComponent(new SpriteComponent("sprite.png")));
        //scene.add(new Entity(new Vector2f(-0.3f, -0.3f), new Vector2f(-0.2f, -0.2f)).addComponent(new SpriteComponent("sprite.png")));

        //scene.add(new Entity(new Vector2f(-0.25f, -0.25f), new Vector2f(0.25f, 0.25f)).addComponent(new SpriteComponent(new Bitmap(16, 16).randomize())));
        //scene.add(new Entity(new Vector2f(-1.0f, -1.0f), new Vector2f(-0.5f, -0.5f)).addComponent(new SpriteComponent(new Bitmap(16, 16).randomize())).addComponent(new PhysicsComponent()));
		
		Random random = new Random();
		float size = 0.1f;
		for(int i = 0; i < 100; i++) {
			scene.add(new Entity((random.nextFloat() - 0.5f) * 2.0f, (random.nextFloat() - 0.5f) * 2.0f, size, size)
                    .addComponent(new SpriteComponent("sprite.png"))
                    .addComponent(new PhysicsComponent(new Vector2f((random.nextFloat() - 0.5f) * 2.0f, (random.nextFloat() - 0.5f) * 2.0f))));
		}
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
