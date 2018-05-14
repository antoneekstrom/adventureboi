package adventuregame;

import java.awt.Color;
import java.awt.Graphics;

public class HealthModule {
	
	int hp, maxhp;
	int damage = 0;
	
	private boolean showHp = false;
	boolean invulnerable = false;
	int dmgcooldown = 20;
	int cooldowncounter = dmgcooldown;
	HudBar hb;
	
	public HealthModule(int mhp) {
		this.hp = mhp;
		maxhp = mhp;
	}
	
	public void setDamage(int i) {
		damage = i;
	}
	
	public void showHp() {
		showHp = true;
		hb = new HudBar(0, 0, 100, 50);
		hb.bg = Color.WHITE;
		hb.fg = Color.RED;
		hb.w = 100;
		hb.stats = true;
		updateBarHp();
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
	
	public boolean hpVisible() {
		return showHp;
	}
	
	public void update() {
		hpCheck();
	}
	
	public void updateBarHp() {
		if (hb != null) {
			if (hb.stats && showHp) {
				hb.updateValues(hp, maxhp);
				hb.update();
			}
		}
	}
	
	public void decreaseHealth(int h) {
		if (!invulnerable) {
			System.out.println(h);
			hp -= h;
			invulnerable = true;
		}
		updateBarHp();
	}
	
	public int getHealth() {
		return hp;
	}
	
	public void paint(Graphics g) {
		if (showHp) {
			hb.paint(g);
		}
	}
	
}
