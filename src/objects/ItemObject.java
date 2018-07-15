package objects;

import java.awt.Dimension;

import adventuregame.Images;
import gamelogic.Item;
import gamelogic.NewObjectStorage;

public class ItemObject extends NewObject {

    /** Size for GameObject Items */
    public static transient Dimension itemSize = new Dimension(75, 75);
    
    Item item;

    public ItemObject(Item i) {
        super();
        item = i;
        start();
    }

    public void level(int level) {
        item.level(level);
    }

    public int level() {return item.level();}

    public void start() {
        setImage(Images.getImage(item.imageName()));
        get().setSize(itemSize);
    }

    @Override
    public void collide(NewObject collision) {
        super.collide(collision);
        if (collision.getClass().equals(NewPlayer.class)) {
            NewPlayer p = (NewPlayer) collision;
            p.addItem(item);
            NewObjectStorage.remove(this);
        }
    }

}