package adventuregame;

import java.awt.Rectangle;
import java.util.ArrayList;

public class ObjectCollision {

	public int defaultMOVSPEED = 0;
	public String side = "none";
	public double dx, dy;
	ArrayList<RectangleObject> collisions;
	
	public ObjectCollision(ArrayList<RectangleObject> rlist) {
		collisions = rlist;
	}
	
	public ArrayList<RectangleObject> getList() {
		return collisions;
	}
	
	public void updateList(ArrayList<RectangleObject> rlist) {
		collisions = rlist;
	}

	public void update() {
		
		for (int i = 0; i < collisions.size(); i++) {

			Rectangle r = collisions.get(i).getObjectRect();

			for (int k = 0; k < collisions.size(); k++) {

				Rectangle ro = collisions.get(k).getObjectRect();
				RectangleObject o = collisions.get(k);

				if (o.hasCollision()) {

					dx = 0;
					dy = 0;
					if (ro.intersects(r)) {
						//if player is left of object
						if (ro.getCenterX() < r.getCenterX()) {

							side = "left";
							dx = ro.getMaxX() - r.getX();

							//if player is right of object	
						} else if (ro.getCenterX() > r.getCenterX()) {

							side = "right";
							dx = r.getMaxX() - ro.getX();

						}

						//if player is above object
						if (ro.getMaxY() > r.getMinY() && !(ro.getMaxY() > r.getMaxY())) {
							dy = ro.getMaxY() - r.getMinY();

						}
						//if player is under object
						if (ro.getMinY() < r.getMaxY() && !(ro.getMinY() < r.getMinY())) {

							//side = "under";
							dy = r.getMaxY() - ro.getMinY();
						}

						//moves player out of object
						if (dx < dy) {
							if (side == "left") {
								o.setX((int) (o.getX() - dx));
							}
							if (side == "right") {
								o.setX((int) (o.getX() + dx));
							}
						}
						if (dy < dx) {
							if (ro.getMinY() < r.getMaxY() && !(ro.getMinY() < r.getMinY())) {
								o.setY((int) (o.getY() + dy));
								side = "under";
							}
							o.setY((int) (o.getY() - dy));
						} 
					}
				}
			}
		}
	}
}
