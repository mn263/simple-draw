package cs355;


import java.util.Iterator;

public class HouseController {

	public boolean showHouse;
	private WireFrame houseModel = new HouseModel();
	private Point3D position = new Point3D(0, 0, 0);
	private float angle;


	public boolean showHouse() {
		return showHouse;
	}

	public void update() {
		showHouse = !showHouse;
	}

	public void updateKeyboard(int keyPressed) {
		if (keyPressed == 65) { //MOVE LEFT
			position.x -= .1f * (float) Math.sin(Math.toRadians(angle - 90));
			position.z += .1f * (float) Math.cos(Math.toRadians(angle - 90));
		}
		if (keyPressed == 68) { //MOVE RIGHT
			position.x -= .1f * (float) Math.sin(Math.toRadians(angle + 90));
			position.z += .1f * (float) Math.cos(Math.toRadians(angle + 90));
		}
		if (keyPressed == 87) { //MOVE FORWARD
			position.x -= 0.1f * (float) Math.sin(Math.toRadians(angle));
			position.z += .1f * (float) Math.cos(Math.toRadians(angle));
		}
		if (keyPressed == 83) { //MOVE BACKWARD
			position.x += .1f * (float) Math.sin(Math.toRadians(angle));
			position.z -= .1f * (float) Math.cos(Math.toRadians(angle));
		}
		if (keyPressed == 81) { //TURN LEFT
			angle += (-0.5f);
		}
		if (keyPressed == 69) { //TURN RIGHT
			angle += 0.5;
		}
		if (keyPressed == 82) { //MOVE UP
			position.y -= .1f;
		}
		if (keyPressed == 70) { //MOVE DOWN
			position.y += .1f;
		}
	}

	//This method is the one that actually draws to the screen.
	public void render() {
        Iterator<Line3D> line3DIterator = houseModel.getLines();
        while (line3DIterator.hasNext()) {
            Line3D line = line3DIterator.next();
//            glVertex3d(line.start.x, line.start.y, line.start.z);
//            glVertex3d(line.end.x, line.end.y, line.end.z);
        }
	}
}
