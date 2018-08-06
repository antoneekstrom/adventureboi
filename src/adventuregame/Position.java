package adventuregame;

import java.awt.Point;
import java.awt.Rectangle;

public class Position {

	public static Rectangle centerX(Rectangle p, Rectangle r) {
		r.setLocation( ( (p.width / 2) - (r.width / 2) ), (r.y) );
		return r;
	}

	public static Rectangle centerY(Rectangle p, Rectangle r) {
		r.setLocation( r.x, p.y - (r.height / 2) + (p.height / 2) );
		return r;
	}

	public static Rectangle placeBetweenX(int x1, int x2, Rectangle r) {
		r.x = x1 + ( (x2 - x1) / 2) - (r.width / 2);
		return r;
	}

	public static Rectangle centerOnPoint(Point center, Rectangle r) {

		r.setLocation(center.x - (r.width / 2), center.y - (r.height / 2));

		return r;
	}

	public static int distance(Point p1, Point p2) {
		int d = (int) Point.distance(p1.x, p1.y, p2.x, p2.y);
		return d; 
	}

	public static int distanceX(Point p1, Point p2) {
		int d = 0;
		if (p1.x > p2.x) {
			d = p1.x - p2.x;
		}
		else {
			d = p2.x - p1.x;
		}
		return d; 
	}

	public static int distance(int p1, int p2) {
		if (p1 > p2) {
			return p1-p2;
		}
		else {
			return p2-p1;
		}
	}

	public static int distanceY(Point p1, Point p2) {
		int d = 0;
		if (p1.y > p2.y) {
			d = p1.y - p2.y;
		}
		else {
			d = p2.y - p1.y;
		}
		return d; 
	}

	public static Point CenterPos(Rectangle r) {
		return new Point((int) r.getCenterX(), (int) r.getCenterY());
	}
	
}
