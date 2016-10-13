package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Add your ellipse code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Ellipse extends Shape {
	
	// The upper left corner of this shape.
	private Point2D.Double upperLeft;

	// The width of this shape.
	private double width;

	// The height of this shape.
	private double height;
	
	//The original upper left
	private Point2D.Double original_ul;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param width the width of the new shape.
	 * @param height the height of the new shape.
	 */
	public Ellipse(Color color, Point2D.Double center, double width, double height) {

		// Initialize the superclass.
		super(color, center);

		// Set fields.
		this.center = center;
		this.width = width;
		this.height = height;
		this.rotation = 0;
	}
	
	/**
	 * Getter for this Square's original upper left corner.
	 * @return the upper left corner as a Java point.
	 */
	public Point2D.Double getOriginalUpperLeft() {
		return original_ul;
	}

	/**
	 * Setter for this Square's original upper left corner.
	 * @param upperLeft the new upper left corner.
	 */
	public void setOriginalUpperLeft(Point2D.Double upperLeft) {
		this.original_ul = upperLeft;
	}
	
	/**
	 * Getter for this Rectangle's upper left corner.
	 * @return the upper left corner as a Java point.
	 */
	public Point2D.Double getUpperLeft() {
		return upperLeft;
	}

	/**
	 * Setter for this Rectangle's upper left corner.
	 * @param upperLeft the new upper left corner.
	 */
	public void setUpperLeft(Point2D.Double upperLeft) {
		this.upperLeft = upperLeft;
	}

	/**
	 * Getter for this shape's center.
	 * @return this shape's center as a Java point.
	 */
	public Point2D.Double getCenter() {
		return center;
	}

	/**
	 * Setter for this shape's center.
	 * @param center the new center as a Java point.
	 */
	public void setCenter(Point2D.Double center) {
		this.center = center;
	}

	/**
	 * Getter for this shape's width.
	 * @return this shape's width as a double.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Setter for this shape's width.
	 * @param width the new width.
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * Getter for this shape's height.
	 * @return this shape's height as a double.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Setter for this shape's height.
	 * @param height the new height.
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	
	/**
	 * Add your code to do an intersection test
	 * here. You shouldn't need the tolerance.
	 * @param pt = the point to test against.
	 * @param tolerance = the allowable tolerance.
	 * @return true if pt is in the shape,
	 *		   false otherwise.
	 */
	@Override
	public boolean pointInShape(Point2D.Double pt, double tolerance) {
		
		System.out.println("center: " + center.toString());
		System.out.println("width: " + width + " height: " + height);
		
		System.out.println("x test: " + Math.abs(pt.getX() - center.getX()));
		System.out.println("y test: " + Math.abs(pt.getY() - center.getY()));
		
		
		if (Math.abs(pt.getX()) <= width / 2 && Math.abs(pt.getY()) <= height / 2) {
			
		
			if (Math.pow((pt.getX())/ (width / 2), 2) + Math.pow((pt.getY())/ (height / 2), 2) <= 1) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
}
