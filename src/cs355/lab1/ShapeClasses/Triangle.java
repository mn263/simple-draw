package cs355.lab1.ShapeClasses;

import cs355.lab1.Point;
import cs355.lab1.Shape;

/**
 * @author mn263
 *         Date: 9/13/13
 *         Time: 1:00 PM
 */
public class Triangle extends Shape {
    
	public static Point cornerOne = null;
    public static Point cornerTwo = null;
    public Point distanceFromOneToCenter = null;
    public Point distanceFromTwoToCenter = null;
    public Point distanceFromThreeToCenter = null;
    private Point one;
    private Point two;
    private Point three;

    public Triangle(double oneX, double oneY, double twoX, double twoY, double threeX, double threeY) {
        this.one = new Point(oneX, oneY);
        this.two = new Point(twoX, twoY);
        this.three = new Point(threeX, threeY);
        super.setCenter((oneX+twoX+threeX)/3, (oneY+twoY+threeY)/3);
        super.setSelectedShape(ShapeEnum.TRIANGLE);
        distanceFromOneToCenter = new Point(oneX - (oneX+twoX+threeX)/3, oneY - (oneY+twoY+threeY)/3);
        distanceFromTwoToCenter = new Point(twoX - (oneX+twoX+threeX)/3, twoY - (oneY+twoY+threeY)/3);
        distanceFromThreeToCenter = new Point(threeX - (oneX+twoX+threeX)/3, threeY - (oneY+twoY+threeY)/3);
    }

    public void resetDistancesToCenter(double oneX, double oneY, double twoX, double twoY, double threeX, double threeY) {
        distanceFromOneToCenter = new Point(oneX - (oneX+twoX+threeX)/3, oneY - (oneY+twoY+threeY)/3);
        distanceFromTwoToCenter = new Point(twoX - (oneX+twoX+threeX)/3, twoY - (oneY+twoY+threeY)/3);
        distanceFromThreeToCenter = new Point(threeX - (oneX+twoX+threeX)/3, threeY - (oneY+twoY+threeY)/3);
        super.setCenter((oneX+twoX+threeX)/3, (oneY+twoY+threeY)/3);
    }
    
    public Point getOne() {
        return one;
    }

    public void setOne(double x, double y) {
        this.one = new Point(x, y);
    }

    public Point getTwo() {
        return two;
    }

    public void setTwo(double x, double y) {
        this.two = new Point(x, y);
    }

    public Point getThree() {
        return three;
    }

    public void setThree(double x, double y) {
        this.three = new Point(x, y);
    }
}
