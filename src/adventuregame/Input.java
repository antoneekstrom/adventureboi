package adventuregame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import actions.PlayerMovement;
import actions.UINavigation;
import UI.UIObject;

public class Input {

    private static JComponent jcomp;

    private static String r = "released";
    
    private static boolean takeInput = true;
    private static boolean movementEnabled = true;
    private static boolean UIKeysEnabled = true;

    //keylistener
    private static boolean keyInput = false;
    private static String inputString = "";
    private static UIObject focusedObject;

    private static HashMap<String, Integer> keybindings;
    private static HashMap<String, Action> actions;
    private static HashMap<String, Action> releasedActions;

    /** Enable/disable player input. */
    public static void enable(boolean b) {
        takeInput = b;
    }

    /** Returns if input is enabled/disabled */
    public static boolean getState() {return takeInput;}
    public static boolean getMovementEnabled() {return movementEnabled;}
    public static void enableMovement(boolean b) {movementEnabled = b;}
    public static void enableUIKeys(boolean b) {UIKeysEnabled = b;}
    public static boolean UIKeysEnabled() {return UIKeysEnabled;}

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

    private static void player1() {
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
    }
    private static void player2() {
        //player2
        actions.put("moveleft2", new PlayerMovement("moveleft", false, 2));
        releasedActions.put("moveleft2", new PlayerMovement("moveleft", true, 2));

        actions.put("moveright2", new PlayerMovement("moveright", false, 2));
        releasedActions.put("moveright2", new PlayerMovement("moveright", true, 2));

        actions.put("jump2", new PlayerMovement("jump", false, 2));
        releasedActions.put("jump2", new PlayerMovement("jump", true, 2));

        actions.put("ability1", new PlayerMovement("ability1", false, 2));
        releasedActions.put("ability1", new PlayerMovement("ability1", true, 2));
    }

    private static void addNavigationAction(String actionkey) {
        actions.put(actionkey, new UINavigation(actionkey, false));
        releasedActions.put(actionkey, new UINavigation(actionkey, true));
    }
    private static void navigation() {
        addNavigationAction("UI_options");
        addNavigationAction("UI_inventory");
        addNavigationAction("UI_up");
        addNavigationAction("UI_down");
        addNavigationAction("UI_creative");
    }

    /** Bind all actionnames to actions. */
    private static void populateActionMap() {
        player1();
        player2();
        navigation();
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
        keybindings.put("ability1", KeyEvent.VK_1);
        //player2
        keybindings.put("moveright2", KeyEvent.VK_NUMPAD6);
        keybindings.put("moveleft2", KeyEvent.VK_NUMPAD4);
        keybindings.put("jump2", KeyEvent.VK_NUMPAD8);
        //navigation
        keybindings.put("UI_options", KeyEvent.VK_ESCAPE);
        keybindings.put("UI_inventory", KeyEvent.VK_I);
        keybindings.put("UI_creative", KeyEvent.VK_C);

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

    public static void keyInput(boolean b) {keyInput = b;}
    public static boolean keyInput() {return keyInput;}
    public static void type(char c) {inputString += c;}
    public static void clearInputString() {inputString = "";}
    public static void setInputString(String s) {inputString = s;}
    public static String getInputString() {return inputString;}
    private static void backspace() {inputString = inputString.substring(0, inputString.length() - 1);}
    public static void focusObject(UIObject o) {focusedObject = o;}
    public static void submitString() {
        focusedObject.submitInput(inputString);
        focusedObject.toggleTyping();
        clearInputString();
    }

    public static KeyListener getKeyListener() {
        KeyListener kl = new KeyListener(){
        
            @Override
            public void keyTyped(KeyEvent e) {
            }
        
            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        
            @Override
            public void keyPressed(KeyEvent e) {
                if (keyInput) {
                    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                        backspace(); /* delete character */
                    }
                    else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        submitString(); /* Submit input */
                    }
                    else {
                        type(e.getKeyChar()); /* Type character. */
                    }
                }
            }
        };
        return kl; 
    }

}
