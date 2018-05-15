package adventuregame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class HudBar {
	int x,y,w,h;
	double fw;
	double percent, ppercent;
	double modifier;
	Color bg, fg, tc;
	String text = "";
	String id;
	int value, maxvalue = 10;
	Player p;
	public boolean stats = false;
	String numbers = "0/0";
	Rectangle rect;
	int numberoffset = 0;
	int textwidth = 0;
	public Font standard = new Font("Comic Sans MS", 20 ,50);
	Font tf = standard;
	int xoffset = 0;
	int yoffset = 0;
	
	int ox, oy;
	boolean offset = false;
	
	public HudBar(int x, int y, int w, int h) {
		bg = Color.WHITE;
		fg = Color.BLACK;
		tc = bg;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		modifier = 1;
		rect = new Rectangle(x, y, w, h);
		id = "";
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
	
	public void offSet(int x, int y) {
		offset = true;
		ox = x;
		oy = y;
	}
	
	public void updateValues(int val, int mval) {
		this.value = val;
		maxvalue = mval;
	}
	
	public void update() {
		
		fw = w * modifier;
		percent = fw / w;
		
		ppercent = (float) value / maxvalue;
		fw = w * ppercent;
		
		if (stats) {
			numbers = value + "/" + maxvalue;
		}
	}
	
	public void updatePlayer() {
		if (id.equals("hp")) {
			
			//percentages
			ppercent = p.health / p.maxhealth;
			fw = w * ppercent;
			numbers = p.health + "/" + p.maxhealth;
			
			//number color
			if (ppercent <= 0.2) {
				tc = fg;
			} else {
				tc = bg;
			}
		}
	}
	
	public void setPos(int x, int y) {
		if (!offset) {
			ox = 0;
			oy = 0;
		}
		this.x = x + ox;
		this.y = y + oy;
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(bg);
		g.fillRect(x, y, w, h);
		//foreground
		g.setColor(fg);
		g.fillRect(x, y, (int)fw, h);
		//text
		g.setFont(tf);
		g.setColor(tc);
		g.drawString(text, x + (w / 2) - (g.getFontMetrics().stringWidth(text) / 2) + xoffset, y - 20 + yoffset);
		if (id == "hp" || stats) {
			g.setColor(tc);
			g.setFont(standard.deriveFont(40f));
			g.drawString(numbers, (int) (x - g.getFontMetrics().stringWidth(numbers) - 10), y + (g.getFontMetrics().getHeight() / 2) + (h / 4));
		}
	}
}


