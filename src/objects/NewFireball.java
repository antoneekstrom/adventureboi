package objects;

import java.awt.Dimension;

public class NewFireball extends Projectile {

    Player player;

    public NewFireball(Player player) {
        super(10, player.abilityDirection, 100, player, new Dimension(50, 50), "fireball");
        this.player = player;
        start();
    }

    private void start() {
        damage((int) player.calculateDamage());
        
    }

}