package nona.starwars.engine.core;

import nona.starwars.engine.physics.AABB;
import nona.starwars.engine.rendering.RenderContext;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    private Entity parent;
    private List<Entity> children;
    private List<EntityComponent> components;

    private Vector2f pos;
    private Vector2f vel;
    private AABB aabb;

    private CoreEngine engine;

    public Entity(Vector2f min, Vector2f max) {
        children = new ArrayList<Entity>();
        components = new ArrayList<EntityComponent>();

        aabb = new AABB(min, max);
        pos = aabb.getCenter();
        vel = new Vector2f(1.0f, 1.0f);
    }

    public Entity(float xCenter, float yCenter, float width, float height) {
        this(new Vector2f(xCenter - width / 2.0f, yCenter - height / 2.0f),
                new Vector2f(xCenter + width / 2.0f, yCenter + height / 2.0f));
    }

    public Entity() {
        this(0, 0, 1, 1);
    }

    public void updateAll(float delta) {
        for(Entity child : children) {
            child.updateAll(delta);
        }

        for(EntityComponent component : components) {
            component.update(delta);
        }

        updateAABB();
    }

    public void renderAll(RenderContext target) {
        for(Entity child : children) {
            child.renderAll(target);
        }

        for(EntityComponent component : components) {
            component.render(target);
        }
    }

    public Entity addChild(Entity child) {
        child.setParent(this);
        children.add(child);

        return this;
    }

    public Entity addComponent(EntityComponent component) {
        component.setEntity(this);
        components.add(component);

        return this;
    }

    public void setParent(Entity parent) {
        this.parent = parent;
    }

    public AABB getAABB() {
        return aabb;
    }

    public Vector2f getPos() {
        return pos;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    public Vector2f getVel() {
        return vel;
    }

    public void setVel(Vector2f vel) {
        this.vel = vel;
    }

    public CoreEngine getEngine() {
        return engine;
    }

    public void setEngine(CoreEngine engine) {
        this.engine = engine;
    }

    private void updateAABB() {
        Vector2f delta = pos.sub(aabb.getCenter());

        aabb = new AABB(aabb.getMin().add(delta), aabb.getMax().add(delta));
    }

}