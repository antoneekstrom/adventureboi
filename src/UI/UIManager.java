package UI;

import java.awt.Graphics;
import java.util.ArrayList;

import adventuregame.GlobalData;
import gamelogic.ObjectCreator;
import gamelogic.ObjectPlacement;

/** This is were all the UI's are stored. */
public class UIManager {

    /** Max number of entries in history. */
    private static int MAX_HISTORY_SIZE = 5;
    /** History for recent GUI's in order. */
    private static ArrayList<String> GUIHistory = new ArrayList<String>();
    private static String lastInHistory = "none";

    private static boolean lockCurrentGUI = false;
    public static void lockCurrentGUI(boolean b) {lockCurrentGUI = b;}
    public static boolean lockCurrentGUI() {return lockCurrentGUI;}

    public static void addToHistory(String name) {
        GUIHistory.add(name);
        getGUI(name).addedToHistory();
        lastInHistory = name;
        /** Remove oldest entry if size is larger than max. */
        if (GUIHistory.size() > MAX_HISTORY_SIZE) {
            GUIHistory.remove(0);
        }
    }
    /** Returns name of latest GUI and removes it from history. */
    public static String getLatestGUI() {
        String name = "none";
        if (GUIHistory.size() > 0) {
            name = GUIHistory.get(GUIHistory.size() - 1);
            GUIHistory.remove(GUIHistory.size() - 1);
        }
        return name;
    }

    public static void removeFromHistory(int index) {GUIHistory.remove(index);}
    public static void removeFromHistory(String name) {GUIHistory.remove(name);}

    /** A list containing all the UI's. */
    private static ArrayList<GUI> interfaces = new ArrayList<GUI>() {
        private static final long serialVersionUID = 1L;
	{
        add(new MenuUI());
        add(new SettingsUI());
        add(new KeybindingsUI());
        add(new NewHUD());
        add(new OptionsUI());
        add(new CreativeUI());
        add(new LevelsUI());
        add(new PlayerSelectUI());
        add(new InventoryUI());
        add(new InspectPlayerUI());
        add(new InputFieldUI("InputField_PlayerName", "Enter Name"));
        add(new InputFieldUI("InputField_RemovePlayer", "Enter Name"));
        add(new InputFieldUI("InputField_NewLevel", "Enter Name"));
        add(new InspectItemUI());
    }};

    /** Starts all UI's */
    public static void start() {
        //start GUI's and update them once
        for (GUI gui : interfaces) {
            gui.start();
        }
    }

    public static GUI getCurrentGUI() {
        GUI r = null;
        for (GUI gui : interfaces) {
            if (gui.isVisible()) {r = gui;}
        }
        return r;
    }

    public static void enableHUD(boolean b) {
        enableGUI("HUD");
    }

    public static void enableLatestGUI() {
        String gui = getLatestGUI();
        String currentgui = getCurrentGUI().getName();
        if (!gui.equals("none") && !lockCurrentGUI) {
            hideAll(false);
            enableGUI(gui);
        }
        addToHistory(currentgui);
    }

    public static boolean isVisible(String guiname) {
        return getGUI(guiname).isVisible();
    }

    /** Get a list of all currently visible GUI's. */
    public static ArrayList<GUI> getAllVisible() {
        ArrayList<GUI> arr = new ArrayList<GUI>();
        for (GUI gui : interfaces) {
            arr.add(gui);
        }
        return arr;
    }

    /** Returns true if no GUI's are visible. */
    public static boolean allHidden() {
        boolean b = true;
        for (GUI gui : interfaces) {
            if (gui.isVisible()) {b = false;}
        }
        return b;
    }

    /** Enable a GUI and disable all others.
     *  @param name : Name of GUI to enable.
     */
    public static void enableGUI(String name) {
        if (!lockCurrentGUI) {
            //don't add to history if it is an inputfield GUI
            boolean history = true;
            if (name.startsWith("InputField")) {history = false;}

            hideAll(history);
            getGUI(name).setVisible(true);
        }
    }

    public static void clearHistory() {GUIHistory.clear();}

    public static ArrayList<GUI> getGUIList() {
        return interfaces;
    }

    public static void hideAll(boolean addToHistory) {
        for (GUI gui : interfaces) {
            if (gui.isVisible() && addToHistory && !gui.incognito() && !gui.getName().equals(lastInHistory)) {
                UIManager.addToHistory(gui.getName());
            }
            gui.setVisible(false);
        }
    }

    public static NewHUD getHUD() {
        return (NewHUD) getGUI("HUD");
    }

    /** Returns GUI with parameter name, else returns null. */
    public static GUI getGUI(String name) {
        GUI ui = null;
        for (GUI gui : interfaces) {
            if (gui.getName().equals(name)) {
                ui = gui;
            }
        }
        return ui;
    }

    /* --------------------------------------------- */
    private static String dstring = "";
    private static int dstringwidth = 0;

    /** --- DEBUG GOES HERE --- */
    private static void debug() {
    }

    /** For putting things on screen all the time regardless of active GUI. For debugging purposes. */
    private static void debug(Graphics g) {
        g.drawString(dstring, (GlobalData.getScreenDim().width / 2) - (dstringwidth / 2), 50);
        dstringwidth = g.getFontMetrics().stringWidth(dstring);
    }
    private static String getHudHistory() {
        String ss = "";
        for (String s : GUIHistory) {ss += s;}
        return ss;
    }
    /* --------------------------------------------- */

    /** Update all UI's. */
    public static void update() {
        for (GUI ui : interfaces) {
            if (ui.isVisible()) {ui.update();}
        }
        debug();
    }

    /** Paint all UI's. */
    public static void paint(Graphics g) {

        if (ObjectCreator.useGrid()) {
            ObjectPlacement.drawGrid(g);
        }

        for (GUI ui : interfaces) {
            if (ui.isVisible()) {ui.paint(g);}
        }
        debug(g);
    }
}
