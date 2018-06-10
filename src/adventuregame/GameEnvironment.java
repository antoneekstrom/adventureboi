package adventuregame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;
import UI.UIManager;

import gamelogic.NewCamera;
import gamelogic.NewObjectStorage;
import objects.NewPlayer;


public class GameEnvironment extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    int FRAMERATE = 12;
    int TIMER_DELAY = 14;
    Timer timer;
    SaveWriter sw;
    JPanel input;

    public GameEnvironment() {
    }

    public void start() {
        setSize(GlobalData.getScreenDim());
        setBackground(Color.CYAN);
        sw = new SaveWriter("menu");
        sw.loadWorld(this);
        
        Images.indexAllImages();
		Images.loadAllImages();
        
        input = Input.start();
        add(input);
        this.setFocusable(true);
		this.requestFocus();
        
		NewCamera.setCameraPos(new Point(0, 0));
		NewObjectStorage.newPlayer();
		NewPlayer player1 = NewObjectStorage.getPlayer(1);
        System.out.println("player: " + NewObjectStorage.getObjectList().indexOf(player1));
        
        timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        NewObjectStorage.paint(g);
        UIManager.paint(g);
    }

	double time1, time2;
	public void actionPerformed(ActionEvent arg0) {
		time1 = System.nanoTime() / 10000;
		if (time1 - time2 > FRAMERATE) {
            time2 = System.nanoTime() / 10000;
            
            NewObjectStorage.update();
            UIManager.update();
			repaint();
		}
	}
}
