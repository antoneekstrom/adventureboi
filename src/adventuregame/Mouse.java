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
	ButtonAction ba;
	
	public Mouse(ListWorld w, Main f, RectangleCreator rc) {
		world = w;
		frame = f;
		this.rc = rc;
		ba = new ButtonAction(w, f);
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
		ba.getClick();
		
		rc.addp2(world.mouse);
		rc.create();
		
	}

}
