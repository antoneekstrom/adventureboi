package adventuregame;

import java.awt.Graphics;
import java.awt.Point;

import worlds.World;

public class Line extends RectangleObject {

	//public int x1, x2, y1, y2;
	public Point p1, p2;
	
	public Line(Main f, World w) {
		super(f, w);
	}

	/*
	public void locations(int nx1, int ny1, int nx2, int ny2) {
		x1 = nx1;
		x2 = nx2;
		y1 = ny1;
		y2 = ny2;
	}
	*/
	
	public void setPoints(Point np1, Point np2) {
		p1 = np1;
		p2 = np2;
	}
	
	public void update() {
	}
	
	public void paint(Graphics g) {
		g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
	}
	
}
