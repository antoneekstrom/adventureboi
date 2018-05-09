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
	ListWorld lw;
	String direction;
	int velocity = 15;
	HealthModule hm;
	boolean hasHealth = false;
	AI ai;
	
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
	
	public void givetype(String s) {
		type = s;
		
		try {
			sprite = ImageIO.read(new File(type + ".png"));
			sprite(sprite);
		} catch (Exception e) {e.printStackTrace();}
		
		if (type.equals("rectangle")) {
			
		}
		if (type.equals("ultrahealth")) {
			setSize(150, 150);
			setY(getY() - 50);
		}
		if (type.equals("spikeboi")) {
			setSize(150, 150);
			setGravity(true);
			hm = new HealthModule(100);
			hasHealth = true;
			setCollision(true);
			hm.showHp();
			hm.hb.offSet(75 - hm.hb.w / 2, -70);
			ai = new AI(getGRAVITY());
		}
		if (type.equals("spike")) {
			
		}
		if (type.equals("donut")) {
			setSize(120, 120);
		}
		if (type.equals("fire")) {
			ai = new AI(getGRAVITY());
		}
	}
	
	public void setDirection(String s) {
		direction = s;
	}

	public void update() {
		super.update();
		
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
			else if (type.equals("donut")) {
				lw.go.rects.remove(this);
				HudObj ho = new HudObj((int) lw.inv.getRect().getMinX(), (int)lw.inv.getRect().getMinY(), 200, 100, Color.ORANGE);
				ho.addImage("donut");
				lw.inv.setEntry(ho);
				lw.inv.addEntry("donut", "");
				lw.inv.alignEntries();
			}
			else if (type.equals("spikeboi")) {
				lw.p.damage(20);
				lw.p.setX(getX());
			}
			
		}
		if (type == "fire") {
			if (direction.equals("left")) {

				setX(getX() - velocity);
				if (getObjectRect().intersects(lw.p.getObjectRect())) {
					lw.p.setX(lw.p.getX() - velocity);
				}
			}
			else if (direction.equals("right")) {
				setX(getX() + velocity);
				if (getObjectRect().intersects(lw.p.getObjectRect())) {				
					lw.p.setX(lw.p.getX() + velocity);
				}
			}
			updateObjectRect();
		}
		for (int i = 0; i < lw.go.rects.size(); i++) {
			if (type.equals("fire")) {
				if (getObjectRect().intersects(lw.go.rects.get(i).getObjectRect()) && !(lw.go.rects.get(i).type == "fire")) {
					
					lw.go.rects.remove(this);
					lw.cl.collisions.remove(getObjectRect());
				}
			}
			
			if (ai != null && lw.go.rects.get(i).getObjectRect().intersects(getObjectRect()) && !(lw.go.rects.get(i).getObjectRect().equals(getObjectRect())) && (lw.go.rects.get(i).type.equals("rectangle"))) {
				ai.passGround(lw.go.rects.get(i).getObjectRect());
			}
			
			if (type.equals("fire") && lw.go.rects.get(i).hasHealth && !(lw.go.rects.get(i).type.equals(type))) {
				if (lw.go.rects.get(i).getObjectRect().intersects(getObjectRect())) {
					lw.go.rects.get(i).hm.decreaseHealth(hm.damage);
					if (lw.go.rects.get(i).ai != null) {
						lw.go.rects.get(i).ai.move = true;
						lw.go.rects.get(i).ai.move();
					}
				}
			}
			if (type.equals("spikeboi") && lw.go.rects.get(i).type.equals("spikeboi") && lw.go.rects.get(i).ai != null && ai != null && !(getObjectRect().equals(lw.go.rects.get(i).getObjectRect())) && lw.go.rects.get(i).getObjectRect().intersects(getObjectRect())) {
				if (ai.nr != null && lw.go.rects.get(i).ai.nr != null) {
					
					if (lw.go.rects.get(i).getY() > getY()) {
						setX((int) (lw.go.rects.get(i).ai.nr.getX()) + 10);
					}
					//setY((int) lw.go.rects.get(i).ai.nr.getY());
					//setX(getX() - (int) (lw.go.rects.get(i).ai.nr.getX() - lw.go.rects.get(i).ai.r.getX()));
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
	}

	public void passWorld(ListWorld lw) {
		this.lw = lw;
	}
	
	public void voidCheck() {
		if (getY() > 3000) {
			hm.decreaseHealth(hm.getHealth());
		}
	}

	public void paint(Graphics g) {
		g.setColor(getCOLOR());
		if (hasHealth) {
			if (hm.hpVisible()) {
				hm.paint(g);
			}
		}
		if (hasImg == false) {
			g.fillRect(getCx(), getCy(), getWidth(), getHeight());			
		} else {
			g.drawImage(sprite, getCx(), getCy(), getWidth(), getHeight(), null);
		}
		if (type.equals("spikeboi")) {
			g.drawString(ai.direction, getCx(), getCy() - 50);
		}
	}
}
