package adventuregame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main extends JFrame {
	
	Dimension fdim;
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static boolean fullscreen = true;
	public Thread t;
	public GameEnvironment ge;
	
	public Main() {
		
	}

	public GameEnvironment get() {return ge;}
	
	public void start(Main f) {	
		this.setTitle("adventureboi");
		this.setSize((int) dim.getWidth(), (int) dim.getHeight());
		this.setResizable(!fullscreen);
		this.setUndecorated(fullscreen);
		this.setBackground(Color.BLACK);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//create and add world
		ge = new GameEnvironment(this);
		add(ge);
		ge.start();
	}
	
	public static void main(String[] args) {
		Main frame = new Main();
		frame.start(frame);

		//set icon for frame
		try {
			ImageIcon icon = new ImageIcon("assets/icon/boi.png");
			frame.setIconImage(icon.getImage());
		}
		catch (Exception e) {}
	}
}


