package adventuregame;

import java.awt.Rectangle;

public class Position {

	public static Rectangle centerX(Rectangle p, Rectangle r) {
		r.setLocation( ( (p.width / 2) - (r.width / 2) ), (r.y) );
		return r;
	}
	
}
