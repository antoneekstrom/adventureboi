package adventuregame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import UI.Healthbar;
import objects.GameObject;

public class HealthModule implements Serializable {
	
	private static final long serialVersionUID = 1L;
	int health, maxHealth;
	int damage = 0;
	
	int dmgcooldown = 35;
	int cooldowncounter = dmgcooldown;

	private boolean invulnerable = false;
	private boolean showHp = false;
	private boolean invincible = false;
	private boolean canDie = true;

	private Color DAMAGEVALUE_COLOR = Color.red;

	private int numberXOffset = 15, numberYOffset = 15;
	private Point damageNumberPosition;
	private int DMG_NUM_DISPLAY_TIME = 35;
	private int dmgNumTimer = 0;
	private ArrayList<HealthModule.DamageNumber> damageNumbers = new ArrayList<HealthModule.DamageNumber>();

	private GameObject object;
	private Healthbar hpbar;
	
	public HealthModule(int maxhp) {
		setHealth(maxhp);
		setMaxHealth(maxhp);
	}

	public void hpLimit() {
		if (!canDie && health < 0) {
			health = 0;
		}
	}
	
	public void showHp(boolean b) {
		showHp = b;
		if (b) {
			hpbar = new Healthbar(125, 35);
		}
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

	public void dmgCooldown(int c) {dmgcooldown = c;}
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
	public Healthbar healthbar() {return hpbar;}
	public boolean isDead() {if (health <= 0 && canDie() && !invincible) {return true;} else {return false;}}
	
	public void update(GameObject object) {
		hpCheck();
		this.object = object;
		if (showHp) {hpbar.update((double) health, (double) maxHealth, object);}
		dmgNumTimer();
	}
	
	public void damage(int h) {
		if (!invulnerable && !invincible) {
			health -= h;
			if (object != null) {
				damageNumber(h);
			}
			invulnerable = true;
		}
	}

	public void heal(int h, boolean overheal) {
		if (health + h > maxHealth && overheal) {
			health += h;
		}
		else if (health + h > maxHealth && !overheal) {
			health = maxHealth;
		}
		else {
			health += h;
		}
	}

	private void dmgNumTimer() {
		if (dmgNumTimer > 0) {dmgNumTimer--;}
		if (dmgNumTimer <= 0) {
			if (damageNumbers.size() > 0) {
				damageNumbers.remove(0);
			}
		}
	}

	private static class DamageNumber {
		Point pos;
		int damage;

		DamageNumber(Point pos, int damage) {
			this.pos = pos;
			this.damage = damage;
		}

		void paint(Graphics g) {
			g.drawString(String.valueOf(damage), pos.x, pos.y);
		}
	}

	/** Generate a number showing how much damage was dealt above object. */
	private void damageNumber(int d) {
		Point pos = object.getDisplayCoordinate();
		damageNumberPosition = new Point(pos.x + numberXOffset, pos.y + numberYOffset);
			
		DamageNumber dn = new DamageNumber(damageNumberPosition, d);
		damageNumbers.add(dn);
			
		dmgNumTimer = DMG_NUM_DISPLAY_TIME;
	}

	public void paint(Graphics g) {
		if (showHp) {
			hpbar.paint(g);
		}
		if (damageNumbers.size() > 0) {
			g.setColor(DAMAGEVALUE_COLOR);
			for (HealthModule.DamageNumber dn : damageNumbers) {
				dn.paint(g);
			}
		}
	}
	
}
