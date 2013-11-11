package cs355.modelHelpers;

import cs355.*;
import cs355.lab1.Model;
import cs355.lab1.Point;
import cs355.lab1.Shape;
import cs355.lab1.Shape.ShapeEnum;
import cs355.lab1.ShapeClasses.Line;
import cs355.lab1.ShapeClasses.Triangle;
import cs355.lab1.singletonManager;

public class SelectShape {
	private selectedShapesMethodEnum selectedShapesMethod;

	private enum selectedShapesMethodEnum {
		ROTATE, TOPRIGHT, TOPLEFT, BOTTOMRIGHT, BOTTOMLEFT, NONE, LINESTARTING,
		LINEENDING, TRIANGLEONE, TRIANGLETWO, TRIANGLETHREE
	}

	public void selectedShapeManipulationType(Shape shape, double mouseX, double mouseY) {
		double height = 9000;
		double width = 100;
		boolean missedShapeManipulation = true;
		if (shape.isLine()) {
			missedShapeManipulation = manipulateLine(shape, mouseX, mouseY);
		} else if (shape.isTriangle()) {
			missedShapeManipulation = manipulateTriangle(shape, mouseX, mouseY);
		} else {
			height = shape.getHeight();
			width = shape.getWidth();
		}

		double zoom = singletonManager.inst().getZoomValue();
		Point objectSpacePoint = getObjectSpacePoint(shape, mouseX, mouseY);
		double adjustedX = objectSpacePoint.getX();
		double adjustedY = objectSpacePoint.getY();
		//CHECK TOPLEFT
		if (Math.abs(adjustedX + width / 2) < 9 / zoom && Math.abs(adjustedY + height / 2) < 9 / zoom) {
			selectedShapesMethod = selectedShapesMethodEnum.TOPLEFT;
			missedShapeManipulation = false;
		}
		//CHECK TOPRIGHT goes too far left
		if (Math.abs(adjustedX - width / 2) < 9 / zoom && Math.abs(adjustedY + height / 2) < 9 / zoom) {
			selectedShapesMethod = selectedShapesMethodEnum.TOPRIGHT;
			missedShapeManipulation = false;
		}
		//CHECK BOTTOMLEFT
		if (Math.abs(adjustedX + width / 2) < 9 / zoom && Math.abs(adjustedY - height / 2) < 9 / zoom) {
			selectedShapesMethod = selectedShapesMethodEnum.BOTTOMLEFT;
			missedShapeManipulation = false;
		}
		//CHECK BOTTOMRIGHT
		if (Math.abs(adjustedX - width / 2) < 9 / zoom && Math.abs(adjustedY - height / 2) < 9 / zoom) {
			selectedShapesMethod = selectedShapesMethodEnum.BOTTOMRIGHT;
			missedShapeManipulation = false;
		}
		//CHECK ROTATE
		if (Math.abs(adjustedX) < 9 / zoom && Math.abs(adjustedY + height / 2 + 8 / zoom) < 9 / zoom) {
			selectedShapesMethod = selectedShapesMethodEnum.ROTATE;
			missedShapeManipulation = false;
		}
		if (missedShapeManipulation) {
			singletonManager.inst().setSelectedShape(null);
			selectedShapesMethod = selectedShapesMethodEnum.NONE;
			Model.inst().selectShape(mouseX, mouseY);
			Shape selectedShape = singletonManager.inst().getselectedShape();
			if (selectedShape != null) {
				singletonManager.inst().centerBeforeDrag = selectedShape.getCenter();
			}
		} else {
			singletonManager.inst().setSelectedShape(shape);
		}
		singletonManager.inst().isDragging = true;
	}

	private boolean manipulateLine(Shape shape, double mouseX, double mouseY) {
		Line line = (Line) shape;
		double zoom = singletonManager.inst().getZoomValue();
		if (Math.abs(mouseX - line.getStartingPoint().getX()) < 9 / zoom
				&& Math.abs(mouseY - line.getStartingPoint().getY()) < 9 / zoom) {
			selectedShapesMethod = selectedShapesMethodEnum.LINESTARTING;
			return false;
		} else if (Math.abs(mouseX - line.getEndPoint().getX()) < 9 / zoom
				&& Math.abs(mouseY - line.getEndPoint().getY()) < 9 / zoom) {
			selectedShapesMethod = selectedShapesMethodEnum.LINEENDING;
			return false;
		}
		return true;
	}

	private boolean manipulateTriangle(Shape shape, double mouseX, double mouseY) {
		Triangle triangle = (Triangle) shape;
		if (Math.abs(mouseX - triangle.getOne().getX()) < 9
				&& Math.abs(mouseY - triangle.getOne().getY()) < 9) {
			selectedShapesMethod = selectedShapesMethodEnum.TRIANGLEONE;
			return false;
		} else if (Math.abs(mouseX - triangle.getTwo().getX()) < 9
				&& Math.abs(mouseY - triangle.getTwo().getY()) < 9) {
			selectedShapesMethod = selectedShapesMethodEnum.TRIANGLETWO;
			return false;
		} else if (Math.abs(mouseX - triangle.getThree().getX()) < 9
				&& Math.abs(mouseY - triangle.getThree().getY()) < 9) {
			selectedShapesMethod = selectedShapesMethodEnum.TRIANGLETHREE;
			return false;
		}
		return true;
	}

	//
//MODIFY SELECTED SHAPE
//
	public void modifySelectedShape(double mouseX, double mouseY, double startingPointX, double startingPointY) {
		Shape shape = singletonManager.inst().getselectedShape();
		Point adjustedPoint = getObjectSpacePoint(shape, mouseX, mouseY);

		switch (selectedShapesMethod) {
			case NONE:
				dragShape(shape, mouseX, mouseY, startingPointX, startingPointY);
				break;
			case ROTATE:
				rotateShape(shape, mouseX, mouseY);
				break;
			case TOPLEFT:
				stretchTopLeft(shape, adjustedPoint.getX(), adjustedPoint.getY());
				break;
			case TOPRIGHT:
				stretchTopRight(shape, adjustedPoint.getX(), adjustedPoint.getY());
				break;
			case BOTTOMLEFT:
				stretchBottomLeft(shape, adjustedPoint.getX(), adjustedPoint.getY());
				break;
			case BOTTOMRIGHT:
				stretchBottomRight(shape, adjustedPoint.getX(), adjustedPoint.getY());
				break;
			case LINESTARTING:
				Line line = (Line) shape;
				line.setStartingPoint(mouseX, mouseY);
				break;
			case LINEENDING:
				Line lineEnding = (Line) shape;
				lineEnding.setEndPoint(mouseX, mouseY);
				break;
			case TRIANGLEONE:
				Triangle triangle = (Triangle) shape;
				triangle.setOne(mouseX, mouseY);
				triangle.resetDistancesToCenter(triangle.getOne().getX(), triangle.getOne().getY(),
						triangle.getTwo().getX(), triangle.getTwo().getY(),
						triangle.getThree().getX(), triangle.getThree().getY());
				break;
			case TRIANGLETHREE:
				Triangle triangleThree = (Triangle) shape;
				triangleThree.setThree(mouseX, mouseY);
				triangleThree.resetDistancesToCenter(triangleThree.getOne().getX(), triangleThree.getOne().getY(),
						triangleThree.getTwo().getX(), triangleThree.getTwo().getY(),
						triangleThree.getThree().getX(), triangleThree.getThree().getY());
				break;
			case TRIANGLETWO:
				Triangle triangleTwo = (Triangle) shape;
				triangleTwo.setTwo(mouseX, mouseY);
				triangleTwo.resetDistancesToCenter(triangleTwo.getOne().getX(), triangleTwo.getOne().getY(),
						triangleTwo.getTwo().getX(), triangleTwo.getTwo().getY(),
						triangleTwo.getThree().getX(), triangleTwo.getThree().getY());
				break;
			default:
				break;
		}
		GUIFunctions.refresh();
	}

	private void dragShape(Shape shape, double mouseX, double mouseY, double startingPointX, double startingPointY) {
		if (shape.isLine()) {
			Line line = (Line) shape;
			Point oldStart = singletonManager.inst().centerBeforeDrag;
			double xDiff = line.getStartingPoint().getX() - line.getEndPoint().getX();
			double yDiff = line.getStartingPoint().getY() - line.getEndPoint().getY();
			double xMovement = startingPointX - mouseX;
			double yMovement = startingPointY - mouseY;

			line.setStartingPoint(oldStart.getX() - xMovement, oldStart.getY() - yMovement);
			line.setEndPoint(line.getStartingPoint().getX() - xDiff, line.getStartingPoint().getY() - yDiff);
			shape.setCenter(oldStart.getX() - xMovement, oldStart.getY() - yMovement);
			singletonManager.inst().centerBeforeDrag = shape.getCenter();
		} else if (shape.isTriangle()) {
			Triangle triangle = (Triangle) shape;
			Point oldCenter = singletonManager.inst().centerBeforeDrag;
			Point distanceBetweenClickAndCenter = new Point(oldCenter.getX() - startingPointX, oldCenter.getY() - startingPointY);

			shape.setCenter(mouseX + distanceBetweenClickAndCenter.getX(), mouseY + distanceBetweenClickAndCenter.getY());
			triangle.setOne(shape.getCenter().getX() + triangle.distanceFromOneToCenter.getX(),
					shape.getCenter().getY() + triangle.distanceFromOneToCenter.getY());
			triangle.setTwo(shape.getCenter().getX() + triangle.distanceFromTwoToCenter.getX(),
					shape.getCenter().getY() + triangle.distanceFromTwoToCenter.getY());
			triangle.setThree(shape.getCenter().getX() + triangle.distanceFromThreeToCenter.getX(),
					shape.getCenter().getY() + triangle.distanceFromThreeToCenter.getY());


			//reset distances if necessary
		} else {
			Point oldCenter = singletonManager.inst().centerBeforeDrag;
			shape.setCenter(oldCenter.getX() - startingPointX + mouseX,
					oldCenter.getY() - startingPointY + mouseY);
		}
	}

	private void rotateShape(Shape shape, double currentX, double currentY) {
		double x = currentX - shape.getCenter().getX();
		double y = currentY - shape.getCenter().getY();
		double angle = Math.toDegrees(Math.atan2(y, x));
		shape.setRotationDegree(angle + 90);
	}

	private void stretchTopLeft(Shape shape, double adjustedX, double adjustedY) {
		double newHeight = (-1 * adjustedY) + shape.getHeight() / 2;
		double newWidth = (-1 * adjustedX) + shape.getWidth() / 2;
		if (isCirlceOrSquare(shape)) {
			newWidth = newHeight = Math.min(newHeight, newWidth);
		}

		double changeInY;
		double changeInYFromY;
		double changeInXFromY;
		if (newHeight < 1) {
			changeInY = (shape.getHeight() - 1) / 2;
			changeInYFromY = -1 * changeInY * Math.cos(Math.toRadians(shape.getRotationDegree()));
			changeInXFromY = -1 * changeInY * Math.sin(Math.toRadians(shape.getRotationDegree()));
			newHeight = 1;
		} else {
			changeInY = (newHeight - shape.getHeight()) / 2;
			changeInYFromY = changeInY * Math.cos(Math.toRadians(shape.getRotationDegree()));
			changeInXFromY = changeInY * Math.sin(Math.toRadians(shape.getRotationDegree()));
		}

		double changeInX;
		double changeInYFromX;
		double changeInXFromX;
		if (newWidth < 1) {
			changeInX = (shape.getWidth() - 1) / 2;
			changeInYFromX = -1 * changeInX * Math.sin(Math.toRadians(shape.getRotationDegree()));
			changeInXFromX = -1 * changeInX * Math.cos(Math.toRadians(shape.getRotationDegree()));
			newWidth = 1;
		} else {
			changeInX = (newWidth - shape.getWidth()) / 2;
			changeInYFromX = changeInX * Math.sin(Math.toRadians(shape.getRotationDegree()));
			changeInXFromX = changeInX * Math.cos(Math.toRadians(shape.getRotationDegree()));
		}
		Point newCenter = new Point(shape.getCenter().getX() - changeInXFromX + changeInXFromY, shape.getCenter().getY() - changeInYFromX - changeInYFromY);
		finalizeChange(shape, newCenter, newHeight, newWidth);

	}

	private void stretchTopRight(Shape shape, double adjustedX, double adjustedY) {
		double newHeight = (-1 * adjustedY) + shape.getHeight() / 2;
		double newWidth = adjustedX + shape.getWidth() / 2;
		if (isCirlceOrSquare(shape)) {
			newWidth = newHeight = Math.min(newHeight, newWidth);
		}

		double changeInY;
		double changeInYFromY;
		double changeInXFromY;
		if (newHeight < 1) {
			changeInY = (shape.getHeight() - 1) / 2;
			changeInYFromY = -1 * changeInY * Math.cos(Math.toRadians(shape.getRotationDegree()));
			changeInXFromY = -1 * changeInY * Math.sin(Math.toRadians(shape.getRotationDegree()));
			newHeight = 1;
		} else {
			changeInY = (newHeight - shape.getHeight()) / 2;
			changeInYFromY = changeInY * Math.cos(Math.toRadians(shape.getRotationDegree()));
			changeInXFromY = changeInY * Math.sin(Math.toRadians(shape.getRotationDegree()));
		}

		double changeInX;
		double changeInYFromX;
		double changeInXFromX;
		if (newWidth < 1) {
			changeInX = (shape.getWidth() - 1) / 2;
			changeInYFromX = -1 * changeInX * Math.sin(Math.toRadians(shape.getRotationDegree()));
			changeInXFromX = -1 * changeInX * Math.cos(Math.toRadians(shape.getRotationDegree()));
			newWidth = 1;
		} else {
			changeInX = (newWidth - shape.getWidth()) / 2;
			changeInYFromX = changeInX * Math.sin(Math.toRadians(shape.getRotationDegree()));
			changeInXFromX = changeInX * Math.cos(Math.toRadians(shape.getRotationDegree()));
		}
		Point newCenter = new Point(shape.getCenter().getX() + changeInXFromX + changeInXFromY, shape.getCenter().getY() + changeInYFromX - changeInYFromY);
		finalizeChange(shape, newCenter, newHeight, newWidth);

	}

	private void stretchBottomLeft(Shape shape, double adjustedX, double adjustedY) {
		double newHeight = adjustedY + shape.getHeight() / 2;
		double newWidth = (-1 * adjustedX) + shape.getWidth() / 2;
		if (isCirlceOrSquare(shape)) {
			newWidth = newHeight = Math.min(newHeight, newWidth);
		}

		double changeInY;
		double changeInYFromY;
		double changeInXFromY;
		if (newHeight < 1) {
			changeInY = (shape.getHeight() - 1) / 2;
			changeInYFromY = -1 * (changeInY) * Math.cos(Math.toRadians(shape.getRotationDegree()));
			changeInXFromY = -1 * changeInY * Math.sin(Math.toRadians(shape.getRotationDegree()));
			newHeight = 1;
		} else {
			changeInY = (newHeight - shape.getHeight()) / 2;
			changeInYFromY = changeInY * Math.cos(Math.toRadians(shape.getRotationDegree()));
			changeInXFromY = changeInY * Math.sin(Math.toRadians(shape.getRotationDegree()));
		}

		double changeInX;
		double changeInYFromX;
		double changeInXFromX;
		if (newWidth < 1) {
			changeInX = (shape.getWidth() - 1) / 2;
			changeInYFromX = -1 * changeInX * Math.sin(Math.toRadians(shape.getRotationDegree()));
			changeInXFromX = -1 * (changeInX) * Math.cos(Math.toRadians(shape.getRotationDegree()));
			newWidth = 1;
		} else {
			changeInX = (newWidth - shape.getWidth()) / 2;
			changeInYFromX = changeInX * Math.sin(Math.toRadians(shape.getRotationDegree()));
			changeInXFromX = changeInX * Math.cos(Math.toRadians(shape.getRotationDegree()));
		}
		Point newCenter = new Point(shape.getCenter().getX() - changeInXFromX - changeInXFromY, shape.getCenter().getY() - changeInYFromX + changeInYFromY);
		finalizeChange(shape, newCenter, newHeight, newWidth);
	}

	private void stretchBottomRight(Shape shape, double adjustedX, double adjustedY) {
		double newHeight = adjustedY + shape.getHeight() / 2;
		double newWidth = adjustedX + shape.getWidth() / 2;
		if (isCirlceOrSquare(shape)) {
			newWidth = newHeight = Math.min(newHeight, newWidth);
		}

		double changeInY;
		double changeInYFromY;
		double changeInXFromY;
		if (newHeight < 1) {
			changeInY = (shape.getHeight() - 1) / 2;
			changeInYFromY = -1 * (changeInY) * Math.cos(Math.toRadians(shape.getRotationDegree()));
			changeInXFromY = -1 * changeInY * Math.sin(Math.toRadians(shape.getRotationDegree()));
			newHeight = 1;
		} else {
			changeInY = (newHeight - shape.getHeight()) / 2;
			changeInYFromY = changeInY * Math.cos(Math.toRadians(shape.getRotationDegree()));
			changeInXFromY = changeInY * Math.sin(Math.toRadians(shape.getRotationDegree()));
		}

		double changeInX;
		double changeInYFromX;
		double changeInXFromX;
		if (newWidth < 1) {
			changeInX = (shape.getWidth() - 1) / 2;
			changeInYFromX = -1 * changeInX * Math.sin(Math.toRadians(shape.getRotationDegree()));
			changeInXFromX = -1 * changeInX * Math.cos(Math.toRadians(shape.getRotationDegree()));
			newWidth = 1;
		} else {
			changeInX = (newWidth - shape.getWidth()) / 2;
			changeInYFromX = changeInX * Math.sin(Math.toRadians(shape.getRotationDegree()));
			changeInXFromX = changeInX * Math.cos(Math.toRadians(shape.getRotationDegree()));
		}
		Point newCenter = new Point(shape.getCenter().getX() + changeInXFromX - changeInXFromY, shape.getCenter().getY() + changeInYFromX + changeInYFromY);
		finalizeChange(shape, newCenter, newHeight, newWidth);
	}

	//
	// CHECK
	//
	public boolean checkIfInLine(Line line, double mouseX, double mouseY) {
		double startX = line.getStartingPoint().getX();
		double startY = line.getStartingPoint().getY();
		Point normal = getVectorNormal(line.getStartingPoint().getX(), line.getStartingPoint().getY(),
				line.getEndPoint().getX(), line.getEndPoint().getY());
		double distFromOrigin = startX * normal.getY() + startY * normal.getX();
		double distFromXYtoLine = Math.abs(mouseX * normal.getY() + mouseY * normal.getX() - distFromOrigin);
		return distFromXYtoLine <= 4;
	}

	public boolean checkIfInBoxShape(Shape shape, double mouseX, double mouseY) {
		double height = shape.getHeight();
		double width = shape.getWidth();
		Point objectSpacePoint = getObjectSpacePoint(shape, mouseX, mouseY);
		return !((Math.abs(objectSpacePoint.getX()) > width / 2) ||
				(Math.abs(objectSpacePoint.getY()) > height / 2));
	}

	public boolean checkIfInOvalShape(Shape shape, double mouseX, double mouseY) {
		double height = shape.getHeight();
		double width = shape.getWidth();
		Point objectSpacePoint = getObjectSpacePoint(shape, mouseX, mouseY);
		double x = objectSpacePoint.getX();
		double y = objectSpacePoint.getY();
		double distanceX = 4 * x * x / (width * height);
		double distanceY = 4 * y * y / (width * height);
		return distanceX + distanceY <= 1;
	}

	public boolean checkIfInTriangle(Triangle triangle, double mouseX, double mouseY) {
		double oneX = triangle.getOne().getX();
		double oneY = triangle.getOne().getY();
		double twoX = triangle.getTwo().getX();
		double twoY = triangle.getTwo().getY();
		double threeX = triangle.getThree().getX();
		double threeY = triangle.getThree().getY();

		double alpha = ((twoY - threeY) * (mouseX - threeX) + (threeX - twoX) * (mouseY - threeY)) /
				((twoY - threeY) * (oneX - threeX) + (threeX - twoX) * (oneY - threeY));
		double beta = ((threeY - oneY) * (mouseX - threeX) + (oneX - threeX) * (mouseY - threeY)) /
				((twoY - threeY) * (oneX - threeX) + (threeX - twoX) * (oneY - threeY));
		double gamma = 1.0f - alpha - beta;
		return alpha > 0 && beta > 0 && gamma > 0;
	}

	//
	// HELPER METHODS
	//
	private void finalizeChange(Shape shape, Point newCenter, double newHeight, double newWidth) {
		shape.setCenter(newCenter.getX(), newCenter.getY());
		shape.setCenter(newCenter.getX(), newCenter.getY());
		shape.setHeight(newHeight);
		shape.setWidth(newWidth);
	}

	private boolean isCirlceOrSquare(Shape shape) {
		return shape.getSelectedShape() == ShapeEnum.CIRCLE || shape.getSelectedShape() == ShapeEnum.SQUARE;
	}

	private Point getVectorNormal(double x1, double y1, double x2, double y2) {
		double normalY = (-(y2 - y1)) / (Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
		double normalX = (x2 - x1) / (Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
		return new Point(normalX, normalY);
	}

	private Point getObjectSpacePoint(Shape shape, double x, double y) {
		double adjustedX = x - shape.getCenter().getX();
		double adjustedY = y - shape.getCenter().getY();
		double translatedX = (adjustedX * Math.cos(Math.toRadians(shape.getRotationDegree()))
				+ adjustedY * Math.sin(Math.toRadians(shape.getRotationDegree())));
		double translatedY = (-adjustedX * Math.sin(Math.toRadians(shape.getRotationDegree()))
				+ adjustedY * Math.cos(Math.toRadians(shape.getRotationDegree())));
		return new Point(translatedX, translatedY);
	}
}
