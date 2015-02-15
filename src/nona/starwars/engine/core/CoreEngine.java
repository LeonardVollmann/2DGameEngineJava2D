package nona.starwars.engine.core;

import nona.starwars.engine.graphics.RenderingEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class CoreEngine extends Canvas implements Runnable {

    private boolean running;
    private Thread thread;

    private double fps;

    private Game game;

    private Dimension dimension;
    private JPanel panel;
    private JFrame frame;

    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private BufferedImage image;
    private byte[] raster;

    private RenderingEngine frameBuffer;

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

        frameBuffer = new RenderingEngine(getWidth(), getHeight());
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
        double unprocessed = 0;

        double nsPerUpdate = Time.SECOND / fps;
        double secondsPerUpdate = 1.0 / fps;

		boolean shouldRender = false;

        long lastTimeMillis = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while(running) {
            now = System.nanoTime();

            unprocessed += (now - lastTime) / nsPerUpdate;

            while(unprocessed > 1) {
                unprocessed--;
                update(secondsPerUpdate);
                updates++;
				shouldRender = true;
            }

			//if(shouldRender) {
                render();
                frames++;
                shouldRender = false;
            //}

            if(System.currentTimeMillis() >= lastTimeMillis) {
                lastTimeMillis += 1000;
                System.out.println(1000.0 / frames + " ms per frame (" + frames + " fps, " + updates + " ups)");
                updates = 0;
                frames = 0;
            }

            lastTime = now;
        }
    }

    private void update(double delta) {
        game.update((float)delta);
    }

    private void render() {
        if(bufferStrategy == null) {
            createBufferStrategy(2);
            bufferStrategy = getBufferStrategy();
            graphics = bufferStrategy.getDrawGraphics();
        }

        frameBuffer.clear((byte)0x00);

        game.render(frameBuffer);

        frameBuffer.copyToBGRArray(raster);

        graphics.drawImage(image, 0, 0, dimension.width, dimension.height, null);

        bufferStrategy.show();
    }

}