package gamelogic;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import adventuregame.Images;
import adventuregame.Items;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

	private String displayName;
    private String name;
    private transient BufferedImage image;
    private String sortingTag;
    
    //Description
    private String description;
    private String effect;

    public Item(String name) {
        this.name = name;
        description = Items.getDescription(name);
        displayName = name;
        effect = Items.getEffect(name);
        sortingTag = Items.getSortingTag(name);
        image = Images.getImageHashMap("assets/items").get(displayName());
    }

    public BufferedImage image() {
        return image;
    }

    public String displayName() {return displayName;}
    public String name() {return name;}
    public String description() {return description;}
    public String effect() {return effect;}
    public String sortingTag() {return sortingTag;}

}
