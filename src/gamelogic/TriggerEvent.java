package gamelogic;

import java.io.Serializable;

import objects.Player;
import objects.Spawner;

public interface TriggerEvent extends Serializable {

    boolean check(Spawner spawner, Player player);
    
    boolean check(Spawner spawner);
}