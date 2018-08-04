package gamelogic;

import objects.GameObject;

public class Collision {

    private String side;
    private GameObject object;

    public Collision(GameObject object, String side) {
        this.side = side;
        this.object = object;
    }

    public String side() {return side;}
    public GameObject object() {return object;}

    public boolean isWall() {
        return side.equals("right") || side.equals("left");
    }

    public boolean isGround() {
        return side.equals("top");
    }

}