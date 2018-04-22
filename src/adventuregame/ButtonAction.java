package adventuregame;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

import worlds.ListWorld;

public class ButtonAction {

	private Main frame;
	private ListWorld world;
	
	public int modeIndex = 0;
	public String mode = "rectangle";
	String[] modes = new String[] {
			"rectangle",
			"text"
	};
	
	public ButtonAction(ListWorld w, Main f) {
		world = w;
		frame = f;
	}

	public void changeMode() {
		
		modeIndex++;
		if (modeIndex >= modes.length) {
			modeIndex = 0;
		}
		mode = modes[modeIndex];
	}
	
	public void getClick() {
		ArrayList<HudObj> hb = world.options.hb;
		if (world.options.visible == true) {
			for (int i = 0; hb.size() > i; i++) {
				if (hb.get(i).mouseOver()) {
					System.out.println(hb.get(i).text);
					
					if (hb.get(i).text == "save stage" && world.options.visible == true) {
						world.sw.writeList(world.go);
						
					} else if (hb.get(i).text == "quit" && world.options.visible == true) {
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						
					} else if (hb.get(i).id == "mode") {
						changeMode();
						
					} else if (hb.get(i).id == "world") {
						world.sw.setWorld("lw", world);
						
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
