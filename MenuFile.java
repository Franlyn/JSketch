import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/* Menu Item - File */
class MenuFile extends JMenuItem implements Observer {
	private Model model;

    /**
     * Create a new View for the Menu
     */
    public MenuFile(Model model, String name) {
        // call the parent constructor
        super(name);

        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        model.addObserver(this);

        // add mouse listener
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                /* open a new file when user click "New" */
                if (name == "New") {
                    model.newFile();
                } else {
                    // Ref: https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html
                    JFileChooser chooser = new JFileChooser();

                    if (name == "Load") {
                        int openVal = chooser.showOpenDialog(getParent());

                        if(openVal == JFileChooser.APPROVE_OPTION) {
                            File filechosed = chooser.getSelectedFile();
                            System.out.println("You chose to load this file: " +
                                filechosed.getName());
                            model.loadFile(filechosed);
                        }
                    } else if (name == "Save") {
                        int saveVal = chooser.showSaveDialog(getParent());

                        if(saveVal == JFileChooser.APPROVE_OPTION) {
                            File filechosed = chooser.getSelectedFile();
                            System.out.println("You chose to save this file: " +
                                filechosed.getName());
                            model.saveFile(filechosed);
                        }
                    }

                }
            }
        }); 
    }

    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        System.out.println("Update Menu - File");
    }
}