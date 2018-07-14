package gamelogic;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import UI.Console;
import UI.UIManager;
import adventuregame.Items;
import items.*;
import items.abilities.*;
import objects.*;

public class ObjectCreator {

    private static int currentObjectIndex = 0;
    private static Runnable currentObject;
    private static boolean enabled = false;
    private static int enemyLevel = 3;

    private static boolean customSize = false;
    private static int width = 100;
    private static int height = 100;
    public static void width(int i) {width = i;}
    public static void height(int i) {height = i;}
    public static void toggleCustomSize() {if (customSize) {customSize = false;} else {customSize = true;}}
    public static boolean customSize() {return customSize;}

    static HashMap<String, Runnable> objects = new HashMap<String, Runnable>() {
        {
            put("angryshroom", () -> createObject(AngryShroom.class.getName()));
            put("bigmush", () -> createEnemy(Bigmush.class.getName()));
            put("object", () -> createObject(NewObject.class.getName()));
            put("starman", () -> createObject(Starman.class.getName()));
        }
    };

    public static void start() {
        addItemObjectsToList();
    }

    private static void addItemObjectsToList() {
        objects.put("fireball", () -> createItemObject(new FireballItem()));
        objects.put("deadangryshroom", () -> createItemObject(new DeceasedAngryShroom()));
        objects.put("donut", () -> createItemObject(new Donut()));
        objects.put("energyshroom", () -> createItemObject(new EnergyShroom()));
        objects.put("tallmush", () -> createItemObject(new TallmushItem()));
    }

    public static void createEnemy(String className) {
        try {
            Enemy o = (Enemy) Class.forName(className).newInstance();
            o.get().setLocation(NewCamera.getMouse());
            o.level(enemyLevel);
            NewObjectStorage.add(o);
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public static String getCurrentObjectName() {return (String) objects.keySet().toArray()[currentObjectIndex];}

    public static void createObject(String className) {
        try {
            NewObject o = (NewObject) Class.forName(className).newInstance();
            o.get().setLocation(NewCamera.getMouse());
            NewObjectStorage.add(o);
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public static void createItemObject(Item i) {
        try {
            Constructor<?> c = Class.forName(ItemObject.class.getName()).getConstructor(Item.class);
            NewObject o = (NewObject) c.newInstance(i);
            o.get().setLocation(NewCamera.getMouse());
            NewObjectStorage.add(o);
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public static Runnable getCurrentObject() {return currentObject;}

    public static void previousObject() {
        if (currentObjectIndex - 1 >= 0) {
            currentObjectIndex -= 1;
        }
        else {
            currentObjectIndex = objects.size() -1;
        }

        currentObject = objects.get(objects.keySet().toArray()[currentObjectIndex]);
    }

    public static void createOnMouse() {
        if (creationAllowed()) {
            getCurrentObject().run();
        }
    }

    private static boolean creationAllowed() {
        boolean b = true;
        if (!isEnabled()) {b = false;}
        if (!UIManager.getGUI("HUD").isVisible()) {b = false;}
        return b;
    }
    
    public static void nextObject() {
        if (currentObjectIndex + 1 <= objects.size() - 1) {
            currentObjectIndex += 1;
        }
        else {
            currentObjectIndex = 0;
        }
        currentObject = objects.get(objects.keySet().toArray()[currentObjectIndex]);
    }

    public static void enabled(boolean b) {
        enabled = b;
        if (b && ObjectInspector.isEnabled()) {ObjectInspector.enable(false);}
    }
    public static boolean isEnabled() {return enabled;}
    public static void toggleEnabled() {
        if (isEnabled()) {
            enabled(false);
        }
        else {
            enabled(true);
        }
    }

    public static void createItem(String name, int x, int y) {
        if (Items.itemClassNames.containsKey(name)) {

            boolean failed = false;
            String className = Items.itemClassNames.get(name);
            Item i = null;
            try {
                i = (Item) Console.findClass(className).newInstance();
            }
            catch (Exception e) {e.printStackTrace(); failed = true;}

            if (!failed) {
                ItemObject o = new ItemObject(i);
                o.get().setLocation(x, y);
                NewObjectStorage.add(o);
            }
            else {Console.logError("No such item with the name " + name + ".");}
        }
    }

}
