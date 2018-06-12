package UI;

import java.awt.Color;

import adventuregame.GlobalData;
import adventuregame.Input;
import gamelogic.NewObjectStorage;

public class NewHUD extends GUI {

    private boolean showPlayer1 = true;
    private boolean showPlayer2 = true;

    public NewHUD() {
        super("HUD");
    }

    public void player1() {
        //healthbar
        UIMeter health = new UIMeter(getName());
        health.get().setLocation(100, 100);
        health.setText("Health");
        health.setForegroundColor(Color.red);
        addObject(health);
        //stamina
        UIMeter stamina = new UIMeter(getName());
        stamina.get().setLocation(100, 200);
        stamina.setText("Stamina"); 
        stamina.setForegroundColor(Color.green);
        addObject(stamina);
        //energy
        UIMeter energy = new UIMeter(getName());
        energy.get().setLocation(100, 300);
        energy.setText("Energy");
        energy.setForegroundColor(Color.blue);
        addObject(energy);
        //experience
    }

    public void update() {
        super.update();
        determineUI();
    }

    public void determineUI() {
        if (NewObjectStorage.playerCount() > 1) {showPlayer2 = true;}
        else {showPlayer2 = false;}
    }

    public void player2() {
        UIMeter health = new UIMeter(getName());
        int x0 = GlobalData.getScreenDim().width - health.get().width - 100;
        health.get().setLocation(x0, 100);
        health.setText("Health");
        health.setForegroundColor(Color.red);
        addObject(health);
        //stamina
        UIMeter stamina = new UIMeter(getName());
        stamina.get().setLocation(x0, 200);
        stamina.setText("Stamina"); 
        stamina.setForegroundColor(Color.green);
        addObject(stamina);
        //energy
        UIMeter energy = new UIMeter(getName());
        energy.get().setLocation(x0, 300);
        energy.setText("Energy");
        energy.setForegroundColor(Color.blue);
        addObject(energy);
    }

    public void start() {
        UIText t1 = new UIText(getName(), "HUD", true);
        addObject(t1);
        
        determineUI();
        if (showPlayer1) {player1();}
        if (showPlayer2) {player2();}
    }
}
