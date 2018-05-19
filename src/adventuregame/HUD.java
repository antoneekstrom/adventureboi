package adventuregame;

import java.awt.Color;
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
	public ArrayList<List> lists;
	public ArrayList<InfoBox> ib;
	public boolean visible = false;
	private ListWorld world;
	public String id;
	public Font font;
	private Main frame;
	Player p;
	SaveWriter sw;
	
	public HUD(ListWorld lw) {
		tf = new ArrayList<TField>();
		hb = new ArrayList<HudObj>();
		hl = new ArrayList<HudList>();
		ht = new ArrayList<HudText>();
		hbr = new ArrayList<HudBar>();
		lists = new ArrayList<List>();
		ib = new ArrayList<InfoBox>();
		
		world = lw;
		font = world.standard;
	}
	
	public void passSw(SaveWriter sw) {
		this.sw = sw;
	}

	public void update() {
		if (visible == true) {
			for (int i = 0; hb.size() > i; i++) {
				hb.get(i).update();
				specificUpdate(hb.get(i));
			}
		}
		for (int i = 0; i < ht.size(); i++) {
			if (visible == true) {
				textUpdate(ht.get(i));
				ht.get(i).update();
				if (ht.get(i).text == "rects") {
					world = world.getWorld();
					ht.get(i).text = String.valueOf(world.go.rects.size());
				}
			}
		}
		for (int i = 0; i < hbr.size(); i++) {
			barUpdate(hbr.get(i));
		}
		for (int i = 0; i < lists.size(); i++) {
			lists.get(i).update();
			for (int k = 0; k < lists.get(i).list.size(); k++) {
				lists.get(i).list.get(k).update();
			}
			listUpdate(lists.get(i));
		}
		for (int i = 0; i < ib.size(); i++) {
			infoupdate(ib.get(i));
		}
		hudUpdate();
	}
	
	public void infoupdate(InfoBox ib) {
		ib.getText("energymax").setText(String.valueOf("max energy: " + world.p.maxenergy));
		ib.getText("energyrate").setText("energyrate: " + world.p.energyrate + "e/tick");
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
		if (ho.id == "newlevel" && world.typelistener.getSource().equals("createlevel")) {
			if (world.typelistener.getEnabled()) {
				ho.color = ho.color2;
			}
			
			if (!world.typelistener.text.equals("")) {
				ho.text = world.typelistener.text;
			}
			else {
				ho.text = "new level";
			}
			if (world.currentHud.equals("levels") && world.typelistener.hasNewOutput()) {
				world.typelistener.newOutput(false);
				sw.createWorld(world.typelistener.output);
				for (int i = 0; i < lists.size(); i++) {
					if (lists.get(i).id.equals("levels")) {
						lists.get(i).addIdList(sw.getWorldList());
						lists.get(i).addEntryList(sw.getWorldList());
						lists.get(i).fill();
					}
				}
			}
		}
		if (ho.id == "deletelevel" && world.typelistener.getSource().equals("deletelevel")) {
			if (world.typelistener.getEnabled()) {
				ho.color = ho.color2;
			}
			
			if (!world.typelistener.text.equals("")) {
				ho.text = world.typelistener.text;
			}
			else {
				ho.text = "delete";
			}
			if (world.currentHud.equals("levels") && world.typelistener.hasNewOutput()) {
				world.typelistener.newOutput(false);
				sw.deleteWorld(world.typelistener.output);
				for (int i = 0; i < lists.size(); i++) {
					if (lists.get(i).id.equals("levels")) {
						lists.get(i).addIdList(sw.getWorldList());
						lists.get(i).addEntryList(sw.getWorldList());
						lists.get(i).fill();
					}
				}
			}
		}
	}
	
	public void hudUpdate() {
		if (id.equals("console")) {
			if (world.typelistener.c.visible) {
				setVisible(true);
			}
			else {
				setVisible(false);
			}
		}
		if (id.equals("menu") && world.name.equals("menu.world") && !world.currentHud.equals("levels")) {
			world.switchHud("menu");
			world.p.enabled = false;
			world.stopPlayerController();
		}
		if (id.equals("actualhud") && !world.currentHud.equals("menu") && !world.currentHud.equals("levels") && !world.currentHud.equals("options") && !world.currentHud.equals("stats")) {
			setVisible(true);
		}
	}
	
	public void passPlayer(Player p) {
		this.p = p;
	}
	
	public void setVisible(boolean b) {
		visible = b;
	}
	
	public void listUpdate(List l) {
		if (l.id == "response") {
			for (int i = 0; i < l.list.size(); i++) {
				if (i < world.typelistener.c.responsehistory.size()) {
					l.list.get(i).text = world.typelistener.c.responsehistory.get(i);
				}
			}
		}
		l.visible = visible;
	}
	
	public void textUpdate(HudText ht) {
		if (ht.id == "debug") {
			ht.text = String.valueOf(Math.round(world.p.forcex) + "," + Math.round(world.p.forcey));
		}
		if (ht.id == "debug2") {
			ht.text = "max" + String.valueOf(p.maxhealth);
		}
		if (ht.id == "console") {
			ht.text = ">>" + world.typelistener.text;
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
		if(hb.getId().equals("stamina")) {
			hb.passPlayer(p);
			hb.updateValues((int) world.p.stamina, (int) world.p.maxstamina);
			hb.update();
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
			for (int i = 0; i < ht.size(); i++) {
				ht.get(i).paint(g);
			}
			for (int i = 0; i < hbr.size(); i++) {
				hbr.get(i).paint(g);
			}
			for (int i = 0; i < lists.size(); i++) {
				lists.get(i).paint(g);
			}
			for (int i = 0; i < ib.size(); i++) {
				ib.get(i).paint(g);
			}
		}
	}
	
}
