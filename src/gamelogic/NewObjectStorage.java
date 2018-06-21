package gamelogic;

import java.awt.Graphics;
import java.util.ArrayList;

import adventuregame.GameEnvironment;
import data.Configuration;
import data.PlayerData;
import data.Players;
import objects.NewObject;
import objects.NewPlayer;

public class NewObjectStorage {

    private static ArrayList<NewObject> objects = new ArrayList<NewObject>();

    //players
    private static int maxPlayers = 2;
    public static int maxPlayers() {return maxPlayers;}
    
    private static int playersToSpawn = 2;
    public static int playersToSpawn() {return playersToSpawn;}
    public static void playersToSpawn(int i) {
        playersToSpawn = i;
        Configuration.setProperty("PLAYER_COUNT", String.valueOf(i));
    }

    private static ArrayList<NewPlayer> players = new ArrayList<NewPlayer>();
    public static ArrayList<NewPlayer> players() {return players;}

    public static void addPlayer(NewPlayer player) {
        if (players.size() + 1 <= maxPlayers) {
            players.add(player);
        }
    }

    /** for the amount of players to are meant to spawn: If name from list in gamenvironment
     *  does not equal null and a {@link data.PlayerData} file with the same name is available,
     *  initialize a {@link NewPlayer} with that data and put it in
     *  the playerlist in {@link NewObjectStorage}. This will not put the {@link NewPlayer} in the
     *  game. Also clears all present {@link NewPlayer} in the playerlist.
     */
    public static void addPlayers() {
        players.clear();
        for (int i = 0; i < playersToSpawn; i++) {
            PlayerData data = Players.getPlayerData(GameEnvironment.playernames.get(i));

            if (GameEnvironment.playernames.get(i) != null) {
                if (data != null) {
                    players.add(new NewPlayer(data));
                }
            }
        }
    }

    /** Spawn and add all players from the player list into the game. */
    public static void spawnPlayers() {
        for (int i = 0; i < playersToSpawn; i++) {
            objects.add(i, players.get(i));
        }
    }

    public static int playerCount() {return players.size();}

    public static NewPlayer getPlayer(String name) {
        NewPlayer p = null;
        for (NewPlayer player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return p;    
    }
    public static NewPlayer getPlayer(int index) {
        NewPlayer p = null;
        index--;
        if (players.size() - 1 >= index) {
            p = players.get(index);
        }
        return p;
    }

    public static ArrayList<NewObject> getObjectList() {
        return objects;
    }

    public static void setList(ArrayList<NewObject> l) {objects = l;}

    /** Add object to game.
     * @param object - Initialized object to add to game.
    */
    public static void add(NewObject object) {
        objects.add(object);
    }

    public static void remove(NewObject object) {
        objects.remove(object);
    }

    public static void clearEnvironment() {
        objects.clear();
    }

    public static void addToIndex(NewObject object, int i) {
        objects.add(i, object);
    }

    /** update all objects */
    public static void update() {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).update();
        }
        NewCamera.update();
    }

    /** Find all objects of a certain type and return a list of them. */
    public static ArrayList<?> findObjects(Class<?> type) {
        ArrayList<NewObject> arr = new ArrayList<NewObject>();
        for (NewObject o : objects) {
            if (checkForType(o, type)) {
                arr.add(o);
            }
        }
        return arr;
    }

    /** Check if object is of a certain subclass. */
    public static boolean checkForType(Object candidate, Class<?> type){
        return type.isInstance(candidate);
    }

    //paint all objects to screen
    public static void paint(Graphics g) {
        for (NewObject o : objects) {
            o.paint(g);
        }
    }
}
