package nona.starwars.engine.core;

public abstract class Game {

    private CoreEngine engine;

    public abstract void input();
    public abstract void update();
    public abstract void render();

    public CoreEngine getEngine() {
        return engine;
    }

    public void setEngine(CoreEngine engine) {
        this.engine = engine;
    }

}
