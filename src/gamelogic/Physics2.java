package gamelogic;

import objects.GameObject;

public class Physics2 {

    /** Mass. */
    int MASS = 45;
    public void mass(int mass) {MASS = mass;}

    /** Object */
    private GameObject object;
    
    /** Resistance. */
    private static double RESISTANCE_FACTOR = 0.136;
    private double xRes = 0, yRes = 0;

    /** Gravity. */
    private static double MAX_GRAV_VEL = 20;
    private static double GRAV = 0.412;
    private static double START_VEL = 6;
    
    private boolean gravity = true;
    public boolean hasGravity() {return gravity;}
    public void setGravity(boolean bool) {gravity = bool;}

    /** Velocity. */
    private double xVel = 0, yVel = 0;
    public double yVelocity() {return yVel;}

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
    }

    /** Apply gravity. */
    void gravity() {
        if (!object.onGround() && hasGravity()) {
            addForce(0, calcForce(getGrav()));
            if (yVel < START_VEL) {
            }
        }
        if (object.onGround() && yVel > 0) {
            yVel = 1;
        }
    }

    double getGrav() {
        if (yVel + GRAV <= MAX_GRAV_VEL) {
            return GRAV;
        }
        else {
            return 0;
        }
    }

    public double calcForce(double velocity) {
        double f = 0;

        f = MASS * velocity;

        return f;
    }

    public void resetFallAcceleration() {
        reduceFallAcceleration(1);
    }
    public void reduceFallAcceleration(float percent) {
        yVel -= yVel * percent;
    }

    /** Get appropriate resistance from velocity. */
    public double getRes(double vel) {
        double res = 0;

        res = ( vel * RESISTANCE_FACTOR );

        return res;
    }

    /** Apply resistance. */
    void applyResistance() {
        double x = (xVel < 0) ? ((xVel + xRes > 0) ? (0) : (xRes)) : ((xVel - xRes < 0) ? (0) : (-xRes));
        double y = (yVel < 0) ? ((yVel + yRes > 0) ? (0) : (yRes)) : ((yVel - yRes < 0) ? (0) : (-yRes));
        xVel += x;
        yVel += y;
    }

    /** Apply force to object. */
    public void addForce(double x, double y) {
        double xv = getVel(x);
        double yv = getVel(y);

        xRes = getRes(xv);
        yRes = getRes(yv);
        
        xVel += xv - xRes;
        yVel += yv - yRes;
    }

    /** Get appropriate velocity from force. */
    public double getVel(double f) {
        return (f / MASS);
    }

    void debug() {
        //object.setDebugString("yv:" + (float)yVel + " yr:" + (float)yRes + " yp:" + object.get().y);
        object.setDebugString("xv:" + xVel + " xr:" + xRes);
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