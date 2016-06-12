package joshie.mariculture.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static joshie.mariculture.lib.MaricultureInfo.MODID;

public interface MCBlock<T extends Block> extends MCRegistry {
    /** Register this block **/
    default T register(String name) {
        Block block = (Block) this;
        block.setUnlocalizedName(name.replace("_", "."));
        block.setRegistryName(new ResourceLocation(MODID, name));
        GameRegistry.register(block);
        new ItemBlockMC(this).register(name); //Register the item block
        return (T) this;
    }

    /** Return a stack version of this **/
    default ItemStack getStack(int amount) {
        return new ItemStack((T) this, 1, amount);
    }

    /** Return this without any data **/
    default ItemStack getStack() {
        return new ItemStack((T) this);
    }
}
