package gamelogic;

import objects.GameObject;

public class Physics2 {

    /** Mass. */
    int MASS = 45;
    public void mass(int mass) {MASS = mass;}

    /** Object */
    private GameObject object;
    
    /** Resistance. */
    private static double RESISTANCE_FACTOR = 0.266;
    private int xRes = 0, yRes = 0;

    /** Gravity. */
    private static double MAX_GRAV_VEL = 30;
    private static double GRAV_SPEED = 0.3234;
    private double gravityVelocity = 0;
    
    private boolean gravity = true;
    public boolean hasGravity() {return gravity;}
    public void setGravity(boolean bool) {gravity = bool;}

    /** Velocity. */
    private int xVel = 0, yVel = 0;
    public int yVelocity() {return yVel;}

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
            addForce(0, getGrav());
        }
        if (object.onGround() && yVel > 0) {
            yVel = 1;
            gravityVelocity = 0;
        }
    }

    double getGrav() {
        if (yVel + (gravityVelocity + GRAV_SPEED) <= MAX_GRAV_VEL) {
            gravityVelocity += GRAV_SPEED;
        }
        double g = gravityVelocity;
        return calcForce(g);
    }

    public double calcForce(double velocity) {
        double f = 0;

        f = MASS * velocity;

        return f;
    }

    public void resetFallAcceleration() {
        yVel = 0;
        gravityVelocity = 0;
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
        int x = (xVel < 0) ? ((xVel + xRes > 0) ? (0) : (xRes)) : ((xVel - xRes < 0) ? (0) : (-xRes));
        int y = (yVel < 0) ? ((yVel + yRes > 0) ? (0) : (yRes)) : ((yVel - yRes < 0) ? (0) : (-yRes));
        xVel += x;
        yVel += y;
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
        //object.setDebugString("yv:" + yVel + " yr:" + yRes + " yp:" + object.get().y + " gv:" + gravityVelocity);
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