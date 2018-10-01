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

    double playerRange = 300;

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
            givePrompt(ObjectStorage.findNearestPlayer(getCenter()).getName());
        }
        else {
            setText(ObjectStorage.findNearestPlayer(getCenter()).getName());
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