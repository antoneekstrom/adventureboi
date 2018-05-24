package adventuregame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class NewObject {

    //switches
    private boolean visible = true;
    private boolean collision = true;
    private boolean intersect = false;
    private boolean camera = true;
    private boolean debug = true;

    //attributes
    Color color_fg = Color.white;
    Font font = new Font("Comic Sans MS", 40, 40);

    //force
    private Force force;

    //positioning
    private Rectangle r;
    private Point displayCoordinate;

    public NewObject() {
        r = new Rectangle(100, 100);
        r.setLocation(0, 0);
        displayCoordinate = new Point(r.x, r.y);

        initialize();
    }

    /**setup object to specified type */
    public void initialize() {
        force = new Force();
    }

    public void setCollision(boolean b) {
        collision = b;
    }

    public void setIntersect(boolean b) {
        intersect = b;
    }

    /** Is called when intersecting with another collision-enabled object */
    private void intersect() {
        if (intersect) {

        }
    }

    /** Get object rectangle (hit/collision box). */
    public Rectangle rectangle() {
        return r;   
    }

    /** Update object logic */
    public void update() {
        collision();
        intersect();
        updateForce();
        updateDisplayCoordinates();
    }

    //coordinate methods for backwards compatability
    public void setX(int x) {
        r.x = x;
    }    

    public void setY(int y) {
        r.y = y;
    }

    public int getX() {
        return r.x;
    }

    public int getY() {
        return r.y;
    }

    //display coordinate methods
    public void setCx(int x) {
        displayCoordinate.x = x;
    }

    public void setCy(int y) {
        displayCoordinate.y = y;
    }

    public Point getDisplayCoordinate() {
        return displayCoordinate;
    }

    public void setDisplayCoordinate(Point p) {
        displayCoordinate = p;
    }

    /** Check collision */
    public void collision() {
        if (collision) {
            NewCollision.check(this);
        }

    }

    /** Enable/disable "camera" */
    public void setCamera(boolean b) {
        camera = b;
    }

    /** Update camera/display coordinates */
    private void updateDisplayCoordinates() {
        if (camera) {
            NewCamera.setDisplayCoordinates(this);
        }
    }

    public Force getForce() {
        return force;
    }

    public void newForce(Force f) {
        force = f;
    }

    /** Applies forces to object */
    private void updateForce() {
        force.gravity(r);
        force.update(r);
    }

    //display object
    public void paint(Graphics g) {

        //attribute setup
        g.setColor(color_fg);
        g.setFont(font);

        //paint to screen
        if (visible) {
            g.fillRect(getDisplayCoordinate().x, getDisplayCoordinate().y, r.width, r.height);

            if (debug) {
                g.drawString(String.valueOf(r.y), r.x, r.y);
            }
        }
    }
}
