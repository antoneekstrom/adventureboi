package gamelogic;

import java.util.Timer;
import java.util.TimerTask;

public class Countdowner implements Runnable {

    int GOAL;
    int interval;
    int delay;
    Timer timer;
    TimerTask task;
    boolean done = false;

    /** @param interval in milliseconds */
    public Countdowner(int goal, int interval) {
        GOAL = goal;
        this.interval = interval;
    }

    /** @param delay delay in milliseconds to execute
     *  @param task task to execute after delay
     */
    public Countdowner(int delay, TimerTask task) {
        giveTask(task);
        this.delay = delay;
        autoStart();
    }

    /** @param offset in milliseconds */
    public Countdowner(int goal, int interval, int offset) {
        GOAL = goal;
        this.interval = interval;
    }

    public void giveTask(TimerTask t) {
        task = t;
    }

    private void autoStart() {
        timer = new Timer();
        timer.schedule(doTask(), delay);
    }

    private TimerTask doTask() {
        return new TimerTask(){
            @Override
            public void run() {
                timer.cancel();
                timer.purge();
                task.run();     
            }
        };
    }

	@Override
	public void run() {
        timer = new Timer();
        timer.schedule(doTask(), 0, interval);
	}

}