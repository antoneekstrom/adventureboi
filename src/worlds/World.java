package worlds;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import adventuregame.Main;
import adventuregame.MethodAction;
import adventuregame.Player;
import adventuregame.PlayerAction;
import adventuregame.PlayerJump;
import adventuregame.RectangleObject;
import adventuregame.SaveWriter;

/** Basic template for worlds/stages */
public class World extends JPanel implements ActionListener {

	
	//key mappings, Left:name, Right:event
	int right = 68;
	int left = 65;
	int up1 = 87;
	int up2 = 32;
	int down = 83;
	int esc = 27;
	
	public double SIZEMOD;
	public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private World nextworld;
	private Timer timer;
	private Main frame;
	public SaveWriter sw;
	String levelname;
	private int FRAMERATE = 24; /* milliseconds, refresh timer */ 
	
	public void startTimer() {
		timer = new Timer(FRAMERATE, this);
		timer.start();
	}
	
	public void run() {
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
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
	}
	
	public void actionPerformed(ActionEvent e) {
		
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
