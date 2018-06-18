package data;

import java.io.Serializable;
import java.util.ArrayList;

import gamelogic.Item;

public class PlayerData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    public void name(String n) {name = n;}
    public String name() {return name;}

    //health
    private double maxhealth;
    private double healthregen;
    public void maxHealth(double h) {maxhealth = h;}
    public double maxHealth() {return maxhealth;}
    public void healthregen(double h) {healthregen = h;}
    public double healthregen() {return healthregen;}

    //energy
    private double maxenergy;
    private double energyregen;
    public void maxenergy(double h) {maxenergy = h;}
    public double maxenergy() {return maxenergy;}
    public void energyregen(double h) {energyregen = h;}
    public double energyregen() {return energyregen;}

    //stamina
    private double maxstamina;
    private double staminaregen;
    public void maxstamina(double h) {maxstamina = h;}
    public double maxstamina() {return maxstamina;}
    public void staminaregen(double h) {staminaregen = h;}
    public double staminaregen() {return staminaregen;}

    //damage
    private double damage;
    public void damage(double h) {damage = h;}
    public double damage() {return damage;}

    //level
    private double experiencelevel;
    public void experiencelevel(double h) {experiencelevel = h;}
    public double experiencelevel() {return experiencelevel;}

    //items
    private ArrayList<Item> inventory;
    public void inventory(ArrayList<Item> h) {inventory = h;}
    public ArrayList<Item> inventory() {return inventory;}

    //equipped
    private Item itemslot1;
    private Item itemslot2;
    private Item itemslot3;
    public void itemslot1(Item h) {itemslot1 = h;}
    public Item itemslot1() {return itemslot1;}
    public void itemslot2(Item h) {itemslot2 = h;}
    public Item itemslot2() {return itemslot2;}
    public void itemslot3(Item h) {itemslot3 = h;}
    public Item itemslot3() {return itemslot3;}
    
    public PlayerData() {

    }

}
