package mariculture.core.blocks;

import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;

public class BlockStationaryWater extends BlockStationary {
	public BlockStationaryWater(int i, Material mat) {
		super(i, mat);
		disableStats();
	}
}
