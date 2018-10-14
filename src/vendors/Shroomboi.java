package vendors;

import adventuregame.Images;
import gamelogic.Item;
import items.DeceasedAngryShroom;
import items.Donut;
import items.abilities.FireballItem;
import objects.Player;
import objects.Vendor;

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
        addToInv(new DeceasedAngryShroom(), 10);
        addToInv(new Donut(), 10);
        addToInv(new FireballItem(), 50);
    }

    @Override
    public double getPrice(Item i) {
        return 23;
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