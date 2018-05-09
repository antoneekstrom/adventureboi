package adventuregame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import worlds.ListWorld;

public class TextCreator {
	
	Point p;
	ListWorld world;
	String text, id;
	Color color;
	
	public TextCreator(ListWorld lw) {
		world = lw;
		text = "boiboi";
		id = "";
	}
	
	public String getText() {
		return text;
	}

	public void setColor(Color c) {
		color = c;
	}
	
	public void setPoint(Point np) {
		p = np;
		p.x -= 800;
		p.y -= 200;
		p.x += world.c.getD2c();
	}
	
	public void setText(String s) {
		text = s;
	}
	
	public void setId(String s) {
		id = s;
	}
	
	public void createText() {
		if (world.optionsactive == false) {
			world.addText(new Point(p.x, p.y), new Font("Comic Sans MS", 100, 40), text, id, color);
		}
	}
	
}
