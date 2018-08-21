package objects;

import java.awt.Dimension;

public class NewFireball extends Projectile {

    Player player;
    boolean charged = false;

    public NewFireball(Player player) {
        super(25, player.abilityDirection, 1000, player, new Dimension(50, 50), "fireball");
        this.player = player;
        start();
    }

    void placement() {
        if (player.abilityDirection.equals("left")) {
            get().setLocation((int) player.get().getCenterX() - (int) player.getWidth() / 2 - getWidth(), (int) player.get().getCenterY() - (int) get().getHeight() + 30);
        }
        else if (player.abilityDirection.equals("right")) {
            get().setLocation((int) player.get().getCenterX() + (int) player.getWidth() / 2, (int) player.get().getCenterY() - (int) (get().getHeight()) + 30);
        }
    }

    void setSize() {
        get().setSize( (int) (get().getWidth() * player.abilityFactor), (int) (get().getHeight() * player.abilityFactor));
    }

    void charge() {
        if (player.chargePercentage == 10) {
            charged = true;
        }
    }

    private void start() {
        contactDamage = (int) player.calculateDamage();
        owner(player);
        charge();
        setSize();
        placement();
        destroyOnHit = true;
    }

}