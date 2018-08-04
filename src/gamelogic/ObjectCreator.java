package gamelogic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.util.HashMap;

import UI.Console;
import UI.UIManager;
import adventuregame.Images;
import adventuregame.Items;
import items.*;
import items.abilities.*;
import objects.*;

public class ObjectCreator {

    private static int currentObjectIndex = 0;
    private static Create currentObject;
    private static boolean enabled = false;
    private static int enemyLevel = 3;

    public static String PREVIEW_OBJECT = "CreatorPreview";

    private static boolean useGrid = true;

    public static void grid(boolean b) {useGrid = b;}
    public static boolean useGrid() {return useGrid;}
    public static void gridSize(int size) {ObjectPlacement.gridSize(size);}

    private static Color color = Color.white;
    public static void setColor(Color c) {color = c;}
    public static Color getColor() {return color;}

    private static boolean customSize = false;
    private static int width = 100;
    private static int height = 100;
    public static void width(int i) {width = i; preview();}
    public static int width() {return width;}
    public static int height() {return height;}
    public static void height(int i) {height = i; preview();}
    public static void toggleCustomSize() {if (customSize) {customSize = false;} else {customSize = true;} preview();}
    public static boolean customSize() {return customSize;}

    public static class Create {
        String className, type;
        Item item;

        public Create(String className, String type) {
            this.className = className;
            this.type = type;
        }
        public Create(Item item, String type) {
            this.item = item;
            this.type = type;
        }
        public void run() {
            switch (type) {
                case "enemy":
                    createEnemy(className);
                break;

                case "object":
                    createObject(className);
                break;

                case "item":
                    createItemObject(item);
                break;
            }

        }
        public String className() {return className;}
        public String type() {return type;}
    }

    static HashMap<String, Create> objects = new HashMap<String, Create>() {
        {
            put("angryshroom", new Create(NewAngryShroom.class.getName(), "enemy"));
            put("coinman", new Create(Coinman.class.getName(), "enemy"));
            put("bigmush", new Create(Bigmush.class.getName(), "enemy"));
            put("object", new Create(GameObject.class.getName(), "object"));
            put("starman", new Create(Starman.class.getName(), "object"));
            put("platform", new Create(Platform.class.getName(), "object"));
            put("mushroomblock", new Create(MushroomBlock.class.getName(), "object"));
            put("lure", new Create(Lure.class.getName(), "object"));
        }
    };

    public static void start() {
        addItemObjectsToList();
    }

    public static void enemyLevel(int l) {enemyLevel = l;}

    private static void addItemObjectsToList() {
        objects.put("fireball", new Create(new FireballItem(), "item"));
        objects.put("deadangryshroom", new Create(new DeceasedAngryShroom(), "item"));
        objects.put("donut", new Create(new Donut(), "item"));
        objects.put("energyshroom", new Create(new EnergyShroom(), "item"));
        objects.put("tallmush", new Create(new TallmushItem(), "item"));
        objects.put("icecube", new Create(new Icecube(), "item"));
        objects.put("barrage", new Create(new Barrage(), "item"));
        objects.put("boin", new Create(new Coin(), "item"));
    }

    public static GameObject getPreview() {
        GameObject[] arr = ObjectStorage.getObjectsByText(PREVIEW_OBJECT);
        if (arr.length > 0) {
            return ObjectStorage.getObjectsByText(PREVIEW_OBJECT)[0];
        }
        else {
            throw new NullPointerException();
        }
    }

    public static void createEnemy(String className) {
        try {
            Enemy o = (Enemy) Class.forName(className).newInstance();
            o.get().setLocation(getPreview().get().getLocation());
            o.setColor(getColor());
            o.level(enemyLevel);
            if (customSize) {o.get().setSize(width, height);}
            ObjectStorage.add(o);
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public static String getCurrentObjectName() {return (String) objects.keySet().toArray()[currentObjectIndex];}

    public static void createObject(String className) {
        try {
            GameObject o = (GameObject) Class.forName(className).newInstance();
            o.get().setLocation(getPreview().get().getLocation());
            o.setColor(getColor());
            if (customSize) {o.get().setSize(width, height);}
            ObjectStorage.add(o);
        }
        catch (Exception e) {e.printStackTrace();}
    }

    private static BufferedImage previewImage() {
        return Images.getImage(getCurrentObjectName());
    }

    public static void addPreview() {
        ObjectPreview op = new ObjectPreview(previewImage()) {
            @Override
            public void paint(Graphics g) {
                if (ObjectCreator.isEnabled()) {
                    super.paint(g);
                }
            }
        };
        op.setText(ObjectCreator.PREVIEW_OBJECT);
        ObjectStorage.add(op);
    }

    /** Update object preview. */
    private static void preview() {
        GameObject[] arr = ObjectStorage.getObjectsByText(PREVIEW_OBJECT);
        GameObject object = null;
        
        if (arr.length > 0) {
            object = arr[0];
            
            object.setImage(object.getImage());
        }

        try {
            if (currentObject.type.equals("item")) {

                object.get().setSize(new ItemObject(currentObject.item).get().getSize());
                object.setImage(Images.getImage(currentObject.item.imageName()));
            }
            else if (currentObject.type.equals("enemy")) {

                GameObject selectedClass = (GameObject) Class.forName(currentObject.className()).newInstance();
                object.setText(PREVIEW_OBJECT);
                if (object.getAI() != null) {
                    object.getAI().stopEvents();
                }
                object.get().setSize(selectedClass.get().getSize());
                object.setImage(Images.getImage(ObjectCreator.getCurrentObjectName()));
            }
            else {

                GameObject selectedClass = (GameObject) Class.forName(currentObject.className()).newInstance();
                object.get().setSize(selectedClass.get().getSize());
                object.setImage(Images.getImage(ObjectCreator.getCurrentObjectName()));
            }
            if (customSize) {
                
                object.get().setSize(width, height);
            }
        }
        catch (Exception e) {e.printStackTrace();}
    }

    private static void preview(GameObject object) {
        GameObject[] arr = ObjectStorage.getObjectsByText(PREVIEW_OBJECT);
        ObjectPreview preview = null;

        if (arr.length > 0) {
            preview = (ObjectPreview) arr[0];

            preview.set(object);
        }
    }

    public static void createItemObject(Item i) {
        try {
            Constructor<?> c = Class.forName(ItemObject.class.getName()).getConstructor(Item.class);
            ItemObject o = (ItemObject) c.newInstance(i);
            o.get().setLocation(getPreview().get().getLocation());
            o.setColor(getColor());
            i.level(enemyLevel);
            
            try {
                Currency cur = (Currency) i;
                cur.setValue(i.level());
            } catch (ClassCastException e) {e.printStackTrace();}

            o.get().setSize(i.size());
            if (customSize) {o.get().setSize(width, height);}
            ObjectStorage.add(o);
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public static Create getCurrentObject() {return currentObject;}

    public static void previousObject() {
        if (currentObjectIndex - 1 >= 0) {
            currentObjectIndex -= 1;
        }
        else {
            currentObjectIndex = objects.size() -1;
        }

        currentObject = objects.get(objects.keySet().toArray()[currentObjectIndex]);
        preview();
    }

    public static void createOnMouse() {
        if (creationAllowed()) {
            getCurrentObject().run();
        }
    }

    private static boolean creationAllowed() {
        boolean b = true;
        if (!isEnabled()) {b = false;}
        if (!UIManager.getGUI("HUD").isVisible()) {b = false;}
        return b;
    }
    
    public static void nextObject() {
        if (currentObjectIndex + 1 <= objects.size() - 1) {
            currentObjectIndex += 1;
        }
        else {
            currentObjectIndex = 0;
        }
        currentObject = objects.get(objects.keySet().toArray()[currentObjectIndex]);
        preview();
    }

    public static void enabled(boolean b) {
        enabled = b;
        if (b && ObjectInspector.isEnabled()) {ObjectInspector.enable(false);}
    }
    public static boolean isEnabled() {return enabled;}
    public static void toggleEnabled() {
        if (isEnabled()) {
            enabled(false);
        }
        else {
            enabled(true);
        }
    }

    public static void createItem(String name, int x, int y) {
        if (Items.itemClassNames.containsKey(name)) {

            boolean failed = false;
            String className = Items.itemClassNames.get(name);
            Item i = null;
            try {
                i = (Item) Console.findClass(className).newInstance();
            }
            catch (Exception e) {e.printStackTrace(); failed = true;}

            if (!failed) {
                ItemObject o = new ItemObject(i);
                o.get().setLocation(x, y);
                ObjectStorage.add(o);
            }
            else {Console.logError("No such item with the name " + name + ".");}
        }
    }

}
