package items;

import gamelogic.Item;

public class TallmushItem extends Item {

    private static final long serialVersionUID = 1L;

	public TallmushItem() {
        super("tallmush");
        description = new String[] {"Tallmushes are said to be very tall,", "I've heard."};
        effect = Item.STATUP;
    }

}