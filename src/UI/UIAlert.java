package UI;

import java.awt.Graphics;

public class UIAlert extends UIObject {

    private String text;
    private UIButton b;
    private String buttonText = "OK";
    public void setButtonText(String text) {buttonText = text;}

    public UIAlert(String message, String parentname) {
        super();
        setParentName(parentname);
        text = message;
        start();
    }

    public void start() {
        getParent().applyGeneralStyle(this);
        centerInParentX(true);
        centerTextX(true);
        centerTextY(true);
        setBackgroundColor(getParent().getUIBackgroundColor());
        setText(text);
        setTag("alert");
        get().y = getParent().yCenter(get().height) - 100;
        autoAdjustBackground(true);

        b = new UIButton(getParentName(), buttonText, true) {
            @Override
            public void leftMouseReleased() {
                super.leftMouseReleased();
                onClose();
                getParent().removeAlerts();
            }
            {
                this.get().y = 550;
                this.setTag("alert");
            }
        };
        getParent().applyGeneralStyle(b);
        getParent().addObject(b);
    }

    public void onClose() {

    }

    public void update() {
        super.update();
        b.setText(buttonText);
    }

    public void paint(Graphics g) {
        super.paint(g);
    }

}
