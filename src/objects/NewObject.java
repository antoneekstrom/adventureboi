package objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import adventuregame.AI;
import adventuregame.Animator;
import adventuregame.Force;
import gamelogic.NewCamera;
import gamelogic.NewCollision;

public class NewObject {

    //switches
    private boolean visible = true;
    private boolean collision = true;
    private boolean intersect = false;
    private boolean camera = true;
    private boolean debug = false;
    private boolean hasImage = false;

    //attributes
    Color color_fg = Color.white;
    Font font = new Font("Comic Sans MS", 40, 40);

    //image/sprite
    private BufferedImage image;

    //labels
    private boolean passThrough = false;

    //modules
    private AI ai;
    private Animator animator;

    //force
    private Force force;

    //last collision
    private NewObject lastCollision;

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

    public void setColor(Color c) {
        color_fg = c;
    }

    public void setCollision(boolean b) {
        collision = b;
    }

    public boolean isPassThrough() {
        return passThrough;
    }

    public void setPassThrough(boolean b) {
        passThrough = b;
    }

    /** Pass object this one has collided with. */
    public void passCollision(NewObject o) {
        lastCollision = o;
    }

    public NewObject getLastCollision() {
        return lastCollision;
    }

    public void setIntersect(boolean b) {
        intersect = b;
    }

    public boolean doesIntersect() {
        return intersect;
    }

    /** Is called when intersecting with another object. */
    protected void intersect() {
    }

    /** Set sprite/image. */
    public void setImage(BufferedImage i) {
        image = i;
        hasImage = true;
    }

    /** Get object image. */
    public BufferedImage getImage() {
        return image;
    }

    public Animator getAnimator() {
        return animator;
    }

    public void createAnimator() {
        animator = new Animator(image);
    }

    public AI getAI() {
        return ai;
    }   

    public void createAI() {
        ai = new AI();
    }

    /** Get object rectangle/bouding box. This is also where position translation should occur. */
    public Rectangle get() {
        return r;
    }

    /** Calculate forces and positioning. */
    private void calculatePosition() {
        updateForce();
        collision();
    }

    /** Update stuff for object every tick. */
    protected void logic() {
    }

    /** Do animation related things every tick. */
    protected void animate() {
        if (animator != null) {
            animator.update();
            image = animator.getSprite();
        }
    }

    /** Set position and size for this object */
    protected void setRectangle(Rectangle nr) {
        r = nr;
    }

    /** Update ai: Pass information, get back information */
    protected void ai() {
        if (ai != null) {
            if (getLastCollision() != null) {
                ai.passCollision(getLastCollision());
            }
            ai.update(this);
        }
    }

    /** Update object. */
    public void update() {
        logic(); /* Do regular update stuff every tick */
        ai(); /* Do AI things every tick. */
        if (doesIntersect()) {intersect();}; /* Execute things when intersecting another object */
        calculatePosition(); /* Calculate forces and positioning */
        updateDisplayCoordinates(); /* Update position on screen */
        animate(); /* Do animation. */
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

    public int getWidth() {
        return r.width;
    }

    public int getHeight() {
        return r.height;
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

    public boolean getCollision() {
        return collision;
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

    public boolean ping() {
        return true;
    }

    public void applyForce(double x, double y) {
        force.applyForce(x, y);
    }

    public Force getForce() {
        return force;
    }

    public void setForce(Force f) {
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
            if (hasImage) {
                g.drawImage(image, getDisplayCoordinate().x,
                getDisplayCoordinate().y, r.width, r.height, null);
            } else {
                g.fillRect(getDisplayCoordinate().x,
                getDisplayCoordinate().y, r.width, r.height);
            }
            if (debug) {
                g.drawString(String.valueOf(getForce().getForceY()), getDisplayCoordinate().x,
                getDisplayCoordinate().y);
            }
        }
    }
}
