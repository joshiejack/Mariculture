package mariculture.core.helpers;

import java.lang.reflect.Field;

import mariculture.Mariculture;
import mariculture.api.core.CoralRegistry;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class RegistryHelper {
	public static void register(Object[] items) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] instanceof IItemRegistry) {
				((IItemRegistry) items[i]).register();
			}
			
			if(items[i] instanceof Item) {
				registerItem((Item) items[i]);
			} else if(items[i] instanceof Block) {
				registerItem(Item.getItemFromBlock((Block)items[i]));
			}
		}
	}

	private static void registerItem(Item item) {
		try {
			String name = item.getUnlocalizedName().substring(5);
			GameRegistry.registerItem(item, name, Mariculture.modid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void registerCoral(ItemStack stack, String color) {
		CoralRegistry.register(stack);
		OreDictionary.registerOre("coral" + color, stack);
	}

	public static Object getStaticItem(String name, String classPackage) {
		try {
			Class clazz = Class.forName(classPackage);
			Field field = clazz.getDeclaredField(name);
			Object ret = field.get(null);
			if ((ret != null) && (((ret instanceof ItemStack)) || ((ret instanceof Item)))) {
				return ret;
			}
			return null;
		} catch (Exception e) {
		}
		return null;
	}

	public static String getName(ItemStack stack) {
		if(stack == null) {
			return "";
		}
		
		if(stack.getItem() instanceof IItemRegistry) {
			return ((IItemRegistry) (stack).getItem()).getName(stack);
		}
		
		return "";
	}
}
