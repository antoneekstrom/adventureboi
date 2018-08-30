package objects;

import adventuregame.GameEnvironment;
import adventuregame.Images;
import gamelogic.ObjectStorage;

public class Checkpoint extends GameObject {

    private boolean active = false;

    public Checkpoint() {
        super();
        start();
        setImage(Images.getImage("checkpoint"));
        get().setSize(300, 300);
    }

    void start() {
        collidable(false);
        collideWithPlayers = false;
        showDebug(true);
    }
    
    @Override
    public void collide(GameObject collision) {
        super.collide(collision);
        setDebugString("isPlayer: " + ObjectStorage.isOfPlayer(collision));
    }

    @Override
    public void playerContact(Player player) {
        super.playerContact(player);

        if (!active) {
            activate(player);
        }
    }

    void activate(Player player) {
        setAsActive();
        saveSpawnpoint(player);
        savePlayer();
    }

    void saveSpawnpoint(Player player) {
        player.spawnPoint = getCenter();
    }

    void saveProgress() {
        GameEnvironment.saveGame();
    }

    void savePlayer() {
        GameEnvironment.savePlayers();
    }

    void setAsActive() {
        for (GameObject o : ObjectStorage.findObjects(this.getClass())) {
            Checkpoint c = (Checkpoint) o;
            c.active(false);
        }
        active(true);
    }

    void active(boolean active) {this.active = active;}

}