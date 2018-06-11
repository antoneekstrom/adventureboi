package UI;

import java.awt.Graphics;
import java.util.ArrayList;

/** This is were all the UI's are stored. */
public class UIManager {

    /** Name of the last GUI that was visible. */
    private static String lastGUI = "Menu";

    public static String getLastGUI() {
        return lastGUI;
    }
    public static void setLastGUI(String name) {
        lastGUI = name;
    }

    /** A list containing all the UI's. */
    private static ArrayList<GUI> interfaces = new ArrayList<GUI>() {
        private static final long serialVersionUID = 1L;
	{
        add(new MenuUI());
        add(new SettingsUI());
        add(new KeybindingsUI());
    }};

    /** Starts all UI's */
    public static void start() {
        //start GUI's and update them once
        for (GUI gui : interfaces) {
            gui.start();
        }
    }

    /** Enable a GUI and disable all others.
     *  @param name : Name of GUI to enable.
     */
    public static void enableGUI(String name) {
        hideAll();
        getGUI(name).setVisible(true);
    }

    public static ArrayList<GUI> getGUIList() {
        return interfaces;
    }

    public static void hideAll() {
        for (GUI gui : interfaces) {
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
