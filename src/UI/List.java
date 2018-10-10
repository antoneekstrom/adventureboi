package UI;

import java.awt.Point;
import java.util.Collection;

public class List extends UIObject {

    Collection<EntryData> entries;

    public List(String parentname) {
        super(parentname);
    }

    public void giveList(Collection<EntryData> entries) {
        this.entries = entries;
        EntryData.initEntries(this.entries, getParent());
        refresh();
    }

    public Point unrelativize(Point entry) {
        return new Point(get().x + entry.x, get().y + entry.y);
    }

    public Point getRelativeEntryLocation(EntryData e, int index) {
        return null;
    }

    private void refreshEntryLocation(EntryData e, int index) {
        e.getEntry().get().setLocation( unrelativize( getRelativeEntryLocation(e, index)));
    }

    private void refreshEntryLocations() {

        int i = 0;
        for (EntryData d : entries) {

            refreshEntryLocation(d, i);

            i++;
        }
    }

    private void moveEntry(EntryData d, int x, int y) {
        UIObject e = d.getEntry();
    }

    protected void scroll(int amount) {
        for (int i = 0; i < amount; i++) {
            
        }
    }

    public void refresh() {
        refreshEntryLocations();
    }

}