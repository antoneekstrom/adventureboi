package items;

import data.NumberFactory;
import data.PlayerData;
import gamelogic.Item;
import objects.NewPlayer;

public class DeceasedAngryShroom extends Item {

    private static final long serialVersionUID = 1L;
    private double DAMAGE = 2;

    public DeceasedAngryShroom() {
        super("angryshroom");
        description = new String[] {"A very angery boi", "who has now been deceased."};
        effect = "+" + DAMAGE + " attack damage";
        imageName("deadangryshroom");
        addTag(STATUP);
    }

    @Override
    protected void scaleStats() {
        super.scaleStats();
        DAMAGE *= NumberFactory.getStatScaling(level());
    }

    @Override
    public String getIdentifier() {
        String id = super.getIdentifier() + DAMAGE;
        return id;
    }

    @Override
    public void use(NewPlayer player) {
        if (!used()) {
            PlayerData d = player.playerData();
            d.damage(d.damage() + DAMAGE);
        }
        super.use(player);
    }

}