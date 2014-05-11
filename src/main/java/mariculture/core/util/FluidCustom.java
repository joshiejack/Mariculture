package mariculture.core.util;

import mariculture.core.Core;
import mariculture.core.lib.TransparentMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;

public class FluidCustom extends Fluid {
	private String fluidsName;
	private ItemStack block;
	
	public FluidCustom(String fluidName, String localized, int id, int meta) {
		super(fluidName);
		this.fluidsName = localized;
		this.block = new ItemStack(id, 1, meta);
	}

	@Override
	public Icon getStillIcon() {
		if(block != null) {
			if(Block.blocksList[block.itemID] != null) {
				return Block.blocksList[block.itemID].getIcon(block.itemID, block.getItemDamage());
			}
		}
		
		return Core.transparent.getIcon(0, TransparentMeta.PLASTIC);
	}
	
	@Override
	public Icon getFlowingIcon() {
		if(block != null) {
			if(Block.blocksList[block.itemID] != null) {
				return Block.blocksList[block.itemID].getIcon(block.itemID, block.getItemDamage());
			}
		}
		
		return Core.transparent.getIcon(0, TransparentMeta.PLASTIC);
	}
	
	public boolean isMolten() {
		return true;
	}

	@Override
	public String getLocalizedName() {
		return fluidsName;
	}
}
