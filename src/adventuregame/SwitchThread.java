package adventuregame;

import worlds.World;

public class SwitchThread extends Thread {
	
	public Main frame;
	public World startworld;
	public World endworld;
	
	public SwitchThread(Main f, World wstart, World wend) {
		startworld = wstart;
		endworld = wend;
		frame = f;
	}
	
	public void run() {
		try {
			System.out.println("thread started");
			endworld.removeAll();
			frame.remove(endworld);
			frame.add(startworld);
			frame.revalidate();
			frame.repaint();
		} catch (IllegalThreadStateException e) {
			e.printStackTrace();
		}
	}
}
