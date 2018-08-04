package data;

import java.util.Random;

    /** This serves as a "manager" of the actual {@link RandomLevel}
     *  that is doing the generating. */
public class LevelGenerator {

    long seed;

    int minLevel, maxLevel;
    public int length;
    public String type;

    public LevelGenerator(long seed) {
        setSeed(seed);
    }

    public LevelGenerator() {
        setSeed(getRandomSeed());
    }

    /** Generate a random seed. */
    public static long getRandomSeed() {

        String s = "";
        Random r = new Random();

        for (int i = 0; i < 10; i++) {
            s += r.nextInt(9);
        }

        return Long.parseLong(s);
    }

    /** Set the seed to use for this generator. */
    public void setSeed(long seed) {
        this.seed = seed;
    }

    /** Create a new level. */
    public LevelData newLevel(String name) {
        return newLevel(seed, name);
    }
    
    /** Create a new level. */
    public LevelData newLevel(long seed, String name) {
        //new random level
        RandomLevel rl = new RandomLevel(seed, name);

        //set it up
        rl.type = type;

        //return it
        return rl.create();
    }

}