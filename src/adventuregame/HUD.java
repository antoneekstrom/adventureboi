package adventuregame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import worlds.ListWorld;

public class HUD {
	
	public ArrayList<HudObj> hb;
	public ArrayList<TField> tf;
	public ArrayList<HudList> hl;
	public ArrayList<HudText> ht;
	public boolean visible = false;
	private ListWorld world;
	String id;
	
	public HUD(ListWorld lw) {
		tf = new ArrayList<TField>();
		hb = new ArrayList<HudObj>();
		hl = new ArrayList<HudList>();
		ht = new ArrayList<HudText>();
		world = lw;
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
	}
	
	public void specificUpdate(HudObj ho) {
		if (ho.id == "mode") {
			ho.text = "mode: " + world.m.ba.mode;
		}
		if (ho.id == "color") {
			ho.colord = world.rc.color;
		}
	}
	
	public void paint(Graphics g) {
		if (visible == true) {
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
		}
	}
	
}
