package nona.starwars.engine.core;

import nona.starwars.engine.rendering.RenderContext;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    private Entity parent;
    private List<Entity> children;
    private List<EntityComponent> components;

    private Vector2f position;

    private CoreEngine engine;

    public Entity() {
        children = new ArrayList<Entity>();
        components = new ArrayList<EntityComponent>();

        position = new Vector2f();
    }

    public void processInputAll() {
        for(Entity child : children) {
            child.processInputAll();
        }

        for(EntityComponent component : components) {
            component.processInput();
        }
    }

    public void updateAll(float delta) {
        for(Entity child : children) {
            child.updateAll(delta);
        }

        for(EntityComponent component : components) {
            component.update(delta);
        }
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

    public Vector2f getPosition() {
        return position;
    }

    public CoreEngine getEngine() {
        return engine;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public void setEngine(CoreEngine engine) {
        this.engine = engine;
    }

}