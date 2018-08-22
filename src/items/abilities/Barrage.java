package items.abilities;

import java.awt.Dimension;
import java.util.HashMap;

import gamelogic.Camera;
import gamelogic.Item;
import gamelogic.ObjectStorage;
import objects.GameObject;
import objects.Player;
import objects.Projectile;

public class Barrage extends Ability {

	private static final long serialVersionUID = 1L;

    double STAT;

    public Barrage() {
        super("barrage");
        description = description();
        effect = getEffect();
        abilityDescription = "haha nice";
        addTag(Item.ABILITY);
        imageName("explosion"); /* Set a name for the image. */
        useOnPickup(false);

        autoFire = true;

        COST = 0;
        CHARGECOST = 0.01;
        PERCENT_DAMAGE = 1;
        FACTORMAX = 1;
        FACTORINCREASE = 0.1;
        COOLDOWN = 10;
    }

    @Override
    protected void scaleStats() {
        super.scaleStats();
        FACTORMAX *= levelPow(Item.SMALL_INCREASE_FACTOR);
        COOLDOWN *= levelPow(Item.SMALL_DECREASE_FACTOR);
        FACTORINCREASE *= levelPow(Item.SMALL_INCREASE_FACTOR);
        CHARGECOST *= levelPow(Item.SMALL_DECREASE_FACTOR);
    }

    @Override
    public void use(Player player) {
        super.use(player);
        shoot(player);
    }

    public void shoot(Player player) {
        Projectile p = new Projectile(10, player.abilityDirection, 400, player, new Dimension(25, 25), "explosion") {
            @Override
            public void hit(GameObject collision) {
                super.hit(collision);
                Camera.shake(60, 15, 20);
            }
        };
        p.contactDamage = (int)player.calculateDamage();
        ObjectStorage.add(p);
    }

    public HashMap<String, Object> getValueMap() {
        HashMap<String, Object> m = super.getValueMap();
        m.remove("cost");
        return m;
    }

    @Override
    public String[] description() {
        return new String[] {"Fire a barrage of things."}; /* Set a description. */
    }

	public String getEffect() { /* Set an effect. */
		return Item.ABILITY;
	}    

}