package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import UI.Console;
import adventuregame.GameEnvironment;
import adventuregame.GlobalData;
import adventuregame.Images;
import data.PlayerData;
import data.Players;
import gamelogic.AbilityValues;
import gamelogic.Item;
import gamelogic.NewCamera;
import gamelogic.NewObjectStorage;
import items.abilities.FireballItem;

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
    private int fallOffset = -20;

    //data
    private int PLAYER_ID;
    private String displayName = "";
    public void displayName(String s) {displayName = s;}
    public String displayName() {return displayName;}

    public boolean showName = true;
    public Color nameColor = Color.white;

    //camera
    private int CAMERA_Y = (GlobalData.getScreenDim().height / 2) - 300;
    private boolean lockCameraY = false;

    //abilities
    final private HashMap<String, Runnable> abilities = new HashMap<String, Runnable>() {
        private static final long serialVersionUID = 1L;
	{
        put("fireball", () -> fireball());
    }};
    private String currentAbility = "fireball";
    private String abilityDirection = "none";
    private boolean abilityCharging = false;
    private int abilityCooldown = AbilityValues.cooldown.get(currentAbility);
    private int chargePercentage = 0;

    //charge animation
    private BufferedImage[] chargeAnimation;

    //playerdata
    private PlayerData playerData;
    public PlayerData playerData() {return playerData;}

    public void initiatePlayerData(PlayerData data) {
        playerData = data;
        setName(data.name());
        healthModule().setMaxHealth((int)data.maxHealth());
        healthModule().setHealth(healthModule().maxHealth());

        if (playerData.inventory() == null) {
            ArrayList<Item> l = new ArrayList<Item>();
            playerData.inventory(l);
        }
    }

    public PlayerData extractPlayerData() {
        playerData.maxHealth(healthModule().maxHealth());
        playerData.name(getName());
        return playerData;
    }
    /*--------------*/

    //Ability
    private double ABILITY_FACTOR_MAX = AbilityValues.factorMax.get(currentAbility);;
    private double ABILITY_FACTOR_INCREASE = AbilityValues.factorIncrease.get(currentAbility);
    private int ABILITY_COOLDOWN = AbilityValues.cooldown.get(currentAbility);
    private double ABILITY_COST = 10;
    private double ABILITY_CHARGE_COST = 0.5;
    private double abilityFactor = 1;

    //statistics
    double energy;
    double stamina;
    boolean energyRegen = true;
    boolean staminaRegen = true;

    //jumping
    /** Amount of times player can jump before touching the ground again. */
    private int MAX_JUMP_COUNT = 2;
    private int jumpCount = MAX_JUMP_COUNT;

    private boolean firstJumpPress = false;

    private int MAX_JUMP_TIME = 20;
    private int jumpTime = 0;

    /** Velocity of jump. This will be added to the strength of gravity when calculated. */
    private int JUMP_SPEED = 30;

    //positioning
    Point spawnPoint = new Point(0,0);

    //logic / physics
    /** Standard walking speed */
    private int WALK_SPEED = 10;
    /** Total player movement speed */
    private int PLAYER_SPEED = WALK_SPEED;
    /** Speed that will be combined with PLAYER_SPEED when sprinting */
    private int SPRINT_SPEED = 10;

    public NewPlayer() {
    }

    public NewPlayer(PlayerData data) {
        playerData = data;
        initiatePlayerData(data);
    }

    public int getMovementSpeed() {
        return WALK_SPEED;
    }

    public int getId() {
        return PLAYER_ID;
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
        if (!physics().hasGravity()) {
            setY(getY() + PLAYER_SPEED);
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
            get().y = get().y - JUMP_SPEED;
        }
    }

    public void initializeData() {
        PlayerData data = null;
        if (PLAYER_ID == 0) {
            data = Players.getPlayerData(GameEnvironment.player1Name());
        }
        else if (PLAYER_ID == 1) {
            data = Players.getPlayerData(GameEnvironment.player2Name());
        }
        initiatePlayerData(data);
    }

    public void statInit() {
        energy = playerData.maxenergy();
        stamina = playerData.maxstamina();
    }

    public boolean useEnergy(double e) {
        boolean b = false;
        if (energy - e >= 0) {
            energy -= e;
            b = true;
        }
        return b;
    }
    
    public void initialize() {
        super.initialize();
        get().setSize(150, 125);
        setName("player" + PLAYER_ID);
        enableHealthModule(100);
        healthModule().setMaxHealth(100);
        healthModule().setHealth(100);
        initializeData();
        showDebug(false);
        statInit();

        //animation/images
        playerimages = Images.getImageHashMap("assets/animated_sprites/aboi");
        setImage(playerimages.get("still"));
        chargeAnimation = new BufferedImage[Images.getFolderImages("assets/animated_sprites/boicharge").size()];
        ArrayList<BufferedImage> l = new ArrayList<BufferedImage>();
        for (int i = 0; i <= 10; i++) {
            l.add(i, Images.getImage("charge" + i));
        }
        BufferedImage[] arr = new BufferedImage[l.size()];
        arr = l.toArray(arr);
        chargeAnimation = arr;
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
        chargeAbility();
        if (PLAYER_ID == 1) {centerCamera();}
        healthLogic();
        statLogic();
        debug();
    }

    public void statLogic() {
        energyRegen();
    }

    public double energy() {return energy;}

    private void energyRegen() {
        if (energyRegen) {
            if (energy + playerData.energyregen() <= playerData.maxenergy()) {
                energy += playerData.energyregen();
            }
            else {
                energy = playerData.maxenergy();
            }
        }
    }

    public void debug() {
        setDebugString("y:" + (float)physics().yVelocity());
    }

    public void healthLogic() {
        if (healthModule().isDead()) {die();}
    }

    public void die() {
        respawn();
        healthModule().setHealth(healthModule().maxHealth());
    }

    public void respawn() {
        get().setLocation(spawnPoint);
    }

    public void addItem(String name) {
        playerData().inventory().add(new FireballItem());
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
        if (abilityCooldown == 0) {
            if (abilityFactor + ABILITY_FACTOR_INCREASE <= ABILITY_FACTOR_MAX && abilityCharging && useEnergy(ABILITY_CHARGE_COST)) {
                abilityFactor += ABILITY_FACTOR_INCREASE;
                energyRegen = false;
            }
            else if (abilityCharging && abilityFactor + ABILITY_FACTOR_INCREASE > ABILITY_FACTOR_MAX) {abilityFactor = ABILITY_FACTOR_MAX;}
            else if (!abilityCharging && abilityFactor != 1) {
                releaseAbility();
            }
        }
        else {
            abilityCooldown--;
        }
    }

    public void releaseAbility() {
        abilities.get(currentAbility).run();
        abilityFactor = 1;
        abilityCooldown = ABILITY_COOLDOWN;
        energyRegen = true;
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
            fallOffset = 10;
        }
        else {fallOffset = 0;}

        //standing still
        if (!movingRight && !movingLeft && doesIntersect()) {
            setImage(playerimages.get("still"));
            animationCounter = 9;
        }
        animateStatus();
        if (abilityCharging) {
            animateCharge();
        }
    }

    private void animateCharge() {
        chargePercentage = (int) Math.round(((abilityFactor - 1) / (ABILITY_FACTOR_MAX - 1)) * 10);
        statusImage = chargeAnimation[chargePercentage];
    }

    private void animateStatus() {
        if (sprinting) {
            statusImage = Images.getImage("stamina_boi.png");
        }
        else {
            statusImage = null;
        }
    }

    public void intersect(NewObject collision) {
        if (collisionSide().equals("top")) {
            jumpCount = MAX_JUMP_COUNT;
        }
    }

    public void destruct() {
        get().setLocation(spawnPoint);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(statusImage, getDisplayCoordinate().x, getDisplayCoordinate().y + fallOffset, getWidth(), getHeight(), null);
        if (showName) {
            setColor(nameColor);
            g.drawString(getName(), getDisplayCoordinate().x, getDisplayCoordinate().y - 50);
        }
    }

    public void fireball() {
        Fireball f = new Fireball(abilityDirection);
        double d = playerData().damage();
        d *= abilityFactor;
        f.damage = (int) d;
        if (chargePercentage == 10) {
            f.charged();
        }
        f.get().setSize( (int) (f.get().getWidth() * abilityFactor), (int) (f.get().getHeight() * abilityFactor));
        if (abilityDirection.equals("left")) {
            f.get().setLocation((int) get().getCenterX() - (int) getWidth() / 2 - f.getWidth(), (int) get().getCenterY() - (int) f.get().getHeight() + 30);
        }
        else if (abilityDirection.equals("right")) {
            f.get().setLocation((int) get().getCenterX() + (int) getWidth() / 2, (int) get().getCenterY() - (int) (f.get().getHeight()) + 30);
        }
        NewObjectStorage.add(f);
    }
}
