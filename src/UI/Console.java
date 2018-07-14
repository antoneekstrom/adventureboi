package UI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import gamelogic.NewObjectStorage;
import gamelogic.ObjectInspector;
import objects.NewObject;

public class Console {

    private static String input;
    private static Object[] parameters;
    private static String[] rawParameters;
    private static Method method;
    private static String methodName;
    private static Object object;
    private static String className;
    private static Class<?>[] parameterTypes;

    private static boolean selectedObject = false;
    
    private static int parameterStart = 1;
    private static String defaultClassName = Console.class.getName();

    private static String regex = " ";
    private static String objectParseRegex = ".";
    private static String selected = "selected";
    private static String gameObjectRegex = "obj:";

    //'find string' values
    private static String quote = String.valueOf('"');
    private static int amountOfSpaces;
    private static String extractedString;
    private static int indexOfString;

    private static int HISTORY_MAX = 7;
    private static ArrayList<String> commandHistory = new ArrayList<String>();
    public static String[] getCommandHistory() {
        String[] arr = new String[commandHistory.size()];
        commandHistory.toArray(arr);
        return arr;
    }
    private static void addToHistory(String command) {
        commandHistory.add(0, command);
        if (commandHistory.size() + 1 > HISTORY_MAX) {commandHistory.remove(HISTORY_MAX - 1);}
        CreativeUI.history.refreshList(getCommandHistory());
    }

    private static HashMap<String, Class<?>> classes = new HashMap<String, Class<?>>() {
        private static final long serialVersionUID = 1L;
        {
            put("default", findClass(defaultClassName));
        }
    };

    //IO
    private static ArrayList<String> log = new ArrayList<String>();

    public static void enter(String command) {
        input = command;

        //parse input into parameters and method
        boolean validInput = parse();

        //if parsing was successful
        if (validInput) {
            //determine which method to use
            determineMethod();
            
            //execute the method
            executeMethod();

            //add to history
            addToHistory(command);
        }
    }

    private static boolean parse() {
        boolean successful = true;

        //split command into parameters
        rawParameters = input.split(regex);

        //short for setting variables to default
        Runnable r = () -> {
            object = null;
            methodName = rawParameters[0];
            className = defaultClassName;
            parameterStart = 1;
        };

        //extract potential string with spaces
        extractString(input);

        //extract potential object on which to invoke method
        if (rawParameters[0].contains(objectParseRegex)) {

            String param1 = rawParameters[0];
            String[] arr = param1.split(Pattern.quote(objectParseRegex));

            if (arr.length == 2) {
                object = determineObject(arr[0]);
                methodName = arr[1];
                parameterStart = 1;
            }
            else {
                Class<?> c = testForClass(arr);
                if (c == null) {
                    successful = false;
                }
                else {
                    object = c;
                    className = c.getName();
                    methodName = arr[arr.length - 1];
                    parameterStart = 1;
                }
            }
        }
        else {
            r.run();
        }

        //define parameter types
        if (rawParameters.length != 0) {
            defineParameterTypes();
        }
        else {
            parameterTypes = (Class<?>[]) null;
        }

        return successful;
    }

    private static Class<?> testForClass(String[] arr) {
        String comb = "";
        Class<?> c = null;

        if (arr.length > 2) {
            for (String s : arr) {
                comb += ((!s.equals(arr[0])) ? (".") : ("")) + s;
                boolean failed = false;
                try {
                    c = Class.forName(comb);
                }
                catch (Exception e) {failed = true;}
                if (!failed) {
                    break;
                }
            }
        }
        return c;
    }

    private static void extractString(String fullstring) {
        boolean stringExists = false;

        if (countMatches(fullstring, quote) == 2) {
            indexOfString = fullstring.indexOf(quote);
            extractedString = fullstring.substring(indexOfString + 1, fullstring.lastIndexOf(quote));
            stringExists = true;
        }

        if (extractedString != null) {
            amountOfSpaces = countMatches(extractedString, " ");
        }
        else {
            extractedString = "";
        }

        if (stringExists) {
            adjustArrays();
        }
    }

    private static void adjustArrays() {
        String[] temparr = rawParameters;
        rawParameters = new String[rawParameters.length - amountOfSpaces];

        String match = "";
        int matchSize = -1;
        for (int i = 0; i < temparr.length; i++) {
            String current = temparr[i].replace(quote, "");

            if (extractedString.contains(current)) {
                match += current + " ";
                matchSize++;
            }
            else {
                rawParameters[i] = temparr[i];
            }

            if (match.equals(extractedString + " ")) {
                rawParameters[i - matchSize] = extractedString;
            }
        }
    }

    private static void defineParameterTypes() {

        Class<?>[] carr = new Class[rawParameters.length - parameterStart];
        parameters = new Object[carr.length];
        
        for (int i = parameterStart; i < rawParameters.length; i++) {

            String param = rawParameters[i];
            Class<?> c = null;

            //if parameter only contains numbers it is an integer
            boolean isInteger = true;
            int integer = 0;
            try {
                integer = Integer.parseInt(param);
            }
            catch (NumberFormatException e) {isInteger = false;}
            if (isInteger) {
                c = Integer.TYPE;
                parameters[i - parameterStart] = integer;
            }
            else {
                c = String.class;
                parameters[i - parameterStart] = param;
            }

            //if parameter is boolean
            if (param.equals("true") || param.equals("false")) {
                c = Boolean.TYPE;
                parameters[i - parameterStart] = Boolean.parseBoolean(param);
            }

            carr[i - parameterStart] = c;
        }

        parameterTypes = carr;
    }

    private static Object determineObject(String name) {
        Object object = null;

        selectedObject = false;
        //if objectstring equals the syntax for choosing the currently selected object in the ObjectInspector.
        if (name.equals(selected)) {
            object = getSelectedObject();
            className = object.getClass().getName();
            selectedObject = true;
        }
        //search objectStorage for object with name
        else if (name.startsWith(gameObjectRegex)) {
            name = name.replace(gameObjectRegex, "");
            object = NewObjectStorage.findObjects(name).get(0);

            if (object.equals(null)) {
                object = NewObjectStorage.getObjectList().get(NewObjectStorage.getObjectList().size() - 1);
            }
            className = object.getClass().getName();
        }
        else {
            className = determineClass(name).getName();
        }

        return object;
    }

    private static Class<?> determineClass(String shortname) {
        Class<?> c = null;

        c = classes.get(shortname);
        if (c == null) {
            c = classes.get(defaultClassName);
        }

        return c;
    }

    private static NewObject getSelectedObject() {
        NewObject object = null;

        object = ObjectInspector.selectedObject();

        return object;
    }

    public static Class<?> findClass(String name) {
        Class<?> c = null;

        try {
            c = Class.forName(name);
        }
        catch (Exception e) {e.printStackTrace();}

        return c;
    }

    private static void determineMethod() {
        try {
            method = findClass(className).getMethod(methodName, parameterTypes);
        }
        catch (SecurityException e) {e.printStackTrace(); logError(e.getMessage());}
        catch (NullPointerException e) {e.printStackTrace(); logError("Class not found.");}
        catch (NoSuchMethodException e) {
            e.printStackTrace(); logError("There is no method in the class " + className + " with the name " + methodName);
            //if method being called on selected object resides in superclass.
            if (selectedObject) {
                className = findClass(className).getSuperclass().getName();
                selectedObject = false;
                determineMethod();
            }
        }
    }
    
    private static void executeMethod() {
        try {
            if (method.getReturnType().equals(null)) {
                method.invoke(object, parameters);
            }
            else {
                String s = String.valueOf(method.invoke(object, parameters));
                if (!s.equals("null")) {
                    log(s);
                }
            }
        }
        catch (IllegalArgumentException e) {e.printStackTrace();}
        catch (IllegalAccessException e) {e.printStackTrace();}
        catch (InvocationTargetException e) {e.printStackTrace();}
    }

    /* IO */
    public static String LOG_GREEN = ":g:", LOG_RED = ":r:";
    public static String colorRegex = ":";
    public static int colorLength = 3;

    public static void logError(String s) {logToConsole(LOG_RED + s);}
    public static void logSuccessful(String s) {logToConsole(LOG_GREEN + s);}
    public static void log(String s) {logToConsole(s);}

    private static void logToConsole(String s) {
        log.add(s);
        CreativeUI.refreshLog();
    }

    public static String[] getLog() {
        String[] arr = new String[log.size()];
        log.toArray(arr);
        return arr;
    }

    public static int countMatches(String line, String regex) {
        return line.length() - line.replace(regex, "").length();
    }

}