package adventuregame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import worlds.ListWorld;
import worlds.World;

public class RectangleObject extends Object {
	
	boolean hasImg = false;
	BufferedImage sprite;
	String type = "rectangle";
	String subtype = "none";
	ListWorld lw;
	HealthModule hm;
	boolean hasHealth = false;
	AI ai;
	Animator animator;
	int index = -1;
	boolean showrect = false;
	boolean visible = true;
	private boolean selected = false;
	String stype = "none";
	private String text = "boioioing";
	boolean hasText = false;
	Color tc = Color.BLACK;
	boolean hasStarted = false;
	boolean started = false;
	boolean charged = false;
	boolean doesCarry = false;
	boolean imgRepeat = false;
	
	double forcex = 0;
	double forcey = 0;
	int xtranslate, ytranslate;
	int mass = 10;
	double resistancex = 1;
	double resistancey = 2;
	
	int repeatCount = 0;
	int imgWidth = 0;
	
	//fireball
	int shrinkcounter = 100;
	int velocity = 15;
	int range = 500;
	Rectangle inceptiverect;
	
	String direction;
	Counter counter;
	Counter appear;
	
	Rectangle iniRect = new Rectangle();
	
	public RectangleObject(Main f, World w) {
		super(f, w);
		setCOLOR(Color.WHITE);
	}
	
	public void sprite(BufferedImage bf) {
		hasImg = true;
		try {
			sprite = bf;
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void giveHealthModule(int i) {
		hm = new HealthModule(i);
		hasHealth = true;
	}
	
	public void text(String s) {
		text = s;
	}
	
	public String getText() {
		return text;
	}
	
	public void setSprite(String path) {
		hasImg = true;
		try {
			sprite = ImageIO.read(new File(path));
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void givetype(String s) {
		type = s;
		stype = type;
		
		try {
			sprite = ImageIO.read(new File("assets/sprites/" + type + ".png"));
			sprite(sprite);
			imgWidth = ImageIO.read(new File("assets/sprites/" + type + ".png")).getWidth();
		} catch (Exception e) {e.printStackTrace();}
		
		if (type.equals("rectangle")) {
			
		}
		if (type.equals("explosion")) {
			setSprite("assets/animated_sprites/explosion/ex1.png");
			stype = "explosion";
			setSize(200, 200);
			animator = new Animator(sprite);
			animator.createList(getPathList());
			animator.countCycles(true);
			animator.speed(7);
		}
		if (type.equals("ultrahealth")) {
			setSize(150, 150);
			subtype = "pickup";
			setY(getY() - 50);
		}
		if (type.equals("health")) {
			subtype = "pickup";
		}
		if (type.equals("invis")) {
			visible = false;
			hasImg = false;
			
		}
		if (type.equals("text")) {
			hasText = true;
			visible = false;
		}
		if (type.equals("energyshroom")) {
			
		}
		if (type.equals("angryshroom")) {
			setGravity(true);
			setCollision(true);
			doesCarry = true;
			subtype = "enemy";
			setSize(150, 150);
			ai = new AI();
			ai.setSpeed(15);
			animator = new Animator(sprite);
			animator.createList(getPathList());
			animator.speed(5);
			animator.setIndexRange(0, 3);
			ai.jumpChance(0.8);
			ai.jumpTime(7);
			ai.jumpForce(70);
			hm = new HealthModule(200);
			hm.setCanDie(false);
			hasHealth = true;
			hm.showHp();
			hm.hb.offSet(Position.centerX(getObjectRect(), hm.hb.rect).x, hm.hb.rect.y - 100);
		}
		if (type.equals("dangerfloor")) {
			setSize(200, 100);
		}
		if (type.equals("fallblock")) {
			hasImg = false;
			setCollision(true);
			setGravity(true);
			doesCarry = true;
		}
		if (type.equals("bigmush")) {
			setSize(350, 300);
			hm = new HealthModule(350);
			hasHealth = true;
			hm.showHp();
			subtype = "enemy";
			ai = new AI();
			doesCarry = true;
			ai.setSpeed(3);
			ai.jumpChance(0);
			setCollision(true);
			setGravity(true);
			hm.hb.offSet(Position.centerX(getObjectRect(), hm.hb.rect).x, hm.hb.rect.y - 100);
			setSprite("assets/animated_sprites/bigmush/bigmush_idle.png");
			stype = "bigmush";
			animator = new Animator(sprite);
			animator.speed(10);
			animator.createList(getPathList());
		}
		if (type.equals("spikeboi")) {
			setSize(150, 150);
			setGravity(true);
			hm = new HealthModule(100);
			hasHealth = true;
			setCollision(true);
			hm.showHp();
			subtype = "enemy";
			hm.hb.offSet(75 - hm.hb.w / 2, -70);
			ai = new AI();
			animator = new Animator(sprite);
			animator.createList(getPathList());
		}
		if (type.equals("shroom")) {
			setGravity(true);
			setCollision(true);
			subtype = "enemy";
			setSize(150, 150);
			ai = new AI();
			hm = new HealthModule(1337);
			hasHealth = true;
		}
		if (type.equals("spike")) {
			subtype = "pickup";
		}
		if (type.equals("kantarell")) {
			setSize(300, 300);
			updateObjectRect();
			iniRect = new Rectangle(getObjectRect());
			setSprite("assets/sprites/kantarell.png");
		}
		if (type.equals("donut")) {
			setSize(120, 120);
			subtype = "pickup";
		}
		if (type.equals("fire")) {
		}
		if (type.equals("star")) {
			setSize(250, 250);
			sprite(sprite);
			hm = new HealthModule(40);
			hasHealth = true;
			animator = new Animator(sprite);
			animator.speed(35);
			animator.createList(getPathList());
		}
		if (type.equals("solidstar")) {
			stype = "star";
			hasText = true;
			text = "";
			
			setSize(250, 250);
			sprite(sprite);
			animator = new Animator(sprite);
			animator.speed(35);
			animator.createList(getPathList());
		}
	}
	
	public void start() {
		if (type.equals("fire") && charged) {
			System.out.println("charged");
			setSprite("assets/sprites/chargedfire.png");
			inceptiverect = getObjectRect();
		}
		if (type.equals("explosion")) {
			lw.cl.collisions.remove(getObjectRect());
		}
	}
	
	public void setDirection(String s) {
		direction = s;
	}
	
	public void updateIndex() {
			for (int i = 0; i < lw.go.rects.size(); i++) {
				if (lw.go.rects.get(i).getObjectRect().equals(this.getObjectRect())) {
					index = i;
				}
			}
	}
	
	public void imgRepeat() {
		imgRepeat = true;
		if (imgWidth != 0 && imgWidth > getWidth()) {
			repeatCount = Math.round(getWidth() % imgWidth);
		}
	}

	public void update() {
		super.update();
		
		if (!started) {
			started = true;
			start();
		}
		
		if (hasImg && type.contains("info")) {
			lw.cl.collisions.remove(getObjectRect());
		}
		
		if (index == -1) {
			updateIndex();
		}
		
		if (hasGravity()) {
			voidCheck();
			force();
		}
		
		if (hasHealth) {
			hm.update();
			if (hm.getHealth() <= 0 && !hm.isInvincible() && hm.canDie()) {
				lw.go.rects.remove(this);
				lw.cl.collisions.remove(getObjectRect());
			}
			
			if (hm.hpVisible()) {
				hm.hb.setPos(getCx(), getCy());
			}
		}
		
		if (type.equals("explosion") && animator.getCycles() > 0) {
			lw.go.rects.remove(this);
			lw.cl.collisions.remove(getObjectRect());
		}
		
		if (counter != null && counter.id.equals("kantarell") && counter.done) {
			if (shrinkcounter > 0) {
				shrinkcounter--;
				setWidth((int) (getWidth() - iniRect.getWidth() * 0.01));
				setHeight((int) (getHeight() - iniRect.getHeight() * 0.01));
				setX((int) (getX() + (iniRect.getWidth() * 0.005)));
				setY((int) (getY() + (iniRect.getHeight() * 0.005)));
			}
			else if (shrinkcounter <= 0) {
				shrinkcounter = 100;
				lw.cl.collisions.remove(getObjectRect());
				appear = new Counter(1000, 10, "appear");
				appear.start();
				counter.done = false;
			}
		}

		if (appear != null && appear.done && type.equals("kantarell")) {
			setX(iniRect.x);
			setY(iniRect.y);
			setSize(iniRect.width, iniRect.height);
			lw.cl.add(getObjectRect());
			appear.done = false;
			hasStarted = false;
		}
		
		if (type.equals("explosion")) {
			//explosion hitbox
			Rectangle r = getObjectRect();
			
			//determine force
			double xdistance = (lw.p.getX() + (lw.p.getWidth() / 2) ) - (getX() + (getWidth() / 2) );
			double ydistance = (lw.p.getY() + (lw.p.getHeight() / 2) ) - (getY() + (getHeight() / 2) );
			
			
			//apply force to nearby player
			if (r.intersects(lw.p.getObjectRect())) {
				lw.p.setForce(xdistance * 0.7, -(ydistance * 1.5) );
			}
		}
		
		if (getObjectRect().intersects(lw.p.getObjectRect())) {
			if (type.equals("spike")) {
				lw.p.damage((int) (lw.p.maxhealth * 0.5));

			}
			else if (type.equals("health") && lw.p.health < lw.p.maxhealth) {
				lw.go.rects.remove(this);
				lw.cl.collisions.remove(getObjectRect());
				lw.p.addHealth(20, false);
			}
			else if (type.equals("ultrahealth")) {
				lw.go.rects.remove(this);
				lw.cl.collisions.remove(getObjectRect());
				lw.p.addHealth(50, true);
			}
			else if (type.equals("star")) {
				lw.go.rects.remove(this);
				lw.cl.collisions.remove(getObjectRect());
				lw.p.invinciblecounter = 1500;
			}
			else if (type.equals("donut")) {
				lw.go.rects.remove(this);
				lw.cl.collisions.remove(getObjectRect());
				Character.Inventory().addItem("donut");
				lw.p.checkInventory();
				lw.p.addHealth(20, false);
			}
			else if (type.equals("deceasedangryshroom")) {
				lw.go.rects.remove(this);
				lw.cl.collisions.remove(getObjectRect());
				Character.Inventory().addItem("angryshroom");
				lw.p.checkInventory();
			}
			else if (type.equals("shroom")) {
				lw.go.rects.remove(this);
				lw.cl.collisions.remove(this.getObjectRect());
				Character.Inventory().addItem("shroom");
				lw.p.checkInventory();
			}
			else if (type.equals("spikeboi")) {
				lw.p.damage(20);
				if (lw.p.getY() < getY()) {
					lw.p.setX(getX());
				}
				animator.setIndexRange(3, 5);
			}
			else if (type.equals("fallblock") && lw.p.getY() < getY()) {
			}
			else if (type.equals("kantarell") && !hasStarted) {
				counter = new Counter(1000, 2, "kantarell");
				counter.start();
				hasStarted = counter.hasStarted();
			}
			else if (type.equals("dangerfloor")) {
				lw.p.damage((int) lw.p.health);
			}
			if (hasGravity() && hasCollision()) {
				if (forcex != 0) {
					lw.p.forcex = forcex;
				}
			}
			if (doesCarry) {
				if (lw.p.getY() < getY() && ai != null && ai.getMove()) {
					if (getObjectRect().intersects(lw.p.getObjectRect())) {
						if (ai.getDirection().equals("left")) {
							lw.p.setX(lw.p.getX() - ai.getSpeed());
						}
						else if (ai.getDirection().equals("right")) {
							lw.p.setX(lw.p.getX() + ai.getSpeed());
						}
					}
				}
			}
		}
		else {
			if (type.equals("spikeboi")) {
				animator.setIndexRange(0, 2);
			}
		}
		if (type == "fire") {
			if (direction.equals("left")) {

				setX(getX() - velocity);
				range -= velocity;
				if (getObjectRect().intersects(lw.p.getObjectRect())) {
					lw.p.setX(lw.p.getX() - velocity);
				}
			}
			else if (direction.equals("right")) {
				setX(getX() + velocity);
				range -= velocity;
				if (getObjectRect().intersects(lw.p.getObjectRect())) {				
					lw.p.setX(lw.p.getX() + velocity);
				}
			}
			updateObjectRect();
			if (range <= 0) {
				
				lw.go.rects.remove(this);
				lw.cl.collisions.remove(getObjectRect());
			}
		}
		if (type.equals("angryshroom") && hm.getHealth() <= 0) {
			ai.setMove(false);
			animator.setIndexRange(animator.size() - 1, animator.size() - 1);
			hm.hideHp();
			givetype("deceasedangryshroom");
		}
		
		//collision with all objects
		for (int i = 0; i < lw.go.rects.size(); i++) {
			RectangleObject ro = lw.go.rects.get(i);
			if (type.equals("fire")) {
				if (getObjectRect().intersects(lw.go.rects.get(i).getObjectRect()) && !(lw.go.rects.get(i).type == "fire") && !lw.go.rects.get(i).subtype.equals("pickup")) {
					if (charged && lw.go.rects.get(i).subtype.equals("enemy")) {
					}
					else {
						lw.cl.collisions.remove(getObjectRect());
						lw.go.rects.remove(this);
					}
				}
			}
			
			if (type.equals("explosion") && ro.hasGravity() && ro.hasCollision()) {
				//explosion hitbox
				Rectangle r = getObjectRect();
				
				//determine force
				double xdistance = (ro.getX() + (ro.getWidth() / 2) ) - (getX() + (getWidth() / 2) );
				double ydistance = (ro.getY() + (ro.getHeight() / 2) ) - (getY() + (getHeight() / 2) );
				
				
				//apply force to nearby player
				if (r.intersects(ro.getObjectRect())) {
					ro.setForce(xdistance * 0.6, -(ydistance * 0.8) );
				}
			}
			
			if (ai != null && lw.go.rects.get(i).getObjectRect().intersects(getObjectRect()) && !(lw.go.rects.get(i).getObjectRect().equals(getObjectRect()))) {
				ai.passCollision(lw.go.rects.get(i));
			}
			
			if (type.equals("fire") && lw.go.rects.get(i).hasHealth && !(lw.go.rects.get(i).type.equals(type))) {
				if (lw.go.rects.get(i).getObjectRect().intersects(getObjectRect())) {
					lw.go.rects.get(i).hm.decreaseHealth(hm.damage);
				}
			}
			if (type.equals("spikeboi") && lw.go.rects.get(i).type.equals("spikeboi") && lw.go.rects.get(i).ai != null && ai != null && !(getObjectRect().equals(lw.go.rects.get(i).getObjectRect())) && lw.go.rects.get(i).getObjectRect().intersects(getObjectRect())) {
				if (ai.getNewRect() != null && lw.go.rects.get(i).ai.getNewRect() != null) {
					
					if (lw.go.rects.get(i).getY() > getY()) {
						setX((int) (lw.go.rects.get(i).ai.getNewRect().getX()) + 10);
					}
				}
			}
			if (doesCarry) {
				if (ro.getY() < getY() && ai != null && ai.getMove()) {
					if (getObjectRect().intersects(ro.getObjectRect())) {
						if (ai.getDirection().equals("left")) {
							ro.setX(ro.getX() - ai.getSpeed());
						}
						else if (ai.getDirection().equals("right")) {
							ro.setX(ro.getX() + ai.getSpeed());
						}
					}
				}
			}
		}
		//ai / pathfinding
		if (ai != null) {
			ai.passObject(this);
			Rectangle r = new Rectangle(getX(), getY(), getWidth(), getHeight());
			ai.update(r);
			setX(ai.returnPos().x);
			setY(ai.returnPos().y);
		}
		//animation
		if (animator != null) {
			animator.update();
			sprite = animator.getSprite();
		}
	}

	public void passWorld(ListWorld lw) {
		this.lw = lw;
	}
	
	public void deselect() {
		selected = false;
	}
	
	public void destroy() {
		lw.go.rects.remove(index);
		lw.cl.collisions.remove(getObjectRect());
	}
	
	public void applyForce(double x, double y) {
		forcex += x;
		forcey += y;
	}
	
	public void setForce(double x, double y) {
		forcex = x;
		forcey = y;
	}
	
	public void voidCheck() {
		if (getY() > 3000) {
			if (hm != null) {
				hm.decreaseHealth(hm.getHealth());
			}
			else {
				lw.go.rects.remove(this);
				lw.cl.collisions.remove(getObjectRect());
			}
		}
	}
	
	public String[] getPathList() {
		if (stype.equals("spikeboi")) {
			String path = "assets/animated_sprites/spikeboi/";
			String[] l = new String[] {
					path + "sb1",
					path + "sb2",
					path + "sb3",
					path + "sbh1",
					path + "sbh2",
					path + "sbh3"
			};
			return l;
		}
		else if (stype.equals("star")) {
			String path = "assets/animated_sprites/star/";
			String[] l = new String[] {
					path + "star1",
					path + "star2",
					path + "star3",
			};
			return l;
		}
		else if (stype.equals("bigmush")) {
			String path = "assets/animated_sprites/bigmush/";
			String[] l = new String[] {
					path + "bigmush_idle",
					path + "bigmush_left",
					path + "bigmush_right"
			};
			return l;
		}
		else if (stype.equals("angryshroom")) {
			String path = "assets/animated_sprites/angryshroom/";
			String[] l = new String[] {
					path + "as1",
					path + "as2",
					path + "as3",
					path + "dm1",
			};
			return l;
		}
		else if (stype.equals("explosion")) {
			String path = "assets/animated_sprites/explosion/";
			String[] l = new String[] {
					path + "ex1",
					path + "ex2",
					path + "ex3",
					path + "ex4",
					path + "ex5",
					path + "ex6",
					path + "ex7",
					path + "ex8",
					path + "ex9",
					path + "ex10",
					path + "ex11",
			};
			return l;
		}
		else {
			return null;
		}
	}
	
	public void select() {
		for (int i = 0; i < lw.go.rects.size(); i++) {
			if (lw.go.rects.get(i).selected) {
				lw.go.rects.get(i).selected = false;
			}
		}
		selected = true;
	}

	public void force() {
		//resistance
		int rx = (int) (resistancex + (Math.pow(forcex, 1/4)) );
		int ry = (int) (resistancey + (Math.pow(forcey, 1/4)) );
		
		//x-axis
		if (forcex > 0) {
			if (forcex - rx < 0) {
				forcex = 0;
			}
			else {
				forcex -= rx;
			}
		}
		else {
			if (forcex + rx > 0) {
				forcex = 0;
			}
			else {
				forcex += rx;
			}
		}
		
		if (forcex > 0) {
			xtranslate = (int) ( (Math.sqrt(forcex)) + (Math.sqrt(forcex) * ( Math.sqrt(mass	) / 2) ) );
			setX(getX() + xtranslate);
		}
		else if (forcex < 0) {
			double f = -forcex;
			xtranslate = (int) ( (Math.sqrt(f)) + (Math.sqrt(f) * Math.sqrt(mass) / 2) );
			setX(getX() - xtranslate);
		}
		
		//y-axis
		if (forcey > 0) {
			if (forcey - ry < 0) {
				forcey = 0;
			}
			else {
				forcey -= ry;
			}
		}
		else {
			if (forcey + ry > 0) {
				forcey = 0;
			}
			else {
				forcey += ry;
			}
		}
		
		if (forcey > 0) {
			ytranslate = (int) ( (Math.sqrt(forcey)) + (Math.sqrt(forcey) * Math.sqrt(mass) / 2) );
			setY(getY() - ytranslate);
		}
		else if (forcey < 0) {
			double f = -forcey;
			ytranslate = (int) ( (Math.sqrt(f)) + (Math.sqrt(f) * Math.sqrt(mass) / 2) );
			setY(getY() + ytranslate);
		}
	}
	
	public void setMass(int i) {
		mass = i;
	}
	
	public int getMass() {
		return mass;
	}
	
	public void applyForceX(double f) {
		forcex = f;
	}
	
	public double getForceX() {
		return forcex;
	}
	
	public void applyForceY(double f) {
		forcey = f;
	}
	
	public double getForceY() {
		return forcey;
	}

	public void paint(Graphics g) {
		//healthbar
		g.setColor(getCOLOR());
		if (hasHealth) {
			if (hm.hpVisible()) {
				hm.paint(g);
			}
		}
		//other
		if (visible) {
			if (hasImg == false) {
				g.fillRect(getCx(), getCy(), getWidth(), getHeight());			
			} else {
				if (!imgRepeat) {
					g.drawImage(sprite, getCx(), getCy(), getWidth(), getHeight(), null);
				}
				else if (imgRepeat){
					for (int i = 0; i < repeatCount; i++) {
						g.drawImage(sprite, getCx() + ( i * getWidth() ), getCy(), getWidth(), getHeight(), null);
					}
				}
			}
		}
		if (showrect) {
			g.drawRect(getCx(), getCy(), getWidth(), getHeight());
		}
		if (hasText) {
			g.setFont(lw.standard);
			g.setColor(tc);
			g.drawString(text, getCx(), getCy());
		}
		if (lw.typelistener != null && lw.typelistener.c.showIndex) {
			g.setColor(Color.BLACK);
			g.setFont(lw.standard);
			if (!selected) {
				g.drawString(String.valueOf(index), getCx(), getCy());
			}
			else {
				g.setColor(Color.ORANGE);
				g.drawString("selected", getCx(), getCy());
			}
		}
		if (ai != null) {
			g.drawString(String.valueOf(""), getCx(), getCy() - 150);
		}
	}
}
