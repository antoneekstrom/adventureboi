package UI;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import adventuregame.GlobalData;
import adventuregame.Images;
import gamelogic.ObjectStorage;
import gamelogic.ObjectCreator;
import gamelogic.ObjectInspector;

public class CreativeUI extends GUI {

    UIText current;
    UIImage img;
    UIButton create;
    UIButton togglesize;
    UIButton inspect;
    UIButton console;
    static UIList clog;
    static UIList history;

    int centerX = GlobalData.getScreenDim().width / 2;
    int y0 = 500;
    int navbuttonoffset = 300;
    int buttonWidth = 150;

    int numberIncrease = 50;

    int logWidth = 800, logHeight = 240;
    int consoleWidth = 900, consoleHeight = 75;
    String consolePrefix = "input";

    Color LOG_BACKGROUND = new Color(255, 255, 255, 200);

    HashMap<String, BufferedImage> images;

    public CreativeUI() {
        super("Creative");
        images = Images.getImageHashMap("assets/items");
        
    }

    public boolean mouseOverConsole() {
        return console.checkMouse();
    }

    public void start() {
        addTitle("Edit");
        getObjectByText("Edit").get().y -= 150;

        //current object
        current = new UIText(getName(), "currentObject", true);
        current.setTag("currentObject");
        current.setFontSize(50);
        current.textColor(getUITextColor());
        current.get().y = 200;
        addObject(current);

        //enable/disable object creation
        create = new UIButton(getName(), "create on click:", false) {{
            get().setLocation(100, 100);
            setTag("toggleObjectCreation");
        }};
        applyGeneralStyle(create);
        addObject(create);

        //toggle object inspection/selection
        inspect = new UIButton(getName(), "inspect on click:", false) {{
            get().setLocation(100, 250);
            setTag("toggleObjectInspection");
        }};
        applyGeneralStyle(inspect);
        addObject(inspect);

        //next object
        UIButton next = new UIButton(getName(), "next", false);
        applyGeneralStyle(next);
        next.setTag("nextObject");
        next.autoAdjustBackgroundWidth(false);
        next.get().width = buttonWidth;
        next.get().setLocation(centerX + navbuttonoffset - next.get().width / 2, y0);
        addObject(next);
        
        //object image
        img = new UIImage(getName()) {
            @Override
            public void scroll(MouseWheelEvent e) {
                super.scroll(e);
                if (e.getWheelRotation() > 0) {
                    ObjectCreator.nextObject();
                }
                else {
                    ObjectCreator.previousObject();
                }
            }
            {
                get().setSize(250, 250);
                setImageSize(200, 200);
                setBackgroundColor(Color.white);
                setBorderColor(getParent().getUIBackgroundColor());
                get().setLocation(centerX - get().width / 2, y0 - getFullHeight() / 2);
            }
        };
        addObject(img);
        
        //previous object
        UIButton prev = new UIButton(getName(), "previous", false);
        applyGeneralStyle(prev);
        prev.autoAdjustBackgroundWidth(false);
        prev.get().width = buttonWidth;
        prev.setTag("prevObject");
        prev.get().setLocation(centerX - navbuttonoffset - prev.get().width / 2, y0);
        addObject(prev);

        //width
        UIButton width = new UIButton(getName(), "width", false) {
            @Override
            public void useInput() {
                ObjectCreator.width(Integer.parseInt(this.getInput()));
            }
            @Override
            public void scroll(MouseWheelEvent e) {
                super.scroll(e);
                if (e.getWheelRotation() < 0) {
                    ObjectCreator.width(ObjectCreator.width() + numberIncrease);
                }
                else {
                    ObjectCreator.width(ObjectCreator.width() - numberIncrease);
                }
                setText("width:" + ObjectCreator.width());
            }
            {
                this.takeInput(true);
                this.setInputPrefix("width");
                get().setLocation(100, 400);
            }
        };
        //height
        UIButton height = new UIButton(getName(), "height", false) {
            @Override
            public void useInput() {
                ObjectCreator.height(Integer.parseInt(this.getInput()));
            }
            @Override
            public void scroll(MouseWheelEvent e) {
                super.scroll(e);
                if (e.getWheelRotation() < 0) {
                    ObjectCreator.height(ObjectCreator.height() + numberIncrease);
                }
                else {
                    ObjectCreator.height(ObjectCreator.height() - numberIncrease);
                }
                setText("height:" + ObjectCreator.height());
            }
            {
                this.takeInput(true);
                this.setInputPrefix("height");
                get().setLocation(100, 550);
            }
        };
        //use custom size
        togglesize = new UIButton(getName(), "use size", false) {
            @Override
            public void leftMouseReleased() {
                ObjectCreator.toggleCustomSize();
            }
            {
                get().setLocation(100, 700);
            }
        };
        applyGeneralStyle(width);
        applyGeneralStyle(height);
        applyGeneralStyle(togglesize);
        addObject(width);
        addObject(height);
        addObject(togglesize);

        //remove object
        UIButton remove = new UIButton(getName(), "remove selected", false) {
            @Override
            public void leftMouseReleased() {
                if (ObjectInspector.selectedObject() != null) {
                    ObjectStorage.remove(ObjectInspector.selectedObject());
                }
            }
            {
                this.get().setLocation(100, 900);
            }
        };
        applyGeneralStyle(remove);
        addObject(remove);

        //console
        console = new UIButton(getName(), "enter command..", true) {
            @Override
            public void useInput() {
                Console.enter(getInput());
                setText("enter command..");
            }
            {
                takeInput(true);
                setInputPrefix(consolePrefix);
                autoAdjustBackground(false);
                this.get().setSize(consoleWidth, consoleHeight);
                this.get().y = GlobalData.getScreenDim().height - 120;
            }
        };
        applyGeneralStyle(console);
        addObject(console);

        //console log
        clog = new UIList(getName()) {

            @Override
            public UIObject getEntry(String text) {
                UIObject o = super.getEntry(text);
                o.textColor(parseColor(o));
                return o;
            }
            {
                this.get().setSize(console.getFullWidth() + 10, logHeight);
                this.setSpacing(0);
                this.setText("ConsoleLog");
                this.hasText(false);
                this.setBackgroundColor(LOG_BACKGROUND);
                this.textColor(Color.black);
                this.entry().setBackgroundColor(new Color(0, 0, 0, 0));
                this.entry().setFontSize(30f);
                this.handle().get().setSize(50, 100);
                this.handle().hoverColorChange(this.handle().getBackgroundColor().brighter());
                this.get().setLocation(GlobalData.getScreenDim().width - this.getFullWidth(), 0);
            }
        };
        addObject(clog);

        //console log
        history = new UIList(getName()) {

            @Override
            public UIObject getEntry(String text) {
                UIObject o = super.getEntry(text);
                o.textColor(parseColor(o));
                return o;
            }
            {
                this.get().setSize(console.getFullWidth() + 10, 250);
                this.setSpacing(0);
                this.setText("ConsoleLog");
                this.hasText(false);
                this.setBackgroundColor(LOG_BACKGROUND);
                this.textColor(Color.black);
                this.entry().setBackgroundColor(new Color(0, 0, 0, 0));
                this.entry().setFontSize(30f);
                this.handle().get().setSize(50, 100);
                this.handle().hoverColorChange(this.handle().getBackgroundColor().brighter());
            }
        };
        startRight();
    }

    private int getRightSideX(Rectangle r) {
        return GlobalData.getScreenDim().width - r.width - 100;
    }

    private void startRight() {
        reuseGuideline();
        setGuidelineY1(100);

        //set color
        addObject(new UIButton(getName(), "Choose Color", false) {
            @Override
            public void leftMouseReleased() {
                super.leftMouseReleased();
                UIManager.enableGUI("ColorPicker_ObjectColor");
            }
            {
                applyGeneralStyle(this);
                get().setLocation(getRightSideX(get()), getGuidelineY1());
                alignRight(true);
            }
        });
        
        //customize spawner 
        addObject(new UIButton(getName(), "Create Spawner", false) {
            @Override
            public void leftMouseReleased() {
                super.leftMouseReleased();
                UIManager.enableGUI("CreateSpawner");
            }
            {
                applyGeneralStyle(this);
                get().setLocation(getRightSideX(get()), getGuidelineY1());
                alignRight(true);
            }
        });

        //more settings
        addObject(new UIButton(getName(), "More Settings", false) {
            {
                applyGeneralStyle(this);
                get().setLocation(getRightSideX(get()), getGuidelineY1());
                alignRight(true);
            }
        });
    }

    public static void refreshLog() {
        clog.refreshList(Console.getLog());
        clog.handle().get().y = (int)clog.get().getMaxY() - clog.handle().get().height;
    }

    public void setVisible(boolean b) {
        super.setVisible(b);
    }

    private Color parseColor(UIObject o) {
        Color c = Color.black;
        String text = o.getText();
        String s = "";
        //extract color id
        if (text.startsWith(Console.colorRegex)) {
            s = text.substring(0, Console.colorLength);
            o.setText(text.replace(s, ""));
        }
        //Determine color
        if (s.equals(Console.LOG_RED)) {
            c = Color.red;
        }
        else if (s.equals(Console.LOG_GREEN)) {
            c = Color.green;
        }
        o.setHoverTextColor(c);

        return c;
    }

    public void update() {
        super.update();
        try {
            img.setImage(ObjectCreator.getPreview().getImage());
        } catch (Exception e) {e.printStackTrace();}
        current.setText(ObjectCreator.getCurrentObjectName());
        create.setText("create on click: " + ObjectCreator.isEnabled());
        inspect.setText("inspect on click: " + ObjectInspector.isEnabled());
        togglesize.setText("use size: " + ObjectCreator.customSize());
        history.get().setLocation(console.get().x - console.BORDER_THICKNESS - console.getBackgroundPadding() / 2, console.get().y + 20 - console.getFullHeight() * 2);
        clog.get().setLocation(console.get().x - console.BORDER_THICKNESS - console.getBackgroundPadding() / 2, console.get().y - console.getFullHeight() * 2 - 20);
    }

}
