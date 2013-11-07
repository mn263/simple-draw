package viewHelpers;

import java.awt.*;
import java.awt.geom.AffineTransform;

import cs355.lab1.Shape;
import cs355.lab1.ShapeClasses.Line;
import cs355.lab1.ShapeClasses.Triangle;
import cs355.lab1.singletonManager;

public class ShapeHighlight {

	public void highlightShape(Shape selectedShape, Color oppositeColor, Graphics2D g2d) {

        double height = selectedShape.getHeight();
		double width = selectedShape.getWidth();
        int size = getHighlightSize();
        int halfSize = size/2;
        AffineTransform worldSpaceToViewSpace = getAffineTransformation(selectedShape);
        g2d.setTransform(worldSpaceToViewSpace);
        g2d.setColor(oppositeColor);
        double zoom = singletonManager.inst().getZoomValue();
        //OUTLINE
        g2d.setStroke(new BasicStroke((float) ((float) 1/zoom)));
		g2d.drawRect((int) -width/2, (int) -height/2, (int) width, (int) height);
		//TOPLEFT
		g2d.fillOval((int) -width/2 - halfSize, (int) -height/2 -halfSize, size, size);
		//TOPRIGHT
		g2d.fillOval( (int) width/2 - halfSize,(int) -height/2 - halfSize, size, size);
		//BOTTOMLEFT
		g2d.fillOval((int) -width/2 - halfSize, (int) height/2 - halfSize, size, size);
		//BOTTOMRIGHT
		g2d.fillOval((int) width/2 - halfSize, (int) height/2 - halfSize, size, size);
		//ROTATOR
		g2d.fillOval(-halfSize, (int) -height/2 - size, size, size);
	}

    public void highlightLine(Shape selectedShape, Color oppositeColor, Graphics2D g2d) {
		Line line = (Line) selectedShape;
        AffineTransform worldSpaceToViewSpace = worldSpaceToViewSpace();
        g2d.setTransform(worldSpaceToViewSpace);
        int size = getHighlightSize();
        int halfSize = size/2;
        g2d.setColor(oppositeColor);
		g2d.fillOval((int) line.getStartingPoint().getX() - halfSize,
				(int) line.getStartingPoint().getY() - halfSize, size, size);
		g2d.fillOval((int) line.getEndPoint().getX() - halfSize,
				(int) line.getEndPoint().getY() - halfSize, size, size);
	}

	public void highlightTriangle(Shape selectedShape, Color oppositeColor, Graphics2D g2d) {
        Triangle triangle = (Triangle) selectedShape;
        AffineTransform worldSpaceToViewSpace = worldSpaceToViewSpace();
        g2d.setTransform(worldSpaceToViewSpace);
        int size = getHighlightSize();
        int halfSize = size / 2;
        g2d.setColor(oppositeColor);
        g2d.fillOval((int) triangle.getOne().getX() - halfSize,
                (int) triangle.getOne().getY() - halfSize, size, size);
        g2d.fillOval((int) triangle.getTwo().getX() - halfSize,
                (int) triangle.getTwo().getY() - halfSize, size, size);
        g2d.fillOval((int) triangle.getThree().getX() - halfSize,
                (int) triangle.getThree().getY() - halfSize, size, size);
    }

    public int getHighlightSize() {
        double zoom = singletonManager.inst().getZoomValue();
        double highlightSize = 16/zoom;
        return (int) highlightSize;
    }

    private AffineTransform getAffineTransformation(Shape shape) {
        AffineTransform viewToObjectSpace = viewToObjectSpace(shape);
        AffineTransform worldSpaceToViewSpace = worldSpaceToViewSpace();
        worldSpaceToViewSpace.concatenate(viewToObjectSpace);
        return worldSpaceToViewSpace;
    }

    private AffineTransform viewToObjectSpace(Shape shape) {
        AffineTransform translate = new AffineTransform(1, 0, 0, 1, shape.getCenter().getX(), shape.getCenter().getY());
        AffineTransform rotate = new AffineTransform(1, Math.sin(shape.getRotationDegree()),
                -Math.sin(shape.getRotationDegree()), 1, -1, -0);
        translate.rotate(Math.toRadians(shape.getRotationDegree()));
//        translate.concatenate(rotate);
        return translate;
    }

    private AffineTransform worldSpaceToViewSpace() {
        singletonManager sm = singletonManager.inst();
        double leftOfView = sm.getLeftSideOfView();
        double topOfView = sm.getTopOfView();
        double zoom = singletonManager.inst().getZoomValue();
        AffineTransform translate = new AffineTransform(1, 0, 0, 1, -leftOfView / zoom, -topOfView / zoom);
        AffineTransform zoomed = new AffineTransform(zoom, 0, 0, zoom, -1, -0);
        translate.concatenate(zoomed);
        return translate;
    }
}
