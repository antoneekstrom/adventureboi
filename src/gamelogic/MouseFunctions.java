package gamelogic;

import java.awt.event.WindowEvent;
import java.util.HashMap;

import UI.PlayerSelectUI;
import UI.UIAlert;
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
        put("Back", () -> UIManager.back());
        put("Custom Levels", () -> UIManager.enableGUI("Levels"));
        put("Save Level", () -> GameEnvironment.saveGame());
        put("Save Player", () -> GameEnvironment.savePlayers());
        put("Select Player", () -> UIManager.enableGUI("PlayerSelect"));
        put("New Player", () -> UIManager.enableGUI("InputField_PlayerName"));
        put("Remove Player", () -> UIManager.enableGUI("InputField_RemovePlayer"));
        put("Menu", () -> {
            GameEnvironment.loadLevel("menu");
            UIManager.enableGUI("Menu");
        });
    }};

    private static final HashMap<String, Runnable> actionsByTag = new HashMap<String, Runnable>() {
        private static final long serialVersionUID = 1L;
	{
        put("nextObject", () -> ObjectCreator.nextObject());
        put("prevObject", () -> ObjectCreator.previousObject());
        put("toggleObjectCreation", () -> ObjectCreator.toggleEnabled());
        put("toggleObjectInspection", () -> ObjectInspector.toggle());
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
        switch (lo.tag()) {
            case "levelList":
                GameEnvironment.loadLevel(lo.getText());
                break;

            case "playerList":
                PlayerSelectUI gui = (PlayerSelectUI) UIManager.getCurrentGUI();
                if (gui.getCurrentPlayer() == 1) {
                    GameEnvironment.setPlayer1Name(lo.getText());
                    gui.addObject(new UIAlert("Player selected.", gui.getName()));
                }
                else if (gui.getCurrentPlayer() == 2) {
                    GameEnvironment.setPlayer2Name(lo.getText());
                    gui.addObject(new UIAlert("Player selected.", gui.getName()));
                }
                break;

            case "objectInspect":
                lo.leftMouseReleased();
                break;
        }
    }
}
