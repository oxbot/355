package cs355.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Iterator;

import cs355.GUIFunctions;
import cs355.model.drawing.Circle;
import cs355.model.drawing.Ellipse;
import cs355.model.drawing.Line;
import cs355.model.drawing.MyModel;
import cs355.model.drawing.Rectangle;
import cs355.model.drawing.Shape;
import cs355.model.drawing.Square;
import cs355.model.drawing.Triangle;
import cs355.view.MyView;

public class MyController implements CS355Controller {
	private String State;
	private Color current_color;
	private boolean debug = true;
	private MyModel model;
	private int current_index;
	private MyView view;
	
	private Point2D.Double a = null;
	private Point2D.Double b = null;
	private Point2D.Double c = null;
	
	private Point2D.Double point_clicked = null;
	private int selected = -1;
	private boolean handle_pressed = false;
	private int zoom_level = 2;
	private double h_scroll = 0;
	private double v_scroll = 0;
	double[] zoom_scale = new double[5];
	
	public MyController() {
		zoom_scale[0] = .25; //25% zoom
		zoom_scale[1] = .5;
		zoom_scale[2] = 1;
		zoom_scale[3] = 2;
		zoom_scale[4] = 4; //400% zoom
			
	}
	

	public void addModel(MyModel model){
		this.model = model;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (State == "LINE") {
			if (debug)
				System.out.println("CLICKED IN LINE" + arg0.getPoint().toString());
			
			//Probably need nothing here, mousePressed will do it all			
			
		}
		else if (State == "SQUARE") {
			
		}
		else if (State == "RECTANGLE") {
			
		}
		else if (State == "CIRCLE") {
			
		}
		else if (State == "ELLIPSE") {
			
		}
		else if (State == "TRIANGLE") {
			//Here I somehow need to track the three points and then create the shape and add it on the third click
			
			if (a == null) {
				a = new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY());
			}
			else if (b == null) {
				b = new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY());
			}
			else if (c == null) {
				c = new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY());
				
				//Need to make sure this is correct
				Point2D.Double center = new Point2D.Double((a.getX() + b.getX() + c.getX()) / 3, (a.getY() + b.getY() + c.getY()) / 3);
				
				a = new Point2D.Double((a.getX() - center.getX()) * (1/get_zoom()), (a.getY() - center.getY()) * (1/get_zoom()));
				b = new Point2D.Double((b.getX() - center.getX()) * (1/get_zoom()), (b.getY() - center.getY()) * (1/get_zoom()));
				c = new Point2D.Double((c.getX() - center.getX())* (1/get_zoom()), (c.getY() - center.getY()) * (1/get_zoom()));
				
				//System.out.println("triangle center: " + center.toString());
				
				Triangle tri = new Triangle(current_color, center, a, b, c);
				
				current_index = model.addShape(tri);
				
				a = null;
				b = null;
				c = null;
				
			}
			
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (State == "LINE") {
			if (debug)
				System.out.println("PRESSED IN LINE" + arg0.getPoint().toString());
			
			
		Line line = new Line(current_color, new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY()), new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY()));
		
		current_index = model.addShape(line);
		
		if (debug)
			System.out.println("CURRENT INDEX" + current_index);
				
		}
		else if (State == "SQUARE") {
			
			Point2D.Double original_point = new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY());
			
			Square square = new Square(current_color, original_point, 0);
			
			square.setOriginalUpperLeft(original_point);
			
			current_index = model.addShape(square);
			
		}
		else if (State == "RECTANGLE") {
			
			Point2D.Double original_point = new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY());
			
			Rectangle rect = new Rectangle(current_color, original_point, 0, 0);
			
			rect.setOriginalUpperLeft(original_point);
			
			current_index = model.addShape(rect);
			
		}
		else if (State == "CIRCLE") {
			
			Point2D.Double original_point = new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY());
			
			Circle circle = new Circle(current_color, original_point, 0);
			
			circle.setOriginalUpperLeft(original_point);
			circle.setUpperLeft(original_point);
			
			current_index = model.addShape(circle);
			
		}
		else if (State == "ELLIPSE") {
			
			Point2D.Double original_point = new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY());
			
			Ellipse ell = new Ellipse(current_color, original_point, 0, 0);
			
			ell.setUpperLeft(original_point);
			
			ell.setOriginalUpperLeft(original_point);
			
			current_index = model.addShape(ell);
			
		}
		else if (State == "SELECT") {
			//System.out.println("mouse select " + arg0.getPoint().getX() + " " + arg0.getPoint().getY());
			
			//AffineTransform worldToObj = new AffineTransform();
			//worldToObj.rotate(arg1, arg2);
			//worldToObj.translate(1, ty);
			
			Point2D.Double sel_point = new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY());
			
			boolean handle = false;
			//First check if there is a handle
			if (view.getHandle() != null) {
				//There is a handle so now check if it's been clicked
				handle = view.handleClicked(sel_point);
				
				handle_pressed = true;
				
			}
			
			if (!handle) {
				handle_pressed = false;
				
				/*
				 * TODO: Add inverse scroll and zoom transformations here to put the point into world coordinates
				 * then pass to the model				 
				 * 
				 */
				Point2D.Double trans_point = new Point2D.Double();
				AffineTransform viewToWorld = new AffineTransform();
				
				double unscale = 1 / get_zoom();
				viewToWorld.scale(unscale, unscale);
				//worldToObj.translate(get_scroll().getX(), get_scroll().getY());
				
				viewToWorld.transform(sel_point, trans_point);
				
				Shape clicked_shape = model.shapeClick(sel_point);
				if (clicked_shape != null){
					System.out.println(clicked_shape.toString());
					GUIFunctions.changeSelectedColor(clicked_shape.getColor());
					
				}
				else {
					model.setSelected(-1);
				}
			}
			else {
				//process handle rotation something, mouse drag will be most of this
				System.out.println("Handle pressed for current shape");
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (State == "LINE") {
			if (debug)
				System.out.println("RELEASED IN LINE" + arg0.getPoint().toString());
			
			//Probably need to do nothing here because the dragged will do it	
		}
		else if (State == "SQUARE") {
			
		}
		else if (State == "RECTANGLE") {
			
		}
		else if (State == "CIRCLE") {
			
		}
		else if (State == "ELLIPSE") {
			
		}
		else if (State == "TRIANGLE") {
			
		}
		else if (State == "SELECT") {
			selected = -1;
		}

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (State == "LINE") {
			if (debug) 
				System.out.println("MOUSE DRAGGED IN LINE");
			
			Line line = (Line) model.getShape(current_index);
			
			//As the mouse is dragged reset the end point of the line		
			line.setEnd(new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY()));
			
			model.deleteShapeSafe(current_index);
			current_index = model.addShape(line);
			
		}
		else if (State == "SQUARE") {
			
			Square square = (Square) model.getShape(current_index);
			
			//Do some maths to get size of square
			

			Point2D.Double p1 = square.getOriginalUpperLeft();
			Point2D.Double p2 = new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY());
			
			
			double width = Math.abs(square.getOriginalUpperLeft().getX() - p2.getX());
			double height = Math.abs(square.getOriginalUpperLeft().getY() - p2.getY());
			
			double size = Math.min(width, height) * 2;
			
			if (p1.getX() < p2.getX()) {
				//original upper left is more left than current location
				if (p1.getY() > p2.getY()) {
					//new upper left is the corner that is height about current upper left
					square.setUpperLeft(new Point2D.Double(p1.getX(), p1.getY() - size/2));
					
					square.setCenter(new Point2D.Double(p1.getX() + (size /4), p1.getY() -( size / 4)));
				}
				else {
					square.setCenter(new Point2D.Double(p1.getX() + (size /4), p1.getY() +( size / 4)));
				}
								
			}
			else {
				//current point is more left than old upper left
				if (p2.getY() < p1.getY()) {
					//New point is higher than old, it should become new upper left
					square.setUpperLeft(new Point2D.Double(p1.getX() - size/2, p1.getY() - size/2));
					square.setCenter(new Point2D.Double(p1.getX() - (size /4), p1.getY() -( size / 4)));
				}
				else {
					//Point height above new point is new upper left
					square.setUpperLeft(new Point2D.Double(p1.getX() - size / 2, p1.getY()));
					square.setCenter(new Point2D.Double(p1.getX() - (size /4), p1.getY() +( size / 4)));
				}
			}
			
			square.setSize(size * (1/get_zoom()));
			
			

			
			model.deleteShapeSafe(current_index);
			current_index = model.addShape(square);
			
		}
		else if (State == "RECTANGLE") {
			
			Rectangle rect = (Rectangle) model.getShape(current_index);
			
			//Do maths to get upper left, width and height based on original point and current location
			
			Point2D.Double p1 = rect.getOriginalUpperLeft();
			Point2D.Double p2 = new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY());
			
			double width = Math.abs(rect.getOriginalUpperLeft().getX() - p2.getX());
			double height = Math.abs(rect.getOriginalUpperLeft().getY() - p2.getY());
			
			if (p1.getX() < p2.getX()) {
				//original upper left is more left than current location
				if (p1.getY() > p2.getY()) {
					//new upper left is the corner that is height about current upper left
					rect.setUpperLeft(new Point2D.Double(p1.getX(), p2.getY()));
					
					rect.setCenter(new Point2D.Double(Math.abs(p1.getX() + width /2), Math.abs(p1.getY() - height / 2)));
				}
				else {
					rect.setCenter(new Point2D.Double(Math.abs(p1.getX() + width /2), Math.abs(p1.getY() + height / 2)));
				}
								
			}
			else {
				//current point is more left than old upper left
				if (p2.getY() < p1.getY()) {
					//New point is higher than old, it should become new upper left
					rect.setUpperLeft(p2);
					
					rect.setCenter(new Point2D.Double(Math.abs(p1.getX() - width /2),Math.abs(p1.getY() - height / 2)));
					
					
				}
				else {
					//Point height above new point is new upper left
					rect.setUpperLeft(new Point2D.Double(p2.getX(), p1.getY()));
					
					rect.setCenter(new Point2D.Double(Math.abs(p1.getX() - width /2), Math.abs(p1.getY() + height / 2)));
				}
			}
			
			rect.setWidth(width* (1/get_zoom()));
			rect.setHeight(height* (1/get_zoom()));
			
			model.deleteShapeSafe(current_index);
			current_index = model.addShape(rect);
			
		}
		else if (State == "CIRCLE") {
			
			Circle circle = (Circle) model.getShape(current_index);
			
			//Do some maths to get size of square in which the circle will be
			Point2D.Double p1 = circle.getOriginalUpperLeft();
			Point2D.Double p2 = new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY());
			
			
			double width = Math.abs(circle.getOriginalUpperLeft().getX() - p2.getX());
			double height = Math.abs(circle.getOriginalUpperLeft().getY() - p2.getY());
			
			double size = Math.min(width, height) * 2;
			
			//!IMPORTANT Need to fix center of circle calculations
			
			if (p1.getX() < p2.getX()) {
				//original upper left is more left than current location
				if (p1.getY() > p2.getY()) {
					//new upper left is the corner that is height about current upper left
					circle.setUpperLeft(new Point2D.Double(p1.getX(), p1.getY() - size/2));
					
					circle.setCenter(new Point2D.Double(Math.abs(p1.getX() + size /4), Math.abs(p1.getY() - size / 4)));
					if (debug)
						System.out.println(circle.getCenter().toString());
				}
				else {
					circle.setCenter(new Point2D.Double(Math.abs(p1.getX() + size /4), Math.abs(p1.getY() + size / 4)));
					
					if (debug)
						System.out.println(circle.getCenter().toString());
				}
								
			}
			else {
				//current point is more left than old upper left
				if (p2.getY() < p1.getY()) {
					//New point is higher than old, it should become new upper left
					circle.setUpperLeft(new Point2D.Double(p1.getX() - size/2, p1.getY() - size/2));
					
					circle.setCenter(new Point2D.Double(Math.abs(p1.getX() - size /4),Math.abs(p1.getY() - size / 4)));
					
					if (debug)
						System.out.println(circle.getCenter().toString());
				}
				else {
					//Point height above new point is new upper left
					circle.setUpperLeft(new Point2D.Double(p1.getX() - size / 2, p1.getY()));
					
					circle.setCenter(new Point2D.Double(Math.abs(p1.getX() - size /4), Math.abs(p1.getY() + size / 4)));
					
					if (debug)
						System.out.println(circle.getCenter().toString());
				}
			}
			
			circle.setSize(size* (1/get_zoom()));
			circle.setRadius(size / 4 * (1/get_zoom()));

			
			model.deleteShapeSafe(current_index);
			current_index = model.addShape(circle);
			
		}
		else if (State == "ELLIPSE") {
			

			Ellipse ell = (Ellipse) model.getShape(current_index);
			
			//Do maths to get upper left, width and height based on original point and current location
			Point2D.Double p1 = ell.getOriginalUpperLeft();
			Point2D.Double p2 = new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY());
			
			double width = Math.abs(ell.getOriginalUpperLeft().getX() - p2.getX());
			double height = Math.abs(ell.getOriginalUpperLeft().getY() - p2.getY());
			
			
			
			if (p1.getX() < p2.getX()) {
				//original upper left is more left than current location
				if (p1.getY() > p2.getY()) {
					//new upper left is the corner that is height about current upper left
					ell.setUpperLeft(new Point2D.Double(p1.getX(), p2.getY()));
					
					ell.setCenter(new Point2D.Double(Math.abs(p1.getX() + width /2), Math.abs(p1.getY() - height / 2)));
					if (debug)
						System.out.println(ell.getCenter().toString());
				}
				else {
					ell.setCenter(new Point2D.Double(Math.abs(p1.getX() + width /2), Math.abs(p1.getY() + height / 2)));
					
					if (debug)
						System.out.println(ell.getCenter().toString());
				}
								
			}
			else {
				//current point is more left than old upper left
				if (p2.getY() < p1.getY()) {
					//New point is higher than old, it should become new upper left
					ell.setUpperLeft(p2);
					
					ell.setCenter(new Point2D.Double(Math.abs(p1.getX() - width /2),Math.abs(p1.getY() - height / 2)));
					
					if (debug)
						System.out.println(ell.getCenter().toString());
				}
				else {
					//Point height above new point is new upper left
					ell.setUpperLeft(new Point2D.Double(p2.getX(), p1.getY()));
					
					ell.setCenter(new Point2D.Double(Math.abs(p1.getX() - width /2), Math.abs(p1.getY() + height / 2)));
					
					if (debug)
						System.out.println(ell.getCenter().toString());
				}
			}
			
			ell.setWidth(width * (1/get_zoom()));
			ell.setHeight(height * (1/get_zoom()));
			
			model.deleteShapeSafe(current_index);
			current_index = model.addShape(ell);
			
		}
		else if (State == "SELECT") {
			System.out.println("moving");
			if (model.getSelected() != -1) {
				//took out the point clicked storage cause I can use that for rotation as well
				int index = model.getSelected();
				if (selected != index) {
					point_clicked = new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY());
					selected= index;
				}
				
				//Distance changed from last point click and drag
				double offset_x = arg0.getPoint().getX() - point_clicked.getX();
				double offset_y = arg0.getPoint().getY() - point_clicked.getY();
				Shape shape = model.getShape(model.getSelected());
				
				if (!handle_pressed) {
					
					
					System.out.println("point Clicked: " + point_clicked.toString());
					System.out.println("current Point: " + arg0.getPoint().toString());
					
					//lines are different because both points need to change
					if (shape instanceof cs355.model.drawing.Line) {
						Line line = (Line) shape;
						
						//Need to move both points the same amount
						line.setCenter(new Point2D.Double(line.getCenter().getX() + offset_x, line.getCenter().getY() + offset_y));
						line.setEnd(new Point2D.Double(line.getEnd().getX() + offset_x, line.getEnd().getY() + offset_y));
	
						model.deleteShapeSafe(model.getSelected());
						model.insertShape(index, line);
					}
					else{
						
						shape.setCenter(new Point2D.Double(shape.getCenter().getX() + offset_x, shape.getCenter().getY() + offset_y));
						
						model.deleteShapeSafe(model.getSelected());
						model.insertShape(index, shape);
					}
					
					
				}
				else {
					//Handle of currently selected shape is being pressed
					//Need to figure out how to calculate the rotation here, change that in the current shape, and then in redraw it will draw it rotated
					
					if (shape instanceof cs355.model.drawing.Line) {
						Line line = (Line) shape;
						//handle moving line endpoints here
					}
					else {
						double angle = Math.atan2(arg0.getPoint().getY() - shape.getCenter().getY(), arg0.getPoint().getX() - shape.getCenter().getX()) + Math.PI / 2;
						System.out.println("The angle of rotation" + angle);
						
						shape.setRotation(angle);
						model.setSelected(selected); //This should be different but I'm not sure of a good way to make the view update after setting the rotation
					}
					
				}
				//Set the point again for the next move
				point_clicked = new Point2D.Double(arg0.getPoint().getX(), arg0.getPoint().getY());
			}
		}


	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void colorButtonHit(Color c) {
		
		if (debug)
			System.out.println("IN COLOR" + c.toString());
		current_color = c;
		
		if (State == "SELECT" && model.getSelected() != -1) {
			Shape shape = model.getShape(model.getSelected());
			shape.setColor(c);
			
			int index = model.getSelected();
			
			model.deleteShape(model.getSelected());
			
			model.insertShape(index, shape);
		}
		
		GUIFunctions.changeSelectedColor(current_color);

	}

	@Override
	public void lineButtonHit() {
		model.setSelected(-1);
		State = "LINE";
		if (debug)
			System.out.println("IN LINE");

	}

	@Override
	public void squareButtonHit() {
		model.setSelected(-1);
		State = "SQUARE";

	}

	@Override
	public void rectangleButtonHit() {
		model.setSelected(-1);
		State = "RECTANGLE";

	}

	@Override
	public void circleButtonHit() {
		model.setSelected(-1);
		State = "CIRCLE";

	}

	@Override
	public void ellipseButtonHit() {
		model.setSelected(-1);
		State = "ELLIPSE";

	}

	@Override
	public void triangleButtonHit() {
		model.setSelected(-1);
		State = "TRIANGLE";

	}

	@Override
	public void selectButtonHit() {
		State = "SELECT";

	}
	
	private int get_knob_divisor() {
		switch (zoom_level) {
		case 0: return 1;
		case 1: return 2;
		case 2: return 4;
		case 3: return 6;
		case 4: return 8;
		default: return 1;
		}
	}

	@Override
	public void zoomInButtonHit() {
		if (zoom_level != 4) {
			zoom_level += 1;
			
			int size = (int) ( (512 * (1/ get_zoom() ))/ get_knob_divisor());
			
			System.out.println("Knob size: " + size);			
			
			init();
			
			model.setSelected(selected);
		}
		System.out.println("Zoom level: " + zoom_level);

	}

	@Override
	public void zoomOutButtonHit() {
		if (zoom_level != 0) {
			zoom_level -= 1;
			
			GUIFunctions.setZoomText(get_zoom());
			
			System.out.println("knob divisor: " + get_knob_divisor());
			
			
			
			int size = (int) ( (512 * (1/ get_zoom() ))/ get_knob_divisor());
			
			System.out.println("Knob size: " + size);
			
			init();
			
			model.setSelected(selected);
		}
		System.out.println("Zoom level: " + zoom_level);

	}

	@Override
	public void hScrollbarChanged(int value) {
		System.out.println("scroll horizontal: " + value);
		h_scroll = value;
		model.setSelected(selected);

	}

	@Override
	public void vScrollbarChanged(int value) {
		System.out.println("scroll vertical: " + value);
		v_scroll = value;
		model.setSelected(selected);

	}

	@Override
	public void openScene(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toggle3DModelDisplay() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(Iterator<Integer> iterator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void openImage(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveImage(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toggleBackgroundDisplay() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveDrawing(File file) {
		if (debug)
			System.out.println(file.toString());
		model.save(file);

	}

	@Override
	public void openDrawing(File file) {
		model.open(file);

	}

	@Override
	public void doDeleteShape() {
		if (model.getSelected() != -1) {
			model.deleteShape(model.getSelected());
		}

	}

	@Override
	public void doEdgeDetection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSharpen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doMedianBlur() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doUniformBlur() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doGrayscale() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doChangeContrast(int contrastAmountNum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doChangeBrightness(int brightnessAmountNum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doMoveForward() {
		if (model.getSelected() != -1) {
			model.moveForward(model.getSelected());
		}

	}

	@Override
	public void doMoveBackward() {
		if (model.getSelected() != -1) {
			model.moveBackward(model.getSelected());
		}

	}

	@Override
	public void doSendToFront() {
		if (model.getSelected() != -1) {
			model.moveToFront(model.getSelected());
		}

	}

	@Override
	public void doSendtoBack() {
		if (model.getSelected() != -1) {
			model.movetoBack(model.getSelected());
		}

	}

	public void addView(MyView my_view) {
		view = my_view;
		
	}
	
	public double get_zoom() {
		return zoom_scale[zoom_level];
	}
	
	public Point2D.Double get_scroll() {
		return new Point2D.Double((double) h_scroll, (double) v_scroll);
	}
	
	public void init() {
		int knob_size = 2048;
		if (zoom_level == 1) {
			System.out.println("1");
			knob_size = 1024;
		}
		else if (zoom_level == 2) {
			System.out.println("2");
			knob_size = 512;
		}
		else if (zoom_level == 3) {
			System.out.println("3");
			knob_size = 256;
		}
		else if (zoom_level == 4) {
			System.out.println("4");
			knob_size = 128;
		}

		int scroll_pos_x = (int)get_scroll().getX();
		int scroll_pos_y = (int)get_scroll().getY();
		if (knob_size == 2048)
		{
			scroll_pos_x = 0;
			scroll_pos_y = 0;
		}
		GUIFunctions.setHScrollBarKnob(knob_size);
		GUIFunctions.setHScrollBarMax((int) ( 2048));
		GUIFunctions.setHScrollBarMin(0);
		GUIFunctions.setHScrollBarPosit(scroll_pos_x);
		
		GUIFunctions.setVScrollBarKnob(knob_size);
		GUIFunctions.setVScrollBarMax((int) (2048));
		GUIFunctions.setVScrollBarMin(0);
		GUIFunctions.setVScrollBarPosit(scroll_pos_y);
		
		GUIFunctions.setZoomText(get_zoom());
	}

}
