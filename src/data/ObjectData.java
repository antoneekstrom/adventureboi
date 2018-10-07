package data;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import gamelogic.ObjectStorage;
import items.Donut;
import items.abilities.FireballItem;
import objects.*;

public class ObjectData implements Serializable, Recreatable {

    private static final long serialVersionUID = 1L;
    
    //data
    private Rectangle rectangle;
    private Color color;
    private String className;
    private String text;
    private boolean gravity;
    private boolean intersect;

    //set
    public void rectangle(Rectangle r) {rectangle = r;}
    public void color(Color c) {color = c;}
    public void className(String name) {className = name;}
    public void text(String t) {text = t;}
    public void gravity(boolean b) {gravity = b;}
    public void intersect(boolean b) {intersect = b;}

    //get
    public Rectangle rectangle() {return rectangle;}
    public Color color() {return color;}
    public String className() {return className;}
    public String text() {return text;}
    public boolean gravity() {return gravity;}
    public boolean intersect() {return intersect;}

    /** Create and return ObjectData that contains vital data for the object.
     *  @param object The object to extract data from.
     */
    public ObjectData(GameObject object) {
        rectangle(object.get());
        color(object.getColor());
        className(object.getClass().getName());
        text(object.getText());
        gravity(object.physics().hasGravity());
        intersect(object.doesIntersect());
    }

    public static ArrayList<ObjectData> createDataList() {
        return createDataList(ObjectStorage.getObjectList());
    }
    public static ArrayList<ObjectData> createDataList(ArrayList<GameObject> objectl) {

        ArrayList<ObjectData> l = new ArrayList<ObjectData>();

        for (GameObject o : objectl) {
            l.add(o.extractData());
        }

        return l;
    }

    public static Object createObject(Constructor<?> constructor, Object[] args) {
        Object object = null;

        try {
            object = constructor.newInstance(args);
        }
        catch (Exception e) {e.printStackTrace();}

        return object;
    }

    public static Object createObject(String className) {
        Object o = null;
        Class<?> c;

        try {
            c = Class.forName(className);
            o = c.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return o;
    }

    public GameObject clone() {
        GameObject object = (GameObject) createObject(className());
        return clone(object);
    }
    
    public GameObject clone(GameObject object) {
        object.setRectangle(rectangle());
        object.setColor(color());
        object.setText(text());
        object.setIntersect(intersect());
        object.physics().setGravity(gravity());
        return object;
    }

    public static ArrayList<GameObject> createObjectList(ArrayList<ObjectData> datalist) {

        ArrayList<GameObject> l = new ArrayList<GameObject>();

        for (ObjectData d : datalist) {
            try {
                if (!d.className().equals(Player.class.getName())) {
                    l.add(d.clone());
                }
            }
            catch (Exception e) {e.printStackTrace();}
        }

        return l;
    }

    /** List of classnames for all objects and their corresponding displaynames. */
    private static transient HashMap<String, String> objects = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L; {
            put("Angryshroom", AngryShroom.class.getName());
            put("Starman", Starman.class.getName());
            put("Fireball", new ItemObject(new FireballItem()).getClass().getName());
            put("donut", new ItemObject(new Donut()).getClass().getName());
            put("Object", GameObject.class.getName());
    }};
    /** Get hashmap with all object display names as keys and entries with their full classname. */
    public static HashMap<String, String> objects() {return objects;}
    /** Get an array of strings representing class names of all objects in the game. */
    public static String[] classNames() {
        ArrayList<String> l = new ArrayList<String>();
        for (String s : objects.keySet()) {l.add(objects.get(s));}
        return l.toArray(new String[objects.size()]);
    }
    /** Get array of keyset. */
    public static String[] displayNames() {
        String[] arr = new String[objects.size()];
        arr = objects.keySet().toArray(arr);

        return arr;
    }


}
