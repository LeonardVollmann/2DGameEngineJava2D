package nona.starwars.engine.core;

import nona.starwars.engine.rendering.RenderContext;

public abstract class Game {

    protected CoreEngine engine;

    protected int windowWidth;
    protected int windowHeight;
    protected String windowTitle;

    protected Scene scene;

    public Game() {
        scene = new Scene();
    }

    public abstract void update(float delta);
    public abstract void render(RenderContext target);

    public CoreEngine getEngine() {
        return engine;
    }

    public void setEngine(CoreEngine engine) {
        this.engine = engine;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public String getWindowTitle() {
        return windowTitle;
    }

}