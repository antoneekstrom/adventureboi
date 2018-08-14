package objects;

import java.util.TimerTask;

import adventuregame.Images;
import gamelogic.Countdowner;

public class FallingPlatform extends GameObject {

    int delay = 1500;

    public FallingPlatform() {
        super();
        start();
    }

    @Override
    public void collide(GameObject collision) {
        super.collide(collision);
        activate();
    }

    void activate() {
        new Countdowner(delay, new TimerTask(){
            @Override
            public void run() {
                physics().setGravity(true);
            }
        });
    }

    void start() {
        setImage(Images.getImage("platform"));
        physics().setGravity(false);
    }

}