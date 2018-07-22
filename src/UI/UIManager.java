package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import adventuregame.GlobalData;
import data.Configuration;
import gamelogic.NewObjectStorage;

/** This is were all the UI's are stored. */
public class UIManager {

    /** Max number of entries in history. */
    private static int MAX_HISTORY_SIZE = 5;
    /** History for recent GUI's in order. */
    private static ArrayList<String> GUIHistory = new ArrayList<String>();

    private static boolean lockCurrentGUI = false;
    public static void lockCurrentGUI(boolean b) {lockCurrentGUI = b;}
    public static boolean lockCurrentGUI() {return lockCurrentGUI;}

    static Color globalBackgroundColor;
    private static String CONFIG_BG_KEY = "background_color";

    public static void addToHistory(String name) {
        GUIHistory.add(name);
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
            if (!getGUI(name).home()) {
                GUIHistory.remove(GUIHistory.size() - 1);
            }
        }
        return name;
    }

    public static void removeFromHistory(int index) {GUIHistory.remove(index);}
    public static void removeFromHistory(String name) {GUIHistory.remove(name);}

    /** A list containing all the UI's. */
    private static ArrayList<GUI> interfaces = new ArrayList<GUI>() {private static final long serialVersionUID = 1L;};

    private static void addInterfaces() {
        interfaces.add(new MenuUI());
        interfaces.add(new SettingsUI());
        interfaces.add(new KeybindingsUI());
        interfaces.add(new NewHUD());
        interfaces.add(new OptionsUI());
        interfaces.add(new CreativeUI());
        interfaces.add(new LevelsUI());
        interfaces.add(new PlayerSelectUI());
        interfaces.add(new InventoryUI());
        interfaces.add(new InspectPlayerUI());
        interfaces.add(new InputFieldUI("InputField_PlayerName", "Enter Name"));
        interfaces.add(new InputFieldUI("InputField_RemovePlayer", "Enter Name"));
        interfaces.add(new InputFieldUI("InputField_NewLevel", "Enter Name"));
        interfaces.add(new InspectItemUI());
        interfaces.add(new UIColor());
    }

    static class UIColor extends ColorPickerUI {
        UIColor() {
            super("UIColor");
        }
        @Override
        void confirm(Color color) {
            Configuration.setProperty(CONFIG_BG_KEY, String.valueOf(color.getRGB()));
            reload();
        }
    }

    /** Starts all UI's */
    private static void startAll() {
        for (GUI gui : interfaces) {
            gui.start();
        }
    }

    private static Color getGlobalBackgroundColor() {
        String c = Configuration.getProperty(CONFIG_BG_KEY);
        return new Color(Integer.parseInt(c));
    }

    /** Initiate UIManager on program start. */
    public static void start() {
        addInterfaces();
        setBackgroundColor(getGlobalBackgroundColor());
        startAll();
    }

    /** Clear and restart all GUI's. */
    public static void reload() {
        String currentGUI = getCurrentGUI().getName();
        interfaces.clear();
        start();
        enableGUI(currentGUI);
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
        String name = getLatestGUI();
        enableGUI(name);
    }

    public static String getLast() {
        String name = "none";
        if (GUIHistory.size() > 0) {
            name = GUIHistory.get(GUIHistory.size() - 2);
            GUIHistory.remove(GUIHistory.size() - 1);
        }
        return name;
    }

    public static void enableLast() {
        enableGUI(getLast(), false);
    }

    /** Change backgroundcolor for all UI's. */
    public static void setBackgroundColor(Color color) {
        for (GUI g : interfaces) {
            g.setUIBackgroundColor(color);
            g.TEXT_HOVER_COLOR = color;
        }
    }

    public static void back() {
        enableLast();
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
        enableGUI(name, true);
    }

    public static void enableGUI(String name, boolean addtohistory) {
        if (!lockCurrentGUI) {
            hideAll();
            getGUI(name).enable(addtohistory);
        }
    }

    public static void clearHistory() {GUIHistory.clear();}

    public static ArrayList<GUI> getGUIList() {
        return interfaces;
    }

    public static void hideAll() {
        for (GUI gui : interfaces) {
            gui.setVisible(false);
        }
    }

    public static InventoryUI getInventory(int player) {
        InventoryUI inv = (InventoryUI) getGUI("Inventory");
        inv.playerName = NewObjectStorage.getPlayer(player).getName();
        return inv;
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
                break;
            }
        }
        return ui;
    }

    /* --------------------------------------------- */
    public static String dstring = "";

    /** --- DEBUG GOES HERE --- */
    private static void debug() {
        dstring = getHudHistory();
    }

    /** For putting things on screen all the time regardless of active GUI. For debugging purposes. */
    private static void debug(Graphics g) {
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
        for (GUI ui : interfaces) {
            if (ui.isVisible()) {ui.paint(g);}
        }
        debug(g);
    }
}
