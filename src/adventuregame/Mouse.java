package adventuregame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import worlds.ListWorld;

public class Mouse implements MouseListener {
	
	ListWorld world;
	Point p1, p2;
	
	public Mouse(ListWorld w) {
		world = w;
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
		System.out.println("mouse pressed");
		p1 = world.mouse;
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		System.out.println("mouse released");
		p2 = world.mouse;
		Rectangle r = new Rectangle(p1);
		r.add(p2);
	}

}
