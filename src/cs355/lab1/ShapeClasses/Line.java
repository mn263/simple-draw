package cs355.lab1.ShapeClasses;

import cs355.lab1.Point;
import cs355.lab1.Shape;

/**
 * @author mn263
 *         Date: 9/13/13
 *         Time: 12:56 PM
 */
public class Line extends Shape {

	private Point endPoint;
	private Point startingPoint;

	public Line(double startX, double startY, double endX, double endY) {
		this.endPoint = new Point(endX, endY);
		this.startingPoint = new Point(startX, startY);
		super.setCenter(startY, startY);
		super.setSelectedShape(ShapeEnum.LINE);
	}

	public Point getStartingPoint() {
		return startingPoint;
	}

	public void setStartingPoint(double x, double y) {
		this.startingPoint = new Point(x, y);
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(double x, double y) {
		this.endPoint = new Point(x, y);
	}
}
