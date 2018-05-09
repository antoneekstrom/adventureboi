package adventuregame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import worlds.ListWorld;
import worlds.World;

public class Player extends Object {

	private Point spawnpoint;
	
	//images and animation
	private BufferedImage playeractive;
	private BufferedImage playerstill;
	private BufferedImage playerright;
	private BufferedImage playerfall;
	private BufferedImage playerleft;
	private BufferedImage fire;
	
	private double aCounter = 0;
	private double ANIMSPEED = 3;
	public boolean enabled = false;
	ListWorld lw;
	
	//hp
	double maxhealth = 100;
	double health = 100;
	int dmgcooldown = 50;
	boolean invulnerable = false;
	public boolean invincible = false;
	
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
	public int JFUELMAX = 15;
	public boolean onground = false;
	
	
	public Player(Main f, World w) {
		super(f, w);
		spawnpoint = new Point();
		try {
			playerstill = ImageIO.read(new File("manboji.png"));
			playerright = ImageIO.read(new File("manboji2.png"));
			playerfall = ImageIO.read(new File("manboji5.png"));
			playerleft = ImageIO.read(new File("manboji3.png"));
			fire = ImageIO.read(new File("fire.png"));
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

		} else if (direction == "none") {
			CALCMOV = BASEMOV;
		}
		
		else {System.out.println("Invalid direction");}
		
	}

	public void jump() {
		jcalculated = getGRAVITY() * JSPEED * JACC;
		if (jump == true && JFUEL != 0 && hasGravity() == true) {
			JFUEL--;
			setY((int) (getY() - jcalculated));
			
		} else if (jump == true && hasGravity() == false) {
			setY((int) (getY() - 20));
		}
		
		if (!onground == true || hasGravity() == false) {
			if (!(lw.cl.side.equals("under"))) {
				JFUEL = JFUELMAX;
			}
			onground = true;
		} else {
			onground = false;
		}
		
		if (direction == "down" && hasGravity() == false) {
			setY( (int) (getY() + 20));
		}
	}
	
	public void isEnabled(boolean b) {
		enabled = b;
		
	}
	
	public void setSize(int w, int h) {
		setWidth(w);
		setHeight(h);
		getObjectRect().setSize(w, h + 25); //55 without collisionCorrection() active
	}
	
	public void die() {
		setX(0);
		setY(0);
		health = maxhealth;
	}
	
	public void passWorld(ListWorld lw) {
		this.lw = lw;
	}
	
	int firecm = 30;
	int firec = firecm;
	
	public void fireCounter() {
		
		if (firec != firecm) {
			firec++;
		}
	}
	
	public void fire(String s) {
		if (firec == firecm) {
			RectangleObject ro = new RectangleObject(lw.frame, lw);
			ro.giveHealthModule(100);
			ro.hm.setDamage(20);
			ro.setSize(100, 100);
			if (s.equals("right")) {
				ro.setLocation(getX() + 150, getY());
				ro.givetype("fire");
				ro.setDirection("right");
			}
			else if (s.equals("left")) {
				ro.setLocation(getX() - 150, getY());
				ro.givetype("fire");
				ro.setDirection("left");
				
			} else {System.out.println("invalid direction");}
			
			ro.sprite(fire);
			lw.addRo(ro);
			firec = 0;
		}
	}
	
	public void collisionCorrection() {
		getObjectRect().y = (int) (getY() - 25);
	}
	
	public void setLocation(int nx, int ny) {
		setX(nx);
		setY(ny);
		getObjectRect().setLocation(nx, ny);
	}
	
	public void update() {	
		animation();
		gravity();
		jump();
		setLocation(getX(), getY());
		updateObjectRect();
		calculateYVelocity();
		move();
		voidCheck();
		hpCheck();
		fireCounter();
	}
	
	public void hpCheck() {
		if (health <= 0) {
			die();
		}
		
		if (invulnerable == true) {
			dmgcooldown -= 1;
			
			if (dmgcooldown <= 0) {
				invulnerable = false;
				dmgcooldown = 50;
			}
		}
	}
	
	public void damage(int d) {
		if (invulnerable == false && !invincible) {
			health = health - d;
			invulnerable = true;
		}
	}
	
	public void voidCheck() {
		if (getY() > 3000) {
			health = 0;
		}
	}
	
	public void setMaxHealth(int i) {
		maxhealth = i;
		health = maxhealth;
	}
	
	public void addHealth(int h, boolean b) {
		if (b) {
			health += h;
		}
		else {
			if (health + h <= maxhealth) {
				health += h;
			} else {
				if (health <= maxhealth) {
					health = maxhealth;
				}
			}
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
		//g.drawRect(getCx(), getCy(), (int) getObjectRect().getWidth(), (int) getObjectRect().getHeight());
		g.drawImage(playeractive, getCx(), getCy(), getWidth(), getHeight(), null);
	}
}
