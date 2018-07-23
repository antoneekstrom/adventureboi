package objects;

import java.util.ArrayList;

import gamelogic.TriggerEvent;

public class Spawner extends GameObject {

    int amountToSpawn, maxAlive, cooldown, timer = 0;
    Class<?> classToSpawn;
    ArrayList<TriggerEvent> triggers = new ArrayList<TriggerEvent>();

    public Spawner(int amountToSpawn, int maxAlive, int cooldown, Class<?> classToSpawn, TriggerEvent e) {
        super();
        start();
        this.amountToSpawn = amountToSpawn;
        this.maxAlive = maxAlive;
        this.cooldown = cooldown;
        this.classToSpawn = classToSpawn;
        addTrigger(e);
    }
    public Spawner(int amountToSpawn, int maxAlive, int cooldown, Class<?> classToSpawn, TriggerEvent[] err) {
        super();
        start();
        this.amountToSpawn = amountToSpawn;
        this.maxAlive = maxAlive;
        this.cooldown = cooldown;
        this.classToSpawn = classToSpawn;
        for (TriggerEvent e : err) {addTrigger(e);}
    }

    private void start() {
    }

    @Override
    protected void logic() {
        super.logic();

        timer();
        if (check(this)) {
            spawn();
        }
    }

    /** Update timer. */
    void timer() {
        if (timer < cooldown) {timer++;}
    }

    /** Spawn a copy. */
    void spawn() {
        if (canSpawn()) {

        }
    }

    boolean canSpawn() {
        boolean can = true;

        if (amountToSpawn - 1 > 0) {amountToSpawn--;} else {can = false;} /* if spawn capacity is unmet */
        if (timer != cooldown) {can = false;} else {timer = 0;} /* If cooldown is ready reset it */

        return can;
    }

    /** Check all triggers and return true if any of them are met. */
    boolean check(Spawner sp) {
        for (TriggerEvent e : triggers) {
            if (e.check(this)) {return true;}
        }
        return false;
    }

    void addTrigger(TriggerEvent e) {
        triggers.add(e);
    }

}