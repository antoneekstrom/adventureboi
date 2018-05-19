package adventuregame;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;

public class Camera {
	
	private ArrayList<Object> objects;
	private Object o;
	private Point cPos;
	public int xdistance = 0;
	public int ydistance = 0;
	public double d2c = 0;
	
	private Point mouse;
	private int cameraspeed = 1;
	
	private int baseyoffset = 200;
	private int yoffset = baseyoffset;
	private int cameraystandard = 0;
	
	private int cposy = 600;
	private int cposx = 800;
	
	private boolean yfollow = false;
	
	public Camera(Dimension dim) {
		objects = new ArrayList<Object>();
		cPos = new Point();
		cPos.setLocation(cposx, cposy);
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
			
			//x-axis
			if (p.getX() > o.getX()) {
				
				xdistance = p.getX() - o.getX();
				o.setCx((int) (cPos.getX() - xdistance));
				
			} else if (p.getX() < o.getX()) {

				xdistance = o.getX() - p.getX();
				o.setCx((int) (cPos.getX() + xdistance));
			}

			//y-axis
			if (p.getY() > o.getY()) {

				ydistance = p.getY() - o.getY();
				o.setCy((int) (cPos.getY() - ydistance));

			} else if (p.getY() < o.getY()) {

				ydistance = o.getY() - p.getY();
				o.setCy((int) (cPos.getY() + ydistance));
			}
			yFollow(p);
		}
		yBaseline(p);
		setMouse(p);
	}

	public void yFollow(Player p) {
		// Camera follows player on y-axis if enabled / true
		o.setCy(o.getY() + yoffset);
		p.setCy(p.getY() + yoffset);

		if (yfollow) {
			//switches y-axis follow
			if (p.getCy() < cameraystandard) {
				if (yoffset + cameraspeed <= cameraystandard) {
					yoffset += cameraspeed;
				}
			}
			else {
				if (yoffset - cameraspeed >= 0) {
					yoffset -= cameraspeed;
				}
			}
		}
	}
	
	public void yBaseline(Player p) {
		cameraystandard = ydistance;
	}
	
	public void setMouse(Player p) {
		mouse = MouseInfo.getPointerInfo().getLocation();
		Point relmouse = new Point(mouse.x, mouse.y - (p.getCy() - p.getY() - (int) (baseyoffset)) );
		GlobalData.setRelativeMouse(relmouse);
	}

	public int getYoffset() {
		return yoffset;
	}
	
	public double getD2c() {
		return d2c;
	}
}
