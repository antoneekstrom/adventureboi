package worlds;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.KeyStroke;
import javax.swing.Timer;

import adventuregame.Camera;
import adventuregame.Controller;
import adventuregame.Main;
import adventuregame.MethodAction;
import adventuregame.Mouse;
import adventuregame.Player;
import adventuregame.PlayerAction;
import adventuregame.PlayerCollision;
import adventuregame.PlayerJump;
import adventuregame.RectangleObject;
import adventuregame.SaveWriter;
import adventuregame.Text;

public class ListWorld extends World {
	
	private int FRAMERATE = 12;
	public String name = "lw";
	public ArrayList<RectangleObject> rects;
	private ArrayList<Text> texts;
	private Timer timer;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public Player p;
	private Main frame;
	private boolean ready = false;
	private Camera c;
	public Point mouse;
	private PlayerCollision cl;
	public Point mousecoord;
	public SaveWriter sw;
	public Controller controller;
	
	public ListWorld(Main f) {
		frame = f;
		setBackground(Color.CYAN);
		setSize(dim);
		texts = new ArrayList<Text>();
		rects = new ArrayList<RectangleObject>();
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public void addRectp(Point point1, Point point2) {
		RectangleObject ro = new RectangleObject(frame, this);
		
	}
	
	public void addRect(Point p, Dimension d, Color color) {
		RectangleObject ro = new RectangleObject(frame, this);
		ro.setLocation((int) p.getX(), (int) p.getY());
		ro.setSize((int) d.getWidth(), (int) d.getHeight());
		ro.setCOLOR(color);
		rects.add(ro);
		c.add(ro);
		cl.add(ro.getObjectRect());
	}
	
	public void addText(Point p, String font, int size, String text, Color color, String id) {
		Text txt = new Text(frame, this, text);
		txt.setCOLOR(color);
		txt.type(font);
		txt.size(size);
		txt.setId(id);
		txt.create();
		txt.setLocation((int)p.getX(), (int)p.getY());
		texts.add(txt);
		c.add(txt);
	}
	
	public void run() {
		MouseListener m = new Mouse(this);
		this.addMouseListener(m);
		mouse = new Point();
		mousecoord = new Point();
		sw = new SaveWriter(name);
		p = new Player(frame, this);
		c = new Camera(dim);
		cl = new PlayerCollision(p);
		sw.loadWorld(this);
		startPlayerController(p);
		p.setGravity(true);
		p.setLocation(0, 100);
		p.setSize(150, 125);
		p.setGRAVITY(30);
		p.JFUEL = 7;
		ready = true;
		c.add(p);
		addText(new Point(400, 400), new String("Comic Sans MS"), 42, new String("hejehje"), Color.WHITE, "debug");
		timer = new Timer(14, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		if (ready == true) {
			
			for (int i = 0; i < texts.size(); i++) {
				texts.get(i).paint(g);
			}
			
			for (int i = 0; i < rects.size(); i++) {
				rects.get(i).setCOLOR(Color.GREEN);
				rects.get(i).paint(g);
			}
			p.paint(g);
		}
	}
	
	double time1, time2;
	public void actionPerformed(ActionEvent arg0) {
		time1 = System.nanoTime() / 1000000;
		if (time1 - time2 > FRAMERATE) {
			time2 = System.nanoTime() / 1000000;
			mouse = MouseInfo.getPointerInfo().getLocation();
			mousecoord.setLocation(mouse.getX() + c.getD2c() - 200, mouse.getY() - 200);
			p.update();
			c.run(p);
			cl.pRun(p);
			
			for (int i = 0; i < texts.size(); i++) {
				texts.get(i).update();

				if (texts.get(i).getId() == "debug") {
					texts.get(i).text(String.valueOf(mousecoord) + " rects:" + rects.size());
				}
			}
			for (int i = 0; i < rects.size(); i++) {
				rects.get(i).update();
			}
		}
		repaint();
	}
	
	//player controller
	public void startPlayerController(Player p) {
		//right
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(right, 0, true), "rightreleased");
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(right, 0, false), "rightpressed");
		this.getActionMap().put("rightreleased", new PlayerAction("rightreleased", p, frame));
		this.getActionMap().put("rightpressed", new PlayerAction("rightpressed", p, frame));
		//left
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(left, 0, true), "leftreleased");
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(left, 0, false), "leftpressed");
		this.getActionMap().put("leftreleased", new PlayerAction("leftreleased", p, frame));
		this.getActionMap().put("leftpressed", new PlayerAction("leftpressed", p, frame));
		//up
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(up1, 0, true), "upreleased");
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(up1, 0, false), "uppressed");
		this.getActionMap().put("uppressed", new PlayerJump("uppressed", p));
		this.getActionMap().put("upreleased", new PlayerJump("upreleased", p));
		//down
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(down, 0, true), "dr");
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(down, 0, false), "dp");
		this.getActionMap().put("dr", new PlayerJump("dr", p));
		this.getActionMap().put("dp", new PlayerJump("dp", p));
		//esc
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(esc, 0, true), "escr");
		this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(esc, 0, false), "escp");
		this.getActionMap().put("escp", new MethodAction("escp", this));
		this.getActionMap().put("escr", new MethodAction("escr", this));
	}
}
