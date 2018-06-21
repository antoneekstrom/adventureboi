package gamelogic;

import UI.UIManager;
import data.ObjectData;
import objects.*;

public class ObjectCreator {

    private static String[] objects;
    private static int currentObjectIndex = 0;
    private static String currentObject = "object";
    private static boolean enabled = false;

    private static boolean customSize = false;
    private static int width = 100;
    private static int height = 100;
    public static void width(int i) {width = i;}
    public static void height(int i) {height = i;}
    public static void toggleCustomSize() {if (customSize) {customSize = false;} else {customSize = true;}}
    public static boolean customSize() {return customSize;}

    public static void start() {
        objects = ObjectData.displayNames();
    }

    public static String getCurrentObject() {return currentObject;}

    public static void previousObject() {
        if (currentObjectIndex - 1 >= 0) {
            currentObjectIndex -= 1;
        }
        else {
            currentObjectIndex = objects.length -1;
        }
        currentObject = objects[currentObjectIndex];
    }

    public static void createOnMouse() {
        if (creationAllowed()) {
            NewObject o = null;

            try {
                o = (NewObject) ObjectData.createObject(ObjectData.objects().get(currentObject));
            }
            catch (Exception e) {e.printStackTrace();}

            if (o != null) {
                o.get().setLocation(NewCamera.getMouse());
                if (customSize) {
                    o.get().setSize(width, height);
                }
                NewObjectStorage.add(o);
            }
        }
    }

    private static boolean creationAllowed() {
        boolean b = true;
        if (!isEnabled()) {b = false;}
        if (!UIManager.getGUI("HUD").isVisible()) {b = false;}
        return b;
    }
    
    public static void nextObject() {
        if (currentObjectIndex + 1 <= objects.length - 1) {
            currentObjectIndex += 1;
        }
        else {
            currentObjectIndex = 0;
        }
        currentObject = objects[currentObjectIndex];
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

}
