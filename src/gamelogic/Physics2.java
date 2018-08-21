package gamelogic;

import objects.GameObject;

public class Physics2 {

    /** Mass. */
    int MASS = 65;
    public void mass(int mass) {MASS = mass;}

    /** Object */
    private GameObject object;
    
    /** Resistance. */
    private static double RESISTANCE_FACTOR = 0.25;
    private int xRes = 0, yRes = 0;

    /** Gravity. */
    private boolean gravity = true;
    public boolean hasGravity() {return gravity;}
    public void setGravity(boolean bool) {gravity = bool;}
    private static double GRAVITY_STRENGTH = 5;
    public static void setGravity(double strength) { GRAVITY_STRENGTH = strength; }

    /** Velocity. */
    private int xVel = 0, yVel = 0;

    public Physics2(int mass) {
        this.MASS = mass;
    }

    public Physics2() {
    }

    /** Move the object according to current forces that are affecting it. */
    void translate() {
        object.get().x += xVel;
        object.get().y += yVel;
    }

    /** Calculate force things. */
    void calculate() {
        gravity();
        resistance();
    }

    /** Apply gravity. */
    void gravity() {
        if (!object.onGround() && hasGravity()) {
            yVel += getGrav();
        }
        if (object.onGround() && yVel > 0) {
            yVel = 1;
        }
    }

    double getGrav() {
        double g = GRAVITY_STRENGTH - ((yVel > 0) ? (Math.sqrt(yVel)) : (GRAVITY_STRENGTH));
        return GRAVITY_STRENGTH;
    }

    /** Calculate resistance. */
    void resistance() {
        xRes = getRes(xVel);
        yRes = getRes(yVel);
        applyResistance();
    }
    
    /** Get appropriate resistance from velocity. */
    public int getRes(int vel) {
        double res = 0;

        res = ( vel * RESISTANCE_FACTOR );

        return (int)res;
    }

    /** Apply resistance. */
    void applyResistance() {
        xVel += (xVel < 0) ? (xRes) : (-xRes);
        yVel += (yVel < 0) ? (yRes) : (-yRes);
    }

    /** Apply force to object. */
    public void addForce(double x, double y) {
        xVel += getVel(x);
        yVel += getVel(y);
    }

    /** Get appropriate velocity from force. */
    public int getVel(double f) {
        return (int) (f / MASS);
    }

    void debug() {
        object.setDebugString("yv:" + yVel + " yr:" + yRes + " yp:" + object.get().y + " g:" + object.onGround());
    }

    /** Update physics for this object. */
    public void update(GameObject object) {
        this.object = object;
        calculate();
        translate();

        debug();
    }

    public void collide(GameObject collision) {
    }

}