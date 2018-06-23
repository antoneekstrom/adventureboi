package adventuregame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import UI.UIManager;
import data.Configuration;
import data.DataHandler;
import data.LevelData;
import data.ObjectData;
import data.Players;
import gamelogic.MouseFunctions;
import gamelogic.NewCamera;
import gamelogic.NewObjectStorage;
import gamelogic.ObjectCreator;

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
    public static ArrayList<String> playernames = new ArrayList<String>();
    public static void setPlayer1Name(String name) {
        playernames.add(0, name);
        Configuration.setProperty("player1_name", name);
    }
    public static void setPlayer2Name(String name) {
        playernames.add(1, name);
        Configuration.setProperty("player2_name", name);
    }
    public static String player1Name() {return playernames.get(0);}
    public static String player2Name() {return playernames.get(1);}

    //environment data
    private static LevelData levelData = new LevelData("menu", true, null);
    private static String saveDirectory = "data/levels/";

    public static Main getFrame() {
        return frame;
    }

    public GameEnvironment(Main f) {
        frame = f;
    }

    public static void saveGame() {
        levelData.objectDataList(ObjectData.createDataList());
        Players.extractAllPlayerData();
        Players.serializePlayerData();
        DataHandler.serialize(levelData, new File(saveDirectory + levelData.name() + ".ser"));
    }

    public static void loadLevel(String name) {
        levelData = (LevelData) DataHandler.deserialize(new File(saveDirectory + name + ".ser"));

        //load level
        NewObjectStorage.clearEnvironment();
        NewObjectStorage.setList(ObjectData.createObjectList(levelData.objectDataList()));

        //load player and gui
        if (!name.equals("menu")) {
            //enable hud
            UIManager.enableGUI("HUD");
            
            //load players
            Players.loadPlayerData();
            NewObjectStorage.addPlayers();
            NewObjectStorage.spawnPlayers();
            
            //set camera to follow player 1
            NewObjectStorage.getPlayer(1).cameraFocus(true);

            //add to config
            Configuration.setProperty("last_level_loaded", name);
        }
        else {
            NewCamera.centerCameraOn(new Point(-50, 300));
        }

    }

    public void start() {
        //set up panel
        setSize(GlobalData.getScreenDim());
        setBackground(Color.CYAN);

        //load config
        Configuration.loadProperties();

        //get player names
        playernames.add(0, Configuration.getProperty("player1_name"));
        playernames.add(1, Configuration.getProperty("player2_name"));

        //add listeners
        addMouseListener(MouseFunctions.getClickListener());
        addKeyListener(Input.getKeyListener());

        //load all images
        Images.indexAllImages();
        Images.loadAllImages();

        //load playerdata
        Players.loadPlayerData();

        //add players to playerlist
        NewObjectStorage.addPlayers();

        //set up save mechanism and load menu world
        loadLevel("menu");

        //set config things
        NewObjectStorage.playersToSpawn(Integer.parseInt(Configuration.getProperty("PLAYER_COUNT")));
        
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
        NewCamera.centerCameraOn(new Point(-50, 300));

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
