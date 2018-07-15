package items;

import data.NumberFactory;
import gamelogic.Item;
import objects.NewPlayer;

public class Donut extends Item {

    private static final long serialVersionUID = 1L;
    private int HEALTH = 5;

	public Donut() {
        super("donut");
        description = new String[] {"Very yummy,", "and good for your tummy."};
        effect = "+" + HEALTH + " max health";
        addTag(Item.STATUP);
    }

    @Override
    protected void scaleStats() {
        super.scaleStats();
        HEALTH *= NumberFactory.getStatScaling(level());
        effect = "+" + HEALTH + " max health";
    }

    @Override
    public void use(NewPlayer player) {
        super.use(player);
        player.playerData().maxHealth(player.playerData().maxHealth() + HEALTH);
        player.healthModule().setMaxHealth(player.healthModule().maxHealth() + HEALTH);
        player.healthModule().heal(HEALTH, true);
    }

}