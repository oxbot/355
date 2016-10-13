package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Add your rectangle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Rectangle extends Shape {

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
	 * @param upperLeft the upper left corner of the new shape.
	 * @param width the width of the new shape.
	 * @param height the height of the new shape.
	 */
	public Rectangle(Color color, Point2D.Double upperLeft, double width, double height) {

		// Initialize the superclass.
		super(color, upperLeft); // should be super(color,center);

		// Set fields.
		this.upperLeft = upperLeft;
		this.width = width;
		this.height = height;
		this.rotation = 0;
	}
	
	/**
	 * Getter for this Rectangle's original upper left corner.
	 * @return the upper left corner as a Java point.
	 */
	public Point2D.Double getOriginalUpperLeft() {
		return original_ul;
	}

	/**
	 * Setter for this Rectangle's original upper left corner.
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
		
		
		
		if (Math.abs(pt.getX()) <= width / 2 && Math.abs(pt.getY()) <= height / 2) {
			return true;
		}
		else {
			return false;
		}
			
	}

}
