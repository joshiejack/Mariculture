package mariculture.core.helpers;

import java.lang.reflect.Field;

import mariculture.api.core.CoralRegistry;
import mariculture.core.config.GeneralStuff;
import mariculture.core.handlers.LogHandler;
import mariculture.core.util.IHasMeta;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.registry.GameRegistry;

public class RegistryHelper {
    //Shorthand for registering items
    public static void registerItems(Item[] items) {
        for (Item item : items) {
            registerItem(item);
        }
    }

    //Short hand for registering blocks
    public static void registerBlocks(Block[] blocks) {
        for (Block block : blocks)
            if (block instanceof IHasMeta) {
                registerMetaBlock(((IHasMeta) block).getItemClass(), block);
            } else {
                registerBlock(block);
            }
    }

    //Short hand for registering tile entities
    public static void registerTiles(Class<? extends TileEntity>[] tiles) {
        for (Class<? extends TileEntity> tile : tiles) {
            GameRegistry.registerTileEntity(tile, "Mariculture:" + tile.getSimpleName());
        }
    }

    private static void registerItem(Item item) {
        try {
            String name = item.getUnlocalizedName().substring(5);
            name = name.replace('.', '_');
            if (GeneralStuff.DEBUG_ON) {
                LogHandler.log(Level.DEBUG, "Mariculture successfully registered the item " + item.getClass().getSimpleName() + " as Mariculture:" + name);
            }
            GameRegistry.registerItem(item, name);

            //Mariculture Item Registry
            if (item instanceof IItemRegistry) {
                ((IItemRegistry) item).register(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerMetaBlock(Class<? extends ItemBlock> clazz, Block block) {
        try {
            String name = block.getUnlocalizedName().substring(5);
            if (clazz == null) {
                clazz = (Class<? extends ItemBlock>) Class.forName(block.getClass().getCanonicalName().toString() + "Item");
            }

            name = name.replace('.', '_');
            if (GeneralStuff.DEBUG_ON) {
                LogHandler.log(Level.DEBUG, "Mariculture successfully registered the block " + block.getClass().getSimpleName() + " with the item " + clazz.getSimpleName() + " as Mariculture:" + name);
            }
            GameRegistry.registerBlock(block, clazz, name);

            //Mariculture Item Registry
            Item item = Item.getItemFromBlock(block);
            if (item instanceof IItemRegistry) {
                ((IItemRegistry) item).register(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void registerBlock(Block block) {
        String name = block.getUnlocalizedName().substring(5);
        name = name.replace('.', '_');
        if (GeneralStuff.DEBUG_ON) {
            LogHandler.log(Level.DEBUG, "Mariculture successfully registered the block " + block.getClass().getSimpleName() + " as Mariculture:" + name);
        }
        GameRegistry.registerBlock(block, name);

        //Mariculture Item Registry
        Item item = Item.getItemFromBlock(block);
        if (item instanceof IItemRegistry) {
            ((IItemRegistry) item).register(item);
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
            if (ret != null && (ret instanceof ItemStack || ret instanceof Item)) return ret;
            return null;
        } catch (Exception e) {}
        return null;
    }

    public static String getName(Object o) {
        if (o instanceof ItemStack) return Item.itemRegistry.getNameForObject(((ItemStack) o).getItem());
        else if (o instanceof Item) return Item.itemRegistry.getNameForObject(o);
        else if (o instanceof Block) return Block.blockRegistry.getNameForObject(o);
        else return null;
    }
}
