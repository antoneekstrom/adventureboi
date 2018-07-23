package objects;

import java.awt.Dimension;

import adventuregame.Images;
import gamelogic.Item;
import gamelogic.ObjectStorage;

public class ItemObject extends GameObject {

    /** Size for GameObject Items */
    public static transient Dimension itemSize;
    
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
        itemSize = item.size();
        get().setSize(itemSize);
        collidable(false);
    }

    @Override
    protected void logic() {
        super.logic();
    }

    @Override
    public void collide(GameObject collision) {
        super.collide(collision);
        if (collision.getClass().equals(Player.class)) {
            Player p = (Player) collision;
            p.addItem(item);
            ObjectStorage.remove(this);
        }
    }

}