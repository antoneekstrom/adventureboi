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
		setWidth(50);
		setHeight(50);
		
		try {
			spike = ImageIO.read(new File("spike.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(spike, getCx(), getCy(), getWidth(), getHeight(), null);
	}

}
