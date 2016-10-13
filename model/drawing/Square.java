package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Add your square code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Square extends Shape {

	// The upper left corner of this shape.
	private Point2D.Double upperLeft;

	// The size of this Square.
	private double size;
	
	//The original upper left
	private Point2D.Double original_ul;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param upperLeft the upper left corner of the new shape.
	 * @param size the size of the new shape.
	 */
	public Square(Color color, Point2D.Double upperLeft, double size) {

		
		// Initialize the superclass.
		super(color, new Point2D.Double(upperLeft.getX() + size /4, upperLeft.getY() + size / 4)); //should be super(color, center);

		// Set fields.
		this.upperLeft = upperLeft;
		this.size = size;
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
	 * Getter for this Square's size.
	 * @return the size as a double.
	 */
	public double getSize() {
		return size;
	}

	/**
	 * Setter for this Square's size.
	 * @param size the new size.
	 */
	public void setSize(double size) {
		this.size = size;
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
		
		if (Math.abs(pt.getX()) <= size / 4 && Math.abs(pt.getY()) <= size / 4) {
			return true;
		}
		else {
			return false;
		}
	}
}
