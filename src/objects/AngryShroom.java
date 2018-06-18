package objects;

import adventuregame.Images;

public class AngryShroom extends NewObject implements ObjectMethods {

    public AngryShroom() {
        setName("angryshroom");
        setText("angryshroom");
    }

    public void initialize() {
        super.initialize();
        super.setImage(Images.getImage("angryshroom"));
        enableAnimator();
        createAI();
        enableHealthModule(100);
        get().setSize(125, 125);
        getAnimator().addList(Images.getFolderImages("assets/animated_sprites/angryshroom"));
        getAnimator().setIndexRange(0, 3);
        getAnimator().speed(5);
    }

    public void ai() {
        super.ai();
    }

    public void logic() {
        if (healthModule().isDead()) {
            die();
        }
    }

    public void die() {
        getAI().setEnabled(false);
        getAnimator().setIndexRange(3, 3);
    }

    public void animate() {
        super.animate();
    }
    
	public void intersect(NewObject collision) {
    }
    
    public void collide(NewObject collision) {
        getAI().collision(collision);
    }

    public void update() {
        super.update();
    }

    public void destruct() {
        super.destruct();
    }
}
