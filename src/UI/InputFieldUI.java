package UI;

import data.PlayerData;
import data.Players;

public class InputFieldUI extends GUI {

    private String title;

    public InputFieldUI(String name, String title) {
        super(name);
        this.title = title;
    }

    public void start() {
        setGuidelineSpacing(150);
        setGuidelineY1(150);

        addTitle(title);
        getObjectByText(title).get().y = getGuidelineY1();

        //texfield
        UIButton textfield = new UIButton(getName(), "Name", true) {
            @Override
            public void useInput() {
                PlayerData data = new PlayerData();
                data.name(this.getInput());
                data.healthregen(0);
                data.damage(15);
                data.maxHealth(100);
                data.maxenergy(100);
                data.maxstamina(100);
                data.experiencelevel(1);
                data.energyregen(0.2);
                Players.savePlayerData(data);
                Players.serializePlayerData();
                UIManager.lockCurrentGUI(false);
                UIManager.enableGUI("PlayerSelect");
            }
            {
                takeInput(true);
                get().width = 500;
                autoAdjustBackgroundWidth(false);
                setInputPrefix("Name");
                get().y = getGuidelineY1() + 200;
            }
        };
        applyGeneralStyle(textfield);
        addObject(textfield);
    }

}
