package adventuregame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.Timer;

import UI.Console;
import UI.LevelsUI;
import UI.UIAlert;
import UI.UIManager;
import data.Configuration;
import data.DataHandler;
import data.LevelData;
import data.ObjectData;
import data.Players;
import gamelogic.MouseFunctions;
import gamelogic.MouseListener;
import gamelogic.Camera;
import gamelogic.EventTimer;
import gamelogic.ObjectStorage;
import gamelogic.RandomEvent;
import gamelogic.ObjectCreator;
import gamelogic.ObjectPlacement;
import objects.Background;
import objects.Platform;

public class GameEnvironment extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    //timer
    final double FRAMERATE = 12;
    final int TIMER_DELAY = 14;
    Timer timer;
    boolean readyToPaint = false;

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
    public static LevelData levelData() {return levelData;}

    //environment data
    private static LevelData levelData = new LevelData("menu", true, null);
    private static String saveDirectory = "data/levels/";

    public static Main getFrame() {
        return frame;
    }

    public GameEnvironment(Main f) {
        frame = f;
    }

    /** Save world state. */
    public static void saveGame(boolean alert) {
        levelData.objectDataList(ObjectData.createDataList());
        DataHandler.serialize(levelData, new File(saveDirectory + levelData.name() + ".ser"));
        if (alert) {
            UIManager.getCurrentGUI().addObject(new UIAlert("Game has been saved to " + levelData.name() + ".", UIManager.getCurrentGUI().getName(), false));
        }
    }

    public static void newLevel(String name) {
        ArrayList<ObjectData> olist = new ArrayList<ObjectData>();

        Platform obj = new Platform();
        obj.setRectangle(new Rectangle(300, 800, 800, 100));
        olist.add(obj.extractData());

        levelData = new LevelData(name, true, olist);
        saveGame(false);
        LevelsUI.refreshList();
    }

    /** Save playerdata for all present players. */
    public static void savePlayers(boolean alert) {
        Players.extractAllPlayerData();
        Players.serializePlayerData();

        //log it
        if (alert) {
            UIManager.getCurrentGUI().addObject(new UIAlert("Players have been saved.", UIManager.getCurrentGUI().getName(), false));
        }
        Console.logSuccessful("Players have been saved.");
    }

    public static void loadLevel(String name) {
        levelData = (LevelData) DataHandler.deserialize(new File(saveDirectory + name + ".ser"));

        //load level
        ObjectStorage.clearEnvironment();
        ObjectStorage.setList(ObjectData.createObjectList(levelData.objectDataList()));

        //load player and gui
        if (!name.equals("menu")) {
            //enable hud
            UIManager.enableGUI("HUD");
            UIManager.clearHistory();
            UIManager.addToHistory("HUD");
            
            //load players
            Players.loadPlayerData();
            ObjectStorage.addPlayers();
            ObjectStorage.spawnPlayers();
            
            //set camera to follow player 1
            ObjectStorage.getPlayer(1).cameraFocus(true);

            //add to config
            Configuration.setProperty("last_level_loaded", name);

            //start events
            ObjectStorage.startEvents();

            //start objectcreator preview
            ObjectCreator.addPreview();

            //background stuff
            background.reCalculate();
        }
        else {
            Camera.centerCameraOn(new Point(-50, 300));
        }

        ObjectStorage.doneLoading();
    }

    private static Background background;
    public static Background background() {return background;}

    private static ArrayList<EventTimer> eventTimers = new ArrayList<EventTimer>();

    public static EventTimer getEventTimer(int interval) {

        EventTimer timer = null;
        for (EventTimer t : eventTimers) {
            if (t.getInterval() == interval) {
                timer = t;
            }
        }

        if (timer == null) {
            timer = new EventTimer(interval);
            eventTimers.add(timer);
            return timer;
        }
        else {
            return timer;
        }
    }

    public static RandomEvent findEvent(String name) {
        for (EventTimer et : GameEnvironment.getEventTimers()) {
            for (RandomEvent e : et.getEvents()) {
                if (e.getName().equals(name)) {
                    return e;
                }
            }
        }
        throw new NullPointerException();
    }

    public static void removeEvent(String name) {
        for (EventTimer et : GameEnvironment.getEventTimers()) {
            for (Iterator<RandomEvent> i = et.getEvents().iterator(); i.hasNext();) {
                RandomEvent e = i.next();
                if (e.getName().equals(name)) {
                    i.remove();
                }
            }
        }
    }

    public static void clearAllEvents() {
        for (EventTimer t : GameEnvironment.getEventTimers()) {t.clearEvents();}
    }

    public static ArrayList<EventTimer> getEventTimers() {return eventTimers;}

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
        addMouseWheelListener(new MouseListener());
        addMouseListener(MouseFunctions.getClickListener());
        addKeyListener(Input.getKeyListener());

        //load all images
        Images.indexAllImages();
        Images.loadAllImages();

        //load playerdata
        Players.loadPlayerData();

        //add players to playerlist
        ObjectStorage.addPlayers();

        //set up save mechanism and load menu world
        loadLevel("menu");

        //set config things
        ObjectStorage.playersToSpawn(Integer.parseInt(Configuration.getProperty("PLAYER_COUNT")));
        
        //start UI
        UIManager.start();

        //if there are no player profiles open player selection UI
        if (Players.playerData().size() < 1) {
            UIManager.enableGUI("PlayerSelect");
        }

        //start object creator
        ObjectCreator.start();

        //set grid
        ObjectPlacement.gridSize(ObjectPlacement.gridSize());

        //start and attach input panel
        input = Input.start();
        add(input);
        this.setFocusable(true);
		this.requestFocus();
        
        //start camera and initialize world
        Camera.centerCameraOn(new Point(-50, 300));

        //set a background
        background = new Background(Images.getImage("mushforest"));

        readyToPaint = true;

        //start gameloop timer
        timer = new Timer(TIMER_DELAY, this);
        timer.start();
    }

    public void paint(Graphics g) {
        if (readyToPaint) {
            super.paintComponent(g);
            background.paint(g);
            ObjectStorage.paint(g);
            UIManager.paint(g);
        }
    }

	double time1, time2;
	public void actionPerformed(ActionEvent arg0) {
		time1 = System.nanoTime() / 100000;
		if (time1 - time2 >= FRAMERATE) {
            time2 = System.nanoTime() / 100000;

            background.update();
            ObjectStorage.update();
            UIManager.update();
			repaint();
		}
	}
}
