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
		this.requestFocus();
		
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
		
		HudObj start = new HudObj((dim.width / 2) -300, 550, 600, 100, Color.ORANGE);
		HudObj exit = new HudObj((dim.width / 2) -300, 700, 600, 100, Color.ORANGE);
		HudObj play = new HudObj((dim.width / 2) -300, 400, 600, 100, Color.ORANGE);
		
		start.setFont(standard);
		start.addText("Custom levels");
		start.setId("start");
		exit.setFont(standard);
		exit.addText("Quit");
		exit.setId("quit");
		play.addText("Play");
		play.setId("play");
		menu.hb.add(play);
		
		HudBar hp = new HudBar((int) ((dim.width / 2) - 200), 100, 400, 50);
		HudBar ep = new HudBar(50, 100, 400, 50);
		HudBar sp = new HudBar(50, 250, 400, 50);
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
		sp.setText("Stamina");
		sp.xoffset = -110;
		sp.tf = standard.deriveFont(40f);
		sp.fg = Color.GREEN;
		sp.setId("stamina");
		sp.bg = Color.WHITE;
		ep.setId("energy");
		cfield.setId("console");
		cfield.setTextColor(Color.WHITE);
		cfield.setBackground(black, 0, (int) dim.getWidth());
		
		List response = new List(new Rectangle(0, dim.height - 700, 800, 500), "text", this);
		HudText responseentry = new HudText(0, 0, "", standard);
		responseentry.text = "response entry";
		responseentry.textcolor = Color.ORANGE;
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
		actualhud.hbr.add(sp);
		actualhud.visible = true;
		
		RectangleObject spike = new RectangleObject(frame, this);
		go.rects.add(spike);
		
		llist = new List(new Rectangle(dim.width / 2 - 600, dim.height / 2 - 450, 1200, 1000), "text", this);
		HudText llistentry = new HudText(0,0,"",standard);
		llistentry.setBackground(Color.ORANGE, 0, 450);
		llistentry.padding = 25;
		llistentry.setTextColor(Color.WHITE);
		llistentry.update = true;
		llistentry.type = "level";
		llistentry.hover = true;
		llist.setTextEntry(llistentry);
		llist.scrollBar();
		llist.setHideOverflow(true, 50);
		llist.id = "levels";
		llist.setPadding(380);
		llist.setPaddingTop(150);
		llist.setSpacing(50);
		llist.addEntryList(sw.getWorldList());
		llist.addIdList(sw.getWorldList());
		llist.fill();
		levels.lists.add(llist);
		HudObj newlevel = new HudObj(dim.width - 300, dim.height - 150, 250, 100, Color.ORANGE);
		HudObj deletelevel = new HudObj(dim.width - 300, dim.height - 300, 250, 100, Color.ORANGE);
		newlevel.addText("new level");
		newlevel.setId("newlevel");
		deletelevel.setId("deletelevel");
		deletelevel.addText("delete");
		levels.hb.add(newlevel);
		levels.hb.add(deletelevel);
		
		menu.hb.add(exit);
		menu.hb.add(start);
		menu.visible = true;
		levels.visible = false;
		menu.ht.add(new HudText((dim.width /2) - 240, 200, "adventureboi", standard.deriveFont(0, 80)));
		levels.ht.add(new HudText(50, 50, "rects", standard));
		HudObj back = new HudObj(100, 100, 200, 100, Color.ORANGE);
		back.highlight = true;
		back.addText("back");
		back.setId("back");
		back.setFont(standard);
		levels.hb.add(back);
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
			
			name = sw.getWorld();
			m.ba.passSaveWriter(sw);
			llist.passSw(sw);
			p.passWorld(this);
			p.update();
			menu.update();
			levels.update();
			options.update();
			actualhud.passPlayer(p);
			actualhud.update();
			console.update();
			console.passWorld(this);
			c.run(p);
			go.update();
			cl.pRun(p);
			
			if (typelistener.c.saving) {
				typelistener.c.saving = false;
				sw.writeList(go);
			}
			levels.passSw(sw);
			
			mouse = MouseInfo.getPointerInfo().getLocation();
			repaint();
		}
	}
}
