package nona.starwars.engine.core;

public abstract class EntityComponent {

    protected Entity entity;

    public abstract void processInput();
    public abstract void update();
    public abstract void render();

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

}