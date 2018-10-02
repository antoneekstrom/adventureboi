package objects;

import java.awt.Graphics;
import java.awt.Graphics2D;

import graphic.Dialog;

public abstract class Vendor extends Interactable {

    public Vendor() {
        super();
        givePrompt("nice");
    }

    /** Give the vendor a prompt to display when a player is near. */
    public void givePrompt(String text) {
        setText(text);

        if (dialog == null) {
            dialog = new Dialog(this);
        }
    }

    Dialog dialog;

    @Override
    public void playerEntersRange(Player p) {
        super.playerEntersRange(p);
        givePrompt(addressNearbyPlayers());
    }

    @Override
    protected void logic() {
        super.logic();
    }

    public String GREETING = "Greetings";

    public String addressNearbyPlayers() {
        String text = GREETING;

        switch (nearbyPlayers().size()) {
        case 1:
            text += " " + nearbyPlayers().get(0).getName();
            break;
            
        case 2:
            text += " " + nearbyPlayers().get(0).getName() + " and " + nearbyPlayers().get(1).getName();
            break;

        default:
            for (Player p : nearbyPlayers()) {
                text += " " + p.getName() + ",";
            }
            break;
        }

        return text;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (dialog != null && playerWithinRange()) {
            dialog.paint((Graphics2D) g, this);
        }
    }

}