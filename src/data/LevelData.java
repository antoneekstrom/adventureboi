package data;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;

public class LevelData implements Serializable {

    private static final long serialVersionUID = 1L;
    
	private String name;
    private boolean creativeMode;
    private ArrayList<ObjectData> objectDataList;

    public String name() {return name;}
    public boolean creativeMode() {return creativeMode;}
    public ArrayList<ObjectData> objectDataList() {return objectDataList;}
    public void objectDataList(ArrayList<ObjectData> l) {objectDataList = l;}

    public LevelData(String name, boolean creativeMode, ArrayList<ObjectData> objectDataList) {
        this.name = name;
        this.creativeMode = creativeMode;
        this.objectDataList = objectDataList;
    }

    public static String[] findLevels() {
        
        File directory = new File("data/levels");
        File[] files;
		files = directory.listFiles(new FilenameFilter() {
            
            public boolean accept(File dir, String name) {
                return name.endsWith(".ser");
			}
        });
        String[] arr = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            arr[i] = files[i].getName().replace(".ser", "");
        }
        
        return arr;
    }
}
