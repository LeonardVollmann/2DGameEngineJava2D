package nona.starwars.engine.core;

public class CoreEngine implements Runnable {

    private boolean running;
    private Thread thread;

    private double fps;

    private Game game;

    public CoreEngine(Game game) {
        running = false;

        thread = new Thread(this);
        thread.start();

        this.game = game;
    }

    public void start() {
        running = true;
        run();
    }

    public void stop() {
        running = false;
    }

    public void run() {
        double frameTime = Time.SECOND / fps;
        int frames = 0;
        double frameCounterTime = 0.0;
        double time_0 = Time.getTime();
        double time_1;
        double delta;
        double unprocessed = 0.0;

        while(running) {
            time_1 = Time.getTime();
            delta = time_1 - time_0;
            time_0 = time_1;

            unprocessed += delta;
            frameCounterTime += delta;

            while (unprocessed >= frameTime) {
                unprocessed -= frameTime;

                input();
                update();
            }

            if (frameCounterTime >= 1) {
                System.out.println(1000.0 / frames + " ms (" + frames + " fps)");
                frameCounterTime = 0;
                frames = 0;
            }

            render();
            frames++;
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
    }
}
