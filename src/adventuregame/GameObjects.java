package adventuregame;

import java.awt.Graphics;
import java.util.ArrayList;

import worlds.ListWorld;

public class GameObjects {

	public ArrayList<RectangleObject> rects;
	public ArrayList<Text> texts;
	private Main frame;
	private ListWorld world;
	
	public GameObjects(Main f, ListWorld lw) {
		rects = new ArrayList<RectangleObject>();
		texts = new ArrayList<Text>();
		frame = f;
		world = lw;
		
	}
	
	public void update() {
		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).update();
		}
		for (int i = 0; i < texts.size(); i++) {
			texts.get(i).update();
			
			if (texts.get(i).getId() == "debug") {
				texts.get(i).text("modeIndex:" + world.m.ba.modeIndex + " mode:" + world.m.ba.mode);
			}
		}
	}
	
	public void paint(Graphics g) {
		
		for (int i = 0; i < rects.size(); i++) {
			rects.get(i).setCOLOR(rects.get(i).getCOLOR());
			rects.get(i).paint(g);
		}
		for (int i = 0; i < texts.size(); i++) {
			texts.get(i).paint(g);
		}
	}
	
}
