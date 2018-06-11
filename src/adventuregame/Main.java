package adventuregame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import worlds.ListWorld;

public class Main extends JFrame {
	
	Dimension fdim;
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static boolean fullscreen = true;
	public Menu m;
	public Thread t;
	
	public Main() {
		
	}
	
	public void start(Main f) {	
		this.setTitle("adventureboi");
		this.setSize((int) dim.getWidth(), (int) dim.getHeight());
		this.setResizable(!fullscreen);
		this.setUndecorated(fullscreen);
		this.setBackground(Color.BLACK);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//create and add world
		GameEnvironment m = new GameEnvironment(this);
		add(m);
		m.start();
	}
	
	public static void main(String[] args) {
		Main frame = new Main();
		frame.start(frame);

		//set icon for frame
		try {
			ImageIcon icon = new ImageIcon("assets/animated_sprites/aboi/manboi.png");
			frame.setIconImage(icon.getImage());
		}
		catch (Exception e) {}
	}
}


