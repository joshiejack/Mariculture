package mariculture.core.lib;

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
	
	public static String getColor(String color) {
		if(color.equals("grey"))
			return GREY;
		if(color.equals("black"))
			return BLACK;
		if(color.equals("green"))
			return DARK_GREEN;
		return DARK_GREY;
	}
}
