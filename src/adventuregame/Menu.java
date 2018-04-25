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
	public String name = "menu";
	public HUD menu;
	public Mouse m2;
	public Font standard = new Font("Comic Sans MS", 20 ,50);
	public HUD levels;
	public Point mouse;
	public SaveWriter sw;
	boolean hasSwitched = false;
	boolean ready = false;
	
	public Menu(Main f) {
		super(f);
		frame = f;
	}
	
	public void switchHud(String s) {
		for (int i = 0; i < huds.size(); i++) {
			
			if (huds.get(i).id.equals(s)) {
				huds.get(i).visible = true;
				System.out.println(true);
				
			} else {
				huds.get(i).visible = false;
				System.out.println(false);
			}
			setBackground(Color.CYAN);
		}
	}
	
	public void run() {
		
		setSize(dim);
		setBackground(Color.CYAN);
		sw = new SaveWriter("menu");
		huds = new ArrayList<HUD>();
		
		menu = new HUD(this);
		menu.id = "menu";
		levels = new HUD(this);
		levels.id = "levels";
		options = new HUD(this);
		options.id = "options";
		huds.add(menu);
		huds.add(levels);
		
		m = new Mouse(this, frame, menu);
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
		back.setId("backtomenu");
		back.setFont(standard);
		llist.addBackground(Color.WHITE);
		llist.addScrollbar();
		levels.hl.add(llist);
		levels.hb.add(back);
		llist.setFont(standard);
		llist.setEntry(new HudObj( ((int) llist.rect.getMinX()), (int) llist.rect.getMinY(), (int) (llist.rect.width * 0.7), 100, Color.ORANGE));
		llist.addEntry("lw", "lw");
		llist.addEntry("world1", "world1");
		llist.addEntry("world2", "id");
		llist.addEntry("world3", "id");
		llist.addEntry("world4", "id");
		llist.addEntry("world5", "id");
		llist.alignEntries();
		llist.huds.add(levels);
		llist.huds.add(menu);
		switchHud("menu");
		ready = true;
		timer = new Timer(14, this);
		p.setGravity(true);
		p.setLocation(0, 100);
		p.setSize(150, 125);
		p.setGRAVITY(30);
		p.isEnabled(true);
		startPlayerController(p);
		p.JFUEL = 7;
		timer.start();
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		if (ready == true) {
			menu.paint(g);
			levels.paint(g);
			go.paint(g);
			if (p.enabled == true) {
				p.paint(g);
			}
		}
	}

	double time1, time2;
	int FRAMERATE = 12;
	public void actionPerformed(ActionEvent arg0) {
		time1 = System.nanoTime() / 1000000;
		if (time1 - time2 > FRAMERATE) {
			time2 = System.nanoTime() / 1000000;
			
			p.update();
			menu.update();
			levels.update();
			c.run(p);
			go.update();
			cl.pRun(p);
			
			if (hasSwitched == true) {
				hasSwitched = false;
				frame.lw.timer.stop();
			}
			
			mouse = MouseInfo.getPointerInfo().getLocation();
			repaint();
		}
	}
}
