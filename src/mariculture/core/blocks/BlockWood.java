package mariculture.core.blocks;

import mariculture.core.lib.WoodMeta;
import net.minecraft.block.material.Material;

public class BlockWood extends BlockDecorative {
	public BlockWood(int i) {
		super(i, Material.wood);
	}
	
	@Override
	public boolean isActive(int meta) {
		return false;
	}
	
	@Override
	public int getMetaCount() {
		return WoodMeta.COUNT;
	}
}
