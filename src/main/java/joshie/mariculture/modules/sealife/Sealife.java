package joshie.mariculture.modules.sealife;

import joshie.mariculture.core.util.annotation.MCLoader;
import joshie.mariculture.core.util.item.ItemComponent;
import joshie.mariculture.modules.sealife.blocks.BlockAnimal;
import joshie.mariculture.modules.sealife.blocks.BlockCoral;
import joshie.mariculture.modules.sealife.blocks.BlockPlant;
import joshie.mariculture.modules.sealife.blocks.BlockRock;
import joshie.mariculture.modules.sealife.lib.Pearl;
import joshie.mariculture.modules.sealife.world.WorldTypeOcean;
import joshie.mariculture.modules.sealife.world.biome.BiomeCoralReef;
import joshie.mariculture.modules.sealife.world.biome.BiomeKelpForest;
import joshie.mariculture.modules.sealife.world.biome.BiomeSeagrassMeadow;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;

import static joshie.mariculture.core.lib.CreativeOrder.PEARLS;

/** The Sealife Module is about bringing the oceans to life
 *  It's mostly plants, and critters, like coral, kelp, turtles adding to the atmosphere of the ocean,
 *  they'll have limited use, but it's about the aesthetic*/
@MCLoader
public class Sealife {
    public static final BlockAnimal ANIMAL = new BlockAnimal().register("animal");
    public static final BlockCoral CORAL = new BlockCoral().register("coral");
    public static final BlockPlant PLANTS = new BlockPlant().register("plant");
    public static final BlockRock ROCKS = new BlockRock().register("rock");
    public static final ItemComponent PEARL = new ItemComponent<>(PEARLS, Pearl.class).register("pearl");

    public static final WorldType OCEANS = new WorldTypeOcean();
    public static final Biome REEF = new BiomeCoralReef((new Biome.BiomeProperties("Coral Reef")).setBaseHeight(-0.5F).setHeightVariation(0.05F).setRainfall(0.5F).setWaterColor(0x279FBA));
    public static final Biome MEADOW = new BiomeSeagrassMeadow((new Biome.BiomeProperties("Seagrass Meadow")).setBaseHeight(-0.375F).setHeightVariation(0.005F).setRainfall(0.1F).setWaterColor(0x179FD1));
    public static final Biome FOREST = new BiomeKelpForest((new Biome.BiomeProperties("Kelp Forest")).setBaseHeight(-0.7F).setHeightVariation(0.075F).setRainfall(0.2F).setWaterColor(0x089BBC));

    public static void preInit() {
        registerBiome(50, "coral_reef", REEF);
        registerBiome(51, "seagrass_meadow", MEADOW);
        registerBiome(52, "kelp_forest", FOREST);
    }

    private static void registerBiome(int id, String name, Biome biome) {
        Biome.registerBiome(id, name, biome);
        BiomeDictionary.registerBiomeType(biome, Type.OCEAN);
        BiomeManager.oceanBiomes.add(biome);
        MinecraftForge.TERRAIN_GEN_BUS.register(biome);
    }
}
