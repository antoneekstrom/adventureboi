package items.abilities;

import java.util.HashMap;

import gamelogic.Item;
import gamelogic.NewObjectStorage;
import objects.Fireball;
import objects.NewPlayer;

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

    public HashMap<String, Object> getValueMap() {
        HashMap<String, Object> m = super.getValueMap();
        m.remove("cost");
        return m;
    }

    public void start() {
        description = new String[] {"cast incredibly epic", "fireballs at your foes."};
        abilityDescription = "Fireball spell";
        imageName("fire");
        useOnPickup(false);

        COST = 0;
        CHARGECOST = 0.7;
        PERCENT_DAMAGE = 0.5;
        FACTORMAX = 2;
        FACTORINCREASE = 0.02;
        COOLDOWN = 35;
    }

    @Override
    public void use(NewPlayer player) {
        super.use(player);
        fireball(player);
    }

    public void fireball(NewPlayer player) {
        Fireball f = new Fireball(player.abilityDirection);
        f.damage = (int) player.calculateDamage();
        f.player = player.playerData().name();
        if (player.chargePercentage == 10) {
            f.charged();
        }
        f.get().setSize( (int) (f.get().getWidth() * player.abilityFactor), (int) (f.get().getHeight() * player.abilityFactor));
        if (player.abilityDirection.equals("left")) {
            f.get().setLocation((int) player.get().getCenterX() - (int) player.getWidth() / 2 - f.getWidth(), (int) player.get().getCenterY() - (int) f.get().getHeight() + 30);
        }
        else if (player.abilityDirection.equals("right")) {
            f.get().setLocation((int) player.get().getCenterX() + (int) player.getWidth() / 2, (int) player.get().getCenterY() - (int) (f.get().getHeight()) + 30);
        }
        NewObjectStorage.add(f);
    }

}