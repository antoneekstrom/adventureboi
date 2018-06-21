package gamelogic;

import java.awt.Rectangle;

import objects.NewObject;

public class Physics {

    private boolean gravity = true;

    private int MASS = 10;
    private double GRAVITY = 10;
    private double RESISTANCE_CONSTANT = 0.1;
    
    private double xResistance, yResistance;
    /** Force */
    private double xForce = 0, yForce = 0;
    /** Velocity added by force. */
    private double xVel = 0, yVel = 0;
    /** Acceleration. */
    private double xAcc = 0, yAcc = 0;

    /** Object */
    NewObject object;

    /** Update physics */
    public void update(NewObject o) {
        object = o;
        
        if (gravity) {gravity();}
        normal();
        carry();
        applyResistance();
        acceleration();
        velocity();
        force();
    }

    /** Force that draws object downwards. */
    private void gravity() {
        yForce(GRAVITY);
    }

    /** Update and calculate acceleration. */
    private void acceleration() {
        xAcc = xForce / MASS;
        yAcc = yForce / MASS;
    }

    private void velocity() {
        xVel += xAcc;
        yVel += yAcc;
    }

    /** Applies force that acts as air resistance. */
    private void applyResistance() {
        //calculate resistance
        calculateResistance();
        // x axis
        if (xVel > 0) {
            if (xForce - xResistance < 0) {xForce = 0;}
            else {
                xForce(-xResistance);
            }
        }
        else if (xVel < 0) {
            if (xForce + xResistance > 0) {xForce = 0;}
            else {
                xForce(xResistance);
            }
        }
        // y axis
        if (yVel > 0) {
            if (yForce - yResistance < 0) {yForce = 0;}
            else {
                yForce(-yResistance);
            }
        }
        else if (yVel < 0) {
            if (yForce + yResistance > 0) {yForce = 0;}
            else {
                yForce(yResistance);
            }
        }
    }

    /** Calculates strength of resistance force. */
    private void calculateResistance() {
        yResistance = (yVel*yVel) * RESISTANCE_CONSTANT;
        xResistance = (xVel*xVel) * RESISTANCE_CONSTANT;
    }

    /** Update and do force calculation. */
    private void force() {
        Rectangle o = object.get();
        o.x += xVel;
        o.y += yVel;
    }

    /** Force that counteract current force on an object when it collides. */
    private void normal() {
        if (object.collisionSide().equals("top")) {
        }
    }

    /** Called when object is colliding with another one. */
    public void collide() {
        normal();
    }

    /** Force that "carries" objects on top of others. */
    private void carry() {

    }

    /** manipulate both axis of force. */
    public void force(double f) {
        xForce += f;
        yForce += f;
    }

    public void xForce(double f) {xForce += f;}
    public void yForce(double f) {yForce += f;}
    public boolean hasGravity() {return gravity;}
    public void setGravity(boolean b) {gravity = b;}
    public double xForce() {return xForce;}
    public double yForce() {return yForce;}
    public double yAcceleration() {return yAcc;}
    public double xAcceleration() {return xAcc;}
    public double yVelocity() {return yVel;}
    public double xVelocity() {return xVel;}
    public void reset() {
        yVel = 0;
        xVel = 0;
        yAcc = 0;
        xAcc = 0;
        yForce = 0;
        xForce = 0;
    }

    public void addForce(double x, double y) {
        xForce += x;
        yForce += y;
    }

}
