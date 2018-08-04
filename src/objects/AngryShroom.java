package objects;

import adventuregame.Images;
import gamelogic.ObjectStorage;
import items.DeceasedAngryShroom;

public class AngryShroom extends GameObject implements ObjectMethods {

    private DeceasedAngryShroom drop;
    private int contactDamage = 35;
    private int healthOnPickup = 10;
    private int experience = 10;

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

        showDebug(false);
    }

    public void ai() {
        super.ai();
    }

    public void logic() {
        if (healthModule().isDead() && getAI().isEnabled()) {
            die();
        }
    }

    public void die() {
        getAI().setEnabled(false);
        getAnimator().setIndexRange(3, 3);
        healthModule().showHp(false);
        dropXp();
    }

    private void dropXp() {
        String pname = ObjectStorage.findNearestPlayerName(get().getLocation());
        ObjectStorage.getPlayer(pname).giveXp(experience);
    }


    public void animate() {
        super.animate();
    }
    
	public void intersect(GameObject collision) {
    }
    
    public void collide(GameObject collision) {
        super.collide(collision);

        //when in contact with a player
        if (collision.getClass().equals(Player.class)) {
            if (healthModule().isDead()) {
                pickup(collision);
            }
            else {
                contactDamage(collision);
            }
        }
    }

    private void contactDamage(GameObject col) {
        col.healthModule().damage(contactDamage);
    }   

    public void pickup(GameObject collision) {
        ObjectStorage.remove(this);
        Player player = (Player) collision;
        player.addItem(drop);
        player.healthModule().heal(healthOnPickup, false);
    }

    public void update() {
        super.update();

    }

    public void destruct() {
        super.destruct();
        die();
        ObjectStorage.remove(this);
    }
}
