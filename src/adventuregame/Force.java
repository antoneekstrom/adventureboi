package adventuregame;

import java.awt.Rectangle;
import java.io.Serializable;

public class Force implements Serializable {

    private static final long serialVersionUID = 1L;
	private int mass = 10;
    private int translatex, translatey;
    private double forcex = 0, forcey = 0;
    private double resistancex = 1, resistancey = 2;
    private double gravityStrength = 20;

    private boolean gravity = true;

    public Force() {
        
    }

    /** Set gravity */
    public void setGravity(boolean b) {
        gravity = b;
    }

    /** Returns true if object has gravity. */
    public boolean hasGravity() {
        return gravity;
    }

    public void setGravityStrength(double d) {
        gravityStrength = d;
    }

    /** Makes object fall */
    public void gravity(Rectangle r) {
        if (gravity) {
            r.y += gravityStrength;
        }
    }

    /** Returns current strength of gravity. */
    public double getGravityStrength() {
        return gravityStrength;
    }

    public double getForceY() {
        return forcey;
    }

    /** Apply forces to object */
    public void applyForce(double x, double y) {
		forcex += x;
		forcey += y;
	}

    /** Update forces */
    public void update(Rectangle r) {
        // resistance
        int rx = (int) (resistancex + (Math.pow(forcex, 1 / 4)));
        int ry = (int) (resistancey + (Math.pow(forcey, 1 / 4)));

        // x-axis
        if (forcex > 0) {
            if (forcex - rx < 0) {
                forcex = 0;
            } else {
                forcex -= rx;
            }
        } else {
            if (forcex + rx > 0) {
                forcex = 0;
            } else {
                forcex += rx;
            }
        }

        if (forcex > 0) {
            translatex = (int) ((Math.sqrt(forcex)) + (Math.sqrt(forcex) * (Math.sqrt(mass) / 2)));
            r.x = r.x + translatex;
        } else if (forcex < 0) {
            double f = -forcex;
            translatex = (int) ((Math.sqrt(f)) + (Math.sqrt(f) * Math.sqrt(mass) / 2));
            r.x = r.x - translatex;
        }

        // y-axis
        if (forcey > 0) {
            if (forcey - ry < 0) {
                forcey = 0;
            } else {
                forcey -= ry;
            }
        } else {
            if (forcey + ry > 0) {
                forcey = 0;
            } else {
                forcey += ry;
            }
        }

        if (forcey > 0) {
            translatey = (int) ((Math.sqrt(forcey)) + (Math.sqrt(forcey) * Math.sqrt(mass) / 2));
            r.y = r.y - translatey;
        } else if (forcey < 0) {
            double f = -forcey;
            translatey = (int) ((Math.sqrt(f)) + (Math.sqrt(f) * Math.sqrt(mass) / 2));
            r.y = r.y + translatey;
        }
    }

}
