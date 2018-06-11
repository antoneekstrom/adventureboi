package UI;

import java.awt.Graphics;
import java.util.ArrayList;

/** This is were all the UI's are stored. */
public class UIManager {

    /** A list containing all the UI's. */
    private static ArrayList<GUI> interfaces = new ArrayList<GUI>() {
        private static final long serialVersionUID = 1L;
	{
        add(new MenuUI("menu"));
    }};

    /** Starts all UI's */
    public static void start() {
        for (GUI gui : interfaces) {
            gui.start();
        }
    }

    public static ArrayList<GUI> getGUIList() {
        return interfaces;
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
