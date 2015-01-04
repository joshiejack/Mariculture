package mariculture.plugins;

import highlands.api.HighlandsBiomes;

import java.lang.reflect.Field;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.Environment.Salinity;
import mariculture.core.handlers.LogHandler;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.world.biome.BiomeGenBase;

import org.apache.logging.log4j.Level;

public class PluginHighlands extends Plugin {
    public PluginHighlands(String name) {
        super(name);
    }

    private void addBiome(BiomeGenBase biome, int temp, Salinity salt) {
        if (biome != null) {
            MaricultureHandlers.environment.addEnvironment(biome, salt, temp);
        }
    }

    private static BiomeGenBase getBiome(String str) {
        try {
            Field field = HighlandsBiomes.class.getField(str);
            return (BiomeGenBase) field.get(null);
        } catch (Exception e) {
            LogHandler.log(Level.INFO, "Couldn't find the Highlands Biome " + str + " : This is NOT an issue, do not report!");
            return null;
        }
    }

    @Override
    public void preInit() {
        return;
    }

    @Override
    public void init() {
        return;
    }

    @Override
    public void postInit() {
        boolean found = true;
        try {
            Class.forName("highlands.api.HighlandsBiomes");
        } catch (ClassNotFoundException e) {
            found = false;
        }

        if (found) {
            addBiome(getBiome("woodsMountains"), 12, Salinity.FRESH);
            addBiome(getBiome("highlandsb"), 6, Salinity.FRESH);
            addBiome(getBiome("tundra"), 1, Salinity.FRESH);
            addBiome(getBiome("cliffs"), 13, Salinity.FRESH);
            addBiome(getBiome("pinelands"), 6, Salinity.FRESH);
            addBiome(getBiome("autumnForest"), 8, Salinity.FRESH);
            addBiome(getBiome("alps"), 1, Salinity.FRESH);
            addBiome(getBiome("tallPineForest"), 5, Salinity.FRESH);
            addBiome(getBiome("meadow"), 10, Salinity.FRESH);
            addBiome(getBiome("savannah"), 28, Salinity.FRESH);
            addBiome(getBiome("tropics"), 27, Salinity.FRESH);
            addBiome(getBiome("outback"), 24, Salinity.FRESH);
            addBiome(getBiome("woodlands"), 11, Salinity.FRESH);
            addBiome(getBiome("bog"), 7, Salinity.FRESH);
            addBiome(getBiome("redwoodForest"), 6, Salinity.FRESH);
            addBiome(getBiome("dunes"), 50, Salinity.FRESH);
            addBiome(getBiome("lowlands"), 9, Salinity.FRESH);
            addBiome(getBiome("sahel"), 23, Salinity.FRESH);
            addBiome(getBiome("birchHills"), 9, Salinity.FRESH);
            addBiome(getBiome("tropicalIslands"), 26, Salinity.FRESH);
            addBiome(getBiome("rainforest"), 25, Salinity.FRESH);
            addBiome(getBiome("estuary"), 10, Salinity.BRACKISH);
            addBiome(getBiome("badlands"), 35, Salinity.FRESH);
            addBiome(getBiome("flyingMountains"), 8, Salinity.FRESH);
            addBiome(getBiome("snowMountains"), 2, Salinity.FRESH);
            addBiome(getBiome("rockMountains"), 3, Salinity.FRESH);
            addBiome(getBiome("desertMountains"), 40, Salinity.FRESH);
            addBiome(getBiome("steppe"), 20, Salinity.FRESH);
            addBiome(getBiome("glacier"), -1, Salinity.FRESH);
            addBiome(getBiome("ocean2"), 4, Salinity.SALINE);
            addBiome(getBiome("forestIsland"), 10, Salinity.FRESH);
            addBiome(getBiome("jungleIsland"), 25, Salinity.FRESH);
            addBiome(getBiome("desertIsland"), 35, Salinity.FRESH);
            addBiome(getBiome("volcanoIsland"), 30, Salinity.FRESH);
            addBiome(getBiome("snowIsland"), 3, Salinity.FRESH);
            addBiome(getBiome("windyIsland"), 8, Salinity.FRESH);
            addBiome(getBiome("valley"), 10, Salinity.FRESH);
            addBiome(getBiome("lake"), 10, Salinity.FRESH);
            addBiome(getBiome("mesa"), 36, Salinity.FRESH);
            addBiome(getBiome("baldHill"), 12, Salinity.FRESH);
            addBiome(getBiome("oasis"), 15, Salinity.FRESH);
            addBiome(getBiome("canyon"), 30, Salinity.FRESH);
            addBiome(getBiome("shrubland"), 25, Salinity.FRESH);
        }
    }
}
