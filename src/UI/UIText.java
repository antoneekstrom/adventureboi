package UI;

public class UIText extends UIObject {

    public UIText(String parentName, String text, boolean centered) {
        super();
        setParentName(parentName);
        setText(text);
        autoAdjustBackground(true);
        if (centered) {
            centerInParentX(true);
            centerTextY(true);
            centerTextX(true);
        }
    }
}
