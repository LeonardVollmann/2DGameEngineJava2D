package nona.starwars.engine.scene;

import nona.starwars.engine.core.Constants;
import nona.starwars.engine.maths.Vector2f;
import nona.starwars.engine.physics.PhysicsComponent;
import nona.starwars.engine.entity.Entity;
import nona.starwars.engine.physics.AABB;
import nona.starwars.engine.graphics.RenderingEngine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Scene {

    private QuadTree tree;

    public Scene() {
        tree = new QuadTree(new AABB(-1, -1, 1, 1), 8);
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

            handleCollisions(current);

            Vector2f startPosition = current.getPos();

            current.updateAll(delta);

            if(current.getPos() != startPosition) {
                remove(current);
                add(current);
            }
        }
    }

    public void render(RenderingEngine target) {
        HashSet<Entity> entitiesToRender = new HashSet<Entity>();
        tree.queryRange(new AABB(-1, -1, 1, 1), entitiesToRender);

        Iterator it = entitiesToRender.iterator();
        while(it.hasNext()) {
            Entity current = (Entity)it.next();

            current.renderAll(target);
        }
    }

    private void handleCollisions(Entity entity) {
        PhysicsComponent component = (PhysicsComponent)entity.getComponent(Constants.COMPONENT_PHYSICS);
        if(component == null) {
            return;
        }

        Set<Entity> set = new HashSet<Entity>();
        tree.queryRange(entity.getAABB(), set);

        Iterator it = set.iterator();
        while(it.hasNext()) {
            Entity other = (Entity)it.next();
            PhysicsComponent otherComponent = (PhysicsComponent)other.getComponent(Constants.COMPONENT_PHYSICS);
            if(otherComponent != null && otherComponent != component) {
                component.collide(otherComponent);
            }
        }
    }

}
