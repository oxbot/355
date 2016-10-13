package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * Add your circle code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Circle extends Shape {
	
	// The upper left corner of this shape.
	private Point2D.Double upperLeft;

	// The radius.
	private double radius;
	
	// The size of this Circle.
	private double size;
	
	//The original upper left
	private Point2D.Double original_ul;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param center the center of the new shape.
	 * @param radius the radius of the new shape.
	 */
	public Circle(Color color, Point2D.Double center, double radius) {

		// Initialize the superclass.
		super(color, center);
		this.rotation = 0;

		// Set fields.
		this.center = center;
		this.radius = radius;
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
	 * Getter for this Circle's radius.
	 * @return the radius of this Circle as a double.
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Setter for this Circle's radius.
	 * @param radius the new radius of this Circle.
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	/**
	 * Getter for this Circle's size.
	 * @return the size as a double.
	 */
	public double getSize() {
		return size;
	}

	/**
	 * Setter for this Circle's size.
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
		System.out.println(center.toString());
		System.out.println(radius);
		if ( Math.pow(pt.getX(), 2) + Math.pow(pt.getY(), 2) <= Math.pow(radius,2)  ) {
			return true;
		}
		else {
			return false;
		}
	}
}
