package adventuregame;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import worlds.ListWorld;

public class ButtonAction {

	private Main frame;
	private ListWorld world;
	ArrayList<HUD> huds;
	SaveWriter sw;
	
	public int modeIndex = 0;
	public String mode = "rectangle";
	String[] modes = new String[] {
			"rectangle",
			"text",
			"spike",
			"health",
			"ultrahealth",
			"spikeboi",
			"donut",
			"star"
	};
	
	public ButtonAction(ListWorld w, Main f, HUD h) {
		world = w;
		frame = f;
		huds = new ArrayList<HUD>();
		huds.add(h);
	}

	public void changeMode() {
		
		modeIndex++;
		if (modeIndex >= modes.length) {
			modeIndex = 0;
		}
		mode = modes[modeIndex];
	}
	
	public void passSaveWriter(SaveWriter lw) {
		this.sw = lw;
	}
	
	public void getClick() {
		for (int k = 0; k < huds.size(); k++) {
			HUD hud = huds.get(k);
			ArrayList<HudObj>hb = hud.hb;
			if (hud.visible == true) {
				for (int i = 0; hb.size() > i; i++) {
					if (hb.get(i).mouseOver()) {
						System.out.println(hb.get(i).text);
						
						if (hb.get(i).text == "save stage" && hud.visible == true) {
							sw.writeList(world.go);
							
						}
						else if (hb.get(i).id == "quit" && hud.visible == true) {
							frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
							
						}
						else if (hb.get(i).id == "mode") {
							changeMode();
							
						}
						else if (hb.get(i).id == "world") {
							world.switchHud("levels");
							
						}
						else if (hb.get(i).id == "start") {
							world.setBackground(Color.ORANGE);
							world.switchHud("levels");
							
						}
						else if (hb.get(i).id == "back") {
							world.switchHud(world.lastHud);
							
						}
						else if (hb.get(i).id == "quit") {
							System.out.println("quit");
							frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						}
						else if (hb.get(i).id == "gravity") {
							if (world.p.hasGravity()) {
								world.p.setGravity(false);
							} else {
								world.p.setGravity(true);
							}
						}
						else if (hb.get(i).id == "invincible") {
							if (world.p.invincible) {
								world.p.invincible = false;
							} else {
								world.p.invincible = true;
							}
						}
						if (hb.get(i).id == "color") {
							System.out.println(hb.get(i).colord);
							world.rc.setColor(hb.get(i).colord);
							world.tc.setColor(hb.get(i).colord);
						}
						if(hb.get(i).id == "modhp") {
							System.out.println("pressed");
						}
					}
				}
			}
		}
	}
	
}
