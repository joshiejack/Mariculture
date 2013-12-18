package mariculture.core.util;

import mariculture.core.Core;
import mariculture.core.lib.GlassMeta;
import net.minecraft.block.Block;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidMariculture extends Fluid {
	public FluidMariculture(String fluidName) {
		super(fluidName);
	}

	@Override
	public Icon getStillIcon() {
		if(this.fluidName == FluidDictionary.natural_gas) {
			return Core.glassBlocks.getIcon(0, GlassMeta.PLASTIC);
		}
		
		int blockID = FluidRegistry.getFluid(this.fluidName).getBlockID();
		BlockFluidBase block = (BlockFluidBase)Block.blocksList[blockID];
		return block.getIcon(0, 0);
	}
	
	@Override
	public Icon getFlowingIcon() {
		if(this.fluidName == FluidDictionary.natural_gas) {
			return Core.glassBlocks.getIcon(0, GlassMeta.PLASTIC);
		}
		
		int blockID = FluidRegistry.getFluid(this.fluidName).getBlockID();
		BlockFluidBase block = (BlockFluidBase)Block.blocksList[blockID];
		return block.getIcon(2, 0);
	}

	public boolean isMolten() {
		return false;
	}

	@Override
	public String getLocalizedName() {
		String s = this.getUnlocalizedName().substring(6);
		return s == null ? "" : StatCollector.translateToLocal("tile." + s + ".name");
	}
}
