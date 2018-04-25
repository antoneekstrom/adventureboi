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
				//if player is left of object
				if (pr.getCenterX() < r.getCenterX()) {

					side = "left";
					dx = pr.getMaxX() - r.getX();
					
				//if player is right of object	
				} else if (pr.getCenterX() > r.getCenterX()) {

					side = "right";
					dx = r.getMaxX() - pr.getX();

				}
				
				//if player is above object
				if (pr.getMaxY() > r.getMinY() && !(pr.getMaxY() > r.getMaxY())) {
					p.onground = true;
					dy = pr.getMaxY() - r.getMinY();
					
				//if player is under object
				}
				if (pr.getMinY() < r.getMaxY() && !(pr.getMinY() < r.getMinY())) {
					
					//side = "under";
					dy = r.getMaxY() - pr.getMinY();
				}
				
				//moves player out of object
				if (dx < dy) {
					if (side == "left") {
						p.setX((int) (p.getX() - dx));
					}
					if (side == "right") {
						p.setX((int) (p.getX() + dx));
					}
				}
				if (dy < dx) {
					if (side == "under") {
						p.setY((int) (p.getY() + dy));
					}
					p.setY((int) (p.getY() - dy));
					p.onground = false;
				} 
			}
		}
	}

	public void add(Rectangle r) {
		collisions.add(r);
	}
}
