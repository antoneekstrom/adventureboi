package objects;

import adventuregame.Images;
import items.DeceasedAngryShroom;

public class NewAngryShroom extends Enemy {


    public NewAngryShroom() {
        super(0, 10, 100, 5, "angryshroom");
    }

    @Override
    public void startCore() {
        get().setSize(125, 125);
    }

    @Override
    public void startAnimator() {
        super.startAnimator();
        getAnimator().addList(Images.getFolderImages("assets/animated_sprites/angryshroom"));
        getAnimator().setIndexRange(0,3);
    }

    @Override
    public void startMisc() {
        super.startMisc();
        addDrop(new DeceasedAngryShroom());
        destructOnDeath = false;
        contactDamage = 35;
    }

    @Override
    public void dropItem() {
        super.dropItem();
    }

    @Override
    public void playerContact(NewPlayer col) {
        if (isDead()) {
            shrinkDone();
        }
        else {
            super.playerContact(col);
        }
    }

    @Override
    public void die() {
        super.die();
        getAnimator().setIndexRange(3, 3);
    }

    @Override
    public void startAI() {
        super.startAI();
    }

}