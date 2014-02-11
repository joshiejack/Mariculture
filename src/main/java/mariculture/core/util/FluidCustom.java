package mariculture.core.util;

import mariculture.core.Core;
import mariculture.core.lib.TransparentMeta;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class FluidCustom extends Fluid {
	private String fluidsName;
	private ItemStack block;
	
	public FluidCustom(String fluidName, String localized, ItemStack stack) {
		super(fluidName);
		this.fluidsName = localized;
		this.block = stack;
	}

	@Override
	public IIcon getStillIcon() {
		if(block != null) {
			return block.getIconIndex();
		}
		
		return Core.transparentBlocks.getIcon(0, TransparentMeta.PLASTIC);
	}
	
	@Override
	public IIcon getFlowingIcon() {
		if(block != null) {
			return block.getIconIndex();
		}
		
		return Core.transparentBlocks.getIcon(0, TransparentMeta.PLASTIC);
	}
	
	public boolean isMolten() {
		return true;
	}

	@Override
	public String getLocalizedName() {
		return fluidsName;
	}
}
