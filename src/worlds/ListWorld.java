package worlds;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.KeyStroke;
import javax.swing.Timer;

import adventuregame.Camera;
import adventuregame.Controller;
import adventuregame.GameObjects;
import adventuregame.HUD;
import adventuregame.HudList;
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
import adventuregame.TextCreator;

public class ListWorld extends World {
	
	private int FRAMERATE = 12;
	public String name = "menu";
	public ArrayList<RectangleObject> rects;
	private ArrayList<Text> texts;
	public Timer timer;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public Player p;
	public Main frame;
	public boolean ready = false;
	public Camera c;
	public Point mouse;
	public PlayerCollision cl;
	public Point mousecoord;
	public SaveWriter sw;
	public Controller controller;
	public Font standard = new Font("Comic Sans MS", 20 ,50);
	public HUD options;
	public RectangleCreator rc;
	public GameObjects go;
	public Mouse m;
	public TextCreator tc;
	public boolean optionsactive = false;
	protected ArrayList<HUD> huds;
	public String currentHud = "menu";
	public String lastHud;
	public HudList inv;
	
	public Thread t;
	
	public ListWorld(Main f) {
		frame = f;
		setBackground(Color.CYAN);
		setSize(dim);
		texts = new ArrayList<Text>();
		rects = new ArrayList<RectangleObject>();
	}
	
	public void switchHud(String s) {
		lastHud = currentHud;
		for (int i = 0; i < huds.size(); i++) {
			
			if (huds.get(i).id.equals(s)) {
				currentHud = huds.get(i).id;
				huds.get(i).visible = true;
				
			} else {
				huds.get(i).visible = false;
			}
			
			setBackground(Color.CYAN);
		}
	}
	
	public Player getPlayer() {
		return p;
	}
	
	public void isHudVisible(String s, boolean b) {
		for (int i = 0; i < huds.size(); i++) {
			if (huds.get(i).id.equals(s)) {
				huds.get(i).visible = b;
			}
		}
	}
	
	public void addRect(Point p, Dimension d, Color color) {
		RectangleObject ro = new RectangleObject(frame, this);
		ro.setLocation((int) p.getX(), (int) p.getY());
		ro.setSize((int) d.getWidth(), (int) d.getHeight());
		ro.setCOLOR(color);
		go.rects.add(ro);
		c.add(ro);
		cl.add(ro.getObjectRect());
	}
	
	public void addRo(RectangleObject ro) {
		go.rects.add(ro);
		c.add(ro);
		cl.add(ro.getObjectRect());
	}
	
	public SaveWriter getSw() {
		return sw;
	}
	
	public ListWorld getWorld() {
		return this;
	}
	
	public void addText(Point p, Font font, String text, String id, Color color) {
		Text txt = new Text(frame, this, text);
		txt.setCOLOR(color);
		txt.setFont(font);
		txt.setId(id);
		txt.create();
		txt.setLocation((int)p.getX(), (int)p.getY());
		go.texts.add(txt);
		c.add(txt);
	}
	
	public void run() {
		
		rc = new RectangleCreator(this);
		tc = new TextCreator(this);
		mouse = new Point();
		go =  new GameObjects(frame, this);
		options = new HUD(this);
		m = new Mouse(this, frame, options);
		m.addRc(rc);
		m.addTc(tc);
		this.addMouseListener(m);
		
		createOptions();
		
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
		addText(new Point(400, 400), new Font("Comic Sans MS", 20, 20), "hejhej", "debug", Color.WHITE);
		timer = new Timer(14, this);
		timer.start();
	}
	
	public void createOptions() {
		options.font = standard.deriveFont(10, 35);
		HudObj quit = new HudObj(50, 200, 400, 100, Color.ORANGE);
		HudObj save = new HudObj(50, 50, 400, 100, Color.ORANGE);
		HudObj colors = new HudObj(500, 50, 200, 100, Color.GRAY);
		HudObj c1 = new HudObj(750, 50, 100, 100, Color.ORANGE);
		HudObj c2 = new HudObj(850, 50, 100, 100, Color.CYAN);
		HudObj c3 = new HudObj(950, 50, 100, 100, Color.BLACK);
		HudObj c4 = new HudObj(1050, 50, 100, 100, Color.GREEN);
		HudObj mode = new HudObj(500, 200, 400, 100, Color.GRAY);
		HudObj world = new HudObj(50, 350, 400, 100, Color.ORANGE);
		HudObj noclip = new HudObj((int) (dim.getWidth() - 350), 50, 300, 100, Color.ORANGE);
		HudObj text = new HudObj((int) (dim.getWidth() - 400), 200, 350, 100, Color.ORANGE);
		inv = new HudList(new Rectangle((int) (dim.getWidth() - 400), 330, 300, 600), frame);
		inv.addBackground(Color.WHITE);
		inv.passWorld(this);
		inv.setFont(standard.deriveFont(0, 30));
		inv.setEntry(new HudObj((int) inv.getRect().getMinX(), (int)inv.getRect().getMinY(), 200, 100, Color.ORANGE));
		inv.margintop = -200;
		inv.addScrollbar();
		inv.alignEntries();
		options.hl.add(inv);
		noclip.addText("gravity");
		noclip.id = "gravity";
		world.setId("world");
		world.addText("Change Level");
		world.highlight = true;
		mode.setId("mode");
		text.id = "field";
		text.addText("items");
		c1.highlight = false;
		c2.highlight = false;
		c3.highlight = false;
		c4.highlight = false;
		c1.id = "color";
		c2.id = "color";
		c3.id = "color";
		c4.id = "color";
		colors.addText("colors:");
		colors.setId("currentcolor");
		save.addText("save stage");
		quit.addText("quit");
		quit.setId("quit");
		mode.addText("mode: " + m.ba.mode);
		options.hb.add(new HudObj(0, 0, dim.width, dim.height, new Color(0, 0, 0, (float)0.7)));
		options.hb.add(world);
		options.hb.add(text);
		options.hb.add(colors);
		options.hb.add(c1);
		options.hb.add(c2);
		options.hb.add(c3);
		options.hb.add(c4);
		options.hb.add(quit);
		options.hb.add(save);
		options.hb.add(mode);
		options.hb.add(noclip);
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		if (ready == true) {
			
			go.paint(g);
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
			go.update();
			
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
