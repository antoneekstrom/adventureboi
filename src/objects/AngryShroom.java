package objects;

import adventuregame.Images;
import gamelogic.NewObjectStorage;
import items.DeceasedAngryShroom;

public class AngryShroom extends NewObject implements ObjectMethods {

    private DeceasedAngryShroom drop;

    public AngryShroom() {
        setName("angryshroom");
        setText("angryshroom");
    }

    public void initialize() {
        super.initialize();
        super.setImage(Images.getImage("angryshroom"));
        get().setSize(125, 125);
        
        //ai
        createAI();
        getAI().jumpFrequency(0.03f);
        getAI().jumpforce(240);
        getAI().speed(14);
        
        //physics
        physics().mass(5);

        //health
        enableHealthModule(100);
        healthModule().showHp(true);
        healthModule().healthbar().yOffset = -75;

        //animator
        enableAnimator();
        getAnimator().addList(Images.getFolderImages("assets/animated_sprites/angryshroom"));
        getAnimator().setIndexRange(0, 3);
        getAnimator().speed(5);
        
        //drop
        drop = new DeceasedAngryShroom();

        showDebug(true);
    }

    public void ai() {
        super.ai();
    }

    public void logic() {
        if (healthModule().isDead() && getAI().isEnabled()) {
            die();
        }
        setDebugString(String.valueOf(doesIntersect()));
    }

    public void die() {
        getAI().setEnabled(false);
        getAnimator().setIndexRange(3, 3);
        healthModule().showHp(false);
    }


    public void animate() {
        super.animate();
    }
    
	public void intersect(NewObject collision) {
    }
    
    public void collide(NewObject collision) {
        super.collide(collision);
        getAI().collision(collision);
        testPickup(collision);
    }

    public void testPickup(NewObject collision) {
        if (healthModule().isDead() && collision.getClass().equals(NewPlayer.class)) {
            NewObjectStorage.remove(this);
            NewPlayer player = (NewPlayer) collision;
            player.playerData().inventory().add(drop);
        }
    }

    public void update() {
        super.update();

    }

    public void destruct() {
        super.destruct();
        die();
        NewObjectStorage.remove(this);
    }
}
