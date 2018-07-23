package objects;

import java.awt.Dimension;
import java.awt.Rectangle;

import adventuregame.Images;
import gamelogic.ObjectStorage;

public class Projectile extends GameObject {
    
    String direction;
    int speed, maxDistance, distanceTraveled = 0, offset = 15;
    public int contactDamage = 0;
    GameObject origin;

    public static final String RIGHT = "right", LEFT = "left", NONE = "none";

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
    public void playerContact(Player player) {
        super.playerContact(player);
        player.healthModule().damage(contactDamage);
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