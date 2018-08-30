package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;

import com.sun.istack.internal.Nullable;

import adventuregame.Animator;
import adventuregame.HealthModule;
import data.ObjectData;
import gamelogic.AI;
import gamelogic.Camera;
import gamelogic.Collision;
import gamelogic.CollisionEngine;
import gamelogic.ObjectStorage;
import gamelogic.Physics2;
import gamelogic.Shrinker;

public class GameObject {

    /* --- static stuff --- */
    public static int getLevel(GameObject object) {
        if (ObjectStorage.isEnemy(object)) {
            Enemy e = (Enemy) object;
            return e.level();
        }
        return 0;
    }

	//switches
    private boolean visible = true;
    /** If this object should collide with players */
    public boolean collideWithPlayers = true;
    /** Set to true if object should receive collision logic. Will still affect other collision enabled objects. */
    private boolean collision = true;
    /** Set to true if object should be moved when colliding. */
    private boolean moveWhenColliding = true;
    /** Set to true if object should not be collidable with. */
    private boolean collidable = true;
    private boolean intersect = false;
    private boolean camera = true;
    private boolean debug = false;
    private boolean hasImage = false;
    private boolean selected = false;
    private boolean onGround = false;

    /** A list containing objects this one is CURRENTLY colliding with, none of that "last collision" bs */
    private ArrayList<Collision> currentCollisions = new ArrayList<Collision>();
    public ArrayList<Collision> collisions() {return currentCollisions;}
    public boolean checkForCollisionSide(String side) {
        for (Collision col : currentCollisions) {
            if (col.side().equals(side)) {
                return true;
            }
            else if (side.equals("wall")) {
                if (col.side().equals("left") || col.side().equals("right")) {return true;}
            }
            else if (side.equals("ground")) {
                if (col.side().equals("bottom") || col.side().equals("top")) {return true;}
            }
        }
        return false;
    }

    public void addedToLevel() {
    }

    public Point getCenter() {
        return new Point((int) get().getCenterX(), (int) get().getCenterY());
    }

    /** First object to the left of this one. */
    @Nullable public GameObject objectToLeft(int range) {
        ArrayList<GameObject> l = ObjectStorage.findNearbyObjects(range, get().getLocation());
        GameObject mostleft = null;

        int i = 0;
        for (GameObject o : l) {
            if (i == 0) {
                mostleft = o;
            }
            else {
                boolean leftOfThis = o.get().x < mostleft.get().x;
                if (!o.equals(this) && leftOfThis) {
                    mostleft = o;
                }
            }
            i++;
        }

        return mostleft;
    }

    /** First object to the right of this one. */
    @Nullable public GameObject objectToRight(int range) {
        ArrayList<GameObject> l = ObjectStorage.findNearbyObjects(range, get().getLocation());
        GameObject mostleft = null;

        int i = 0;
        for (GameObject o : l) {
            if (i == 0) {
                mostleft = o;
            }
            else {
                boolean rightOfThis = o.get().x > mostleft.get().x;
                if (!o.equals(this) && rightOfThis) {
                    mostleft = o;
                }
            }
            i++;
        }

        return mostleft;
    }
    
    public boolean onGround() {return onGround;}

    public boolean moveWhenColliding() {return moveWhenColliding;}
    public void moveWhenColliding(boolean b) {moveWhenColliding = b;}

    public boolean collidable() {return collidable;}
    public void collidable(boolean b) {collidable = b;}

    private int IDNumber;
    public void giveIdNumber(int i) {IDNumber = i;}
    public int idNumber() {return IDNumber;}

    public boolean isOfType(Class<?> c) {
        return c.isInstance(getClass());
    }

    public boolean cameraFocus = false;
    public void cameraFocus(boolean b) {
        for (GameObject o : ObjectStorage.getObjectList()) {
            if (o.cameraFocus()) {o.cameraFocus = false;}
        }
        cameraFocus = b;
    }
    public boolean cameraFocus() {return cameraFocus;}

    //attributes
    Color color_fg = Color.white;
    Font font = new Font("Comic Sans MS", 40, 40);
    protected String name = "Object";
    private String text;

    //image/sprite
    private BufferedImage image;

    //values
    private String debugString = "";

    //modules
    private AI ai;
    private Animator animator;
    private HealthModule healthModule;

    //physics
    private Physics2 physics = new Physics2();

    //tags
    private ArrayList<String> tags = new ArrayList<String>();
    public void addTag(String tag) {tags.add(tag);}
    public boolean hasTag(String tag) {
        for (String t : tags) {
            if (t.equals(tag)) {
                return true;
            }
        }
        return false;
    }
    public boolean hasTagThatContains(String text) {
        for (String t : tags) {
            if (t.contains(text)) {
                return true;
            }
        }
        return false;
    }

    //velocity
    private double velocityX = 0;
    private double velocityY = 0;
    private Point lastPos;


    //collision
    private GameObject lastCollision;
    private String collisionSide = "none";

    //positioning
    private Rectangle r;
    private Point displayCoordinate;
    private int yLimit = 3000;

    public GameObject() {
        r = new Rectangle(100, 100);
        r.setLocation(0, 0);
        setText("");
        displayCoordinate = new Point(r.x, r.y);
        onLevelLoad();
        initialize();
    }

    public GameObject(String text) {
        setText(text);
        r = new Rectangle(100, 100);
        r.setLocation(0, 0);
        displayCoordinate = new Point(r.x, r.y);
        initialize();
    }

    public GameObject(Rectangle r) {
        this.r = r;
        setText("");
        displayCoordinate = new Point(r.x, r.y);
        initialize();
    }

    public void name(String name) {this.name = name;}
    public void setCollisonSide(String s) {collisionSide = s;}
    public String collisionSide() {return collisionSide;}

    public String getText() {
        return text;
    }
    public ObjectData extractData() {
        return new ObjectData(this);
    }

    public void onLevelLoad() {
    }

    private boolean shrinked = false;
    private Dimension originalSize;
    private int shrinkDelay = 10;
    private Timer shrinkTimer;
    private float shrinkAmount = 1;

    public boolean beenShrunked = false;
    public int shrinkSpeed = 2;

    /** @param percent Percent to shrink, for example: "85" will shrink object to 15%. */
    public void shrinkAmount(int percent) {
        shrinkAmount = ((float) percent / (float) 100);
    }

    public void shrinkDone() {
        
    }

    public boolean isShrinked() {return shrinked;}

    public void shrink() {
        shrinked = true;
        originalSize = get().getSize();

        Shrinker s = new Shrinker(this, shrinkSpeed, shrinkAmount);
        if (!removeActionListener(shrinkTimer)) {
            shrinkTimer = new Timer(shrinkDelay, s);
        }
        else {shrinkTimer.addActionListener(s);}
        shrinkTimer.start();
    }
    public void expand() {
        if (shrinked) {
            shrinked = false;
            removeActionListener(shrinkTimer);
            Shrinker s =  new Shrinker(this, shrinkSpeed, originalSize);
            shrinkTimer.addActionListener(s);
            shrinkTimer.start();
        }
    }
    public void stopShrinker() {
        shrinkTimer.stop();
    }

    public void shrink(int percent) {
        shrinkAmount(percent);
        shrink();
    }

    private boolean removeActionListener(Timer t) {
        if (t != null) {
            if (t.getActionListeners().length > 0) {
                t.removeActionListener(t.getActionListeners()[0]);
            }
            return true;
        }
        else {
            return false;
        }
    }

    public String sideOfObject(GameObject object) {
        if (get().getCenterX() < object.get().getCenterX()) {
            return "left";
        }
        else {
            return "right";
        }
    }

    public Color getColor() {return color_fg;}

    public GameObject(ObjectData data) {
        setRectangle(data.rectangle());
        setColor(data.color());
        setText(data.text());
    }

    public void setDebugString(String s) {
        debugString = s;
    }

    public String getDebugString() {
        return debugString;
    }

    public void setText(String t) {
        text = t;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    /** setup object to specified type. Overwrite this method if this is not a generic object. */
    public void initialize() {
        physics = new Physics2();
        lastPos = get().getLocation();
    }

    public void setColor(Color c) {
        color_fg = c;
    }

    public void setCollision(boolean b) {
        collision = b;
    }

    public boolean canMove() {
        return canMove();
    }

    public void showDebug(boolean b) {
        debug = b;
    }

    /** Pass object this one has collided with. */
    public void passCollision(GameObject o) {
        lastCollision = o;
    }

    public GameObject getLastCollision() {
        return lastCollision;
    }

    public boolean isSelected() {return selected;}
    public void deselect() {selected = false;}
    public void select() {
        for (GameObject o : ObjectStorage.getObjectList()) {
            if (o.isSelected()) {o.deselect();}
        }
        selected = true;
    }

    public void setIntersect(boolean b) {
        intersect = b;
    }

    public boolean doesIntersect() {
        return intersect;
    }

    /** Is called when intersecting with another object. Use this one for removing/creating objects. Call super. */
    public void intersect(GameObject collision) {
    }
    
    /** Is called immediately upon intersection. Call super. */
    public void collide(GameObject collision) {
        physics().collide(collision);
        boolean collisionIsPlayer = false;
        if (collision.getClass().equals(Player.class)) {
            playerContact((Player)collision);
            collisionIsPlayer = true;
        }
        //collide with players only if allowed
        boolean shouldCollide = true;
        if (collisionIsPlayer) {shouldCollide = collideWithPlayers;}

        if (shouldCollide) {
            Collision col = new Collision(collision, collisionSide);
            if (!collisionExists(col)) {
                currentCollisions.add(col);
            }
        }
    }

    public boolean collisionExists(Collision c) {
        for (Collision col : currentCollisions) {
            if (col.object().equals(c.object()) && col.side().equals(c.side())) {return true;}
        }
        return false;
    }

    public void playerContact(Player player) {

    }

    protected HealthModule getHealthModule() {
        return healthModule;
    }

    /** Set sprite/image. */
    public void setImage(BufferedImage i) {
        image = i;
        hasImage = true;
    }

    public double velocityX() {return velocityX;}
    public double velocityY() {return velocityY;}

    private void calculateVelocity() {
        velocityX = get().x - lastPos.x;
        velocityY = get().y - lastPos.y;
        lastPos = get().getLocation();
    }

    /** Get object image. */
    public BufferedImage getImage() {
        return image;
    }

    public Animator getAnimator() {
        return animator;
    }

    public void setLocation(Point location) {
        get().setLocation(location);
    }

    public void enableAnimator() {
        animator = new Animator(image);
    }

    public AI getAI() {
        return ai;
    }   

    public void createAI() {
        ai = new AI(this);
    }

    /** Get object rectangle/bouding box. This is also where position translation should occur. */
    public Rectangle get() {
        return r;
    }

    /** Calculate forces and positioning. */
    private void calculatePosition() {
        updatePhysics();
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
    public void setRectangle(Rectangle nr) {
        r = nr;
    }

    /** Update ai: Pass information, get back information */
    protected void ai() {
        if (ai != null) {
            ai.update(this);
        }
    }

    /** Update object. */
    public void update() {
        ai(); /* Do AI things every tick. */
        logic(); /* Do regular update stuff every tick */
        basicLogic(); /* Basic logic for all objects */
        calculateVelocity();
        if (intersect) {intersect(lastCollision);}
        updateCollisionList();
        calculatePosition(); /* Calculate forces and positioning */
        updateDisplayCoordinates(); /* Update position on screen */
        animate(); /* Do animation. */
        if (healthModule != null) {healthModule().update(this);}
        debug();
    }

    private void updateCollisionList() {
        for (Iterator<Collision> i = currentCollisions.iterator(); i.hasNext();) {
            GameObject col = i.next().object();
            if (!col.get().intersects(get())) {i.remove();}
        }
    }
    
    /** Update method for debugging. */
    private void debug() {
    }

    /** Update basic logic stuff for all objects */
    private void basicLogic() {
        if (get().y > yLimit) {
            destruct();
        }
        onGround = checkForCollisionSide("top");
    }

    /** Called when object dies/is destroyed. Should invoke super. */
    public void destruct() {
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

    public Rectangle getDisplayBox() {
        return new Rectangle(getDisplayCoordinate().x, getDisplayCoordinate().y, get().width, get().height);
    }

    public void setDisplayCoordinate(Point p) {
        displayCoordinate = p;
    }

    /** Check collision */
    public void collision() {
        if (collision) {
            CollisionEngine.check(this);
        }

    }

    public HealthModule healthModule() {
        return healthModule;
    }

    public void enableHealthModule(int health) {
        healthModule = new HealthModule(health);
    }

    public boolean getCollision() {
        return collision;
    }

    /** Enable/disable "camera" */
    public void setCamera(boolean b) {
        camera = b;
    }

    /** Update camera/display coordinates */
    protected void updateDisplayCoordinates() {
        if (camera) {
            Camera.setDisplayCoordinates(this);
        }
    }

    public void damage(int i) {healthModule().damage(i);}

    public boolean ping() {
        return true;
    }

    public void addForce(int x, int y) {
        physics.addForce(x, y);
    }

    public Physics2 physics() {
        return physics;
    }

    /** Applies forces to object */
    private void updatePhysics() {
        physics().update(this);
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
            if (healthModule != null) {
                healthModule.paint(g);
            }
            if (debug) {
                g.setColor(Color.black);
                g.drawRect(getDisplayCoordinate().x, getDisplayCoordinate().y, get().width, get().height);
                g.drawString(debugString, getDisplayCoordinate().x - 125, getDisplayCoordinate().y -100);
            }
            if (isSelected()) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(20));
                g2d.setColor(Color.red);
                g2d.drawString("SELECTED", getDisplayCoordinate().x, getDisplayCoordinate().y - 50);
                g2d.setColor(Color.green);
                g2d.drawRect(getDisplayCoordinate().x, getDisplayCoordinate().y, get().width, get().height);
            }
        }
    }
}
