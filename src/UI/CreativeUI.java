package UI;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import adventuregame.GlobalData;
import adventuregame.Images;
import gamelogic.NewObjectStorage;
import gamelogic.ObjectCreator;
import gamelogic.ObjectInspector;

public class CreativeUI extends GUI {

    UIText current;
    UIImage img;
    UIButton create;
    UIButton togglesize;
    UIButton inspect;

    int centerX = GlobalData.getScreenDim().width / 2;
    int y0 = 500;
    int navbuttonoffset = 300;
    int buttonWidth = 150;

    HashMap<String, BufferedImage> images;

    public CreativeUI() {
        super("Creative");
        images = Images.getImageHashMap("assets/items");
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
        img = new UIImage(getName()) {{
            get().setSize(250, 250);
            setImageSize(200, 200);
            setBackgroundColor(Color.white);
            setBorderColor(getParent().getUIBackgroundColor());
            get().setLocation(centerX - get().width / 2, y0 - getFullHeight() / 2);
        }};
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
                    NewObjectStorage.remove(ObjectInspector.selectedObject());
                }
            }
            {
                this.get().setLocation(100, 900);
            }
        };
        applyGeneralStyle(remove);
        addObject(remove);
    }

    public void update() {
        super.update();
        img.setImage(images.get(ObjectCreator.getCurrentObject()));
        current.setText(ObjectCreator.getCurrentObject());
        create.setText("create on click: " + ObjectCreator.isEnabled());
        inspect.setText("inspect on click: " + ObjectInspector.isEnabled());
        togglesize.setText("use size: " + ObjectCreator.customSize());
    }

}
