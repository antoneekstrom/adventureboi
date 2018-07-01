package items.abilities;

import gamelogic.Item;

public class Ability extends Item {
    private static final long serialVersionUID = 1L;

	public Ability(String name) {
        super(name);
        effect = ABILITY;
        addTag(ABILITY);
        equippable = true;
    }

    public double COST, CHARGECOST, DAMAGE, FACTORMAX, FACTORINCREASE, COOLDOWN;

    public Double[] getValues() {
        return new Double[] {COST, CHARGECOST, DAMAGE, FACTORMAX, FACTORINCREASE, COOLDOWN};
    }

    public static boolean hasAllValues(Ability a) {
        boolean b = true;
        for (Double d : a.getValues()) {
            if (d == null) {b = false;}
        }
        return b;
    }

}