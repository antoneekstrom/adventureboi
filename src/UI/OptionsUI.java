package UI;

public class OptionsUI extends GUI {

    UIButton saveplayer, savelevel;

    public OptionsUI() {
        super("Options");
        setGuidelineSpacing(150);
        setGuidelineY1(200);
    }

    public void start() {
        addTitle("Options");
        getObjectByText("Options").get().y = getGuidelineY1() - 50;
        addMenuButton("Resume", getGuidelineY1());

        //save level state
        savelevel = new UIButton(getName(), "Save Level", false) {
            {
                UIButton resume = (UIButton) getObjectByText("Resume");
                get().setLocation(resume.get().x, getGuidelineY1());
                get().setSize((-40) + resume.get().width / 2, resume.get().height);
            }
        };
        applyGeneralStyle(savelevel);
        savelevel.autoAdjustBackgroundWidth(false);
        addObject(savelevel);
        //save player state
        saveplayer = new UIButton(getName(), "Save Player", false) {
            {
                get().setLocation( (int) savelevel.get().getMaxX() + 10, savelevel.get().y);
                get().setSize(savelevel.get().width, savelevel.get().height);
            }
        };
        applyGeneralStyle(saveplayer);
        saveplayer.autoAdjustBackgroundWidth(false);
        addObject(saveplayer);

        addMenuButton("Settings", getGuidelineY1());
        addMenuButton("Menu", getGuidelineY1());
    }

    public void setPosition() {
        UIButton resume = (UIButton) getObjectByText("Resume");
        savelevel.get().x = resume.get().x;
        saveplayer.get().setLocation( (int) savelevel.get().x + savelevel.getFullWidth() + 40, savelevel.get().y);
    }

    public void update() {
        super.update();
        setPosition();
    }

}
