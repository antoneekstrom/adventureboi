package UI;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import adventuregame.GlobalData;
import adventuregame.Images;
import gamelogic.ObjectCreator;
import gamelogic.ObjectInspector;

public class CreativeUI extends GUI {

    UIText current;
    UIImage img;
    UIButton create;
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

    }

    public void update() {
        super.update();
        img.setImage(images.get(ObjectCreator.getCurrentObject()));
        current.setText(ObjectCreator.getCurrentObject());
        create.setText("create on click: " + ObjectCreator.isEnabled());
        inspect.setText("inspect on click: " + ObjectInspector.isEnabled());
    }

}
