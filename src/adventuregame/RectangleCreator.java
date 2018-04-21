package adventuregame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import worlds.ListWorld;
import worlds.World;

public class RectangleCreator {

	public Point p1, p2;
	public ListWorld world;
	public Color color;
	
	public RectangleCreator(ListWorld w) {
		world = w;
		color = Color.ORANGE;
		p1 = new Point();
		p2 = new Point();
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public void addp1(Point p) {
		p1 = world.mouse;
		p1.x -= 800;
		p1.x += world.c.getD2c();
		p1.y -= 200;
	}
	
	public void addp2(Point p) {
		p2 = world.mouse;
		p2.x -= 800;
		p2.y -= 200;
		p2.x += world.c.getD2c();
	}
	
	public void create() {
		Rectangle r = new Rectangle(p1);		
		r.add(p2);
		world.addRect(new Point(r.x, r.y), new Dimension(r.width, r.height), color);
	}
	
}
