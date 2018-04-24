package adventuregame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import worlds.ListWorld;

public class HudList {
	
	Rectangle rect;
	boolean background = false;
	Color color;
	Color barcolor;
	HudObj entry;
	Font font;
	ArrayList<HudObj> entries;
	Scrollbar sb;
	ListWorld world;
	Main frame;
	
	public HudList(Rectangle r, Main f) {
		rect = r;
		frame = f;
		entries = new ArrayList<HudObj>();
	}
	
	public void addScrollbar() {
		sb = new Scrollbar(barcolor, rect);
		sb.passWorld(world);
	}
	
	public void passWorld(ListWorld w) {
		world = w;
	}
	
	public void addBackground(Color c) {
		background = true;
		color = c;
	}
	
	public void setFont(Font f) {
		font = f;
	}
	
	public void addEntry(String s, String i) {
		HudObj ho = new HudObj(entry.hrect.x, entry.hrect.y, entry.hrect.width, entry.hrect.height, entry.colord);
		ho.addText(s);
		ho.setId(i);
		ho.setFont(font);
		entries.add(ho);
		sb.addList(entries);
	}
	
	public void setEntry(HudObj e) {
		entry = e;
	}
	
	public void alignEntries() {
		for (int i = 0; i < entries.size(); i++) {
			HudObj e = entries.get(i);
			
			e.hrect.x = (rect.width / 2) + (e.hrect.width / 2);
			e.hrect.y = i * 200 + 200;
			System.out.println(e.hrect.y);
		}
	}
	
	public void update() {
		for (int i = 0; i < entries.size(); i++) {
			HudObj e = entries.get(i);
			e.update();
		}
		sb.update();
		specificUpdate();
	}
	
	public void specificUpdate() {
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		for (int i = 0; i < sb.listobjs.size(); i++) {
			HudObj o = sb.listobjs.get(i);
			if (world.m.pressed == true && o.hrect.contains(mouse)) {
			}
		}
	}
	
	public void paint(Graphics g) {
		if (background == true) {
			g.setColor(color);
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
			for (int i = 0; i < entries.size(); i++) {
				if (entries.get(i).visible == true) {
					entries.get(i).paint(g);
				}
			}
			sb.paint(g);
		}
	}

}
