package UI;

import java.awt.Graphics;
import java.util.ArrayList;

/** This is were all the UI's are stored. */
public class UIManager {

    public static MenuUI menu = new MenuUI();

    /** A list containing all the UI's. */
    private static ArrayList<GUI> interfaces = new ArrayList<GUI>() {
        private static final long serialVersionUID = 1L;
	{
        add(menu);
    }};

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
