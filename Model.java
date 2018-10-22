import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.geom.*;
import java.awt.Point;
import java.awt.Graphics2D;


/* Main Model */
public class Model {
    /** The observers that are watching this model for changes. */
    private List<Observer> observers;
    private View mainview;

    // a list of shapes on the canvas
    private List<Shape> shapes;

    // current file
    File file = null;

    // true if the full size option is selected
    Boolean fullsize = true;

    // current tool used on tool palette, by default selection tool
    int tool = 0;
    // current color used on color palette, by default BLACK
    int color = 0;
    // current line style used on line palette, by default THINNEST
    int line = 0;

    /**
     * Create a new model.
     */
    public Model() {
        this.observers = new ArrayList<Observer>();
        this.shapes = new ArrayList<Shape>();
    }

    /**
     * Add a main view observer to be notified when this model changes.
     */
    public void addMainView(View v) {
        this.mainview = v;
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /* Get the width and height of the current window */
    public double getW() {
        return this.mainview.getW();
    }

    public double getH() {
        return this.mainview.getH();
    }


    /**
     * Choose a tool on tool Palette
     */
    public void chooseTool(int t) {
        if (tool != t) {
            int prevtool = this.tool;
            this.tool = t;

            if (prevtool != -1) {
                notifyObservers(prevtool + 5);  // first 5 are menu items
            }
            notifyObservers(this.tool + 5);
        }
    }

    /**
     * Choose a color on color Palette
     */
    public void chooseColor(int c) {
        if (color != c) {
            int prevc = this.color;
            this.color = c;

            if (prevc != -1) {
                notifyObservers(prevc + 11);  // 5 menu items, 6 tools
            }
            notifyObservers(this.color + 11);

            // change the color for selected shape
            int num_shapes = this.shapes.size();

            for (int i = 0; i < num_shapes; i++) {
                Shape cur = shapes.get(i);
                if (cur.getSelected()) {
                    cur.setColor(this.color);
                }
            }
            notifyObservers(21);
        }
    }

    /**
     * Choose a line on line Palette
     */
    public void chooseLine(int l) {
        if (line != l) {
            int prevl = this.line;
            this.line = l;

            if (prevl != -1) {
                notifyObservers(prevl + 17);  // 5  menu items, 6 tools, 6 colors
            }
            notifyObservers(this.line + 17);

            // change the line for selected shape
            int num_shapes = this.shapes.size();

            for (int i = 0; i < num_shapes; i++) {
                Shape cur = shapes.get(i);
                if (cur.getSelected()) {
                    cur.setStrokeThickness(this.line);
                }
            }
            notifyObservers(21);
        }
    }

    /**
     * Select, Unselect, Erase, Draw shapes when mouse pressed
     * Used in Canvas.java
     */
    public void selectShape(Point pt) {
        int num_shapes = this.shapes.size();

        for (int i = 0; i < num_shapes; i++) {
            Shape cur = shapes.get(i);
            if (cur.ifSelected(pt)) {
                cur.setSelected(true, pt);
                //cur.setColor(this.color);
                //cur.setStrokeThickness(this.line);
                this.color = cur.getColor();
                this.line = cur.getStrokeThickness();
            } else {
                cur.setSelected(false, null);
            }
        }

        notifyObservers(-1); // notify all colors & lines
    }

    public void clearSelection() {
        int num_shapes = this.shapes.size();

        for (int i = 0; i < num_shapes; i++) {
            shapes.get(i).setSelected(false, null);
        }

        notifyObservers(21);
    }

    public void eraseShape(Point pt) {
        int num_shapes = this.shapes.size();

        for (int i = 0; i < num_shapes; i++) {
            if (shapes.get(i).ifSelected(pt)) {
                System.out.println("erased!");
                shapes.remove(i);
                break;
            }
        }

        notifyObservers(21);
    }

    public void drawShape(Point pt) {
        int shapeType = this.tool - 2;
        Shape s = new Shape(pt, shapeType, this.color, this.line);
        shapes.add(s);
    }

    public void fillShape(Point pt) {
        int num_shapes = this.shapes.size();

        for (int i = 0; i < num_shapes; i++) {
            Shape cur = shapes.get(i);
            if (cur.ifSelected(pt)) {
                cur.setColor(this.color);
                shapes.get(i).setIsFilled(true);
                break;
            }
        }

        notifyObservers(21);
    }

    /**
     * Move a shape. Complete drawing a shape when mouse dragged
     */
    public void moveShape(Point pt) {
        int num_shapes = this.shapes.size();

        for (int i = 0; i < num_shapes; i++) {
            Shape cur = shapes.get(i);
            if (cur.getSelected()) {
                cur.movePoints(pt);
            }
        }

        notifyObservers(21);
    }

    public void completeDrawingShape(Point pt) {
        // get the latest shape
        Shape latest = (this.shapes).get((this.shapes).size() - 1);
        latest.updateEndPt(pt);

        // notify the canvas and main view
        notifyObservers(21);
        //notifyMainView();
    }

    /**
     * Draw all shapes on the canvas
     * Used in Canvas.java
     */
    public void drawShapesOnCanvas(Graphics2D g2) {
        int num_shapes = this.shapes.size();

        for (int i = 0; i < num_shapes; i++) {
            this.shapes.get(i).draw(g2);
        }
    }


    /**
     * Three functions: Open, Load, Save a file
     * Used in MenuFile.java
     */
    public void newFile() {
        file = null;
        // clean the canvas
        shapes.clear();

        notifyObservers(21);
        notifyMainView();
    }

    public void loadFile(File f) {
        file = f;

        // clean the canvas
        shapes.clear();

        // read from the file
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            //sc.useDelimiter("\\n");

            while (sc.hasNextLine()) {

                String line = sc.nextLine();
                String input[] = line.split(" ");
                Shape curShape = new Shape(new Point(Integer.parseInt(input[0]), 
                                                    Integer.parseInt(input[1])),
                                        Integer.parseInt(input[4]),
                                        Integer.parseInt(input[5]),
                                        Integer.parseInt(input[6]));
                curShape.updateEndPt(new Point(Integer.parseInt(input[2]), 
                                           Integer.parseInt(input[3])));
                curShape.setIsFilled(Integer.parseInt(input[7]) == 1 ? true : false);
                shapes.add(curShape);
            }

            sc.close();
        } catch (IOException e) {
            System.out.println("Fail to load file.");

            if (sc != null) sc.close();
        }

        notifyObservers(21);
        notifyMainView();
    }

    public void saveFile(File f) {
        FileWriter output = null;

        try {

            output = new FileWriter(f.getPath());

            // loop through all shapes on canvas, write to filewriter
            int num_shapes = this.shapes.size();

            for (int i = 0; i < num_shapes; i++) {
                Shape cur = shapes.get(i);

            // write to the file
                String shapedata = Integer.toString(cur.getStartPt().x) + ' '
                + Integer.toString(cur.getStartPt().y) + ' '
                + Integer.toString(cur.getEndPt().x) + ' '
                + Integer.toString(cur.getEndPt().y) + ' '
                + Integer.toString(cur.getShape()) + ' '
                + Integer.toString(cur.getColor()) + ' '
                + Integer.toString(cur.getStrokeThickness()) + ' '
                + Integer.toString(cur.getIsFilled() ? 1 : 0) + '\n';
                output.write(shapedata);
            }

            output.close();
        } catch (IOException e) {
            System.out.println("Fail to save file.");
            
            try {
                output.close();
            } catch (IOException closeE) {
                // pass
            }
        }

        notifyObservers(21);
        notifyMainView();
    }

    /**
     * Two different view modes
     * op = 0: Full Size
     * op = 1: Fit to Window
     * Used in MenuView.java
     */
    public void viewOptions(int op) {
        if (op == 0) {
            this.fullsize = true;
        } else {
            this.fullsize = false;
        }
        notifyObservers(21);
        notifyMainView();
    }

    public Boolean isFullSize() {
        return this.fullsize;
    }


    /**
     * Notify observers that the model has changed.
     * if num = -1, then notify all observers
     */
    public void notifyObservers(int num) {
        if (num != -1) {
            observers.get(num).update(this);
            return;
        }

        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }

    /**
     * Notify the main view observer that the model has changed.
     */
    public void notifyMainView() {
        mainview.update(this);
    }
}
