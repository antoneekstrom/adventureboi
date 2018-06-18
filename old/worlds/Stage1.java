package worlds;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import adventuregame.Camera;
import adventuregame.Main;
import adventuregame.Player;
import adventuregame.PlayerCollision;
import adventuregame.RectangleObject;
import adventuregame.Text;

public class Stage1 extends World {
	
	private Main frame;
	private int FRAMERATE = 24;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private Timer timer;
	private World nextworld;
	private boolean ready = false;
	
	private Player p;
	private RectangleObject g1;
	private PlayerCollision cl;
	private Text info;
	private RectangleObject r1;
	private Point mouse;
	private Camera c;
	private Point player;
	private int ptomd;
	private RectangleObject r2;
	private RectangleObject r3;
	
	public Stage1(Main f) {		
		frame = f;
	}
	
	public void run() {
		setSize(dim);
		setBackground(Color.BLUE);
		mouse = MouseInfo.getPointerInfo().getLocation();
		player = new Point();
		g1 = new RectangleObject(frame, this);
		p = new Player(frame, this);
		info = new Text(frame, this, "");
		r1 = new RectangleObject(frame, this);
		cl = new PlayerCollision(p);
		c = new Camera(dim);
		r2 = new RectangleObject(frame, this);
		r3 = new RectangleObject(frame, this);
		
		ready = true;
		
		info.type("Comic Sans MS");
		info.size(40);
		info.create();
		
		g1.setLocation((int)-dim.getWidth(), 900);
		g1.setCOLOR(Color.WHITE);
		g1.setSize((int) dim.getWidth() * 3, 100);
		
		r1.setLocation((int)mouse.getX(), (int)mouse.getY());
		r1.setSize(50, 150);
		
		r2.setLocation((int)player.getX(), (int)player.getY());
		r2.setSize(10, 10);
		
		r3.setLocation(200, 200);
		r3.setSize(50, 50);
		r3.setGravity(true);
		
		p.setGravity(true); 
		p.setLocation(100, 300);
		p.setSize(150, 125);
		p.setGRAVITY(20);
		p.JFUEL = 10;
		
		cl.add(r1.getObjectRect());
		cl.add(g1.getObjectRect());
		c.add(r1);
		c.add(g1);
		
		timer = new Timer(FRAMERATE, this);
		timer.start();
		startPlayerController(p);
	}
	
	public void paintComponent(Graphics g) {
		//if statement to wait for objects to initialize
		if (ready == true) {
			super.paintComponent(g);
			g1.paint(g);
			info.paint(g);
			r1.paint(g);
			p.paint(g);
			r2.paint(g);
			r3.paint(g);
		}
		
	}
	
	public void actionPerformed(ActionEvent arg0) {
		mouse = MouseInfo.getPointerInfo().getLocation();
		player.setLocation(p.getX(), p.getY());
		ptomd = (int) mouse.distance(player);
		r2.setLocation((int)player.getX(), (int)player.getY());
		//c.run(p);
		p.update();
		cl.pRun(p);
		r2.update();
		info.setLocation(0, 50);
		info.text("distance:" + ptomd);
		g1.update();
		r1.update();
		r3.update();
		repaint();
	}
	
	public void addNext(World w) {
		nextworld = w;
	}
	
	public void next() {
		frame.add(nextworld);
		timer.stop();
		nextworld.run();
		frame.revalidate();
		frame.repaint();
		frame.remove(this);
	}
}
