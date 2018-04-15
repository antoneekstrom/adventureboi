package worlds;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.Timer;

public class ListWorld extends World {
	
	private int FRAMERATE = 12;
	private ArrayList<Object> objs;
	private Timer timer;
	
	public ListWorld() {
		objs = new ArrayList<Object>();
	}
	
	public void run() {
		timer = new Timer(14, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		
	}
	
	double time1, time2;
	public void actionPerformed(ActionEvent arg0) {
		time1 = System.nanoTime() / 1000000;
		if (time1 - time2 > FRAMERATE) {
			time2 = System.nanoTime() / 1000000;
		}
		repaint();
	}
}
