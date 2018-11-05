package objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import UI.UIManager;
import UI.VendorItem;
import UI.VendorUI;
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

    public void showPrompt(boolean visible) {
        if (dialog != null) {
            dialog.setVisible(visible);
        }
    }

    public double getPrice(Item i) {
        if (hasItem(i)) {
            for (objects.VendorItem vi : getInv()) {
                if (vi.item.equals(i)) {
                    return vi.price;
                }
            }
        }
        return 0;
    }

    public boolean hasItem(Item specimen) {
        boolean b = false;

        for (objects.VendorItem i : getInv()) {
            if (i.item.equals(specimen)) {
                b = true;
            }
        }

        return b;
    }

    Dialog dialog;
    public Dialog getDialog() { return dialog; }

    private ArrayList<objects.VendorItem> inv = new ArrayList<>();
    public ArrayList<objects.VendorItem> getInv() { return inv; }
    public void setInv(ArrayList<objects.VendorItem> i) { inv = i; }

    public void addToInv(objects.VendorItem item) {
        getInv().add(item);
    }

    public Collection<VendorItem> getListEntries(String parentname) {
        Collection<VendorItem> c = new ArrayList<VendorItem>();

        for (objects.VendorItem i : getInv()) {
            c.add(new VendorItem(parentname, i, this));
        }
        return c;
    }

    public void openInventory() {
        VendorUI ui = (VendorUI) UIManager.getGUI("vendor");
        String current = UIManager.getCurrentGUI().getName();

        if (current.equals("HUD") || current.equals("vendor")) {
            if (!ui.isVisible()) {
                ui.enable(this);
                showPrompt(false);
            }
            else {
                UIManager.enableHUD(true);
                showPrompt(true);
                getDialog().setTextWidth(Dialog.DEFAULT_TEXT_WIDTH);
            }
        }
    }

    public objects.VendorItem getVendorItem(Item item) {

        for (objects.VendorItem vi : getInv()) {

            if (vi.item.equals(item)) {
                return vi;
            }
        }
        return null;
    }

    public String
        GREETING = "Greetings",
        ON_PURCHASE = "It is very epic of you to trade with me, I am incredibly grateful.",
        ON_FAIL = "You are too poor to purchase my wares, that is very unfortunate.";

    public boolean purchase(Item item, Player player) {
        int price = (int) getPrice(item);

        if (!hasItem(item)) {return false; } /* Return unsuccessful if vendor does not sell item. */

        boolean successful = player.purchase(price);

        if (successful) {
            givePrompt(ON_PURCHASE);
            player.addItem(item.duplicate(getVendorItem(item).level));
            player.refreshInventory();
            VendorUI.updateValues();
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
    public Point getCameraLocation() {
        Point p = super.getCameraLocation();
        p.y -= 200;
        return p;
    }

    @Override
    public void playerEntersRange(Player p) {
        super.playerEntersRange(p);

        givePrompt(addressNearbyPlayers());
        cameraFocus(true);
    }
    
    @Override
    public void playerLeavesRange(Player p) {
        super.playerLeavesRange(p);

        UIManager.getGUI("vendor").setVisible(false);
        p.cameraFocus(true);
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