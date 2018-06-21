package gamelogic;

import java.awt.Point;

import adventuregame.GlobalData;
import objects.NewObject;

/** The purpose of this class is to act as a "camera" to
 *  move around the environment and show things outside the 
 *  initial viewport of the monitor/window. It is used by placing
 *  a "camera point" which will act as a center point and display
 *  objects relative to it.
 */
public class NewCamera {

    /** Position of camera */
    private static Point cameraPosition = new Point(0,0);
    /** Position camera wants to be at, used for slow/smooth camera. */
    private static Point cameraDestination = new Point(0,0);
    private static boolean smoothCamera = true;
    private static NewObject focusedObject;

    /** Run the camera, position display coordinates
     *  relative to camera position for all objects */
    public static void update() {
        //smooth camera follow
        followObject();
        if (smoothCamera) {
            moveCameraTo(cameraDestination);
        }
        else { cameraPosition = cameraDestination; }

        //update object camera position for all objects
        for (int i = 0; i < NewObjectStorage.getObjectList().size(); i++) {
            setDisplayCoordinates(NewObjectStorage.getObjectList().get(i));
        }
    }

    private static void setFocusObject() {
        for (NewObject object : NewObjectStorage.getObjectList()) {
            if (object.cameraFocus()) {focusedObject = object;}
        }
    }

    /** Set camera destination to object. */
    private static void followObject() {
        setFocusObject();
        if (focusedObject != null) {
            centerCameraOn( new Point((int) focusedObject.get().getCenterX(), (int) focusedObject.get().getCenterY()) );
        }
    }

    /** Set the camera position to a point */
    public static void setCameraPos(Point p) {
        cameraPosition = p;
    }

    /** Center camera on object. */
    public static void centerCameraOn(Point p) {
        cameraDestination.setLocation(p.x - GlobalData.getScreenDim().width / 2,
        p.y - GlobalData.getScreenDim().height / 2);
    }

    /** Advance camera position towards destination. */
    public static void moveCameraTo(Point p) {
        //for x-axis
        int x = p.x - cameraPosition.x;
        cameraPosition.x += x / 10;
        
        //for y-axis
        int y = p.y - cameraPosition.y;
        cameraPosition.y += y / 10;
    }

    public static Point getCameraPosition() {
        return cameraPosition;
    }

    /** Get position of mouse relative to camera. The point represents the point
     *  in the game that is present on the screen.
     */
    public static Point getMouse() {
        Point m = GlobalData.getMouse();
        return new Point(m.x + getCameraPosition().x, m.y + getCameraPosition().y);
    }

    /** Set display coordinates for an object */
    public static void setDisplayCoordinates(NewObject o) {
        Point p = new Point();

        int x = checkX(o.get().getLocation());
        int y = checkY(o.get().getLocation());
        p.setLocation(p.getX() + x, p.getY() + y);

        o.setDisplayCoordinate(p);
    }
    
    /** Calculate distance to camera positon from object
     * and return relative distance to center of screen.
     */
    private static int checkX(Point object) {
        return object.x - getCameraPosition().x;
    }

    /** See checkX(). */
    private static int checkY(Point object) {
        return object.y - getCameraPosition().y;
    }

}
