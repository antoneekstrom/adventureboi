package gamelogic;

import objects.GameObject;

@FunctionalInterface
public interface CollisionFilter {

    boolean check(GameObject col);

}