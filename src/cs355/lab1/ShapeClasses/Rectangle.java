package cs355.lab1.ShapeClasses;

import cs355.lab1.Shape;

/**
 * @author mn263
 *         Date: 9/13/13
 *         Time: 12:56 PM
 */
public class Rectangle extends Shape {

    public Rectangle(double height, double width, double left, double top) {
        super.setHeight(height);
        super.setWidth(width);
        super.setCenter(left, top);
        super.setSelectedShape(ShapeEnum.RECTANGLE);
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

    public void setWidth(int width) {
        super.setWidth(width);
    }
}
