package adventuregame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import worlds.ListWorld;

public class HUD {
	
	public ArrayList<HudObj> hb;
	public ArrayList<TField> tf;
	public ArrayList<HudList> hl;
	public ArrayList<HudText> ht;
	public ArrayList<HudBar> hbr;
	public boolean visible = false;
	private ListWorld world;
	public String id;
	public Font font;
	private Main frame;
	Player p;
	
	public HUD(ListWorld lw) {
		tf = new ArrayList<TField>();
		hb = new ArrayList<HudObj>();
		hl = new ArrayList<HudList>();
		ht = new ArrayList<HudText>();
		hbr = new ArrayList<HudBar>();
		world = lw;
		font = world.standard;
	}

	public void update() {
		if (visible == true) {
			for (int i = 0; hb.size() > i; i++) {
				hb.get(i).update();
				specificUpdate(hb.get(i));
			}
		}
		for (int i = 0; i < tf.size(); i++) {
			if (visible == true) {
				tf.get(i).setVisible(true);
			} else {
				tf.get(i).setVisible(false);
			}
		}
		for (int i = 0; i < hl.size(); i++) {
			if (visible == true) {
				hl.get(i).update();
			}
		}
		for (int i = 0; i < ht.size(); i++) {
			if (visible == true) {
				textUpdate(ht.get(i));
				if (ht.get(i).text == "rects") {
					world = world.getWorld();
					ht.get(i).text = String.valueOf(world.go.rects.size());
				}
			}
		}
		for (int i = 0; i < hbr.size(); i++) {
			barUpdate(hbr.get(i));
		}
	}
	
	public void specificUpdate(HudObj ho) {
		if (ho.id == "mode") {
			ho.text = "mode: " + world.m.ba.mode;
		}
		if (ho.id == "currentcolor") {
			ho.colord = world.rc.color;
		}
		if (ho.id == "gravity") {
			ho.addText("gravity: " + world.p.hasGravity());
		}
		if (ho.id == "invincible") {
			ho.addText("invincible: " + world.p.invincible);
		}
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		if (world.m.pressed && ho.hrect.contains(mouse)) {
			if (ho.id == "modhp") {
				for (int i = 0; i < hbr.size(); i++) {
					if (hbr.get(i).id == "hp") {
						
						p.setMaxHealth((int) (p.maxhealth + 20));
					}
				}
			} else if (ho.id == "dmgdebug") {
				
			} else if (ho.id == "shoot") {
				world.p.fire("right");
			}
		}
	}
	
	public void passPlayer(Player p) {
		this.p = p;
	}
	
	public void textUpdate(HudText ht) {
		if (ht.id == "debug") {
			ht.text = "";
		}
		if (ht.id == "debug2") {
			ht.text = "max" + String.valueOf(p.maxhealth);
		}
	}
	
	public void passWorld(ListWorld lw) {
		world = lw;
	}
	
	public void barUpdate(HudBar hb) {
		
		if (hb.getId().equals("hp")) {
			hb.passPlayer(p);
			hb.updatePlayer();
			
		}
		if (hb.getId().equals("energy")) {
			hb.passPlayer(p);
			hb.updateValues((int) world.p.energy, (int) world.p.maxenergy);
			hb.update();
		}
	}
	
	public void paint(Graphics g) {
		if (visible == true) {
			g.setFont(font);
			for (int i = 0; hb.size() > i; i++) {
				hb.get(i).paint(g);
			}
			for (int i = 0; i < tf.size(); i++) {
				tf.get(i).update(g);
				tf.get(i).paint(g);
			}
			for (int i = 0; i < ht.size(); i++) {
				ht.get(i).paint(g);
			}
			for (int i = 0; i < hl.size(); i++) {
				hl.get(i).paint(g);
			}
			for (int i = 0; i < hbr.size(); i++) {
				hbr.get(i).paint(g);
			}
		}
	}
	
}
