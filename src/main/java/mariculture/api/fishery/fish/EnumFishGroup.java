package mariculture.api.fishery.fish;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.Fishing;
import net.minecraft.world.World;

public enum EnumFishGroup {
	//Nether Fish
	NETHER(0, new EnumBiomeType[] { EnumBiomeType.HELL, EnumBiomeType.ARID }, 
			  new EnumSalinityType[] { EnumSalinityType.MAGIC }),
	
	//River Fish
	RIVER(1, new EnumBiomeType[] { EnumBiomeType.NORMAL, EnumBiomeType.COLD, EnumBiomeType.FROZEN }, 
			 new EnumSalinityType[] { EnumSalinityType.FRESH }),
			
	//Ocean Fish
	OCEAN(2, new EnumBiomeType[] { EnumBiomeType.OCEAN, EnumBiomeType.FROZEN_OCEAN, EnumBiomeType.COLD, EnumBiomeType.FROZEN }, 
			 new EnumSalinityType[] { EnumSalinityType.SALT, EnumSalinityType.FRESH }),
			
	//Tropical Fish
	TROPICAL(3, new EnumBiomeType[] { EnumBiomeType.HOT, EnumBiomeType.OCEAN }, 
				new EnumSalinityType[] { EnumSalinityType.FRESH, EnumSalinityType.SALT }),
				
	//Domesticated Fish
	DOMESTICATED(4, new EnumBiomeType[] { EnumBiomeType.NORMAL, EnumBiomeType.HOT, EnumBiomeType.OCEAN, EnumBiomeType.FROZEN_OCEAN,
										  EnumBiomeType.COLD, EnumBiomeType.MUSHROOM, EnumBiomeType.FROZEN}, 
					new EnumSalinityType[] { EnumSalinityType.FRESH, EnumSalinityType.SALT, EnumSalinityType.MAGIC }),
			
	//Ender Fish
	ENDER(5, new EnumBiomeType[] { EnumBiomeType.ENDER, EnumBiomeType.COLD }, 
			 new EnumSalinityType[] { EnumSalinityType.MAGIC }),
	
	//Amazon River Fish
	AMAZONIAN(6, new EnumBiomeType[] { EnumBiomeType.HOT }, 
				 new EnumSalinityType[] { EnumSalinityType.FRESH }),
	
	//Flat fish
	FLATFISH(7, new EnumBiomeType[] { EnumBiomeType.FROZEN_OCEAN, EnumBiomeType.OCEAN, EnumBiomeType.HOT, EnumBiomeType.ARID }, 
				new EnumSalinityType[] { EnumSalinityType.SALT, EnumSalinityType.MAGIC }),
				
	//Jellyfish
	JELLY(8, new EnumBiomeType[] { EnumBiomeType.OCEAN, EnumBiomeType.FROZEN_OCEAN, EnumBiomeType.COLD },  
			 new EnumSalinityType[] { EnumSalinityType.SALT, EnumSalinityType.MAGIC }),
			 
	//Nemo Fish
	NEMO(9, new EnumBiomeType[] { EnumBiomeType.MUSHROOM, EnumBiomeType.OCEAN }, 
			new EnumSalinityType[] { EnumSalinityType.MAGIC, EnumSalinityType.SALT });

	private final int id;
	private final EnumBiomeType[] biomes;
	private final EnumSalinityType[] salinity;

	private EnumFishGroup(int id, EnumBiomeType[] biomes, EnumSalinityType[] salinity) {
		this.id = id;
		this.biomes = biomes;
		this.salinity = salinity;
	}

	public int getID() {
		return this.id;
	}
	
	public EnumBiomeType[] getBiomes() {
		return this.biomes;
	}

	public boolean canLive(World world, int x, int y, int z) {
		return Fishing.fishHelper.canLive(world.getWorldChunkManager().getBiomeGenAt(x, z), getBiomes(), getSalinityRequired(), world.getTileEntity(x, y, z));
	}

	public EnumSalinityType[] getSalinityRequired() {
		return salinity;
	}
}
