package adventuregame;

import java.awt.Graphics;
import java.awt.Rectangle;

import worlds.World;

public class RectangleObject extends Object {

	public RectangleObject(Main f, World w) {
		super(f, w);
	}

	public void paint(Graphics g) {
		g.setColor(getCOLOR());
		g.fillRect(getCx(), getCy(), getWidth(), getHeight());			
	}
}
