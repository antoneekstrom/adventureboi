package objects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import adventuregame.GlobalData;
import adventuregame.Images;
import gamelogic.AbilityValues;
import gamelogic.NewCamera;

public class NewPlayer extends NewObject implements ObjectMethods {

    //states
    private boolean sitting = false;
    private boolean movingRight = false;
    private boolean movingLeft = false;
    private boolean jumping = false;
    private boolean sprinting = false;
    
    //animation
    private HashMap <String, BufferedImage> playerimages;
    private int animationCounter = 0;
    private int ANIMATION_COUNTER_GOAL = 10;
    private BufferedImage statusImage;

    //data
    private int PLAYER_ID;
    private String debugString = "";
    
    //camera
    private int CAMERA_Y = (GlobalData.getScreenDim().height / 2) - 300;
    private boolean lockCameraY = false;

    //abilities
    private HashMap<String, Runnable> abilities = new HashMap<String, Runnable>() {
        private static final long serialVersionUID = 1L;
	{
        put("fireball", () -> fireball());
    }};
    private String currentAbility = "fireball";
    private String abilityDirection = "none";
    private boolean abilityCharging = false;

    //Ability
    private double ABILITY_FACTOR_MAX = 3;
    private double ABILITY_FACTOR_INCREASE = 0.2;
    private double AbilityFactor = 1;

    //jumping
    /** Amount of times player can jump before touching the ground again. */
    private int MAX_JUMP_COUNT = 2;
    private int jumpCount = MAX_JUMP_COUNT;

    private boolean firstJumpPress = false;

    private int MAX_JUMP_TIME = 20;
    private int jumpTime = 0;

    /** Velocity of jump. This will be added to the strength of gravity when calculated. */
    private int JUMP_SPEED = 20;

    //positioning
    Point spawnPoint = new Point(0,0);

    //logic / physics
    /** Standard walking speed */
    private int WALK_SPEED = 15;
    /** Total player movement speed */
    private int PLAYER_SPEED = WALK_SPEED;
    /** Speed that will be combined with PLAYER_SPEED when sprinting */
    private int SPRINT_SPEED = 10;

    public NewPlayer(int id) {
        PLAYER_ID = id;
    }

    public int getMovementSpeed() {
        return WALK_SPEED;
    }

    public int getId() {
        return PLAYER_ID;
    }

    public void setDebugString(String s) {
        debugString = s;
    }

    public void sit(boolean b) {
        sitting = b;
    }

    public void moveLeft(boolean b) {
        movingLeft = b;
    }

    public void MoveRight(boolean b) {
        movingRight = b;
    }

    public void moveDown() {
        if (!getForce().hasGravity()) {
            setY(getY() + 20);
        }
    }

    public void sprint(boolean b) {
        sprinting = b;
    }

    /** Tell player to jump (Used for input) */
    public void doJump(boolean b) {
        if (!jumping) {
            firstJumpPress = true;
            jumping = b;
        }
        else {
            jumping = b;
        }
    }

    /** Execute the jump. state: firstpress/holding/null  */
    private void jump() {
        if (firstJumpPress) {
            firstJumpPress = false;
            if (jumpCount - 1 >= 0) {
                jumpCount--;
                jumpTime = MAX_JUMP_TIME;
            }
        }
        if (jumping && jumpTime - 1 >= 0) {
            jumpTime--;
            get().y = get().y - JUMP_SPEED - (int) getForce().getGravityStrength();
        }
    }
    
    public void initialize() {
        super.initialize();
        get().setSize(150, 125);

        //animation/images
        playerimages = Images.getImageHashMap("assets/animated_sprites/aboi");
        setImage(playerimages.get("still"));
    }

    public void ai() {
        super.ai();
    }

    /** Player movement. */
    private void movement() {
        if (sprinting) {PLAYER_SPEED = WALK_SPEED + SPRINT_SPEED;}
        else {
            PLAYER_SPEED = WALK_SPEED;
        }
        if (movingRight) {
            setX(getX() + PLAYER_SPEED);
        }
        if (movingLeft) {
            setX(getX() - PLAYER_SPEED);
        }
        if (jumping) {
            jump();
        }
        if (sitting) {
            setImage(playerimages.get("falling"));
            moveDown();
        }
    }

    public void logic() {
        movement();
        abilities();
        if (PLAYER_ID == 1) {centerCamera();}
    }

    public void abilities() {
        if (abilityCharging) {chargeAbility();}
    }

    public void selectAbility(String s) {
        currentAbility = s;
        ABILITY_FACTOR_MAX = AbilityValues.factorMax.get(s);
        ABILITY_FACTOR_INCREASE = AbilityValues.factorIncrease.get(s);
    }

    public void useAbility(String d, boolean b) {
        abilityDirection = d;
        abilityCharging = b;
    }

    public void chargeAbility() {
        if (AbilityFactor + ABILITY_FACTOR_INCREASE <= ABILITY_FACTOR_MAX) {
            AbilityFactor += ABILITY_FACTOR_INCREASE;
        }
        else {AbilityFactor = ABILITY_FACTOR_MAX;}
    }

    public void releaseAbility() {
        abilities.get(currentAbility);
        AbilityFactor = 1;
    }

    /** Center camera on player */
    public void centerCamera() {
        if (lockCameraY) {
            NewCamera.centerCameraOn(new Point( (int) get().getCenterX(), CAMERA_Y ));
        }
        else {
            NewCamera.centerCameraOn(new Point( (int) get().getCenterX(), (int) get().getCenterY()));
        }
    }
    
    public void animate() {
        //advance counter
        animationCounter++;
        if (animationCounter > ANIMATION_COUNTER_GOAL) {animationCounter = 0;}
        
        //movement
        if (movingRight || movingLeft) {
            if (animationCounter == ANIMATION_COUNTER_GOAL) {
                if (movingRight) {setImage(playerimages.get("right"));}
                if (movingLeft) {setImage(playerimages.get("left"));}
            }
            if (animationCounter == ANIMATION_COUNTER_GOAL / 2) {
                if (movingRight) {setImage(playerimages.get("left"));}
                if (movingLeft) {setImage(playerimages.get("right"));}
            }
        }
        
        //jumping
        if (!doesIntersect()) {
            setImage(playerimages.get("falling"));
        }

        //standing still
        if (!movingRight && !movingLeft && doesIntersect()) {
            setImage(playerimages.get("still"));
            animationCounter = 9;
        }

        animateStatus();
    }

    public void animateStatus() {
        if (sprinting) {
            statusImage = Images.getImage("stamina_boi.png");
        }
        else {
            statusImage = null;
        }
    }

    public void intersect() {
        jumpCount = MAX_JUMP_COUNT;
    }

    public void update() {
        super.update();
    }

    public void destruct() {
        get().setLocation(spawnPoint);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(statusImage, getDisplayCoordinate().x, getDisplayCoordinate().y, getWidth(), getHeight(), null);
        g.drawString(String.valueOf("charge:" + AbilityFactor), getDisplayCoordinate().x, getDisplayCoordinate().y - 150);
    }

    public void fireball() {
        Fireball f = new Fireball(abilityDirection);
        if (abilityDirection.equals("left")) {
            f.get().setLocation((int) get().getCenterX() - f.getWidth(), (int) get().getCenterY());
        }
        else if (abilityDirection.equals("right")) {
            f.get().setLocation((int) get().getCenterX() + f.getWidth(), (int) get().getCenterY());
        }
    }

}
