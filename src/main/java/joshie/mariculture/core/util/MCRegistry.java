package joshie.mariculture.core.util;

import joshie.mariculture.core.lib.CreativeOrder;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface MCRegistry extends CreativeSorted {
    /** Register the item models
     *  @param item the item
     *  **/
    @SideOnly(Side.CLIENT)
    default void registerModels(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    /** The creative sort value for this item/block
     * @param stack
     * @return the value */
    default int getSortValue(ItemStack stack) {
        return CreativeOrder.DEFAULT;
    }
}
