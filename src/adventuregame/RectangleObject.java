package adventuregame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import worlds.ListWorld;
import worlds.World;

public class RectangleObject extends Object {
	
	boolean hasImg = false;
	BufferedImage sprite;
	String type = "rectangle";
	ListWorld lw;

	public RectangleObject(Main f, World w) {
		super(f, w);
	}
	
	public void sprite(BufferedImage bf) {
		hasImg = true;
		try {
			sprite = bf;
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void givetype(String s) {
		type = s;
	}

	public void update() {
		
		if (getObjectRect().intersects(lw.p.getObjectRect())) {
			
			if (type == "spike") {
				lw.p.damage((int) (lw.p.maxhealth * 0.5));

			} else if (type == "health") {
				lw.go.rects.remove(this);
				lw.cl.collisions.remove(getObjectRect());
				lw.p.health += 20;
			}
		}
		if (type == "fire") {
			setX(getX() + 5);
			if (getObjectRect().intersects(lw.p.getObjectRect())) {				
				lw.p.setX(lw.p.getX() + 5);
			}
			super.updateObjectRect();
			for (int i = 0; i < lw.go.rects.size(); i++) {
				if (getObjectRect().intersects(lw.go.rects.get(i).getObjectRect()) ) {
					//lw.go.rects.remove(this);
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
			g.drawRect(getCx(), getCy(), getWidth(), getHeight());
			g.drawImage(sprite, getCx(), getCy(), getWidth(), getHeight(), null);
		}
	}
}
