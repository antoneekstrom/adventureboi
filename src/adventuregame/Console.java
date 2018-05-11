package adventuregame;

import java.util.ArrayList;

import worlds.ListWorld;

public class Console {
	
	ListWorld lw;
	TypeListener tl;
	
	int currentIndex = 0;
	String output;
	int selected = 0;
	private String response = "";
	String interpretation = "";
	ArrayList<String> history = new ArrayList<String>();
	ArrayList<Integer> parameters = new ArrayList<Integer>();
	ArrayList<String> stringparameters = new ArrayList<String>();
	
	boolean showIndex = false;
	
	String key = "none";
	
	String[] keys = new String[] {
			//aligns two objects to the same axis-value
			"alignx",
			"aligny",
			
			//shows index values of all objects
			"showindex",
			"si",
			
			//remove object
			"remove",
			
			//get rectangle values of object
			"getrect",
			"gr",
			"rect",
			
			//show objectrect
			"showrect",
			
			//select object
			"select",
			
			//set object x,y,w,h
			"setx",
			"sety",
			"setw",
			"seth",
			
			//add to x,y,w,h
			"addx",
			"addy",
			"addw",
			"addh",
			
			//get object values
			"xmin",
			"xmax",
			"ymin",
			"ymax",
			
			//type
			"gettype",
			"givetype",
			
			//teleport player to x,y
			"teleport",
			
			//update objectrect/hitbox for selected object
			"updaterect",
			
			//update index of all objects
			"updateindex",
	};
	
	public Console(ListWorld lw, TypeListener tl) {
		this.lw = lw;
		this.tl = tl;
	}
	
	public String getOutput() {
		if (history.size() != 0) {
			return history.get(history.size() - 1);
		}
		else {
			return null;
		}
	}
	
	public String getResponse() {
		return response;
	}
	
	public String getCurrent() {
		if (!history.isEmpty()) {
			return history.get(currentIndex);
		}
		else {
			return null;
		}
	}
	
	public void next() {
		if (currentIndex + 1 < history.size()) {
			currentIndex += 1;
			tl.text = getCurrent();
		}
		else if (history.size() == 1) {
			currentIndex = 0;
			tl.text = getCurrent();
		}
	}

	public void prev() {
		if (currentIndex - 1 >= 0) {
			currentIndex -= 1;
			tl.text = getCurrent();
		}
	}

	/** parse console input and put parameters (if present) into arraylist */
	public void parse() {

		key = "none";
		//find keyword in output string
		for (int i = 0; i < keys.length; i++) {
			if (output.contains(keys[i])) {
				key = keys[i];
				
				//remove keyword from output string
				interpretation = output.replace(keys[i], "");
				
				//find parameters
				String[] il = interpretation.split(" ");
				
				//convert parameter to integer
				for (int k = 0; k < il.length; k++) {
					if (!il[k].equals("")) {
						try {
							parameters.add( Integer.parseInt( il[k]) );
						}
						catch (NumberFormatException e) {
							e.printStackTrace();
						}
					}
					
				}
				//parameterlist as string if needed
				for (int k = 0; k < il.length; k++) {
					if (!il[k].equals("")) {
						stringparameters.add( il[k] );
					}
				}
			}
		}
		action();
	}
	
	/** compare RectangleObject arraylist to parameter size, returns true if parameter is lower than arraylist size */
	public boolean checkIndexSize() {
		boolean b = false;
		for (int i = 0; i < parameters.size(); i++) {
			if (parameters.get(i) < lw.go.rects.size()) {
				b = true;
			}
		}
		if (b) {
			return true;
		}
		else {
			return false;
		}
	}

	/** Makes things happen if console input is recognized as a command */
	public void action() {
		//determine outcome
		if (key.equals("alignx")) {
			//action
			if (checkIndexSize()) {
				lw.go.rects.get(parameters.get(0)).setX(lw.go.rects.get(parameters.get(1)).getX());
			}
		}
		if (key.equals("aligny")) {
			//action
			if (checkIndexSize()) {
				lw.go.rects.get(parameters.get(0)).setY((int) lw.go.rects.get(parameters.get(1)).getObjectRect().getMinY() - lw.go.rects.get(0).getHeight());
			}
		}
		if (key.equals("showindex") || key.equals("si")) {
			if (showIndex) {
				showIndex = false;
			}
			else {
				showIndex = true;
			}
		}
		if (key.equals("remove")) {
			boolean b = true;
			if (checkIndexSize()) {
				lw.go.rects.get(parameters.get(0)).destroy();
				b = false;
			}
			if (b) {
				lw.go.rects.get(selected).destroy();
			}
			for (int i = 0; i < lw.go.rects.size(); i++) {
				lw.go.rects.get(i).updateIndex();
			}
		}
		if (key.equals("xmin")) {
			response = String.valueOf(lw.go.rects.get(selected).getObjectRect().getMinX());
		}
		if (key.equals("xmax")) {
			response = String.valueOf(lw.go.rects.get(selected).getObjectRect().getMaxX());
		}
		if (key.equals("ymin")) {
			response = String.valueOf(lw.go.rects.get(selected).getObjectRect().getMinY());
		}
		if (key.equals("ymax")) {
			response = String.valueOf(lw.go.rects.get(selected).getObjectRect().getMaxY());
		}
		if (key.equals("getrect") || key.equals("gr") || key.equals("rect")) {
			boolean b = true;
			if (checkIndexSize()) {
				int x = lw.go.rects.get(parameters.get(0)).getX();
				int y = lw.go.rects.get(parameters.get(0)).getY();
				int w = lw.go.rects.get(parameters.get(0)).getWidth();
				int h = lw.go.rects.get(parameters.get(0)).getHeight();
				response = x + "," + y + "," + w + "," + h;
				b = false;
			}
			if (b) {
				int x = lw.go.rects.get(selected).getX();
				int y = lw.go.rects.get(selected).getY();
				int w = lw.go.rects.get(selected).getWidth();
				int h = lw.go.rects.get(selected).getHeight();
				response = x + "," + y + "," + w + "," + h;
			}
		}
		if (key.equals("select")) {
			if (checkIndexSize()) {
				selected = parameters.get(0);
				response = "selected:" + selected;
				lw.go.rects.get(selected).select();
			}
		}
		if (key.equals("addx")) {
			lw.go.rects.get(selected).setX(lw.go.rects.get(selected).getX() + parameters.get(0));
		}
		if (key.equals("addy")) {
			lw.go.rects.get(selected).setY(lw.go.rects.get(selected).getY() + parameters.get(0));
		}
		if (key.equals("addw")) {
			lw.go.rects.get(selected).setWidth(lw.go.rects.get(selected).getWidth() + parameters.get(0));
			lw.go.rects.get(selected).getObjectRect().width = lw.go.rects.get(selected).getWidth();
		}
		if (key.equals("addh")) {
			lw.go.rects.get(selected).setHeight(lw.go.rects.get(selected).getHeight() + parameters.get(0));
			lw.go.rects.get(selected).getObjectRect().height = lw.go.rects.get(selected).getHeight();
		}
		if (key.equals("setx")) {
			lw.go.rects.get(selected).setX(parameters.get(0));
		}
		if (key.equals("sety")) {
			lw.go.rects.get(selected).setY(parameters.get(0));
		}
		if (key.equals("setw")) {
			lw.go.rects.get(selected).setWidth(parameters.get(0));
		}
		if (key.equals("seth")) {
			lw.go.rects.get(selected).setHeight(parameters.get(0));
		}
		if (key.equals("gettype")) {
			response = lw.go.rects.get(selected).type;
		}
		if (key.equals("givetype")) {
			lw.go.rects.get(selected).givetype(stringparameters.get(0));
		}
		if (key.equals("showrect")) {
			response = "show rects";
			for (int i = 0; i < lw.go.rects.size(); i++) {
				if (lw.go.rects.get(i).showrect) {
					lw.go.rects.get(i).showrect = false;
				} else {
					lw.go.rects.get(i).showrect = true;
				}
			}
		}
		if (key.equals("teleport")) {
			if (parameters.size() >= 2) {
				lw.p.setLocation(parameters.get(0), parameters.get(1));
			}
			else if (parameters.size() == 0) {
				lw.p.setLocation(lw.go.rects.get(selected).getX(), (int) lw.go.rects.get(selected).getObjectRect().getMaxY());
			}
			
		}
		if (key.equals("updateindex")) {
			for (int i = 0; i < lw.go.rects.size(); i++) {
				lw.go.rects.get(i).updateIndex();
			}
		}
		if (key.equals("updaterect")) {
			response = "rect index " + selected + "updated";
			lw.go.rects.get(selected).updateObjectRect();
		}
		stringparameters.clear();
		parameters.clear();
	}

	public void enter(String s) {
		output = s;
		history.add(output);
		parse();
	}
	
}
