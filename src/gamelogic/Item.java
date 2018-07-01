package gamelogic;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<String> tags = new ArrayList<String>();

    protected String name;
    protected transient BufferedImage image;
    private String sortingTag;
    private boolean equipped = false;
    protected boolean equippable = false;
    
    //Description
    protected String[] description;
    protected String effect;

    public Item(String name) {
        this.name = name;
    }

    public BufferedImage image() {
        return image;
    }

    public boolean equippable() {return equippable;}
    public String name() {return name;}
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

    //values for easy access

    /** Signifies that this item is some sort of statistical upgrade. */
    public static final String STATUP = "statup";

    /** This item unlocks some type of ability for the character. */
    public static final String ABILITY = "ability";

    /** This item is currently equipped. */
    public static final String EQUIPPED = "equipped";

}
