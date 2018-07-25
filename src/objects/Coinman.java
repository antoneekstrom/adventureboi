package objects;

import java.awt.Dimension;

import adventuregame.Images;
import data.NumberFactory;
import gamelogic.ActionEvent;
import gamelogic.ObjectStorage;
import gamelogic.RandomEvent;
import items.Coin;

public class Coinman extends Enemy {

    int projDamage = 15, projSpeed = 12, projDistance = 800;

    public Coinman() {
        super(0, 50, 200, 100, "boinman");
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
    public void die() {
        super.die();
    }

    @Override
    public void playerContact(Player col) {
        super.playerContact(col);
    }

    void shoot(GameObject object, String direction) {
        ObjectStorage.add(new Projectile(projSpeed, direction, projDistance, object, new Dimension(50, 50), "boin_gold2"){{
            contactDamage = (int) (NumberFactory.getEnemyScaling(level()) * projDamage);
        }});
    }

    @Override
    public void startMisc() {
        super.startMisc();
        addDrop(new Coin(Coin.HUGE));
    }
    
    
    @Override
    public void startEvents() {
        addShootEvent();
    }

    private void addShootEvent() {
        GameObject o = this;

        getAI().addEvent(new RandomEvent(new ActionEvent(){
            @Override
            public void run(GameObject object) {
                GameObject nearestPlayer = ObjectStorage.getPlayer(ObjectStorage.findNearestPlayer(get().getLocation()));
                shoot(o, nearestPlayer.sideOfObject(o));
            }
        }, o, 0.7f, shootEventName()), 1500);
    }

    public String shootEventName() {return EVENT_SHOOT_COIN + "_" + idNumber();}
    static String EVENT_SHOOT_COIN = "ShootCoin";

    @Override
    public void startAI() {
        super.startAI();
        getAI().speed(2);
        getAI().jumpFrequency(0.007f);
    }

}