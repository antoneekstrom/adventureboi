package adventuregame;

import java.awt.Color;
import java.awt.Graphics;
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
			hm = new HealthModule(10);
			hasHealth = true;
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
		}
		if (type.equals("spike")) {
			
		}
		if (type.equals("donut")) {
			setSize(120, 120);
		}
	}
	
	public void setDirection(String s) {
		direction = s;
	}

	public void update() {
		super.update();
		if (hasHealth) {
			hm.update();
			if (hm.getHealth() <= 0) {
				lw.go.rects.remove(this);
				lw.cl.collisions.remove(getObjectRect());
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
			if (type.equals("fire") && lw.go.rects.get(i).hasHealth && !(lw.go.rects.get(i).type.equals(type))) {
				if (lw.go.rects.get(i).getObjectRect().intersects(getObjectRect())) {
					lw.go.rects.get(i).hm.decreaseHealth(hm.damage);
				}
			}
		}
	}

	public void passWorld(ListWorld lw) {
		this.lw = lw;
	}

	public void paint(Graphics g) {
		g.setColor(getCOLOR());
		if (hasImg == false) {
			g.fillRect(getCx(), getCy(), getWidth(), getHeight());			
		} else {
			g.drawImage(sprite, getCx(), getCy(), getWidth(), getHeight(), null);
			
			//debug
			if (hasHealth) {
				g.setFont(lw.standard);
				g.drawString(String.valueOf(hm.getHealth()), (int) getCx(), (int) getCy());
			}
		} 
	}
}
