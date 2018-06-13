package objects;

import adventuregame.Images;

public class AngryShroom extends NewObject implements ObjectMethods {

    public AngryShroom() {
    }

    public void initialize() {
        super.initialize();
        super.setImage(Images.getImage("angryshroom"));
        enableAnimator();
        createAI();
        getAI().setSpeed(8);
        getAI().jumpChance(0.8);
        getAI().turnChance(0.4);
        get().setSize(125, 125);
        getAnimator().addList(Images.getFolderImages("assets/animated_sprites/angryshroom"));
        getAnimator().setIndexRange(0, 3);
        getAnimator().speed(5);
    }

    public void ai() {
        super.ai();
    }

    public void logic() {
        
    }

    public void animate() {
        super.animate();
    }
    
	public void intersect(NewObject collision) {
        getAI().passCollision(collision);
    }

    public void update() {
        super.update();
    }

    public void destruct() {
        super.destruct();
    }
}
