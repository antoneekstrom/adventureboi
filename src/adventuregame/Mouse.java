package adventuregame;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import worlds.ListWorld;

public class Mouse implements MouseListener {
	
	Main frame;
	ListWorld world;
	Point p1, p2;
	RectangleCreator rc;
	TextCreator tc;
	public ButtonAction ba;
	HUD hud;
	Point mouse;
	boolean pressed = false;
	
	public Mouse(ListWorld w, Main f, HUD h) {
		world = w;
		frame = f;
		hud = h;
		ba = new ButtonAction(w, f, hud);
	}
	
	public boolean getPressed() {
		return pressed;
	}
	
	public void addRc(RectangleCreator rc) {
		this.rc = rc;
	}
	
	public void addTc(TextCreator tc) {
		this.tc = tc;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		mouse = GlobalData.getRelativeMouse();
		if (rc != null) {
			if (ba.mode == "rectangle") {
				rc.mode = "rectangle";
				rc.addp1(mouse);
				
			} else if (ba.mode == "text") {
				tc.setPoint(mouse);
			} else if (ba.mode == "spike") {
				rc.addp1(mouse);
				rc.mode = "spike";
			} else {
				rc.addp1(mouse);
				rc.mode = ba.mode;
			}
		}
		pressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		ba.getClick();
		mouse = GlobalData.getRelativeMouse();
		//checks hud clicks
		if (rc != null && tc != null) {
			if (hud.visible == false) {
				if (ba.mode == "rectangle") {
					rc.addp2(mouse);
					rc.create();
					
				} else if (ba.mode == "text") {
					tc.createText();
					
				} else if (ba.mode == "spike") {
					rc.create();
				} else {
					rc.create();
				}
			}
		}
		pressed = false;
	}

}
