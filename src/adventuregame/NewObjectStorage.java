package adventuregame;

import java.awt.Graphics;
import java.util.ArrayList;

public class NewObjectStorage {

    private static ArrayList<NewObject> objects = new ArrayList<NewObject>();

    public static ArrayList<NewObject> getObjectList() {
        return objects;
    }

    /** Add object to game.
     * @param object -Initialized object to add to game.
    */
    public static void add(NewObject object) {
        objects.add(object);
    }

    /** update all objects */
    public static void update() {
        for (NewObject o : objects) {
            o.update();
        }
    }

    //paint all objects to screen
    public static void paint(Graphics g) {
        for (NewObject o : objects) {
            o.paint(g);
        }
    }

}
