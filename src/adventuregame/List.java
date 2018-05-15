package adventuregame;

import java.awt.Rectangle;
import java.util.ArrayList;

import worlds.ListWorld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;

public class List {
	
	Rectangle rect;
	HudText textentry;
	ArrayList<HudText> list = new ArrayList<HudText>();
	ArrayList<String> entries = new ArrayList<String>();
	ArrayList<String> ids = new ArrayList<String>();
	private Color bg = Color.WHITE;
	private int padding = 0;
	private int paddingTop = 0;
	private int spacing = 0;
	String id = "";
	boolean background = true;
	boolean hasIdList = false;
	ListWorld world;
	SaveWriter sw;
	boolean visible = false;
	private boolean hideoverflow = false;
	
	private boolean scrollbar = false;
	HudObj bar;
	int barwidth = 50;
	Rectangle brect;
	int listheight = 0;
	int entryheight = 95;
	Point mouse;
	
	int blockHeight = 50;
	HudObj blockTop;
	HudObj blockBottom;
	
	private boolean baractive = false;
	private boolean barpress = false;
	private int mdistance;
	int scrolldistance = 0;
	
	boolean hasEntry = false;
	
	public List(Rectangle r, String type, ListWorld lw) {
		rect = r;
		world = lw;
	}
	
	public void setTextEntry(HudText t) {
		textentry = t;
	}
	
	public void setPaddingTop(int i) {
		paddingTop = i;
	}
	
	public void setBackground(Color c) {
		background = true;
		bg = c;
	}
	
	public void addEntryList(ArrayList<String> l) {
		entries = l;
	}

	public void fill() {
		list.clear();
		listheight = 0;
		for (int i = 0; i < entries.size(); i++) {
			addEntry(entries.get(i));
			if (hasIdList && ids.size() == list.size()) {
				list.get(i).setId(ids.get(i));
			}
		}
		
		if (scrollbar) {
			calculateTotalEntryHeight();
			calculateBarHeight();
		}
	}
	
	public void calculateTotalEntryHeight() {
		int lastentry;
		lastentry = (int) (rect.getMinY() + (list.size() - 1) * entryheight + paddingTop + (((list.size() - 1) > 0) ? (spacing * (list.size() - 1)) : (0)) + ((scrollbar) ? (scrolldistance) : (0)) );		
		listheight = (int) (lastentry - rect.getMinY());
	}

	public void scrollBar() {
		scrollbar = true;
		Color c;
		c = textentry.bgcolor;
		brect = new Rectangle();
		brect.setLocation((int) rect.getMaxX(), (int) rect.getMinY());
		brect.setSize(barwidth, rect.height);
		bar = new HudObj(brect.x - brect.width, brect.y, brect.width, brect.height, c);
	}
	
	public void addIdList(ArrayList<String> l) {
		ids = l;
		hasIdList = true;
	}

	public void addEntry(String s) {
		HudText t = new HudText(0,0,"",null);
		t.text = s;
		t.setBackground(textentry.bgcolor, 0, textentry.fontwidth);
		t.autoWidth(textentry.autowidth);
		t.setId(id + (list.size()));
		t.width = textentry.width;
		t.padding = textentry.padding;
		t.update = textentry.update;
		t.hover = textentry.hover;
		t.textcolor = textentry.textcolor;
		t.type = textentry.type;
		list.add(t);
		hasEntry = true;
	}
	
	public void setHideOverflow(boolean b) {
		hideoverflow = b;
		if (b) {
			blockTop = new HudObj((int) rect.getMinX(), (int) rect.getMinY() - blockHeight, rect.width, blockHeight, bg);
			blockBottom = new HudObj((int) rect.getMinX(), (int) rect.getMaxY(), rect.width, blockHeight, bg);
		}
	}

	public void hideOverflow() {
		if (hideoverflow) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).r.getMaxY() > rect.getMaxY() + blockHeight) {
					list.get(i).setVisible(false);
				}
				else if (list.get(i).r.getMinY() < rect.getMinY() - blockHeight) {
					list.get(i).setVisible(false);
				}
				else {
					list.get(i).setVisible(true);
				}
			}
		}
	}

	public int getEntries() {
		return list.size();
	}

	public void setPadding(int i) {
		padding = i;
	}

	public void setSpacing(int i) {
		spacing = i;
	}

	public void align() {
		for (int i = 0; i < list.size(); i++) {
			HudText t = list.get(i);
			t.x = (int) rect.getMinX() + padding;
			t.y = (int) (rect.getMinY() + i * t.getTotalHeight() + paddingTop + ((i > 0) ? (spacing * i) : (0)) + ((scrollbar) ? (scrolldistance) : (0)) );
		}
	}

	public void passSw(SaveWriter sw) {
		this.sw = sw;
	}

	public void calculateBarHeight() {
		double ratio = (rect.getHeight() - paddingTop) / listheight;
		
		if (ratio > 1) {
			ratio = 1;
		}
		
		bar.hrect.height = (int) (rect.height * (ratio));
	}
	
	public void moveBar() {
		mouse = MouseInfo.getPointerInfo().getLocation();
		if (world.m.pressed && bar.mouseOver()) {
			
			if (!barpress) {
				barpress = true;
				mdistance = (int) (mouse.getY() - bar.hrect.getMinY());
			}
			baractive = true;
			
		}
		else if (!world.m.pressed) {
			baractive = false;
			barpress = false;
		}
		
		if (baractive) {
			bar.color = bar.colord.darker();
			bar.hrect.y = (int) mouse.getY() - mdistance;
		}
		limitBar();
	}
	
	public void limitBar() {
		if (bar.hrect.getMaxY() > rect.getMaxY()) {
			bar.hrect.y = (int) rect.getMaxY() - bar.hrect.height;
		}
		if (bar.hrect.y < rect.getMinY()) {
			bar.hrect.y = (int) rect.getMinY();
		}
	}
	
	public void scroll() {
		int bardistance = (int) (bar.hrect.getMinY() - rect.getMinY());
		double scrollratio = (((double) bardistance) / rect.height) + 1;
		
		scrolldistance = (int) -((listheight * scrollratio) - listheight);
		
	}

	public void update() {
		if (hasEntry) {
			hideOverflow();
			align();
		}
		if (scrollbar) {
			calculateBarHeight();
			bar.update();
			moveBar();
			scroll();
		}
	}

	public void paint(Graphics g) {
		g.setColor(bg);
		if (background) {
			g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).paint(g);
		}
		if (scrollbar) {
			bar.paint(g);
		}
		if (hideoverflow) {
			blockTop.paint(g);
			blockBottom.paint(g);
		}
	}

}
