package mariculture.api.core;

import mariculture.api.core.Environment.Salinity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public interface IEnvironmentHandler {
    //Registers a biomes Salinity and 'default' temperature
    public void addEnvironment(BiomeGenBase biome, Salinity salinity, int temperature);

    //Returns the Salinity at the coordinates
    public Salinity getSalinity(World world, int x, int z);

    //Returns the Temperature at the coordinates
    public int getTemperature(World world, int x, int y, int z);

    //Ignores the time of day
    public int getBiomeTemperature(World world, int x, int y, int z);
    
    //Whether the salinity and temperature falls within the ranges
    public boolean matches(Salinity biomeSalinity, int biomeTemp, Salinity checkSaltBase, int checkSaltVariation, int checkTempBase, int checkTempVariation);
}
