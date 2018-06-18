package adventuregame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.lang.Object;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import UI.UIManager;
import data.DataHandler;
import data.LevelData;
import data.ObjectData;
import data.Players;
import gamelogic.MouseFunctions;
import gamelogic.NewCamera;
import gamelogic.NewObjectStorage;
import gamelogic.ObjectCreator;
import objects.AngryShroom;
import objects.NewObject;
import objects.NewPlayer;


public class GameEnvironment extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    //timer
    int FRAMERATE = 12;
    int TIMER_DELAY = 14;
    Timer timer;

    //components
    JPanel input;
    private static Main frame;

    //players
    private static int numberOfPlayers = 2;
    private static String player1_name = "raviolo";
    private static String player2_name = "boi";
    public static void setPlayer1Name(String name) {player2_name = name;}
    public static void setPlayer2Name(String name) {player1_name = name;}
    public static String player1Name() {return player1_name;}
    public static String player2Name() {return player2_name;}

    //environment data
    private static LevelData levelData = new LevelData("menu", true, null);
    private static String saveDirectory = "data/levels/";

    public static Main getFrame() {
        return frame;
    }

    public GameEnvironment(Main f) {
        frame = f;
    }

    public static void loadPlayers() {
        for (int i = 1; i <= numberOfPlayers; i++) {
            NewObjectStorage.newPlayer();
        }
    }

    public static void saveGame() {
        levelData.objectDataList(ObjectData.createDataList());
        Players.savePlayerData();
        DataHandler.serialize(levelData, new File(saveDirectory + levelData.name() + ".ser"));
    }

    private static void createBoilerplateLevel(LevelData data) {
        NewObject ground = new NewObject();
        ground.setRectangle(new Rectangle(-1000, 500, 3000, 100));
        ground.setColor(Color.orange);
        ground.physics().setGravity(false);
        ground.setIntersect(false);

        levelData.objectDataList().add(new ObjectData(ground));
    }

    public static void loadLevel(String name) {
        levelData = (LevelData) DataHandler.deserialize(new File(saveDirectory + name + ".ser"));

        if (levelData.objectDataList().isEmpty()) {createBoilerplateLevel(levelData);}

        NewObjectStorage.clearEnvironment();
        NewObjectStorage.setList(ObjectData.createObjectList(levelData.objectDataList()));
    }

    public void test() {
    }

    public void start() {
        //set up panel
        setSize(GlobalData.getScreenDim());
        setBackground(Color.CYAN);

        //add listeners
        addMouseListener(MouseFunctions.getClickListener());
        addKeyListener(Input.getKeyListener());

        //testing
        test();

        //load all images
        Images.indexAllImages();
        Images.loadAllImages();

        //load playerdata
        Players.loadPlayerData();

        //set up save mechanism and load menu world
        loadLevel("menu");

        //start UI
        UIManager.start();

        //if there are no player profiles open player selection UI
        if (Players.playerData().size() < 1) {
            UIManager.enableGUI("PlayerSelect");
        }

        //start object creator
        ObjectCreator.start();

        //start and attach input panel
        input = Input.start();
        add(input);
        this.setFocusable(true);
		this.requestFocus();
        
        //start camera and initialize world
        NewCamera.centerCameraOn(new Point(-50, 500));

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
