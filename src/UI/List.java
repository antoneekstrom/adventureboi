package UI;

import java.awt.Point;
import java.util.Collection;

import gamelogic.Numbers;

public class List extends UIObject {

    Collection<EntryData> entries;

    public List(String parentname) {
        super(parentname);
    }

    public void giveList(Collection<EntryData> entries) {
        this.entries = entries;
        EntryData.initEntries(this.entries, getParent());
    }

    /**
     * Takes a location relative to this {@code List} from for example
     * {@code getRelativeEntryLocation()} and returns the absolute position on the screen.
     * @param location the location to convert
     * @return an absolute position onscreen.
     */
    public Point unrelativize(Point location) {
        return new Point(get().x + location.x, get().y + location.y);
    }

    /**
     * Get location of entry relative to this {@code List}.
     * @param e the entry in question
     * @return location of the entry
     */
    public Point getRelativeEntryLocation(EntryData e) {
        return new Point(0,0);
    }

    /**
     * Refresh the location of a single {@code EntryData}.
     * @param e the entry.
     */
    protected void refreshEntryLocation(EntryData e) {
        e.getEntry().get().setLocation( unrelativize( getRelativeEntryLocation(e)));
    }

    /**
     * Do a {@code refreshEntryLocation()} on every entry in this {@code List}.
     */
    protected void refreshEntryLocations() {

        for (EntryData d : entries) {
            refreshEntryLocation(d);
        }
    }

    void moveEntry(EntryData d, int x, int y) {
        UIObject e = d.getEntry();
        e.get().translate(x, y);
    }

    /**
     * Move all entries in this {@code List} vertically, "scroll" as some might say.
     * @param amount of pixels to scroll
     */
    protected void scroll(int amount) {
        for (EntryData e : entries) {
            e.getEntry().get().translate(0, amount);
        }
    }

    /**
     * Limit entries in certain ways and check if they are out of the bounds of this {@code List}.
     * Make them not visible if they are outside the list.
     */
    void limitEntry(EntryData d) {
        UIObject e = d.getEntry();
        boolean visible = true;

        if (Numbers.checkMaxLimit(e.get().getMaxY(), get().getMaxY())) {
            visible = false;
        }
        else if (Numbers.checkMinLimit(e.get().getMinY(), get().getMinY())) {
            visible = false;
        }

        e.setVisible(visible);
    }

    /**
     * Limit all entries in this {@code List}. Perform a {@code limitEntry()}
     * on every {@code EntryData}.
     */
    void limitEntries() {
        for (EntryData d : entries) {
            limitEntry(d);
        }
    }

    /** Perform all neccessary refreshing on entries. */
    public void refresh() {
        refreshEntryLocations();
        limitEntries();
    }

    /**
     * Refresh list while also giving it a new collection of entries before refreshing.
     * @param entries said collection of entries to be refreshed with.
     */
    public void refresh(Collection<EntryData> entries) {
        giveList(entries);
        refresh();
    }

}