package gamelogic;

import java.awt.Rectangle;
import java.io.Serializable;

import objects.NewObject;

public class NewAI implements Serializable {

    private static final long serialVersionUID = 1L;
	private NewObject object;
    private boolean enabled = true;

    //collision
    private NewObject collision;
    private NewObject lastGround;
    private NewObject lastWall;
    private String collisionType = "none";
    private String direction = "left";

    //values
    private int speed = 10;
    private double jumpFrequency = 0.05;
    private double jumpforce = 600;

    //direction switching
    private int setDirectionCooldown = 0;
    private int SET_DIRECTION_COOLDOWN = 35;
    private double spontaneousDirectionSwitchFrequency = 0.027;

    //edges
    private boolean closeToEdge = false;
    private int edgeDetectionRadius = 50;

    public void update(NewObject o) {
        if (isEnabled()) {
            object = o;
            //move object in current direction
            move();
            //method for debugging
            debug();
            //do things
            logic();
        }
    }
    
    public void collision(NewObject c) {
        if (isEnabled()) {
            collision = c;
            //determine type of collision
            determineCollision();
            //change direction when colliding
            detectCollision();
            //potentially execute an action
            action();
        }
    }

    private void action() {
        if (Math.random() < jumpFrequency && !closeToEdge) {jump();}
        if (Math.random() < spontaneousDirectionSwitchFrequency && !closeToEdge) {switchDirection();}
    }
    
    private void debug() {
        
    }
    
    /** determine type of collision. */
    private void determineCollision() {
        if (object.collisionSide().equals("top")) {
            collisionType = "ground";
        }
        else if (object.collisionSide().equals("left") && object.collisionSide().equals("right")) {
            collisionType = "wall";            
        }
    }

    private void logic() {
        edgeDetection();
        fallOfPrevention();
        updateCooldown();
    }

    private String sideOfObject(NewObject c) {
        String side = "none";
        if (object.get().getMaxX() < c.get().getCenterX()) {
            side = "left";
        }
        else if (object.get().getMinX() > c.get().getCenterX()) {
            side = "right";
        }
        return side;
    }

    private void fallOfPrevention() {
        if (closeToEdge && sideOfObject(lastGround).equals("left")) {
            setDirection("right");
        }
        else if (closeToEdge && sideOfObject(lastGround).equals("right")) {
            setDirection("left");
        }
    }
    
    /** check if close to edge */
    private void edgeDetection() {
        if (lastGround != null) {
            if (object.get().getMinX() < lastGround.get().getMinX() + edgeDetectionRadius 
            || object.get().getMaxX() > lastGround.get().getMaxX() - edgeDetectionRadius) {
                closeToEdge = true;
            }
            else {
                closeToEdge = false;
            }
        }
    }

    private void updateCooldown() {
        if (setDirectionCooldown > 0) {
            setDirectionCooldown--;
        }
    }

    public void jump() {
        object.physics().addForce(0, -jumpforce);
    }
    
    /** Determine if object should change direction. */
    private void detectCollision() {
        if (collisionType.equals("ground")) {
            lastGround = collision;
            if (newPosition().getMaxX() > collision.get().getMaxX() || newPosition().getMinX() < collision.get().getMinX()) {
                switchDirection();       
            }
        }
        else if (collisionType.equals("wall")) {
            lastWall = collision;
            if (object.collisionSide().equals("left")) {setDirection("right");}
            else if (object.collisionSide().equals("right")) {setDirection("left");}
        }
    }

    private Rectangle newPosition() {
        if (direction.equals("right")) {
            return new Rectangle(object.get().x + speed, object.get().y, object.get().width, object.get().height);
        }
        else if (direction.equals("left")) {
            return new Rectangle(object.get().x - speed, object.get().y, object.get().width, object.get().height);
        }
        else {
            return object.get();
        }
    }
    
    /** Move object in current direction. */
    private void move() {
        if (direction.equals("left")) {
            object.get().setLocation(object.get().x - speed, object.get().y);
        }
        else if (direction.equals("right")) {
            object.get().setLocation(object.get().x + speed, object.get().y);
        }
    }

    public void switchDirection() {
        if (direction.equals("left")) {setDirection("right");}
        else if (direction.equals("right")) {setDirection("left");}
    }
    public void setDirection(String d) {
        if (setDirectionCooldown == 0) {
            direction = d;
            setDirectionCooldown = SET_DIRECTION_COOLDOWN;
        }
    }

    public boolean isEnabled() {return enabled;}
    public void setEnabled(boolean b) {enabled = b;}
    public void jumpFrequency(float f) {jumpFrequency = f;}
    public void jumpforce(double d) {jumpforce = d;}
    public void speed(int s) {speed = s;}
}
