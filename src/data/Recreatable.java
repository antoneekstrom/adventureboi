package data;

import objects.GameObject;

public interface Recreatable {

    public GameObject clone(GameObject object);

    public GameObject clone();

}