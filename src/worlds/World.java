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
