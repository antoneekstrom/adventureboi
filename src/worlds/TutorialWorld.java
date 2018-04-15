package worlds;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import javax.swing.Timer;

import adventuregame.Camera;
import adventuregame.Main;
import adventuregame.Player;
import adventuregame.PlayerCollision;
import adventuregame.RectangleObject;
import adventuregame.Shoot;
import adventuregame.Spike;
import adventuregame.Text;

public class TutorialWorld extends World implements ActionListener, ImageObserver {

	private Main frame;
	String levelname;
	private int FRAMERATE = 12;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private Timer timer;
	//objects
	public Camera c;
	
	private Player p;
	private RectangleObject r1;
	private RectangleObject g1;
	private RectangleObject r2;
	private PlayerCollision cl;
	private Text t1;
	private Spike sp1;
	//colors
	private Color sky = new Color(142, 185, 255);
	
	public TutorialWorld(Main f) {
		frame = f;
	}
	
	public void run() {
		SIZEMOD = 1;
		setBackground(sky);
		setSize(dim);
		g1 = new RectangleObject(frame, this);
		p = new Player(frame, this);
		r1 = new RectangleObject(frame, this);
		r2 = new RectangleObject(frame, this);
		c = new Camera(dim);
		t1 = new Text(frame, this, "hold UP to jump");
		cl = new PlayerCollision(p);
		sp1 = new Spike(frame, this);
		//text obj
		t1.setCOLOR(Color.BLACK);
		t1.type("Comic Sans MS");
		t1.size(50);
		t1.create();
		t1.setLocation(1200,  300);
		//ground
		g1.setSize((int) dim.getWidth() * 3, (int) dim.getHeight());
		g1.setLocation(0, 600);
		g1.setCOLOR(Color.WHITE);
		//rectangle
		r1.setLocation(500, 100);
		r1.setSize(200, 200);
		r1.setGravity(true);
		
		sp1.setLocation(1500, 100);
		sp1.setSize(100, 100);
		sp1.setGravity(true);
		
		r2.setLocation(1000, 100);
		r2.setSize(100, 300);
		r2.setGravity(true);
		
		//player
		p.setGravity(true);
		p.setLocation(100, 100);
		p.setSize(150, 125);
		p.setGRAVITY(30);
		p.JFUEL = 7;
		//collision
		c.add(g1);
		c.add(r1);
		c.add(r2);
		c.add(t1);
		c.add(sp1);
		cl.add(sp1.getObjectRect());
		cl.add(g1.getObjectRect());
		cl.add(r1.getObjectRect());
		cl.add(r2.getObjectRect());
		//Start timer
		timer = new Timer(14, this);
		timer.start();
		startPlayerController(p);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g1.paint(g);
		r2.paint(g);
		r1.paint(g);
		t1.paint(g);
		p.paint(g);
		sp1.paint(g);
	}

	//timer
	public void actionPerformed(ActionEvent arg0) {
		t1.update();
		r1.update();
		sp1.update();
		r2.update();
		sp1.update(p);
		g1.checkCollision(r1);
		g1.checkCollision(r2);
		g1.checkCollision(sp1);
		p.update();
		cl.pRun(p);
		c.run(p);
		repaint();
	}
}