package objects;

import java.util.ArrayList;
import java.util.Iterator;

import gamelogic.ObjectStorage;

public abstract class Interactable extends GameObject {

    /** Range that player has to be within to interact with this object. */
    double playerRange = 300;
    public void setPlayerRange(double range) {playerRange = range;}
    public double getPlayerRange() {return playerRange;}

    public abstract boolean interact(Player player);

    ArrayList<Player> nearbyPlayers = new ArrayList<>();

    public ArrayList<Player> nearbyPlayers() {
        return nearbyPlayers;
    }

    public void playerEntersRange(Player p) {
    }

    public void playerLeavesRange(Player p) {
    }

    public Player[] playersWithinRange() {
        return ObjectStorage.findPlayersWithinRange((int)getPlayerRange(), getCenter());
    }

    public boolean playerWithinRange() { return nearbyPlayers.size() > 0; }

    public void updatePlayerList() {
        ArrayList<Player> newl = ObjectStorage.listPlayersWithinRange((int)playerRange, getCenter());

        //player leaves range if old list contains a player that the new one doesn't
        for (Iterator<Player> i = nearbyPlayers.iterator(); i.hasNext();) {
            Player p = i.next();

            if (!newl.contains(p)) {
                i.remove();
                playerLeavesRange(p);
                p.removeNearbyInteractable(this);
            }
        }

        //player enters range if new list contains player the old one doesn't
        for (Player p : newl) {
            if (!nearbyPlayers.contains(p)) {
                nearbyPlayers.add(p);
                playerEntersRange(p);
                p.addNearbyInteractable(this);
            }
        }
    }

    @Override
    protected void logic() {
        super.logic();
        updatePlayerList();
    }

}