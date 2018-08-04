package objects;

import adventuregame.Images;
import items.Donut;

public class Bigmush extends Enemy implements EnemyMold {

    public Bigmush() {
        super(0, 125, 300, 15, "bigmush");
    }

    @Override
    public void startCore() {
        get().setSize(250, 250);
    }

    @Override
    public void startAnimator() {
        getAnimator().addList(Images.getFolderImages("assets/animated_sprites/bigmush"));
        getAnimator().setIndexRange(0, getAnimator().size() - 1);
        getAnimator().speed(15);
    }

    @Override
    public void startMisc() {
        super.startMisc();
        addDrop(new Donut());
    }

    @Override
    public void dropItem() {
        super.dropItem();
    }

    @Override
    public void startAI() {
        getAI().jumpFrequency(0f);
        getAI().speed(3);
        getAI().spontaneousDirectionSwitchFrequency = 0.017;
    }

}