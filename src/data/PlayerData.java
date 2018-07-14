package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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
    private int experiencelevel = -1;
    public void experiencelevel(int h) {experiencelevel = h;}
    public int experiencelevel() {return experiencelevel;}
    public void increaselevel(int i) {experiencelevel += i;}

    private double experiencepoints = 0;
    public void experiencepoints(double h) {experiencepoints = h;}
    public double experiencepoints() {return experiencepoints;}

    private double experiencegoal = 0;
    public void experiencegoal(int h) {experiencegoal = h;}
    public double experiencegoal() {return experiencegoal;}

    //items
    private ArrayList<Item> inventory;
    public void inventory(ArrayList<Item> h) {inventory = h;}
    public ArrayList<Item> inventory() {return inventory;}

    //equipped
    private Item itemslot1;
    private Item itemslot2;
    private Item itemslot3;
    private Item abilityslot;
    public void itemslot1(Item h) {itemslot1 = h;}
    public Item itemslot1() {return itemslot1;}
    public void itemslot2(Item h) {itemslot2 = h;}
    public Item itemslot2() {return itemslot2;}
    public void itemslot3(Item h) {itemslot3 = h;}
    public Item itemslot3() {return itemslot3;}
    public void abilityslot(Item h) {abilityslot = h;}
    public Item abilityslot() {return abilityslot;}

    //item slots
    public static final String SLOT1 = "1", SLOT2 = "2", SLOT3 = "3", ABILITY = "ability";

    public HashMap<String, Object> getStatMap() {

        HashMap<String, Object> map = new HashMap<String, Object>() {

            private static final long serialVersionUID = 1L;
			{
                put("level", experiencelevel());
                put("health", maxHealth());
                put("energy", maxenergy());
                put("energy regen", energyregen());
                put("stamina", maxstamina());
                put("stamina regen", staminaregen());
                put("damage", damage());
            }
        };

        return map;
    }
    
    public PlayerData() {
    }

}
