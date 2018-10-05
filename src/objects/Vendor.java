package objects;

import java.awt.Graphics;
import java.awt.Graphics2D;

import gamelogic.Item;
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

    public abstract double getPrice(Item i);
    public abstract boolean hasItem(Item i);

    Dialog dialog;

    public String
        GREETING = "Greetings",
        ON_PURCHASE = "haha nice get scammed bitch that was way too expensive",
        ON_FAIL = "Woah you are to poor to purchase my wares, that is very unfortunate.";

    public boolean purchase(Item item, Player player) {
        int price = (int) getPrice(item);

        if (!hasItem(item)) {return false; } /* Return unsuccessful if vendor does not sell item. */

        boolean successful = player.purchase(price);

        if (successful) {
            givePrompt(ON_PURCHASE);
            player.addItem(item.duplicate());
            player.refreshInventory();
        }
        else {
            givePrompt(ON_FAIL);
        }

        return successful;
    }

    @Override
    public boolean interact(Player player) {
        return false;
    }

    @Override
    public void playerEntersRange(Player p) {
        super.playerEntersRange(p);
        givePrompt(addressNearbyPlayers());
    }

    @Override
    protected void logic() {
        super.logic();
    }

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