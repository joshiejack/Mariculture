package joshie.mariculture.helpers;

import joshie.mariculture.util.BlockMCEnum;
import joshie.mariculture.util.ItemBlockMC;
import joshie.mariculture.util.ItemMCEnum;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import static joshie.mariculture.lib.MaricultureInfo.MODID;

public class RegistryHelper {
    private static boolean isClient() {
        return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
    }

    public static void register(BlockMCEnum block, String name) {
        ResourceLocation resource = new ResourceLocation(MODID, name.replace(".", "_"));
        ItemBlockMC item = new ItemBlockMC(block);
        block.setRegistryName(resource); //Set the block registry name
        item.setRegistryName(resource); //Set the item block registry name
        GameRegistry.register(block); //Register the block
        GameRegistry.register(item); //Register the item block
        if (isClient()) {
            item.getBlock().registerModels(item, name);
        }
    }

    public static void register(Item item, String name) {
        ResourceLocation resource = new ResourceLocation(MODID, name.replace(".", "_"));
        item.setRegistryName(resource);
        GameRegistry.register(item); //Register the item
        if (isClient() && item instanceof ItemMCEnum) {
            ((ItemMCEnum)item).registerModels(item, name);
        }
    }
}
