package UI;

public class UIButton extends UIObject {

    private NavTask task;

    public UIButton(String parentname, String text, boolean centered) {
        super();
        setParentName(parentname);
        setBackgroundColor(getParent().getUIBackgroundColor());
        setText(text);
        autoAdjustBackground(true);
        hoverColorChange(getParent().BACKGROUND_HOVER_COLOR);
        setHoverTextColor(getParent().TEXT_HOVER_COLOR);
        if (centered) {
            centerInParentX(true);
            centerTextY(true);
            centerTextX(true);
        }
    }

    public void giveTask(NavTask nt) {
        task = nt;
    }

    public void doTask() {
        if (task != null) {
            task.run();
        }
    }

    @Override
    public void leftMouseReleased() {
        super.leftMouseReleased();
        doTask();
    }
}
