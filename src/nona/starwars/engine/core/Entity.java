package nona.starwars.engine.core;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    private Entity parent;
    private List<Entity> children;

    public Entity() {
        children = new ArrayList<Entity>();
    }

    public void processInputAll() {
        for(Entity child : children) {
            child.processInputAll();
        }
    }

    public void updateAll() {
        for(Entity child : children) {
            child.updateAll();
        }
    }

    public void renderAll() {
        for(Entity child : children) {
            child.renderAll();
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