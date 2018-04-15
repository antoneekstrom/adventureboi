package adventuregame;

import java.awt.Dimension;
import java.awt.Point;

public class GObj {
	
	public RectangleObject obj;
	public Point location;
	public Dimension size;
	
	public GObj(RectangleObject o, Point p, Dimension d) {
		obj = o;
		location = p;
		size = d;
		obj.setLocation((int) location.getX(), (int) location.getY());
		obj.setSize((int) size.getWidth(), (int) size.getWidth());
	}
	
	public RectangleObject getObj() {
		return obj;
	}
	
	public void setLocation(Point p) {
		location = p;
	}
	
	public void run() {
		getObj().setLocation((int) location.getX(), (int) location.getY());
		getObj().update();
	}
	
}


