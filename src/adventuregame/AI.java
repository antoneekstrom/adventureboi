package adventuregame;

import java.awt.Rectangle;
import java.util.ArrayList;

public class AI {

	private Rectangle r;
	private Rectangle nr;
	private ArrayList<String> q;
	private int gravity;
	private Rectangle prevground;
	private Rectangle ground;
	private Rectangle obstacle;
	private String lastobstacle = "";
	
	private Counter rcounter = new Counter(1000, 5000, "randomizer");
	
	String[] excluded = new String[] {
		"fire",
		"spike",
		"pickup",
	};
	
	private boolean ready = true;
	
	private Counter c = new Counter(1000, 2, "start");
	
	private int speed = 5;
	private int jumptrigger = 100;
	private String direction = "right";
	private boolean move = false;

	public AI(int g) {
		q = new ArrayList<String>();
		gravity = g;
		obstacle = new Rectangle();
		ground = new Rectangle();
	}
	
	public void setSpeed(int s) {
		speed = s;
	}
	
	public int getSpeed() {
		return speed;
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
		randomizer();
		pathfind();
	}
	
	public boolean getMove() {
		return move;
	}
	
	public Rectangle getNewRect() {
		return nr;
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
	
	public void randomizer() {
		double r = Math.random();
		if (rcounter.isDone()) {
			if (r < 0.5) {
				jump(false);
			}
		}
		else {
			rcounter.start();
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
	
	public String getDirection() {
		return direction;
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
