package adventuregame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import worlds.ListWorld;
import worlds.World;

public class RectangleObject extends Object {
	
	boolean hasImg = false;
	BufferedImage sprite;
	String type = "rectangle";
	ListWorld lw;
	String direction;
	int velocity = 5;
	
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
	
	public void givetype(String s) {
		type = s;
		
		try {
			sprite = ImageIO.read(new File(type + ".png"));
			sprite(sprite);
		} catch (Exception e) {e.printStackTrace();}
		
		if (type.equals("ultrahealth")) {
			setSize(150, 150);
			setY(getY() - 50);
		}
		if (type.equals("spikeboi")) {
			setSize(150, 150);
			setGravity(true);
		}
		if (type.equals("spike")) {
			setWidth(200);
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
<<<<<<< HEAD
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
=======
		
		if (type.equals("fire")) {
			setX(getX() + 10);
			
			if (getObjectRect().intersects(lw.p.getObjectRect())) {		
				lw.p.setX(lw.p.getX() + 10);
				
>>>>>>> branch 'newgameloop' of https://github.com/zimbosaurus/adventureboi.git
			}
			
			updateObjectRect();
			
			for (int i = 0; i < lw.go.rects.size(); i++) {	
				
				if (getObjectRect().intersects(lw.go.rects.get(i).getObjectRect()) && !(lw.go.rects.get(i).type == "fire")) {
					
					lw.go.rects.remove(this);
					lw.cl.collisions.remove(getObjectRect());
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
		}
	}
}
