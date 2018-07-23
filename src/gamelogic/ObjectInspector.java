package gamelogic;

import UI.CreativeUI;
import UI.HUD;
import UI.UIManager;
import adventuregame.GlobalData;
import objects.GameObject;

public class ObjectInspector {

    private static boolean enabled = false;
    private static GameObject inspectedObject;
    public static void inspectObject(GameObject o) {inspectedObject = o;}
    private static GameObject selectedObject;
    public static void selectObject(GameObject o) {selectedObject = o;}
    public static GameObject selectedObject() {return selectedObject;}

    public static void enable(boolean b) {
        enabled = b;
        if (b && ObjectCreator.isEnabled()) {ObjectCreator.enabled(false);}
    }
    public static boolean isEnabled() {return enabled;}
    public static void toggle() {
        if (isEnabled()) {enable(false);} else {enable(true);}
    }

    public static void selectWithMouse() {
        CreativeUI c = (CreativeUI) UIManager.getGUI("Creative");
        if (isEnabled() && !c.mouseOverConsole()) {
            for (GameObject o : ObjectStorage.getObjectList()) {
                if (o.getDisplayBox().contains(GlobalData.getMouse()) && !o.isSelected()) {
                    o.select();
                    selectObject(o);
                }
                else if (o.getDisplayBox().contains(GlobalData.getMouse()) && o.isSelected()) {
                    o.deselect();
                    selectObject(null);
                }
            }
        }
    }

    public static void changeProperties(String property, String newValue) {
        if (property.equals("height")) {
            inspectedObject.get().height = Integer.valueOf(newValue);
        }
        if (property.equals("width")) {
            inspectedObject.get().width = Integer.valueOf(newValue);
        }
        if (property.equals("x")) {
            inspectedObject.get().x = Integer.valueOf(newValue);
        }
        if (property.equals("y")) {
            inspectedObject.get().y = Integer.valueOf(newValue);
        }
        if (property.equals("gravity")) {
            inspectedObject.physics().setGravity(Boolean.valueOf(newValue));
        }
        if (property.equals("name")) {
            inspectedObject.setName(newValue);
        }
        if (property.equals("focus")) {
            inspectedObject.cameraFocus(Boolean.parseBoolean(newValue));
        }
    }

    public static void inspect() {
        if (isEnabled()) {
            for (GameObject o : ObjectStorage.getObjectList()) {
                if (o.getDisplayBox().contains(GlobalData.getMouse()) && UIManager.getGUI("HUD").isVisible()) {
                    HUD hud = (HUD) UIManager.getCurrentGUI();
                    hud.inspectionContextMenu(o);
                    hud.setCurrentObject(o);
                }
            }
        }
    }

}
