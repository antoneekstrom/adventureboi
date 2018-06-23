package objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import adventuregame.Animator;
import adventuregame.HealthModule;
import data.ObjectData;
import gamelogic.NewAI;
import gamelogic.NewCamera;
import gamelogic.NewCollision;
import gamelogic.NewObjectStorage;
import gamelogic.Physics;

public class NewObject {

	//switches
    private boolean visible = true;
    /** Set to true if object should receive collision logic. Will still affect other collision enabled objects. */
    private boolean collision = true;
    /** Set to true if object should be pushable. */
    private boolean pushable = false;
    private boolean intersect = false;
    private boolean camera = true;
    private boolean debug = false;
    private boolean hasImage = false;
    private boolean passThrough = false;
    private boolean selected = false;

    public boolean cameraFocus = false;
    public void cameraFocus(boolean b) {
        for (NewObject o : NewObjectStorage.getObjectList()) {
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
    private NewAI ai;
    private Animator animator;
    private HealthModule healthModule;

    //physics
    private Physics physics = new Physics();

    //velocity
    private double velocityX = 0;
    private double velocityY = 0;
    private Point lastPos;


    //collision
    private NewObject lastCollision;
    private String collisionSide = "none";

    //positioning
    private Rectangle r;
    private Point displayCoordinate;
    private int yLimit = 3000;

    public NewObject() {
        r = new Rectangle(100, 100);
        r.setLocation(0, 0);
        setText("");
        displayCoordinate = new Point(r.x, r.y);
        initialize();
    }

    public NewObject(Rectangle r) {
        this.r = r;
        setText("");
        displayCoordinate = new Point(r.x, r.y);
        initialize();
    }

    public boolean isPassThrough() {
        return passThrough;
    }

    public void setCollisonSide(String s) {collisionSide = s;}
    public String collisionSide() {return collisionSide;}

    public void setPassThrough(boolean b) {
        passThrough = b;
    }

    public String getText() {
        return text;
    }
    public ObjectData extractData() {
        return new ObjectData(this);
    }

    public Color getColor() {return color_fg;}

    public NewObject(ObjectData data) {
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
        physics = new Physics();
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
    public void passCollision(NewObject o) {
        lastCollision = o;
    }

    public NewObject getLastCollision() {
        return lastCollision;
    }

    public boolean isSelected() {return selected;}
    public void deselect() {selected = false;}
    public void select() {
        for (NewObject o : NewObjectStorage.getObjectList()) {
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
    public void intersect(NewObject collision) {
    }
    
    /** Is called immediately upon intersection. Call super. */
    public void collide(NewObject collision) {
        physics().collide();
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

    public void enableAnimator() {
        animator = new Animator(image);
    }

    public NewAI getAI() {
        return ai;
    }   

    public void createAI() {
        ai = new NewAI();
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
        calculatePosition(); /* Calculate forces and positioning */
        updateDisplayCoordinates(); /* Update position on screen */
        animate(); /* Do animation. */
        if (healthModule != null) {healthModule().update(this);}
        debug();
    }
    
    /** Update method for debugging. */
    private void debug() {
    }

    /** Update basic logic stuff for all objects */
    private void basicLogic() {
        if (get().y > yLimit) {
            destruct();
        }
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
            NewCollision.check(this);
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
    private void updateDisplayCoordinates() {
        if (camera) {
            NewCamera.setDisplayCoordinates(this);
        }
    }

    public boolean ping() {
        return true;
    }

    public boolean isPushable() {
        return pushable;
    }

    public void pushable(boolean b) {
        pushable = b;
    }

    public void addForce(double x, double y) {
        physics.addForce(x, y);
    }

    public Physics physics() {
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
            if (debug) {
                g.setColor(Color.black);
                g.drawRect(getDisplayCoordinate().x, getDisplayCoordinate().y, get().width, get().height);
                g.drawString(debugString, getDisplayCoordinate().x - 250, getDisplayCoordinate().y -100);
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
