package vendors;

import adventuregame.Images;
import gamelogic.Item;
import items.DeceasedAngryShroom;
import objects.Player;
import objects.Vendor;

public class Shroomboi extends Vendor {

    public Shroomboi() {
        super();
        setPlayerRange(450);
        setImage(Images.getImage("longvendor"));
        get().setSize(150, 300);
    }

    int price = 23;

    @Override
    public double getPrice(Item i) {
        return 23;
	}

    @Override
    public boolean interact(Player player) {
        return purchase(new DeceasedAngryShroom(), player);
    }

    @Override
    public boolean hasItem(Item i) {
        return true;
    }

}