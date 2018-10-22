import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;


/* A View for the Canvas */
class Canvas extends JPanel implements Observer {

    private Model model;

    // position of the mouse
    private int x;
    private int y;

    /**
     * Create a new View for the Tool Palette
     */
    public Canvas(Model model) {
        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        model.addObserver(this);

        this.setBackground(Color.WHITE);

        /* add key listener
        setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                switch(e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                        System.out.println("escape");
                        model.clearSelection();
                        break;
                }
            }
        });*/

        /* add mouse listener
         * For mouse dragged to work, need to add mouse motion listener
         * Reason: https://stackoverflow.com/questions/5577619/why-are-mousedragged-events-not-received-when-using-mouseadapter
         */
        setFocusable(true);
        MouseAdapter handler = new MouseAdapter() {
            // a tool is choosed
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();

                if (!model.isFullSize()){
                    double pos[] = getScaled();
                    x /= pos[0];
                    y /= pos[1];
                }

                Point pt = new Point(x, y);

                if (model.tool == 0) { // selection tool
                    model.selectShape(pt);
                } else if (model.tool == 1) { // eraser
                    model.eraseShape(pt);
                } else if (model.tool == 2 || model.tool == 3 || model.tool == 4) { // draw shapes
                    model.drawShape(pt);
                } else if (model.tool == 5) { // fill tool
                    model.fillShape(pt);
                }
            }

            // mouse dragged, create a shape
            public void mouseDragged(MouseEvent e) {
                x = e.getX();
                y = e.getY();

                if (!model.isFullSize()){
                    double pos[] = getScaled();
                    x /= pos[0];
                    y /= pos[1];
                }

                Point pt = new Point(x, y);

                if (model.tool == 0) { // selection tool, move shapes
                    model.moveShape(pt);
                } else if (model.tool == 1) { // eraser
                    model.eraseShape(pt);
                } else if (model.tool == 2 || model.tool == 3 || model.tool == 4) { // drag and complete shapes
                    //System.out.println("continue to draw shape");
                    model.completeDrawingShape(pt);
                }
            }
        };

        this.addMouseListener(handler);
        this.addMouseMotionListener(handler);

    }

    /* scale the point clicked */
    public double[] getScaled() {
        int w = getWidth();
        int h = getHeight();

        double sx = (double) w / model.getW();
        double sy = (double) h / model.getH();
        return new double[] {sx, sy};
    }

    /**
     * Paint the tool palette
     * Ref: AdapterEvents.java
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ref: Shapes.java
        Graphics2D g2 = (Graphics2D) g;
        // get component width and height
        int w = getWidth();
        int h = getHeight();

        if (!model.isFullSize()) {
            double pos[] = getScaled();

            g2.scale(pos[0], pos[1]);
        }

        model.drawShapesOnCanvas(g2);
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        if (model.isFullSize()) {
            setPreferredSize(new Dimension(1600, 1200)); // for scroll bar to appear
        } else {
            setPreferredSize(new Dimension(0, 0));
        }

        repaint();
        //System.out.println("Update Canvas");
    }
}