package objects;

import java.awt.Dimension;

import adventuregame.Images;
import data.NumberFactory;
import gamelogic.Camera;

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

    void chargeShake() { Camera.shake(65, 15, 5); }

    int MIN_HIT_SHAKE_STRENGTH = 5, MAX_HIT_SHAKE_STRENGTH = 15;
    void hitShake() {
        double percent = player.chargePercentage;
        int strength = (int)NumberFactory.percentFromMinToMax(MIN_HIT_SHAKE_STRENGTH, MAX_HIT_SHAKE_STRENGTH, percent * 10);
        Camera.shake(65, strength, 5);
    }

    void charge() {
        if (player.chargePercentage == 10) {
            charged = true;
            setImage(Images.getImage("chargedfire"));
            chargeShake();
        }
    }

    @Override
    public void hit(GameObject collision) {
        super.hit(collision);
        hitShake();
    }

    @Override
    public void playerContact(Player player) {
        super.playerContact(player);
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