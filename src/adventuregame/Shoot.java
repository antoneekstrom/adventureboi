package adventuregame;

import java.awt.Graphics;
import java.awt.Point;

import worlds.World;

public class Shoot {
	
	private Line l1;
	private Point pp = new Point();
	private Point mp = new Point();
	
	public Shoot() {
	}
	
	public void update(World w, Point m, Player p) {
		pp.setLocation(p.getCx() + p.getWidth(), p.getCy() + p.getHeight() / 2);
		mp.setLocation(w.getMousePosition());
		l1.p1 = pp; 
		l1.p2 = mp;
	}
	
	public void paintComponent(Graphics g) {
		l1.paint(g);
	}
}
