package data;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import adventuregame.Position;
import gamelogic.TriggerEvent;
import objects.Bigmush;
import objects.Coinman;
import objects.Enemy;
import objects.GameObject;
import objects.MushroomBlock;
import objects.NewAngryShroom;
import objects.Platform;
import objects.Spawner;

public class RandomLevel {

    long seed;
    LevelData level;

    Random random;

    int minLevel = 0, maxLevel = 5;
    int length = LENGH_MEDIUM;
    String type = TYPE_MISC;
    
    //generation
    int startElevation = 500;
    int minX = 0, maxX = 0, levelWidth = 0;
    int standardPlatformWidth = 1200, platformWidthVariance = 600;
    int standardPlatformHeight = 100, platformHeightVariance = 0;
    int standardPlatformDistance = 550, platformDistanceVariance = 400;
    int platformElevationVariance = 225;
    int standardDecorationCount = 4, decorationCountVariance = 3;
    int arenaSpawnerHeightOffset = 500;

    //spawner parameters
    TriggerEvent[] spawnerTriggers;
    int spawnerMaxAlive, spawnerCooldown, amountToSpawn, spawnerPlayerDistance;
    Dimension spawnerRange;

    ArrayList<GameObject> objectList;
    Point nextPos;
    Rectangle platform;
    int currentIndex;
    
    boolean generated = false;

    /** Generate a random level. */
    public RandomLevel(long seed, String name) {
        this.seed = seed;
        level = new LevelData(name, false, null);
        start();
    }

    private void start() {
        setDefaultSpawnerSettings();
    }

    /** Get the {@link LevelData} of the random level. */
    public LevelData create() {
        if (!generated) {
            generate();
        }
        
        return level;
    }

    /** Generate the objects in the level using parameters in this {@link RandomLevel} class. */
    void generate() {

        //init
        generated = true;
        random = new Random(seed);
        objectList = new ArrayList<GameObject>();
        nextPos = new Point(0, startElevation);

        for (int i = 0; i < length; i++) {
            currentIndex = i;
            next();
        }

        //finalize
        finalize(objectList);
    }

    /** convert to an ObjectData list and put into levelData. */
    void finalize(ArrayList<GameObject> l) {
        level.objectDataList(ObjectData.createDataList(l));
    }


    /* --- Level resources --- */


    /* --- Static resources --- */

    /** Type of level. */
    public static final String TYPE_MUSHROOM = "mushroom", TYPE_GOLD = "gold", TYPE_MISC = "misc";

    /** Length of level. */
    public static final int LENGTH_SHORT = 5, LENGH_MEDIUM = 15, LENGH_LONG = 30, LENGTH_VERYLONG = 50;

    /** Type of decoration */
    static String DECORATION_ENEMY = "enemy", DECORATION_TREASURE = "treasure", DECORATION_HEALTH = "health", DECORATION_BOIN = "boin", DECORATION_SPAWNER = "spawner";
    static String[] DECORATION_TYPES = new String[] {
        DECORATION_ENEMY,
        DECORATION_TREASURE,
        DECORATION_HEALTH,
        DECORATION_BOIN,
    };

    /** Mushroom enemies */
    static Class<?>[] MUSHROOM_ENEMIES = new Class<?>[] {
        Bigmush.class,
        NewAngryShroom.class,
    };

    /** Misc enemies */
    static Class<?>[] MISC_ENEMIES = new Class<?>[] {
        Coinman.class,
        Bigmush.class,
        NewAngryShroom.class,
    };

    /** Decoration size */
    class Size {
        int standard, variance;
        public Size(int standard, int variance) {
            this.standard = standard;
            this.variance = variance;
        }
        public Dimension get() {
            return new Dimension(variate(standard, variance), variate(standard, variance));
        }
    }

    /* ---  Misc methods --- */
    String type() {
        return type;
    }

    /* --- Generation methods --- */

    void next() {
        choosePlatform();
    }

    void choosePlatform() {
        double r = random.nextDouble();
        if (r < 0.25) {
            spawnerPlatform();
        }
        else {
            miscPlatform();
        }
    }

    void addObject(GameObject o) {
        objectList.add(o);
    }

    void setDefaultSpawnerSettings() {
        spawnerMaxAlive = 3;
        spawnerCooldown = 300;
        amountToSpawn = 9;
        spawnerRange = new Dimension(325, 325);
        spawnerPlayerDistance = 450;

        TriggerEvent e1 = Spawner.playerProximity(spawnerPlayerDistance);
        spawnerTriggers = new TriggerEvent[] {
            e1,
        };
    }

    void decorateRandom(int amount) {
        for (int i = 0; i < amount; i++) {
            placeDecoration(getRandomDecorationType());
        }
    }

    void decoratePlatform(int amount, String type) {
        for (int i = 0; i < amount; i++) {
            placeDecoration(type);
        }
    }

    void placeDecoration(String decorationType) {
        if (decorationType.equals(DECORATION_ENEMY)) {
            placeEnemy(getEnemyClass(type()));
        }
        else if (decorationType.equals(DECORATION_HEALTH)) {
            placeHealth();
        }
        else if (decorationType.equals(DECORATION_SPAWNER)) {
            placeSpawner(getEnemyClass(type()));
        }
        else {
            placeBlock(smallRandomSize(), randomColor());
        }
    }

    Color randomColor() {
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    Dimension smallRandomSize() {
        return new Size(150, 15).get();
    }

    Class<?> getEnemyClass(String type) {
        if (type.equals(TYPE_MUSHROOM)) {
            return MUSHROOM_ENEMIES[random.nextInt(MUSHROOM_ENEMIES.length)];
        }
        else {
            return MISC_ENEMIES[random.nextInt(MUSHROOM_ENEMIES.length)];
        }
    }

    void placeEnemy(Class<?> c) {
        try {
            Enemy e = (Enemy) c.newInstance();
            e.level(getRandomLevel());
            e.get().setLocation(getPlacement(e.get()));
            addObject(e);
        }
        catch (Exception e) {e.printStackTrace();}
    }

    /** Return a random (experience) level between min and max. */
    int getRandomLevel() {
        int r = random.nextInt(maxLevel + minLevel) - minLevel;
        return r;
    }

    void placeBlock(Dimension size, Color color) {
        GameObject block = new GameObject();
        block.get().setSize(size);
        block.get().setLocation(getPlacement(block.get()));
        block.setColor(randomColor());
        
        addObject(block);
    }

    Spawner getSpawner(Class<?> sample) {
        determineSpawnerSettings(sample);
        Spawner spawner = new Spawner(amountToSpawn, spawnerMaxAlive, spawnerCooldown, sample, spawnerTriggers);
        spawner.level = getRandomLevel();
        return spawner;
    }

    /** Choose settings based on enemy type. */
    void determineSpawnerSettings(Class<?> c) {
        if (c.equals(Bigmush.class)) {
            spawnerMaxAlive = 1;
            amountToSpawn = 1;
        }
        else if (c.equals(Coinman.class)) {
            spawnerMaxAlive = 2;
            amountToSpawn = 3;

        }
        else if (c.equals(NewAngryShroom.class)) {
            spawnerMaxAlive = 2;
            amountToSpawn = 4;
        }
        else {
            setDefaultSpawnerSettings();
        }
    }
    
    void placeSpawner(Class<?> sample) {
        Spawner spawner = getSpawner(sample);
        spawner.get().setLocation(getPlacement(spawner.get()));

        addObject(spawner);
    }

    Spawner getSpawner(GameObject specimen) {
        Spawner spawner = getSpawner(specimen.getClass());
        return spawner;
    }

    void placeTreasure() {
    }

    void placeHealth() {
        placeBlock(smallRandomSize(), randomColor());
    }

    String getRandomDecorationType() {
        return DECORATION_TYPES[random.nextInt(DECORATION_TYPES.length)];
    }

    /** Get a randomized spot on current platform to place object. */
    Point getPlacement(Rectangle r) {
        //give location
        Point p = new Point(platformSpot(), placeOnTop(r));
        r.setLocation(p);

        //validate placement
        validatePlacement(r);

        return r.getLocation();
    }

    void validatePlacement(Rectangle r) {
        for (GameObject o : objectList) {
            if (o.get().intersects(r)) {
                getPlacement(r);
            }
        }
    }

    /** Place on top of current platform. */
    int placeOnTop(Rectangle r) {
        return platform.y - r.height;
    }

    /** Get a random x-pos on top of the platform. */
    int platformSpot() {
        return variate(platform.x + (platform.width / 2), platform.width / 2);
    }

    /** Platform with random decorations. */
    void miscPlatform() {
        newPlatform();
        decorateRandom(variate(standardDecorationCount, decorationCountVariance));
    }

    /** Platform with a spawner that spawns a certain amounts of enemies. Kind of like some kind of arena you know, haha hell yeah */
    void spawnerPlatform() {
        //create a kind of large platform for the "arena"
        newPlatform(new Dimension(800, 200));

        //decorate it with a spawner in the middle, in the air
        Spawner spawner = getSpawner(getEnemyClass(type()));
        spawner.get().setLocation(Position.placeBetweenX(platform.x, (int)platform.getMaxX(), spawner.get()).x, platform.y - arenaSpawnerHeightOffset);
        addObject(spawner);
    }

    /** Basic platform template with a random size. */
    void newPlatform() {
        newPlatform(platformSize());
    }

    /** Basic platform with a custom size. */
    void newPlatform(Dimension size) {
        Platform p = getPlatformType();

        p.get().setLocation(nextPos);
        p.get().setSize(size);
        p.setColor(platformColor());

        objectList.add(p);

        //measure level
        measure();

        //save position of this platform
        setPlatformPos(p);

        //set position for next platform
        setNextPos();
    }

    Platform getPlatformType() {
        if (type().equals(TYPE_MUSHROOM)) {
            return new MushroomBlock();
        }
        else if (type().equals(TYPE_MISC)) {
            return new Platform();
        }
        else {
            return new Platform();
        }
    }

    void setPlatformPos(Platform p) {
        platform = p.get();
    }

    int platformDistance() {
        return variate(standardPlatformDistance, platformDistanceVariance);
    }

    Color platformColor() {
        if (type().equals(TYPE_MUSHROOM)) {
            return Color.blue;
        }
        else if (type().equals(TYPE_GOLD)) {
            return Color.yellow;
        }
        else if (type().equals(TYPE_MISC)) {
            return Color.orange;
        }
        else {
            return Color.white;
        }
    }

    Dimension platformSize() {
        return new Dimension(platformWidth(), platformHeight());
    }

    int platformWidth() {
        return variate(standardPlatformWidth, platformWidthVariance);
    }

    int platformHeight() {
        return variate(standardPlatformHeight, platformHeightVariance);
    }

    int variate(int subject, int variance) {

        variance = random.nextInt(variance + 1);

        if (random.nextBoolean()) {variance = -variance;}

        if (subject + variance > 0) {
            subject += variance;
        }
        else {
            subject = 0;
        }

        return subject;
    }

    /** Get the size and position of the current platform. */
    Rectangle platform() {
        return platform;
    }
    
    void measure() {
        minX = 0;
        maxX = 0;
        for (GameObject object : objectList) {
            measureObject(object);
        }

        levelWidth = maxX - minX;
    }

    void setNextPos() {
        nextPos.x = maxX + platformDistance();
        nextPos.y = variate(nextPos.y, platformElevationVariance);
    }

    void measureObject(GameObject object) {

        int objectMinX = (int)object.get().getMinX(),
            objectMaxX = (int)object.get().getMaxX();

        if (objectMaxX > maxX) {
            maxX = objectMaxX;
        }
        
        if (objectMinX <= minX) {
            minX = objectMinX;
        }
    }

}