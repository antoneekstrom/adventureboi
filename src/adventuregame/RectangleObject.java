package adventuregame;

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
		if (type == "spike") {
			if (getObjectRect().intersects(lw.p.getObjectRect())) {
				lw.p.health = lw.p.health - 10;
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
		}
	}
}
