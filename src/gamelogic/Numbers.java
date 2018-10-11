package gamelogic;

public class Numbers {

    public static double limitMin(double num, double min) {
        if (num < min) {
            num = min;
        }
        return num;
    }

    public static double limitMax(double num, double max) {
        if (num > max) {
            num = max;
        }
        return num;
    }

    public static double limitNum(double num, double min, double max) {
        limitMax(num, max);
        limitMin(num, min);
        return num;
    }

    public static boolean checkMinLimit(double num, double min) {
        return num == limitMin(num, min);
    }

    public static boolean checkMaxLimit(double num, double max) {
        return num == limitMax(num, max);
    }

}