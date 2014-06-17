package mariculture.api.util;

import net.minecraft.util.StatCollector;

public class Text {
    private final static String prfx = "\u00a7";
    public static final String BLACK = prfx + "0";
    public static final String DARK_BLUE = prfx + "1";
    public static final String DARK_GREEN = prfx + "2";
    public static final String DARK_AQUA = prfx + "3";
    public static final String DARK_RED = prfx + "4";
    public static final String PURPLE = prfx + "5";
    public static final String ORANGE = prfx + "6";
    public static final String GREY = prfx + "7";
    public static final String DARK_GREY = prfx + "8";
    public static final String INDIGO = prfx + "9";
    public static final String BRIGHT_GREEN = prfx + "a";
    public static final String AQUA = prfx + "b";
    public static final String RED = prfx + "c";
    public static final String PINK = prfx + "d";
    public static final String YELLOW = prfx + "e";
    public static final String WHITE = prfx + "f";
    public static final String BOLD = prfx + "l";
    public static final String UNDERLINE = prfx + "n";
    public static final String ITALIC = prfx + "o";
    public static final String END = prfx + "r";
    public static final String OBFUSCATED = prfx + "k";
    public static final String STRIKETHROUGH = prfx + "m";
    public static final String DEGREES = "\u00B0" + "C";

    public static String getColor(String color) {
        if (color.equals("grey")) return GREY;
        if (color.equals("black")) return BLACK;
        if (color.equals("green")) return DARK_GREEN;
        return DARK_GREY;
    }

    public static String localize(String key) {
        return StatCollector.translateToLocal(key);
    }

    public static String getShiftText(String string) {
        String shift = "mariculture.string.shift";
        return Text.INDIGO + localize(shift + ".hold") + " " + Text.WHITE + localize(shift + ".shift") + " " + Text.INDIGO + localize(shift + "." + string);
    }

    public static String translate(String name) {
        return localize("mariculture.string." + name);
    }
}
