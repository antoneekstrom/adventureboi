package adventuregame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class InfoBox {

	private ArrayList<HudText> tlist;
	private Rectangle r;
	private boolean visible = false;
	private boolean autoWidth = false;
	
	private String id;
	private Font f;
	private Color tc = Color.WHITE;
	private Color fg = Color.ORANGE;
	private Color bg = Color.WHITE;
	
	private boolean hasBackground = false;
	
	public InfoBox(Rectangle r, Font f) {
		tlist = new ArrayList<HudText>();
		this.r = r;
		this.f = f;
	}
	
	public void setId(String s) {
		id = s;
	}
	
	public String getId() {
		return id;
	}
	
	public void setBackground(Color c) {
		hasBackground = true;
		bg = c;
	}
	
	public void setForeground(Color c) {
		fg = c;
	}
	
	public void setTextColor(Color c) {
		tc = c;
	}
	
	public void addImage(String path) {
		HudText t = new HudText(r.x, r.y, id, f);
		t.setId(id);
		t.hasImage(true);
		t.setImage(path, 120, 120);
		t.setText("");
		t.centerImage(r);
		tlist.add(t);
	}
	
	public void addText(String id) {
		HudText t = new HudText(r.x, r.y, id, f);
		t.setBackground(fg, 10, 100);
		t.autoWidth(true);
		t.update = true;
		t.setTextColor(tc);
		t.setId(id);
		t.centerHorizontally(r);
		tlist.add(t);
	}
	
	public void addParagraph(String id) {
		HudText t = new HudText(r.x, r.y, id, f);
		t.update = true;
		t.setTextColor(Color.ORANGE);
		t.setId(id);
		t.alignLeft(r);
		tlist.add(t);
	}
	
	public void addTextObject(HudText ht) {
		tlist.add(ht);
	}
	
	public void place(Point p) {
		r.setLocation(p.x - r.width, p.y);
		
		for (int i = 0; i < tlist.size(); i++) {
			tlist.get(i).update();
			if (tlist.get(i).getAlignment().equals("left")) {
				tlist.get(i).alignLeft(r);
			}
			else {
				tlist.get(i).centerHorizontally(r);
			}
			tlist.get(i).y = (int) (r.getMinY() + 50 + i * (tlist.get(i).getTotalHeight() + 10) );
		}
		
		visible = true;
	}
	
	public void setVisible(boolean b) {
		visible = b;
	}
	
	public void autoWidth(boolean b) {
		autoWidth = b;
	}
	
	public void adjustWidth() {
		int largestwidth = 0;
		int padding = 0;
		for (int i = 0; i < tlist.size(); i++) {
			if (tlist.get(i).fontwidth > largestwidth) {
				largestwidth = tlist.get(i).fontwidth;
			}
			if (tlist.get(i).getAlignment().equals("left")) {
				padding = tlist.get(i).paddingLeft;
			}
		}
		if (largestwidth > r.width) {
			r.width = largestwidth + padding * 2;
		}
	}
	
	public HudText getText(String id) {
		for (int i = 0; i < tlist.size(); i++) {
			if (tlist.get(i).id.equals(id)) {
				return tlist.get(i);
			}
		}
		return null;
	}
	
	public void update() {
		for (int i = 0; i < tlist.size(); i++) {
			tlist.get(i).update();
			if (tlist.get(i).centerImage) {
				tlist.get(i).centerImage(r);
			}
		}
		if (autoWidth) {
			adjustWidth();
		}
	}
	
	public void paint(Graphics g) {
		if (visible) {
			if (hasBackground) {
				g.setColor(bg);
				g.fillRect(r.x, r.y, r.width, r.height);
			}
			g.setColor(fg);
			for (int i = 0; i < tlist.size(); i++) {
				tlist.get(i).paint(g);
			}
		}
	}
	
}
