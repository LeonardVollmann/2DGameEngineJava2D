package nona.starwars.engine.core;

import nona.starwars.engine.rendering.RenderContext;

public abstract class EntityComponent {

    protected Entity entity;

    public abstract void processInput();
    public abstract void update();
    public abstract void render(RenderContext target);

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

}