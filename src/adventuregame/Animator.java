package adventuregame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Animator {
	
	private ArrayList<BufferedImage> images;
	BufferedImage currentimg;
	private BufferedImage backup;
	
	private int firstIndex = 0;
	private int lastIndex = 0;
	
	private int counter = 0;
	private int countergoal = 5;
	
	public Animator(BufferedImage b) {
		images = new ArrayList<BufferedImage>();
		backup = b;
	}
	
	public void addList(ArrayList<BufferedImage> list) {
		images = list;
		lastIndex = images.size();
	}
	
	public void addImage(BufferedImage img, int index) {
		images.add(index, img);
		lastIndex = images.size();
	}
	
	public void createList(String[] patharray) {
		for (int i = 0; i < patharray.length; i++) {
			try {
				BufferedImage sprite = ImageIO.read(new File(patharray[i] + ".png"));
				images.add(sprite);
			} catch (IOException e) {e.printStackTrace();}
		}
		lastIndex = images.size();
	}
	
	public BufferedImage getSprite() {
		return currentimg;
	}
	
	public void setIndexRange(int c, int cg) {
		firstIndex = c;
		if (firstIndex >= images.size()) {
			firstIndex = 0;
		}
		lastIndex = cg;
		if (lastIndex >= images.size()) {
			lastIndex = images.size();
		}
	}
	
	public void speed(int i) {
		countergoal = i;
	}
	
	public String getIndexRange() {
		return "firstIndex:" + firstIndex + " lastIndex:" + lastIndex;
	}
	
	public int getLastIndex() {
		return lastIndex;
	}
	
	public void update() {
		counter++;
		if (counter >= countergoal) {
			counter = 0;
			
			int currentIndex = images.indexOf(currentimg);
			if (currentIndex + 1 < lastIndex) {
				currentimg = images.get(currentIndex + 1);
			}
			else {
				currentimg = images.get(firstIndex);
			}
		}
	}
	
}
