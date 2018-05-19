package adventuregame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Counter implements ActionListener {
	
	private Timer timer;
	int goal;
	int counter = 0;
	boolean done = false;
	String id;
	private boolean started = false;
	
	public Counter(int delay, int m, String s) {
		timer = new Timer(delay, this);
		goal = m;
		id = s;
	}
	
	public void start() {
		timer.start();
		started = true;
	}
	
	public boolean hasStarted() {
		return started;
	}
	
	public boolean isDone() {
		return done;
	}
	
	public void reset() {
		timer.stop();
		started = false;
		done = false;
		counter = 0;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (counter < goal) {
			counter++;
		}
		else if (counter == goal) {
			counter = -1;
			done = true;
		}
	}
}
