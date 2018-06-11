package objects;

import java.util.ArrayList;
import java.util.HashMap;

public class ObjectTypes {

    final private static HashMap<String, NewObject> objectTypes = new HashMap<String, NewObject>() {
        private static final long serialVersionUID = 1L;
	{
        put("Fireball", new Fireball(""));
        put("AngryShroom", new AngryShroom());
        put("Object", new NewObject());
        put("star", new Starman());
        put("solidstar", new Starman());
    }};

    final private static ArrayList<String> exceptions = new ArrayList<String>() {
        private static final long serialVersionUID = 1L;
	{
        add("rectangle");
        add("object");
    }};

    public static boolean isExcluded(String s) {
        return exceptions.contains(s);
    }

    public static boolean isKnownType(String name) {
        return objectTypes.containsKey(name);
    }

    public static NewObject getObject(String name) {
        if (objectTypes.containsKey(name)) {
            return objectTypes.get(name);
        }
        else {
            NewObject o = new NewObject();
            o.setName(name);
            o.getForce().setGravity(false);
            o.setCollision(false);
            o.inititializeType();
            return o;
        }
    }

}
