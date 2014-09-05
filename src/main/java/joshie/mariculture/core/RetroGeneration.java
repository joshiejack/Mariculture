package joshie.mariculture.core;

import java.lang.reflect.Field;
import java.util.ArrayList;

import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.core.MaricultureHandlers;
import joshie.mariculture.core.config.WorldGeneration.OreGen;
import joshie.mariculture.core.config.WorldGeneration.WorldGen;
import joshie.mariculture.core.handlers.LogHandler;
import joshie.mariculture.core.handlers.WorldEventHandler;
import joshie.mariculture.core.handlers.WorldGenHandler;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.world.GenerationHandler;
import joshie.maritech.handlers.MTWorldGen;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RetroGeneration {
    public static ArrayList<String> retro;

    public boolean doGen(RetroData data, String ore, Chunk chunk) {
        try {
            Field field = joshie.mariculture.core.config.WorldGeneration.RetroGen.class.getField(ore.toUpperCase());
            boolean isEnabled = field.getBoolean(joshie.mariculture.core.config.WorldGeneration.RetroGen.class);
            if (isEnabled || joshie.mariculture.core.config.WorldGeneration.RetroGen.ALL) if (!data.hasRetroGenned(ore, chunk)) if (data.setHasRetroGenned(ore, chunk)) return true;
        } catch (Exception e) {
            e.printStackTrace();
            ore = ore.substring(0, 1).toUpperCase() + ore.substring(1);
            LogHandler.log(Level.WARN, "Retro-Gen of " + ore + " Failed");
        }

        return false;
    }

    @SubscribeEvent
    public void onChunk(ChunkEvent.Load event) {
        if (WorldEventHandler.isBlacklisted(event.world.provider.dimensionId)) return;

        if (!event.world.isRemote) {
            Chunk chunk = event.getChunk();
            //Load the Retro Data, if it's null OR the stored key does not match the configs key, load in a new data set
            RetroData data = (RetroData) event.world.mapStorage.loadData(RetroData.class, "retrogen-mariculture");
            if (data == null || data != null && data.getLastKey() != joshie.mariculture.core.config.WorldGeneration.RetroGen.KEY) {
                data = new RetroData();
                event.world.setItemData("retrogen-mariculture", data);
            }

            if (retro == null) {
                retro = new ArrayList<String>();
            }

            if (chunk.isChunkLoaded) {
                try {
                    int x = chunk.xPosition * 16;
                    int z = chunk.zPosition * 16;
                    
                    try {
                        if(Loader.isModLoaded("MariTech")) {
                            if (OreGen.NATURAL_GAS_ON && doGen(data, "gas", chunk)) {
                                MTWorldGen.generateGas(chunk.worldObj, chunk.worldObj.rand, x, z);
                            }
                        }
                    } catch (Exception e) {}

                    if (OreGen.BAUXITE_ON && doGen(data, "bauxite", chunk)) {
                        WorldGenHandler.generateBauxite(chunk.worldObj, chunk.worldObj.rand, x, z);
                    }

                    if (OreGen.COPPER_ON && doGen(data, "copper", chunk)) {
                        WorldGenHandler.generateCopper(chunk.worldObj, chunk.worldObj.rand, x, z);
                    }

                    if (WorldGen.OYSTER_ENABLED && doGen(data, "oyster", chunk)) {
                        WorldGenHandler.generateOyster(chunk.worldObj, chunk.worldObj.rand, x, z);
                    }

                    if (Modules.isActive(Modules.worldplus) && MaricultureHandlers.environment.getSalinity(chunk.worldObj, x, z) == Salinity.SALINE) {
                        if (WorldGen.KELP_FOREST_ENABLED && doGen(data, "kelp", chunk)) {
                            GenerationHandler.kelpGenerator.generate(chunk.worldObj, chunk.worldObj.rand, x, 0, z);
                        }

                        if (WorldGen.CORAL_REEF_ENABLED && doGen(data, "coralreef", chunk)) {
                            GenerationHandler.generateCoralReef(chunk.worldObj, chunk.worldObj.rand, chunk.xPosition, chunk.zPosition);
                        }

                        if (WorldGen.ANCIENT_SAND_ENABLED && doGen(data, "ancient", chunk)) {
                            GenerationHandler.generateAncientSand(chunk.worldObj, chunk.worldObj.rand, chunk.xPosition, chunk.zPosition);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
