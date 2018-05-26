package adventuregame;

import java.awt.Rectangle;

import objects.NewObject;

public class AI {

	private Rectangle ground;
	private Rectangle obstacle;
	private NewObject object;
	
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

	public void update(NewObject o) {
		object = o;
		
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

	public NewObject returnObject() {
		return object;
	}
	
	public void setMove(boolean b) {
		move = b;
	}
	
	public boolean getMove() {
		return move;
	}
	
	public void passCollision(NewObject o) {
		Rectangle g = o.get();
		if (g.getMinY() > object.get().getMinY()) {
			ground = g;
		}
		else {
			if (!object.isPassThrough()) {
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
	
	public void pathfind() {
		//switch direction
		if (object.get().intersects(ground) || object.get().intersects(obstacle)) {
			avoidBlockade();
		}
	}
	
	public void avoidBlockade() {
		if (ground.getMinX() >= object.get().getMinX()) {
			direction("right");
		}
		if (ground.getMaxX() <= object.get().getMaxX()) {
			direction("left");
		}
		if (obstacle.intersects(object.get()) && !ground.equals(obstacle)) {
			if (obstacle.getMaxX() > object.get().getMinX()) {
				direction("switch");
			}
		}
	}

	public void move() {
		if (direction.equals("right")) {
			object.get().x = (int) (object.get().getX() + speed);
		}
		if (direction.equals("left")) {
			object.get().x = (int) (object.get().getX() - speed);
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

	public String getLogic() {
		String s = "";
		
		s = s + "Ground equals Obstacle:" + ground.equals(obstacle);
		
		return s;
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
	
	public void passObject(NewObject o) {
		object = o;
	}

	/** ai jump, true: add to queue, false: jump */
	public void jump() {
		object.applyForce(0.0, jumpforce);
	}
}






