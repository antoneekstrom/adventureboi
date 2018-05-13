package adventuregame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import worlds.ListWorld;

public class SaveWriter {
	private BufferedWriter writer;
	private File file;
	private File directory;
	private File[] levels;
	public String line;
	private int lnum;
	private int lcount;
	
	public void ping() {
		System.out.println("ping");
	}
	
	public SaveWriter(String name) {
		file = new File("data/levels/" + name + ".world");
		System.out.println(file.getPath());
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			writer = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void setWorld(String s, ListWorld w) {
		file = new File("data/levels/" + s + ".world");
		System.out.println(file.getPath());
		try {
			if (!file.exists()) {
				file.createNewFile();
				writer = new BufferedWriter(new FileWriter(file, true));
				System.out.println("newfile");
				write("-1550,607,3000,148,-14336,rectangle,boing\r\n");
				newLine();
				write("-424,374,250,250,-14336,solidstar, what is up dog");
			}
			else {
				System.out.println("write");
				writer = new BufferedWriter(new FileWriter(file, true));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		w.setName(s);
		loadWorld(w);
	}
	
	public void setDirectory(File f) {
		directory = f;
	}
	
	public File[] getDirWorlds() {
		return levels;
	}
	
	public void findWorlds() {
		directory = new File("data/levels");
		levels = directory.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return name.endsWith(".world");
			}
		});
	}
	
	public String getWorld() {
		return file.getName();
	}
	
	public void writeList(GameObjects go) {
		System.out.println("stage saved to " + file.getPath());
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.close();
		} catch (IOException e) {e.printStackTrace();}
		
		for (int i = 0; i < go.rects.size(); i++) {
			RectangleObject o = go.rects.get(i);
			if (!(o.getWidth() == 0) || !(o.getHeight() == 0)) {
				write(o.getX() + "," + o.getY() + "," + o.getWidth() + "," + o.getHeight() + "," + o.getCOLOR().hashCode());
				
				//additional properties
				if (o.type != null && o.type != "") {
					write("," + o.type);
				}
				else {
					write(",none");
				}
				if (o.hasText) {
					write("," + o.getText());
				} else {
					write(",boing");
				}
				newLine();
			}
		}	
	}
	
	public void readLine(int n) {
		lnum = n;
		try (Stream<String> lines = Files.lines(Paths.get(file.getPath()))) {
		    line = lines.skip(lnum).findFirst().get();
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
	String t = "";
	String txt = "";
	Color c;
	public void loadWorld(ListWorld world) {
		try {
			//clears current world
			world.go.rects.clear();
			world.cl.collisions.clear();
			lcount = countLines(file.getPath());
		} catch (IOException e) {e.printStackTrace();}
		//parse rectangleobject from .world file
		for (int i = 0; i <= lcount; i++) {
			readLine(i);
			if (!(line.contains(","))) {
				line = "0,0,0,0,-1,rectangle";
			}
			String[] a = line.split(",");
			for (int k = 0; k < a.length; k++) {
				x = Integer.parseInt(a[0]);
				y = Integer.parseInt(a[1]);
				w = Integer.parseInt(a[2]);
				h = Integer.parseInt(a[3]);
				c = Color.decode(a[4]);
				
				//additional properties
				if (a.length > 5) {
					t = String.valueOf(a[5]);
				}
				if (a.length > 6) {
					txt = String.valueOf(a[6]);
				}
			}
			
			//create object and put it into game world
			RectangleObject ro = new RectangleObject(world.frame, world);
			ro.setLocation(x, y);
			ro.setSize(w, h);
			ro.setCOLOR(c);
			if (!(t.equals(""))) {
				if (!t.equals("rectangle")) {
					ro.hasImg = true;
				}
				ro.givetype(t);
			}
			if (!txt.equals("")) {
				ro.text(txt);
			}
			world.addRo(ro);
			
			//world.addRect(new Point(x, y), new Dimension(w, h), c);
		}
	}
	
	public void write(String s) {
		try {
			writer.write(s);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void newLine() {
		try {
			writer.newLine();
		} catch (Exception e) {e.printStackTrace();}
	}
	
}
