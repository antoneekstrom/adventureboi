package adventuregame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import worlds.ListWorld;
import worlds.World;

public class Player extends Object {

	Point spawnpoint;
	
	//images and animation
	private BufferedImage playeractive;
	private BufferedImage playerstill;
	private BufferedImage playerright;
	private BufferedImage playerfall;
	private BufferedImage playerleft;
	private BufferedImage fire;
	private BufferedImage charge1;
	
	Animator auranimaton;
	Animator chargeanim;
	int ay = 0;
	private BufferedImage aura;
	private BufferedImage aura1;
	private BufferedImage aura2;
	private BufferedImage aura3;
	private BufferedImage chargeimg;
	private BufferedImage greenboi;
	
	private double aCounter = 0;
	private double ANIMSPEED = 3;
	public boolean enabled = false;
	private boolean animation = false;
	ListWorld lw;
	
	//energy
	double maxenergybase = 100;
	double maxenergy = maxenergybase;
	double maxenergybonus = 0;
	double energy = maxenergy;
	double energyrate = 0.4;
	boolean energyregen = true;
	
	//force
	double forcex, forcey;
	int xtranslate, ytranslate;
	double resistancex = 1;
	double resistancey = 2;
	int mass = 10;
	
	//fire
	boolean charging = false;
	boolean charged = false;
	
	double cmodifier = 0.04;
	int chargelimit = 3;
	double chargemultiplier = 1;
	double chargepercentage;
	
	int firerange = 400;
	int chargedrange = 700;
	int firevelocity = 7;
	int unchargedfiresize = 70;
	int firesize = 0;
	int firedamage = 20;
	
	int fireyoffset = 20;
	int firecm = 20;
	int firec = firecm;
	int firecost = 1;
	private String firedirection = "none";
	
	//stamina
	double maxstamina = 100;
	double stamina = maxstamina;
	double staminarate = 0.5;
	boolean staminaregen = true;
	
	int staminacooldown = 120;
	int staminacounter = 0;
	
	double sprintspeed = 2;
	double sprintmod = 1;
	boolean sprint = false;
	double sprintcost = 0.5;
	
	//hp
	double maxhealthbase = 100; 
	double maxhealth = maxhealthbase;
	double health = 100;
	int dmgcooldown = 50;
	boolean invulnerable = false;
	int invinciblecounter = 0;
	public boolean invincible = false;
	
	double maxhealthbonus = 0;
	
	//movement
	public String direction = "none";
	public float CALCMOV = 1;
	public int MOVSPEED = 14;
	public float MOVACC = 1.3f;
	public float BASEMOV = 1;
	public float MOVMOD = 1.05f;
	public float MOVMAX = 4;
	
	//jump
	public boolean jump = false;
	
	double jcalculated;
	double JACC = 1f;
	double JSPEED = 1.7;
	public int JFUEL = 0;
	public int JFUELMAX = 15;
	public boolean onground = false;
	
	boolean spritesLoaded = false;
	
	
	public Player(Main f, World w) {
		super(f, w);
		spawnpoint = new Point();
		
		try {
			playerstill = ImageIO.read(new File("assets/animated_sprites/aboi/manboji.png"));
			playerright = ImageIO.read(new File("assets/animated_sprites/aboi/manboji2.png"));
			playerfall = ImageIO.read(new File("assets/animated_sprites/aboi/manboji5.png"));
			playerleft = ImageIO.read(new File("assets/animated_sprites/aboi/manboji3.png"));
			fire = ImageIO.read(new File("assets/sprites/fire.png"));
			aura1 = ImageIO.read(new File("assets/animated_sprites/aura/aura1.png"));
			aura2 = ImageIO.read(new File("assets/animated_sprites/aura/aura2.png"));
			aura3 = ImageIO.read(new File("assets/animated_sprites/aura/aura3.png"));
			charge1 = ImageIO.read(new File("assets/animated_sprites/boicharge/charge1.png"));
			greenboi = ImageIO.read(new File("assets/boi/stamina_boi.png"));
		} catch (IOException e) {e.printStackTrace();}
		
		//effect animation
		auranimaton = new Animator(aura1);
		auranimaton.speed(10);
		auranimaton.addImage(aura1, 0);
		auranimaton.addImage(aura2, 1);
		auranimaton.addImage(aura3, 2);
		
		chargeanim = new Animator(charge1);
		chargeanim.speed(5);
		chargeanim.doesCycle(false);
		for (int i = 0; i < 11; i++) {
			try {
				chargeanim.addImage(ImageIO.read(new File("assets/animated_sprites/boicharge/charge" + i + ".png")), i);
			} catch (IOException e) {e.printStackTrace();}
		}
		chargeanim.addImage(greenboi, chargeanim.size());
		chargeanim.setIndexRange(0, chargeanim.size() - 2);
		spritesLoaded = true;
	}
	
	public void move() {
		if (direction == "right") {
			if (MOVACC * MOVMOD <= MOVMAX) {
				MOVACC *= MOVMOD;
			}
			CALCMOV = (float) (MOVSPEED * sprintmod);
			setX((int) (getX() + CALCMOV));

		} else if (direction == "left") {
			if (MOVACC * MOVMOD <= MOVMAX) {
				MOVACC *= MOVMOD;
			}
			CALCMOV = (float) (MOVSPEED * sprintmod);
			setX((int) (getX() - CALCMOV));

		} else if (direction == "none") {
			CALCMOV = BASEMOV;
		}
	}

	public void jump() {
		jcalculated = getGRAVITY() * JSPEED * JACC;
		if (jump == true && JFUEL != 0 && hasGravity() == true) {
			JFUEL--;
			setY((int) (getY() - jcalculated));
			
		} else if (jump == true && hasGravity() == false) {
			setY((int) (getY() - 20));
		}
		
		if (!onground == true || hasGravity() == false) {
			if (!(lw.cl.side.equals("under"))) {
				JFUEL = JFUELMAX;
			}
			onground = true;
		} else {
			onground = false;
		}
		
		if (direction == "down" && hasGravity() == false) {
			setY( (int) (getY() + 20));
		}
	}
	
	public void isEnabled(boolean b) {
		enabled = b;
		
	}
	
	public void setSize(int w, int h) {
		setWidth(w);
		setHeight(h);
		getObjectRect().setSize(w, h + 25); //55 without collisionCorrection() active
	}
	
	public void die() {
		setX(0);
		setY(0);
		setForce(0,0);
		health = maxhealth;
	}
	
	public void passWorld(ListWorld lw) {
		this.lw = lw;
	}
	
	public void fireCounter() {
		
		if (firec != firecm) {
			firec++;
		}
	}
	
	public void fireDirection(String s) {
		firedirection = s;
	}
	
	public void charging(boolean b) {
		charging = b;
	}
	
	public void charge() {
		if (charging && chargemultiplier + cmodifier <= chargelimit && useEnergy(firecost)) {
			chargemultiplier += cmodifier;
			energyregen = false;
			chargepercentage = Math.round(((chargemultiplier - 1) / (chargelimit - 1)) * 10);
		}
		else if (charging && chargemultiplier + cmodifier > chargelimit) {
			chargemultiplier = chargelimit;
			charged = true;
		}
		if (!charging && chargemultiplier != 1) {
			release();
		}
	}

	public void release() {
		fire(firedirection);
		chargemultiplier = 1;
		chargepercentage = 0;
		energyregen = true;
		chargeanim.setIndex(0);
		charged = false;
		chargeanim.setImageToIndex();
	}

	public void fire(String s) {
		if (firec == firecm) {
			firesize = (int) (unchargedfiresize * (chargemultiplier * 0.8));
			RectangleObject ro = new RectangleObject(lw.frame, lw);
			ro.giveHealthModule(100);
			ro.velocity = (int) (firevelocity * chargemultiplier);
			if (charged) {
				ro.range = (int) (chargedrange * chargemultiplier);
			}
			else {
				ro.range = (int) ((firerange * chargemultiplier));
			}
			ro.hm.setDamage((int) (firedamage * chargemultiplier));
			ro.charged = ((charged) ? (true) : (false));
			ro.setSize(firesize, firesize);
			if (s.equals("right")) {
				ro.setLocation((int) (getObjectRect().getCenterX() + firesize + 20), (int) (getY() - ((70 * (chargemultiplier * 0.8)) - 70 - fireyoffset)));
				ro.givetype("fire");
				ro.setDirection("right");
			}
			else if (s.equals("left")) {
				ro.setLocation((int) (getObjectRect().getCenterX() - (firesize * 2) - 20), (int) (getY() - ((70 * (chargemultiplier * 0.8)) - 70 - fireyoffset)));
				ro.givetype("fire");
				ro.setDirection("left");

			}

			ro.sprite(fire);
			lw.addRo(ro);
			firec = 0;
		}
	}
	
	public void force() {
		//resistance
		int rx = (int) (resistancex + (Math.pow(forcex, 1/4)) );
		int ry = (int) (resistancey + (Math.pow(forcey, 1/4)) );
		
		//x-axis
		if (forcex > 0) {
			if (forcex - rx < 0) {
				forcex = 0;
			}
			else {
				forcex -= rx;
			}
		}
		else {
			if (forcex + rx > 0) {
				forcex = 0;
			}
			else {
				forcex += rx;
			}
		}
		
		if (forcex > 0) {
			xtranslate = (int) ( (Math.sqrt(forcex)) + (Math.sqrt(forcex) * ( Math.sqrt(mass	) / 2) ) );
			setX(getX() + xtranslate);
		}
		else if (forcex < 0) {
			double f = -forcex;
			xtranslate = (int) ( (Math.sqrt(f)) + (Math.sqrt(f) * Math.sqrt(mass) / 2) );
			setX(getX() - xtranslate);
		}
		
		//y-axis
		if (forcey > 0) {
			if (forcey - ry < 0) {
				forcey = 0;
			}
			else {
				forcey -= ry;
			}
		}
		else {
			if (forcey + ry > 0) {
				forcey = 0;
			}
			else {
				forcey += ry;
			}
		}
		
		if (forcey > 0) {
			ytranslate = (int) ( (Math.sqrt(forcey)) + (Math.sqrt(forcey) * Math.sqrt(mass) / 2) );
			setY(getY() - ytranslate);
		}
		else if (forcey < 0) {
			double f = -forcey;
			ytranslate = (int) ( (Math.sqrt(f)) + (Math.sqrt(f) * Math.sqrt(mass) / 2) );
			setY(getY() + ytranslate);
		}
	}
	
	public void applyForce(double x, double y) {
		forcex += x;
		forcey += y;
	}
	
	public void setForce(double x, double y) {
		forcex = x;
		forcey = y;
	}
	
	public void checkInventory() {
		maxhealthbonus = 0;
		maxenergybonus = 0;
		maxhealth = maxhealthbase;
		maxenergy = maxenergybase;
		for (int i = 0; i < lw.inventory.size(); i++) {
			String s = lw.inventory.get(i);
			
			if (s.equals("donut")) {
				maxhealthbonus += 20;
			}
			if (s.equals("shroom")) {
				maxenergybonus += 20;
				System.out.println("shroom");
			}
		}
		maxenergy += maxenergybonus;
		maxhealth += maxhealthbonus;
	}

	public void collisionCorrection() {
		getObjectRect().y = (int) (getY() - 25);
	}
	
	public void setLocation(int nx, int ny) {
		setX(nx);
		setY(ny);
		getObjectRect().setLocation(nx, ny);
	}
	
	public void update() {
		animation();
		gravity();
		jump();
		setLocation(getX(), getY());
		updateObjectRect();
		calculateYVelocity();
		move();
		voidCheck();
		hpCheck();
		fireCounter();
		energy();
		stamina();
		invincible();
		force();
		charge();
	}
	
	public void invincible() {
		if (invinciblecounter > 0) {
			invinciblecounter--;
			invincible = true;
		}
		else if (invinciblecounter == 0) {
			invincible = false;
			invinciblecounter = -1;
		}
	}
	
	public void sprint(boolean b) {
		sprint = b;
	}
	
	public void stamina() {
		
		if (staminacounter > 0) {
			staminacounter--;
			
		}
		else if (staminacounter == 0) {
			stamina = 1;
			staminacounter = -1;
		}
		
		//sprint drain
		if (sprint && useStamina(sprintcost)) {
			sprintmod = sprintspeed;
			staminaregen = false;
		}
		else {
			sprintmod = 1;
			staminaregen = true;
		}
		
		//regen
		if (staminaregen && staminacounter == -1) {
			if (stamina + staminarate <= maxstamina) {
				stamina += staminarate;
			}
			else if (stamina + staminarate > maxstamina) {
				stamina = maxstamina;
			}
		}
		
		//cooldown
		if (stamina == 0) {
			staminacounter = staminacooldown;
			stamina = -1;
		}
		
	}
	
	public boolean useStamina(double s) {
		if (stamina - s >= 0) {
			stamina -= s;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void energy() {
		if (energyregen) {
			if (energy + energyrate <= maxenergy) {
				energy += energyrate;
			}
			else if (energy + energyrate > maxenergy) {
				energy = maxenergy;
			}
		}
	}
	
	public boolean useEnergy(double e) {
		if (energy - e >= 0) {
			energy -= e;
			return true;
		}
		else {
			return false;
		}
	}
	
	int deathdelay = 50;
	Point deathpos = new Point();
	public void hpCheck() {
		
		if (health <= 0) {
			health = 0;
			deathdelay--;
			setLocation((int)deathpos.getX(), (int)deathpos.getY());
			if (deathdelay <= 0) {
				setLocation(0,0);
				die();
				deathdelay = 100;
			}
		}
		else {
			deathpos.setLocation(getX(), getY());
		}
		
		if (invulnerable == true) {
			dmgcooldown -= 1;
			
			if (dmgcooldown <= 0) {
				invulnerable = false;
				dmgcooldown = 50;
			}
		}
	}
	
	public void damage(int d) {
		if (invulnerable == false && !invincible) {
			health = health - d;
			invulnerable = true;
		}
	}
	
	public void voidCheck() {
		if (getY() > 3000) {
			health = 0;
		}
	}
	
	public void setMaxHealth(int i) {
		maxhealth = i;
		health = maxhealth;
	}
	
	public void addHealth(int h, boolean b) {
		if (b) {
			health += h;
		}
		else {
			if (health + h <= maxhealth) {
				health += h;
			} else {
				if (health <= maxhealth) {
					health = maxhealth;
				}
			}
		}
	}
	
	public void animation() {
		//moving right
		if (direction == "right") {
			aCounter += ANIMSPEED * (CALCMOV / 4);
			if (aCounter < 10) {
				playeractive = playerright;
			}
			else if (aCounter > 10 && aCounter < 50) {
				playeractive = playerleft;
			}
			else if (aCounter > 50 && aCounter < 100) {
				playeractive = playerright;
			}
			else if (aCounter > 100) {
				aCounter = 0;
			}

		}
		//standing still
		if (direction == "none") {
			playeractive = playerstill;
			aCounter = 0;
		}
		//moving left
		if (direction == "left") {
			aCounter += ANIMSPEED * (CALCMOV / 4);
			if (aCounter < 10) {
				playeractive = playerleft;
			}
			else if (aCounter > 10 && aCounter < 50) {
				playeractive = playerright;
			}
			else if (aCounter > 50 && aCounter < 100) {
				playeractive = playerleft;
			}
			else if (aCounter > 100) {
				aCounter = 0;
			}
		}
		//falling
		if (onground == true) {
			playeractive = playerfall;	
		}
		
		//sit
		if (direction == "down") {
			playeractive = playerfall;
		}
		//effects
		if (playeractive.equals(playerfall)) {
			ay = getCy() + 10;
		}
		else {
			ay = getCy();
		}
		if (auranimaton != null && invincible) {
			auranimaton.update();
			aura = auranimaton.getSprite();
		}
		if (charging && chargeanim != null && spritesLoaded) {
			chargeanim.setIndex((int)chargepercentage);
			chargeanim.update();
			chargeimg = chargeanim.getSprite();
			animation = true;
		}
		if (sprint && stamina > 0 && !charging) {
			chargeanim.setIndex(chargeanim.size() - 1);
			chargeanim.update();
			chargeimg = chargeanim.getSprite();
			animation = true;
		}
		else {
			chargeanim.setIndex((int) chargepercentage);
			chargeanim.setImageToIndex();
			chargeimg = chargeanim.getSprite();
		}
		if (!sprint && !charging) {
			animation = false;
		}
	}
	
	public void setDirection(String d) {
		direction = d;
	}
	
	public void paint(Graphics g) {
		g.drawImage(playeractive, getCx(), getCy(), getWidth(), getHeight(), null);
		if (invincible) {
			g.drawImage(aura, getCx(), ay, getWidth(), getHeight(), null);
		}
		if (animation) {
			g.drawImage(chargeimg, getCx(), ay, getWidth(), getHeight(), null);
		}
		g.drawString(String.valueOf(forcex), getCx(), getCy() - 200);
	}
}
