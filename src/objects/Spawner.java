package objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import adventuregame.Position;
import data.NumberFactory;
import data.ObjectData;
import data.SpawnerData;
import gamelogic.ObjectStorage;
import gamelogic.TriggerEvent;

public class Spawner extends GameObject {

    public int amountToSpawn, maxAlive, cooldown, timer = 0;
    public int level = 0;
    public Dimension spawnArea = new Dimension(250, 250);
    public Dimension spawnerSize = new Dimension(100, 100);
    public String spawnerId;
    public Class<?> classToSpawn;
    public ArrayList<TriggerEvent> triggers = new ArrayList<TriggerEvent>();

    public Spawner(int amountToSpawn, int maxAlive, int cooldown, Class<?> classToSpawn, TriggerEvent e) {
        super();
        this.amountToSpawn = amountToSpawn;
        this.maxAlive = maxAlive;
        this.cooldown = cooldown;
        this.classToSpawn = classToSpawn;
        addTrigger(e);
        start();
    }
    public Spawner(int amountToSpawn, int maxAlive, int cooldown, Class<?> classToSpawn, TriggerEvent[] err) {
        super();
        this.amountToSpawn = amountToSpawn;
        this.maxAlive = maxAlive;
        this.cooldown = cooldown;
        this.classToSpawn = classToSpawn;
        for (TriggerEvent e : err) {addTrigger(e);}
        start();
    }

    /** Might not be very reliable if spawners are added or removed which will cause some to have the same id. Most of the time they won't though, hopefully. */
    private void setSpawnerId() {
        spawnerId = "SPAWNER_" + NumberFactory.randomString(16) + "_" + "diarrhea hell yeah";
    }

    private void start() {
        //object related
        get().setSize(spawnerSize);
        physics().setGravity(false);
        moveWhenColliding(false);
        setCollision(false);
        setIntersect(false);
        setColor(Color.green);

        //logic related
        setSpawnerId();
    }

    @Override
    public ObjectData extractData() {
        return new SpawnerData(this);
    }

    @Override
    protected void logic() {
        super.logic();

        timer();
        if (checkTriggers(this) && canSpawn()) {
            spawn();
        }
    }

    /** Update timer. */
    void timer() {
        if (timer < cooldown) {timer++;}
    }

    /** Spawn a copy. */
    void spawn() {
        Point location = findLocation();
        GameObject object = createObject();

        if (object != null) {
            object.get().setLocation(location);
            setEnemyLevel(object);
            object.addTag(tagChild(object));
        }
        ObjectStorage.add(object);
    }

    String tagChild(GameObject object) {
        return object.getClass().getSimpleName() + "_" + spawnerId;
    }

    public void setEnemyLevel(GameObject object) {
        if (ObjectStorage.isEnemy(object)) {
            Enemy e = (Enemy) object;
            e.level(level);
        }
    }
    
    GameObject createObject() {
        GameObject object = null;
        try {   
            object = (GameObject)classToSpawn.newInstance();
        }
        catch (Exception e) {e.printStackTrace();}
        return object;
    }

    Point findLocation() {
        Point p = new Point(0,0);
        Random r = new Random();

        int x = r.nextInt(spawnArea.width);
        if (r.nextFloat() < 0.5) {
            x = -x;
        }
        x = get().x +x;
        
        int y = r.nextInt(spawnArea.height);
        if (r.nextFloat() < 0.5) {
            y = -y;
        }
        y = get().y +y;

        p.setLocation(x, y);

        return p;
    }

    int countChildren() {
        int c = 0;
        ArrayList<GameObject> l = ObjectStorage.getObjectList();
        for (int i = l.size()-1; i > 1; i--) {
            if (isChild(l.get(i))) {
                c++;
            }
        }
        return c;
    }

    boolean isChild(GameObject object) {
        if (object.hasTagThatContains(spawnerId)) {
            return true;
        }
        else {
            return false;
        }
    }

    boolean canSpawn() {
        boolean can = true;

        if (amountToSpawn - 1 < 0) {can = false;} /* if spawn capacity is unmet */
        if (timer != cooldown) {can = false;} else {timer = 0;} /* If cooldown is ready reset it */
        if (countChildren()+1 > maxAlive) {can = false;} /* If there are too many bois alive yknow */

        if (can) {
            amountToSpawn--;
        }

        return can;
    }

    /** Check all triggers and return true if any of them are met. */
    boolean checkTriggers(Spawner sp) {
        Player player = ObjectStorage.findNearestPlayer(get().getLocation());
        for (TriggerEvent e : triggers) {
            if (e.check(this, player)) {return true;}
        }
        return false;
    }

    void addTrigger(TriggerEvent e) {
        triggers.add(e);
    }

    /* --- Preset triggers --- */
    public static TriggerEvent playerProximity(int range) {
        return new TriggerEvent(){
            private static final long serialVersionUID = 1L;
			@Override
            public boolean check(Spawner spawner) {
                return false;
            }
            @Override
            public boolean check(Spawner spawner, Player player) {
                if (range > Position.distance(spawner.get().getLocation(), player.get().getLocation())) {
                    return true;
                }
                else {
                    return false;
                }
            }
        };
    }
}