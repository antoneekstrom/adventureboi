package adventuregame;

public class HealthModule {
	
	int hp, maxhp;
	int damage = 0;
	
	boolean invulnerable = false;
	int dmgcooldown = 10;
	
	public HealthModule(int mhp) {
		this.hp = mhp;
		maxhp = mhp;
	}
	
	public void setDamage(int i) {
		damage = i;
	}
	
	public void hpCheck() {
		
		if (invulnerable == true) {
			dmgcooldown -= 1;
			
			if (dmgcooldown <= 0) {
				invulnerable = false;
				dmgcooldown = 50;
			}
		}
	}
	
	public void update() {
		hpCheck();
	}
	
	public void decreaseHealth(int h) {
		hp = hp - h;
		if (invulnerable == false) {
			h = hp - h;
			invulnerable = true;
		}
	}
	
	public int getHealth() {
		return hp;
	}
	
}
