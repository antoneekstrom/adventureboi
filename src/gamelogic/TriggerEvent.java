package gamelogic;

import objects.GameObject;
import objects.Spawner;

public interface TriggerEvent {

    boolean check(GameObject object);
    
    boolean check(Spawner spawner);
}