package nona.starwars.engine.core;

import nona.starwars.engine.rendering.Window;

public class CoreEngine implements Runnable {

    private boolean running;
    private Thread thread;

    private double fps;

    private Game game;

    private Window window;

    public CoreEngine(Game game, int fps) {
        this.game = game;
        this.fps = (double)fps;
        this.window = new Window(game.getWindowWidth(), game.getWindowHeight(), game.getWindowTitle());

        running = false;

        thread = new Thread(this);
        thread.start();
    }

    public void start() {
        running = true;
        run();
    }

    public void stop() {
        running = false;
    }

    public void run() {
        long lastTime = Time.getTimeNano();
        long now;
        double nsPerUpdate = Time.SECOND / fps;
        double delta = 0;
        long lastTimeMillis = Time.getTimeMillis();
        int updates = 0;
        int frames = 0;
		boolean shouldrender = false;

        while(running) {
            now = System.nanoTime();

            delta += (now - lastTime) / nsPerUpdate;

            while(delta > 1) {
                delta--;
                update();
                updates++;
				shouldrender = true;
            }

			if(shouldrender) {
                render();
                frames++;
				shouldrender = false;
            }

            lastTime = now;

            if(System.currentTimeMillis() - lastTimeMillis >= 1000) {
                lastTimeMillis += 1000;
                System.out.println(1000.0 / frames + " ms per frame (" + frames + " fps, " + updates + " ups)");
                updates = 0;
                frames = 0;
            }
        }
    }

    private void input() {
        game.input();
    }

    private void update() {
        game.update();
    }

    private void render() {
        game.render();
        window.update();
    }
}