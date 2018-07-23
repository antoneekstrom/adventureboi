package gamelogic;

import objects.NewObject;
import objects.Spawner;

public interface TriggerEvent {

    boolean check(NewObject object);
    
    boolean check(Spawner spawner);
}