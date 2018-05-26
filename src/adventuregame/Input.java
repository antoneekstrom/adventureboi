package adventuregame;

import java.util.HashMap;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import worlds.ListWorld;

public class Input {

    private static ListWorld lw;

    private static String r = "released";
    
    private static boolean takeInput = true;

    private static HashMap<String, String> keybindings;

    /** Enable/disable player input. */
    public static void enable(boolean b) {
        takeInput = b;
    }

    /** Returns if input is enabled/disabled */
    public static boolean getState() {
        return takeInput;
    }

    /** Start/initialize controller/playerinput. */
    public static void start(ListWorld w) {
        lw = w;
    }

    public static void passFrame(ListWorld w) {
        lw = w;
    }

    /** Create keybinding. */
    private static void bindKey(int key, String name, Action a) {
        lw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, 0, true), name + r);
        lw.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, 0, false), name);
        lw.getActionMap().put(name + r, a);
        lw.getActionMap().put(name, a);
    }

}
