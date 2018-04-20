package adventuregame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import worlds.ListWorld;

public class SaveWriter {
	private BufferedWriter writer;
	private File file;
	public String line;
	private int lnum;
	private int lcount;
	
	public SaveWriter(String name) {
		file = new File(name + ".txt");
		System.out.println(file.getName());
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			writer = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void writeList(ArrayList<RectangleObject> list) {
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.close();
		} catch (IOException e) {e.printStackTrace();}
		
		for (int i = 0; i < list.size(); i++) {
			RectangleObject o = list.get(i);
			write(o.getX() + "," + o.getY() + "," + o.getWidth() + "," + o.getHeight());
		}	
	}
	
	public void readLine(int n) {
		lnum = n;
		try (Stream<String> lines = Files.lines(Paths.get(file.getName()))) {
		    line = lines.skip(lnum).findFirst().get();
		    System.out.println(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int countLines(String filename) throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(filename));
	    try {
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
	
	int x,y,w,h;
	public void loadWorld(ListWorld world) {
		try {
			world.rects.clear();
			lcount = countLines(file.getName());
		} catch (IOException e) {e.printStackTrace();}
		for (int i = 0; i < lcount; i++) {
			readLine(i);
			String[] a = line.split(",");
			for (int k = 0; k < a.length; k++) {
				x = Integer.parseInt(a[0]);
				y = Integer.parseInt(a[1]);
				w = Integer.parseInt(a[2]);
				h = Integer.parseInt(a[3]);
			}
			world.addRect(new Point(x, y), new Dimension(w, h), Color.ORANGE);
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
