package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Set;

public class Configuration {

    private static Properties properties = new Properties();
    private static File file = new File("data/config.properties");

    public static void loadProperties() {
        try {
            if (!file.exists()) {file.createNewFile();}
            properties.load(new FileInputStream(file));
        } catch (Exception e) {e.printStackTrace();}
    }

    public static String getProperty(String key) {
        loadProperties();
        String value = null;
        if (properties.containsKey(key)) {
            value = (String) properties.get(key);
        }
        return value;
    }

    public static Set<Object> getKeySet() {
        return properties.keySet();
    }

    public static void setProperty(String key, String value) {
        properties.put(key, value);
        saveProperties("Value of " + key + " changed to " + value + ".");
    }

    public static void saveProperties(String comment) {
        try {
            properties.store(new FileOutputStream(file), comment);
        }
        catch (Exception e) {e.printStackTrace();}
    }
}
