package items;

import gamelogic.Item;
import objects.NewPlayer;

public class EnergyShroom extends Item {

    private static final long serialVersionUID = 1L;
	private double ENERGY = 5;

    public EnergyShroom() {
        super("energyshroom");
        description = new String[] {"It will make you have", "more energy than you did", "before consuming it."};
        effect = "+" + ENERGY + " max energy";
        addTag(Item.STATUP);
    }

    @Override
    public void use(NewPlayer player) {
        super.use(player);
        player.playerData().maxenergy(player.playerData().maxenergy() + ENERGY);
    }

}