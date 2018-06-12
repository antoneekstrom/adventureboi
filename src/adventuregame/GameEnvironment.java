package adventuregame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import UI.UIManager;
import gamelogic.MouseFunctions;
import gamelogic.NewCamera;
import gamelogic.NewObjectStorage;
import objects.NewObject;
import objects.NewPlayer;


public class GameEnvironment extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    int FRAMERATE = 12;
    int TIMER_DELAY = 14;
    Timer timer;
    SaveWriter sw;
    JPanel input;
    private static JFrame frame;
    private int numberOfPlayers = 1;

    public static JFrame getFrame() {
        return frame;
    }

    public GameEnvironment(JFrame f) {
        frame = f;
    }

    public void start() {
        //set up panel
        setSize(GlobalData.getScreenDim());
        setBackground(Color.CYAN);

        //set up save mechanism and load menu world
        sw = new SaveWriter("menu");
        sw.loadWorld(this);

        //add mouselistener
        addMouseListener(MouseFunctions.getClickListener());
        
        //load all images
        Images.indexAllImages();
        Images.loadAllImages();

        //start UI
        UIManager.start();

        //start and attach input panel
        input = Input.start();
        add(input);
        this.setFocusable(true);
		this.requestFocus();
        
        //add players
		for (int i = 1; i <= numberOfPlayers; i++) {
            NewObjectStorage.newPlayer();
        }
        //start camera and initialize world
		NewCamera.setCameraPos(new Point(0, 0));
		NewObjectStorage.add(new NewObject() {{
			get().setLocation(500, 0);
			get().setSize(100, 500);
			getForce().setGravity(false);
			this.setCollision(false);
        }});
        NewPlayer player = NewObjectStorage.getPlayer(1);

        //start gameloop timer
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
