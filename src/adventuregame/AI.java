package adventuregame;

import java.awt.Rectangle;
import java.util.ArrayList;

public class AI {

	Rectangle r;
	Rectangle nr;
	ArrayList<String> q;
	int gravity;
	Rectangle prevground;
	Rectangle ground;
	Rectangle obstacle;
	String lastobstacle = "";
	
	String[] excluded = new String[] {
		"fire",
		"spike",
		"pickup",
	};
	
	boolean ready = true;
	
	Counter c = new Counter(1000, 2, "start");
	
	int speed = 5;
	int jumptrigger = 100;
	String direction = "right";
	boolean move = false;

	public AI(int g) {
		q = new ArrayList<String>();
		gravity = g;
		obstacle = new Rectangle();
		ground = new Rectangle();
	}

	public void update(Rectangle r) {
		this.r = r;
		nr = r;
		
		if (ready) {
			if (!c.hasStarted()) {
				c.start();
			}
			if (c.done) {
				start();
				ready = false;
			}
		}
		
		if (q.size() > 0) {
			if (q.get(0).equals("jump")) {
				q.remove(0);
				jump(false);
			}
		}
		
		if (move) {
			move();
		}
		pathfind();
	}
	
	public void passCollision(RectangleObject ro) {
		Rectangle g = ro.getObjectRect();
		if (nr != null && g.getMinY() > nr.getMinY()) {
			ground = g;
		}
		else {
			if (!isExcluded(ro)) {
				obstacle = g;
				lastobstacle = ro.type;
			}
		}
	}

	public Rectangle returnPos() {		
		return r;
	}
	
	public String getLogic() {
		String s = "";
		
		s = s + "Ground equals Obstacle:" + ground.equals(obstacle);
		
		return s;
	}
	
	public String getQ() {
		String q = "";
		for (String s : this.q) {
			q = q + "," + s;
		}
		return q;
	}
	
	public boolean isExcluded(RectangleObject ro) {
		boolean b = false;
		for (int i = 0; i < excluded.length; i++) {
			if (ro.type.equals(excluded[i]) || ro.subtype.equals(excluded[i])) {
				b = true;
			}
		}
		return b;
	}
	
	public void pathfind() {
		//switch direction
		if (ground.getMinX() >= nr.getMinX()) {
			direction("right");
		}
		if (ground.getMaxX() <= nr.getMaxX()) {
			direction("left");
		}
		if (obstacle.intersects(nr) && !ground.equals(obstacle)) {
			if (obstacle.getMaxX() > nr.getMinX()) {
				direction("switch");
			}
		}
	}

	public void move() {
		if (direction.equals("right")) {
			nr.x = (int) (nr.getX() + speed);
		}
		if (direction.equals("left")) {
			nr.x = (int) (nr.getX() - speed);
		}
	}
	
	public void direction(String d) {
		if (d.equals("switch")) {
			if (direction.equals("right")) {
				direction = "left";
			}
			else if (direction.equals("left")) {
				direction = "right";
			}
		}
		else {
			direction = d;
		}
	}
	
	public void start() {
		
		System.out.println("spikeboi has started");
		if (Math.random() < 0.5) {
			direction("left");
		}
		else {
			direction("right");
		}
		move = true;
		move();
	}

	/** ai jump, true: add to queue, false: jump */
	public void jump(boolean b) {
		int jduration = 25;
		double jspeed = 15;
		
		
		if (b) {
			for (int i = 0; i < jduration; i++) {
				q.add("jump");
			}
		}
		else {
			nr.y = (int) (nr.y - (jspeed + gravity));
		}
	}


}
