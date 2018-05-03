package adventuregame;

import java.awt.Graphics;
import java.awt.Rectangle;

import worlds.World;

public class RectangleObject extends Object {
	
	boolean spike = false;

	public RectangleObject(Main f, World w) {
		super(f, w);
	}
	
	public void spike() {
		spike = true;
	}

	public void paint(Graphics g) {
		g.setColor(getCOLOR());
		if (spike == false) {
			g.fillRect(getCx(), getCy(), getWidth(), getHeight());			
		} else {
			//g.drawImage(x, y, w, h, null);
		}
	}
}
