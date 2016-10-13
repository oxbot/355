package cs355.model.drawing;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyModel extends CS355Drawing{
	
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	int selected = -1; //-1 means nothing is selected

	@Override
	public Shape getShape(int index) {
		return shapes.get(index);
	}
	
	public void insertShape(int index, Shape s) {
		shapes.add(index, s);
		setSelected(index);
		//this.setChanged();
		//this.notifyObservers();
	}

	@Override
	public int addShape(Shape s) {
		shapes.add(s);
		this.setChanged();
		this.notifyObservers();
		return shapes.size()-1;
	}

	@Override
	public void deleteShape(int index) {
		shapes.remove(index);
		setSelected(-1);
		
	}
	public void deleteShapeSafe(int index) {
		shapes.remove(index);
		
	}

	@Override
	public void movetoBack(int index) {
		if (index != 0) {
			Collections.swap(shapes, index, 0);
			setSelected(0);
		}
		
	}

	@Override
	public void moveToFront(int index) {
		if (index != shapes.size()-1) {
			Collections.swap(shapes, index, shapes.size()-1);
			setSelected(shapes.size()-1);
		}
		
	}

	@Override
	public void moveBackward(int index) {
		if (index != 0) {
			Collections.swap(shapes, index, index-1);
			setSelected(index-1);
		}
		
	}

	@Override
	public void moveForward(int index) {
		if (index != shapes.size()-1) {
			Collections.swap(shapes, index, index+1);
			setSelected(index+1);
		}
		
	}

	@Override
	public List<Shape> getShapes() {
		
		return shapes;
	}

	@Override
	public List<Shape> getShapesReversed() {
		
		//Create new array, copy shape data, reverse it, return that
		ArrayList<Shape> copy = new ArrayList<Shape>();
		Collections.copy(copy, shapes);
		Collections.reverse(copy);
		
		return copy;
	}

	@Override
	public void setShapes(List<Shape> shapes) {
		this.shapes = (ArrayList<Shape>) shapes;
		this.setChanged();
		this.notifyObservers();
		
	}
	
	public Shape shapeClick(Point2D.Double clicked) {
		Shape cur_shape = null;
		int tolerance = 4;
		
		for (int i = shapes.size() - 1; i >= 0;i--) {
			AffineTransform worldToObj = new AffineTransform();
			//worldToObj.rotate();
			
			cur_shape = shapes.get(i);
			worldToObj.rotate(- cur_shape.getRotation());
			worldToObj.translate(-cur_shape.getCenter().getX(), -cur_shape.getCenter().getY());
			
			Point2D.Double objCoord = new Point2D.Double();
			worldToObj.transform(clicked, objCoord);
			
			if (cur_shape.pointInShape(objCoord, tolerance)) {
				setSelected(i);
				
				return cur_shape;
			}
		}
		
		
		return null;
	}

	public int getSelected() {
		return selected;
	}

	public void setSelected(int selected) {
		this.selected = selected;
		this.setChanged();
		this.notifyObservers();
	}

}
