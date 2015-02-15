package nona.starwars.engine.entity;

import nona.starwars.engine.maths.Vector2f;
import nona.starwars.engine.graphics.RenderingEngine;

public abstract class EntityComponent {

    protected Entity entity;

    public void update(float delta) {}
    public void render(RenderingEngine target) {}

    public Entity getEntity() {
        return entity;
    }

    public Vector2f getPos() {
        return entity.getPos();
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public abstract String getName();

}
