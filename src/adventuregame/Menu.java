package adventuregame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;


import javax.swing.Timer;

import worlds.ListWorld;

public class Menu extends ListWorld implements ActionListener {
	
	public Timer timer;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public Main frame;
	public Mouse m2;
	public Font standard = new Font("Comic Sans MS", 20 ,50);
	public HUD menu;
	public HUD levels;
	public HUD console;
	public HUD actualhud;
	public Point mouse;
	public SaveWriter sw;
	boolean ready = false;
	Color black = new Color(0, 0, 0, 150);
	
	
	boolean optionsactive = false;
	
	public Menu(Main f) {
		super(f);
		frame = f;
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
	
	public void isHudVisible(String s, boolean b) {
		for (int i = 0; i < huds.size(); i++) {
			if (huds.get(i).id.equals(s)) {
				huds.get(i).visible = b;
			}
		}
	}
	
	public void run() {
		
		lastHud = "menu";
		typelistener = new TypeListener(this);
		addKeyListener(typelistener);
		
		
		setSize(dim);
		setBackground(Color.CYAN);
		sw = new SaveWriter("menu");
		huds = new ArrayList<HUD>();
		
		actualhud = new HUD(this);
		actualhud.id = "actualhud";
		menu = new HUD(this);
		menu.id = "menu";
		levels = new HUD(this);
		levels.id = "levels";
		options = new HUD(this);
		options.id = "options";
		huds.add(actualhud);
		huds.add(menu);
		huds.add(levels);
		huds.add(options);
		
		console = new HUD(this);
		console.id = "console";
		
		rc = new RectangleCreator(this);
		tc =  new TextCreator(this);
		this.setFocusable(true);
		
		m = new Mouse(this, frame, menu);
		m.ba.huds.add(options);
		m.ba.huds.add(levels);
		m.addRc(rc);
		m.addTc(tc);
		addMouseListener(m);
		
		c = new Camera(dim);
		go = new GameObjects(frame, this);
		p = new Player(frame, this);
		cl = new PlayerCollision(p);
		
		createOptions();
		
		HudObj start = new HudObj((dim.width / 2) -300, 400, 600, 100, Color.ORANGE);
		HudObj exit = new HudObj((dim.width / 2) -300, 550, 600, 100, Color.ORANGE);
		
		start.setFont(standard);
		start.addText("start");
		start.setId("start");
		exit.setFont(standard);
		exit.addText("exit");
		exit.setId("quit");
		
		HudBar hp = new HudBar((int) ((dim.width / 2) - 200), 100, 400, 50);
		HudBar ep = new HudBar(50, 100, 400, 50);
		HudText debug = new HudText(50, 400, "debug", standard);
		HudText cfield = new HudText(0, (int) dim.getHeight() - 100, "", standard.deriveFont(40f));
		debug.setId("debug");
		actualhud.ht.add(debug);
		hp.setText("Health");
		hp.fg = Color.RED;
		hp.modifier = 1;
		hp.setId("hp");
		ep.tc = Color.WHITE;
		ep.tf = standard.deriveFont(40f);
		ep.fg = Color.BLUE;
		ep.xoffset = -120;
		ep.bg = Color.WHITE;
		ep.setText("Energy");
		ep.setId("energy");
		cfield.setId("console");
		cfield.setTextColor(Color.WHITE);
		cfield.setBackground(black, 0, (int) dim.getWidth());
		
		/*HudText cval = new HudText(0, (int) dim.getHeight() - 170, "", standard.deriveFont(40f));
		cval.setBackground(black, 0, (int) dim.getWidth());
		cval.setId("consoleresponse");
		cval.autoWidth(true);
		console.ht.add(cval);*/
		List response = new List(new Rectangle(0, dim.height - 700, 800, 500), "text");
		HudText responseentry = new HudText(0, 0, "", standard);
		responseentry.text = "response entry";
		responseentry.textcolor = Color.WHITE;
		response.setBackground(black);
		response.hasEntry = true;
		response.setSpacing(10);
		response.setPadding(15);
		response.setPaddingTop(40);
		response.setTextEntry(responseentry);
		response.addEntry("");
		response.addEntry("");
		response.addEntry("");
		response.addEntry("");
		response.addEntry("");
		response.addEntry("");
		response.addEntry("");
		response.id = "response";
		
		console.ht.add(cfield);
		console.lists.add(response);
		actualhud.hbr.add(ep);
		actualhud.hbr.add(hp);
		actualhud.visible = true;
		
		RectangleObject spike = new RectangleObject(frame, this);
		go.rects.add(spike);
		
		menu.hb.add(exit);
		menu.hb.add(start);
		menu.visible = true;
		levels.visible = false;
		menu.ht.add(new HudText((dim.width /2) - 240, 200, "adventureboi", standard.deriveFont(0, 80)));
		HudList llist = new HudList(new Rectangle((dim.width / 2) - 400, 100, 800, 900), frame);
		levels.ht.add(new HudText(50, 50, "rects", standard));
		llist.passWorld(this);
		llist.passSw(sw);
		llist.huds.add(menu);
		llist.huds.add(levels);
		HudObj back = new HudObj(100, 100, 200, 100, Color.ORANGE);
		back.highlight = true;
		back.addText("back");
		back.setId("back");
		back.setFont(standard);
		llist.addBackground(Color.WHITE);
		llist.addScrollbar();
		levels.hl.add(llist);
		levels.hb.add(back);
		llist.setFont(standard);
		llist.setEntry(new HudObj( ((int) llist.rect.getMinX()), (int) llist.rect.getMinY(), (int) (llist.rect.width * 0.7), 100, Color.ORANGE));
		llist.addEntry("lw", "lw");
		llist.margintop = 200;
		llist.addEntry("world1", "world1");
		llist.addEntry("world2", "world2");
		llist.addEntry("world3", "id");
		llist.addEntry("world4", "id");
		llist.addEntry("world5", "id");
		llist.alignEntries();
		llist.huds.add(levels);
		llist.setId("levels");
		llist.huds.add(menu);
		switchHud("menu");
		ready = true;
		timer = new Timer(14, this);
		p.setGravity(true);
		p.setLocation(0, 100);
		p.setSize(150, 125);
		p.setGRAVITY(30);
		startPlayerController(p);
		p.JFUEL = 7;
		timer.start();
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		if (ready == true) {
			go.paint(g);
			if (p.enabled == true) {
				p.paint(g);
			}
			menu.paint(g);
			levels.paint(g);
			console.paint(g);
			options.paint(g);
			actualhud.paint(g);
		}
	}

	double time1, time2;
	int FRAMERATE = 12;
	public void actionPerformed(ActionEvent arg0) {
		time1 = System.nanoTime() / 1000000;
		if (time1 - time2 > FRAMERATE) {
			time2 = System.nanoTime() / 1000000;
			
			m.ba.passSaveWriter(sw);
			p.passWorld(this);
			p.update();
			menu.update();
			levels.update();
			options.update();
			actualhud.passPlayer(p);
			actualhud.update();
			console.update();
			c.run(p);
			go.update();
			cl.pRun(p);
			
			mouse = MouseInfo.getPointerInfo().getLocation();
			repaint();
		}
	}
}
