package mariculture.api.fishery.fish;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.Fishing;
import net.minecraft.world.World;

public enum EnumFishGroup {
	NETHER(0, new EnumBiomeType[] { EnumBiomeType.HELL }),
	RIVER(1, new EnumBiomeType[] { EnumBiomeType.COLD, EnumBiomeType.FROZEN,
			EnumBiomeType.ENDER, EnumBiomeType.HOT, EnumBiomeType.NORMAL, EnumBiomeType.MUSHROOM }),
	OCEAN(2, new EnumBiomeType[] { EnumBiomeType.COLD, EnumBiomeType.FROZEN, EnumBiomeType.FROZEN_OCEAN,
			EnumBiomeType.ENDER, EnumBiomeType.NORMAL, EnumBiomeType.OCEAN, EnumBiomeType.MUSHROOM }),
	TROPICAL(3, new EnumBiomeType[] { EnumBiomeType.HOT, EnumBiomeType.NORMAL, EnumBiomeType.OCEAN, EnumBiomeType.ARID }),
	DOMESTICATED(4, new EnumBiomeType[] { EnumBiomeType.ARID, EnumBiomeType.COLD, EnumBiomeType.ENDER,
			EnumBiomeType.FROZEN, EnumBiomeType.FROZEN_OCEAN, EnumBiomeType.HOT, EnumBiomeType.MUSHROOM,
			EnumBiomeType.NORMAL, EnumBiomeType.OCEAN }),
	ENDER(5, new EnumBiomeType[] { EnumBiomeType.ENDER, EnumBiomeType.COLD }),
	AMAZONIAN(6, new EnumBiomeType[] { EnumBiomeType.HOT, EnumBiomeType.ARID }),
	FLATFISH(7, new EnumBiomeType[] { EnumBiomeType.COLD, EnumBiomeType.ENDER, EnumBiomeType.FROZEN,
			EnumBiomeType.FROZEN_OCEAN, EnumBiomeType.MUSHROOM, EnumBiomeType.NORMAL, EnumBiomeType.OCEAN, EnumBiomeType.HELL }),
	JELLY(8, new EnumBiomeType[] { EnumBiomeType.OCEAN, EnumBiomeType.FROZEN_OCEAN, EnumBiomeType.ENDER }),
	NEMO(9, new EnumBiomeType[] { EnumBiomeType.OCEAN, EnumBiomeType.HOT, EnumBiomeType.MUSHROOM });

	private final int id;
	private final EnumBiomeType[] biomes;

	private EnumFishGroup(int id, EnumBiomeType[] biomes) {
		this.id = id;
		this.biomes = biomes;
	}

	public int getID() {
		return this.id;
	}

	private EnumBiomeType[] getBiomes() {
		return this.biomes;
	}

	public boolean canLive(World world, int x, int y, int z) {
		return Fishing.fishHelper.canLive(world.getWorldChunkManager().getBiomeGenAt(x, z), getBiomes(),
				world.getBlockTileEntity(x, y, z));
	}
}
