package nona.starwars.engine.core;

import nona.starwars.engine.rendering.RenderContext;

public abstract class Game {

    protected CoreEngine engine;

    protected int windowWidth;
    protected int windowHeight;
    protected String windowTitle;

    protected Entity root;

    public Game() {
        root = new Entity();
    }

    public abstract void input();
    public abstract void update();
    public abstract void render(RenderContext target);

    public CoreEngine getEngine() {
        return engine;
    }

    public void setEngine(CoreEngine engine) {
        this.engine = engine;
        root.setEngine(engine);
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

    public Entity getRootEntity() {
        return root;
    }

    public void add(Entity entity) {
        root.addChild(entity);
    }

}