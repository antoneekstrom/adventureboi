package UI;

import java.util.ArrayList;

import adventuregame.Character;

public class Statistics {
	
	//get data
	public int getHealth() {
		return Integer.parseInt(Character.getData("maxhealth"));
	}
	
	public int getStamina() {
		return Integer.parseInt(Character.getData("maxstamina"));
	}
	
	public int getEnergy() {
		return Integer.parseInt(Character.getData("maxenergy"));
	}
	
	public int getDamage() {
		return Integer.parseInt(Character.getData("basedamage"));
	}
	
	public double getEnergyRate() {
		return Double.parseDouble(Character.getData("energyrate"));
	}
	
	public ArrayList<String> get() {
		ArrayList<String> l = new ArrayList<String>();
		l.add("max health: " + getHealth() + " ");
		l.add("max energy: " + getEnergy() + " ");
		l.add("max stamina: " + getStamina() + " ");
		l.add("energy rate: " + getEnergyRate() + "e/tick ");
		l.add("base damage: " + getDamage() + " ");
		return l;
	}
	
	//set data
	public void setHealth(int i) {
		Character.setData("maxhealth", String.valueOf(i));
	}
	
	public void setStamina(int i) {
		Character.setData("maxstamina", String.valueOf(i));
	}
	
	public void setEnergy(int i) {
		Character.setData("maxenergy", String.valueOf(i));
	}
	
	public void setDamage(int i) {
		Character.setData("basedamage", String.valueOf(i));
	}
	
	public void setEnergyRate(int i) {
		Character.setData("energyrate", String.valueOf(i));
	}
	
}
