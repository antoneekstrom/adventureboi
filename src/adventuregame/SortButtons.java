package adventuregame;

import java.awt.Color;

import worlds.ListWorld;

public class SortButtons {

    private static void select(HudObj o, ListWorld w) {
        o.setHighlightColor(Color.WHITE);
        o.setHighlightTextColor(Color.ORANGE);
        o.select(true);

        for (int i = 0; i < w.invscreen.hb.size(); i++) {
            if (!w.invscreen.hb.get(i).id.equals(o.id)) {
                w.invscreen.hb.get(i).select(false);
            }
        }
    }

    public static void sort(HudObj o, ListWorld w) {
        String s = o.id;
        if (s.equals("statsort")) {
            w.inv.sortByTag("statup");
            w.inv.sort();
            select(o, w);
        }
        if (s.equals("misc")) {
            w.inv.sortByTag("misc");
            w.inv.sort();
            select(o, w);
        }
        if (s.equals("all")) {
            w.inv.sortByTag("all");
            w.inv.sort();
            select(o, w);
        }
    }
}