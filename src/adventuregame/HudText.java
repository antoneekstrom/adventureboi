package adventuregame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class HudText {
	
	public String text;
	public int x,y;
	public Font font;
	public String id;
	private Color textcolor = Color.ORANGE;
	private Color bgcolor = Color.BLACK;
	boolean background = false;
	int padding = 0;
	int fontwidth, fontheight;
	int width = 0;
	boolean autowidth = false;
	private boolean visible = true;
	
	public HudText(int x, int y, String t, Font f) {
		text = t;
		this.x = x;
		this.y = y;
		font = f;
	}
	
	public void setTextColor(Color c) {
		textcolor = c;
	}
	
	public void setBackground(Color c, int p, int w) {
		background = true;
		bgcolor = c;
		padding = p;
		width = w;
	}
	
	public void autoWidth(boolean b) {
		autowidth = b;
	}
	
	public void setId(String s) {
		id = s;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public void setVisible(boolean b) {
		visible = b;
	}
	
	public void paint(Graphics g) {
		if (visible) {
			g.setFont(font);
			fontheight = g.getFontMetrics().getHeight();
			fontwidth = g.getFontMetrics().stringWidth(text);
			if (background) {
				g.setColor(bgcolor);
				if (autowidth) {
					width = fontwidth;
				}
				g.fillRect(x, y - fontheight + 20, width + padding, fontheight + padding);
			}
			g.setColor(textcolor);
			g.drawString(text, x, y);
		}
		}
}
