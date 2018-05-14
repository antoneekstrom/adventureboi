package adventuregame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Timer;

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
	
	//fireball
	int shrinkcounter = 100;
	int velocity = 15;
	int range = 500;
	
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
		} catch (Exception e) {e.printStackTrace();}
		
		if (type.equals("rectangle")) {
			
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
		if (type.equals("spikeboi")) {
			setSize(150, 150);
			setGravity(true);
			hm = new HealthModule(100);
			hasHealth = true;
			setCollision(true);
			hm.showHp();
			subtype = "enemy";
			hm.hb.offSet(75 - hm.hb.w / 2, -70);
			ai = new AI(getGRAVITY());
			animator = new Animator(sprite);
			animator.createList(getPathList());
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
		}
		
		if (hasHealth) {
			hm.update();
			if (hm.getHealth() <= 0) {
				lw.go.rects.remove(this);
				lw.cl.collisions.remove(getObjectRect());
			}
			
			if (hm.hpVisible()) {
				hm.hb.setPos(getCx(), getCy());
			}
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
				HudObj ho = new HudObj((int) lw.inv.getRect().getMinX(), (int)lw.inv.getRect().getMinY(), 200, 100, Color.ORANGE);
				ho.addImage("donut");
				lw.inv.setEntry(ho);
				lw.inv.addEntry("donut", "");
				lw.inv.alignEntries();
			}
			else if (type.equals("spikeboi")) {
				lw.p.damage(20);
				if (lw.p.getY() < getY() && ai.move) {
					lw.p.setX(getX());
					animator.setIndexRange(3, 5);
				}
			}
			else if (type.equals("kantarell") && !hasStarted) {
				counter = new Counter(1000, 2, "kantarell");
				counter.start();
				hasStarted = counter.hasStarted();
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
		for (int i = 0; i < lw.go.rects.size(); i++) {
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
			
			if (ai != null && lw.go.rects.get(i).getObjectRect().intersects(getObjectRect()) && !(lw.go.rects.get(i).getObjectRect().equals(getObjectRect()))) {
				ai.passCollision(lw.go.rects.get(i));
			}
			
			if (type.equals("fire") && lw.go.rects.get(i).hasHealth && !(lw.go.rects.get(i).type.equals(type))) {
				if (lw.go.rects.get(i).getObjectRect().intersects(getObjectRect())) {
					lw.go.rects.get(i).hm.decreaseHealth(hm.damage);
				}
			}
			if (type.equals("spikeboi") && lw.go.rects.get(i).type.equals("spikeboi") && lw.go.rects.get(i).ai != null && ai != null && !(getObjectRect().equals(lw.go.rects.get(i).getObjectRect())) && lw.go.rects.get(i).getObjectRect().intersects(getObjectRect())) {
				if (ai.nr != null && lw.go.rects.get(i).ai.nr != null) {
					
					if (lw.go.rects.get(i).getY() > getY()) {
						setX((int) (lw.go.rects.get(i).ai.nr.getX()) + 10);
					}
				}
			}
		}
		//ai / pathfinding
		if (ai != null) {
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
	
	public void voidCheck() {
		if (getY() > 3000) {
			hm.decreaseHealth(hm.getHealth());
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

	public void paint(Graphics g) {
		g.setColor(getCOLOR());
		if (hasHealth) {
			if (hm.hpVisible()) {
				hm.paint(g);
			}
		}
		if (visible) {
			if (hasImg == false) {
				g.fillRect(getCx(), getCy(), getWidth(), getHeight());			
			} else {
				g.drawImage(sprite, getCx(), getCy(), getWidth(), getHeight(), null);
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
	}
}
