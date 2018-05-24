package adventuregame;

import java.awt.Rectangle;
import java.util.ArrayList;

public class NewCollision {

    public static void check(NewObject object1) {

        //frequent recreation of arraylist, might result in bad performance?
        ArrayList<NewObject> objects = NewObjectStorage.getObjectList();

        /* Object1 : Current object, object that will be acted upon and moved
           Object2 : other objects from list */

        //loop through all other objects
        for (NewObject object2 : objects) {
            if (object1.rectangle().intersects(object2.rectangle())) {
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
        Rectangle r1 = o1.rectangle();
        Rectangle r2 = o2.rectangle();

        //determine distance of itersection on all side in pixels
        int x, y;
        x = checkX(r1,r2);
        y = checkY(r1,r2);

        //move object1 out of object2
        r1.setLocation(r1.x + x, r1.y + y);
    }

    //check intersection on x axis
    private static int checkX(Rectangle r1, Rectangle r2) {
        int i;

        i = r1.x - r2.x;

        return i;
    }
    
    //check intersection on y axis
    private static int checkY(Rectangle r1, Rectangle r2) {
        int i;
        
        i = r1.y - r2.y;
        
        System.out.println(i);

        return i;
    }
}
