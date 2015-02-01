package nona.starwars.engine.core;

import nona.starwars.engine.rendering.RenderContext;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class CoreEngine extends Canvas implements Runnable {

    private boolean running;
    private Thread thread;

    private int fps;
    private float delta = 0;

    private Game game;

    private Dimension dimension;
    private JPanel panel;
    private JFrame frame;

    private BufferStrategy bufferStrategy;
    private Graphics2D graphics;
    private BufferedImage image;
    private byte[] raster;

    private RenderContext frameBuffer;

    public CoreEngine(Game game, int fps) {
        super();
        this.dimension = new Dimension(game.getWindowWidth(), game.getWindowHeight());
        this.game = game;
        this.fps = fps;

        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);

        panel = new JPanel(new BorderLayout());
        panel.add(this, BorderLayout.CENTER);

        frame = new JFrame(game.getWindowTitle());
        frame.setContentPane(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        raster = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();

        running = false;

        thread = new Thread(this);
        thread.start();

        frameBuffer = new RenderContext(getWidth(), getHeight());
    }

    public void start() {
        running = true;
        run();
    }

    public void stop() {
        running = false;
    }

    public void run() {
        long lastTime = System.nanoTime();
        long now;
        double nsPerUpdate = Time.SECOND / (double)fps;
        double unprocessed = 0;
        long lastTimeMillis = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
		boolean shouldRender = false;

        while(running) {
            now = System.nanoTime();

            delta = (now - lastTime) / (float)Time.SECOND;
            unprocessed += (now - lastTime) / nsPerUpdate;

            while(unprocessed > 1) {
                unprocessed--;
                update();
                updates++;
				shouldRender = true;
            }

			if(shouldRender) {
                render();
                frames++;
                shouldRender = false;
            }

            if(System.currentTimeMillis() - lastTimeMillis >= 1000) {
                lastTimeMillis += 1000;
                System.out.println(1000.0 / frames + " ms per frame (" + frames + " fps, " + updates + " ups)");
                updates = 0;
                frames = 0;
            }

            lastTime = now;
        }
    }

    private void input() {
        game.input();
    }

    private void update() {
        game.update(delta);
    }

    private void render() {
        if(bufferStrategy == null) {
            createBufferStrategy(2);
            bufferStrategy = getBufferStrategy();
        }

        frameBuffer.clear((byte)0x00);

        game.render(frameBuffer);

        frameBuffer.copyToBGRArray(raster);

        graphics = (Graphics2D)bufferStrategy.getDrawGraphics();
        graphics.drawImage(image, 0, 0, dimension.width, dimension.height, null);
        graphics.dispose();

        bufferStrategy.show();
    }

    public int getFPS() {
        return fps;
    }

    public float getFrameTime() {
        return 1.0f / (float)fps;
    }

}