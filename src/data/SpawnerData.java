package data;

import java.io.Serializable;

import gamelogic.TriggerEvent;
import objects.GameObject;
import objects.Spawner;

public class SpawnerData extends ObjectData implements Serializable {

    private static final long serialVersionUID = 1L;

    public SpawnerData(Spawner object) {
        super(object);
        amountToSpawn = object.amountToSpawn;
        maxAlive = object.maxAlive;
        cooldown = object.cooldown;
        classToSpawn = object.classToSpawn;
        triggers = new TriggerEvent[object.triggers.size()];
        triggers = object.triggers.toArray(triggers);
        level = object.level;
    }

    int DEFAULT_DETECTION_RANGE = 150;
    int level = 0;
    int amountToSpawn, maxAlive, cooldown;
    Class<?> classToSpawn;
    TriggerEvent[] triggers;
    TriggerEvent trigger;

    @Override
    public GameObject clone() {
        Spawner spawner;
        if (triggers != null) {
            spawner = new Spawner(amountToSpawn, maxAlive, cooldown, classToSpawn, triggers);
            spawner.level = level;
        }
        else if (trigger != null) {
            spawner = new Spawner(amountToSpawn, maxAlive, cooldown, classToSpawn, trigger);
            spawner.level = level;
        }
        else {
            spawner = new Spawner(amountToSpawn, maxAlive, cooldown, classToSpawn, Spawner.playerProximity(DEFAULT_DETECTION_RANGE));
        }

        return clone(spawner);
    }

}