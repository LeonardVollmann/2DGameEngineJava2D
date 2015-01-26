package nona.starwars.engine.rendering;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Window extends Canvas {

    private Dimension dimension;

    private JPanel panel;
    private JFrame frame;

    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    private BufferedImage image;
    private byte[] raster;

    public Window(int width, int height, String title) {
        super();

        this.dimension = new Dimension(width, height);

        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);

        panel = new JPanel(new BorderLayout());
        panel.add(this, BorderLayout.CENTER);

        frame = new JFrame(title);
        frame.setContentPane(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        raster = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
    }

    public void update() {
        if(bufferStrategy == null) {
            createBufferStrategy(2);
            bufferStrategy = getBufferStrategy();
            graphics = bufferStrategy.getDrawGraphics();
        }

        graphics.drawImage(image, 0, 0, dimension.width, dimension.height, null);

        bufferStrategy.show();
    }

    public byte[] getRaster() {
        return raster;
    }

}
