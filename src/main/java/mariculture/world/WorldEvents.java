package mariculture.world;

import java.util.logging.Level;

import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.Extra;
import mariculture.core.lib.WorldGeneration;
import mariculture.world.terrain.MapGenCavesWater;
import mariculture.world.terrain.MapGenMineshaftsDisabled;
import mariculture.world.terrain.MapGenRavineWater;
import mariculture.world.terrain.MapGenRavineWaterBOP;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;

public class WorldEvents {
	@ForgeSubscribe
	public void onWorldGen(InitMapGenEvent event) {
		if(WorldGeneration.NO_MINESHAFTS) {
			if(event.type == EventType.MINESHAFT) {
				try {
					event.newGen = new MapGenMineshaftsDisabled();
				} catch (Exception e) {
					LogHandler.log(Level.INFO, "Mariculture couldn't remove mineshafts from oceans");
				}
			}
		}
			
		if(WorldGeneration.WATER_CAVES) {
			if(event.type == EventType.CAVE) {
				try {
					event.newGen = new MapGenCavesWater();
				} catch (Exception e) {
					LogHandler.log(Level.INFO, "Mariculture couldn't add water filled caves");
				}
			}
		}
			
		if(WorldGeneration.WATER_RAVINES) {
			if(event.type == EventType.RAVINE) {
				try {
					if(Extra.HAS_BOP) {
						event.newGen = new MapGenRavineWaterBOP();
					} else {
						event.newGen = new MapGenRavineWater();
					}
				} catch (Exception e) {
					LogHandler.log(Level.INFO, "Mariculture couldn't add water filled ravines");
				}
			}
		}
	}
	
	@ForgeSubscribe
	public void onWorldDecorate(Decorate event) {
		
	}
}
