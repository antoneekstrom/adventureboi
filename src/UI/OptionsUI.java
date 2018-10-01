package UI;

import java.awt.Rectangle;

import adventuregame.GameEnvironment;
import adventuregame.GlobalData;
import adventuregame.Position;
import gamelogic.ObjectStorage;
import objects.Player;

public class OptionsUI extends GUI {

    UIButton saveplayer, savelevel;

    public OptionsUI() {
        super("Options");
        setGuidelineSpacing(150);
        setGuidelineY1(200);
    }

    @Override
    public void enable(boolean addToHistory) {
        super.enable(addToHistory);
        updateDeathCount();
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

        //level completion
        Rectangle g = get();
        addObject(new UIMeter(getName()) {
            @Override
            public String getValueText() {
                setPercent(GameEnvironment.background().percentTraveled);
                return percent() + "%";
            }
            {
                get().setSize(600, 25);
                setFontSize(getFontSize() - 5);
                get().setLocation(Position.centerX(g, get()).x, 100);
                setForegroundColor(getUIBackgroundColor());
                createText("Level Completion");
            }
        });

        //deathcount
        int deathY = (int) (GlobalData.getScreenDim().getHeight() / 2) - 75;

        UIText d = new UIText(getName(), "deaths:", false);
        d.get().setLocation(0, deathY);
        d.setBackgroundColor(getUIBackgroundColor());
        d.setBackgroundPadding(25);
        d.autoAdjustBackground(true);
        d.centerText();
        addObject(d);
        deathY += 75;

        for (Player p : ObjectStorage.players()) {
            addDeathText(deathY, p);
            deathY += 75;
        }
    }

    public void updateDeathCount() {
        for (UIObject o : getObjectsByTag("deathCount")) {
            o.setText(deathCount(getPlayerFromCounter(o.getText())));
            o.setBackgroundColor(getUIBackgroundColor());
            o.setBackgroundPadding(25);
            o.autoAdjustBackground(true);
            o.centerText();
        }
    }

    private Player getPlayerFromCounter(String text) {
        String n = text.substring(0, text.indexOf(":"));
        Player p = ObjectStorage.getPlayer(n);
        return p;
    }

    public void addDeathText(int y, Player p) {
        if (p != null) {
            addObject(new UIText(getName(), deathCount(p), false) {
                {
                    setTag("deathCount");
                    get().setLocation(0, y);
                }
            });
        }
    }

    public String deathCount(Player p) {
        if (p != null) {
            return p.getName() + ": " + p.getDeathCount();
        }
        return "nice:";
    }

    public void setPosition() {
        UIButton resume = (UIButton) getObjectByText("Resume");
        savelevel.get().x = resume.get().x;
        saveplayer.get().setLocation( (int) savelevel.get().x + savelevel.getFullWidth() + savelevel.getBackgroundPadding() / 2 + savelevel.BORDER_THICKNESS, savelevel.get().y);
    }

    public void update() {
        super.update();
        setPosition();
    }

}
