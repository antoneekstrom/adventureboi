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
	
	public void place(Point p) {
		r.setLocation(p);
		
		for (int i = 0; i < tlist.size(); i++) {
			tlist.get(i).update();
			tlist.get(i).centerHorizontally(r);
			tlist.get(i).y = (int) (r.getMinY() + 50 + i * (tlist.get(i).getTotalHeight() + 10) );
		}
		
		visible = true;
	}
	
	public void setVisible(boolean b) {
		visible = b;
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
