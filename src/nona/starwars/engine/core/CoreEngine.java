package nona.starwars.engine.core;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;

public class CoreEngine extends Canvas implements Runnable {

    private boolean running;
    private Thread thread;

    private double fps;

    private Game game;

    private Dimension dimension;
    private JPanel panel;
    private JFrame frame;

    private BufferStrategy bufferStrategy;
    private Graphics2D graphics;
    private BufferedImage image;
    private byte[] raster;

    public CoreEngine(Game game, int fps) {
        super();
        this.dimension = new Dimension(game.getWindowWidth(), game.getWindowHeight());
        this.game = game;
        this.fps = (double)fps;

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

        image = new BufferedImage((int)dimension.getWidth(), (int)dimension.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        raster = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();

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
        if(bufferStrategy == null) {
            createBufferStrategy(2);
            bufferStrategy = getBufferStrategy();
        }

        Arrays.fill(raster, (byte)0);

        game.render();

        graphics = (Graphics2D)bufferStrategy.getDrawGraphics();
        graphics.drawImage(image, 0, 0, dimension.width, dimension.height, null);
        graphics.dispose();

        bufferStrategy.show();
    }
}