package objects;

import java.awt.Graphics;

import adventuregame.Images;
import gamelogic.NewObjectStorage;

public class Fireball extends NewObject implements ObjectMethods {

    String direction = "";
    int velocity = 25;
    int distanceTraveled = 0;
    int maxDistance = 1000;
    int damage = 15;
    /** True if ability is fully charged. */
    private boolean charged = false;

    public Fireball(String d) {
        setName("fireball");
        direction = d;
    }

    public void charged() {
        charged = true;
        setImage(Images.getImage("chargedfire"));
    }
    
    public void initialize() {
        super.initialize();
        get().setSize(50, 50);
        physics().setGravity(false);
        setImage(Images.getImage("fire"));
    }

    public void logic() {
        if (direction.equals("left")) {
            setX(getX() - velocity);
            distanceTraveled += velocity;
        }
        else if (direction.equals("right")) {
            setX(getX() + velocity);
            distanceTraveled += velocity;
        }
        if (distanceTraveled >= maxDistance) {
            NewObjectStorage.remove(this);
        }
    }

    public void intersect(NewObject collision) {
        NewObjectStorage.remove(this);
        if (collision.getHealthModule() != null) {
            collision.getHealthModule().decreaseHealth(damage);
        }
    }

    public void animate() {

    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawString(String.valueOf(charged), getDisplayCoordinate().x, getDisplayCoordinate().y - 50);
    }

    public void ai() {

    }

}
