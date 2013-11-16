package cs355.lab1;

import cs355.*;

import java.awt.*;

public class singletonManager {
	private static singletonManager instance;

	protected singletonManager() {
	}

	public static singletonManager inst() {
		if (instance == null) {
			instance = new singletonManager();
		}
		return instance;
	}

	public Point centerBeforeDrag;
	private Color selectedColor;
	public boolean isDragging;
	private static Shape selectedShape;
	private Point topLeftOfView = new Point(0, 0);

	public final double TWENTYFIVEPERCENT = .25;
	public final double FIFTYPERCENT = .50;
	public final double ONEHUNDREDPERCENT = 1;
	public final double TWOHUNDREDPERCENT = 2;
	public final double FOURHUNDREDPERCENT = 4;
	private double zoomValue = ONEHUNDREDPERCENT;
//    public int maxScrollBarValue;


	public void setSelectedColor(Color c) {
		GUIFunctions.changeSelectedColor(c);
		this.selectedColor = c;
	}

	public Color getSelectedColor() {
		return this.selectedColor;
	}

	public Shape getselectedShape() {
		return selectedShape;
	}

	public void setSelectedShape(Shape shape) {
		if (shape != null) {
			setSelectedColor(shape.getColor());
			centerBeforeDrag = shape.getCenter();
			if (shape.getColor() == null) {
				shape.setColor(new Color(255, 255, 255));
			}
		}
		selectedShape = shape;
	}

	public void setZoomValue(double zoomValue) {
		this.zoomValue = zoomValue;
	}

	public double getZoomValue() {
		return this.zoomValue;
	}

	public void setTopOfView(double newMiddle) {
		topLeftOfView.setY(newMiddle);
	}

	public void setLeftSideOfView(double newMiddle) {
		topLeftOfView.setX(newMiddle);
	}

	public double getTopOfView() {
		return topLeftOfView.getY();
	}

	public double getLeftSideOfView() {
		return topLeftOfView.getX();
	}
}
