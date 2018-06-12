package UI;

import java.awt.Graphics;
import java.util.ArrayList;

import adventuregame.GameEnvironment;
import gamelogic.NewObjectStorage;

/** This is were all the UI's are stored. */
public class UIManager {

    /** Max number of entries in history. */
    private static int MAX_HISTORY_SIZE = 5;
    /** History for recent GUI's in order. */
    private static ArrayList<String> GUIHistory = new ArrayList<String>();

    public static void addToHistory(String name) {
        GUIHistory.add(name);
        /** Remove oldest entry if size is larger than max. */
        if (GUIHistory.size() > MAX_HISTORY_SIZE) {
            GUIHistory.remove(0);
        }
    }
    /** Returns name of latest GUI and removes it from history. */
    public static String getLatestGUI() {
        String name = null;
        if (GUIHistory.size() > 0) {
            name = GUIHistory.get(GUIHistory.size() - 1);
            GUIHistory.remove(GUIHistory.size() - 1);
        }
        return name;
    }

    /** A list containing all the UI's. */
    private static ArrayList<GUI> interfaces = new ArrayList<GUI>() {
        private static final long serialVersionUID = 1L;
	{
        add(new MenuUI());
        add(new SettingsUI());
        add(new KeybindingsUI());
        add(new NewHUD());
        add(new OptionsUI());
    }};

    /** Starts all UI's */
    public static void start() {
        //start GUI's and update them once
        for (GUI gui : interfaces) {
            gui.start();
        }
    }

    public static void enableHUD(boolean b) {
        getGUI("HUD").setVisible(b);
    }

    public static void enableLatestGUI() {
        hideAll(false);
        enableGUI(getLatestGUI());
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
        hideAll(true);
        getGUI(name).setVisible(true);
    }

    public static ArrayList<GUI> getGUIList() {
        return interfaces;
    }

    public static void hideAll(boolean addToHistory) {
        for (GUI gui : interfaces) {
            if (gui.isVisible() && addToHistory) {UIManager.addToHistory(gui.getName());}
            gui.setVisible(false);
        }
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

    /** Update all UI's. */
    public static void update() {
        for (GUI ui : interfaces) {
            if (ui.isVisible()) {ui.update();}
        }
    }

    /** Paint all UI's. */
    public static void paint(Graphics g) {
        for (GUI ui : interfaces) {
            if (ui.isVisible()) {ui.paint(g);}
        }
    }
}
