package mariculture.core.helpers.cofh;

import java.util.ArrayList;
import java.util.List;

import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Text;
import mariculture.core.util.FluidDictionary;
import mariculture.core.util.FluidMari;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.input.Keyboard;

/**
 * Contains various helper functions to assist with String manipulation.
 * 
 * @author King Lemming
 * 
 */
public final class StringHelper {

	private StringHelper() {

	}

	public static boolean isAltKeyDown() {

		return Keyboard.isKeyDown(Keyboard.KEY_LMENU) || Keyboard.isKeyDown(Keyboard.KEY_RMENU);
	}

	public static boolean isControlKeyDown() {

		return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
	}

	public static boolean isShiftKeyDown() {

		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}

	public static int getSplitStringHeight(FontRenderer fontRenderer, String input, int width) {

		List stringRows = fontRenderer.listFormattedStringToWidth(input, width);
		return stringRows.size() * fontRenderer.FONT_HEIGHT;
	}

	public static String camelCase(String input) {

		return input.substring(0, 1).toLowerCase() + input.substring(1);
	}

	public static String titleCase(String input) {

		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}

	public static String localize(String key) {

		return StatCollector.translateToLocal(key);
	}

	public static String getFluidName(Fluid fluid) {

		String fluidName = "";
		if (fluid.getRarity() == EnumRarity.uncommon) {
			fluidName += YELLOW;
		} else if (fluid.getRarity() == EnumRarity.rare) {
			fluidName += BRIGHT_BLUE;
		} else if (fluid.getRarity() == EnumRarity.epic) {
			fluidName += PINK;
		} 
		fluidName += fluid.getLocalizedName() + END;

		return fluidName;
	}
	
	public static String getFluidName(FluidStack fluid) {
		if(fluid == null || fluid.getFluid() == null || fluid.amount <= 0)
			return Text.WHITE + StatCollector.translateToLocal("mariculture.string.empty");
		return getFluidName(fluid.getFluid());
	}
	
	public static List getFluidQty(List tooltip, FluidStack fluid, int max) {	
		if(fluid == null || fluid.getFluid() == null)
			tooltip.add(Text.GREY + "" + 0 + "/" + max + "mB");
		else if(Modules.fishery.isActive() && fluid.fluidID == FluidRegistry.getFluidID(FluidDictionary.fish_food))
			tooltip.add(Text.GREY + "" + fluid.amount + "/" + max + " " + StatCollector.translateToLocal("mariculture.string.pieces"));
		else if(fluid.getFluid().getName().contains("glass") || fluid.getFluid().getName().contains("salt"))
			tooltip.add(Text.GREY + "" + fluid.amount + "/" + max + "mB");
		else if(fluid.getFluid().getName().contains("molten")) {
			int ingots = fluid.amount / MetalRates.INGOT;
            if (ingots > 0)
                tooltip.add(Text.GREY + StatCollector.translateToLocal("mariculture.string.ingots") + ": " + ingots);
            int mB = fluid.amount % MetalRates.INGOT;
            if (mB > 0)  {
                int nuggets = mB / MetalRates.NUGGET;
                int junk = (mB % MetalRates.NUGGET);
                if (nuggets > 0)
                    tooltip.add(Text.GREY + StatCollector.translateToLocal("mariculture.string.nuggets") + ": " + nuggets);
                if (junk > 0)
                    tooltip.add(Text.GREY + "mB: " + junk);
            }
            
            tooltip.add("");
            tooltip.add(Text.GREY + StatCollector.translateToLocal("mariculture.string.outof"));
            tooltip.add(Text.GREY + (int)max/MetalRates.INGOT + " " + StatCollector.translateToLocal("mariculture.string.ingots") + " & " 
            			+ max%MetalRates.INGOT + "mB");
		} else {
			tooltip.add(Text.GREY + "" + fluid.amount + "/" + max + "mB");
		}
		
		return tooltip;
	}


	public static String getScaledNumber(int number) {

		return getScaledNumber(number, 2);
	}

	public static String getScaledNumber(int number, int minDigits) {

		String numString = "";

		int numMod = 10 * minDigits;

		if (number > 100000 * numMod) {
			numString += number / 1000000 + "M";
		} else if (number > 100 * numMod) {
			numString += number / 1000 + "k";
		} else {
			numString += number;
		}
		return numString;
	}

	public static String getShiftText() {

		return shiftForInfo;
	}

	public static String getActivationText(String key) {

		return BRIGHT_BLUE + ITALIC + localize(key) + END;
	}

	public static String getDeactivationText(String key) {

		return YELLOW + ITALIC + localize(key) + END;
	}

	public static String getInfoText(String key) {

		return BRIGHT_GREEN + localize(key) + END;
	}

	public static String getFlavorText(String key) {

		return WHITE + ITALIC + localize(key) + END;
	}

	/** When formatting a string, always apply color before font modification. */
	public static final String BLACK = (char) 167 + "0";
	public static final String BLUE = (char) 167 + "1";
	public static final String GREEN = (char) 167 + "2";
	public static final String TEAL = (char) 167 + "3";
	public static final String RED = (char) 167 + "4";
	public static final String PURPLE = (char) 167 + "5";
	public static final String ORANGE = (char) 167 + "6";
	public static final String LIGHT_GRAY = (char) 167 + "7";
	public static final String GRAY = (char) 167 + "8";
	public static final String LIGHT_BLUE = (char) 167 + "9";
	public static final String BRIGHT_GREEN = (char) 167 + "a";
	public static final String BRIGHT_BLUE = (char) 167 + "b";
	public static final String LIGHT_RED = (char) 167 + "c";
	public static final String PINK = (char) 167 + "d";
	public static final String YELLOW = (char) 167 + "e";
	public static final String WHITE = (char) 167 + "f";

	public static final String OBFUSCATED = (char) 167 + "k";
	public static final String BOLD = (char) 167 + "l";
	public static final String STRIKETHROUGH = (char) 167 + "m";
	public static final String UNDERLINE = (char) 167 + "n";
	public static final String ITALIC = (char) 167 + "o";
	public static final String END = (char) 167 + "r";

	public static boolean displayShiftForDetail = true;
	public static boolean displayStackCount = false;

	public static String shiftForInfo = LIGHT_GRAY + localize("message.cofh.holdShift1") + " " + YELLOW + ITALIC + localize("message.cofh.holdShift2") + " "
			+ END + LIGHT_GRAY + localize("message.cofh.holdShift3") + END;
}
