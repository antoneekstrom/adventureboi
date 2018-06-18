package data;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

import gamelogic.NewObjectStorage;
import objects.*;

public class ObjectData implements Serializable {

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
    public ObjectData(NewObject object) {
        rectangle(object.get());
        color(object.getColor());
        className(object.getClass().getName());
        text(object.getText());
        gravity(object.physics().hasGravity());
        intersect(object.doesIntersect());
    }

    public static ArrayList<ObjectData> createDataList() {

        ArrayList<ObjectData> l = new ArrayList<ObjectData>();

        for (NewObject o : NewObjectStorage.getObjectList()){
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

    public static ArrayList<NewObject> createObjectList(ArrayList<ObjectData> datalist) {

        ArrayList<NewObject> l = new ArrayList<NewObject>();

        for (ObjectData d : datalist) {
            try {
                NewObject o = (NewObject) createObject(d.className());
                
                o.setRectangle(d.rectangle());
                o.setColor(d.color());
                o.setText(d.text());
                o.setIntersect(d.intersect());
                o.physics().setGravity(d.gravity());

                l.add(o);  
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
            put("Fireball", Fireball.class.getName());
            put("Object", NewObject.class.getName());
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
