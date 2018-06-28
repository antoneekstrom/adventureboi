package items.abilities;

import gamelogic.Item;

public class FireballItem extends Item {

    private static final long serialVersionUID = 1L;

	public FireballItem() {
        super("fireball");
        start();
    }

    public void start() {
        description = new String[] {"Summon incredibly epic", "fireballs upon your foes."};
        effect = ability;
        sortingTag = ability;
    }

}