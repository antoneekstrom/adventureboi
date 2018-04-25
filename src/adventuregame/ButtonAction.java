package adventuregame;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import worlds.ListWorld;

public class ButtonAction {

	private Main frame;
	private ListWorld world;
	private HUD hud;
	
	public int modeIndex = 0;
	public String mode = "rectangle";
	String[] modes = new String[] {
			"rectangle",
			"text"
	};
	
	public ButtonAction(ListWorld w, Main f, HUD h) {
		world = w;
		frame = f;
		hud = h;
	}

	public void changeMode() {
		
		modeIndex++;
		if (modeIndex >= modes.length) {
			modeIndex = 0;
		}
		mode = modes[modeIndex];
	}
	
	public void getClick() {
		ArrayList<HudObj> hb = hud.hb;
		if (hud.visible == true) {
			for (int i = 0; hb.size() > i; i++) {
				if (hb.get(i).mouseOver()) {
					System.out.println(hb.get(i).text);
					
					if (hb.get(i).text == "save stage" && hud.visible == true) {
						world.sw.writeList(world.go);
						
					} else if (hb.get(i).id == "quit" && hud.visible == true) {
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						
					} else if (hb.get(i).id == "mode") {
						changeMode();
						
					} else if (hb.get(i).id == "world") {
						world.sw.setWorld("lw", world);

					} else if (hb.get(i).id == "start") {
						world.setBackground(Color.ORANGE);
						world.switchHud("levels");
						
					} else if (hb.get(i).id == "backtomenu") {
						world.switchHud("menu");
					}
					if (hb.get(i).highlight == false) {
						System.out.println(hb.get(i).colord);
						world.rc.setColor(hb.get(i).colord);
						world.tc.setColor(hb.get(i).colord);
					}
				}
			}
		}
	}
	
}
