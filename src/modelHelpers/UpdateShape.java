package modelHelpers;

import cs355.lab1.Shape;
import cs355.lab1.ShapeClasses.*;

public class UpdateShape {

	public Shape updateCircle(Circle circle, int newX, int newY, int startX, int startY) {
		int xDiff = Math.abs(startX - newX) / 2;
		int radius = Math.abs(startY - newY) / 2;
		if (xDiff < radius) {
			radius = xDiff;
		}
		if (newY < startY && newX < startX) {
			circle.setCenter(startX - radius, startY - radius);
		} else if (newY > startY && newX < startX) {
			circle.setCenter(startX - radius, startY + radius);
		} else if (newY < startY && newX > startX) {
			circle.setCenter(startX + radius, startY - radius);
		} else if (newY > startY && newX > startX) {
			circle.setCenter(startX + radius, startY + radius);
		}
		circle.setRadius(radius);
		return circle;
	}

	public Shape updateEllipse(Ellipse ellipse, int newX, int newY, int startX, int startY) {
		double height = Math.abs(newY - startY);
		double width = Math.abs(newX - startX);
		if (newY > startY) {
			newY = startY;
		}
		if (newX > startX) {
			newX = startX;
		}
		ellipse.setCenter(newX + width / 2, newY + height / 2);
		ellipse.setHeight((int) height);
		ellipse.setWidth((int) width);
		return ellipse;
	}

	public Shape updateRectangle(Rectangle rectangle, int newX, int newY, int startX, int startY) {
		int height = Math.abs(newY - startY);
		int width = Math.abs(newX - startX);
		if (newY > startY) {
			newY = startY;
		}
		if (newX > startX) {
			newX = startX;
		}
		rectangle.setHeight(height);
		rectangle.setCenter(newX + width / 2, newY + height / 2);
		rectangle.setWidth(width);
		return rectangle;
	}

	public Shape updateSquare(Square square, int newX, int newY, int startX, int startY) {
		int xDiff = Math.abs(startX - newX);
		double size = Math.abs(startY - newY);
		if (xDiff < size) {
			size = xDiff;
		}
		if (newY < startY && newX < startX) {
			square.setCenter(startX - size / 2, startY - size / 2);
		} else if (newY > startY && newX < startX) {
			square.setCenter(startX - size / 2, startY + size / 2);
		} else if (newY < startY && newX > startX) {
			square.setCenter(startX + size / 2, startY - size / 2);
		} else if (newY > startY && newX > startX) {
			square.setCenter(startX + size / 2, startY + size / 2);
		}
		square.setSize(size);
		return square;
	}

	public Shape updateLine(Line line, int endX, int endY) {
		line.setCenter((int) (line.getStartingPoint().getX() + endX) / 2, (int) (line.getStartingPoint().getY() + endY) / 2);
		line.setEndPoint(endX, endY);
		return line;
	}
}
