package vendors;

import adventuregame.Images;
import gamelogic.Item;
import items.DeceasedAngryShroom;
import items.Donut;
import items.abilities.FireballItem;
import objects.Player;
import objects.Vendor;
import objects.VendorItem;

public class Shroomboi extends Vendor {

    public Shroomboi() {
        super();
        setPlayerRange(450);
        setImage(Images.getImage("longvendor"));
        get().setSize(150, 300);
        setName("shroomboi");

        setInv();
    }

    void setInv() {
        addToInv(new VendorItem(new DeceasedAngryShroom(), 10, 0));
        addToInv(new VendorItem(new Donut(), 10, 0));
        addToInv(new VendorItem(new Donut(), 99, 1000));
        addToInv(new VendorItem(new FireballItem(), 50, 0));
    }

    @Override
    public boolean interact(Player player) {
        openInventory();
        return true;
    }

    @Override
    public boolean hasItem(Item i) {
        return true;
    }

}