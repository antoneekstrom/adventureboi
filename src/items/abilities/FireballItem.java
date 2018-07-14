package items.abilities;

import gamelogic.Item;

public class FireballItem extends Ability {

    private static final long serialVersionUID = 1L;

	public FireballItem() {
        super("fireball");
        start();
    }

    @Override
    protected void scaleStats() {
        super.scaleStats();
        FACTORMAX *= levelPow(Item.SMALL_INCREASE_FACTOR);
        COOLDOWN *= levelPow(Item.SMALL_DECREASE_FACTOR);
        FACTORINCREASE *= levelPow(Item.SMALL_INCREASE_FACTOR);
        CHARGECOST *= levelPow(Item.SMALL_DECREASE_FACTOR);
    }

    public void start() {
        description = new String[] {"cast incredibly epic", "fireballs at your foes."};
        imageName("fire");

        COST = 0;
        CHARGECOST = 0.7;
        PERCENT_DAMAGE = 0.5;
        FACTORMAX = 2;
        FACTORINCREASE = 0.02;
        COOLDOWN = 35;
    }

}