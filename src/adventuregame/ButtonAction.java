package adventuregame;

import java.awt.event.WindowEvent;
import java.util.ArrayList;

import worlds.ListWorld;

public class ButtonAction {

	private Main frame;
	private ListWorld world;
	
	
	public ButtonAction(ListWorld w, Main f) {
		world = w;
		frame = f;
	}
	
	public void getClick() {
		ArrayList<HudObj> hb = world.options.hb;
		for (int i = 0; hb.size() > i; i++) {
			if (hb.get(i).mouseOver()) {
				System.out.println(hb.get(i).text);
				
				if (hb.get(i).text == "save stage" && world.options.visible == true) {
					world.sw.writeList(world.rects);
					
				} else if (hb.get(i).text == "quit" && world.options.visible == true) {
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				}
			}
		}
	}
	
}
