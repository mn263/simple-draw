package cs355.lab1.ShapeClasses;

import cs355.lab1.Shape;

/**
 * @author mn263
 *         Date: 9/13/13
 *         Time: 1:00 PM
 */
public class Circle extends Shape {

    public Circle(double radius, double x, double y) {
        super.setCenter(x, y);
        super.setHeight(radius*2);
        super.setWidth(radius*2);
        super.setSelectedShape(ShapeEnum.CIRCLE);
    }

    public void setRadius(double radius) {
        super.setHeight(radius*2);
        super.setWidth(radius*2);
    }

	public double getRadius() {
    	assert(super.getHeight() == super.getWidth());
		return super.getHeight();
	}
}
