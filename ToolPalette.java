import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

/* A View for the Tool Palette */
class ToolPalette extends JPanel implements Observer {

    private Model model;

    int tool;

    BufferedImage icon;

    /**
     * Create a new View for the Tool Palette
     */
    public ToolPalette(Model model, int t) {
        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;

        this.tool = t;

        model.addObserver(this);

        this.setBackground(Color.WHITE);

        /* get icon image
         * Ref: https://docs.oracle.com/javase/tutorial/2d/images/examples/JumbledImageApplet.java
         */
        try {
            icon = ImageIO.read(new File("tool_" + t + ".png"));
        } catch (IOException e) {
            System.out.println("Invalid tool selected!");
        }

        // add mouse listener
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("Tool " + tool + " choosed!");
                /* choose the tool clicked */
                model.chooseTool(tool);
            }
        });
    }

    /**
     * Paint the tool palette
     * Ref: AdapterEvents.java
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ref: Shapes.java
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.LIGHT_GRAY);

        g2.setStroke(new BasicStroke(1));

        // if the the tool is choosed
        if (tool == model.tool) {
            g2.setStroke(new BasicStroke(7));
        }

        // get component width and height
        int w = getWidth();
        int h = getHeight();
        g2.drawImage(icon, w/15, h/15, 9*w/10, 9*h/10, null); // scale image
        g2.drawRect(0, 0, w, h);
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        repaint();
        //System.out.println("Update ToolPalette View, tool " + this.tool);
    }
}