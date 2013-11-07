package cs355.lab1;

import cs355.GUIFunctions;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

/**
 * @author mn263
 *         Date: 9/13/13
 *         Time: 12:08 PM
 */
public class Controller implements cs355.CS355Controller {

    private static Point startingPoint;
    private static Controller instance;
    public static Controller inst() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    private ShapeEnum operationSelected = ShapeEnum.LINE;
    public static enum ShapeEnum {
        CIRCLE, ELLIPSE, LINE, RECTANGLE, SQUARE, TRIANGLE, SELECT
    }

    public void setStartingPoint(int x, int y) {
        Controller.startingPoint = new Point(x, y);
    }
    private void setSelectedOperation(ShapeEnum selectedShape) {
        this.operationSelected = selectedShape;
    }

    @Override
    public void colorButtonHit(Color c) {
        GUIFunctions.changeSelectedColor(c);
        singletonManager.inst().setSelectedColor(c);
        if(singletonManager.inst().getselectedShape() != null) {
        	singletonManager.inst().getselectedShape().setColor(c);
        }
        GUIFunctions.refresh();
    }

    @Override
    public void triangleButtonHit() {
        setSelectedOperation(ShapeEnum.TRIANGLE);
    }

    @Override
    public void squareButtonHit() {
        setSelectedOperation(ShapeEnum.SQUARE);
    }

    @Override
    public void rectangleButtonHit() {
        setSelectedOperation(ShapeEnum.RECTANGLE);
    }

    @Override
    public void circleButtonHit() {
        setSelectedOperation(ShapeEnum.CIRCLE);
    }

    @Override
    public void ellipseButtonHit() {
        setSelectedOperation(ShapeEnum.ELLIPSE);
    }

    @Override
    public void lineButtonHit() {
        setSelectedOperation(ShapeEnum.LINE);
    }

    @Override
    public void selectButtonHit() {
        setSelectedOperation(ShapeEnum.SELECT);
    }

    @Override
    public void zoomInButtonHit() {
        Model.inst().notTriangle();
        singletonManager sm = singletonManager.inst();
        double zoomValue = sm.getZoomValue();
        if(zoomValue == sm.TWENTYFIVEPERCENT) {
            sm.setZoomValue(sm.FIFTYPERCENT);
        } else if(zoomValue == sm.FIFTYPERCENT) {
            sm.setZoomValue(sm.ONEHUNDREDPERCENT);
        } else if(zoomValue == sm.ONEHUNDREDPERCENT) {
            sm.setZoomValue(sm.TWOHUNDREDPERCENT);
        } else {
            sm.setZoomValue(sm.FOURHUNDREDPERCENT);
        }

        if(zoomValue != sm.FOURHUNDREDPERCENT) {
            sm.setLeftSideOfView(sm.getLeftSideOfView() * 2);
            sm.setTopOfView(sm.getTopOfView() * 2);
        }
        updateScrollBarSizes();
        setScrollBarPositionIn(zoomValue);
//        GUIFunctions.setHScrollBarPosit(0);
//        GUIFunctions.setVScrollBarPosit(0);
        GUIFunctions.refresh();
    }


    @Override
    public void zoomOutButtonHit() {
        Model.inst().notTriangle();
        singletonManager sm = singletonManager.inst();
        double zoomValue = sm.getZoomValue();
        if(zoomValue == sm.FOURHUNDREDPERCENT) {
            sm.setZoomValue(sm.TWOHUNDREDPERCENT);
        } else if(zoomValue == sm.TWOHUNDREDPERCENT) {
            sm.setZoomValue(sm.ONEHUNDREDPERCENT);
        } else if(zoomValue == sm.ONEHUNDREDPERCENT) {
            sm.setZoomValue(sm.FIFTYPERCENT);
        } else {
            sm.setZoomValue(sm.TWENTYFIVEPERCENT);
        }
        sm.setLeftSideOfView(sm.getLeftSideOfView()/2);
        sm.setTopOfView(sm.getTopOfView() / 2);
        updateScrollBarSizes();
        setScrollBarPositionOut(zoomValue);
        GUIFunctions.refresh();
    }

    private void setScrollBarPositionOut(double zoomValue) {
        singletonManager sm = singletonManager.inst();

        double left = sm.getLeftSideOfView();
        double top = sm.getTopOfView();
        if(zoomValue == sm.FOURHUNDREDPERCENT) {
            GUIFunctions.setHScrollBarPosit(3519);
            GUIFunctions.setVScrollBarPosit(3519);
        } else if(zoomValue == sm.TWOHUNDREDPERCENT) {
            GUIFunctions.setHScrollBarPosit(752);
            GUIFunctions.setVScrollBarPosit(752);
        } else if(zoomValue == sm.ONEHUNDREDPERCENT) {
            GUIFunctions.setHScrollBarPosit(124);
            GUIFunctions.setVScrollBarPosit(124);
        } else {
            GUIFunctions.setHScrollBarPosit(124);
            GUIFunctions.setVScrollBarPosit(124);
        }
    }
    private void setScrollBarPositionIn(double zoomValue) {
        singletonManager sm = singletonManager.inst();
        double left = sm.getLeftSideOfView();
        double top = sm.getTopOfView();
        if(zoomValue == sm.FOURHUNDREDPERCENT) {
            GUIFunctions.setHScrollBarPosit((int) left);
            GUIFunctions.setVScrollBarPosit((int) top);
        } else if(zoomValue == sm.TWOHUNDREDPERCENT) {
            GUIFunctions.setHScrollBarPosit((int)(left*4) + 256*4);
            GUIFunctions.setVScrollBarPosit((int)(top*4) + 256*4);
        } else if(zoomValue == sm.ONEHUNDREDPERCENT) {
            GUIFunctions.setHScrollBarPosit((int)(left*4) + 256*2);
            GUIFunctions.setVScrollBarPosit((int)(top*4) + 256*2);
        } else if(zoomValue == sm.FIFTYPERCENT) {
            GUIFunctions.setHScrollBarPosit((int)(left*4) + 256);
            GUIFunctions.setVScrollBarPosit((int)(top*4) + 256);
        } else {
            GUIFunctions.setHScrollBarPosit(124);
            GUIFunctions.setVScrollBarPosit(124);
        }
    }

    private void updateScrollBarSizes() {
        singletonManager sm = singletonManager.inst();
        double zoomValue = sm.getZoomValue();
        int scrollBarMax = 2048;
        if(zoomValue == sm.FOURHUNDREDPERCENT) {
            scrollBarMax = 32768-1536;
        } else if(zoomValue == sm.TWOHUNDREDPERCENT) {
            scrollBarMax = 8192-512;
        } else if(zoomValue == sm.FIFTYPERCENT) {
            scrollBarMax = 1024-256;
        } else if(zoomValue == sm.TWENTYFIVEPERCENT) {
            scrollBarMax = 512;
        }
        GUIFunctions.setHScrollBarKnob(512);
        GUIFunctions.setVScrollBarKnob(512);
        GUIFunctions.setHScrollBarMax(scrollBarMax);
        GUIFunctions.setVScrollBarMax(scrollBarMax);
        GUIFunctions.setHScrollBarMin(0);
        GUIFunctions.setVScrollBarMin(0);
    }

    private Point newPoint(int x, int y) {
        singletonManager sm = singletonManager.inst();
        double zoomValue = sm.getZoomValue();
        Point zoomedPoint = new Point(x/zoomValue, y/zoomValue);
        zoomedPoint.setX(zoomedPoint.getX() + sm.getLeftSideOfView() / (zoomValue * zoomValue));
        zoomedPoint.setY(zoomedPoint.getY() + sm.getTopOfView() / (zoomValue * zoomValue));
        return zoomedPoint;
    }

    @Override
    public void hScrollbarChanged(int value) {
        singletonManager.inst().setLeftSideOfView(value);
        GUIFunctions.refresh();
    }

    @Override
    public void vScrollbarChanged(int value) {
        singletonManager.inst().setTopOfView(value);
        GUIFunctions.refresh();
    }

    public void mouseDraggedHandler(MouseEvent e) {
        Point pointInView = convertPointWorldToView(e);
    	if(isOperationAShape()) {
    		Model.inst().updateShape(operationSelected, pointInView.getX(), pointInView.getY(), (int) startingPoint.getX(), (int) startingPoint.getY());
    	} else if(singletonManager.inst().getselectedShape() != null && singletonManager.inst().isDragging) {
    		Model.inst().getSelectShapeClass().modifySelectedShape(pointInView.getX(), pointInView.getY(),
                    startingPoint.getX(), startingPoint.getY());
    	}
    }
    
    public void mousePressedHandler(MouseEvent e) {
        Point pointInView = convertPointWorldToView(e);
        startingPoint = new Point(pointInView.getX(), pointInView.getY());
        if(isOperationAShape()) {
        	Model.inst().addShape(operationSelected, startingPoint.getX(), startingPoint.getY());
        }
        if(this.operationSelected == ShapeEnum.SELECT) {
    		Model.inst().selectShape(startingPoint.getX(), startingPoint.getY());
        } else {
    		singletonManager.inst().setSelectedShape(null);
    		singletonManager.inst().centerBeforeDrag = null;
    		singletonManager.inst().isDragging = false;
        }
        if (this.operationSelected != ShapeEnum.TRIANGLE) {
            Model.inst().notTriangle();
        }
        GUIFunctions.refresh();
    }

    private boolean isOperationAShape() {
        switch (this.operationSelected) {
        case CIRCLE:
    		return true;
        case ELLIPSE:
    		return true;
        case LINE:
    		return true;
        case RECTANGLE:
    		return true;
        case SQUARE:
    		return true;
        case TRIANGLE:
    		return true;
		default:
			break;
        }
		return false;
    }

    private Point convertPointWorldToView(MouseEvent e) {
        Point mousePoint = new Point(e.getX(), e.getY());
        singletonManager sm = singletonManager.inst();
        Point objectPoint = newPoint(e.getX(), e.getY());
        double zoomValue = sm.getZoomValue();
        AffineTransform transform = new AffineTransform(-zoomValue, 0, 0, -zoomValue, -1, -0);
        AffineTransform transformer = new AffineTransform(1, 0, 0, 1, e.getX()/zoomValue, e.getY()/zoomValue);
        transform.concatenate(transformer);
        Point returnPoint = (Point) transform.transform(mousePoint, objectPoint);
        return returnPoint;
    }
}
