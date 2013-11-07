package cs355.lab1;

import java.awt.*;

/**
 * @author mn263
 *         Date: 9/13/13
 *         Time: 11:49 AM
 */
public abstract class Shape {

    private Color color;
    private Point center;
    private double height;
    private double width;
    private double rotationDegree = 0;
    private ShapeEnum SELECTEDSHAPE;
    public enum ShapeEnum {
        CIRCLE, ELLIPSE, LINE, RECTANGLE, SQUARE, TRIANGLE
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public  ShapeEnum getSelectedShape() {
        return SELECTEDSHAPE;
    }

    public void setSelectedShape(ShapeEnum shape) {
        this.SELECTEDSHAPE = shape;
    }

    public double getHeight() {
    	if((getSelectedShape() == ShapeEnum.TRIANGLE) || (getSelectedShape() == ShapeEnum.LINE)) {
    		return 0.00;
    	} else {
    		return height;
    	}
	}
    
    public void setHeight(double size) {
    	this.height = size;
    }
    
    public void setWidth(double width) {
    	this.width = width;
    }
    
    public double getWidth() {
    	if((getSelectedShape() == ShapeEnum.TRIANGLE) || (getSelectedShape() == ShapeEnum.LINE)) {
    		return 0.00;
    	} else {
    		return width;
    	}
	}
    
	public Point getCenter() {
		return center;
	}

	public void setCenter(double x, double y) {
		this.center = new Point(x, y);
	}

	public double getRotationDegree() {
		return rotationDegree;
	}

	public void setRotationDegree(double rotationDegree) {
		this.rotationDegree = rotationDegree;
	}
	
	public boolean isOval() {
		if((getSelectedShape() == ShapeEnum.ELLIPSE) || (getSelectedShape() == ShapeEnum.CIRCLE)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isBox() {
		if((getSelectedShape() == ShapeEnum.RECTANGLE) || (getSelectedShape() == ShapeEnum.SQUARE)) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isTriangle() {
		if(getSelectedShape() == ShapeEnum.TRIANGLE) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isLine() {
		if(getSelectedShape() == ShapeEnum.LINE) {
			return true;
		} else {
			return false;
		}
	}
}
