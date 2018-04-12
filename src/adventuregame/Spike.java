package adventuregame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import worlds.World;

public class Spike extends RectangleObject {
	
	private BufferedImage spike;
	
	public Spike(Main f, World w) {
		super(f, w);
		setWidth(150);
		setHeight(150);
		setGravity(false);
		
		try {
			spike = ImageIO.read(new File("spike.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Player p) {
		if (p.getObjectRect().intersects(getObjectRect())) {
			p.die();
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(spike, getCx(), getCy(), getWidth(), getHeight(), null);
	}

}
