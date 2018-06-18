package UI;

import java.awt.Color;
import java.awt.Rectangle;

import adventuregame.GlobalData;
import adventuregame.SaveWriter;

public class LevelsUI extends GUI {

    private static UIList levels;
    int centerX = GlobalData.getScreenDim().width / 2;
    int listWidth = 700;
    int listHeight = 900;

    public static void refreshList() {
        String[] arr = new String[SaveWriter.getWorldList().size()];
        SaveWriter.getWorldList().toArray(arr);
        levels.refreshList(arr);
    }

    public LevelsUI() {
        super("Levels");
    }

    public void start() {
        addTitle("Custom Levels");
        getObjectByText("Custom Levels").get().y -= 180;
        //list
        levels = new UIList(getName()) {{
            setBox(new Rectangle(centerX - listWidth / 2, 130, listWidth, listHeight));
            handle().get().setSize(50, 100);

            setTag("levelList");
            setText("levelList");
            hasText(false);
            setSpacing(0);
            setBackgroundColor(Color.white);

            centerTextY(true);
            centerTextX(true);
            setFontSize(40);
            handle().hoverColorChange(handle().getBackgroundColor().brighter());
        }};
        addObject(levels);

        //refresh button
        UIButton refresh = new UIButton(getName(), "Refresh", false) {{
            get().setLocation((int)levels.get().getMaxX() + 50, levels.get().height);
            setTag("refreshLevels");
        }};
        applyGeneralStyle(refresh);
        addObject(refresh);
    }

    public void update() {
        super.update();
    }
}
