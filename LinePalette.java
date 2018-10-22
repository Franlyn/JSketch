import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

/* A View for the Line Palette */
class LinePalette extends JPanel implements Observer {

    private Model model;

    int line;

    /**
     * Create a new View for the Line Palette
     */
    public LinePalette(Model model, int l) {
        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        this.line = l;

        model.addObserver(this);

        this.setBackground(Color.WHITE);

        // add mouse listener
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("Line " + line + " choosed!");
                /* choose the line clicked */
                model.chooseLine(line);
            }
        });
    }

    /**
     * Paint the line palette
     * Ref: AdapterEvents.java
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ref: Shapes.java
        Graphics2D g2 = (Graphics2D) g;
        // get component width and height
        int w = getWidth();
        int h = getHeight();

        // draw the line
        g2.setStroke(new BasicStroke(2 * (line + 1)));
        g2.setColor(Color.BLACK);
        g2.drawLine(w/10, h/2, 9 * w/10, h/2);
        g2.setColor(Color.LIGHT_GRAY);

        // the rectangle outside the line
        g2.setStroke(new BasicStroke(1));

        // if the the line is choosed
        if (this.line == model.line) {
            g2.setStroke(new BasicStroke(7));
        }
        g2.drawRect(0, 0, w, h);
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        repaint();
        //System.out.println("Update LinePalette View");
    }
}