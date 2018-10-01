package objects;

import java.awt.Graphics;
import java.awt.Graphics2D;

import gamelogic.ObjectStorage;
import graphic.Dialog;

public class Vendor extends GameObject implements Interactable {

    public Vendor() {
        super("nice");
        givePrompt("nice");
    }

    /** Range that player has to be within to interact with this vendor. */
    double playerRange = 300;
    public void setPlayerRange(double range) {playerRange = range;}
    public double getPlayerRange() {return playerRange;}


    /** Give the vendor a prompt to display when a player is near. */
    public void givePrompt(String text) {
        setText(text);
        dialog = new Dialog(this);
    }

    Dialog dialog;

    @Override
    public boolean interact(Player player) {


        return true;
    }

    @Override
    protected void logic() {
        super.logic();
        if (getText().equals("nice")) {
            givePrompt("greetings " + ObjectStorage.findNearestPlayer(getCenter()).getName());
        }
        else {
            setText("greetings " + ObjectStorage.findNearestPlayer(getCenter()).getName());
        }
    }

    public boolean playerInRange() {
        return ObjectStorage.distanceToNearestPlayer(getCenter()) <= playerRange;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (dialog != null && playerInRange()) {
            dialog.paint((Graphics2D) g, this);
        }
    }

}