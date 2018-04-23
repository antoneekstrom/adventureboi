package adventuregame;

import java.awt.Font;
import java.awt.Graphics;

public class HudText {
	
	public String text;
	public int x,y;
	public Font font;
	
	public HudText(int x, int y, String t, Font f) {
		text = t;
		this.x = x;
		this.y = y;
		font = f;
	}
	
	public void paint(Graphics g) {
		g.setFont(font);
		g.drawString(text, x, y);
	}
}
