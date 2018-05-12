package adventuregame;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;

public class List {
	
	Rectangle rect;
	HudObj entry;
	HudText textentry;
	ArrayList<HudObj> list = new ArrayList<HudObj>();
	ArrayList<HudText> textlist = new ArrayList<HudText>();
	private Color bg = Color.WHITE;
	private int padding = 0;
	private int paddingTop = 0;
	private int spacing = 0;
	String id = "";
	boolean background = true;
	
	String type = "text";
	
	boolean hasEntry = false;
	
	public List(Rectangle r, String type) {
		rect = r;
	}
	
	public void setTextEntry(HudText t) {
		textentry = t;
	}
	
	public void setEntryObj(HudObj e) {
		entry = e;
	}
	
	public void setPaddingTop(int i) {
		paddingTop = i;
	}
	
	public void setBackground(Color c) {
		background = true;
		bg = c;
	}
	
	public void addEntry(String s) {
		if (type.equals("obj")) {
			HudObj e = new HudObj(0, 0, 0, 0, null);
			e = entry.copy(e);
			list.add(e);
			e.setIndex(list.indexOf(e));
		}
		else if (type.equals("text")) {
			HudText t = new HudText(0,0,"",null);
			t.text = s;
			t.setBackground(textentry.bgcolor, 0, textentry.fontwidth);
			t.autoWidth(textentry.autowidth);
			t.setId(id + (textlist.size()));
			t.width = textentry.width;
			t.padding = textentry.padding;
			t.textcolor = textentry.textcolor;
			textlist.add(t);
			hasEntry = true;
		}
	}
	
	public int getEntries() {
		if (type.equals("text")) {
			return textlist.size();
		}
		else {
			return list.size();
		}
	}
	
	public void setPadding(int i) {
		padding = i;
	}
	
	public void setSpacing(int i) {
		spacing = i;
	}
	
	public void align() {
		if (type.equals("obj")) {
			for (int i = 0; i < list.size(); i++) {
				Rectangle nr = new Rectangle();
				nr.x = (int) (rect.getMinX() + padding);
				nr.y = (int) (rect.getMinY() + padding + i * list.get(i).hrect.height + ((i > 1) ? (spacing) : (0)));
				nr.width = entry.hrect.width;
				nr.height = entry.hrect.height;
				list.get(i).hrect = nr;
			}
		}
		else if (type.equals("text")) {
			for (int i = 0; i < textlist.size(); i++) {
				HudText t = textlist.get(i);
				t.x = (int) rect.getMinX() + padding;
				t.y = (int) (rect.getMinY() + padding + i * t.getTotalHeight() + paddingTop + ((i > 0) ? (spacing * i) : (0)));
			}
		}
	}
	
	public void update() {
		if (hasEntry) {
			align();
		}
		if (type.equals("obj")) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).update();
			}
		}
	}
	
	public void paint(Graphics g) {
		g.setColor(bg);
		if (background) {
			g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
		}
		if (type.equals("obj")) {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).paint(g);
			}
		}
		if (type.equals("text")) {
			for (int i = 0; i < textlist.size(); i++) {
				textlist.get(i).paint(g);
			}
		}
	}
	
}
