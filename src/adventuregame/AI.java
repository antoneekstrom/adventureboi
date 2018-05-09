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
	
	int speed = 5;
	String direction = "right";
	boolean move = false;

	public AI(int g) {
		q = new ArrayList<String>();
		gravity = g;
		ground = new Rectangle();
	}

	public void update(Rectangle r) {
		this.r = r;
		nr = r;
		
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
	
	public void passGround(Rectangle g) {
		if (g.getMinY() > nr.getWidth()) {
			ground = g;
		}
	}

	public Rectangle returnPos() {		
		return r;
	}
	
	public String getQ() {
		String q = "";
		for (String s : this.q) {
			q = q + "," + s;
		}
		return q;
	}
	
	public void pathfind() {
		if (ground.getMinX() >= nr.getMinX()) {
			direction("right");
		}
		if (ground.getMaxX() <= nr.getMaxX()) {
			direction("left");
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
