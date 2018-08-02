package UI;

public class UIDropdown extends UIList {

    int height = 150;

    public UIDropdown(UIObject parent, TaskSet[] tasks) {
        super(parent.getParentName());
        attachToParent(parent);
        createList(tasks);
        alwaysOnTop(true);
    }

    private void attachToParent(UIObject parent) {
        get().setLocation(parent.getActualLocation().x, parent.get().y + parent.getFullHeight());
        get().width = parent.getFullWidth() + BORDER_THICKNESS;
        get().height = height;
    }

    private void createList(TaskSet[] tasks) {
        list.clear();
        for (TaskSet set : tasks) {
            list.add(getEntry(set));
        }
        determineContentHeight();
        determineHandleHeight();
    }

    public UIObject getEntry(TaskSet set) {
        UIObject entry = getEntry(set.text);
        entry.giveTask(set.task);
        return entry;
    }

    @Override
    public void leftMouseReleasedSomewhere() {
        super.leftMouseReleasedSomewhere();
        getParent().remove(this);
    }

    @Override
    public void leftMouseReleased() {
        for (UIObject o : list) {
            if (o.checkMouse()) {
                o.leftMouseReleased();
            }
        }
    }

    @Override
    public UIObject getEntry(String text) {
        UIObject o = new UIObject() {
            @Override
            public void leftMouseReleased() {
                super.leftMouseReleased();
                this.doTask();
            }
        };
        this.text = text;
        cloneEntry(o);
        return o;
    }

    @Override
    public void start() {
        super.start();
        handle().get().setSize(50, 100);
        handle().hoverColorChange(handle().getBackgroundColor().brighter());
        setBackgroundColor(getParent().getUITextColor());
        textColor(getParent().getUITextColor());
        setSpacing(0);
    }

}