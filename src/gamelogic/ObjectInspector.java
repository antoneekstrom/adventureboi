package gamelogic;

import UI.NewHUD;
import UI.UIManager;
import adventuregame.GlobalData;
import objects.NewObject;

public class ObjectInspector {

    private static boolean enabled = false;
    private static NewObject inspectedObject;
    public static void inspectObject(NewObject o) {inspectedObject = o;}

    public static void enable(boolean b) {
        enabled = b;
        if (b && ObjectCreator.isEnabled()) {ObjectCreator.enabled(false);}
    }
    public static boolean isEnabled() {return enabled;}
    public static void toggle() {
        if (isEnabled()) {enable(false);} else {enable(true);}
    }

    public static void selectWithMouse() {
        if (isEnabled()) {
            for (NewObject o : NewObjectStorage.getObjectList()) {
                if (o.getDisplayBox().contains(GlobalData.getMouse())) {
                    o.select();
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
    }

    public static void inspect() {
        if (isEnabled()) {
            for (NewObject o : NewObjectStorage.getObjectList()) {
                if (o.getDisplayBox().contains(GlobalData.getMouse()) && UIManager.getGUI("HUD").isVisible()) {
                    NewHUD hud = (NewHUD) UIManager.getCurrentGUI();
                    hud.inspectionContextMenu(o);
                    hud.setCurrentObject(o);
                }
            }
        }
    }

}
