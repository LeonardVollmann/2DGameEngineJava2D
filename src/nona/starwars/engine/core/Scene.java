package nona.starwars.engine.core;

import nona.starwars.engine.physics.AABB;
import nona.starwars.engine.rendering.RenderContext;

import java.util.HashSet;
import java.util.Iterator;

public class Scene {

    private QuadTree tree;

    public Scene() {
        tree = new QuadTree(new AABB(-100, -100, 100, 100), 2);
    }

    public void add(Entity entity) {
        tree.add(entity);
    }

    public void remove(Entity entity) {
        tree.remove(entity);
    }

    public void update(float delta) {
        HashSet<Entity> entities = new HashSet<Entity>();
        tree.getAll(entities);

        Iterator it = entities.iterator();
        while(it.hasNext()) {
            Entity current = (Entity)it.next();

            Vector2f startPosition = current.getPosition();

            current.updateAll(delta);

            if(current.getPosition() != startPosition) {
                remove(current);
                add(current);
            }
        }
    }

    public void render(RenderContext target) {
        HashSet<Entity> entitiesToRender = new HashSet<Entity>();
        tree.queryRange(new AABB(-1, -1, 1, 1), entitiesToRender);

        Iterator it = entitiesToRender.iterator();
        while(it.hasNext()) {
            Entity current = (Entity)it.next();

            current.renderAll(target);
        }
    }

}
