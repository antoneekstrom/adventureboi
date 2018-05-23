package adventuregame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Inventory {
	
	private boolean updateRequest = false;

	public String[] getInventory() {
		String i = Character.getData("inventory");
		return i.split(",");
	}
	
	public int itemCount(String s) {
		String[] inv = getInventory();
		int c = 0;
		for (int i = 0; i < inv.length; i++) {
			if (inv[i].equals(s)) {
				c++;
			}
		}
		return c;
	}
	
	public void requestUpdate() {
		updateRequest = true;
	}
	
	public boolean updateRequest() {
		return updateRequest;
	}
	
	public void fullFillRequest() {
		updateRequest = false;
	}
	
	public ArrayList<String> get() {
		ArrayList<String> inv = new ArrayList<>(Arrays.asList(getInventory()));
		return inv;
	}
	
	public void addItem(String s) {
		int lnum = Character.findLine("inventory");
		String data = Character.readLine(lnum);
		
		try {
			Character.writeToLine(lnum, data + s + ",");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
