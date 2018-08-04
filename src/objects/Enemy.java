package objects;

import java.awt.Rectangle;
import java.util.ArrayList;

import adventuregame.Animator;
import adventuregame.HealthModule;
import adventuregame.Images;
import data.EnemyData;
import data.NumberFactory;
import data.ObjectData;
import gamelogic.Item;
import gamelogic.ObjectStorage;
import gamelogic.ThreadEvent;
import objects.EnemyMold;

public class Enemy extends GameObject implements EnemyMold {

    int level = 0;
    int contactDamage = 0;
    int health;
    int money;

    public void level(int level) {this.level = level; scale();}
    public int level() {return level;}
    public double experience() {return experience;}

    protected boolean destructOnDeath = true;
    boolean dead = false;

    //drops
    ArrayList<Item> drops = new ArrayList<Item>();
    double experience;

    public Enemy(int level, double experience, int health, int money, String name) {
        super();
        this.level = level;
        this.experience = experience;
        this.health = health;
        this.money = money;

        setName(name);
        setText(name);

        startCore();
        start();
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    /** Start vital things for game not to crash. */
    public void startCore() {
        get().setSize(100, 100);
    }

    public void start() {
        enableHealthModule(health);
        healthModule().showHp(true);
        enableAnimator();
        startAnimator();
        createAI();
        startAI();
        startMisc();
        
        showDebug(false);

        setImage(Images.getImage("object"));

        scale();
    }

    void debug() {
        setDebugString("jump:" + getAI().jumpToPlayer + " follow:" + getAI().playerWithinRange() + " goal:" + getAI().followGoal());
    }


    @Override
    public ObjectData extractData() {
        return new EnemyData(this);
    }

    @Override
    public void setText(String t) {
        super.setText(t);
    }

    public void startMisc() {
        shrinkSpeed = 4;
        HealthModule h = getHealthModule();
        h.dmgCooldown(0);
        h.healthbar().showLevel();
    }

    void setHome() {
        new ThreadEvent(new Runnable(){
            int i = 0;
            @Override
            public void run() {

                while (getAI() == null) {
                    i++;
                    if (i >= 100) {
                        break;
                    }
                };
                if (i < 100) {
                    getAI().setHome(get().getLocation());
                }
            }
        });
    }

    @Override
    public void onLevelLoad() {
        setHome();
    }

    public void scale() {
        double f = NumberFactory.getEnemyScaling(level);
        health = (int) (health * f);
        contactDamage = (int) (contactDamage * f);
        NumberFactory.getXpScaling(level);
        
        healthModule().setMaxHealth(health);
        healthModule().setHealth(health);
    }

    public void logic() {
        if (healthModule().isDead() && getAI().isEnabled()) {
            die();
        }
        dead();
        debug();
    }

    public void startAnimator() {
        Animator a = getAnimator();
        a.setIndexRange(0, a.size() -3);
    }

    public void startAI() {
    }

    public void addDrop(Item item) {
        drops.add(item);
    }

    public int dropAmount() {return drops.size();}

    public boolean isDead() {return dead;}

    public void startEvents() {
    }

    @Override
    public void collide(GameObject collision) {
        super.collide(collision);
        //getAI().collision(collision);
    }

    @Override
    /** Called when enemy first dies. */
	public void die() {

        //cleanup
        healthModule().showHp(false);
        getAI().setEnabled(false);
        dead = true;

        //destruct
        if (destructOnDeath) {
            shrink();
            moveWhenColliding(false);
            physics().setGravity(false);
        }
    }

    @Override
    public void shrinkDone() {
        destruct();
        drop();
    }

    /** Repeatedly called when enemy is dead. */
    public void dead() {
    }

    public void dropXp() {
        String pname = ObjectStorage.findNearestPlayerName(get().getLocation());
        ObjectStorage.getPlayer(pname).giveXp((int)experience);
    }
    
    public void drop() {
        dropXp();
        dropItem();
    }

    public void setDropLevel() {
        for (Item drop : drops) {
            drop.level(Item.getRandomLevel(level()));
        }
    }

    public void dropItem() {
        for (Item drop : drops) {
            if (drop != null) {
                setDropLevel();
                
                Rectangle e = get();
                ItemObject io = new ItemObject(drop) {{
                    Rectangle i = this.get();
                    this.get().setLocation(e.x - (i.width / 2), e.y - (i.height / 2));
                }};
                ObjectStorage.add(io);
            }
        }
    }

    public void destruct() {
        super.destruct();
        getAI().stopEvents();
        ObjectStorage.remove(this);
    }

    @Override
	public void playerContact(Player col) {
        if (contactDamage > 0) {
            col.healthModule().damage(contactDamage);
        }
    }
}