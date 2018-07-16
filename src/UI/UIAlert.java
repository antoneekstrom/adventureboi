package UI;

import java.awt.Graphics;

public class UIAlert extends UIObject {

    private String text;
    private UIButton yesButton, noButton;

    private String yesButtonText = "OK";
    private String noButtonText = "extremely no tbh";
    private int buttonOffset = 45;

    private boolean showNoButton = true;

    public void setButtonText(String text) {yesButtonText = text;}

    public UIAlert(String message, String parentname) {
        super();
        setParentName(parentname);
        text = message;
        start();
    }

    public UIAlert(String message, String parentname, boolean nobutton) {
        super();
        setParentName(parentname);
        text = message;
        showNoButton = nobutton;
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
        this.alwaysOnTop(true);

        //yes
        yesButton = new UIButton(getParentName(), yesButtonText, false) {
            @Override
            public void leftMouseReleased() {
                super.leftMouseReleased();
                onClose();
                getParent().removeAlerts();
            }
            {
                this.get().setLocation(getParent().xCenter(this.get().width) + buttonOffset, 550);
                this.setTag("alert");
                this.alwaysOnTop(true);
            }
        };

        //no
        if (showNoButton) {
            noButton = new UIButton(getParentName(), noButtonText, false) {
                @Override
                public void leftMouseReleased() {
                    super.leftMouseReleased();
                    getParent().removeAlerts();
                }
                {
                    this.get().setLocation(getParent().xCenter(this.get().width) - buttonOffset - this.get().width, 550);
                    this.setTag("alert");
                    this.alwaysOnTop(true);
                }
            };
            getParent().applyGeneralStyle(noButton);
            getParent().addObject(noButton);
        }
            
        getParent().applyGeneralStyle(yesButton);
        getParent().addObject(yesButton);

        //disable parent mouse-checking
        getParent().onTop(false);
    }

    public void onClose() {
        getParent().onTop(true);
    }

    public void update() {
        super.update();
        //yes
        yesButton.setText(yesButtonText);
        yesButton.get().setLocation(getParent().xCenter(yesButton.getFullWidth()) + yesButton.getBackgroundPadding() / 2, 550);
        //no
        if (showNoButton) {
            noButton.setText(noButtonText);
            noButton.get().setLocation(getParent().xCenter(noButton.getFullWidth()) - noButton.getFullWidth() / 2 + noButton.getBackgroundPadding() / 2, 550);
            
            yesButton.get().x = getParent().xCenter(yesButton.getFullWidth()) + buttonOffset + yesButton.getFullWidth() + yesButton.getBackgroundPadding() / 2;
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
    }

}
