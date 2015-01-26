package nona.starwars.engine.core;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {

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

    public void renderAll() {
        for(Entity child : children) {
            child.renderAll();
        }

        for(EntityComponent component : components) {
            component.render();
        }
    }

    public void addChild(Entity child) {
        children.add(child);
        child.setParent(this);
    }

    public void setParent(Entity parent) {
        this.parent = parent;
    }

}