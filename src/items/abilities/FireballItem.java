package items.abilities;

public class FireballItem extends Ability {

    private static final long serialVersionUID = 1L;

	public FireballItem() {
        super("fireball");
        start();
    }

    public void start() {
        description = new String[] {"cast incredibly epic", "fireballs at your foes."};

        COST = 0;
        CHARGECOST = 0.5;
        DAMAGE = 15;
        FACTORMAX = 3;
        FACTORINCREASE = 0.03;
        COOLDOWN = 50;
    }

}