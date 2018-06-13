package UI;

import java.awt.Color;

import adventuregame.GlobalData;
import gamelogic.NewObjectStorage;
import objects.NewPlayer;

public class NewHUD extends GUI {

    private boolean showPlayer1 = true;
    private boolean showPlayer2 = true;

    public NewHUD() {
        super("HUD");
        setGuidelineY1(100);
        setGuidelineSpacing(150);
    }

    public void player1() {
        //healthbar
        UIMeter health = new UIMeter(getName());
        health.get().setLocation(100, getGuidelineY1());
        health.createText("Health");
        health.setForegroundColor(Color.red);
        health.setTag("player1");
        addObject(health);
        //stamina
        UIMeter stamina = new UIMeter(getName());
        stamina.get().setLocation(100, getGuidelineY1());
        stamina.createText("Stamina");
        stamina.setForegroundColor(Color.green);
        stamina.setTag("player1");
        addObject(stamina);
        //energy
        UIMeter energy = new UIMeter(getName());
        energy.get().setLocation(100, getGuidelineY1());
        energy.createText("Energy");
        energy.setForegroundColor(Color.blue);
        energy.setTag("player1");
        addObject(energy);
        //experience
    }
    
    public void update() {
        super.update();
        determineUI();
        updateStats();
    }

    public void updateStats() {
        //player1
        NewPlayer p1 = NewObjectStorage.getPlayer(1);
        UIMeter health1 = (UIMeter) GUI.findObject(getObjectsByTag("player1"), "Health");
        health1.setValue( (double) p1.healthModule().health());
        health1.setMaxValue( (double) p1.healthModule().maxHealth());
        
        //player2
        NewPlayer p2 = NewObjectStorage.getPlayer(2);
        UIMeter health2 = (UIMeter) GUI.findObject(getObjectsByTag("player2"), "Health");
        health2.setValue( (double) p2.healthModule().health());
        health2.setMaxValue( (double) p2.healthModule().maxHealth());
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
        addObject(health);
        //stamina
        UIMeter stamina = new UIMeter(getName());
        stamina.get().setLocation(x0, getGuidelineY1());
        stamina.createText("Stamina"); 
        stamina.setForegroundColor(Color.green);
        stamina.setTag("player2");
        addObject(stamina);
        //energy
        UIMeter energy = new UIMeter(getName());
        energy.get().setLocation(x0, getGuidelineY1());
        energy.createText("Energy");
        energy.setForegroundColor(Color.blue);
        energy.setTag("player2");
        addObject(energy);
    }
    
    public void start() {
        UIText t1 = new UIText(getName(), "HUD", true);
        addObject(t1);
     
        player1();
        player2();
    }
}
