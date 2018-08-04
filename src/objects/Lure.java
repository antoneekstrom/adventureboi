package objects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.TimerTask;

import adventuregame.Images;
import gamelogic.Countdowner;
import gamelogic.ObjectStorage;

public class Lure extends GameObject {

    public Lure() {
        super();
        get().setSize(120, 100);
        ArrayList<BufferedImage> l = Images.getFolderImages("assets/animated_sprites/aboi");
        setImage(l.get(l.size()-1));
        enableAnimator();
        getAnimator().addList(l);
        setName("lure");
    }

    public Lure(boolean activateOnSpawn) {
        super();
        get().setSize(120, 100);
        ArrayList<BufferedImage> l = Images.getFolderImages("assets/animated_sprites/aboi");
        setImage(l.get(l.size()-1));
        enableAnimator();
        getAnimator().addList(l);
        setName("lure");
    }

    @Override
    protected void animate() {
        super.animate();
    }

    int delay = 1000;
    int duration = 3000;
    boolean activated = false;

    void start() {
        new Countdowner(delay, new TimerTask(){
            @Override
            public void run() {
                attract();
            }
        });
    }

    @Override
    public void collide(GameObject collision) {
        super.collide(collision);
        if (!activated) {
            start();
            activated = true;
        }
    }

    @Override
    public void onLevelLoad() {
    }

    void attract() {
        for (GameObject o : ObjectStorage.findObjects(Enemy.class)) {
            o.getAI().ignorePlayer(duration);
            o.getAI().setGoal(get().getLocation());
            o.getAI().followGoal(true);
        }
        new Countdowner(duration, new TimerTask(){
            @Override
            public void run() {
                physics().setGravity(false);
                shrink();     
            }
        });
    }

    @Override
    protected void logic() {
        super.logic();
    }

}