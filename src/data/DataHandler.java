package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataHandler {

    private static String directory = "data/levels/";
    public void directory(String s) {directory = s;}
    public String directory() {return directory;}

    private static String fileExtension = ".ser";
    public void fileExtension(String s) {fileExtension = s;}
    public String fileExtension() {return fileExtension;}

    public static void serialize(Object o, String file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(directory + file + fileExtension));
            out.writeObject(o);
            out.close();
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public static Object deserialize(String file) {
        Object o = null;
        File f = new File(directory + file +  fileExtension);

        if (!f.exists()) {
            try {
                f.createNewFile();
            }
            catch (Exception e) {e.printStackTrace();}
        }

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(directory + file + fileExtension));
            o = in.readObject();
            in.close();
        }
        catch (Exception e) {e.printStackTrace();}

        return o;
    }
}
