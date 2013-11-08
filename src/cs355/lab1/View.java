package cs355.lab1;

import cs355.ViewRefresher;
import cs355.lab1.Shape.ShapeEnum;
import cs355.lab1.ShapeClasses.Line;
import cs355.lab1.ShapeClasses.Triangle;
import viewHelpers.ShapeDraw;
import viewHelpers.ShapeHighlight;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * @author mn263
 *         Date: 9/13/13
 *         Time: 12:18 PM
 */
public class View implements ViewRefresher {

	private ShapeDraw shapeDraw = new ShapeDraw();
	private ShapeHighlight shapeHighlight = new ShapeHighlight();

	private static View instance;

	static View inst() {
		if (instance == null) {
			instance = new View();
		}
		return instance;
	}

	@Override
	public void refreshView(Graphics2D g2d) {
		for (cs355.lab1.Shape shape : Model.inst().shapeStack) {
			g2d.setColor(shape.getColor());
			drawShape(shape, g2d);
		}
		if (singletonManager.inst().getselectedShape() != null) {
			highlightShape(g2d);
		}
	}

	private void drawShape(Shape shape, Graphics2D g2d) {
		AffineTransform saveTransform = g2d.getTransform();
		if (shape.isOval()) {
			shapeDraw.drawOval(shape, g2d);
		} else if (shape.isBox()) {
			shapeDraw.drawBox(shape, g2d);
		} else if (shape.isTriangle()) {
			shapeDraw.drawTriangle((Triangle) shape, g2d);
		} else if (shape.isLine()) {
			shapeDraw.drawLine((Line) shape, g2d);
		}
		g2d.setTransform(saveTransform);
	}

	private void highlightShape(Graphics2D g2d) {
		AffineTransform saveTransform = g2d.getTransform();
		Shape selectedShape = singletonManager.inst().getselectedShape();
		if (selectedShape != null) {
			Color oppositeColor = getOppositeColor(selectedShape.getColor());
			if (selectedShape.isBox() || selectedShape.isOval()) {
				shapeHighlight.highlightShape(selectedShape, oppositeColor, g2d);
			} else if (selectedShape.getSelectedShape() == ShapeEnum.TRIANGLE) {
				shapeHighlight.highlightTriangle(selectedShape, oppositeColor, g2d);
			} else if (selectedShape.getSelectedShape() == ShapeEnum.LINE) {
				shapeHighlight.highlightLine(selectedShape, oppositeColor, g2d);
			}
			g2d.setTransform(saveTransform);
		}
	}

	private Color getOppositeColor(Color currentColor) {
		if (currentColor == null) {
			currentColor = new Color(255, 255, 255);
		}
		int red = 255 - currentColor.getRed();
		int green = 255 - currentColor.getGreen();
		int blue = 255 - currentColor.getBlue();
		return new Color(red, green, blue);
	}
}

