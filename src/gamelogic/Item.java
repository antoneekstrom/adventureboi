package gamelogic;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String name;
    protected transient BufferedImage image;
    protected String sortingTag;
    
    //Description
    protected String[] description;
    protected String effect;

    public Item(String name) {
        this.name = name;
    }

    public BufferedImage image() {
        return image;
    }

    public String name() {return name;}
    public String[] description() {return description;}
    public String effect() {return effect;}
    public String sortingTag() {return sortingTag;}

    //values for easy access

    /** Signifies that this item is some sort of statistical upgrade. */
    public static String statup = "statup";

    /** This item unlocks some type of ability for the character. */
    public static String ability = "ability";

}
