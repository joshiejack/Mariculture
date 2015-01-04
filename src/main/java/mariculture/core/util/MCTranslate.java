package mariculture.core.util;


public class MCTranslate {
    public static String translate(String name) {
        return mariculture.lib.util.Text.localize("mariculture." + name);
    }

    public static String getShiftText(String string) {
        String shift = "mariculture.shift";
        return mariculture.lib.util.Text.INDIGO + mariculture.lib.util.Text.localize(shift + ".hold") + " " + mariculture.lib.util.Text.WHITE + mariculture.lib.util.Text.localize(shift + ".shift") + " " + mariculture.lib.util.Text.INDIGO + mariculture.lib.util.Text.localize(shift + "." + string);
    }
}
