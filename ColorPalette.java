import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

/* A View for the Color Palette */
class ColorPalette extends JPanel implements Observer {

    private Model model;

    int color;

    BufferedImage icon;

    /**
     * Create a new View for the Color Palette
     */
    public ColorPalette(Model model, int c) {
        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        this.color = c;

        model.addObserver(this);

        this.setBackground(Color.WHITE);

        /* get icon image
         * Ref: https://docs.oracle.com/javase/tutorial/2d/images/examples/JumbledImageApplet.java
         */
        try {
            icon = ImageIO.read(new File("color_" + c + ".png"));
        } catch (IOException e) {
            System.out.println("Invalid color selected!");
        }

        // add mouse listener
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("Color " + color + " choosed!");
                /* choose the color clicked */
                model.chooseColor(color);
            }
        });
    }

    /**
     * Paint the color palette
     * Ref: AdapterEvents.java
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ref: Shapes.java
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.LIGHT_GRAY);

        // if the the color is choosed
        if (this.color == model.color) {
            g2.setStroke(new BasicStroke(7));
        }

        // get component width and height
        int w = getWidth();
        int h = getHeight();
        g2.drawImage(icon, 0, 0, w, h, null); // scale image
        g2.drawRect(0, 0, w, h);
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        repaint();
        //System.out.println("Update ColorPalette View");
    }
}