package worlds;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import adventuregame.Camera;
import adventuregame.Controller;
import adventuregame.HUD;
import adventuregame.HudObj;
import adventuregame.Main;
import adventuregame.MethodAction;
import adventuregame.Mouse;
import adventuregame.Player;
import adventuregame.PlayerAction;
import adventuregame.PlayerCollision;
import adventuregame.PlayerJump;
import adventuregame.RectangleCreator;
import adventuregame.RectangleObject;
import adventuregame.SaveWriter;
import adventuregame.Text;

public class ListWorld extends World {
	
	private int FRAMERATE = 12;
	public String name = "world1";
	public ArrayList<RectangleObject> rects;
	private ArrayList<Text> texts;
	private Timer timer;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public Player p;
	private Main frame;
	private boolean ready = false;
	public Camera c;
	public Point mouse;
	private PlayerCollision cl;
	public Point mousecoord;
	public SaveWriter sw;
	public Controller controller;
	public HUD options;
	public RectangleCreator rc;
	
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
		
		rc = new RectangleCreator(this);
		MouseListener m = new Mouse(this, frame, rc);
		this.addMouseListener(m);
		mouse = new Point();
		
		options = new HUD();
		HudObj quit = new HudObj(50, 200, 400, 100, Color.ORANGE);
		HudObj save = new HudObj(50, 50, 400, 100, Color.ORANGE);
		HudObj colors = new HudObj(500, 50, 200, 100, Color.GRAY);
		HudObj c1 = new HudObj(750, 50, 100, 100, Color.ORANGE);
		HudObj c2 = new HudObj(850, 50, 100, 100, Color.CYAN);
		HudObj c3 = new HudObj(950, 50, 100, 100, Color.BLACK);
		HudObj c4 = new HudObj(1050, 50, 100, 100, Color.GREEN);
		c1.highlight = false;
		c2.highlight = false;
		c3.highlight = false;
		c4.highlight = false;
		colors.highlight = false;
		colors.addText("colors:");
		save.addText("save stage");
		quit.addText("quit");
		
		options.hb.add(new HudObj(0, 0, dim.width, dim.height, new Color(0, 0, 0, (float)0.7)));
		options.hb.add(colors);
		options.hb.add(c1);
		options.hb.add(c2);
		options.hb.add(c3);
		options.hb.add(c4);
		options.hb.add(quit);
		options.hb.add(save);
		
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
				rects.get(i).setCOLOR(rects.get(i).getCOLOR());
				rects.get(i).paint(g);
			}
			p.paint(g);
			options.paint(g);
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
			
			options.update();
			
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
	
	public void stopPlayerController() {
		this.getInputMap().clear();
		this.getActionMap().clear();
	}
}
