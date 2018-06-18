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
                    object1.collide(object2);
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

        int l = checkLeft(r1, r2);
        int r = checkRight(r1, r2);
        int t = checkTop(r1, r2);
        int b = checkBottom(r1, r2);
        int x = r+l;
        int y = b+t;

        boolean moveX = false, moveY = false;
        boolean invertX = false, invertY = false;

        if (x < 0) {x = -x; invertX = true;}
        if (y < 0) {y = -y; invertY = true;}
        //---------------------//
        if (x < y) {
            moveX = true;

            //set side
            if (invertX) {o1.setCollisonSide("left");}
            else {o1.setCollisonSide("right");}
        }
        else {
            moveY = true;
            if (invertY) {o1.setCollisonSide("top");}
            else {o1.setCollisonSide("bottom");}
        }
        //---------------------//
        if (invertX) {x = -x;}
        if (invertY) {y = -y;}

        if (moveX) {
            r1.x = r1.x + x;
        }
        else if (moveY) {
            r1.y = r1.y + y;
        }
        if (o1.equals(NewObjectStorage.getPlayer(1))) {
            o1.setDebugString("x:" + x + " y:" + y);
        }
    }

    //check distance of intersection on all sides
    private static int checkLeft(Rectangle r1, Rectangle r2) {
        int i;
        
        if (r1.getCenterX() < r2.getCenterX()) {
            i = (int) ( r2.getMinX() - r1.getMaxX() );
        } else {i = 0;}

        return i;
    }
    
    private static int checkRight(Rectangle r1, Rectangle r2) {
        int i;
        
        if (r1.getCenterX() > r2.getCenterX()) {
            i = (int) ( r2.getMaxX() - r1.getMinX() );
        } else {i = 0;}
        
        return i;
    }

    private static int checkTop(Rectangle r1, Rectangle r2) {
        int i;

        if (r1.getCenterY() <= r2.getCenterY()) {
            i = (int) ( r2.getMinY() - r1.getMaxY() );
        } else {i = 0;}

        return i;
    }
    
    private static int checkBottom(Rectangle r1, Rectangle r2) {
        int i;
        
        if (r1.getCenterY() >= r2.getCenterY()) {
            i = (int) ( r2.getMaxY() - r1.getMinY() );
        } else {i = 0;}
        
        return i;
    }
}
