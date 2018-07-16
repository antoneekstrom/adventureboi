package UI;

import java.awt.Color;
import java.awt.Dimension;

import adventuregame.GlobalData;
import data.PlayerData;
import gamelogic.NewObjectStorage;
import objects.NewObject;
import objects.NewPlayer;

public class NewHUD extends GUI {

    private UIText t1;
    private NewObject currentObject;
    public NewObject getCurrentObject() {return currentObject;}
    public void setCurrentObject(NewObject o) {currentObject = o;}
    private Dimension barSize = new Dimension(300, 40);

    private int xpOffset = 250;
    private int xpScreenTime = 100;
    private int xpTimeCounter = 0;
    private int xpIndexOffset = 50;

    private int xpBarScreenTime = 250;
    private int xpBarTimeCounter = 0;
    private Dimension xpBarSize = new Dimension(400,  20);
    private UIMeter xpMeter;
    private int xpBarOffset = 150;

    public NewHUD() {
        super("HUD");
        setGuidelineY1(100);
        setGuidelineSpacing(110);
    }

    public void player1() {
        //healthbar
        UIMeter health = new UIMeter(getName());
        health.get().setLocation(100, getGuidelineY1());
        health.createText("Health");
        health.setForegroundColor(Color.red);
        health.setTag("player1");
        health.setFontSize(FONT_SIZE);
        health.get().setSize(barSize);
        addObject(health);
        //stamina
        UIMeter stamina = new UIMeter(getName());
        stamina.get().setLocation(100, getGuidelineY1());
        stamina.createText("Stamina");
        stamina.setForegroundColor(Color.green);
        stamina.setTag("player1");
        stamina.setFontSize(FONT_SIZE);
        stamina.get().setSize(barSize);
        addObject(stamina);
        //energy
        UIMeter energy = new UIMeter(getName());
        energy.get().setLocation(100, getGuidelineY1());
        energy.createText("Energy");
        energy.setForegroundColor(Color.blue);
        energy.setTag("player1");
        energy.setFontSize(FONT_SIZE);
        energy.get().setSize(barSize);
        addObject(energy);
        //experience
    }
    
    public void update() {
        super.update();
        determineUI();
        updateStats();
        updateXp();
        debug();
    }

    @Override
    public void leftClick() {
        for (UIObject o : getObjectsByTag("objectInspect")) {
            if (!o.checkMouse()) {
                getUIObjectList().remove(o);
            }
        }
    }

    private void updateXp() {
        //update xp text
        int xponscreen = getObjectsByTag("xp").length;

        if (xpTimeCounter + 1 <= xpScreenTime && xponscreen > 0) {xpTimeCounter++;}
        else {
            xpTimeCounter = 0;
            UIObject[] arr = getObjectsByTag("xp");
            if (arr.length > 0) {
                remove(arr[0]);
            }
        }

        //update xpbar
        if (xpBarTimeCounter + 1 <= xpBarScreenTime && xpMeter.visible()) {xpBarTimeCounter++;}
        else {
            xpBarTimeCounter = 0;
            xpMeter.setVisible(false);
        }
    }

    public void gainXp(int amount, String playername) {
        //show text
        UIText t = new UIText(getName(), playername + ": " + "+" + amount + "xp", true) {
            {
                textColor(Color.green);
                setTag("xp");
                //set position to bottom of screen and higher depending on amount of other xp texts currently showing.
                int xpObjectsOnScreen = getObjectsByTag("xp").length;
                get().y = GlobalData.getScreenDim().height - xpOffset - (xpObjectsOnScreen * xpIndexOffset);
            }
        };
        addObject(t);

        showXpBar(xpBarScreenTime, playername);
    }

    public void showXpBar(int time, String playername) {
        PlayerData pd = NewObjectStorage.getPlayer(playername).playerData();

        xpMeter.setValue(pd.experiencepoints());
        xpMeter.setMaxValue(pd.experiencegoal());

        if (!xpMeter.visible()) {
            xpMeter.setVisible(true);
            xpBarScreenTime = time;
        }
    }

    public void debug() {
        t1.setText(String.valueOf(NewObjectStorage.getPlayer(1).playerData().abilityslot()));
    }

    public void updateStats() {
        //player1
        if (NewObjectStorage.getPlayer(1) != null) {
            NewPlayer p1 = NewObjectStorage.getPlayer(1);

            UIMeter health1 = (UIMeter) GUI.findObject(getObjectsByTag("player1"), "Health");
            health1.setValue( (double) p1.healthModule().health());
            health1.setMaxValue( (double) p1.healthModule().maxHealth());
            
            UIMeter energy2 = (UIMeter) GUI.findObject(getObjectsByTag("player1"), "Energy");
            energy2.setValue((int)p1.energy());
            energy2.setMaxValue((int)p1.playerData().maxenergy());
        }
        
        //player2
        if (NewObjectStorage.playerCount() > 1) {
            NewPlayer p2 = NewObjectStorage.getPlayer(2);
            UIMeter health2 = (UIMeter) GUI.findObject(getObjectsByTag("player2"), "Health");
            health2.setValue( (double) p2.healthModule().health());
            health2.setMaxValue( (double) p2.healthModule().maxHealth());
        }
    }
    
    public void determineUI() {
        for (UIObject o : getObjectsByTag("player2")) {
            if (NewObjectStorage.playerCount() > 1) {o.setVisible(true);} else {o.setVisible(false);}
        }
    }
    
    public void player2() {
        UIMeter health = new UIMeter(getName());
        int x0 = GlobalData.getScreenDim().width - health.get().width - 100;
        resetGuidelines();
        setGuidelineY1(100);
        setGuidelineSpacing(150);
        health.get().setLocation(x0, getGuidelineY1());
        health.createText("Health");
        health.setForegroundColor(Color.red);
        health.setTag("player2");
        health.valueSide = "left";
        addObject(health);
        //stamina
        UIMeter stamina = new UIMeter(getName());
        stamina.get().setLocation(x0, getGuidelineY1());
        stamina.createText("Stamina"); 
        stamina.setForegroundColor(Color.green);
        stamina.setTag("player2");
        stamina.valueSide = "left";
        addObject(stamina);
        //energy
        UIMeter energy = new UIMeter(getName());
        energy.get().setLocation(x0, getGuidelineY1());
        energy.createText("Energy");
        energy.setForegroundColor(Color.blue);
        energy.setTag("player2");
        energy.valueSide = "left";
        addObject(energy);
    }

    public void start() {
        t1 = new UIText(getName(), "HUD", true);
        FONT_SIZE = 30;
     
        player1();
        player2();

        xpInit();
    }

    public void xpInit() {
        xpMeter = new UIMeter(getName()) {
            {
                setTag("xpbar");
                setValue(0);
                setMaxValue(0);
                setForegroundColor(Color.green);
                setBackgroundPadding(10);
                get().setSize(xpBarSize);
                get().setLocation(0, GlobalData.getScreenDim().height - xpBarOffset);
                centerInParentX(true);
            }
        };
        addObject(xpMeter);
    }
}
