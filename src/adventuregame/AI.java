package adventuregame;

import java.awt.Rectangle;

public class AI {

	private Rectangle r;
	private Rectangle nr;
	private Rectangle ground;
	private Rectangle obstacle;
	private RectangleObject ro;
	
	String[] excluded = new String[] {
		"fire",
		"spike",
		"pickup",
	};
	
	private double jumpchance = 0.5;
	private double jumpforce = 120;
	private int randomtime = 100;
	
	private double turnchance = 0.4;
	private int speed = 5;
	
	private boolean randomalt = false;
	
	private boolean ready = true;
	private String direction = "right";
	private boolean move = false;
	
	private Counter rcounter = new Counter(randomtime, 10, "randomizer");
	private Counter c = new Counter(1000, 2, "start");

	public AI() {
		obstacle = new Rectangle();
		ground = new Rectangle();
	}
	
	public void setSpeed(int s) {
		speed = s;
	}

	public void turnChance(double d) {
		turnchance = d;
	}
	
	public void jumpForce(double f) {
		jumpforce = f;
	}
	
	public void randomTime(int i) {
		randomtime = i;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public Counter rcounter() {
		return rcounter;
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
		
		if (move) {
			move();
		}
		randomizer();
		pathfind();
	}
	
	public void setMove(boolean b) {
		move = b;
	}
	
	public boolean getMove() {
		return move;
	}
	
	public Rectangle getNewRect() {
		return nr;
	}
	
	public void passCollision(RectangleObject ro) {
		this.ro = ro;
		Rectangle g = ro.getObjectRect();
		if (nr != null && g.getMinY() > nr.getMinY()) {
			ground = g;
		}
		else {
			if (!isExcluded(ro)) {
				obstacle = g;
			}
		}
	}
	
	public void randomizer() {
		if (randomalt) {
			randomalt = false;
		}
		else {
			randomalt = true;
		}
		
		if (rcounter.isDone()) {
			rcounter.reset();
			rcounter.start();
			
			if (Math.random() < jumpchance) {
				jump();
			}
			if (Math.random() < turnchance && randomalt) {
				direction("switch");
			}
		}
		else if (!rcounter.hasStarted()) {
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
		avoidBlockade();
	}
	
	public void avoidBlockade() {
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
	
	/** Jumps if random number 0-1 is lower than parameter */
	public void jumpChance(double d) {
		jumpchance = d;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void start() {
		
		if (Math.random() < 0.5) {
			direction("left");
		}
		else {
			direction("right");
		}
		move = true;
		move();
	}
	
	public void passObject(RectangleObject o) {
		ro = o;
	}

	/** ai jump, true: add to queue, false: jump */
	public void jump() {
		ro.applyForceY(jumpforce);
	}
}






