package adventuregame;

import java.awt.Point;

/** The purpose of this class is to act as a "camera" to
 *  move around the environment and show things outside the 
 *  initial viewport of the monitor/window. It is used by placing
 *  a "camera point" which will act as a center point and display
 *  objects relative to it.
 */
public class NewCamera {

    private static Point cameraPosition = new Point(0,0);    

    /** Run the camera, position display coordinates
     *  relative to camera position for all objects */
    public static void update() {
        for (NewObject o : NewObjectStorage.getObjectList()) {
            setDisplayCoordinates(o);
        }
    }

    /** Set the camera position to a point */
    public static void setCameraPos(Point p) {
        cameraPosition = p;
    }

    public static Point getCameraPosition() {
        return cameraPosition;
    }

    /** Set display coordinates for an object */
    public static void setDisplayCoordinates(NewObject o) {
        Point p = new Point();
        
        int x = checkX(o.rectangle().getLocation());
        int y = checkY(o.rectangle().getLocation());
        p.setLocation(p.getX() + x, p.getY() + y);

        setCameraPos(new Point(getCameraPosition().x -1, getCameraPosition().y));
        
        o.setDisplayCoordinate(p);
    }
    
    /** Calculate distance to camera positon from object
     * and return relative distance to center of screen.
     */
    private static int checkX(Point object) {
        return object.x - getCameraPosition().x;
    }

    /** See checkX() documentation */
    private static int checkY(Point object) {
        return object.y - getCameraPosition().y;
    }

}
