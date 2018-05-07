package adventuregame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import worlds.ListWorld;
import worlds.World;

public class RectangleCreator {

	public Point p1, p2;
	public ListWorld world;
	public Color color;
	String mode = "rectangle";
	BufferedImage spike;
	
	public RectangleCreator(ListWorld w) {
		world = w;
		color = Color.ORANGE;
		p1 = new Point();
		p2 = new Point();
		
		try {
			spike = ImageIO.read(new File("spike.png"));
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public void addp1(Point p) {
		p1 = p;
		p1.x -= 800;
		p1.x += world.c.getD2c();
		p1.y -= 200;
	}
	
	public void addp2(Point p) {
		p2 = p;
		p2.x -= 800;
		p2.y -= 200;
		p2.x += world.c.getD2c();
	}
	
	public void create() {
		Rectangle r = new Rectangle(p1);		
		if (mode == "rectangle") {
			r.add(p2);
			world.addRect(new Point(r.x, r.y), new Dimension(r.width, r.height), color);
		} else if (mode == "spike") {
			
			RectangleObject ro = new RectangleObject(world.frame, world);
			ro.setLocation((int)p1.getX(), (int)p1.getY());
			ro.setSize(100, 100);
			ro.type = "spike";
			ro.sprite(spike);
			world.addRo(ro);
		}
	}
	
}
