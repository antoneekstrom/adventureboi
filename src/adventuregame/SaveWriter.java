package adventuregame;

import java.awt.Color;
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
import java.util.ArrayList;
import java.util.stream.Stream;

import gamelogic.NewCamera;
import gamelogic.NewObjectStorage;
import objects.NewObject;
import objects.ObjectTypes;

public class SaveWriter {
	private static BufferedWriter writer;
	static private File file;
	static private File directory;
	static private File[] levels;
	public static String line;
	private static int lnum;
	private static int lcount;
	
	public static void ping() {
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

	public static void loadLevel(String s) {
		setWorld(s);
		NewCamera.setCameraPos(new Point(0,0));
		GameEnvironment.getFrame().get().loadPlayers();
	}

	public static void setWorld(String s) {
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
		GameEnvironment.getFrame().get().setName(s);
		loadWorld(GameEnvironment.getFrame().get());
	}
	
	public static void createWorld(String s) {
		File f = new File(directory.getPath() + "/" + s + ".world");
		if (!f.exists() && !s.equals("") && !s.equals(" ")) {
			try {
				f.createNewFile();
				writer = new BufferedWriter(new FileWriter(f, true));
				System.out.println("newfile");
				write("-1550,607,3000,148,-14336,rectangle,boing\r\n");
				newLine();
				write("-424,374,250,250,-14336,solidstar, what is up dog");
			}
			catch (Exception e) {e.printStackTrace();}
		}
	}
	
	public static void deleteWorld(String s) {
		File f = new File(directory.getPath() + "/" + s + ".world");
		if (f.exists()) {
			try {
				f.delete();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setDirectory(File f) {
		directory = f;
	}
	
	static public File[] getDirWorlds() {
		return levels;
	}
	
	static public ArrayList<String> getWorldList() {
		findWorlds();
		ArrayList<String> l = new ArrayList<String>();
		for (int i = 0; i < getDirWorlds().length; i++) {
			String s = "";
			if (getDirWorlds()[i].getName().endsWith(".world")) {
				s = getDirWorlds()[i].getName().replace(".world", "");
				l.add(s);
			}
		}
		return l;
	}
	
	static public void findWorlds() {
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
	
	public static void writeList(GameObjects go) {
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
	
	public static void readLine(int n) {
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
	
	static int x,y,w,h;
	static String t = "";
	static String txt = "";
	static Color c;
	public static void loadWorld(GameEnvironment ge) {
		try {
			//clears current world
			NewObjectStorage.clearEnvironment();
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
					//type
					t = String.valueOf(a[5]);
				}
				if (a.length > 6) {
					//text
					txt = String.valueOf(a[6]);
				}
			}
			
			//create object and put it into game world
			NewObject o = new NewObject();
			o = ObjectTypes.getObject(t);
			if (!ObjectTypes.isKnownType(t) || ObjectTypes.isExcluded(t)) {
				o.setColor(c);
				o.setName(t);
				o.get().setSize(w, h);
			}
			o.setText(txt);
			o.get().setLocation(x, y);

			NewObjectStorage.add(o);
		}
	}
	
	public static void write(String s) {
		try {
			writer.write(s);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void newLine() {
		try {
			writer.newLine();
		} catch (Exception e) {e.printStackTrace();}
	}
	
}
