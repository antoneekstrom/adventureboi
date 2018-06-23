package adventuregame;

import java.awt.Graphics;
import java.io.Serializable;

import objects.NewObject;

public class HealthModule implements Serializable {
	
	private static final long serialVersionUID = 1L;
	int health, maxHealth;
	int damage = 0;
	
	int dmgcooldown = 20;
	int cooldowncounter = dmgcooldown;

	private boolean invulnerable = false;
	private boolean showHp = false;
	private boolean invincible = false;
	private boolean canDie = true;
	
	public HealthModule(int maxhp) {
		setHealth(maxhp);
		setMaxHealth(maxhp);
	}

	public void hpLimit() {
		if (!canDie && health < 0) {
			health = 0;
		}
	}
	
	public void showHp() {
	}

	public void hpCheck() {
		
		if (invulnerable) {
			cooldowncounter -= 1;
			
			if (cooldowncounter <= 0) {
				invulnerable = false;
				cooldowncounter = dmgcooldown;
			}
		}
	}

	public int maxHealth() {return maxHealth;}
	public void setDamage(int i) {damage = i;}
	public void setMaxHealth(int h) {maxHealth = h;}
	public void setCanDie(boolean b) {canDie = b;}
	public boolean canDie() {return canDie;}
	public void setHealth(int h) {health = h;}
	public int health() {return health;}
	public boolean isInvincible() {return invincible;}
	public void invincible(boolean b) {invincible = b;}
	public boolean hpVisible() {return showHp;}
	public boolean isDead() {if (health < 0 && canDie() && !invincible) {return true;} else {return false;}}
	
	public void update(NewObject object) {
		hpCheck();
	}
	
	public void decreaseHealth(int h) {
		if (!invulnerable && !invincible) {
			health -= h;
			invulnerable = true;
		}
	}


	public void paint(Graphics g) {
		if (showHp) {
		}
	}
	
}
