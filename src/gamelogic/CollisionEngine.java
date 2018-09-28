package gamelogic;

import java.awt.Rectangle;
import java.util.ArrayList;

import objects.GameObject;

public class CollisionEngine {

    public static void check(GameObject object1) {

        //frequent recreation of arraylist, might result in bad performance?
        ArrayList<GameObject> objects = ObjectStorage.getObjectList();

        /* Object1 : Current object, object that will be acted upon and moved
           Object2 : other objects from list */

        //for intersection fix
        boolean i = false;

        //loop through all other objects
        for (int k = 0; k < objects.size(); k++) {
            GameObject object2 = objects.get(k);

            //if object1 is not the same as object2 and they are intersecting
            if (object1.get().intersects(object2.get()) && !object2.equals(object1)) {

                if (object1.getCollision() && object2.collidable()) {
                    if (object1.moveWhenColliding() && checkFilters(object1, object2)) {
                        collision(object1, object2);
                    }
                    i = true;
                    object1.setIntersect(true);
                    setCollisionSideObject2(object1, object2);
                    object1.passCollision(object2);
                    object1.collide(object2);
                    object2.collide(object1);
                }
            }
            else {
                if (i == false) {
                    object1.setIntersect(false);
                }
            }
        }
    }

    private static boolean checkFilters(GameObject o1, GameObject o2) {
        return o1.checkCollisionFilter(o2) && o2.checkCollisionFilter(o1);
    }

    private static void setCollisionSideObject2(GameObject object1, GameObject object2) {
        if (object1.collisionSide().equals("right")) {
            object2.setCollisonSide("left");
        }
        if (object1.collisionSide().equals("left")) {
            object2.setCollisonSide("right");
        }
        if (object1.collisionSide().equals("top")) {
            object2.setCollisonSide("bottom");
        }
        if (object1.collisionSide().equals("bottom")) {
            object2.setCollisonSide("top");
        }
    }

    // Collision is hell and I am sorry for this code.

    /** Calculate collision for two objects. */
    private static void collision(GameObject o1, GameObject o2) {

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

        //prevent falling through when object1 is in the center of object2 on x-axis
        if (x == 0) {
            r = 100; l = 100;
            x = r+l;
        }
        /* ------ */

        //pushing fix
        if (y == 0 && x > 0) {
            y = x+1;
        }
        else if ( y == 0 && x < 0) {
            y = x-1;
        }
        /* ------ */

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
