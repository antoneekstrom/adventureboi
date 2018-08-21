package gamelogic;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import adventuregame.GameEnvironment;
import data.Configuration;
import data.PlayerData;
import data.Players;
import objects.Enemy;
import objects.GameObject;
import objects.Player;
import objects.Property;

public class ObjectStorage {

    private static ArrayList<GameObject> objects = new ArrayList<GameObject>();

    //players
    private static int maxPlayers = 2;
    public static int maxPlayers() {return maxPlayers;}
    
    private static int playersToSpawn = 2;
    public static int playersToSpawn() {return playersToSpawn;}
    public static void playersToSpawn(int i) {
        playersToSpawn = i;
        Configuration.setProperty("PLAYER_COUNT", String.valueOf(i));
    }

    private static ArrayList<Player> players = new ArrayList<Player>();
    public static ArrayList<Player> players() {return players;}

    public static void addPlayer(Player player) {
        if (players.size() + 1 <= maxPlayers) {
            players.add(player);
        }
    }

    public static void doneLoading() {
        for (GameObject o : objects) {
            o.onLevelLoad();
        }
    }

    /** for the amount of players to are meant to spawn: If name from list in gamenvironment
     *  does not equal null and a {@link data.PlayerData} file with the same name is available,
     *  initialize a {@link Player} with that data and put it in
     *  the playerlist in {@link ObjectStorage}. This will not put the {@link Player} in the
     *  game. Also clears all present {@link Player} in the playerlist.
     */
    public static void addPlayers() {
        players.clear();
        for (int i = 0; i < playersToSpawn; i++) {
            PlayerData data = Players.getPlayerData(GameEnvironment.playernames.get(i));

            if (GameEnvironment.playernames.get(i) != null) {
                if (data != null) {
                    players.add(new Player(data));
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

    public static Player getPlayer(String name) {
        Player p = null;
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return p;    
    }
    public static Player getPlayer(int index) {
        Player p = null;
        index--;
        if (players.size() - 1 >= index) {
            p = players.get(index);
        }
        return p;
    }

    public static ArrayList<GameObject> getObjectList() {
        return objects;
    }

    public static GameObject[] getObjectsByText(String text) {
        ArrayList<GameObject> l = new ArrayList<GameObject>();
        GameObject[] arr = null;

        for (GameObject o : objects) {
            if (o.getText().equals(text)) {l.add(o);}
        }
        
        if (l.size() > 0) {
            arr = new GameObject[l.size()];
            l.toArray(arr);
        }
            
        return arr;
    }

    public static void setList(ArrayList<GameObject> l) {objects = l;}

    private static int idnCounter = 0;
    /** Add object to game.
     * @param object - Initialized object to add to game.
    */
    public static void add(GameObject object) {
        //give id
        object.giveIdNumber(idnCounter);

        //start events
        startEvents(object);
        object.addedToLevel();

        //add it to the game
        objects.add(object);

        //increase id counter
        idnCounter++;
    }

    /** Returns true if object is a player or is owned by a player. */
    public static boolean isOfPlayer(GameObject object) {
        boolean ofPlayer = checkForType(object, Player.class);
        try {
            Property p = (Property) object;
            if (checkForType(p.owner(), Player.class)) {
                ofPlayer = true;
            }
        }
        catch (ClassCastException e) {}
        return ofPlayer;
    }

    /** Run startEvents() method for an object if it is an enemy. */
    public static void startEvents(GameObject object) {
        if (isEnemy(object)) {
            Enemy e = (Enemy) object;
            e.startEvents();
        }
    }

    /** Start events for all enemies. */
    public static void startEvents() {
        for (GameObject o : getObjectList()) {
            startEvents(o);
        }
    }

    public static boolean isEnemy(GameObject object) {
        return Enemy.class.isAssignableFrom(object.getClass());
    }
    
    public static void zoom(float percent) {
        for (GameObject o : getObjectList()) {
            o.get().setLocation((int)(o.get().x * percent), (int)(o.get().y * percent));
            o.get().setSize((int)(o.get().width * percent), (int)(o.get().height * percent));
        }
    } 

    public static GameObject getObjectByIdNumber(int idn) {
        GameObject o = null;
        for (GameObject ob : objects) {
            if (ob.idNumber() == idn) {o = ob;}
        }
        return o;
    }

    public static void remove(GameObject object) {
        objects.remove(object);
    }

    public static void clearEnvironment() {
        GameEnvironment.clearAllEvents();
        objects.clear();
    }
    
    public static void addToIndex(GameObject object, int i) {
        objects.add(i, object);
    }

    /** update all objects */
    public static void update() {
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).update();
        }
        Camera.update();
    }

    /** Find all objects of a certain type and return a list of them. */
    public static ArrayList<GameObject> findObjects(Class<?> type) {
        ArrayList<GameObject> arr = new ArrayList<GameObject>();
        for (GameObject o : objects) {
            if (checkForType(o, type)) {
                arr.add(o);
            }
        }
        return arr;
    }

    public static ArrayList<?> findObjects(String name) {
        ArrayList<GameObject> arr = new ArrayList<GameObject>();
        for (GameObject o : objects) {
            if (o.getName().equals(name)) {
                arr.add(o);
            }
        }
        return arr;
    }

    /** This is probably quite resource intensive and very inefficient but I do not care to be honest. */
    public static int countType(Class<?> c) {
        int i = 0;

        for (GameObject object : objects) {
            if (checkForType(object, c)) {
                i++;
            }
        }

        return i;
    }

    /** Check if object is of a certain subclass. */
    public static boolean checkForType(Object candidate, Class<?> type) {
        if (candidate != null) {
            return type.isInstance(candidate);
        }
        else {
            return false;
        }
    }

    //paint all objects to screen
    public static void paint(Graphics g) {
        for (GameObject o : objects) {
            o.paint(g);
        }
    }

    public static String findNearestPlayerName(Point p) {
        String name = null;

        int shortest = -1;
        for (Player player : players) {
            int distance = (int)Point.distance(p.x, p.y, player.get().x, player.get().y);
            if (shortest == -1) {shortest = distance; name = player.getName();}
            else if (distance < shortest) {shortest = distance; name = player.getName();}
        }

        return name;
    }
    public static Player findNearestPlayer(Point p) {
        return getPlayer(findNearestPlayerName(p));
    }

    /** Find all gameobjects in specified range.
     *  @param distance as length of side on a square.
     */
    public static ArrayList<GameObject> findNearbyObjects(int distance, Point origin) {
        ArrayList<GameObject> l = new ArrayList<GameObject>();

        /** Area to search in */
        Rectangle area = new Rectangle(origin.x - (distance / 2), origin.y - (distance / 2), distance, distance);

        for (GameObject object : objects) {
            if (area.intersects(object.get())) {
                l.add(object);
            }
        }

        return l;
    }
}
