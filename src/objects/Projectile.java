package objects;

import java.awt.Dimension;
import java.awt.Rectangle;

import adventuregame.Images;
import gamelogic.ObjectStorage;

public class Projectile extends GameObject implements Property {
    
    String direction;
    int speed, maxDistance, distanceTraveled = 0, offset = 15, collisions = 0;
    public int contactDamage = 0;
    GameObject origin;
    GameObject owner;
    
    boolean destroyOnHit = true;

    public static final String RIGHT = "right", LEFT = "left", NONE = "none";

    public void owner(GameObject owner) {this.owner = owner;}
    public GameObject owner() {return owner;}

    public Projectile(int speed, String direction, int maxDistance, GameObject origin, Dimension size) {
        super();
        this.speed = speed;
        this.direction = direction;
        this.maxDistance = maxDistance;
        this.origin = origin;
        get().setSize(size);
        start();
    }
    
    public Projectile(int speed, String direction, int maxDistance, GameObject origin, Dimension size, String imagename) {
        super();
        this.speed = speed;
        this.direction = direction;
        this.maxDistance = maxDistance;
        this.origin = origin;
        get().setSize(size);
        setImage(Images.getImage(imagename));
        start();
    }

    @Override
    protected void logic() {
        super.logic();
        travel();
        checkDistance();
    }

    void checkDistance() {
        if (distanceTraveled >= maxDistance) {
            destruct();
        }
    }

    @Override
    public void destruct() {
        super.destruct();
        ObjectStorage.remove(this);
    }

    void travel() {
        if (direction.equals(Projectile.RIGHT)) {
            get().x += speed;
            distanceTraveled += speed;
        }
        else if (direction.equals(Projectile.LEFT)) {
            get().x -= speed;
            distanceTraveled += speed;
        }
    }

    @Override
    public void collide(GameObject collision) {
        super.collide(collision);
        if (collision.getHealthModule() != null && collisions == 0) {
            collision.healthModule().damage(contactDamage);
            collisions++;
        }
        if (destroyOnHit) {
            ObjectStorage.remove(this);
        }
    }

    private void startPos() {
        Rectangle r = origin.get();
        int y = r.y + (r.height / 2) - (get().height / 2);
        if (direction.equals(Projectile.RIGHT)) {
            get().setLocation(r.x + r.width + get().width + offset, y);
        }
        else if (direction.equals(Projectile.LEFT)) {
            get().setLocation(r.x - get().width - offset, y);
        }
    }

    private void start() {
        startPos();
        physics().setGravity(false);
    }

}