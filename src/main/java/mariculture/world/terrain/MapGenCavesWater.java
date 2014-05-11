package mariculture.world.terrain;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.MaricultureHandlers;
import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenCaves;

public class MapGenCavesWater extends MapGenCaves
{	
    //Exception biomes to make sure we generate like vanilla
    private boolean isExceptionBiome(BiomeGenBase biome) {
        if (biome == BiomeGenBase.mushroomIsland) return true;
        if (biome == BiomeGenBase.beach) return true;
        if (biome == BiomeGenBase.desert) return true;
        return false;
    }

    @Override
    protected void digBlock(byte[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop) {
    	BiomeGenBase biome = worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
		if (y < 63 && MaricultureHandlers.environment.getSalinity(worldObj, x + chunkX * 16, z + chunkZ * 16) == Salinity.SALINE) {
	        int top    = (isExceptionBiome(biome) ? Block.grass.blockID : biome.topBlock);
	        int filler = (isExceptionBiome(biome) ? Block.dirt.blockID  : biome.fillerBlock);
	        int block  = data[index];
	
	        if (block == Block.stone.blockID || block == filler || block == top) {
	            if (y < 10)  {
	                data[index] = (byte)Block.lavaMoving.blockID;
	            } else {
	            	data[index] = (byte)Block.waterMoving.blockID;
	
	                if (foundTop && data[index - 1] == filler) {
	                    data[index - 1] = (byte)top;
	                }
	            }
	        }
    	} else {
    		super.digBlock(data, index, x, y, z, chunkX, chunkZ, foundTop);
    	}
    }
}
