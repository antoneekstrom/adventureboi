package gamelogic;

import UI.Console;
import UI.UIManager;
import objects.Enemy;

public class DefaultConsoleCommands {

    /** Get a random level somwhere around target level. */
    public static int randomizeLevel(int level) {return Item.getRandomLevel(level);}

    /** Set objectcreator level. */
    public static void level(int level) {
        ObjectCreator.enemyLevel(level); Console.logSuccessful("Spawn level set to " + level + ".");
    }

    public static void reload() {
        UIManager.reload();
    }

    public static void back() {
        UIManager.enableLatestGUI();
    }

    public static void test() {

    }

    public static void refreshInv() {
        UIManager.getInventory(1).refreshInv();
    }

    /** Kill selected enemy. */
    public static void kill() {
        try {
            Enemy e = (Enemy) ObjectInspector.selectedObject();
            e.die();
        }
        catch (ClassCastException e) {
            ObjectStorage.remove(ObjectInspector.selectedObject());
        }
    }

}