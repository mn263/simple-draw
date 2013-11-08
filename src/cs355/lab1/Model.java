package cs355.lab1;

import cs355.GUIFunctions;
import cs355.lab1.Controller.ShapeEnum;
import cs355.lab1.ShapeClasses.*;
import modelHelpers.AddShape;
import modelHelpers.SelectShape;
import modelHelpers.UpdateShape;

import java.util.Stack;

/**
 * @author mn263
 *         Date: 9/13/13
 *         Time: 8:03 PM
 */
public class Model {

	public Stack<Shape> shapeStack = new Stack<Shape>();

	private static Model instance;
	private SelectShape selectShapeClass = new SelectShape();
	private UpdateShape updateShapeClass = new UpdateShape();
	private AddShape addShape = new AddShape();

	public static Model inst() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}

	//
//  ADD SHAPES
//
	public void addShape(ShapeEnum operationSelected, double x, double y) {
		Shape shape = null;
		switch (operationSelected) {
			case CIRCLE:
				shape = addShape.addCircle(0, x, y);
				break;
			case ELLIPSE:
				shape = addShape.addEllipse(0, 0, x, y);
				break;
			case LINE:
				shape = addShape.addLine(x, y, x, y);
				break;
			case RECTANGLE:
				shape = addShape.addRectangle(0, 0, x, y);
				break;
			case SQUARE:
				shape = addShape.addSquare(x, y, 0);
				break;
			case TRIANGLE:
				shape = addShape.checkTriangleStatus(x, y);
				break;
			default:
				break;
		}
		if (shape != null) {
			shapeStack.push(shape);
		}
	}

	//
//    UPDATE SHAPES
//
	public void updateShape(ShapeEnum operationSelected, double mouseX, double mouseY, int x, int y) {
		switch (operationSelected) {
			case CIRCLE:
				shapeStack.push(updateShapeClass.updateCircle((Circle) shapeStack.pop(), (int) mouseX, (int) mouseY, x, y));
				break;
			case ELLIPSE:
				shapeStack.push(updateShapeClass.updateEllipse((Ellipse) shapeStack.pop(), (int) mouseX, (int) mouseY, x, y));
				break;
			case LINE:
				shapeStack.push(updateShapeClass.updateLine((Line) shapeStack.pop(), (int) mouseX, (int) mouseY));
				break;
			case RECTANGLE:
				shapeStack.push(updateShapeClass.updateRectangle((Rectangle) shapeStack.pop(), (int) mouseX, (int) mouseY, x, y));
				break;
			case SQUARE:
				shapeStack.push(updateShapeClass.updateSquare((Square) shapeStack.pop(), (int) mouseX, (int) mouseY, x, y));
				break;
			default:
				break;
		}
		GUIFunctions.refresh();
	}

	public void notTriangle() {
		Triangle.cornerOne = null;
		Triangle.cornerTwo = null;
	}

	//
//  SELECT SHAPE
//
	public void selectShape(double mouseX, double mouseY) {
		boolean pointInLine = false;
		//If the shape is already selected then modify it
		if (singletonManager.inst().getselectedShape() != null) {
			manipulateSelectedShape(mouseX, mouseY);
			return;
		}
		//If a shape has not been selected then iterate through to see if you clicked on one
		for (int index = shapeStack.size() - 1; index >= 0; index--) {
			Shape shape = shapeStack.get(index);

			if (shape.isBox()) {
				pointInLine = getSelectShapeClass().checkIfInBoxShape(shape, mouseX, mouseY);
			} else if (shape.isOval()) {
				pointInLine = getSelectShapeClass().checkIfInOvalShape(shape, mouseX, mouseY);
			} else if (shape.isTriangle()) {
				pointInLine = getSelectShapeClass().checkIfInTriangle((Triangle) shape, mouseX, mouseY);
			} else if (shape.isLine()) {
				pointInLine = getSelectShapeClass().checkIfInLine((Line) shape, mouseX, mouseY);
			}
			if (pointInLine) {
				shapeStack = moveShapeToFront(shape, shapeStack);
				singletonManager.inst().setSelectedShape(shape);
				return;
			}
		}
	}

	private void manipulateSelectedShape(double mouseX, double mouseY) {
		Shape shape = singletonManager.inst().getselectedShape();
		getSelectShapeClass().selectedShapeManipulationType(shape, mouseX, mouseY);
	}

	private Stack<Shape> moveShapeToFront(Shape selectedShape, Stack<Shape> shapeStack) {
		Stack<Shape> copyStack = new Stack<Shape>();
		for (Shape shape : shapeStack) {
			if (shape != selectedShape) {
				copyStack.push(shape);
			}
		}
		copyStack.push(selectedShape);
		return copyStack;
	}

	public SelectShape getSelectShapeClass() {
		return selectShapeClass;
	}
}
