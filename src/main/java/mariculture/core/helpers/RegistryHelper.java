package mariculture.core.helpers;

import java.lang.reflect.Field;

import mariculture.Mariculture;
import mariculture.api.core.CoralRegistry;
import mariculture.core.blocks.BlockWaterItem;
import mariculture.core.lib.Extra;
import mariculture.core.lib.Modules;
import mariculture.core.util.IHasMeta;
import mariculture.core.util.IItemRegistry;
import mariculture.factory.Factory;
import mariculture.fishery.Fishery;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class RegistryHelper {
	public static void register(Object[] items) {
		for (int i = 0; i < items.length; i++) {			
			if(items[i] instanceof Item) {
				registerItem((Item) items[i]);
			} else if(items[i] instanceof IHasMeta) {
				registerMetaBlock(((IHasMeta)items[i]).getItemClass(), (Block) items[i]);
			} else if(items[i] instanceof Block) {
				registerBlock((Block) items[i]);
			}
		}
	}

	private static void registerItem(Item item) {
		try {
			String name = item.getUnlocalizedName().substring(5);
			name = name.replace('.', '_');
			if(Extra.DEBUG_ON) System.out.println("Mariculture successfully registered the item " + item.getClass().getSimpleName() + " as Mariculture:" + name);
			GameRegistry.registerItem(item, name, Mariculture.modid);
			
			//Mariculture Item Registry
			if(item instanceof IItemRegistry) ((IItemRegistry) item).register(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static void registerMetaBlock(Class<? extends ItemBlock> clazz, Block block) {
		try {
			String name = block.getUnlocalizedName().substring(5);
			if(clazz == null) {
				clazz = (Class<? extends ItemBlock>) Class.forName(block.getClass().getCanonicalName().toString() + "Item");
			}
			
			name = name.replace('.', '_');
			if(Extra.DEBUG_ON) System.out.println("Mariculture successfully registered the block " + block.getClass().getSimpleName() + " with the item " + clazz.getSimpleName() + " as Mariculture:" + name);
			GameRegistry.registerBlock(block, clazz, name);
			
			//Mariculture Item Registry
			Item item = Item.getItemFromBlock(block);
			if(item instanceof IItemRegistry) ((IItemRegistry) item).register(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void registerBlock(Block block) {
		String name = block.getUnlocalizedName().substring(5);
		name = name.replace('.', '_');
		if(Extra.DEBUG_ON) System.out.println("Mariculture successfully registered the block " + block.getClass().getSimpleName() + " as Mariculture:" + name);
		GameRegistry.registerBlock(block, name);
		
		//Mariculture Item Registry
		Item item = Item.getItemFromBlock(block);
		if(item instanceof IItemRegistry) ((IItemRegistry) item).register(item);
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
			return " null stack ";
		}
		
		if(stack.getItem() instanceof IItemRegistry) {
			return ((IItemRegistry) (stack).getItem()).getName(stack);
		}
		
		return " not iitem registered " + stack.getUnlocalizedName();
	}
}
