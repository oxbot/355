package cs355.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Observable;

import cs355.GUIFunctions;
import cs355.controller.MyController;
import cs355.model.drawing.Circle;
import cs355.model.drawing.Ellipse;
import cs355.model.drawing.Line;
import cs355.model.drawing.MyModel;
import cs355.model.drawing.Rectangle;
import cs355.model.drawing.Shape;
import cs355.model.drawing.Square;
import cs355.model.drawing.Triangle;

public class MyView implements ViewRefresher {
	private MyModel model;
	private Ellipse2D.Double handle;
	private Ellipse2D.Double handle2;
	private MyController controller;
	//private Point2D.Double handle_center;
	
	public void addModel(MyModel model){
		this.model = model;
		this.model.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		GUIFunctions.refresh();

	}

	@Override
	public void refreshView(Graphics2D g2d) {
		ArrayList<Shape> shapes = (ArrayList<Shape>) model.getShapes();
		
		double scale = controller.get_zoom();
		
		for (int i = 0; i < shapes.size();i++) {
			//get each shape, translate it, and draw it
			if (shapes.get(i) instanceof cs355.model.drawing.Line) {
				
				Line line = (Line) shapes.get(i);
				
				AffineTransform objToWorld = new AffineTransform();
				
				objToWorld.translate(line.getCenter().getX(), line.getCenter().getY());
				
				
				objToWorld.scale(scale, scale);
				objToWorld.translate(-controller.get_scroll().getX(), -controller.get_scroll().getY());
				//objToWorld.rotate(square.getRotation());
				g2d.setTransform(objToWorld);
				
				g2d.setPaint(line.getColor());
				
				//Need to make the end of the line just the offset from the start for the transform
				g2d.drawLine(0, 0, (int)line.getEnd().getX() - (int)line.getCenter().getX(), (int)line.getEnd().getY() - (int)line.getCenter().getY());
				
			}
			else if (shapes.get(i) instanceof cs355.model.drawing.Square) {
				
				Square square = (Square) shapes.get(i);
				g2d = objToView(g2d, square.getCenter(), square.getRotation(), scale, controller.get_scroll(), square.getColor());
				g2d.fill(new Rectangle2D.Double(-square.getSize() / 4,  -square.getSize() / 4, square.getSize()/2, square.getSize()/2));
			}
			else if (shapes.get(i) instanceof cs355.model.drawing.Rectangle) {
				
				Rectangle rect = (Rectangle) shapes.get(i);
				g2d = objToView(g2d, rect.getCenter(), rect.getRotation(), scale, controller.get_scroll(), rect.getColor());
				g2d.fill(new Rectangle2D.Double(-rect.getWidth() / 2, -rect.getHeight()/2, rect.getWidth(), rect.getHeight()));
			}
			else if (shapes.get(i) instanceof cs355.model.drawing.Circle) {
				
				Circle circle = (Circle) shapes.get(i);
				g2d = objToView(g2d, circle.getCenter(), circle.getRotation(), scale, controller.get_scroll(), circle.getColor());
				g2d.fill(new Ellipse2D.Double(-circle.getSize()/4, -circle.getSize()/4, circle.getSize()/2, circle.getSize()/2));
			}
			else if (shapes.get(i) instanceof cs355.model.drawing.Ellipse) {
				
				Ellipse ell = (Ellipse) shapes.get(i);
				g2d = objToView(g2d, ell.getCenter(), ell.getRotation(), scale, controller.get_scroll(), ell.getColor());
				g2d.fill(new Ellipse2D.Double(-ell.getWidth()/2, -ell.getHeight()/2, ell.getWidth(), ell.getHeight()));
			}
			else if (shapes.get(i) instanceof cs355.model.drawing.Triangle) {
				
				Triangle tri = (Triangle) shapes.get(i);
				g2d = objToView(g2d, tri.getCenter(), tri.getRotation(), scale, controller.get_scroll(), tri.getColor());
				
				int[] xPoints = new int[3];
				int[] yPoints = new int[3];
				xPoints[0] = (int) tri.getA().getX();
				yPoints[0] = (int) tri.getA().getY();
				xPoints[1] = (int) tri.getB().getX();
				yPoints[1] = (int) tri.getB().getY();
				xPoints[2] = (int) tri.getC().getX();
				yPoints[2] = (int) tri.getC().getY();
				
				g2d.fillPolygon(xPoints, yPoints, 3);
			}
		}
		
		//System.out.println("Before selection, value of selected: " + model.getSelected());
		if (model.getSelected() != -1) {
			if (shapes.get(model.getSelected()) instanceof cs355.model.drawing.Line) {
				
				Line line = (Line) shapes.get(model.getSelected());
				
				AffineTransform objToWorld = new AffineTransform();
				
				objToWorld.translate(line.getCenter().getX(), line.getCenter().getY());
				//objToWorld.rotate(square.getRotation());
				
				objToWorld.translate(-controller.get_scroll().getX(), -controller.get_scroll().getY());
				objToWorld.scale(scale, scale);
				
				g2d.setTransform(objToWorld);
				
				g2d.setPaint(Color.RED);
				
				g2d.draw(new Ellipse2D.Double(- 5, - 5, 10, 10));
				g2d.draw(new Ellipse2D.Double(line.getEnd().getX() - (int)line.getStart().getX() - 5, line.getEnd().getY() - (int)line.getStart().getY() - 5, 10, 10));
				
			}
			else if (shapes.get(model.getSelected()) instanceof cs355.model.drawing.Square) {
				//System.out.println("selected, drawing stuff");
				Square square = (Square) shapes.get(model.getSelected());
				
				//These two lines draw the outline around the shape, first set up the same rotation/translation/etc, but make it red and don't fill it
				g2d = objToView(g2d, square.getCenter(), square.getRotation(), scale, controller.get_scroll(), Color.RED);
				g2d.draw(new Rectangle2D.Double(-square.getSize() / 4,  -square.getSize() / 4, square.getSize()/2, square.getSize()/2));
				
				/*
				 * Code below is for drawing the handle and making the handle object, this needs to be better, doesn't work very well currently
				 */
				//need to draw this and add it to the top of the list of shapes, then delete it on deselect
				//g2d.draw(new Ellipse2D.Double(-5, -square.getSize()/4 - 20, 10, 10));
				
				draw_handle(g2d, -square.getSize()/4, square.getCenter());
				
				Point2D.Double handle_enter_org = new Point2D.Double(square.getCenter().getX() -5, square.getCenter().getY() - square.getSize() / 4 - 20);
				Point2D.Double handle_point = new Point2D.Double();			
				
				
				//objToWorld.transform(handle_enter_org, handle_point);				
				Ellipse2D.Double handle = new Ellipse2D.Double(handle_point.getX(), 
						handle_point.getY(), 
						10, 
						10);
				
				this.handle = handle;
				
			}
			else if (shapes.get(model.getSelected()) instanceof cs355.model.drawing.Rectangle) {
				Rectangle rect = (Rectangle) shapes.get(model.getSelected());
				
				g2d = objToView(g2d, rect.getCenter(), rect.getRotation(), scale, controller.get_scroll(), Color.RED);
				
				g2d.draw(new Rectangle2D.Double(-rect.getWidth() / 2,  -rect.getHeight() / 2, rect.getWidth(), rect.getHeight()));
				
				//need to draw this and add it to the top of the list of shapes, then delete it on deselect
				g2d.draw(new Ellipse2D.Double(-5, -rect.getHeight()/2 - 20, 10, 10));
				
				

				Point2D.Double handle_enter_org = new Point2D.Double(rect.getCenter().getX() -5, rect.getCenter().getY() - rect.getHeight() / 2 - 20);
				Point2D.Double handle_point = new Point2D.Double();			
				
				
				//objToWorld.transform(handle_enter_org, handle_point);
				
				AffineTransform worldToObj = new AffineTransform();
				//worldToObj.rotate();

				worldToObj.rotate(- rect.getRotation());
				worldToObj.translate(-rect.getCenter().getX(), -rect.getCenter().getY());
				
				worldToObj.transform(handle_enter_org, handle_point);
				
				Ellipse2D.Double handle = new Ellipse2D.Double(handle_point.getX(), 
						handle_point.getY(), 
						10, 
						10);
				
				this.handle = handle;
				
			}
			else if (shapes.get(model.getSelected()) instanceof cs355.model.drawing.Circle) {
				Circle circle = (Circle) shapes.get(model.getSelected());
				
				g2d = objToView(g2d, circle.getCenter(), circle.getRotation(), scale, controller.get_scroll(), Color.RED);
				
				g2d.draw(new Rectangle2D.Double(-circle.getRadius(),  -circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2));
				
				//need to draw this and add it to the top of the list of shapes, then delete it on deselect
				g2d.draw(new Ellipse2D.Double(-5, -circle.getRadius() - 20, 10, 10));
				
				Ellipse2D.Double handle = new Ellipse2D.Double(circle.getCenter().getX()-5, 
						circle.getCenter().getY() - circle.getRadius() - 20, 
						10, 
						10);
				
				this.handle = handle;
				
	
			}
			else if (shapes.get(model.getSelected()) instanceof cs355.model.drawing.Ellipse) {
				Ellipse ell = (Ellipse) shapes.get(model.getSelected());
				
				g2d = objToView(g2d, ell.getCenter(), ell.getRotation(), scale, controller.get_scroll(), Color.RED);
				
				g2d.draw(new Rectangle2D.Double(-ell.getWidth() / 2,  -ell.getHeight() / 2, ell.getWidth(), ell.getHeight()));
				
				draw_handle(g2d, -ell.getHeight()/2, ell.getCenter());
				
				
				Point2D.Double handle_enter_org = new Point2D.Double(ell.getCenter().getX() -5, ell.getCenter().getY() - ell.getHeight() / 2 - 20);
				Point2D.Double handle_point = new Point2D.Double();			
				
				
				Ellipse2D.Double handle = new Ellipse2D.Double(handle_point.getX(), 
						handle_point.getY(), 
						10, 
						10);
				
				this.handle = handle;
	
			}
			else if (shapes.get(model.getSelected()) instanceof cs355.model.drawing.Triangle) {
				
				Triangle tri = (Triangle) shapes.get(model.getSelected());
				
				g2d = objToView(g2d, tri.getCenter(), tri.getRotation(), scale, controller.get_scroll(), Color.RED);
				
				Point2D.Double handle_enter_org = new Point2D.Double(tri.getCenter().getX() -5, tri.getCenter().getY() - Math.abs(Math.min(Math.min(tri.getA().getY(), tri.getB().getY()), tri.getC().getY())) - 20);
				Point2D.Double handle_point = new Point2D.Double();			
				
				
				//objToWorld.transform(handle_enter_org, handle_point);
				
				AffineTransform worldToObj = new AffineTransform();
				//worldToObj.rotate();
				
				worldToObj.scale(1/scale, 1/scale);
				worldToObj.translate(controller.get_scroll().getX(), controller.get_scroll().getY());

				worldToObj.rotate(- tri.getRotation());
				worldToObj.translate(-tri.getCenter().getX(), -tri.getCenter().getY());
				
				worldToObj.transform(handle_enter_org, handle_point);
				
				
				int[] xPoints = new int[3];
				int[] yPoints = new int[3];
				xPoints[0] = (int) tri.getA().getX();
				yPoints[0] = (int) tri.getA().getY();
				xPoints[1] = (int) tri.getB().getX();
				yPoints[1] = (int) tri.getB().getY();
				xPoints[2] = (int) tri.getC().getX();
				yPoints[2] = (int) tri.getC().getY();
				
				g2d.drawPolygon(xPoints, yPoints, 3);
				
				Ellipse2D.Double handle = new Ellipse2D.Double(handle_point.getX(), 
						handle_point.getY() , 
						10, 
						10);
				

				g2d.draw(new Ellipse2D.Double(-5, Math.min(Math.min(tri.getA().getY(), 
						tri.getB().getY()), tri.getC().getY()) - 20, 
						10, 
						10));
				
				this.handle = handle;
				
	
			}
			
		}

	}


	public Ellipse2D.Double getHandle() {
		return handle;
	}

	public void setHandle(Ellipse2D.Double handle) {
		this.handle = handle;
	}

	public Ellipse2D.Double getHandle2() {
		return handle2;
	}

	public void setHandle2(Ellipse2D.Double handle2) {
		this.handle2 = handle2;
	}

	public boolean handleClicked(Point2D.Double pt) {
		//System.out.println(handle.getCenterX().toString());
		System.out.println("Checking for handle clicked in view");
		System.out.println("Input point: " + pt.toString());
		
		double unscale = 1 / controller.get_zoom();
		
		if (model.getSelected() != -1) {
			
			//need to turn point into the object space of the currently selected object
			
			
			Shape cur_shape = model.getShape(model.getSelected());
			
			System.out.println("Unscale: " + unscale);
			
			
			AffineTransform worldToObj = new AffineTransform();
			
			
			//Shape cur_shape = model.getShapes().get(model.getSelected());
			
			double highest_y = get_high_y(cur_shape);
			
			//This gets the center of the handle in view space
			Point2D.Double handle_center = new Point2D.Double(cur_shape.getCenter().getX(), highest_y-15);
			
			worldToObj.scale(unscale, unscale);
			//worldToObj.translate(controller.get_scroll().getX(), controller.get_scroll().getY());

			worldToObj.translate(-cur_shape.getCenter().getX(), -cur_shape.getCenter().getY());
			worldToObj.rotate(- cur_shape.getRotation());
			System.out.println("The rotation: " + cur_shape.getRotation());
			
			Point2D.Double objCoord = new Point2D.Double();
			
			
			Point2D.Double center_trans = new Point2D.Double();
			
			
			
			worldToObj.transform(pt, objCoord);
			
			//This doesn't seem to be transforming correctly, it stays in the original position always, actually, it's off, not sure why
			worldToObj.transform(handle_center, center_trans);
		
			System.out.println("objcoord: " + objCoord.getX() + "," + objCoord.getY());
			System.out.println("trans Center: " + center_trans.getX() + "," + center_trans.getY());
			System.out.println("Handle_center: " + handle_center);
			
			
			
			//The objCoord should be - the center of the handle
			
			if ( Math.pow(objCoord.getX(), 2) + Math.pow(objCoord.getY() - center_trans.getY(), 2) <= Math.pow(5,2)  ) {
				return true;
			}
			else {
				System.out.println("No match");
				return false;
			}
		}
		else {
			return false;
		}
	}

	//Gets the highest y position of a shape
	private double get_high_y(Shape shape) {
		if (shape instanceof cs355.model.drawing.Line) {
			
			Line line = (Line) shape;

		}
		else if (shape instanceof cs355.model.drawing.Square) {
			
			Square square = (Square) shape;
			
			return square.getCenter().getY() - square.getSize() / 4;

		}
		else if (shape instanceof cs355.model.drawing.Rectangle) {
			
			Rectangle rect = (Rectangle) shape;
			
			return rect.getCenter().getY() - rect.getHeight() / 2;

		}
		else if (shape instanceof cs355.model.drawing.Circle) {
			
			Circle circle = (Circle) shape;
			
			return circle.getCenter().getY() - circle.getRadius();

		}
		else if (shape instanceof cs355.model.drawing.Ellipse) {
			
			Ellipse ell = (Ellipse) shape;
			
			return  ell.getCenter().getY() - ell.getHeight() / 2;

		}
		else if (shape instanceof cs355.model.drawing.Triangle) {
			
			Triangle tri = (Triangle) shape;


			return tri.getCenter().getY() - Math.abs(Math.min(Math.min(tri.getA().getY(), tri.getB().getY()), tri.getC().getY()));

		}
		return 0;
	}

	public void addController(MyController my_controller) {
		controller = my_controller;
		
	}
	
	public Graphics2D objToView(Graphics2D g2d, Point2D.Double center, double angle, double scale, Point2D.Double scroll, Color color) {
		
		AffineTransform objToWorld = new AffineTransform();
		
		//Object to world
		objToWorld.translate(center.getX(), center.getY());
		objToWorld.rotate(angle);
		
		//World to view
		//Currently the scroll is throwing it off, need to figure out how to do this. It needs to be stored taking the scroll into account, that's what
		objToWorld.translate(-scroll.getX(), -scroll.getY());
		objToWorld.scale(scale, scale);
		
		
		g2d.setTransform(objToWorld);
		
		g2d.setPaint(color);
		
		return g2d;
	}
	
	public Graphics2D viewToObj(Graphics2D g2d, Point2D.Double center, double angle, double scale, Point2D.Double scroll, Color color) {
		
		AffineTransform viewToObj = new AffineTransform();
		
		//View to World
		viewToObj.scale(1/scale, 1/scale);
		viewToObj.translate(scroll.getX(), scroll.getY());

		//World to obj
		viewToObj.rotate(-angle);
		viewToObj.translate(-center.getX(), -center.getY());
		
		g2d.setTransform(viewToObj);
		
		g2d.setPaint(color);
		
		return g2d;
	}
	
	//The y_val is the top of the shape
	//the -20 makes the top left of the handle drawn 20px above the top of the shape
	public void draw_handle(Graphics2D g2d, double y_val, Point2D.Double center) {
		
		//handle_center = new Point2D.Double(center.getX(), center.getY() + y_val-15);
		
		g2d.draw(new Ellipse2D.Double(-5, y_val - 20, 10, 10));
		
	}

}
