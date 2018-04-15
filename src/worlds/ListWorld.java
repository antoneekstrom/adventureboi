package worlds;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import adventuregame.Camera;
import adventuregame.Main;
import adventuregame.Player;
import adventuregame.PlayerCollision;
import adventuregame.RectangleObject;

public class ListWorld extends World {
	
	private int FRAMERATE = 12;
	private ArrayList<RectangleObject> rects;
	private Timer timer;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private Player p;
	private Main frame;
	private boolean ready = false;
	private Camera c;
	private Point mouse;
	private PlayerCollision cl;
	private int counter =  0;
	
	public ListWorld(Main f) {
		frame = f;
		setBackground(Color.WHITE);
		setSize(dim);
		rects = new ArrayList<RectangleObject>();
	}
	
	public void addRect(Point p, Dimension d) {
		RectangleObject ro = new RectangleObject(frame, this);
		ro.setLocation((int) p.getX(), (int) p.getY());
		ro.setSize((int) d.getWidth(), (int) d.getHeight());
		rects.add(ro);
		c.add(ro);
		cl.add(ro.getObjectRect());
	}
	
	public void run() {
		mouse = new Point();
		p = new Player(frame, this);
		cl = new PlayerCollision(p);
		p.setGravity(true);
		p.setLocation(100, 100);
		p.setSize(150, 125);
		p.setGRAVITY(30);
		p.JFUEL = 7;
		ready = true;
		startPlayerController(p);
		c = new Camera(dim);
		c.add(p);
		addRect(new Point(100, 100), new Dimension(100, 100));
		timer = new Timer(14, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		if (ready == true) {
			p.paint(g);
			
			for (int i = 0; i < rects.size(); i++) {
				rects.get(i).paint(g);
			}
		}
	}
	
	double time1, time2;
	public void actionPerformed(ActionEvent arg0) {
		time1 = System.nanoTime() / 1000000;
		if (time1 - time2 > FRAMERATE) {
			time2 = System.nanoTime() / 1000000;
			mouse = MouseInfo.getPointerInfo().getLocation();
			p.update();
			c.run(p);
			cl.pRun(p);
			for (int i = 0; i < rects.size(); i++) {
				rects.get(i).update();
			}
		}
		repaint();
	}
}
