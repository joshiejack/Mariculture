package enchiridion.api;

import net.minecraft.util.StatCollector;

public class Formatting {

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

	public static String getColor(String color) {
		if(color.equalsIgnoreCase("black")) 		return BLACK;
		if(color.equalsIgnoreCase("dark blue")) 	return DARK_BLUE;
		if(color.equalsIgnoreCase("dark green")) 	return DARK_GREEN;
		if(color.equalsIgnoreCase("dark_aqua"))		return DARK_AQUA;
		if(color.equalsIgnoreCase("dark red")) 		return DARK_RED;
		if(color.equalsIgnoreCase("purple")) 		return PURPLE;
		if(color.equalsIgnoreCase("orange")) 		return ORANGE;
		if(color.equalsIgnoreCase("grey")) 			return GREY;
		if(color.equalsIgnoreCase("indigo"))		return INDIGO;
		if(color.equalsIgnoreCase("bright green")) 	return BRIGHT_GREEN;
		if(color.equalsIgnoreCase("aqua")) 			return AQUA;
		if(color.equalsIgnoreCase("red")) 			return RED;
		if(color.equalsIgnoreCase("pink")) 			return PINK;
		if(color.equalsIgnoreCase("yellow"))		return YELLOW;
		if(color.equalsIgnoreCase("white"))			return WHITE;
		return WHITE;
	}

	public static String translate(String string) {
		return StatCollector.translateToLocal(string);
	}
}
