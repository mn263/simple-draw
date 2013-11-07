package modelHelpers;

import cs355.lab1.Point;
import cs355.lab1.Shape;
import cs355.lab1.singletonManager;
import cs355.lab1.ShapeClasses.Circle;
import cs355.lab1.ShapeClasses.Ellipse;
import cs355.lab1.ShapeClasses.Line;
import cs355.lab1.ShapeClasses.Rectangle;
import cs355.lab1.ShapeClasses.Square;
import cs355.lab1.ShapeClasses.Triangle;

public class AddShape {
	
    public Shape addCircle(int radius, double intX, double intY) {
    	return setShapeColor(new Circle(radius, intX, intY));
    }
    public Shape addEllipse(int height, int width, double x, double y) {
    	return setShapeColor(new Ellipse(height, width, x, y));
    }
    public Shape addLine(double startX, double startY, double endX, double endY) {
    	return setShapeColor(new Line(startX, startY, endX, endY));
    }
    public Shape addRectangle(int height, int width, double left, double top) {
        return setShapeColor(new Rectangle(height, width, left, top));
    }
    public Shape addSquare(double left, double top, int size) {
        return setShapeColor(new Square(left, top, size));
    }
    public Shape addTriangle(double oneX, double oneY, double twoX, double twoY, double threeX, double threeY) {
        return setShapeColor(new Triangle(oneX, oneY, twoX, twoY, threeX, threeY));
    }

    public Shape checkTriangleStatus(double x, double y) {
        if (Triangle.cornerOne == null) {
            Triangle.cornerOne = new Point(x, y);
        } else if (Triangle.cornerTwo == null) {
            Triangle.cornerTwo = new Point(x, y);
        } else {
            Shape triangle = addTriangle(Triangle.cornerOne.getX(), Triangle.cornerOne.getY(),
                    Triangle.cornerTwo.getX(), Triangle.cornerTwo.getY(), x, y);
            Triangle.cornerOne = null;
            Triangle.cornerTwo = null;
            return triangle;
        }
		return null;
    }
    
    public Shape setShapeColor(Shape shape) {
    	shape.setColor(singletonManager.inst().getSelectedColor());
    	return shape;
    }
}
