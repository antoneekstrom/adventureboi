package adventuregame;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

public class Camera {
	
	private ArrayList<Object> objects;
	public int VIEWPORTSIZE_X;
	private Object o;
	private Point cPos;
	public int xdistance = 0;
	public int ydistance = 0;
	public double d2c = 0;
	
	public Camera(Dimension dim) {
		objects = new ArrayList<Object>();
		cPos = new Point();
		cPos.setLocation(800, 400);
	}
	
	public void add(Object o) {
		objects.add(o);
	}
	
	public void run(Player p) {
		
		//distance to center of map
		if (p.getX() < 0) {
			d2c = p.getX();
		} else {
			d2c = p.getX();
		}
		
		p.setCx((int) cPos.getX());
		p.setCy((int) cPos.getY());
		
		for (int i = 0; i < objects.size(); i++) {
			o = objects.get(i);
			
			if (p.getX() > o.getX()) {
				
				xdistance = p.getX() - o.getX();
				o.setCx((int) (cPos.getX() - xdistance));
				
			} else if (p.getX() < o.getX()) {
				
				xdistance = o.getX() - p.getX();
				o.setCx((int) (cPos.getX() + xdistance));
			}/*
			if (p.getY() > o.getY()) {
				
				ydistance = p.getY() - o.getY();
				o.setCy((int) (cPos.getY() - ydistance));
				
			} else if (p.getY() < o.getY()) {
				
				ydistance = o.getY() - p.getY();
				o.setCy((int) (cPos.getY() + ydistance));
			}*/
			o.setCy(o.getY() + 200);
			p.setCy(p.getY() + 200);
		}
	}
	
	public double getD2c() {
		return d2c;
	}
}
