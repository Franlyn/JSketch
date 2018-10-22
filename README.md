# JSketch
JSketch is a vector-drawing program, consisting of a canvas and tool palettes. Users can select drawing properties, and drawing shapes on-screen.

## How to play

### Menu bar:
<ul>
	<li>File-New: create a new drawing. The drawing will always fit the canvas when it is created.</li>
	<li>File-Load: load a drawing from a file (i.e. a file that you previously saved, which replaces the current drawing).</li>
	<li>File-Save: save the current drawing (The file format will be .txt).</li>
	<li>View-Full Size: if this option is selected, the image remains the same size as you resize the window, and scrollbars appear to let you scroll the image (note that you can only select one of the view options at a time, by default the option is set as Full size).</li>
	<li>View-Fit to Window: if this option is selected, the image resizes to fit the window as the window is resized (note that you can only select one of the view options at a time).</li>
</ul>

### Tool palette:
<ul>
	<li>A selection tool: this allows the user to select a shape that has been drawn (there should be some visual indication which shape is selected). To select a shape, the user should click this tool, then click on an existing shape. <strong>Click on another shape or on empty space on the canvas will change / clear shape selection.</strong> Selecting a shape will cause the color palette and line thickness palette to update their state to reflect the currently selected shape (e.g. if I select a red circle with the largest line thickness, the color palette should change to red, and the line thickness should change to the largest line to match the selected shape). Changing color or line thickness when a shape is selected will update the shape properties to the new values.</li>
	<li>An erase tool: click on this tool and then click on a shape to delete it.</li>
	<li>A line drawing tool: select this to draw a line (using the selected color and the line thickness).</li>
	<li>A circle drawing tool: select this to draw an unfilled circle at the point indicated (using the selected line thickness and color for the border).</li>
	<li>A rectangle tool: select this to draw an unfilled rectangle (using the selected line thickness and color for the border).</li>
	<li>A fill tool: select this tool, and click on a shape to fill that shape with the currently selected color.</li>
</ul>

### Color palette:
A graphical display of 6 or more colors, which the user can use to select the current color (alternately, the user can click on the "Chooser" button to bring up a color chooser dialog to select a color). Choosing a color will set the current drawing color, which will be used for any new shapes that are drawn. If a shape is already selected when a color is chosen, the shape's border will change to that new color, if possible.

### Line thickness palette: 
a graphical display of at least 3 line widths that the user can select. Selecting a line width will set the border thickness for any new shapes drawn. Selecting a shape will change the border thickness to reflect the line thickness of that shape.

## Files
JSketch.java: The main file<br \>
Model.java: Model in MVC<br \>
View.java: The main View<br \>
Observer.java: The view interface<br \>
MenuFile.java: Menu - File<br \>
MenuView.java: Menu - View<br \>
ToolPalette.java: A view for the tool palette.<br \>
ColorPalette.java: A view for the color palette.<br \>
LinePalette.java: A view for the line palette.<br \>
Shape.java: A class for defining shapes: line, circle, rectangle.

makefile: compile the program<br \>

Icons: (Reference: https://icons8.com/icon/new-icons/all) <br \>
tool_0.png, tool_1.png, tool_2.png, tool_3.png, tool_4.png, tool_5.png <br \>
color_0.png, color_1.png, color_2.png, color_3.png, color_4.png, color_5.png 

## Compilation
I implemented the program under Mac OS, Java 10.0.2 using Swing components, so I recommend to run it under Mac OS for best experience.<br \>
To compile the program in command line: Enter "make"<br \>
To run the program: Enter "java JSketch"<br \><br \>
Example:
<pre><code>make
java JSketch</code></pre>
