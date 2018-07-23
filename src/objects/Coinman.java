package objects;

import java.awt.Dimension;

import adventuregame.Images;
import gamelogic.ObjectStorage;
import items.Coin;

public class Coinman extends Enemy {

    public Coinman() {
        super(0, 50, 200, 100, "coinman");
    }

    @Override
    public void startCore() {
        super.startCore();
        get().setSize(150, 150);
        setImage(Images.getImage("coinman"));
    }

    @Override
    public void startAnimator() {
        super.startAnimator();
        getAnimator().addList(Images.getFolderImages("assets/animated_sprites/coinman"));
        getAnimator().speed(20);
    }

    @Override
    public void playerContact(Player col) {
        super.playerContact(col);
        ObjectStorage.add(new Projectile(3, Projectile.RIGHT, 500, this, new Dimension(25, 25), "boin_gold2"){{
            contactDamage = 5;
        }});
    }

    @Override
    public void startMisc() {
        super.startMisc();
        addDrop(new Coin(Coin.HUGE));
    }

    @Override
    public void startAI() {
        super.startAI();
        getAI().speed(2);
        getAI().jumpFrequency(0.007f);
    }

}