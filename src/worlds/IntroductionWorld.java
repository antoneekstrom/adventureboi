package worlds;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import javax.swing.Timer;

import adventuregame.Camera;
import adventuregame.Door;
import adventuregame.Main;
import adventuregame.Player;
import adventuregame.PlayerCollision;
import adventuregame.RectangleObject;
import adventuregame.Spike;
import adventuregame.Text;

public class IntroductionWorld extends World implements ActionListener, ImageObserver {

	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private Main frame;
	private int FRAMERATE = 12; /* milliseconds, refresh timer */
	public Timer timer;
	private World nextworld;
	//objects
	private Text t1;
	private RectangleObject g1;
	private Text t2;
	private Player p;
	private PlayerCollision cl;
	private RectangleObject r1;
	private Door d1;
	private Text t3;
	private Text t4;
	private Camera c;
	private Spike sp1;
	//colors
	private Color sky = new Color(142, 185, 255);
	
	//add fire to bottom of jumping boi
	//fix hyperspeed
	
	public IntroductionWorld(Main setframe) {		
		frame = setframe;
		System.out.println("created");
	}
	
	public void run() {
		System.out.println("ran");
		
		p = new Player(frame, this);
		t2 = new Text(frame, this, "Welcome to");
		t1 = new Text(frame, this,"the epic adventures of boiman");
		g1 = new RectangleObject(frame, this);
		cl = new PlayerCollision(p);
		r1 = new RectangleObject(frame, this);
		d1 = new Door(frame, this, p);
		t3 = new Text(frame, this, "level 1");
		t4 = new Text(frame, this, "press down to enter");
		c = new Camera(dim);
		sp1 = new Spike(frame, this);
		
		//text obj
		t1.type("Comic Sans MS");
		t1.setSize(100, 20);
		t1.setLocation(700, 200);
		t1.size(60);
		t1.setGravity(true);
		t1.setGRAVITY(4);
		t1.setCOLOR(Color.BLACK);
		t1.create();
		
		t2.copyText(t1);
		t2.setSize(100, 20);
		t2.text("welcome to");
		t2.setLocation(300, 200);
		t2.setGravity(true);
		t2.create();
		
		t4.size(30);
		t4.create();
		t4.setLocation(1250, 530);
		
		//ground
		g1.setSize((int) dim.getWidth(), (int) (dim.getHeight()));
		g1.setLocation(0, 600);
		g1.setCOLOR(Color.WHITE);
		cl.add(g1.getObjectRect());
		//rectangle
		r1.setSize((int) dim.getWidth(), 50);
		r1.setLocation(0, 800);
		r1.setCOLOR(Color.WHITE);
		
		sp1.setSize(150, 150);
		sp1.setLocation(0, 450);
		sp1.setGravity(false);
		
		d1.setSize(160, 200);
		d1.setCOLOR(Color.BLACK);
		d1.setLocation(1000, (int) g1.getObjectRect().getMinY() - d1.getHeight());
		//player
		p.setGravity(true);
		p.setLocation(100, 100);
		p.setSize(150, 125);
		p.setGRAVITY(30);
		p.JFUEL = 14;
		//order dependant calls
		c.add(g1);
		c.add(t1);
		c.add(t2);
		c.add(t3);
		c.add(t4);
		c.add(d1);
		c.add(r1);
		c.add(sp1);
		t3.setLocation(1000, (int) d1.getObjectRect().getMinY() - 20);
		setSize(dim);
		setBackground(sky);
		//Start timer
		timer = new Timer(14, this);
		timer.start();
		startPlayerController(p);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g1.paint(g);
		r1.paint(g);
		t1.paint(g);
		t3.paint(g);
		t2.paint(g);
		d1.paint(g);
		t4.paint(g);
		p.paint(g);
		sp1.paint(g);
	}

	//timer
	public void actionPerformed(ActionEvent arg0) {
		t3.setLocation(p.getX(), p.getY());
		t3.text("onground:" + p.onground);
		t1.update();
		t2.update();
		g1.update();
		r1.update();
		p.update();
		d1.update();
		cl.pRun(p);
		r1.checkCollision(t1);
		r1.checkCollision(t2);
		c.run(p);
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
