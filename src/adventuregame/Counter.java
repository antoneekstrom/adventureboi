package adventuregame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Counter implements ActionListener {
	
	private Timer timer;
	int goal;
	boolean done = false;
	String id;
	private boolean started = false;
	
	double modifier = 0.1;
	double result = 1;
	
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
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (goal > 0) {
			goal--;
			if (result - modifier >= 0) {
				result -= modifier;
			}
		}
		else if (goal == 0) {
			goal = -1;
			done = true;
		}
	}
}
