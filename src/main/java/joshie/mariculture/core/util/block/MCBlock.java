package joshie.mariculture.core.util.block;

import joshie.mariculture.core.util.item.MCItem;
import joshie.mariculture.core.util.MCRegistry;
import joshie.mariculture.core.util.item.ItemBlockMC;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static joshie.mariculture.core.lib.MaricultureInfo.MODID;

public interface MCBlock<T extends Block> extends MCRegistry {
    /** Register this block **/
    default T register(String name) {
        Block block = (Block) this;
        block.setUnlocalizedName(name.replace("_", "."));
        block.setRegistryName(new ResourceLocation(MODID, name));
        GameRegistry.register(block);
        getItemBlock().register(name); //Register the item block
        return (T) this;
    }

    /** Register this block WITHOUT assigning the item **/
    default T registerWithoutItem(String name) {
        Block block = (Block) this;
        block.setUnlocalizedName(name.replace("_", "."));
        block.setRegistryName(new ResourceLocation(MODID, name));
        GameRegistry.register(block);
        return (T) this;
    }

    String getItemStackDisplayName(ItemStack stack);

    /** Return a stack version of this **/
    default ItemStack getStack(int amount) {
        return new ItemStack((T) this, amount);
    }

    /** Return this without any data **/
    default ItemStack getStack() {
        return new ItemStack((T) this);
    }

    /** Get the item block **/
    default MCItem getItemBlock() {
        return new ItemBlockMC(this);
    }

    /** Called by ItemBlockMC to see if the block can be placed**/
    default boolean canPlaceBlockAt(EntityPlayer player, ItemStack stack, World world, BlockPos pos, EnumFacing side) {
        return true;
    }
}
