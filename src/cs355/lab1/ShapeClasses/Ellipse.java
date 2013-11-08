package cs355.lab1.ShapeClasses;

import cs355.lab1.Shape;

/**
 * @author mn263
 *         Date: 9/13/13
 *         Time: 12:59 PM
 */
public class Ellipse extends Shape {

	public Ellipse(double height, double width, double x, double y) {
		super.setCenter(x, y);
		super.setHeight(height);
		super.setWidth(width);
		super.setSelectedShape(ShapeEnum.ELLIPSE);
	}

	public double getHeight() {
		return super.getHeight();
	}

	public void setHeight(double height) {
		super.setHeight(height);
	}

	public double getWidth() {
		return super.getWidth();
	}

	public void setWidth(double width) {
		super.setWidth(width);
	}
}
