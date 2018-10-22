import java.io.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;	
import java.util.*;

/* Main class
   Create the frame and the view
*/
public class JSketch {
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View(model, "JSKetch");

        // build menu - File
        JMenu menu_file = new JMenu("File");
        // New, Load, and Save
        for (String s: new String[] {"New", "Load", "Save"}) {
        	MenuFile mi = new MenuFile(model, s);
        	menu_file.add(mi);
        }

        // build menu - View
        JMenu menu_view = new JMenu("View");
        // Full size, and Fit to Window
        for (String s: new String[] {"Full Size", "Fit to Window"}) {
        	MenuView mi = new MenuView(model, s);
        	menu_view.add(mi);
        }

        // create the menu bar, Add the above to the menuBar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu_file);
        menuBar.add(menu_view);

        // set up the menubar
        view.setJMenuBar(menuBar);


        /* Create the layout Constraints
         * Ref: LayoutDemo.java
         */
        GridBagConstraints gc = new GridBagConstraints();

        int windowW = view.getW();
        int windowH = view.getH();

        /* create the six tool palettes
         * they are: a selection tool, an erase tool, a line drawing tool,
         * a circle drawing tool, a rectangle tool, a fill tool
         */
        for (int i = 0; i < 6; i++) {
        	ToolPalette tool = new ToolPalette(model, i);

        	// stretch the widget horizontally and vertically
			gc.fill = GridBagConstraints.BOTH;
			gc.gridx = i % 2; // 0 or 1 only
			gc.gridy = i / 2; // can be 0, 1, 2
            
			gc.weightx = 0.06; // % horizontal space give to gc
			gc.weighty = 0.1; // % vertical space give to gc

			// add to view
			view.add(tool, gc);
        }

        /* create the six colors
         * they are: RED, BLACK, YELLOW, GREEN, BLUE, PURPLE
         */
        for (int i = 0; i < 6; i++) {
        	ColorPalette color = new ColorPalette(model, i);

            // stretch the widget horizontally and vertically
            gc.fill = GridBagConstraints.BOTH;
            gc.gridx = i % 2; // 0 or 1 only
            gc.gridy = i / 2 + 3; // can be 3, 4, 5
            
            gc.weightx = 0.06; // % horizontal space give to gc
            gc.weighty = 0.1; // % vertical space give to gc

            // add to view
            view.add(color, gc);
        }

        /* create three line thickness palettes
         */
        for (int i = 0; i < 4; i++) {
        	LinePalette line = new LinePalette(model, i);

            // stretch the widget horizontally and vertically
            gc.fill = GridBagConstraints.BOTH;
            gc.gridx = 0; // 0 only
            gc.gridy = i + 6; // can be 6, 7, 8, 9
            gc.gridwidth = 2; // 2 cells in a row
            
            gc.weightx = 0.12; // % horizontal space give to gc
            gc.weighty = 0.1; // % vertical space give to gc

            // add to view
            view.add(line, gc);
        }

        /* Create the canvas */
        Canvas canvas = new Canvas(model);
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 2; // from the 2nd cell
        gc.gridy = 0; // from the first cell
        gc.gridheight = 10; // 10 cells in a column
            
        gc.weightx = 0.88; // % horizontal space give to gc
        gc.weighty = 1; // % vertical space give to gc

        // add to view
        JScrollPane scroller = new JScrollPane(canvas);
        view.add(scroller, gc);

        view.setVisible(true);
    }
}
