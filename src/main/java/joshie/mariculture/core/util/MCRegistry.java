package joshie.mariculture.core.util;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface MCRegistry extends CreativeSorted {
    /** Register the models **/
    @SideOnly(Side.CLIENT)
    default void registerModels(Item item, String name) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    default boolean isClient() {
        return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
    }
}
