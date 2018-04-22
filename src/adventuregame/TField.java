package adventuregame;

import java.awt.Font;
import java.awt.TextField;

import worlds.ListWorld;

public class TField extends TextField {
	
	public int x,y,w,h;
	Font font;
	
	public TField(int x, int y, int w, int h, ListWorld lw) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		setSize(w, h);
		setLocation(x, y);
		setVisible(true);
		lw.add(this);
	}
	
	public void setFont(Font f) {
		font = f;
		this.setFont(font);
	}
	
}
