package objects;

import adventuregame.Images;
import gamelogic.Item;
import items.Coin;
import items.Donut;

public class Bigmush extends Enemy implements EnemyMold {

    public Bigmush() {
        super(0, 125, 300, "bigmush");
    }

    @Override
    public void startCore() {
        get().setSize(250, 250);
    }

    @Override
    public void startAnimator() {
        getAnimator().addList(Images.getFolderImages("assets/animated_sprites/bigmush"));
    }

    @Override
    public void startMisc() {
        super.startMisc();
        addDrop(new Donut());
        addDrop(new Coin(Coin.SMALL));
        addDrop(new Coin(Coin.LARGE));
        addDrop(new Coin(Coin.HUGE));
    }

    @Override
    public void dropItem() {
        for (Item drop : drops) {
            drop.level(level());
        }
        super.dropItem();
    }

    @Override
    public void startAI() {
        getAI().jumpFrequency(0f);
        getAI().speed(5);
    }

}