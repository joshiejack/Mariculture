package mariculture.core.blocks;

import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.item.ItemStack;

public class BlockMaricultureStairs extends BlockStairs implements IItemRegistry {
	public BlockMaricultureStairs(int blockID, Block modelBlock, int modelMetadata) {
		super(blockID, modelBlock, modelMetadata);
		setLightOpacity(0);
		setCreativeTab(MaricultureTab.tabMariculture);
	}
	
	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(getName(new ItemStack(this.blockID, 1, j)), new ItemStack(this.blockID, 1, j));
		}
	}

	@Override
	public int getMetaCount() {
		return 1;
	}

	@Override
	public String getName(ItemStack stack) {
		return this.getUnlocalizedName().substring(5);
	}
}
