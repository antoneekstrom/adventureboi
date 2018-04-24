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
	
	public void run() {
		
		setSize(dim);
		setBackground(Color.CYAN);
		
		menu = new HUD(this);
		levels = new HUD(this);
		sw = new SaveWriter("menu");
		m = new Mouse(this, frame, menu);
		addMouseListener(m);
		
		c = new Camera(dim);
		go = new GameObjects(frame, this);
		p = new Player(frame, this);
		cl = new PlayerCollision(p);
		sw.setWorld("world1", this);
		sw.loadWorld(this);
		
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
		llist.passWorld(this);
		HudObj back = new HudObj(100, 100, 200, 100, Color.CYAN);
		back.setHighlightColor(new Color(150, 255, 255));
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
		ready = true;
		timer = new Timer(14, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		if (ready == true) {
			menu.paint(g);
			levels.paint(g);
		}
	}

	double time1, time2;
	int FRAMERATE = 12;
	public void actionPerformed(ActionEvent arg0) {
		time1 = System.nanoTime() / 1000000;
		if (time1 - time2 > FRAMERATE) {
			time2 = System.nanoTime() / 1000000;
			
			menu.update();
			levels.update();
			
			if (menu.visible == false) {
				levels.visible = true;
			}
			
			if (hasSwitched == true) {
				hasSwitched = false;
				frame.lw.timer.stop();
			}
			
			mouse = MouseInfo.getPointerInfo().getLocation();
			repaint();
		}
	}
}
