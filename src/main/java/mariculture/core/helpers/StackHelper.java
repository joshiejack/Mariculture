package mariculture.core.helpers;

import java.util.logging.Level;

import mariculture.core.handlers.LogHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StackHelper {
	public static ItemStack getStackFromString(String str) {
		return getStackFromArray(str.trim().split(" "));
	}
	
	public static String getStringFromStack(ItemStack stack) {
		String str = stack.getUnlocalizedName().substring(5);
		if(stack.getHasSubtypes()) str = str + " " + stack.getItemDamage();
		if(stack.hasTagCompound()) {
			str = str + " " + stack.stackTagCompound.toString();
		}
		
		return str;
	}
	
	public static boolean matches(String str, ItemStack stack) {
		return getStringFromStack(stack).equals(str);
	}
	
	private static ItemStack getStackFromArray(String[] str) {
		Item item = getItemByText(str[0]);
		int meta = 0;
		if(str.length > 1) meta = parseInt(str[1]);
		return item == null? null: new ItemStack(item, 1, meta);
	}

	private static Item getItemByText(String str) {
		try {
            Item item = Item.itemsList[Integer.parseInt(str)];
			return Item.itemsList[Integer.parseInt(str)];
		} catch (NumberFormatException numberformatexception) {
            for(Item item: Item.itemsList) {
                if(item != null) {
                	try {
	                	String text = item.getUnlocalizedName();
	                    if(text.length() > 5 && text.substring(5).equals(str)) {
	                        return item;
	                    }
                	} catch (Exception e) {
                		LogHandler.log(Level.SEVERE, "A Mod has incorrectly registered an item block, go and complain to the author about this!");
                		LogHandler.log(Level.SEVERE, item.toString());
                		e.printStackTrace();
                	}
                }
            }
		}

		return null;
	}

	private static int parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException numberformatexception) {
			return 0;
		}
	}
}
