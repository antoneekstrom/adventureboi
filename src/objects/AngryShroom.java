package objects;

import adventuregame.Images;

public class AngryShroom extends NewObject implements ObjectMethods {

    public AngryShroom() {
    }

    public void initialize() {
        super.initialize();
        super.setImage(Images.getImage("angryshroom"));
        createAnimator();
        getAnimator().addList(Images.getAnimationImages("assets/animated_sprites/angryshroom"));
        getAnimator().setIndexRange(0, 3);
    }

    public void ai() {
    }

    public void logic() {
        
    }

    public void animate() {
        super.animate();
    }
    
	public void intersect() {
    }

    public void update() {
        super.update();
    }
}
