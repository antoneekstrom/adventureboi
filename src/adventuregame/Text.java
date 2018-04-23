package adventuregame;

import java.awt.Graphics;

import worlds.World;

import java.awt.Dimension;
import java.awt.Font;

/** Object subclass. First set font color, size and family. then createFont(). */
public class Text extends Object {

	private String text;
	private int FONT_SIZE = 30;
	private String FONT_NAME = "Comic Sans MS";
	public Font font;
	private int FONT_STYLE = Font.PLAIN;
	private String id;
	
	public Text(Main f, World w, String t) {
		super(f, w);
		text = t;
	}
	
	public void setFont(Font f) {
		font = f;
	}
	
	public void text(String t) {
		text = t;
	}
	
	public void setId(String s) {
		id = s;
	}
	
	public String getId() {
		return id;
	}
	
	public void type(String n) {
		FONT_NAME = n;
	}

	public void style(int s) {
		FONT_STYLE = s;
	}
	
	public void create() {	
		font = new Font(FONT_NAME, FONT_STYLE, FONT_SIZE);
	}
	
	/** copies properties of another text object */
	public void copyText(Text t) {
		text = t.text;
		FONT_SIZE = t.FONT_SIZE;
		FONT_NAME = t.FONT_NAME;
		FONT_STYLE = t.FONT_STYLE;
	}
	
	public void size(int size) {
		this.FONT_SIZE = size;
	}
	
	public void paint(Graphics g) {
		g.setColor(getCOLOR());
		g.setFont(font);
		g.drawString(text, getCx(), getCy());
	}
	
}
