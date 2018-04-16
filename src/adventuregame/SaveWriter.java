package adventuregame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveWriter {
	private BufferedWriter writer;
	private BufferedReader reader;
	private File file;
	
	public SaveWriter(File f) {
		file = f;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			writer = new BufferedWriter(new FileWriter(file));
			reader = new BufferedReader(new FileReader(file));
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void writeList(ArrayList<RectangleObject> list) {
		for (int i = 0; i < list.size(); i++) {
			RectangleObject o = list.get(i);
			write(o.getX() + "," + o.getY() + "," + o.getWidth() + "," + o.getHeight());
		}
	}
	
	public void write(String s) {
		try {
			writer.write(s);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
