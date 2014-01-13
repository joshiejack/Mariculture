package mariculture.core.blocks;

import mariculture.core.lib.Modules;
import mariculture.core.lib.WoodMeta;
import net.minecraft.block.material.Material;

public class BlockWood extends BlockDecorative {
	public BlockWood(int i) {
		super(i, Material.wood);
	}
	
	@Override
	public boolean isActive(int meta) {
		switch(meta) {
		case WoodMeta.BASE_WOOD:
			return true;
		case WoodMeta.POLISHED_LOG:
			return Modules.fishery.isActive();
		case WoodMeta.POLISHED_PLANK:
			return Modules.fishery.isActive();
		default:
			return true;
		}
	}
	
	@Override
	public int getMetaCount() {
		return WoodMeta.COUNT;
	}
}
