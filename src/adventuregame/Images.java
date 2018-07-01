package adventuregame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;

public class Images {

	//directories
	private static final String[] directories = new String[] {
		"assets/animated_sprites/angryshroom",
		"assets/animated_sprites/aboi/effects",
		"assets/animated_sprites/boicharge",
		"assets/animated_sprites/star",
		"assets/sprites",
	};

	public String[] getDirectories() {
		return directories;
	}

	//images
	private static HashMap<String, HashMap<String, BufferedImage>> images = new HashMap<String, HashMap<String, BufferedImage>>();

	//directories to imagepaths <directoryname, HashMap<filename, filepath>
	private static HashMap<String, HashMap<String,String>> paths = new HashMap<String, HashMap<String, String>>();

	/** Index images from all directories into 'paths' hashmap. */
	public static void indexAllImages() {
		for (String dir : directories) {
			indexDirectory(dir);
		}
	}

	/** Index all images in specified directory, and put them into a map in the 'paths' map. */
	private static void indexDirectory(String dirpath) {
		File dir = new File(dirpath);
		HashMap<String, String> map = new HashMap<String, String>();

		File[] files = dir.listFiles(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".png");
			}
		});
		Arrays.sort(files);

		for (File f : files) {
			map.put(getOnlyName(f), f.getPath());
		}
		paths.put(dir.getName(), map);
	}

	public static String getOnlyName(File f) {
		return f.getName().replace(".png", "");
	}

	public static void printPaths() {
		for (String s : paths.keySet()) {
			System.out.println(paths.get(s) +  "\\" + s);
		}
	}

	public static HashMap<String, BufferedImage> getImageHashMap(String dir) {
		HashMap<String, String> indexmap = new HashMap<String, String>();
		HashMap<String, BufferedImage> imagemap = new HashMap<String, BufferedImage>();
		indexDirectory(dir);
		try {
			for (String name : indexmap.keySet()) {
				String path = indexmap.get(name);
				imagemap.put(name.replace(".png", ""), ImageIO.read(new File(path + "//" + name)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imagemap;
	}

	/** Get all images in a folder. Uses full relative path (from game folder) */
	public static ArrayList<BufferedImage> getFolderImages(String dirname) {
		ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();
		for (String n : images.get(dirname).keySet()) {
			imgs.add(images.get(dirname).get(n));
		}

		return imgs;
	}
	
	/** Load images from 'paths' into 'images' */
	public static void loadAllImages() {
		for (Iterator<String> iterator = paths.keySet().iterator(); iterator.hasNext();) {
				try {
					String directoryName = iterator.next();
					HashMap<String, String> pathmap = paths.get(directoryName);
				} catch (Exception e) {e.printStackTrace();}
			}
	}


	/** Load images from a HashMap containing the name of an image and its path. */
	public static HashMap<String, BufferedImage> loadHashMap(HashMap<String, String> map) {
		HashMap<String, BufferedImage> imagemap = new HashMap<String, BufferedImage>();
		for (String s : map.keySet()) {
			try {
				String imagepath = map.get(s);
				imagemap.put(s, ImageIO.read(new File(imagepath)));
			}
			catch (Exception e) {e.printStackTrace();}
		}
		return imagemap;
	}

	public static boolean isIndexed(String s) {
		return images.containsKey(s);
	}

	public static BufferedImage getImage(String directory, String name) {
		if (!name.endsWith(".png")) {
			name = name + ".png";
		}
		return images.get(directory).get(name);
	}
}
