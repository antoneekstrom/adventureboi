package gamelogic;

import java.util.Timer;
import java.util.TimerTask;

public class Countdowner implements Runnable {

    int GOAL;
    int interval;
    int offset;
    Timer timer;
    TimerTask task;

    /** @param interval in milliseconds */
    public Countdowner(int goal, int interval) {
        GOAL = goal;
        this.interval = interval;
    }

    /** @param offset in milliseconds */
    public Countdowner(int goal, int interval, int offset) {
        GOAL = goal;
        this.interval = interval;
    }

    public void done() {

    }

    public void giveTask(TimerTask t) {
        task = t;
    }

	@Override
	public void run() {
        timer = new Timer();
        timer.schedule(task, 0, interval);
	}

}