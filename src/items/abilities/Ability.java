package items.abilities;

import java.util.HashMap;

import data.NumberFactory;
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
    protected String abilityDescription;

    public String abilityDescription() {return abilityDescription;}

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

        map.put("cost", NumberFactory.round(COST));
        map.put("chargecost", NumberFactory.round(CHARGECOST));
        map.put("damagepercent", NumberFactory.round(PERCENT_DAMAGE));
        map.put("factormax", NumberFactory.round(FACTORMAX));
        map.put("factorincrease", NumberFactory.round(FACTORINCREASE));
        map.put("cooldown", NumberFactory.round(COOLDOWN));

        return map;
    }

}