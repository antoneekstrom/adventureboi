package adventuregame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	private String type = "none";
	Rectangle r = new Rectangle();
	public boolean update = false;
	InfoBox ib;
	int paddingLeft = 10;
	private String alignment = "none";
	private boolean hasTooltip = false;
	int ttoffsetx = 50;
	
	boolean centerImage = false;
	String imagepath;
	int imagex = x;
	int imagewidth = 100;
	int imageheight = 100;
	private BufferedImage image;
	boolean hasImage = false;
	
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
		ib = new InfoBox(new Rectangle(0, 0, 400, 400), font);
		ib.addText(text);
		ib.setBackground(Color.WHITE);
		ib.setId(id);
		ib.autoWidth(true);
		hasTooltip = true;
		if (id.equals("item")) {
			ib.addParagraph(Items.getDescription(text));
			ib.addParagraph(Items.getEffect(text));
			ib.addImage("assets/thumbnails/" + text + ".png");
		}
	}
	
	public void setType(String s) {
		type = s;
	}
	
	public void hasImage(boolean b) {
		hasImage = b;
	}
	
	public void setImage(String fullpath, int w, int h) {
		imagepath = fullpath;
		imagewidth = w;
		imageheight = h;
		try {
			image = ImageIO.read(new File(fullpath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getAlignment() {
		return alignment;
	}
	
	public String getType() {
		return type;
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
			if (ib != null) {
				ib.update();
			}
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
	
	public void alignLeft(Rectangle parent) {
		alignment = "left";
		x = parent.x + paddingLeft;
	}
	
	public boolean hasTooltip() {
		return hasTooltip;
	}
	
	public void setImagePath(String s) {
		imagepath = s;
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
	
	public void centerImage(Rectangle parent) {
		centerImage = true;
		imagex = parent.x + (parent.width / 2) - (imagewidth / 2);
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
			if (hasImage) {
				g.drawImage(image, imagex, y, imagewidth, imageheight, null);
			}
		}
	}
}
