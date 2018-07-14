package adventuregame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Images {

	//directories
	private static final String[] directories = new String[] {
		"assets/sprites",
		"assets/animated_sprites",
		"assets/animated_sprites/aboi/effects",
		"assets/animated_sprites/boicharge",
		"assets/animated_sprites/star"
	};

	public String[] getDirectories() {
		return directories;
	}

	//images
	private static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

	//paths to images <Name, Path>
	private static HashMap<String, String> paths = new HashMap<String, String>();

	/** Index images from all directories into 'paths' hashmap. */
	public static void indexAllImages() {
		for (String dir : directories) {
			indexDirectory(dir, paths);
		}
	}

	/** Index all images in specified directory, and put them into 'paths' hashmap. */
	private static void indexDirectory(String dirpath, HashMap<String, String> map) {
		File dir = new File(dirpath);

		File[] files = dir.listFiles(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".png");
			}
		});

		try {
			Arrays.sort(files);
			for (File f : files) {
				map.put(f.getName(), dir.getPath());
			}
		}
		catch (NullPointerException e) {e.printStackTrace();}

	}

	public static void printPaths() {
		for (String s : paths.keySet()) {
			System.out.println(paths.get(s) +  "\\" + s);
		}
	}

	public static HashMap<String, BufferedImage> getImageHashMap(String dir) {
		HashMap<String, String> indexmap = new HashMap<String, String>();
		HashMap<String, BufferedImage> imagemap = new HashMap<String, BufferedImage>();
		indexDirectory(dir, indexmap);
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
	public static ArrayList<BufferedImage> getFolderImages(String dirpath) {
		HashMap<String, String> map = new HashMap<String, String>();
		indexDirectory(dirpath, map);
		ArrayList<BufferedImage> imgs = new ArrayList<BufferedImage>();
		try {
			for (String s : map.keySet()) {
				imgs.add(ImageIO.read(new File(map.get(s) + "\\" + s)));
			}
		} catch (Exception e) {}
		return imgs;
	}
	
	/** Load images from 'paths' into 'images' */
	public static void loadAllImages() {
		try {
			for (String name : paths.keySet()) {
				String path = paths.get(name);
				images.put(name, ImageIO.read(new File(path + "\\" + name)));
			}
		} catch (Exception e) {e.printStackTrace();}
	}

	public static boolean isIndexed(String s) {
		return images.containsKey(s);
	}

	public static BufferedImage getImage(String key) {
		if (!key.endsWith(".png")) {
			key = key + ".png";
		}
		return images.get(key);
	}
}
