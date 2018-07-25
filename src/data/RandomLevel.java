package data;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import objects.AngryShroom;
import objects.Bigmush;
import objects.Coinman;
import objects.Enemy;
import objects.GameObject;
import objects.Platform;

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
    int standardPlatformSize = 800, platformVariance = 450;
    int standardPlatformDistance = 550, platformDistanceVariance = 400;
    int platformHeightVariance = 175;
    int standardDecorationCount = 4, decorationCountVariance = 3;

    ArrayList<GameObject> objectList;
    Point nextPos;
    Rectangle platform;
    int currentIndex;
    
    boolean generated = false;

    /** Generate a random level. */
    public RandomLevel(long seed, String name) {
        this.seed = seed;
        level = new LevelData(name, false, null);
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
    static String TYPE_MUSHROOM = "mushroom", TYPE_GOLD = "gold", TYPE_MISC = "misc";

    /** Length of level. */
    public static int LENGTH_SHORT = 5, LENGH_MEDIUM = 15, LENGH_LONG = 30, LENGTH_VERYLONG = 50;

    /** Type of decoration */
    static String DECORATION_ENEMY = "enemy", DECORATION_TREASURE = "treasure", DECORATION_HEALTH = "health", DECORATION_BOIN = "boin";
    static String[] DECORATION_TYPES = new String[] {
        DECORATION_ENEMY,
        DECORATION_TREASURE,
        DECORATION_HEALTH,
        DECORATION_BOIN,
    };

    /** Mushroom enemies */
    static Class<?>[] MUSHROOM_ENEMIES = new Class<?>[] {
        Bigmush.class,
        AngryShroom.class,
    };

    /** Misc enemies */
    static Class<?>[] MISC_ENEMIES = new Class<?>[] {
        Coinman.class,
        Bigmush.class,
        AngryShroom.class,
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

    /* --- Generation methods --- */

    void next() {
        miscPlatform();
    }

    void addObject(GameObject o) {
        objectList.add(o);
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
            placeBlock(smallRandomSize(), randomColor());
        }
        else if (decorationType.equals(DECORATION_HEALTH)) {
            placeHealth();
        }
        else {
            placeBlock(smallRandomSize(), randomColor());
        }
    }

    Color randomColor() {
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    Dimension smallRandomSize() {
        return new Size(50, 15).get();
    }

    Class<?> randomEnemyClass(String type) {
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
            addObject(e);
        }
        catch (Exception e) {e.printStackTrace();}
    }

    /** Return a random (experience) level between min and max. */
    int getRandomLevel() {
        return random.nextInt(maxLevel + minLevel) - minLevel;
    }

    void placeBlock(Dimension size, Color color) {
        GameObject block = new GameObject();
        block.get().setSize(size);
        block.get().setLocation(getPlacement(block.get()));
        block.setColor(randomColor());
        
        addObject(block);
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
        platform();
        decorateRandom(variate(standardDecorationCount, decorationCountVariance));
    }

    /** Basic platform template. */
    void platform() {
        Platform p = new Platform();

        p.get().setLocation(nextPos);
        p.get().setSize(platformSize());
        p.setColor(platformColor());

        objectList.add(p);

        //measure level
        measure();

        //save position of this platform
        setPlatformPos(p);

        //set position for next platform
        setNextPos();
    }

    void setPlatformPos(Platform p) {
        platform = p.get();
    }

    int platformDistance() {
        return variate(standardPlatformDistance, platformDistanceVariance);
    }

    Color platformColor() {
        if (type.equals(TYPE_MUSHROOM)) {
            return Color.blue;
        }
        else if (type.equals(TYPE_GOLD)) {
            return Color.yellow;
        }
        else if (type.equals(TYPE_MISC)) {
            return Color.orange;
        }
        else {
            return Color.white;
        }
    }

    Dimension platformSize() {
        return new Dimension(variate(standardPlatformSize, platformVariance), 100);
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
        nextPos.y = variate(nextPos.y, platformHeightVariance);
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