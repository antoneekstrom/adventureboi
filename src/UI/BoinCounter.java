package UI;

import java.awt.Color;
import java.awt.Point;

import gamelogic.ObjectStorage;
import objects.Player;

public class BoinCounter extends UIText {

    static final String BOIN_COUNTER = "BoinCounter";

    String playerName;
    int valueLength = 6;

    public BoinCounter(String playerName, String parentname) {
        super(parentname, "boin", false);
        this.playerName = playerName;
        style();
    }

    public void setPlayer(Player p) {
        playerName = p.getName();
    }

    public void setPlayerToClosest(Point p) {
        setPlayer(ObjectStorage.findNearestPlayer(p));
    }

    void style() {
        getParent().applyGeneralStyle(this);
        setTag(BOIN_COUNTER);
        setBackgroundColor(getParent().getUIBackgroundColor());
        textColor(Color.yellow);
        setBorderColor(Color.yellow);
    }

    public void updateValue() {

        int b = ObjectStorage.getPlayer(playerName).boinCount();
        int k = (int) Math.log10(b);
        String d = "";

        if (k < 0) {
            k = 0;
        }
        
        for (int i  = 0; i < valueLength - (k + 1); i++) {
            d += "0";
        }
        d += b;

        setText(d);
    }

}