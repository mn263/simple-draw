package cs355.lab1.ShapeClasses;

import cs355.lab1.Shape;

/**
 * @author mn263
 *         Date: 9/13/13
 *         Time: 12:59 PM
 */
public class Square extends Shape {
	
    public Square(double left, double top, double size) {
        super.setCenter(left, top);
        super.setHeight(size);
        super.setWidth(size);
        super.setSelectedShape(ShapeEnum.SQUARE);
    }

    public double getSize() {
    	assert(super.getHeight() == super.getWidth());
        return super.getHeight();
    }

    public void setSize(double size) {
        super.setHeight(size);
        super.setWidth(size);
    }
}
