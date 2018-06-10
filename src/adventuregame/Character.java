package adventuregame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import UI.Statistics;

import java.util.List;

public class Character {
	
	public static File file = new File("data/player/character.properties");
	public static BufferedWriter writer;
	
	private static Inventory inventory;
	private static Statistics stats;
	
	private static boolean initialized = false;
	
	public Character() {
		
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			 writer = new BufferedWriter(new FileWriter(file, true));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		inventory = new Inventory();
		stats = new Statistics();
		initialized = true;
	}
	
	public static Inventory Inventory() {
		return inventory;
	}
	
	public static Statistics Stats() {
		return stats;
	}
	
	public static boolean initialized() {
		return initialized;
	}
	
	public static void write(String s) {
		try {
			writer.write(s);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToLine(int i, String s) throws IOException {
		List<String> lines = Files.readAllLines(file.toPath());
		lines.set(i, s);
		Files.write(file.toPath(), lines);
	}
	
	public static int findLine(String s) {
		int lcount = countLines(file.getPath());
		
		for (int i = 0; i < lcount; i++) {
			if (readLine(i).startsWith(s)) {
				return i;
			}
		}
		return 0;
	}
	
	public static String getData(String s) {
		int ln = findLine(s);
		String data = "";
		String l = readLine(ln);
		String[] a = l.split(":");
		if (a.length == 2) {
			data = a[1];
		}
		return data;
	}
	
	public static void backup() {
		File backup = new File("data/player/backup/backup1.properties");
		
		try {
			Files.copy(file.toPath(), backup.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Set data to String, clears previous */
	public static void setData(String key, String newdata) {
		int l = findLine(key);
		
		if (l != 0) {
			try {
				writeToLine(l, key + ":" + newdata);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int countLines(String filename) {
		try {
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(file.getPath()));
			int lines = 0;
			while (reader.readLine() != null) lines++;
			reader.close();
			return lines;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void newLine() {
		try {
			writer.newLine();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static String readLine(int n) {
		int lnum = n;
		String line = "";
		try (Stream<String> lines = Files.lines(Paths.get(file.getPath()))) {
		    line = lines.skip(lnum).findFirst().orElse(null);
		    return line;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
