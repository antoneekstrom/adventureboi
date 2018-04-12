package adventuregame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import worlds.IntroductionWorld;
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
		IntroductionWorld intro = new IntroductionWorld(f);
		TutorialWorld tut = new TutorialWorld(f);
		Stage1  st1 = new Stage1(f);
		add(intro);
		intro.run();
		intro.addNext(tut);
	}
	
	public static void main(String[] args) {
		Main frame = new Main();
		frame.start(frame);
	}
}

