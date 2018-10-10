package UI;

import java.util.Collection;

public abstract class EntryData {

    String[] meta;
    String text;

    UIObject entry;

    public EntryData() {}

    public EntryData(String text) {
        init(text, new String[] {""});
    }

    public EntryData(String text, String[] meta) {
        init(text, meta);
    }

    protected void init(String text, String[] meta) {
        this.meta = meta;
        this.text = text;
    }

    /**
     * Create a new instance of this {@code ListEntry}'s {@link UIObject}.
     * @return new {@code UIObject} representing this {@code ListEntry}.
     */
    public abstract UIObject createEntry();

    public UIObject getEntry() {
        if (entry == null) {
            createEntry();
        }
            return entry;
    }

    protected void initEntry(GUI gui) {
        entry = createEntry();
        gui.addObject(entry);
    }

    public static void initEntries(Collection<EntryData> c, GUI gui) {
        for (EntryData d : c) {
            d.initEntry(gui);
        }
    }

}