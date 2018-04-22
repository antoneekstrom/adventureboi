package adventuregame;

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
	
	public Mouse(ListWorld w, Main f, RectangleCreator rc, TextCreator tc) {
		world = w;
		frame = f;
		this.rc = rc;
		this.tc = tc;
		ba = new ButtonAction(w, f);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		ba.getClick();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
		if (ba.mode == "rectangle") {
			rc.addp1(world.mouse);
			
		} else if (ba.mode == "text") {
			tc.setPoint(world.mouse);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		//checks hud clicks
		if (world.options.visible == false) {
			if (ba.mode == "rectangle") {
				rc.addp2(world.mouse);
				rc.create();
				
			} else if (ba.mode == "text") {
				tc.createText();			
			}
		}
		
	}

}
