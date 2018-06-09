package adventuregame;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import actions.PlayerMovement;

public class Input {

    private static JComponent jcomp;

    private static String r = "released";
    
    private static boolean takeInput = true;
    private static boolean movementEnabled = true;

    private static HashMap<String, Integer> keybindings;
    private static HashMap<String, Action> actions;
    private static HashMap<String, Action> releasedActions;

    /** Enable/disable player input. */
    public static void enable(boolean b) {
        takeInput = b;
    }

    /** Returns if input is enabled/disabled */
    public static boolean getState() {
        return takeInput;
    }

    public static boolean getMovementEnabled() {
        return movementEnabled;
    }

    public static void enableMovement(boolean b) {
        movementEnabled = b;
    }

    /** Start/initialize controller/playerinput. */
    public static JPanel start() {
        JPanel inputPanel = new JPanel();
        jcomp = inputPanel;
        keybindings = new HashMap<String, Integer>();
        actions = new HashMap<String, Action>();
        releasedActions = new HashMap<String, Action>();
        setupKeys();
        populateActionMap();
        bindKeys();
        return inputPanel;
    }

    public static void passFrame(JComponent w) {
        jcomp = w;
    }

    /** Bind all actionnames to actions. */
    private static void populateActionMap() {
        //player1
        actions.put("moveleft1", new PlayerMovement("moveleft", false, 1));
        releasedActions.put("moveleft1", new PlayerMovement("moveleft", true, 1));

        actions.put("moveright1", new PlayerMovement("moveright", false, 1));
        releasedActions.put("moveright1", new PlayerMovement("moveright", true, 1));
        
        actions.put("jump1", new PlayerMovement("jump", false, 1));
        releasedActions.put("jump1", new PlayerMovement("jump", true, 1));
        
        actions.put("sit1", new PlayerMovement("sit", false, 1));
        releasedActions.put("sit1", new PlayerMovement("sit", true, 1));

        actions.put("sprint1", new PlayerMovement("sprint", false, 1));
        releasedActions.put("sprint1", new PlayerMovement("sprint", true, 1));
        
        actions.put("abilityleft1", new PlayerMovement("abilityleft", false, 1));
        releasedActions.put("abilityleft1", new PlayerMovement("abilityleft", true, 1));

        actions.put("abilityright1", new PlayerMovement("abilityright", false, 1));
        releasedActions.put("abilityright1", new PlayerMovement("abilityright", true, 1));

        //player2
        actions.put("moveleft2", new PlayerMovement("moveleft", false, 2));
        releasedActions.put("moveleft2", new PlayerMovement("moveleft", true, 2));

        actions.put("moveright2", new PlayerMovement("moveright", false, 2));
        releasedActions.put("moveright2", new PlayerMovement("moveright", true, 2));

        actions.put("jump2", new PlayerMovement("jump", false, 2));
        releasedActions.put("jump2", new PlayerMovement("jump", true, 2));
    }

    /** Change a key in the keybinding hashmap. Will need to recreate keybinding after this to work. */
    public static void changeKey(String action, int key) {
        for (String name : keybindings.keySet()) {
            if (action.equals(name)) {
                keybindings.put(action, key);
            }
            else {
                System.out.println("Action does not exist.");
            }
        }
    }

    /** Get an array of action names. */
    public static String[] getActions() {
        String[] arr = new String[keybindings.size()];
        keybindings.keySet().toArray(arr);
        return arr;
    }

    /** Give all actions default keys. */
    private static void setupKeys() {
        //player1
        keybindings.put("moveleft1", KeyEvent.VK_A);
        keybindings.put("moveright1", KeyEvent.VK_D);
        keybindings.put("jump1", KeyEvent.VK_W);
        keybindings.put("sit1", KeyEvent.VK_S);
        keybindings.put("sprint1", KeyEvent.VK_UP);
        keybindings.put("abilityleft1", KeyEvent.VK_LEFT);
        keybindings.put("abilityright1", KeyEvent.VK_RIGHT);

        //player2
        keybindings.put("moveright2", KeyEvent.VK_NUMPAD6);
        keybindings.put("moveleft2", KeyEvent.VK_NUMPAD4);
        keybindings.put("jump2", KeyEvent.VK_NUMPAD8);
    }

    /** Bind all the keys from keybindings hashmap. Use this after changeKey method. */
    private static void bindKeys() {
        for (String name : keybindings.keySet()) {
            bindKey(keybindings.get(name), name, actions.get(name), false);
            bindKey(keybindings.get(name), name, releasedActions.get(name), true);

            System.out.println(name + " bound to " + keybindings.get(name));
        }
    }

    public static Action getAction(int key) {
        return jcomp.getActionMap().get(key);
    }

    /** Create keybinding. */
    private static void bindKey(int key, String name, Action a, boolean released) {
        if (released) {
            jcomp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, 0, false), name + r);
            jcomp.getActionMap().put(name + r, a);
        }
        else {
            jcomp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, 0, true), name);
            jcomp.getActionMap().put(name, a);
        }
    }

}
