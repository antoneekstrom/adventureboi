package adventuregame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import worlds.World;

public class Player extends Object {

	private Point spawnpoint;
	
	//images and animation
	private BufferedImage playeractive;
	private BufferedImage playerstill;
	private BufferedImage playerright;
	private BufferedImage playerfall;
	private BufferedImage playerleft;
	
	private double aCounter = 0;
	private double ANIMSPEED = 3;
	public boolean enabled = false;
	
	//movement
	public String direction = "none";
	public float CALCMOV = 1;
	public int MOVSPEED = 7;
	public float MOVACC = 1.3f;
	public float BASEMOV = 1;
	public float MOVMOD = 1.05f;
	public float MOVMAX = 4;
	
	//jump
	public boolean jump = false;
	
	double jcalculated;
	double JACC = 1f;
	double JSPEED = 1.7;
	public int JFUEL = 0;
	public int JFUELMAX = 14;
	public boolean onground = false;
	
	
	public Player(Main f, World w) {
		super(f, w);
		spawnpoint = new Point();
		try {
			playerstill = ImageIO.read(new File("manboji.png"));
			playerright = ImageIO.read(new File("manboji2.png"));
			playerfall = ImageIO.read(new File("manboji5.png"));
			playerleft = ImageIO.read(new File("manboji3.png"));
		} catch (IOException e) {e.printStackTrace();}
		
	}
	
	public void move() {
		if (direction == "right") {
			if (MOVACC * MOVMOD <= MOVMAX) {
				MOVACC *= MOVMOD;
			}
			CALCMOV = MOVACC * MOVSPEED;
			setX((int) (getX() + CALCMOV));

		} else if (direction == "left") {
			if (MOVACC * MOVMOD <= MOVMAX) {
				MOVACC *= MOVMOD;
			}
			CALCMOV = MOVACC * MOVSPEED;
			setX((int) (getX() - CALCMOV));

		} else if (direction == "none") {CALCMOV = BASEMOV;}
		else if (direction == "down") {}
		else {System.out.println("Invalid direction");}
	}

	public void jump() {
		jcalculated = getGRAVITY() * JSPEED * JACC;
		if (jump == true && JFUEL != 0) {
			JFUEL--;
			setY((int) (getY() - jcalculated));
		}
		if (!onground == true) {
			JFUEL = JFUELMAX;
			onground = true;
		} else {
			onground = false;
		}
	}
	
	public void isEnabled(boolean b) {
		enabled = b;
		
	}
	
	public void setSize(int w, int h) {
		setWidth(w);
		setHeight(h);
		getObjectRect().setSize(w, h + 55);
	}
	
	public void die() {
		setX(0);
		setY(0);
	}
	
	public void setLocation(int nx, int ny) {
		setX(nx);
		setY(ny);
		getObjectRect().setLocation(nx, ny);
	}
	
	public void update() {
		setLocation(getX(), getY());
		updateObjectRect();
		gravity();
		calculateYVelocity();
		animation();
		move();
		jump();
		voidCheck();
	}
	
	public void voidCheck() {
		if (getY() > 3000) {
			setLocation(0, 0);
		}
	}
	
	public void animation() {
		//moving right
		if (direction == "right") {
			aCounter += ANIMSPEED * (CALCMOV / 4);
			if (aCounter < 10) {
				playeractive = playerright;
			}
			else if (aCounter > 10 && aCounter < 50) {
				playeractive = playerleft;
			}
			else if (aCounter > 50 && aCounter < 100) {
				playeractive = playerright;
			}
			else if (aCounter > 100) {
				aCounter = 0;
			}

		}
		//standing still
		if (direction == "none") {
			playeractive = playerstill;
			aCounter = 0;
		}
		//moving left
		if (direction == "left") {
			aCounter += ANIMSPEED * (CALCMOV / 4);
			if (aCounter < 10) {
				playeractive = playerleft;
			}
			else if (aCounter > 10 && aCounter < 50) {
				playeractive = playerright;
			}
			else if (aCounter > 50 && aCounter < 100) {
				playeractive = playerleft;
			}
			else if (aCounter > 100) {
				aCounter = 0;
			}
		}
		//falling
		if (onground == true) {
			playeractive = playerfall;	
		}
		
		//sit
		if (direction == "down") {
			playeractive = playerfall;
		}
	}
	
	public void setDirection(String d) {
		direction = d;
	}
	
	public void paint(Graphics g) {
		g.drawImage(playeractive, getCx(), getCy(), getWidth(), getHeight(), null);
	}
}
