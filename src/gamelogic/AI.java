package gamelogic;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TimerTask;

import adventuregame.GameEnvironment;
import adventuregame.Position;
import objects.GameObject;
import objects.Player;

public class AI implements Serializable {

    private static final long serialVersionUID = 1L;
	private GameObject object;
    private boolean enabled = true;

    public AI(GameObject object) {
        this.object = object;
        start();
    }

    void start() {
        home = object.get().getLocation();
        pathGoal = home;
    }

    //collision
    GameObject lastWall;
    private GameObject collisionObject;
    private GameObject lastGround;
    private Collision collision;
    private String direction = "left";

    //events
    ArrayList<String> events = new ArrayList<String>();

    public void addEvent(RandomEvent e, int interval) {
        GameEnvironment.getEventTimer(interval).addRandomEvent(e);
        events.add(e.getName());
    }

    public void stopEvents() {
        for (String event : events) {
            GameEnvironment.removeEvent(event);
        }
    }

    //values
    private int speed = 10;
    private double standardJumpFrequency = 0.05;
    private double jumpFreq = standardJumpFrequency;
    private double jumpForce = 250;
    private int JUMP_COOLDOWN = 500;
    public boolean canJump = true;

    //path to player
    private boolean ignorePlayer = false;
    private boolean pathToPlayer = true;
    private boolean playerWithinRange = false;
    public boolean jumpToPlayer = false;
    private double jumpFreqBonus = 0.07;
    private int pathToPlayerRange = 700; 
    private int jumpToPlayerDistance = 325;

    /* General pathing */
    private Countdowner homeCountdown;
    private boolean isCountingDownToReturn = false;
    private boolean followGoal = false;
    private Point home = new Point(0,0);
    private int returnToHomeDelay = 5000;
    int obstacleJumpHeight = 115;
    /** Position to navigate to if enabled. */
    private Point pathGoal = home;
    private int homeRange = 75;
    private int maxGapWidth = 125;
    public boolean ledgeJumpAvailable = true;
    private int ledgeJumpCooldown = 1500;
    private boolean canJumpGap = false;

    //direction switching
    private int setDirectionCooldown = 0;
    private int SET_DIRECTION_COOLDOWN = 35;
    public double spontaneousDirectionSwitchFrequency = 0.022;

    //edges
    private boolean closeToEdge = false;
    private int edgeDetectionRadius = 40;

    public void update(GameObject o) {
        if (isEnabled()) {
            object = o;
            //do things when colliding
            collisionLogic();
            //move object in current direction
            move();
            //method for debugging
            debug();
            //do things
            logic();
        }
    }
    
    public void collide(Collision c) {
        if (isEnabled()) {
            collisionObject = c.object();
            collision = c;
            //determine type of collision
            determineCollision();
            //change direction when colliding
            detectCollision();
            //potentially execute an action
            collisionAction();
        }
    }

    void collisionLogic() {
        for (Collision col : object.collisions()) {
            collide(col);
        }
    }

    private void collisionAction() {
        if (Math.random() < jumpFreq && (!closeToEdge || playerWithinRange)) {jump(jumpForce);}
        if (Math.random() < spontaneousDirectionSwitchFrequency && !closeToEdge) {switchDirection();}
        if (collision.isWall()) {
            jumpWall();
        }
    }
    
    private void debug() {
        
    }

    /** determine type of collision. */
    private void determineCollision() {
        if (object != null) {
            if (object.collisionSide().equals("top")) {
                lastGround = collisionObject;
            }
            if (object.collisionSide().equals("left") || object.collisionSide().equals("right")) {
                lastWall = collisionObject;
            }
        }
    }

    private void logic() {
        edgeDetection();
        updateCooldown();
        if (followGoal) {
            pathToGoal();
        }
        if (pathToPlayer && !ignorePlayer) {
            pathToPlayer();
        }
        if (closeToEdge) {
            defeatEdge();
        }
    }

    /** Choose a way to overcome this potentially incredibly dangerous obstacle. */
    void defeatEdge() {
        /** Next platform. */
        GameObject nextO = null;
        String side = sideOfObject(lastGround);

        /** Find platform to jump to if possible. */
        if (side.equals("left")) {
            nextO = object.objectToLeft(maxGapWidth);
        }
        else if (side.equals("right")) {
            nextO = object.objectToRight(maxGapWidth);
        }

        canJumpGap = false;
        if (nextO != null) {
            canJumpGap = canJumpGap(nextO);
        }
        int d2l = distanceToLedge();
        boolean closeEnough = true;

        /* Determine if a jump should take place, or perhaps a change of direction */
        if (nextO == null || !canJumpGap) { /* If nextO is null then there is no platform within range. If there is nothing within range and jump is not possible then turn around. */
            fallOfPrevention();
        }
        else if (canJumpGap && closeEnough) {
            jumpGap(nextO);
        }
    }

    public int ledgeDistanceOffset() {
        return (int) (object.get().width * 0.25);
    }

    boolean canJumpGap(GameObject destination) {
        boolean withinReach = false;
        Point ledgeCorner = null;

        String side = sideOfObject(lastGround);
        if (side.equals("right")) {
            ledgeCorner = new Point((int) destination.get().getMinX(), (int) destination.get().getMinY());
        }
        else if (side.equals("left")) {
            ledgeCorner = new Point((int) destination.get().getMaxX(), (int) destination.get().getMinY());
        }
        else {
            ledgeCorner = destination.getCenter();
        }

        boolean destinationBelow = destination.get().y >= lastGround.get().y;
        int gapDistance = Position.distanceX(ledgeCorner, object.getCenter());

        if (gapDistance <= obstacleJumpHeight && gapDistance <= maxGapWidth || (destinationBelow && gapDistance <= maxGapWidth)) {
            withinReach = true;
        }

        return closeToEdge && withinReach && ledgeJumpAvailable;
    }

    /** Attempt to jump off a ledge.
     *  @return Wether the jump was executed/successful
     */
    void jumpGap(GameObject destination) {

        setDirection(sideOfObject(lastGround));
        jump(jumpForce + ledgeJumpForceBoost());
        ledgeJumpSpeedBoost();

        gapJumpCooldown();
    }

    double ledgeJumpForceBoost() {
        return jumpForce * 0.2;
    }

    void ledgeJumpSpeedBoost() {
        int oldSpeed = speed;
        int newSpeed = (int) (speed *3);

        speed = newSpeed;
        new Countdowner(500, new TimerTask() {
            @Override
            public void run() {
                speed = oldSpeed;
            }
        });
    }

    void gapJumpCooldown() {
        if (ledgeJumpAvailable) {
            ledgeJumpAvailable = false;
            new Countdowner(ledgeJumpCooldown, new TimerTask() {
                @Override
                public void run() {
                    ledgeJumpAvailable = true;
                }
            });
        }
    }

    void jumpWall() {
        int height = Position.distanceY(object.get().getLocation(), collisionObject.get().getLocation());
        boolean jumpable = obstacleJumpHeight >= height;
        if (!playerWithinRange && jumpable) {
            jump(jumpForce);
        }
        else {
            switchDirection();
        }
    }

    void pathToGoal() {
        /* Set direction to current goal. */
        String direction = object.get().x < pathGoal.x ? "right" : "left";
        setDirection(direction);

        int distanceToHome = Position.distanceX(pathGoal, object.get().getLocation());
        /* Check if goal has been reached */
        if (distanceToHome < homeRange) {
            followGoal(false);
        }
    }

    public void ignorePlayer(int duration) {
        ignorePlayer = true;
        new Countdowner(duration, new TimerTask(){
            @Override
            public void run() {
                ignorePlayer = false;
                returnHomeDelay(returnToHomeDelay);
            }
        });
    }

    Countdowner homeCountdown() {return homeCountdown;}

    void returnHome() {
        setGoal(home);
        followGoal(true);
    }

    void returnHomeDelay(int delay) {
        if (!isCountingDownToReturn) {
            isCountingDownToReturn = true;
            homeCountdown = new Countdowner(delay, newReturnHomeTask());
        }
    }

    TimerTask newReturnHomeTask() {
        return new TimerTask(){
            @Override
            public void run() {
                isCountingDownToReturn = false;
                returnHome();
            }
        };
    }

    public boolean followGoal() {return followGoal;}
    public void followGoal(boolean b) {followGoal = b;}
    /** Try to navigate to a specific point. */
    public void setGoal(Point pos) {
        pathGoal = pos;
    }
    public void setHome(Point pos) {home = pos;}

    /** Try to reach the same elevation as the player by jumping while moving towards player. */
    private void pathToPlayer() {
        Player player = ObjectStorage.findNearestPlayer(object.get().getLocation());
        boolean prevPlayerWithinRange = playerWithinRange;

        /* Determine if player is within range */
        Point playerCenter = Position.CenterPos(player.get()), objectCenter = Position.CenterPos(object.get());
        int distance = Position.distance(playerCenter, objectCenter);
        playerWithinRange = distance <= pathToPlayerRange;

        /* If player is above this object and not too close, try to jump. */
        jumpToPlayer = (distance >= jumpToPlayerDistance) && playerWithinRange;
        /* Still jump if too close but there is a wall */
        boolean colWithWall = object.checkForCollisionSide("wall");
        boolean playerAbove = player.get().y < object.get().y;

        /* Change the jump freq according to conditions */
        if ( (playerAbove && jumpToPlayer && !closeToEdge) || (colWithWall && playerWithinRange) ) {
            jumpFreq = standardJumpFrequency + jumpFreqBonus;
        }
        else {
            jumpFreq = standardJumpFrequency;
        }
        
        /* if player is within range  */
        if (playerWithinRange) {
            /* Stop following current goal. */
            followGoal(false);

            /* Get direction of player */
            String playerDirection = "none";
            if (player.get().x < object.get().x) {
                playerDirection = "left";
            }
            else {
                playerDirection = "right";
            }
            /* Switch direction towards player */
            setDirection(playerDirection);
        }
        if (prevPlayerWithinRange && !playerWithinRange) {
            returnHomeDelay(returnToHomeDelay);
        }
    }

    private String sideOfObject(GameObject c) {
        String side = "none";
        if (c != null) {
            if (object.get().getMaxX() < c.get().getCenterX()) {
                side = "left";
            }
            else if (object.get().getMinX() > c.get().getCenterX()) {
                side = "right";
            }
        }
        return side;
    }

    /** Change direction. */
    private void fallOfPrevention() {
        if (closeToEdge && sideOfObject(lastGround).equals("left")) {
            setDirection("right");
        }
        else if (closeToEdge && sideOfObject(lastGround).equals("right")) {
            setDirection("left");
        }
    }
    
    /** check if close to edge
     *  @return Side of object that the edge is present.
     */
    private void edgeDetection() {
        if (lastGround != null) {
            if (object.get().getMinX() < lastGround.get().getMinX() + edgeDetectionRadius) {
                closeToEdge = true;
            }
            else if (object.get().getMaxX() > lastGround.get().getMaxX() - edgeDetectionRadius) {
                closeToEdge = true;
            }
            else {
                closeToEdge = false;
            }
        }
    }

    public int distanceToLedge() {
        String side = sideOfObject(lastGround);
        if (side.equals("right")) {
            return Position.distance((int)object.get().getMaxX(), (int)lastGround.get().getMaxX());
        }
        else if (side.equals("left")) {
            return Position.distance((int)object.get().getMinX(), (int)lastGround.get().getMinX());
        }
        else {
            return 0;
        }
    }

    private void updateCooldown() {
        if (setDirectionCooldown > 0) {
            setDirectionCooldown--;
        }
    }

    public void jump(double force) {
        if (object.onGround() && canJump) {
            object.physics().addForce(0, -force);
            canJump = false;
            new Countdowner(JUMP_COOLDOWN, new TimerTask(){
                @Override
                public void run() {
                    canJump = true;
                }
            });
        }
    }
    
    /** Determine if object should change direction. */
    private void detectCollision() {
        if (collision.isGround()) {
            if (newPosition().getMaxX() > collisionObject.get().getMaxX() || newPosition().getMinX() < collisionObject.get().getMinX()) {
                switchDirection();       
            }
        }
        else if (collision.isWall()) {
            switchDirection();
        }
    }

    private Rectangle newPosition() {
        if (direction.equals("right")) {
            return new Rectangle(object.get().x + speed, object.get().y, object.get().width, object.get().height);
        }
        else if (direction.equals("left")) {
            return new Rectangle(object.get().x - speed, object.get().y, object.get().width, object.get().height);
        }
        else {
            return object.get();
        }
    }
    
    /** Move object in current direction. */
    private void move() {
        if (direction.equals("left")) {
            object.get().setLocation(object.get().x - speed, object.get().y);
        }
        else if (direction.equals("right")) {
            object.get().setLocation(object.get().x + speed, object.get().y);
        }
    }

    public void switchDirection() {
        if (direction.equals("left")) {setDirection("right");}
        else if (direction.equals("right")) {setDirection("left");}
    }
    public void setDirection(String d) {
        if (setDirectionCooldown == 0) {
            direction = d;
            setDirectionCooldown = SET_DIRECTION_COOLDOWN;
        }
    }

    public boolean isEnabled() {return enabled;}
    public void setEnabled(boolean b) {enabled = b;}
    public void jumpFrequency(float f) {standardJumpFrequency = f; jumpFreq = f;}
    public double currentJumpFrequency() {return jumpFreq;}
    public boolean playerWithinRange() {return playerWithinRange;}
    public GameObject groundCollision() {return lastGround;}
    public GameObject wallCollision() {return lastWall;}
    public boolean closeToEdge() {return closeToEdge;}
    public int speed() {return speed;}
    public void jumpforce(double d) {jumpForce = d;}
    public void speed(int s) {speed = s;}
}
