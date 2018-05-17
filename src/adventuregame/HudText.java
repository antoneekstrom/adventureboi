package adventuregame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;

public class HudText {
	
	public String text;
	public int x,y;
	public Font font;
	public String id;
	
	Color textcolor = Color.ORANGE;
	Color currentcolor = textcolor;
	Color bgcolor = Color.BLACK;
	Color highlightcolor = bgcolor.brighter();
	boolean background = false;
	int padding = 0;
	int fontwidth, fontheight;
	int width = 0;
	boolean autowidth = false;
	private boolean visible = true;
	int correction = 10;
	public boolean hover = false;
	public boolean hasMouse = false;
	Point mouse;
	String type = "none";
	Rectangle r = new Rectangle();
	public boolean update = false;
	InfoBox ib;
	private boolean hasTooltip = false;
	int ttoffsetx = 400;
	
	public HudText(int x, int y, String t, Font f) {
		text = t;
		this.x = x;
		this.y = y;
		font = f;
	}
	
	public void setText(String s) {
		text = s;
	}
	
	public void toolTip() {
		ib = new InfoBox(new Rectangle(0, 0, 300, 200), font);
		ib.addText(text);
		ib.setBackground(Color.WHITE);
		ib.setId(id);
		hasTooltip = true;
		if (id.equals("item")) {
			ib.addText(Items.getDescription(text));
		}
	}
	
	public void centerHorizontally(Rectangle parent) {
		x = parent.x + (parent.width / 2) - (r.width / 2);
	}
	
	public void centerText() {
		if (background) {
			
		}
	}
	
	public void setLocation(Point p) {
		setX(p.x);
		setY(p.y);
	}
	
	public void setTextColor(Color c) {
		textcolor = c;
		currentcolor = bgcolor;
	}
	
	public void setHighlightColor(Color c) {
		highlightcolor = c;
	}
	
	public void setBackground(Color c, int p, int w) {
		background = true;
		bgcolor = c;
		padding = p;
		width = w;
		currentcolor = bgcolor;
		highlightcolor = bgcolor.brighter();
	}
	
	public void autoWidth(boolean b) {
		autowidth = b;
	}
	
	public void setX(int i) {
		x = i;
	}
	
	public void setPadding(int i) {
		padding = i;
	}
	
	public void setY(int i) {
		y = i;
	}
	
	public void hover() {
		mouse = MouseInfo.getPointerInfo().getLocation();
		if (hover && r.contains(mouse)) {
			currentcolor = highlightcolor;
			hasMouse = true;
		}
		else {
			currentcolor = bgcolor;
			hasMouse = false;
		}
		if (hover && r.contains(mouse) && hasTooltip) {
			ib.place(new Point(r.x - ttoffsetx, r.y));
		}
		else if (hasTooltip) {
			ib.setVisible(false);
		}
	}
	
	public void updateRect() {
		r.setSize(width + padding, fontheight + padding);
		r.setLocation(x - padding, y - fontheight + correction);
	}
	
	public void update() {
		if (update) {
			updateRect();
			hover();
		}
	}
	
	public HudText copy(HudText t) {
		t = this;
		return t;
	}
	
	public void setId(String s) {
		id = s;
	}
	
	public boolean hasTooltip() {
		return hasTooltip;
	}
	
	public int getTotalHeight() {
		return fontheight + padding;
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
				g.setColor(currentcolor);
				if (autowidth) {
					width = fontwidth;
				}
				g.fillRect(x - padding, y - fontheight + correction, width + padding, fontheight + padding);
			}
			g.setColor(textcolor);
			g.drawString(text, x, y);
			if (hasTooltip) {
				ib.paint(g);
			}
		}
	}
}
