package worlds;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import adventuregame.Main;
import adventuregame.RectangleObject;

public class ListWorld extends World {
	
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private Timer timer;
	private Main frame;
	private Color COLOR;
	private Point mouse;
	
	public ListWorld(Main f) {
		frame = f;
	}
	
	public void run() {
		setBackground(Color.BLACK);
		setSize(dim);
		super.run();
		addGObj(new RectangleObject (frame, this), new Point(200, 200), new Dimension(100, 100));
		timer = new Timer(0, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int i = 0; i < objs.size(); i++) {
			objs.get(i).getObj().paint(g);
		}
	}
	
	public int FRAMERATE = 14;
	public double time1, time2;
	public void actionPerformed(ActionEvent arg0) {
		time1 = System.nanoTime() / 1000000;
		if (time1 - time2 > FRAMERATE) {
			
			mouse = MouseInfo.getPointerInfo().getLocation();
			
			for (int i = 0; i < objs.size(); i++) {
				objs.get(i).getObj().setLocation((int) mouse.getX(), (int) mouse.getY());
				objs.get(i).run();
			}
			
			time2 = System.nanoTime() / 1000000;
		}
		repaint();
	}
}
