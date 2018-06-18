package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataHandler {

    private static String fileExtension = ".ser";
    public void fileExtension(String s) {fileExtension = s;}
    public String fileExtension() {return fileExtension;}

    public static void serialize(Object o, File file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(o);
            out.close();
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public static Object deserialize(File file) {
        Object o = null;

        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (Exception e) {e.printStackTrace();}
        }

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            o = in.readObject();
            in.close();
        }
        catch (Exception e) {e.printStackTrace();}

        return o;
    }
}
