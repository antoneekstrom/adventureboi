package adventuregame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import worlds.ListWorld;

public class Mouse implements MouseListener {
	
	Main frame;
	ListWorld world;
	Point p1, p2;
	RectangleCreator rc;
	
	public Mouse(ListWorld w, Main f) {
		world = w;
		frame = f;
		rc = new RectangleCreator(world);
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
		rc.addp1(world.mouse);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		//checks hud clicks
		ArrayList<HudObj> hb = world.options.hb;
		for (int i = 0; hb.size() > i; i++) {
			if (hb.get(i).mouseOver()) {
				System.out.println(hb.get(i).text);
				
				if (hb.get(i).text == "save stage") {
					world.sw.writeList(world.rects);
					
				} else if (hb.get(i).text == "quit") {
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				}
			}
		}

		rc.addp2(world.mouse);
		rc.create();
		
	}

}
