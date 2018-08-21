package gamelogic;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

class Screenshaker implements Runnable {
    
    int DURATION, STRENGTH;
    int FREQUENCY = 10;
    int offsetX = 0, offsetY = 0;

    public Screenshaker(int duration, int strength) {
        DURATION = duration;
        STRENGTH = strength;
    }

    public Screenshaker(int duration, int strength, int frequency) {
        DURATION = duration;
        STRENGTH = strength;
        FREQUENCY = frequency;
    }

    int getShake(Axis axis) {
        Random r = new Random();

        int i = r.nextInt(STRENGTH);
        if (r.nextFloat() < 0.5) {
            i = -i;
        }

        if (axis.equals(Axis.X)) {
            offsetX += i;
        }
        else if (axis.equals(Axis.Y)) {
            offsetY += i;
        }   

        return i;
    }

    void resetPos() {
        Camera.getCameraPosition().x += offsetX;
        Camera.getCameraPosition().y += offsetY;
    }

    public static enum Axis {
        X, Y
    }

    public void run() {
        Timer timer = new Timer();

        //scheduled to stop after DURATION
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                timer.cancel();
                timer.purge();
            }
        }, DURATION);

        //scheduled to run every FREQUENCY
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                Camera.getCameraPosition().x += getShake(Axis.X);
                Camera.getCameraPosition().y += getShake(Axis.Y);
            }
        }, 0, FREQUENCY);
    }

}