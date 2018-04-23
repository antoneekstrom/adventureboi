package adventuregame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import worlds.ListWorld;

public class Scrollbar {
	
	Rectangle rect;
	Color color;
	Rectangle parent;
	HudObj ho;
	ListWorld world;
	ArrayList<HudObj> listobjs;
	boolean hold = false;
	
	public Scrollbar(Color c, Rectangle r) {
		color = c;
		parent = r;
		listobjs = new ArrayList<HudObj>();
		rect = new Rectangle((int)parent.getMaxX(), (int)parent.getMinY(), 50, parent.height);
		ho = new HudObj(rect.x, rect.y, rect.width, 100, Color.ORANGE);
		ho.highlight = true;
	}
	
	public void addList(ArrayList<HudObj> al) {
		listobjs = al;
	}
	
	public void passWorld(ListWorld w) {
		world = w;
	}
	
	public void update() {
		ho.update();
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		if (world.m.getPressed() && rect.contains(mouse)) {
			hold = true;
		} else if (world.m.getPressed() == false) {
			hold = false;
		}
		if (hold == true) {
			ho.hrect.y = mouse.y;
		}
		if (ho.hrect.getMinY() < rect.getMinY()) {
			ho.hrect.y = (int) rect.getMinY();
		} else if (ho.hrect.getMaxY() > rect.getMaxY()) {
			ho.hrect.y = (int) rect.getMaxY() - ho.hrect.height;
		}
		for (int i = 0; i < listobjs.size(); i++) {
			HudObj o = listobjs.get(i);
			o.hrect.y = (i * 200 + 100) + (int) rect.getMinY() + (rect.y - ho.hrect.y) * (listobjs.size() / 5);
			if (o.hrect.getMaxY() > rect.getMaxY()) {
				o.visible = false;
			} else if (o.hrect.y < rect.getMinY()) {
				o.visible = false;
			} else {
				o.visible = true;
			}
		}
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		ho.paint(g);
	}
	
}
