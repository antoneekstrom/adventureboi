package gamelogic;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import adventuregame.Images;
import adventuregame.Items;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

	protected String displayName;
    protected String name;
    protected transient BufferedImage image;
    protected String sortingTag;
    
    //Description
    protected String[] description;
    protected String effect;

    public Item(String name) {
        this.name = name;
        displayName = name;
        description = Items.getDescription(name);
        effect = Items.getEffect(name);
        sortingTag = Items.getSortingTag(name);
        image = Images.getImageHashMap("assets/items").get(displayName());
    }

    public BufferedImage image() {
        return image;
    }

    public String displayName() {return displayName;}
    public String name() {return name;}
    public String[] description() {return description;}
    public String effect() {return effect;}
    public String sortingTag() {return sortingTag;}

    //values for easy access

    /** Signifies that this item is some sort of statistical upgrade. */
    public static String statup = "statup";

}
