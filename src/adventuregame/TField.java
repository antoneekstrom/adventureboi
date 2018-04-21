package adventuregame;

import java.awt.Font;
import java.awt.TextField;

public class TField extends TextField {
	
	public int x,y,w,h;
	Font font;
	
	public TField(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void setFont(Font f) {
		font = f;
		this.setFont(font);
	}
	
}
