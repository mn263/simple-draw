package cs355;


import cs355.lab1.Point;
import cs355.lab1.ShapeClasses.Line;

import java.util.ArrayList;
import java.util.Iterator;

public class HouseController {

	private MatrixUtils matrixUtils = new MatrixUtils();
	private boolean showHouse;
	private WireFrame houseModel = new HouseModel();
	private Point3D cameraPosition = new Point3D(0, 0, 20);
	private float angle;


	public boolean showHouse() {
		return showHouse;
	}

	public void update() {
		showHouse = !showHouse;
	}

	public void updateKeyboard(int keyPressed) {
		if (!showHouse()) {
			return;
		}
		switch (keyPressed) {
			case 65: //MOVE LEFT
				cameraPosition.x -= .1f * (float) Math.sin(angle - 3.14159265359/2);
				cameraPosition.z += .1f * (float) Math.cos(angle - 3.14159265359/2);
				break;
			case 68: //MOVE RIGHT
				cameraPosition.x -= .1f * (float) Math.sin(angle + 3.14159265359/2);
				cameraPosition.z += .1f * (float) Math.cos(angle + 3.14159265359/2);
				break;
			case 87: //MOVE FORWARD
				cameraPosition.x += .1f * (float) Math.sin(angle);
				cameraPosition.z -= .1f * (float) Math.cos(angle);
				break;
			case 83: //MOVE BACKWARD
				cameraPosition.x -= .1f * (float) Math.sin(angle);
				cameraPosition.z += .1f * (float) Math.cos(angle);
				break;
			case 81: //TURN LEFT
				angle += (-0.01f);
				break;
			case 69: //TURN RIGHT
				angle += 0.01;
				break;
			case 82: //MOVE UP
				cameraPosition.y += .1f;
				break;
			case 70: //MOVE DOWN
				cameraPosition.y -= .1f;
				break;
			case 72: //GO HOME
				cameraPosition = new Point3D(0, 0, 20);
				angle = 0;
				break;
		}
	}

	//This method is the one that actually draws to the screen.
	public ArrayList<Line> getHouse() {
		ArrayList<Line> houseLines = new ArrayList<>();
		Iterator<Line3D> line3DIterator = houseModel.getLines();
		while (line3DIterator.hasNext()) {
			Line3D line = line3DIterator.next();
			Line lineToAdd = convertLineTo2D(line);
			if (lineToAdd != null) {
				houseLines.add(convertLineTo2D(line));
			}
		}
		return houseLines;
	}

	private Line convertLineTo2D(Line3D line) {
		//create rotation Matrix
		Matrix4D rotationMatrix = matrixUtils.createRotationMatrix(angle);
		//create translation Matrix
		Matrix4D translationMatrix = matrixUtils.createTranslationMatrix(cameraPosition);

		//Build a single matrix that converts from world to camera coordinates
		Matrix4D worldToCamera = matrixUtils.multiply4x4Matrices(rotationMatrix, translationMatrix);
		//Apply worldToCamera matrix to the 3D homogeneous world-space point to get a 3D homogeneous camera-space point
		ArrayList<Double> startCameraCoords = matrixUtils.multiplyRowWithMatrix(worldToCamera, line.start.x, line.start.y, line.start.z);
		ArrayList<Double> endCameraCoords = matrixUtils.multiplyRowWithMatrix(worldToCamera, line.end.x, line.end.y, line.end.z);

		//return if the line has a point that goes behind the camera
		if ((startCameraCoords.get(2) > -1) || (endCameraCoords.get(2) > -1)) {
			return null;
		}

		double z_CLOSE = 1;
		double z_FAR = 200;
		//Build a clip matrix as discussed in class
		Matrix4D clipMatrix = matrixUtils.createClipMatrix(1, 1, z_CLOSE, z_FAR);
		//Apply  clip matrix to the 3D camera-space points
		ArrayList<Double> startClipSpaceCoords = matrixUtils.multiplyRowWithMatrix(clipMatrix, startCameraCoords.get(0), startCameraCoords.get(1), startCameraCoords.get(2));
		ArrayList<Double> endClipSpaceCoords = matrixUtils.multiplyRowWithMatrix(clipMatrix, endCameraCoords.get(0), endCameraCoords.get(1), endCameraCoords.get(2));

		//Apply clipping test: Reject line if both points are outside the view frustum
		double wStart = Math.abs(startClipSpaceCoords.get(3));
		double wEnd = Math.abs(endClipSpaceCoords.get(3));
		if ((startClipSpaceCoords.get(0) > wStart || startClipSpaceCoords.get(1) > wStart) && (endClipSpaceCoords.get(0) > wEnd || endClipSpaceCoords.get(1) > wEnd)) {
			return null;
		}

		//Apply perspective by normalizing the clip-space coordinates
		Point startCanonicalCoords = new Point(startClipSpaceCoords.get(0) / startClipSpaceCoords.get(3), -startClipSpaceCoords.get(1) / startClipSpaceCoords.get(3));
		Point endCanonicalCoords = new Point(endClipSpaceCoords.get(0) / endClipSpaceCoords.get(3), -endClipSpaceCoords.get(1) / endClipSpaceCoords.get(3));

		return new Line((startCanonicalCoords.getX() + 1) * 1048, (-startCanonicalCoords.getY() + 1) * 1048, (endCanonicalCoords.getX() + 1) * 1048, (-endCanonicalCoords.getY() + 1) * 1048);
	}
}
