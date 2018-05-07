package adventuregame;

import java.awt.Color;
import java.awt.Graphics;

public class HudBar {
	int x,y,w,h;
	double fw;
	double percent, ppercent;
	double modifier;
	Color bg, fg;
	String text = "";
	String id;
	int hp;
	Player p;
	
	public HudBar(int x, int y, int w, int h) {
		bg = Color.WHITE;
		fg = Color.BLACK;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		modifier = 1;
		
	}
	
	public void setText(String s) {
		text = s;
	}
	
	public void passPlayer(Player p) {
		this.p = p;
	}
	
	public void setId(String s) {
		id = s;
	}
	
	public String getId() {
		return id;
	}
	
	public void update() {
		
		fw = w * modifier;
		percent = fw / w;
		
		if (id.equals("hp")) {
			ppercent = p.health / p.maxhealth;
			fw = w * ppercent;
		}
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(bg);
		g.fillRect(x, y, w, h);
		//foreground
		g.setColor(fg);
		g.fillRect(x, y, (int)fw, h);
		//text
		g.drawString(text, x + (w / 2) - (g.getFontMetrics().stringWidth(text) / 2), y - 20);
	}
}


