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

        //loop through all other objects
        for (NewObject object2 : objects) {
            //if object1 is not the same as object2 and they are intersecting
            if (object1.get().intersects(object2.get()) && !object2.equals(object1)) {
                collision(object1, object2);
                object1.setIntersect(true);
            }
            else {
                object1.setIntersect(false);
            }
        }
    }

    private static void collision(NewObject o1, NewObject o2) {

        //define hitboxes
        Rectangle r1 = o1.get();
        Rectangle r2 = o2.get();

        //determine distance of itersection on all side in pixels
        int r,l,t,b;
        r = checkRight(r1, r2);
        l = checkLeft(r1, r2);
        t = checkTop(r1, r2);
        b = checkBottom(r1, r2);

        //move object1 out of object2
        r1.setLocation(r1.x + r + l, r1.y + b + t);
    }

    //check intersection on x axis
    private static int checkTop(Rectangle r1, Rectangle r2) {
        int i;

        if (r1.y < r2.y) {
            i = (int) ( r2.getMinY() - r1.getMaxY() );
        } else {i = 0;}

        return i;
    }

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
    
    //check intersection on y axis
    private static int checkBottom(Rectangle r1, Rectangle r2) {
        int i;
        
        if (r1.y > r2.y) {
            i = (int) ( r2.getMaxY() - r1.getMinY() );
        } else {i = 0;}
        
        return i;
    }
}
