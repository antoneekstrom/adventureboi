package objects;

import adventuregame.Images;
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
        drop = new Donut();
        drop.level(level());
    }

    @Override
    public void dropItem() {
        drop.level(level());
        super.dropItem();
    }

    @Override
    public void startAI() {
        getAI().jumpFrequency(0f);
        getAI().speed(5);
    }

}