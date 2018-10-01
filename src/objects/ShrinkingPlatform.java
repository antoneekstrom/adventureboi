package objects;

import java.util.TimerTask;

import adventuregame.Images;
import gamelogic.Countdowner;

public class ShrinkingPlatform extends GameObject {

    int delay = 1500;
    boolean shouldActivate = true;

    public ShrinkingPlatform() {
        super();
        start();
        showDebug(false);
    }

    @Override
    public void collide(GameObject collision) {
        super.collide(collision);
        activate();
    }

    @Override
    protected void logic() {
        super.logic();
        setDebugString("shouldactivate: " + shouldActivate + " delay: " + delay);
    }

    void activate() {
        if (!isShrinked() && shouldActivate) {
            shouldActivate = false;

            Countdowner.wait(delay);
            deactivate();
        }
    }

    void deactivate() {
        shouldActivate = true;
        expand();
    }

    void start() {
        setImage(Images.getImage("kantarell"));
        physics().setGravity(false);
        get().setSize(300, 300);
    }

}