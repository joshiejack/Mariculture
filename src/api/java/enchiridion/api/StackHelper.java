package enchiridion.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class StackHelper {
	public static ItemStack getStackFromString(String str) {
		return getStackFromArray(str.trim().split(" "));
	}
	
	public static String getStringFromStack(ItemStack stack) {
		String str = Item.itemRegistry.getNameForObject(stack.getItem());
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
		ItemStack stack = new ItemStack(item, 1, meta);
		if(str.length > 2) {
			String s = formatNBT(str, 2).getUnformattedText();
			try {
				NBTBase nbtbase = JsonToNBT.func_150315_a(s);

				if (!(nbtbase instanceof NBTTagCompound)) {
					return null;
				}

				stack.setTagCompound((NBTTagCompound) nbtbase);
			} catch (Exception nbtexception) {
				return null;
			}
		}
		
		return stack;
	}

	private static Item getItemByText(String str) {
		Item item = (Item) Item.itemRegistry.getObject(str);

		if (item == null) {
			try {
				Item item1 = Item.getItemById(Integer.parseInt(str));
				item = item1;
			} catch (NumberFormatException numberformatexception) {
				;
			}
		}
		
		return item;
	}

	private static IChatComponent formatNBT(String[] str, int start) {
		ChatComponentText chatcomponenttext = new ChatComponentText("");

		for (int j = start; j < str.length; ++j) {
			if (j > start) {
				chatcomponenttext.appendText(" ");
			}

			Object object = new ChatComponentText(str[j]);
			chatcomponenttext.appendSibling((IChatComponent) object);
		}

		return chatcomponenttext;
	}

	private static int parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException numberformatexception) {
			return 0;
		}
	}
}
