package data;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;

import gamelogic.NewObjectStorage;

public class Players implements Serializable {
    private static final long serialVersionUID = 1L;

	private static ArrayList<PlayerData> playerdata = new ArrayList<PlayerData>();
    public static ArrayList<PlayerData> playerData() {return playerdata;}
    public static void playerData(ArrayList<PlayerData> datalist) {playerdata = datalist;}
    private static File directory = new File("data/players");

    /** Clears playerdatalist and reloads all files. */
    public static void loadPlayerData() {
        playerdata.clear();
        for (File f : searchDirectory()) {
            playerdata.add((PlayerData) DataHandler.deserialize(f));
        }
    }

    public static void extractAllPlayerData() {
        playerdata.clear();
        for (int i = 1; i <= NewObjectStorage.playerCount(); i++) {
            playerdata.add(NewObjectStorage.getPlayer(i).extractPlayerData());
        }
    }

    public static void serializePlayerData() {
        for (PlayerData d : playerdata) {
            DataHandler.serialize(d, new File("data/players/" + d.name() + ".ser"));
        }
    }

    public static void savePlayerData(PlayerData data) {
        playerdata.add(data);
    }

    public static String[] getPlayerNames() {
        ArrayList<String> l = new ArrayList<String>();

        for (PlayerData data : playerdata) {
            l.add(data.name());
        }
        String[] arr = new String[l.size()];
        return l.toArray(arr);
    }

    public static PlayerData getPlayerData(String playername) {
        PlayerData data = null;
        for (PlayerData d : playerdata) {
            if (d.name().equals(playername)) {data = d;}
        }
        return data;
    }

    public static void removePlayer(String name) {
        File f = getPlayerFile(name);
        f.delete();
    }

    public static File getPlayerFile(String playername) {
        File f = null;
        File[] arr = searchDirectory();
        for (File file : arr) {
            if (removeExtension(file.getName()).equals(playername)) {
                f = file;
            }
        }
        return f;
    }

    /** Check if profile with playername exists. */
    public static boolean exists(String playername) {
        for (String s : getPlayerNames()) {if (playername.equals(s)) {return true;}}
        return false;
    }

    private static String removeExtension(String getname) {
        int pos = getname.lastIndexOf(".");
        if (pos > 0) {
            getname = getname.substring(0, pos);
        }
        return getname;
    }

    private static File[] searchDirectory() {
        File[] files;
		files = directory.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.endsWith(".ser");
			}
        });
        return files;
    }

}
