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
	
	ArrayList<String> responsehistory = new ArrayList<String>();
	int responselimit = 7;
	
	int totalparameters;
	ArrayList<String> history = new ArrayList<String>();
	ArrayList<Integer> parameters = new ArrayList<Integer>();
	ArrayList<String> stringparameters = new ArrayList<String>();
	
	boolean showIndex = false;
	boolean saving = false;
	boolean visible = false;
	
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
			
			//spikeboi jump
			"jump",
			
			//get object values
			"xmin",
			"xmax",
			"ymin",
			"ymax",
			
			//save world
			"save",
			
			//type
			"gettype",
			"givetype",
			
			//get ai variables and logic
			"getai",
			
			//change text
			"changetext",
			
			//teleport player to x,y
			"teleport",
			
			//toggle player gravity
			"fly",
			
			//update objectrect/hitbox for selected object
			"updaterect",
			
			//update index of all objects
			"updateindex",
			
			//change place mode
			"mode",
			
			//heal player : addHealth(amount, [bool] surpass max)
			"healplayer",
			
			//energy
			"energy",
			
			//return object count in world
			"objectcount",
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
		totalparameters = stringparameters.size() + parameters.size();
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
				giveResponse("object " + parameters.get(0) + " has been vertically aligned with object " + parameters.get(1));
				lw.go.rects.get(parameters.get(0)).setY((int) lw.go.rects.get(parameters.get(1)).getObjectRect().getMinY() - lw.go.rects.get(0).getHeight());
			}
		}
		if (key.equals("showindex") || key.equals("si")) {
			if (showIndex) {
				showIndex = false;
				giveResponse("showIndex = false");
			}
			else {
				showIndex = true;
				giveResponse("showIndex = true");
			}
		}
		if (key.equals("remove")) {
			boolean b = true;
			if (checkIndexSize()) {
				lw.go.rects.get(parameters.get(0)).destroy();
				b = false;
				giveResponse("object " + parameters.get(0) + " has been removed");
			}
			if (b) {
				giveResponse("object " + selected + " has been removed");
				lw.go.rects.get(selected).destroy();
			}
			for (int i = 0; i < lw.go.rects.size(); i++) {
				lw.go.rects.get(i).updateIndex();
			}
		}
		if (key.equals("xmin")) {
			giveResponse(String.valueOf(lw.go.rects.get(selected).getObjectRect().getMinX()));
		}
		if (key.equals("jump")) {
			if (totalparameters == 0 && lw.go.rects.get(selected).ai != null) {
				lw.go.rects.get(selected).ai.jump(true);
			}
		}
		if (key.equals("xmax")) {
			giveResponse(String.valueOf(lw.go.rects.get(selected).getObjectRect().getMaxX()));
		}
		if (key.equals("ymin")) {
			giveResponse(String.valueOf(lw.go.rects.get(selected).getObjectRect().getMinY()));
		}
		if (key.equals("ymax")) {
			giveResponse(String.valueOf(lw.go.rects.get(selected).getObjectRect().getMaxY()));
		}
		if (key.equals("getrect") || key.equals("gr") || key.equals("rect")) {
			boolean b = true;
			if (checkIndexSize()) {
				int x = lw.go.rects.get(parameters.get(0)).getX();
				int y = lw.go.rects.get(parameters.get(0)).getY();
				int w = lw.go.rects.get(parameters.get(0)).getWidth();
				int h = lw.go.rects.get(parameters.get(0)).getHeight();
				giveResponse("rect-" + parameters.get(0) + ": " + x + "," + y + "," + w + "," + h);
				b = false;
			}
			if (b) {
				int x = lw.go.rects.get(selected).getX();
				int y = lw.go.rects.get(selected).getY();
				int w = lw.go.rects.get(selected).getWidth();
				int h = lw.go.rects.get(selected).getHeight();
				giveResponse("rect-" + selected + ": " + x + "," + y + "," + w + "," + h);
			}
		}
		if (key.equals("select")) {
			if (checkIndexSize()) {
				selected = parameters.get(0);
				giveResponse("selected:" + selected);
				lw.go.rects.get(selected).select();
			}
		}
		if (key.equals("addx")) {
			lw.go.rects.get(selected).setX(lw.go.rects.get(selected).getX() + parameters.get(0));
			giveResponse("x: " + (parameters.get(0) + lw.go.rects.get(selected).getX()));
		}
		if (key.equals("addy")) {
			lw.go.rects.get(selected).setY(lw.go.rects.get(selected).getY() + parameters.get(0));
			giveResponse("y: " + (parameters.get(0) + lw.go.rects.get(selected).getY()));
		}
		if (key.equals("addw")) {
			lw.go.rects.get(selected).setWidth(lw.go.rects.get(selected).getWidth() + parameters.get(0));
			lw.go.rects.get(selected).getObjectRect().width = lw.go.rects.get(selected).getWidth();
			giveResponse("w: " + (parameters.get(0) + lw.go.rects.get(selected).getWidth()));
		}
		if (key.equals("addh")) {
			giveResponse("h: " + (parameters.get(0) + lw.go.rects.get(selected).getHeight()));
			lw.go.rects.get(selected).setHeight(lw.go.rects.get(selected).getHeight() + parameters.get(0));
			lw.go.rects.get(selected).getObjectRect().height = lw.go.rects.get(selected).getHeight();
		}
		if (key.equals("setx")) {
			giveResponse("x: " + parameters.get(0));
			lw.go.rects.get(selected).setX(parameters.get(0));
		}
		if (key.equals("sety")) {
			giveResponse("y: " + parameters.get(0));
			lw.go.rects.get(selected).setY(parameters.get(0));
		}
		if (key.equals("setw")) {
			giveResponse("w: " + parameters.get(0));
			lw.go.rects.get(selected).setWidth(parameters.get(0));
		}
		if (key.equals("seth")) {
			giveResponse("h: " + parameters.get(0));
			lw.go.rects.get(selected).setHeight(parameters.get(0));
		}
		if (key.equals("gettype")) {
			giveResponse(lw.go.rects.get(selected).type);
		}
		if (key.equals("givetype")) {
			giveResponse("object " + selected + " is now of the type " + stringparameters.get(0));
			lw.go.rects.get(selected).givetype(stringparameters.get(0));
		}
		if (key.equals("showrect")) {
			giveResponse("show rects");
			for (int i = 0; i < lw.go.rects.size(); i++) {
				if (lw.go.rects.get(i).showrect) {
					lw.go.rects.get(i).showrect = false;
				} else {
					lw.go.rects.get(i).showrect = true;
				}
			}
		}
		if (key.equals("healplayer")) {
			if (parameters.size() < 1) {
				lw.p.health = lw.p.maxhealth;
			}
			else {
				lw.p.addHealth(parameters.get(0), Boolean.parseBoolean(stringparameters.get(1)));
				giveResponse("added " + parameters.get(0) + " health to player");
			}
		}
		if (key.equals("changetext")) {
			if (!hasParameters()) {
				giveResponse(String.valueOf(lw.go.rects.get(selected).getText()));
			}
			else if (stringparameters.size() > 0 && parameters.size() == 0) {
				System.out.println("text");
				if (lw.go.rects.get(selected).hasText) {
					String s = "";
					for (int i = 0; i < stringparameters.size(); i++) {
						s = s + " " + stringparameters.get(i);
					}
					lw.go.rects.get(selected).text(s);
					giveResponse("object " + selected + ":" + s);
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
		if (key.equals("fly")) {
			if (lw.p.hasGravity()) {
				lw.p.setGravity(false);
			}
			else {
				lw.p.setGravity(true);
			}
			giveResponse("gravity: " + lw.p.hasGravity());
		}
		if (key.equals("getai")) {
			System.out.println("ai");
			if (parameters.size() == 1 && checkIndexSize()) {
				if (lw.go.rects.get(0).ai != null) {
					giveResponse(lw.go.rects.get(parameters.get(0)).ai.getLogic());
				}
			}
			if (totalparameters == 0) {
				if (lw.go.rects.get(selected).ai != null) {
					giveResponse(lw.go.rects.get(selected).ai.getLogic());
				}
			}
		}
		if (key.equals("mode")) {
			if (stringparameters.size() == 1) {
				lw.m.ba.mode = stringparameters.get(0);
			}
			giveResponse(lw.m.ba.mode);
		}
		if (key.equals("save")) {
			saving = true;
			giveResponse("world saved to " + lw.name);
		}
		if (key.equals("updateindex")) {
			for (int i = 0; i < lw.go.rects.size(); i++) {
				lw.go.rects.get(i).updateIndex();
			}
			giveResponse("updated all object indexes");
		}
		if (key.equals("energy")) {
			energy();
		}
		if (key.equals("updaterect")) {
			giveResponse("rect with index" + selected + " updated");
			lw.go.rects.get(selected).updateObjectRect();
		}
		if (key.equals("objectcount")) {
			giveResponse("There are " + lw.go.rects.size() + " objects in this world");
		}
		stringparameters.clear();
		parameters.clear();
	}
	
	public void energy() {
		Player p = lw.p;
		
		if (stringparameters.size() == 0) {
			giveResponse("energy: " + Math.round(p.energy) + "/" + p.maxenergy + " + " + p.energyrate + "energy/tick");
			giveResponse("energy [max/current/rate/fill] [integer]");
		}
		if (totalparameters >= 2) {
			if (stringparameters.get(0).equals("max")) {
				p.maxenergy = parameters.get(0);
				giveResponse("max energy set to " + parameters.get(0));
			}
			if (stringparameters.get(0).equals("current")) {
				p.energy = parameters.get(0);
				giveResponse("energy set to " + parameters.get(0));
			}
			if (stringparameters.get(0).equals("rate")) {
				giveResponse("energy rate set to " + parameters.get(0));
				p.energyrate = parameters.get(0);
			}
			if (stringparameters.get(0).equals("fill")) {
				giveResponse("energy filled");
				p.energy = p.maxenergy;
			}
		}
	}
	
	public boolean hasParameters() {
		if (totalparameters < 1) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void giveResponse(String s) {
		response = s;
		responsehistory.add(response);
		if (responsehistory.size() > responselimit) {
			responsehistory.remove(0);
		}
	}
	
	public String getLatestResponse() {
		return responsehistory.get(responsehistory.size() - 1);
	}
	
	public void getResponse() {
		
	}

	public void enter(String s) {
		output = s;
		history.add(output);
		parse();
	}
	
}
