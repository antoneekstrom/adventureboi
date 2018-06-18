package data;

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
}
