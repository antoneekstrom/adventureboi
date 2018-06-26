package UI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Console {

    private static String input;
    private static String[] parameters;
    private static String regex = " ";
    private static Method method;
    private static String methodName;

    public static void enter(String command) {
        input = command;

        //parse input into parameters and method
        parse();
    }

    private static void parse() {
        parameters = input.split(regex);
        determineMethod();
    }

    private static void determineMethod() {
        methodName = parameters[0];
        try {
            method.invoke(null, (Object[]) null);
        }
        catch (IllegalArgumentException e) {e.printStackTrace();}
        catch (IllegalAccessException e) {e.printStackTrace();}
        catch (InvocationTargetException e) {e.printStackTrace();}
    }

}