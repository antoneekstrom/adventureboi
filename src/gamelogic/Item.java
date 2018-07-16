package gamelogic;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import data.NumberFactory;
import objects.ItemObject;
import objects.NewObject;
import objects.NewPlayer;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<String> tags = new ArrayList<String>();

    protected String name;
    protected String imageName;
    protected transient BufferedImage image;
    private String sortingTag;
    private boolean equipped = false;
    protected boolean equippable = false;
    /** How many times this items use() function has been used. */
    private int uses = 0;
    private boolean useOnPickup = true;
    private int level = 0;

    //Description
    protected String[] description;
    protected String effect;

    public Item(String name) {
        this.name = name;
    }

    public BufferedImage image() {
        return image;
    }

    public String imageName() {
        if (imageName == null) {
            return name();
        }
        else {return imageName;}
    }
    public void imageName(String n) {imageName = n;}
    public boolean equippable() {return equippable;}
    public String name() {return name;}
    public void name(String n) {name = n;}
    public String[] description() {return description;}
    public String effect() {return effect;}
    public String sortingTag() {return sortingTag;}
    public void equip(boolean b) {
        if (b) {
            addTag(EQUIPPED);
        }
        else {
            removeTag(EQUIPPED);
        }
    }
    public void addTag(String tag) {
        sortingTag = tag;
        tags.add(tag);
    }
    public boolean hasTag(String tag) {
        boolean b = false;
        try {
            if (tags.size() > 0) {
                b = tags.contains(tag);
            }
        }
        catch (Exception e) {e.printStackTrace();}
        
        return b;
    }
    public void removeTag(String tag) {
        String tagToRemove = "none";
        for (String t : tags) {
            if (t.equals(tag)) {
                tagToRemove = t;
            }
        }
        tags.remove(tagToRemove);
    }

    public int level() {return level;}
    public void level(int i) {
        level = i;
        scaleStats();
    }

    protected void scaleStats() {
        
    }

    /** Return an ID string built from stats and data from this item. */
    public String getIdentifier() {
        String id = name + level + equipped + uses + tags.size();
        return id;
    }

    /** If item should invoke use() on being picked up. */
    public boolean useOnPickup() {return useOnPickup;}

    /** Activates this items effect on the player. When overriding invoke super last. */
    public void use(NewPlayer player) {
        uses++;
    }

    public boolean used() {return uses > 0;}

    /** Get gameobject that can be picked up as item. */
    public NewObject getGameObject() {
        return new ItemObject(this);
    }

    //values for easy access

    /** Signifies that this item is some sort of statistical upgrade. */
    public static final String STATUP = "statup";

    /** This item unlocks some type of ability for the character. */
    public static final String ABILITY = "ability";

    /** This item is currently equipped. */
    public static final String EQUIPPED = "equipped";

    /** Get a hashmap of all values on this item. */
    public HashMap<String, Object> getValueMap() {

        HashMap<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("name", name());
                put("effect", effect());
                put("tags", tags);
                put("equippable", equippable());
                put("level", level);
            }

        };

        return map;
    }

    public static double SMALL_INCREASE_FACTOR = 1.1;
    public static double SMALL_DECREASE_FACTOR = 0.90;

    public static int LEVEL_VARIANCE = 3;

    public static int getRandomLevel(int lvl) {
        return lvl;
    }

    public double levelPow(double factor) {
        return Math.pow(factor, NumberFactory.getStatScaling(level()));
    }

}
