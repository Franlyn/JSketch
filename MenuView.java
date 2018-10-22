import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/* Menu - View */
class MenuView extends JMenuItem implements Observer {
	private Model model;

    /**
     * Create a new View for the Menu
     */
    public MenuView(Model model, String name) {
        // call the parent constructor
        super(name);

        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        model.addObserver(this);

        // add mouse listener
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                /* perform operations when centain option is choosed */
                int option = -1;
                if (name == "Full Size") {
                    option = 0;
                } else if (name == "Fit to Window") {
                    option = 1;
                }
                model.viewOptions(option);
            }
        });
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        System.out.println("Update Menu View");
    }
}