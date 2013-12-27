package mariculture.core.util;

import mariculture.core.Core;
import mariculture.core.lib.GlassMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class FluidCustom extends FluidMariculture {
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
		
		return Core.glassBlocks.getIcon(0, GlassMeta.PLASTIC);
	}
	
	public boolean isMolten() {
		return true;
	}

	@Override
	public String getLocalizedName() {
		return fluidsName;
	}
}
