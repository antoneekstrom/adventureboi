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
	
}
