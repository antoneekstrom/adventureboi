package worlds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import javax.swing.Timer;

import adventuregame.Main;
import adventuregame.Player;

/** extends this for new semi-blank world that functions at the very least, hopefully.
 *  and also copy code for template
 *  */
public class Blank extends World implements ActionListener, ImageObserver {

	private Main frame;
	String levelname;
	private int FRAMERATE = 24; /* milliseconds, refresh timer */
	//objects
	private Player p;
	//colors
	private Color sky = new Color(142, 185, 255);
	
	public Blank(Main setframe) {
		frame = setframe;
		setBackground(sky);
		
	}
	
	public void run() {
		p = new Player(frame, this);
		//text obj
		//ground
		//rectangle
		//player
		p.setGravity(true);
		p.setLocation(100, 100);
		p.setSize(150, 125);
		p.setGRAVITY(20);
		p.JFUEL = 35;
		
		//Start timer
		Timer timer = new Timer(FRAMERATE, this);
		timer.start();
		startPlayerController(p);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		p.paint(g);
	}

	//timer
	public void actionPerformed(ActionEvent arg0) {
		p.update();
		repaint();
	}
}
