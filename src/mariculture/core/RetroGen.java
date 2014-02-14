package mariculture.core;

import mariculture.core.handlers.LogHandler;
import mariculture.core.handlers.WorldGenHandler;
import mariculture.core.lib.Modules;
import mariculture.core.lib.OreGeneration;
import mariculture.core.lib.RetroGeneration;
import mariculture.core.lib.WorldGeneration;
import mariculture.core.util.Rand;
import mariculture.world.WorldGen;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.ChunkEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Level;

public class RetroGen {
    public static ArrayList<String> retro;

	public boolean doGen(RetroData data, String ore, Chunk chunk) {
		try {
			Field field = mariculture.core.lib.RetroGeneration.class.getField(ore.toUpperCase());
			boolean isEnabled = field.getBoolean(mariculture.core.lib.RetroGeneration.class);
			if(isEnabled || RetroGeneration.ALL) {
				if(!data.hasRetroGenned(ore, chunk)) {
					if(data.setHasRetroGenned(ore, chunk)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ore = ore.substring(0, 1).toUpperCase() + ore.substring(1);
			LogHandler.log(Level.WARNING, "Mariculture's Retro-Gen of " + ore + " Failed");
		}
		
		return false;
	}
	
	@ForgeSubscribe
	public void onChunk(ChunkEvent.Load event) {
        if(event.world.provider.dimensionId == -1 || event.world.provider.dimensionId == 1) {
            return;
        }

		if(!event.world.isRemote) {
			Chunk chunk = event.getChunk();
			//Load the Retro Data, if it's null OR the stored key does not match the configs key, load in a new data set
			RetroData data = (RetroData) event.world.mapStorage.loadData(RetroData.class, "retrogen-mariculture");
			if(data == null || (data != null && data.getLastKey() != RetroGeneration.KEY)) {
				data = new RetroData();
				event.world.setItemData("retrogen-mariculture", data);
			}

            if(retro == null) {
                retro = new ArrayList<String>();
            }
			
			if(chunk.isChunkLoaded) {
				try {
					int x = chunk.xPosition * 16;
					int z = chunk.zPosition * 16;
					if(OreGeneration.NATURAL_GAS_ON && doGen(data, "gas", chunk))
						WorldGenHandler.generateGas(chunk.worldObj, Rand.rand, x, z);
					if(OreGeneration.BAUXITE_ON && doGen(data, "bauxite", chunk))
						WorldGenHandler.generateBauxite(chunk.worldObj, Rand.rand, x, z);
					if(OreGeneration.COPPER_ON && doGen(data, "copper", chunk))
						WorldGenHandler.generateCopper(chunk.worldObj, Rand.rand, x, z);
					if(OreGeneration.LIMESTONE && doGen(data, "limestone", chunk))
						WorldGenHandler.generateLimestone(chunk.worldObj, Rand.rand, x, z);
					if(OreGeneration.RUTILE && doGen(data, "rutile", chunk))
						WorldGenHandler.generateRutile(chunk.worldObj, Rand.rand, x, z);
					if(WorldGeneration.OYSTER_ENABLED && doGen(data, "oyster", chunk))
						WorldGenHandler.generateOyster(chunk.worldObj, Rand.rand, x, z);
					if(Modules.world.isActive()) {
						if(WorldGeneration.KELP_PATCH_ENABLED && doGen(data, "kelppatch", chunk))
							WorldGen.generateKelp(chunk.worldObj, Rand.rand, x, z);
						if(WorldGeneration.KELP_FOREST_ENABLED && doGen(data, "kelpforest", chunk))
							WorldGen.generateKelpForest(chunk.worldObj, Rand.rand, x, z);
						if(WorldGeneration.CORAL_ENABLED && doGen(data, "coralreef", chunk))
							WorldGen.generateCoral(chunk.worldObj, Rand.rand, x, z);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
