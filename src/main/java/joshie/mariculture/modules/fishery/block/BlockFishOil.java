package joshie.mariculture.modules.fishery.block;

import joshie.mariculture.core.util.block.MCBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFishOil extends BlockFluidClassic implements MCBlock<BlockFishOil> {
    public BlockFishOil(Fluid fluid) {
        super(fluid, Material.WATER);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return "Fish Oil";
    }
}