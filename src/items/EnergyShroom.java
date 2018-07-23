package items;

import data.NumberFactory;
import gamelogic.Item;
import objects.Player;

public class EnergyShroom extends Item implements Statup {

    private static final long serialVersionUID = 1L;
	private double ENERGY = 5;

    public EnergyShroom() {
        super("energyshroom");
        description = new String[] {"It will make you have", "more energy than you did", "before consuming it."};
        effect = getEffect();
        addTag(Item.STATUP);
    }

    protected void scaleStats() {
        super.scaleStats();
        ENERGY *= NumberFactory.getStatScaling(level());
        effect = "+" + ENERGY + " max energy";
    }

    @Override
    public void use(Player player) {
        super.use(player);
        player.playerData().maxenergy(player.playerData().maxenergy() + ENERGY);
    }

	@Override
	public double getStat() {
		return ENERGY;
	}

	@Override
	public String getEffect() {
		return "+" + (int)getStat() + " max energy";
	}

}