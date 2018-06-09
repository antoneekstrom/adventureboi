package objects;

import adventuregame.Images;

public class Fireball extends NewObject implements ObjectMethods {

    String direction = "";
    int velocity = 10;

    public Fireball(String d) {
        direction = d;
        get().setSize(100, 100);
        setImage(Images.getImage("fire"));
    }

    public void initialize() {

    }

    public void logic() {
        if (direction.equals("left")) {
            setX(getX() - 10);
        }
        if (direction.equals("right")) {
            setX(getX() + 10);
        }
    }

    public void intersect() {

    }

    public void animate() {

    }

    public void ai() {

    }

}
