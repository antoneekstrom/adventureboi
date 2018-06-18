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

    public static void loadPlayerData() {
        for (File f : searchDirectory("data/player")) {
            playerdata.add((PlayerData) DataHandler.deserialize(f));
        }
    }

    private static void extractAllPlayerData() {
        playerdata.clear();
        for (int i = 1; i <= NewObjectStorage.playerCount(); i++) {
            playerdata.add(NewObjectStorage.getPlayer(i).extractPlayerData());
        }
    }

    public static void savePlayerData() {
        extractAllPlayerData();
        for (PlayerData d : playerdata) {
            DataHandler.serialize(d, new File("data/players/" + d.name() + ".ser"));
        }
    }

    public static PlayerData getPlayerData(String playername) {
        PlayerData data = null;
        for (PlayerData d : playerdata) {
            if (d.name().equals(playername)) {data = d;}
        }
        return data;
    }

    private static File[] searchDirectory(String path) {
        File directory = new File("data/players");
        File[] files;
		files = directory.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.endsWith(".ser");
			}
        });
        return files;
    }

}
