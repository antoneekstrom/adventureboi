package gamelogic;

import java.awt.Rectangle;
import java.util.ArrayList;

import objects.NewObject;

public class NewCollision {

    public static void check(NewObject object1) {

        //frequent recreation of arraylist, might result in bad performance?
        ArrayList<NewObject> objects = NewObjectStorage.getObjectList();

        /* Object1 : Current object, object that will be acted upon and moved
           Object2 : other objects from list */

        //for intersection fix
        boolean i = false;

        //loop through all other objects
        for (NewObject object2 : objects) {
            //if object1 is not the same as object2 and they are intersecting
            if (object1.get().intersects(object2.get()) && !object2.equals(object1)) {
                if (object1.getCollision()) {
                    collision(object1, object2);
                    i = true;
                    object1.setIntersect(true);
                    object1.passCollision(object2);
                }
            }
            else {
                if (i == false) {
                    object1.setIntersect(false);
                }
            }
        }
    }

    // Collision is hell and I am sorry for this code.

    /** Calculate collision for two objects. */
    private static void collision(NewObject o1, NewObject o2) {

        //define hitboxes
        Rectangle r1 = o1.get();
        Rectangle r2 = o2.get();

        //determine distance of itersection on all side in pixels
        int r = 0, l = 0, t = 0, b = 0, dx = 0, dy = 0;

        //Which side o1 is of o2
        String side = "";
        String verticalSide = "";

        //if translation of o1 should take place on each axis
        boolean x = false;
        boolean y = false;

        //determine all distance values
        t = checkTop(r1, r2);
        b = checkBottom(r1, r2);
        dy = t+b;
        l = checkLeft(r1, r2);
        r = checkRight(r1, r2);
        dx = r+l;

        //determine side variables
        if (l == 0) {
            side = "right";
        }
        else {
            side = "left";
        }
        if (t == 0) {
            verticalSide = "bottom";
        }
        else {
            verticalSide = "top";
        }

        //determine if translation on each axis should take place
        if (dx < dy) {
            x = true;
        }
        else {
            y = true;
        }
        if (dx < r1.width) {
            x = true;
        }
        if (dy < r1.height) {
            y = true;
        }

        //move o1 out of o2
        //this might as well be magic to me, I have no idea what I am actually doing regarding this, sorry
        if (x && !y) {
            r1.x = r1.x + dx;
        }
        else if (y && !x) {
            r1.y = r1.y + dy;
        }
        else if (x && y) {
            if (side.equals("left")) {
                dx = -dx;
            }
            if (dx + r1.width < dy) {
                if (side.equals("left")) {
                    dx = -dx;
                }
                r1.x = r1.x + dx;
            }
            else {
                r1.y = r1.y + dy;
            }
        }
    }

    //check distance of intersection on all sides
    private static int checkLeft(Rectangle r1, Rectangle r2) {
        int i;
        
        if (r1.x < r2.x) {
            i = (int) ( r2.getMinX() - r1.getMaxX() );
        } else {i = 0;}

        return i;
    }
    
    private static int checkRight(Rectangle r1, Rectangle r2) {
        int i;
        
        if (r1.x > r2.x) {
            i = (int) ( r2.getMaxX() - r1.getMinX() );
        } else {i = 0;}
        
        return i;
    }

    private static int checkTop(Rectangle r1, Rectangle r2) {
        int i;

        if (r1.y < r2.y) {
            i = (int) ( r2.getMinY() - r1.getMaxY() );
        } else {i = 0;}

        return i;
    }
    
    private static int checkBottom(Rectangle r1, Rectangle r2) {
        int i;
        
        if (r1.y > r2.y) {
            i = (int) ( r2.getMaxY() - r1.getMinY() );
        } else {i = 0;}
        
        return i;
    }
}
