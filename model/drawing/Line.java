package cs355.model.drawing;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Add your line code here. You can add fields, but you cannot
 * change the ones that already exist. This includes the names!
 */
public class Line extends Shape {

	// The starting point of the line.
	private Point2D.Double start;

	// The ending point of the line.
	private Point2D.Double end;

	/**
	 * Basic constructor that sets all fields.
	 * @param color the color for the new shape.
	 * @param start the starting point.
	 * @param end the ending point.
	 */
	public Line(Color color, Point2D.Double start, Point2D.Double end) {

		// Initialize the superclass.
		super(color, start);

		// Set fields.
		this.start = start;
		this.end = end;
	}

	/**
	 * Getter for this Line's starting point.
	 * @return the starting point as a Java point.
	 */
	public Point2D.Double getStart() {
		return start;
	}

	/**
	 * Setter for this Line's starting point.
	 * @param start the new starting point for the Line.
	 */
	public void setStart(Point2D.Double start) {
		this.start = start;
	}

	/**
	 * Getter for this Line's ending point.
	 * @return the ending point as a Java point.
	 */
	public Point2D.Double getEnd() {
		return end;
	}

	/**
	 * Setter for this Line's ending point.
	 * @param end the new ending point for the Line.
	 */
	public void setEnd(Point2D.Double end) {
		this.end = end;
	}
	
	/**
	 * Add your code to do an intersection test
	 * here. You <i>will</i> need the tolerance.
	 * @param pt = the point to test against.
	 * @param tolerance = the allowable tolerance.
	 * @return true if pt is in the shape,
	 *		   false otherwise.
	 */
	@Override
	public boolean pointInShape(Point2D.Double pt_in, double tolerance) {
		
		AffineTransform objToWorld = new AffineTransform();
		//worldToObj.rotate();
		
		objToWorld.translate(getCenter().getX(), getCenter().getY());
		
		Point2D.Double pt = new Point2D.Double();
		objToWorld.transform(pt_in, pt);
		
		
		//calculate d
		Double magnitude = Math.sqrt(Math.pow(end.getX() - center.getX(), 2) +  Math.pow(end.getY() - center.getY(), 2));
		
		//n hat
		Point2D.Double n = new Point2D.Double(-(end.getY() - center.getY()) / magnitude, 
				(end.getX() - center.getX()) / magnitude);
		
		Point2D.Double point = new Point2D.Double((end.getX() - center.getX()) / magnitude, 
				(end.getY() - center.getY()) / magnitude);
		
		//need d: start dotted with n-hat
		Double d = center.getX() * n.getX() + center.getY() * n.getY();
		
		//Calculate distance from point |q . n-hat - d|
		Double distance = Math.abs(  (pt.getX() * n.getX() + pt.getY() * n.getY()) - d );
		
		if (distance <= tolerance) {
			System.out.println("distance is within tolerance");
			//test if it's within the bounds
			//(q - start) . n-hat
			
			//I must have the wrong equation for the bounds, need to get this right
			Double bounds = (pt.getX() - center.getX()) * point.getX() + (pt.getY() - center.getY()) * point.getY();
			System.out.println("Bounds: " + bounds + " magnitude: " + magnitude);
			
			if (bounds <= magnitude && bounds >= 0) {
				System.out.println("distance is within bounds");
				return true;
			}
			else {
				System.out.println("distance is not within bounds");
				return false;
			}

		}
		else {
			System.out.println("distance is not within tolerance");
			return false;
		}
	

	}
}
