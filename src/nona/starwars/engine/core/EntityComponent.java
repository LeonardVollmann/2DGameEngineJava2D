package nona.starwars.engine.core;

import nona.starwars.engine.rendering.RenderContext;

public abstract class EntityComponent {

    protected Entity entity;

    public void processInput() {}
    public void update() {}
    public void render(RenderContext target) {}

    public Entity getEntity() {
        return entity;
    }

    public Vector2f getPosition() {
        return entity.getPosition();
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

}