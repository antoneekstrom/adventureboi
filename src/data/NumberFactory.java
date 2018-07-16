package data;

public class NumberFactory {

    private static double CONSTANT_FACTOR = 1.2;
    private static double ENEMY_XP_FACTOR = 0.95;

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

        d += Math.log10(level  + 1) * CONSTANT_FACTOR;

        return d;
    }

    /** Factor for scaling enemies with level. */
    public static double getEnemyScaling(int level) {
        double d = 1;

        d += Math.log10(level + 1) * CONSTANT_FACTOR;

        return d;
    }

}