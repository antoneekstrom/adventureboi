package adventuregame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import worlds.IntroductionWorld;
import worlds.ListWorld;
import worlds.Stage1;
import worlds.TutorialWorld;

public class Main extends JFrame {
	
	Dimension fdim;
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static boolean fullscreen = true;
	
	public Main() {
		
	}
	
	public void start(Main f) {	
		this.setTitle("Epic game");
		this.setSize((int) dim.getWidth(), (int) dim.getHeight());
		this.setResizable(!fullscreen);
		this.setUndecorated(fullscreen);
		this.setBackground(Color.BLACK);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//create and add world
		ListWorld lw = new ListWorld(f);
		Menu m = new Menu(f);
		add(m);
		add(lw);
		m.run();
	}
	
	public static void main(String[] args) {
		Main frame = new Main();
		frame.start(frame);
	    /*Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            System.out.println("In shutdown hook");
	        }
	    }, "Shutdown-thread"));*/
	}
}


