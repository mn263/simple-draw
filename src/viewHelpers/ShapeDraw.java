package viewHelpers;

import java.awt.*;
import java.awt.geom.AffineTransform;

import cs355.lab1.Shape;
import cs355.lab1.ShapeClasses.Line;
import cs355.lab1.ShapeClasses.Triangle;
import cs355.lab1.singletonManager;

public class ShapeDraw {
	
	public void drawOval(Shape shape, Graphics2D g2d) {
        AffineTransform worldSpaceToViewSpace = getAffineTransformation(shape);
        g2d.setTransform(worldSpaceToViewSpace);
        g2d.fillOval((int) -shape.getWidth()/2, (int) -shape.getHeight()/2, (int) shape.getWidth(), (int) shape.getHeight());
    }

    public void drawBox(Shape shape, Graphics2D g2d) {
        AffineTransform worldSpaceToViewSpace = getAffineTransformation(shape);
        g2d.setTransform(worldSpaceToViewSpace);
		g2d.fillRect((int) -shape.getWidth()/2, (int) -shape.getHeight()/2, (int) shape.getWidth(), (int) shape.getHeight());
	}
	public void drawLine(Line line, Graphics2D g2d) {
        AffineTransform worldSpaceToViewSpace = worldSpaceToViewSpace();
        g2d.setTransform(worldSpaceToViewSpace);
        double zoom = singletonManager.inst().getZoomValue();
        g2d.setStroke(new BasicStroke((float) ((float) 1/zoom)));
        g2d.drawLine((int) line.getStartingPoint().getX(), (int) line.getStartingPoint().getY(),
                (int) line.getEndPoint().getX(), (int) line.getEndPoint().getY());
    }
	public void drawTriangle(Triangle triangle, Graphics2D g2d) {
        AffineTransform worldSpaceToViewSpace = worldSpaceToViewSpace();
        g2d.setTransform(worldSpaceToViewSpace);
        int[] xPoints = new int[] {(int) triangle.getOne().getX(), (int) triangle.getTwo().getX(),
                (int) triangle.getThree().getX()};
        int[] yPoints = new int[] { (int) triangle.getOne().getY(), (int) triangle.getTwo().getY(),
                (int) triangle.getThree().getY()};
        g2d.fillPolygon(xPoints, yPoints, 3);
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
