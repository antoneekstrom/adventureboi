package gamelogic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class EventTimer extends Timer {

    TimerTask timerTask;
    ArrayList<RandomEvent> events = new ArrayList<RandomEvent>();

    public ArrayList<RandomEvent> getEvents() {return events;}

    int interval;

    public EventTimer() {
        createTask();
    }
    
    public EventTimer(int interval) {
        createTask();
        start(interval);
    }

    public void start(int interval) {
        schedule(timerTask, 0, interval);
        this.interval = interval;
    }

    /** Set up task for the main timer to run. */
    void createTask() {
        timerTask = new TimerTask(){
            @Override
            public void run() {
                runEvents();
            }
        };
    }

    public void stop() {
        cancel();
    }

    RandomEvent getEvent(String name) {
        for (RandomEvent e : events) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        throw new NullPointerException("Could not find event " + name + "."); /* Should I do this? I don't know, I probably should just return null. This looks cooler though. */
    }

    /** Remove an event from this timer by name.
     *  @param name of event.
     */
    void removeEvent(String name) {
        for (Iterator<RandomEvent> iterator = events.iterator(); iterator.hasNext();) {
            RandomEvent e = iterator.next();
            if (e.getName().equals(name)) {
                iterator.remove();
            }
        }
    }

    /** Remove all events from this timer. */
    public void clearEvents() {
        events.clear();
    }

    /** Run all RandomEvents in this EventTimer. */
    void runEvents() {
        for (RandomEvent e : events) {
            e.run();
        }
    }

    /** Add a random event to this timer to be run on the set interval. */
    public void addRandomEvent(RandomEvent event) {
        events.add(event);
    }

    public int getInterval() {return interval;}

}