package joshie.mariculture.core.util;


public class MCTranslate {
    public static String translate(String name) {
        return joshie.lib.util.Text.localize("mariculture." + name);
    }

    public static String getShiftText(String string) {
        String shift = "mariculture.shift";
        return joshie.lib.util.Text.INDIGO + joshie.lib.util.Text.localize(shift + ".hold") + " " + joshie.lib.util.Text.WHITE + joshie.lib.util.Text.localize(shift + ".shift") + " " + joshie.lib.util.Text.INDIGO + joshie.lib.util.Text.localize(shift + "." + string);
    }
}
