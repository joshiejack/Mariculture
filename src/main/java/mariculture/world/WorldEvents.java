package mariculture.world;

import mariculture.core.config.WorldGeneration.WorldGen;
import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.Extra;
import mariculture.world.terrain.MapGenCavesWater;
import mariculture.world.terrain.MapGenMineshaftsDisabled;
import mariculture.world.terrain.MapGenRavineWater;
import mariculture.world.terrain.MapGenRavineWaterBOP;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class WorldEvents {
	@SubscribeEvent
	public void onWorldGen(InitMapGenEvent event) {
		if(WorldGen.NO_MINESHAFTS) {
			if(event.type == EventType.MINESHAFT) {
				try {
					event.newGen = new MapGenMineshaftsDisabled();
				} catch (Exception e) {
					LogHandler.log(Level.TRACE, "Failed to remove mineshafts from oceans");
				}
			}
		}
			
		if(WorldGen.WATER_CAVES) {
			if(event.type == EventType.CAVE) {
				try {
					event.newGen = new MapGenCavesWater();
				} catch (Exception e) {
					LogHandler.log(Level.TRACE, "Failed to add water filled caves");
				}
			}
		}
			
		if(WorldGen.WATER_RAVINES) {
			if(event.type == EventType.RAVINE) {
				try {
					if(Extra.HAS_BOP) {
						event.newGen = new MapGenRavineWaterBOP();
					} else {
						event.newGen = new MapGenRavineWater();
					}
				} catch (Exception e) {
					LogHandler.log(Level.TRACE, "Failed to add water filled ravines");
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onWorldDecorate(Decorate event) {
		
	}
}
