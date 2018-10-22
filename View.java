
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

/* The main view */
public class View extends JFrame implements Observer {

    private Model model;

    // width and height of the current window, 8:6
    private int w = 800;
    private int h = 600;

    /**
     * Create a new View.
     */
    public View(Model model, String title) {
        super(title);

        // Set up the window.
        this.setTitle(title);
        this.setMinimumSize(new Dimension(400, 300));
        this.setMaximumSize(new Dimension(1600, 1200));
        this.setSize(w, h);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());

        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        model.addMainView(this);

        this.setVisible(true);
    }

    /**
     * Return the width, height of the window
     */
    public int getW() {
        return this.w;
    }

    public int getH() {
        return this.h;
    }

    /**
     * Resize the window
     */
    public void resize(int op) {
        // TODO: update correct sizes
        if (op == 0) {
            this.w = 750;
            this.h = 400;
        } else if (op == 1) {
            this.w = 1200;
            this.h = 800;
        } else {
            System.out.println("Illegal view option!");
        }
        this.setSize(w, h);
        this.setVisible(true);
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        this.setVisible(true);
        System.out.println("Main View changed!");
    }
}
