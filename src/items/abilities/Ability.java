package items.abilities;

import java.util.HashMap;

import gamelogic.Item;

public class Ability extends Item {
    private static final long serialVersionUID = 1L;

	public Ability(String name) {
        super(name);
        effect = ABILITY;
        addTag(ABILITY);
        equippable = true;
    }

    public double COST, CHARGECOST, PERCENT_DAMAGE, FACTORMAX, FACTORINCREASE, COOLDOWN;

    public Double[] getValues() {
        return new Double[] {COST, CHARGECOST, PERCENT_DAMAGE, FACTORMAX, FACTORINCREASE, COOLDOWN};
    }

    public static boolean hasAllValues(Ability a) {
        boolean b = true;
        for (Double d : a.getValues()) {
            if (d == null) {b = false;}
        }
        return b;
    }

    @Override
    public HashMap<String, Object> getValueMap() {
        HashMap<String, Object> map = super.getValueMap();

        map.put("cost", COST);
        map.put("chargecost", CHARGECOST);
        map.put("damagepercent", PERCENT_DAMAGE);
        map.put("factormax", FACTORMAX);
        map.put("factor increase", FACTORINCREASE);
        map.put("cooldown", COOLDOWN);

        return map;
    }

}