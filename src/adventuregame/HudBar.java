package adventuregame;

import java.awt.Color;
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
	int hp;
	Player p;
	boolean stats = false;
	String numbers = "0/0";
	Rectangle rect;
	
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
	
	public void paint(Graphics g) {
		//background
		g.setColor(bg);
		g.fillRect(x, y, w, h);
		//foreground
		g.setColor(fg);
		g.fillRect(x, y, (int)fw, h);
		//text
		g.drawString(text, x + (w / 2) - (g.getFontMetrics().stringWidth(text) / 2), y - 20);
		if (id == "hp") {
			g.setColor(tc);
			g.setFont(g.getFont().deriveFont(40f));
			g.drawString(numbers, x - g.getFontMetrics().stringWidth(numbers) - 10, y + (g.getFontMetrics().getHeight() / 2) + (h / 4));
		}
	}
}


