package gamelogic;

import java.awt.event.WindowEvent;
import java.util.HashMap;

import UI.UIManager;
import adventuregame.GameEnvironment;

public class MouseFunctions {

    private static ClickListener clickListener = new ClickListener();

    private static final HashMap<String, Runnable> actionsByText = new HashMap<String, Runnable>() {
        private static final long serialVersionUID = 1L;
	{
        put("Quit Game", () -> quitGame());
        put("Custom Levels", () -> UIManager.getGUI("menu").setVisible(false));
    }};

    public static ClickListener getClickListener() {
        return clickListener;
    }

    public static void quitGame() {
        GameEnvironment.getFrame().dispatchEvent(new WindowEvent(GameEnvironment.getFrame(), WindowEvent.WINDOW_CLOSING));
    }

    public static void executeClickActionByText(String name) {
        actionsByText.get(name).run();
    }

}
