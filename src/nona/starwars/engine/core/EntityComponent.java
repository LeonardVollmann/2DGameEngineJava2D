package nona.starwars.engine.core;

import nona.starwars.engine.rendering.RenderContext;

public abstract class EntityComponent {

    protected Entity entity;

    public void update(float delta) {}
    public void render(RenderContext target) {}

    public Entity getEntity() {
        return entity;
    }

    public Vector2f getPos() {
        return entity.getPos();
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

}