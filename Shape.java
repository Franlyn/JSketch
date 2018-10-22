/*
*  Shape
*  Ref: 2-8-Transformation/shapemodel/Shapes.java
*/
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.*;

// simple shape model class
class Shape {

    // shape points
    Point start_pt;
    Point end_pt;

    // type of the shape: 0 - line, 1 - circle, 2 - rectangle
    int type = -1;

    // drawing attributes
    Color color = Color.BLACK;
    int colorNumber = -1;
    float strokeThickness = 2.0f;
    int strokeThicknessNumber = -1;

    // if polygon is filled or not
    Boolean isFilled = false; 

    // if the shape is selected
    Boolean selected = false;
    Point selectedPt;

    public Shape(Point p, int type, int color, int line) {
        this.start_pt = p;
        this.end_pt = p;
        this.selectedPt = new Point(p.x, p.y);

        this.type = type;
        setColor(color);
        setStrokeThickness(line);
    }

    public void updateEndPt(Point new_p) {
        this.end_pt = new_p;
    }

    public Point getStartPt() {
        return start_pt;
    }

    public Point getEndPt() {
        return end_pt;
    }

    public int getShape() {
        return type;
    }

    public void movePoints(Point new_p) {
        //System.out.println("move points are "+new_p.x+", "+new_p.y);
        // calculate difference
        int diffX = new_p.x - selectedPt.x;
        int diffY = new_p.y - selectedPt.y;

        // update start, end point, and selected point
        selectedPt.x = new_p.x;
        selectedPt.y = new_p.y;
        start_pt.x += diffX;
        start_pt.y += diffY;
        end_pt.x += diffX;
        end_pt.y += diffY;
    }

    public void setSelected(Boolean s, Point p) {
        this.selected = s;
        if (s) {
            //System.out.println("points are "+p.x+", "+p.y);
            selectedPt.x = p.x;
            selectedPt.y = p.y;
        }
    }

    public Boolean getSelected() {
        return this.selected;
    }


    public Boolean getIsFilled() {
        return isFilled;
    }

    public void setIsFilled(Boolean isFilled) {
        this.isFilled = isFilled;
    }    

    public int getColor() {
		return colorNumber;
	}

	public void setColor(int c) {
        this.colorNumber = c;

		if (c == 0) {
            this.color = Color.BLACK;
        } else if (c == 1) {
            this.color = Color.RED;
        } else if (c == 2) {
            this.color = Color.YELLOW;
        } else if (c == 3) {
            this.color = Color.GREEN;
        } else if (c == 4) {
            this.color = Color.BLUE;
        } else if (c == 5) {
            this.color = Color.PINK;
        }
	}

    public int getStrokeThickness() {
		return strokeThicknessNumber;
	}

	public void setStrokeThickness(int l) {
        this.strokeThicknessNumber = l;

        if (l == 0) {
            this.strokeThickness = 2.0f;
        } else if (l == 1) {
            this.strokeThickness = 3.0f;
        } else if (l == 2) {
            this.strokeThickness = 6.0f;
        } else if (l == 3) {
            this.strokeThickness = 10.0f;
        }
	}

    // Determine if the shape is selected
    public boolean ifSelected(Point p) {
        if (this.type == 0) { // line

            return ifOnLine(start_pt, end_pt, p);

        } else if (this.type == 1) { // circle
            int centerX = start_pt.x + (end_pt.x - start_pt.x) / 2;
            int centerY = start_pt.y + (end_pt.y - start_pt.y) / 2;
            int halfW = Math.abs(end_pt.x - start_pt.x) / 2;
            int halfH = Math.abs(end_pt.y - start_pt.y) / 2;

            double left = Math.pow(p.x - centerX, 2) / Math.pow(halfW, 2)
                    + Math.pow(p.y - centerY, 2) / Math.pow(halfH, 2);
            
            //if (this.getIsFilled()) {
                // if filled, check if p is within or on the circle
                return left <= 1.05;
            //}

            // else check if p is on the circle only
            //return (left <= 1.05 && left >= 0.95);
            
        } else if (this.type == 2) { // rectangle
            int h = end_pt.y - start_pt.y;
            int w = end_pt.x - start_pt.x;

            Point topleft = new Point(start_pt.x, start_pt.y);
            Point bottomright = new Point(end_pt.x, end_pt.y);
            if (h < 0 && w < 0) { // start pt is on the bottom-right
                topleft.x += w;
                topleft.y += h;
                bottomright.x -= w;
                bottomright.y -= h;
            } else if (h < 0) { // start pt is on the bottom-left
                topleft.y += h;
                bottomright.y -= h;
            } else if (w < 0) { // start pt is on the top-right
                topleft.x += w;
                bottomright.x -= w;
            }


            //if (this.getIsFilled()) {
                Rectangle rect = new Rectangle(topleft.x, topleft.y, 
                                            Math.abs(w), Math.abs(h));
                return rect.contains(p);
            /*} else {
                // check each four edges
                Point topright = new Point(topleft.x + Math.abs(w), topleft.y);
                Point bottomleft = new Point(bottomright.x - Math.abs(w), bottomright.y);
                return (ifOnLine(topleft, topright, p)) ||
                    (ifOnLine(topright, bottomright, p)) ||
                    (ifOnLine(bottomright, bottomleft, p)) ||
                    (ifOnLine(topleft, bottomleft, p));
            }*/
        }
        return false;
    }


    // return true if p is on line(start, end)
    public boolean ifOnLine(Point start, Point end, Point p) {
        int leftD = distance(start_pt, p) + distance(p, end_pt);
        int rightD = distance(start_pt, end_pt);
            
        // for better user experience
        return (leftD <= rightD + 1) && (leftD >= rightD - 1);
    }

    // return distance between p1, p2
    public int distance(Point p1, Point p2) {
        return (int) Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }

	
    
    // let the shape draw itself
    // (note this isn't good separation of shape View from shape Model)
    public void draw(Graphics2D g2) {

        // don't draw if points are empty (not shape)
        if (start_pt == null || end_pt == null) return;

        g2.setStroke(new BasicStroke(this.strokeThickness));
        g2.setColor(this.color);

        // set dashed stroke 
        // ref: https://docstore.mik.ua/orelly/java-ent/jfc/ch04_05.htm
        Stroke dashed = new BasicStroke(this.strokeThickness,    // Width
                           BasicStroke.CAP_SQUARE,    // End cap
                           BasicStroke.JOIN_MITER,    // Join style
                           10.0f,                     // Miter limit
                           new float[] {16.0f,20.0f}, // Dash pattern
                           0.0f);                     // Dash phase

        if (this.selected) {
            g2.setStroke(dashed);
        }

        // call drawing functions  
        if (this.type == 0) { // line
            g2.drawLine(start_pt.x, start_pt.y, end_pt.x, end_pt.y);
        } else if (this.type == 1 || this.type == 2) { // circle or rectangle
            // find diameter
            int h = end_pt.y - start_pt.y;
            int w = end_pt.x - start_pt.x;

            int topleftX = start_pt.x;
            int topleftY = start_pt.y;
            if (h < 0 && w < 0) { // start pt is on the bottom-right
                topleftX += w;
                topleftY += h;
            } else if (h < 0) { // start pt is on the bottom-left
                topleftY += h;
            } else if (w < 0) { // start pt is on the top-right
                topleftX += w;
            }

            if (this.type == 1) { // circle
                if (this.getIsFilled()) {
                    g2.fillOval(topleftX, topleftY, Math.abs(w), Math.abs(h));

                    if (this.selected) {
                        g2.setColor(Color.WHITE);
                        g2.drawOval(topleftX, topleftY, Math.abs(w), Math.abs(h));
                    }
                } else {
                    g2.drawOval(topleftX, topleftY, Math.abs(w), Math.abs(h));
                }
                
            } else { // rect
                if (this.getIsFilled()) {
                    g2.fillRect(topleftX, topleftY, Math.abs(w), Math.abs(h));

                    if (this.selected) {
                        g2.setColor(Color.WHITE);
                        g2.drawRect(topleftX, topleftY, Math.abs(w), Math.abs(h));
                    }
                } else {
                    g2.drawRect(topleftX, topleftY, Math.abs(w), Math.abs(h));
                }
                
            }
            
        }
    }
    
   
    // let shape handle its own hit testing
    // (x,y) is the point to test against
    // (x,y) needs to be in same coordinate frame as shape, you could add
    // a panel-to-shape transform as an extra parameter to this function
    // (note this isn't good separation of shape Controller from shape Model)    
    public boolean hittest(double x, double y)
    {   
    	//if (points != null) {

            // TODO Implement

    	//}
    	
    	return false;
    }
}
