package adventuregame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

import UI.Healthbar;
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
	private boolean damageNumber = false;

	private Color DAMAGEVALUE_COLOR = Color.red;

	private String damageNumberText = "";
	private int numberXOffset = 15, numberYOffset = 15;
	private Point damageNumberPosition;
	private int DMG_NUM_DISPLAY_TIME = 35;
	private int dmgNumTimer = 0;

	private NewObject object;
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
			hpbar = new Healthbar(150, 50);
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
	public boolean isDead() {if (health < 0 && canDie() && !invincible) {return true;} else {return false;}}
	
	public void update(NewObject object) {
		hpCheck();
		this.object = object;
		if (showHp) {hpbar.update((double) health, (double) maxHealth, object);}
		dmgNumTimer();
	}
	
	public void decreaseHealth(int h) {
		if (!invulnerable && !invincible) {
			health -= h;
			invulnerable = true;
		}
		damageNumber(h);
	}

	private void dmgNumTimer() {
		if (dmgNumTimer > 0) {dmgNumTimer--;}
		if (dmgNumTimer <= 0) {damageNumber = false;}
	}

	/** Generate a number showing how much damage was dealt above object. */
	private void damageNumber(int d) {
		Point pos = object.getDisplayCoordinate();
		damageNumberPosition = new Point(pos.x + numberXOffset, pos.y + numberYOffset);
		damageNumberText = String.valueOf(d);
		damageNumber = true;
		dmgNumTimer = DMG_NUM_DISPLAY_TIME;
	}

	public void paint(Graphics g) {
		if (showHp) {
			hpbar.paint(g);
		}
		if (damageNumber) {
			g.setColor(DAMAGEVALUE_COLOR);
			g.drawString(damageNumberText, damageNumberPosition.x, damageNumberPosition.y);
		}
	}
	
}
