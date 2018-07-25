package gamelogic;

import java.awt.Rectangle;

import objects.GameObject;

public class Physics {

    private boolean gravity = true;

    private int MASS = 10;
    private double GRAVITY = 2;
    private double RESISTANCE_CONSTANT = 0.05;
    
    private double xResistance, yResistance;
    /** Force */
    private double xForce = 0, yForce = 0;

    /** Velocity added by force. */
    private double xVel = 0, yVel = 0;

    /** Object */
    GameObject object;

    /** Update physics */
    public void update(GameObject o) {
        object = o;
        if (gravity) {gravity();}
        applyResistance();
        applyForce();
    }

    /** Force that draws object downwards. */
    private void gravity() {
        yVel += GRAVITY;
    }

    /** Applies force that acts as air resistance. */
    private void applyResistance() {
        //calculate resistance
        calculateResistance();
        // x axis
        if (xVel > 0) {
            if (xVel - (xResistance / MASS) < 0) {xVel = 0;}
            else {
                xForce(-xResistance);
            }
        }
        else if (xVel < 0) {
            if (xVel + (xResistance / MASS) > 0) {xVel = 0;}
            else {
                xForce(xResistance);
            }
        }
        // y axis
        if (yVel > 0) {
            if (yVel - (yResistance / MASS) < 0) {yVel = 0;}
            else {
                yForce(-yResistance);
            }
        }
        else if (yVel < 0) {
            if (yVel + (yResistance / MASS) > 0) {yVel = 0;}
            else {
                yForce(yResistance);
            }
        }
    }

    /** Calculates strength of resistance force. */
    private void calculateResistance() {
        xResistance = RESISTANCE_CONSTANT * (xVel*xVel);
        yResistance = RESISTANCE_CONSTANT * (yVel*yVel);
    }

    private void calculateForce() {
        xVel += calcVel(xForce);
        yVel += calcVel(yForce);
        xForce = 0;
        yForce = 0;
    }

    /** Update and do force calculation. */
    private void applyForce() {
        Rectangle o = object.get();

        o.x += xVel;
        o.y += yVel;
    }

    /** Force that counteract current force on an object when it collides. */
    private void normal() {
        if (object != null) {
            if (object.collisionSide().equals("top")) {
                double f = MASS * yVel;
                yForce(-f);
            }
        }
    }

    /** Called when object is colliding with another one. */
    public void collide() {
        normal();
    }

    /** Force that "carries" objects on top of others. */
    private void carry() {
        if (object.collisionSide().equals("top")) {
            object.get().x += object.getLastCollision().velocityX() / 2;
        }
    }

    public void xForce(double f) {addForce(f, 0);}
    public void yForce(double f) {addForce(0, f);}
    public boolean hasGravity() {return gravity;}
    public void setGravity(boolean b) {gravity = b;}
    public double xForce() {return xForce;}
    public double yForce() {return yForce;}
    public double yVelocity() {return yVel;}
    public double xVelocity() {return xVel;}
    public void mass(int m) {MASS = m;}
    public int mass() {return MASS;}
    public void reset() {
        yVel = 0;
        xVel = 0;
        yForce = 0;
        xForce = 0;
    }

    private double calcVel(double f) {
        return f / MASS;
    }

    public void addForce(double x, double y) {
        xForce += x;
        yForce += y;
        calculateForce();
    }

}