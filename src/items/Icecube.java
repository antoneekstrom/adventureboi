package items;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import gamelogic.Item;
import items.abilities.Ability;
import objects.Player;

public class Icecube extends Ability {

    private static final long serialVersionUID = 1L;
    private int PERCENT;
    private int TIME;

    private int INTERVAL = 100;
    private int timerCounter = 0;

    private Player player;

	public Icecube() {
        super("icecube");
        start();
    }

    public void start() {
        description = new String[] {"Wow this icecube", "is very cold haha.", "nice."};
        effect = Item.ABILITY;
        abilityDescription = "Makes you shrink";
        imageName("icecube2");
        useOnPickup(false);

        PERCENT = 45;
        COOLDOWN = 150;
        TIME = 35;
        COST = 70;

        FACTORMAX = 0;
        FACTORINCREASE = 0;
    }

    @Override
    public void use(Player player) {
        if (!player.isShrinked()) {
            super.use(player);
            this.player = player;
            player.shrink(PERCENT);
            player.physics().setGravity(false);
            player.physics().resetVelocity();
            startTimer();
        }
    }

    private void startTimer() {
        Timer timer = new Timer();

        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                if (timerCounter + 1 <= TIME) {timerCounter++;}
                else {
                    timerDone();
                    cancel();
                }
            }
        };
        timer.schedule(task, 0, INTERVAL);
    }

    private void timerDone() {
        timerCounter = 0;
        player.physics().setGravity(true);
        player.expand();
    }

    @Override
    public HashMap<String, Object> getValueMap() {
        HashMap<String, Object> m = super.getValueMap();
        m.remove("damagepercent");
        m.remove("chargecost");
        m.remove("factorincrease");
        m.remove("factormax");
        m.put("shrinkage", PERCENT);
        m.put("time", TIME);
        return m;
    }

    @Override
    protected void scaleStats() {
        PERCENT *= levelPow(Item.SMALL_INCREASE_FACTOR);
        COOLDOWN *= levelPow(Item.SMALL_DECREASE_FACTOR);
        COST *= levelPow(Item.SMALL_DECREASE_FACTOR);
        TIME *= levelPow(Item.SMALL_INCREASE_FACTOR);
    }

}