package mariculture.core.lib;

public class Dye {
    public static final int LIGHT_BLUE = 12;
    public static final int MAGENTA = 13;
    public static final int ORANGE = 14;
    public static final int PINK = 9;
    public static final int PURPLE = 5;
    public static final int RED = 1;
    public static final int BONE = 15;
    public static final int YELLOW = 11;
    public static final int LAPIS = 4;
    public static final int INK = 0;
    public static final int GREEN = 2;
    public static final int GREY = 8;
    public static final int LIGHT_GREY = 7;
    public static final int BROWN = 3;
    public static final int CYAN = 6;
    public static final int LIME = 10;

    public static String getName(int i) {
        switch (i) {
            case INK:
                return "dyeBlack";
            case RED:
                return "dyeRed";
            case GREEN:
                return "dyeGreen";
            case BROWN:
                return "dyeBrown";
            case LAPIS:
                return "dyeBlue";
            case PURPLE:
                return "dyePurple";
            case CYAN:
                return "dyeCyan";
            case LIGHT_GREY:
                return "dyeLightGray";
            case GREY:
                return "dyeGray";
            case PINK:
                return "dyePink";
            case LIME:
                return "dyeLime";
            case YELLOW:
                return "dyeYellow";
            case LIGHT_BLUE:
                return "dyeLightBlue";
            case MAGENTA:
                return "dyeMagenta";
            case ORANGE:
                return "dyeOrange";
            case BONE:
                return "dyeWhite";
            default:
                return "dye";
        }
    }
}
