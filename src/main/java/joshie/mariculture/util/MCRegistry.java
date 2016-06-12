package joshie.mariculture.util;

import joshie.mariculture.helpers.StringHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static joshie.mariculture.lib.MaricultureInfo.MODID;
import static joshie.mariculture.lib.MaricultureInfo.MODPREFIX;

public interface MCRegistry extends CreativeSorted {
    /** Register the models **/
    @SideOnly(Side.CLIENT)
    default void registerModels(Item item, String name) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(MODID, name.replace(".", "_")), "inventory"));
    }

    /** Return the unlocalized name for this block **/
    String getUnlocalizedName();

    /** Returns the name for this block **/
    default String getItemStackDisplayName(ItemStack stack) {
        String unlocalized = MODPREFIX + getUnlocalizedName();
        return StringHelper.localize(unlocalized);
    }

    default boolean isClient() {
        return FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT;
    }
}
