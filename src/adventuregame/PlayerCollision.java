package adventuregame;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class PlayerCollision {
	
	public List<Rectangle> collisions;
	public int defaultMOVSPEED = 0;
	public String side = "none";
	public double dx, dy;
	
	public PlayerCollision(Player p) {
		collisions = new ArrayList<Rectangle>();
		defaultMOVSPEED = p.MOVSPEED;
		
	}
	
	public List<Rectangle> getList() {
		return collisions;
	}

	public void pRun(Player p) {
		
		p.onground = true;
		
		for (int i = 0; i < collisions.size(); i++) {
			Rectangle pr = p.getObjectRect();
			Rectangle r = collisions.get(i);
			dx = 0;
			dy = 0;
			
			if (pr.intersects(r)) {
				if (pr.getCenterX() < r.getCenterX()) {

					side = "left";
					dx = pr.getMaxX() - r.getX();

				} else if (pr.getCenterX() > r.getCenterX()) {

					side = "right";
					dx = r.getMaxX() - pr.getX();

				}
				if (pr.getMaxY() > r.getMinY()) {
					//down
					p.onground = true;
					dy = pr.getMaxY() - r.getMinY();
				}
				if (dx < dy) {
					if (side == "left") {
						p.setX((int) (p.getX() - dx));
					}
					if (side == "right") {
						p.setX((int) (p.getX() + dx));
					}
				} else if (dy < dx) {
					p.setY((int) (p.getY() - dy));
					p.onground = false;
				} 
			}
		}
	}

	public void add(Rectangle r) {
		collisions.add(r);
	}
	
	public void updateCollision() {
		
	}
	
	
}
