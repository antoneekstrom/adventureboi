package adventuregame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class HUD {
	
	public ArrayList<HudObj> hb;
	public boolean visible = false;
	
	public HUD() {
		hb = new ArrayList<HudObj>();
	}

	public void update() {
		if (visible == true) {
			for (int i = 0; hb.size() > i; i++) {
				hb.get(i).update();
			}
		}
	}
	
	public void paint(Graphics g) {
		if (visible == true) {
			for (int i = 0; hb.size() > i; i++) {
				hb.get(i).paint(g);
			}
		}
	}
	
}
