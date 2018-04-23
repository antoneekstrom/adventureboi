package adventuregame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
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
	
	public Menu(Main f) {
		super(f);
		frame = f;
	}
	
	public void run() {
		
		setSize(dim);
		setBackground(Color.CYAN);
		SaveWriter sw = new SaveWriter("menu");
		sw.loadWorld(this);
		
		rc = new RectangleCreator(this);
		tc = new TextCreator(this);
		m = new Mouse(this, frame, rc, tc);
		menu = new HUD(this);
		
		HudObj start = new HudObj((dim.width / 2) -300, 400, 600, 100, Color.ORANGE);
		HudObj exit = new HudObj((dim.width / 2) -300, 550, 600, 100, Color.ORANGE);
		
		start.setFont(new Font("Comic Sans MS", 20, 35));
		start.addText("start");
		exit.setFont(new Font("Comic Sans MS", 20, 35));
		exit.addText("exit");
		exit.setId("quit");
		
		menu.hb.add(exit);
		menu.hb.add(start);
		menu.visible = true;
		menu.ht.add(new HudText((dim.width /2) - 200, 200, "adventureboi", new Font("Comic Sans MS", 20, 65)));
		
		timer = new Timer(14, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		menu.paint(g);
	}

	double time1, time2;
	int FRAMERATE = 12;
	public void actionPerformed(ActionEvent arg0) {
		time1 = System.nanoTime() / 1000000;
		if (time1 - time2 > FRAMERATE) {
			time2 = System.nanoTime() / 1000000;
			menu.update();
			mouse = MouseInfo.getPointerInfo().getLocation();
			repaint();
		}
	}
}
