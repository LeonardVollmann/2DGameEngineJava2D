package nona.starwars.engine.core;

import nona.starwars.engine.rendering.RenderContext;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    protected Entity parent;
    protected List<Entity> children;
    protected List<EntityComponent> components;

    public Entity() {
        children = new ArrayList<Entity>();
        components = new ArrayList<EntityComponent>();
    }

    public void processInputAll() {
        for(Entity child : children) {
            child.processInputAll();
        }

        for(EntityComponent component : components) {
            component.processInput();
        }
    }

    public void updateAll() {
        for(Entity child : children) {
            child.updateAll();
        }

        for(EntityComponent component : components) {
            component.update();
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

}