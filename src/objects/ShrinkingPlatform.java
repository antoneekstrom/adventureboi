package objects;

import java.util.TimerTask;

import adventuregame.Images;
import gamelogic.Countdowner;

public class ShrinkingPlatform extends GameObject {

    int delay = 1500;

    public ShrinkingPlatform() {
        super();
        start();
    }

    @Override
    public void collide(GameObject collision) {
        super.collide(collision);
        activate();
    }

    void activate() {
        if (!isShrinked()) {    
            new Countdowner(delay, new TimerTask(){
                @Override
                public void run() {
                    shrink();
                    new Countdowner(delay, new TimerTask(){
                        @Override
                        public void run() {
                            expand();
                        }
                    });
                }
            });
        }
    }

    void start() {
        setImage(Images.getImage("kantarell"));
        physics().setGravity(false);
        get().setSize(300, 300);
    }

}