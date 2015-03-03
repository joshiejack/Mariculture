package mariculture.core.lib;

public class PearlColor {
    public static final int COUNT = 12;
    public static final int WHITE = 0;
    public static final int GREEN = 1;
    public static final int YELLOW = 2;
    public static final int ORANGE = 3;
    public static final int RED = 4;
    public static final int GOLD = 5;
    public static final int BROWN = 6;
    public static final int PURPLE = 7;
    public static final int BLUE = 8;
    public static final int BLACK = 9;
    public static final int PINK = 10;
    public static final int SILVER = 11;

    public static String get(int i) {
        switch (i) {
            case WHITE:
                return "white";
            case GREEN:
                return "green";
            case YELLOW:
                return "yellow";
            case ORANGE:
                return "orange";
            case RED:
                return "red";
            case GOLD:
                return "gold";
            case BROWN:
                return "brown";
            case PURPLE:
                return "purple";
            case BLUE:
                return "blue";
            case BLACK:
                return "black";
            case PINK:
                return "pink";
            case SILVER:
                return "silver";
            default:
                return "unnamed";
        }
    }
}
