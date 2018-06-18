package gamelogic;

import java.awt.event.WindowEvent;
import java.util.HashMap;

import UI.LevelsUI;
import UI.UIManager;
import UI.UIObject;
import adventuregame.GameEnvironment;

public class MouseFunctions {

    private static ClickListener clickListener = new ClickListener();


    private static final HashMap<String, Runnable> actionsByText = new HashMap<String, Runnable>() {
        private static final long serialVersionUID = 1L;
	{
        put("Quit Game", () -> quitGame());
        put("Start", () -> UIManager.enableGUI("HUD"));
        put("Resume", () -> UIManager.enableGUI("HUD"));
        put("Settings", () -> UIManager.enableGUI("Settings"));
        put("Keybindings", () -> UIManager.enableGUI("Keybindings"));
        put("Menu", () -> UIManager.enableGUI("Menu"));
        put("Back", () -> UIManager.enableLatestGUI());
        put("Custom Levels", () -> UIManager.enableGUI("Levels"));
        put("Save", () -> GameEnvironment.saveGame());
        put("Select Player", () -> UIManager.enableGUI("PlayerSelect"));
    }};

    private static final HashMap<String, Runnable> actionsByTag = new HashMap<String, Runnable>() {
        private static final long serialVersionUID = 1L;
	{
        put("nextObject", () -> ObjectCreator.nextObject());
        put("prevObject", () -> ObjectCreator.previousObject());
        put("toggleObjectCreation", () -> ObjectCreator.toggleEnabled());
        put("toggleObjectInspection", () -> ObjectInspector.toggle());
        put("refreshLevels", () -> LevelsUI.refreshList());
    }};

    public static ClickListener getClickListener() {
        return clickListener;
    }

    public static void quitGame() {
        GameEnvironment.getFrame().dispatchEvent(new WindowEvent(GameEnvironment.getFrame(), WindowEvent.WINDOW_CLOSING));
    }

    public static void executeClickActionByTag(String tag) {
        if (actionsByTag.containsKey(tag)) {
            actionsByTag.get(tag).run();
        }
    }

    public static void executeClickActionByText(String name) {
        if (actionsByText.containsKey(name)) {
            actionsByText.get(name).run();
        }
    }

    public static void executeListAction(UIObject lo) {
        if (lo.tag().equals("levelList")) {
            GameEnvironment.loadLevel(lo.getText());
        }
        lo.leftMouseReleased();
    }
}
