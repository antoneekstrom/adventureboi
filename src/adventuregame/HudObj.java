package adventuregame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JTextField;

import worlds.World;

public class HudObj {

	public Rectangle hrect;
	public Point mouse;
	public Color colord, color2, color;
	public String text;
	public boolean highlight = true;
	public String id;
	public Font font;
	public boolean visible = true;
	
	public HudObj(int x, int y, int w, int h, Color c) {
		colord = c;
		hrect = new Rectangle(w, h);
		hrect.setLocation(x, y);
		color2 = colord.brighter();			
		color = colord;
	}
	
	public void setHighlightColor(Color c) {
		color2 = c;
	}
	
	public void setId(String s) {
		id = s;
	}
	
	public void setFont(Font f) {
		font = f;
	}
	
	public void addText(String t) {
		text = t;
	}

	public void update() {
		mouse = MouseInfo.getPointerInfo().getLocation();
		
		if (highlight == true) {
			if (hrect.contains(mouse)) {
				color = color2;
			} else {
				color = colord;
			}
		}
	}

	public boolean mouseOver() {
		mouse = MouseInfo.getPointerInfo().getLocation();
		return hrect.contains(mouse);
	}

	public void paint(Graphics g) {
		g.setFont(font);
		g.setColor(color);
		g.fillRect(hrect.x, hrect.y, hrect.width, hrect.height);
		if (!(text == null) ) {
			g.setColor(Color.WHITE);
			g.drawString(text, (int) (hrect.getMinX() + (hrect.getWidth() / 2) - (g.getFontMetrics().stringWidth(text) / 2)), (int)(hrect.getMaxY() - (hrect.getHeight() / 2) + (g.getFontMetrics().getHeight() / 4)));
		}
	}

}
