package data;

import java.util.Random;

public class NumberFactory {

    private static double CONSTANT_FACTOR = 1.7;
    private static double ENEMY_XP_FACTOR = 0.95;

    /** Get a random string of numbers and hope that it is unique, otherwise it will cause problems. */
    public static String randomString(int length) {
        Random r = new Random();
        String s = "";
        for (int i = 0; i < length; i++) {
            s += r.nextInt(10);
        }
        return s;
    }

    /** Return a factor representing the amount an objects xp drop should scale given it's experience level.  */
    public static double getXpScaling(int level) {
        double g;
        int CONSTANT_ONE = 100;
        double CONSTANT_THREE = 0.05;

        double factorOne = CONSTANT_FACTOR + (level * CONSTANT_THREE);

        g = (level + 1) * (CONSTANT_ONE * (level+1)) * (factorOne);
        g *= ENEMY_XP_FACTOR;


        return (int) g;
    }

    /** Returns a number representing the amount of xp the player needs to advance to the next level. */
    public static int getXpGoal(int level) {
        double g;
        int CONSTANT_ONE = 100;
        double CONSTANT_THREE = 0.05;

        double factorOne = CONSTANT_FACTOR + (level * CONSTANT_THREE);

        g = (level + 1) * (CONSTANT_ONE * (level+1)) * (factorOne);


        return (int) g;
    }

    /** Factor for scaling items with level.*/
    public static double getStatScaling(int level) {
        double d = 1;

        d += Math.log10(level + 1) * CONSTANT_FACTOR;

        return d;
    }

    /** Factor for scaling enemies with level. */
    public static double getEnemyScaling(int level) {
        double d = 1;

        d += Math.log10(level + 1) * CONSTANT_FACTOR;

        return d;
    }

    public static double round(double value) {
        int i = Math.round((float)value * 10);
        return ((double)i / (double)10);
    }

    /**
     *  @param percent from 1 to 100
     */
    public static double percentFromMinToMax(double min, double max, double percent) {
        return min + (int) ((1-((100 - percent) / 100)) * (max - min));
    }

}