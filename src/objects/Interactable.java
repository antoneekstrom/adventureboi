package objects;

import java.util.ArrayList;

import gamelogic.ObjectStorage;

public abstract class Interactable extends GameObject {

    /** Range that player has to be within to interact with this object. */
    double playerRange = 300;
    public void setPlayerRange(double range) {playerRange = range;}
    public double getPlayerRange() {return playerRange;}

    public abstract boolean interact(Player player);

    ArrayList<Player> nearbyPlayers = new ArrayList<>();
    boolean playerWithinRange = false;

    public ArrayList<Player> nearbyPlayers() {
        return nearbyPlayers;
    }

    public void playerEntersRange(Player p) {
        nearbyPlayers().add(p);
    }

    public void playerLeavesRange(Player p) {
        nearbyPlayers().remove(p);
    }

    private void updatePlayerInRange() {
        playerWithinRange = ObjectStorage.distanceToNearestPlayer(getCenter()) <= playerRange;
    }

    public Player[] playersWithinRange() {
        return ObjectStorage.findPlayersWithinRange((int)getPlayerRange(), getCenter());
    }

    public boolean playerWithinRange() { return playerWithinRange; }

    public void updatePlayerLists() {
        for (Player p : ObjectStorage.players()) {
            p.removeNearbyInteractable(this);
        }

        for (Player p : playersWithinRange()) {
            p.registerNearbyInteractable(this);
        }
    }

    @Override
    protected void logic() {
        super.logic();
        updatePlayerInRange();
        updatePlayerLists();
    }

}