package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import UI.UIManager;
import adventuregame.GameEnvironment;
import adventuregame.GlobalData;
import adventuregame.Images;
import data.NumberFactory;
import data.PlayerData;
import data.Players;
import gamelogic.AbilityValues;
import gamelogic.Item;
import gamelogic.Camera;
import items.Currency;
import items.abilities.Ability;

public class Player extends GameObject implements ObjectMethods {
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
    public String currentAbility = "fireball";
    public String abilityDirection = "none";
    public boolean abilityCharging = false;
    public int abilityCooldown = AbilityValues.cooldown.get(currentAbility);
    public int chargePercentage = 0;

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
    private double ABILITY_COST = 0;
    private double ABILITY_DAMAGE_PERCENT;
    private double ABILITY_CHARGE_COST = 0;
    public double abilityFactor = 1;
    private String ABILITY_NAME = "none";

    //statistics
    double energy;
    double stamina;
    boolean energyRegen = true;
    boolean staminaRegen = true;

    //jumping
    /** Amount of times player can jump before touching the ground again. */
    private int MAX_JUMP_COUNT = 1;
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
    private int WALK_SPEED = 10;
    /** Total player movement speed */
    private int PLAYER_SPEED = WALK_SPEED;
    /** Speed that will be combined with PLAYER_SPEED when sprinting */
    private int SPRINT_SPEED = 10;

    public Player() {
    }

    public Player(PlayerData data) {
        playerData = data;
        initiatePlayerData(data);
        abilityInit();
        xpInit();
    }

    private void xpInit() {
        int level = playerData().experiencelevel();
        if (level == -1) {
            playerData().experiencelevel(0);
            level = playerData().experiencelevel();
            playerData().experiencegoal(NumberFactory.getXpGoal(level));
        }
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
                physics().resetFallAcceleration();
                jumpCount--;
                jumpTime = MAX_JUMP_TIME;
            }
        }
        if (jumping && jumpTime - 1 >= 0) {
            jumpTime--;
            get().y = get().y - jumpSpeed();
        }
    }

    public int jumpSpeed() {
        return JUMP_SPEED;
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

    public void abilityInit() {
        selectAbility();
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
        abilityInit();

        //animation/images
        playerimages = Images.getImageHashMap("assets/animated_sprites/aboi");
        setImage(playerimages.get("still"));
        chargeAnimation = new BufferedImage[11];
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
    }

    public void healthLogic() {
        if (healthModule().isDead()) {die();}
    }

    public void die() {
        respawn();
        healthModule().setHealth(healthModule().maxHealth());
    }

    @Override
    public void shrink() {
        super.shrink();
        physics().setGravity(false);
    }
    
    @Override
    public void shrinkDone() {
        super.shrinkDone();
        physics().setGravity(true);
    }

    public void respawn() {
        get().setLocation(spawnPoint);
    }

    public int boinCount() {
        int count = 0;

        for (Item item : playerData().inventory()) {
            if (item.hasTag(Item.CURRENCY)) {
                Currency c = (Currency) item;
                count += c.getValue();
            }
        }

        return count;
    }

    void pickupShake() {
        Camera.shake(10, 10, 30);
    }

    public void addItem(Item i) {
        pickupShake();
        playerData().inventory().add(i);
        if (i.useOnPickup()) {i.use(this);}
    }

    public void emptyInventory() {
        playerData().inventory().clear();
    }

    public void giveXp(int amount) {
        // add xp
        playerData().experiencepoints(playerData().experiencepoints() + amount);

        //show on HUD
        UIManager.getHUD().gainXp(amount, playerData().name());

        //check if goal is reached
        if (playerData().experiencepoints() >= playerData().experiencegoal()) {
            levelUp();
        }
    }

    public void levelUp() {
        playerData().increaselevel(1);
        playerData().experiencepoints(playerData().experiencepoints() - playerData().experiencegoal());
        playerData().experiencegoal(NumberFactory.getXpGoal(playerData().experiencelevel()));

        if (playerData().experiencepoints() > playerData().experiencegoal()) {levelUp();}
    }

    public boolean equip(Item i, String slot) {
        boolean b = false;
        Item l = null;
        boolean sameItem = false;
        //check that item is in inventory
        for (Iterator<Item> iterator = playerData.inventory().iterator(); iterator.hasNext();) {
            l = iterator.next();
            if (l.equals(i) && l.equippable() && !l.hasTag(Item.EQUIPPED)) {
                l.addTag(Item.EQUIPPED);
                b = true;
                break;
            }
            else if (l.equals(i) && i.equippable()) {
                sameItem = true;
            }
        }
        if (sameItem) {
            i.equip(false);
            l.equip(false);
            l = null;
        }

        //determine slot
        switch (slot) {
            case PlayerData.SLOT1:
            playerData.itemslot1().equip(false);
            playerData.itemslot1(l);
            break;

            case PlayerData.SLOT2:
            playerData.itemslot2().equip(false);
            playerData.itemslot2(l);
            break;

            case PlayerData.SLOT3:
            playerData.itemslot3().equip(false);
            playerData.itemslot3(l);
            break;

            case PlayerData.ABILITY:
            if (playerData.abilityslot() != null) {
                playerData.abilityslot().equip(false);
            }
            playerData.abilityslot(l);
            break;
        }
        selectAbility();

        return b;
    }

    public void selectAbility() {
        boolean allowed = false;
        Ability item = null;
        if (playerData().abilityslot() != null) {
            item = (Ability) playerData.abilityslot();
            if (playerData.abilityslot().hasTag(Item.ABILITY) && Ability.hasAllValues(item)) {allowed = true;}
        }
        else {ABILITY_NAME = "none";}
        if (allowed) {
            ABILITY_NAME = item.name();
            ABILITY_DAMAGE_PERCENT = item.PERCENT_DAMAGE;
            ABILITY_COOLDOWN = (int) item.COOLDOWN;
            ABILITY_COST = item.COST;
            ABILITY_CHARGE_COST = item.CHARGECOST;
            ABILITY_FACTOR_MAX = item.FACTORMAX;
            ABILITY_FACTOR_INCREASE = item.FACTORINCREASE;
        }
    }

    public void useAbility(String d, boolean b) {
        abilityDirection = d;
        abilityCharging = b;
    }

    public boolean hasAbility() {
        if (playerData().abilityslot() != null) {
            return true;
        }
        else {
            return false;
        }
    }

    public void chargeAbility() {
        Ability a = (Ability) playerData().abilityslot();
        if (abilityCooldown == 0) {
            if (hasAbility()) {
                if (abilityFactor + ABILITY_FACTOR_INCREASE <= ABILITY_FACTOR_MAX && abilityCharging && useEnergy(ABILITY_CHARGE_COST)) {
                    abilityFactor += ABILITY_FACTOR_INCREASE;
                    energyRegen = false;
                }
                else if (abilityCharging && abilityFactor + ABILITY_FACTOR_INCREASE > ABILITY_FACTOR_MAX) {abilityFactor = ABILITY_FACTOR_MAX;}
                else if (!abilityCharging && abilityFactor != 1) {
                    releaseAbility();
                }
                if (a.autoFire && abilityCharging) {
                    releaseAbility();
                }
            }
        }
        else {
            abilityCooldown--;
        }
    }

    public void releaseAbility() {
        Ability a = (Ability) playerData().abilityslot();
        if (a != null && useEnergy(ABILITY_COST)) {a.use(this);}
        abilityFactor = 1;
        abilityCooldown = ABILITY_COOLDOWN;
        energyRegen = true;
    }

    /** Center camera on player */
    public void centerCamera() {
        if (lockCameraY) {
            Camera.centerCameraOn(new Point( (int) get().getCenterX(), CAMERA_Y ));
        }
        else {
            Camera.centerCameraOn(new Point( (int) get().getCenterX(), (int) get().getCenterY()));
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

    public void intersect(GameObject collision) {
        if (collisionSide().equals("top")) {
            jumpCount = MAX_JUMP_COUNT;
        }
    }

    @Override
    public void collide(GameObject collision) {
        if (collision.collideWithPlayers) {
            super.collide(collision);
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

    public double calculateDamage() {
        return ABILITY_DAMAGE_PERCENT * playerData().damage() * abilityFactor;
    }
}
