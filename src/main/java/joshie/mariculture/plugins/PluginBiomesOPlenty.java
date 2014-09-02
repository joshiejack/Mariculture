package joshie.mariculture.plugins;

import java.lang.reflect.Field;

import joshie.mariculture.api.core.Environment.Salinity;
import joshie.mariculture.api.core.MaricultureHandlers;
import joshie.mariculture.core.handlers.LogHandler;
import joshie.mariculture.core.handlers.OreDicHandler;
import joshie.mariculture.core.helpers.RecipeHelper;
import joshie.mariculture.core.lib.Extra;
import joshie.mariculture.core.lib.ItemLib;
import joshie.mariculture.plugins.Plugins.Plugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

import org.apache.logging.log4j.Level;

import biomesoplenty.api.content.BOPCBiomes;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class PluginBiomesOPlenty extends Plugin {
    public PluginBiomesOPlenty(String name) {
        super(name);
    }

    public static enum Biome {
        KELP, CORAL
    }

    private void addBiome(BiomeGenBase biome, int temp, Salinity salt) {
        if (biome != null) {
            MaricultureHandlers.environment.addEnvironment(biome, salt, temp);
        }
    }

    @Override
    public void preInit() {
        Extra.HAS_BOP = Loader.isModLoaded("BiomesOPlenty");
        OreDicHandler.registerWildcard(new ItemStack(GameRegistry.findBlock("BiomesOPlenty", "planks")), new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14 });
        OreDicHandler.registerWildcard(new ItemStack(GameRegistry.findBlock("BiomesOPlenty", "saplings")), new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 });
        OreDicHandler.registerWildcard(new ItemStack(GameRegistry.findBlock("BiomesOPlenty", "colorizedSaplings")), new Integer[] { 0, 1, 2, 3, 4, 5, 6 });
        OreDicHandler.registerWildcard(new ItemStack(GameRegistry.findBlock("BiomesOPlenty", "woodenSingleSlab1")), new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7 });
        OreDicHandler.registerWildcard(new ItemStack(GameRegistry.findBlock("BiomesOPlenty", "woodenSingleSlab2")), new Integer[] { 0, 1, 2, 3, 4 });
        OreDicHandler.registerWildcard(new ItemStack(GameRegistry.findBlock("BiomesOPlenty", "colorizedLeaves1")), new Integer[] { 0, 1, 2, 3 });
        OreDicHandler.registerWildcard(new ItemStack(GameRegistry.findBlock("BiomesOPlenty", "colorizedLeaves2")), new Integer[] { 0, 1, 2 });
        OreDicHandler.registerWildcard(new ItemStack(GameRegistry.findBlock("BiomesOPlenty", "leaves1")), new Integer[] { 0, 1, 2, 3 });
        OreDicHandler.registerWildcard(new ItemStack(GameRegistry.findBlock("BiomesOPlenty", "leaves2")), new Integer[] { 0, 1, 2, 3 });
        OreDicHandler.registerWildcard(new ItemStack(GameRegistry.findBlock("BiomesOPlenty", "leaves3")), new Integer[] { 0, 1, 2, 3 });
        OreDicHandler.registerWildcard(new ItemStack(GameRegistry.findBlock("BiomesOPlenty", "leaves4")), new Integer[] { 0, 1 });
        OreDicHandler.registerWildcard(new ItemStack(GameRegistry.findBlock("BiomesOPlenty", "appleLeaves")), new Integer[] { 0 });
        OreDicHandler.registerWildcard(new ItemStack(GameRegistry.findBlock("BiomesOPlenty", "persimmonLeaves")), new Integer[] { 0 });
    }

    private static BiomeGenBase getBiome(String str) {
        try {
            Field field = BOPCBiomes.class.getField(str);
            return (BiomeGenBase) field.get(null);
        } catch (Exception e) {
            LogHandler.log(Level.INFO, "Couldn't find the BiomesOPlenty Biome " + str + " : This is NOT an issue, do not report!");
            return null;
        }
    }

    @Override
    public void init() {
        boolean found = true;
        try {
            Class.forName("biomesoplenty.api.content.BOPCBiomes");
        } catch (ClassNotFoundException e) {
            found = false;
        }

        if (found) {
            addBiome(getBiome("alps"), -5, Salinity.FRESH);
            addBiome(getBiome("alpsForest"), -1, Salinity.FRESH);
            addBiome(getBiome("arctic"), -1, Salinity.SALINE);
            addBiome(getBiome("bambooForest"), 20, Salinity.FRESH);
            addBiome(getBiome("bayou"), 15, Salinity.FRESH);
            addBiome(getBiome("bog"), 7, Salinity.FRESH);
            addBiome(getBiome("borealForest"), 11, Salinity.FRESH);
            addBiome(getBiome("brushland"), 30, Salinity.FRESH);
            addBiome(getBiome("canyon"), 40, Salinity.FRESH);
            addBiome(getBiome("chaparral"), 6, Salinity.FRESH);
            addBiome(getBiome("cherryBlossomGrove"), 12, Salinity.FRESH);
            addBiome(getBiome("coniferousForest"), 5, Salinity.FRESH);
            addBiome(getBiome("snowyConiferousForest"), 2, Salinity.FRESH);
            addBiome(getBiome("crag"), 40, Salinity.FRESH);
            addBiome(getBiome("deadForest"), 40, Salinity.FRESH);
            addBiome(getBiome("deadSwamp"), 7, Salinity.FRESH);
            addBiome(getBiome("deciduousForest"), 8, Salinity.FRESH);
            addBiome(getBiome("dunes"), 50, Salinity.FRESH);
            addBiome(getBiome("fen"), 12, Salinity.FRESH);
            addBiome(getBiome("flowerField"), 11, Salinity.FRESH);
            addBiome(getBiome("frostForest"), 3, Salinity.FRESH);
            addBiome(getBiome("fungiForest"), 6, Salinity.FRESH);
            addBiome(getBiome("grassland"), 10, Salinity.FRESH);
            addBiome(getBiome("grove"), 10, Salinity.FRESH);
            addBiome(getBiome("heathland"), 7, Salinity.FRESH);
            addBiome(getBiome("highland"), 6, Salinity.FRESH);
            addBiome(getBiome("jadeCliffs"), 13, Salinity.BRACKISH);
            addBiome(getBiome("lavenderFields"), 10, Salinity.FRESH);
            addBiome(getBiome("lushDesert"), 37, Salinity.FRESH);
            addBiome(getBiome("lushSwamp"), 8, Salinity.FRESH);
            addBiome(getBiome("mapleWoods"), 12, Salinity.FRESH);
            addBiome(getBiome("marsh"), 7, Salinity.FRESH);
            addBiome(getBiome("meadow"), 10, Salinity.FRESH);
            addBiome(getBiome("moor"), 3, Salinity.FRESH);
            addBiome(getBiome("mountain"), 0, Salinity.FRESH);
            addBiome(getBiome("mysticGrove"), 10, Salinity.FRESH);
            addBiome(getBiome("ominousWoods"), 8, Salinity.FRESH);
            addBiome(getBiome("originValley"), 11, Salinity.FRESH);
            addBiome(getBiome("outback"), 24, Salinity.FRESH);
            addBiome(getBiome("prairie"), 20, Salinity.FRESH);
            addBiome(getBiome("rainforest"), 25, Salinity.FRESH);
            addBiome(getBiome("redwoodForest"), 5, Salinity.FRESH);
            addBiome(getBiome("sacredSprings"), 14, Salinity.FRESH);
            addBiome(getBiome("seasonalForest"), 10, Salinity.FRESH);
            addBiome(getBiome("shield"), 8, Salinity.FRESH);
            addBiome(getBiome("shrubland"), 17, Salinity.FRESH);
            addBiome(getBiome("silkglades"), 20, Salinity.FRESH);
            addBiome(getBiome("sludgepit"), 15, Salinity.FRESH);
            addBiome(getBiome("spruceWoods"), 8, Salinity.FRESH);
            addBiome(getBiome("steppe"), 20, Salinity.FRESH);
            addBiome(getBiome("temperateRainforest"), 15, Salinity.FRESH);
            addBiome(getBiome("thicket"), 10, Salinity.FRESH);
            addBiome(getBiome("timber"), 7, Salinity.FRESH);
            addBiome(getBiome("tropicalRainforest"), 28, Salinity.FRESH);
            addBiome(getBiome("tundra"), 1, Salinity.FRESH);
            addBiome(getBiome("wasteland"), 40, Salinity.FRESH);
            addBiome(getBiome("wetland"), 8, Salinity.FRESH);
            addBiome(getBiome("woodland"), 10, Salinity.FRESH);

            // Sub Biomes
            addBiome(getBiome("glacier"), 0, Salinity.FRESH);
            addBiome(getBiome("scrubland"), 32, Salinity.FRESH);
            addBiome(getBiome("oasis"), 22, Salinity.FRESH);
            addBiome(getBiome("quagmire"), 9, Salinity.FRESH);
            addBiome(getBiome("tropics"), 27, Salinity.FRESH);
            addBiome(getBiome("volcano"), 75, Salinity.FRESH);
            addBiome(getBiome("meadowForest"), 9, Salinity.FRESH);
            addBiome(getBiome("alpsForest"), -1, Salinity.FRESH);

            // Ocean Biomes
            addBiome(getBiome("kelpForest"), 24, Salinity.SALINE);

            // Nether Biomes
            addBiome(getBiome("corruptedSands"), 90, Salinity.FRESH);
            addBiome(getBiome("undergarden"), 78, Salinity.FRESH);
            addBiome(getBiome("phantasmagoricInferno"), 100, Salinity.FRESH);
            addBiome(getBiome("boneyard"), 79, Salinity.FRESH);
            addBiome(getBiome("visceralHeap"), 80, Salinity.FRESH);

            // River Biomes
            addBiome(getBiome("lushRiver"), 8, Salinity.FRESH);
            addBiome(getBiome("dryRiver"), 37, Salinity.FRESH);
        }

        Item flowers = GameRegistry.findItem("BiomesOPlenty", "flowers");
        Item flower2 = GameRegistry.findItem("BiomesOPlenty", "flowers2");
        Item mushroom = GameRegistry.findItem("BiomesOPlenty", "mushrooms");

        RecipeHelper.addCrushRecipe(RecipeHelper.asStack(ItemLib.cyanDye, 2), new ItemStack(flowers, 1, 1), true);
        RecipeHelper.addCrushRecipe(RecipeHelper.asStack(ItemLib.purpleDye, 2), new ItemStack(flowers, 1, 8), true);
        RecipeHelper.addCrushRecipe(RecipeHelper.asStack(ItemLib.purpleDye, 2), new ItemStack(flower2, 1, 3), true);
        RecipeHelper.addCrushRecipe(RecipeHelper.asStack(ItemLib.lightGreyDye, 2), new ItemStack(flowers, 1, 15), true);
        RecipeHelper.addCrushRecipe(RecipeHelper.asStack(ItemLib.pinkDye, 2), new ItemStack(flowers, 1, 6), true);
        RecipeHelper.addCrushRecipe(RecipeHelper.asStack(ItemLib.pinkDye, 2), new ItemStack(flower2, 1, 0), true);
        RecipeHelper.addCrushRecipe(RecipeHelper.asStack(ItemLib.limeDye, 2), new ItemStack(mushroom, 1, 3), true);
        RecipeHelper.addCrushRecipe(RecipeHelper.asStack(ItemLib.dandelionDye, 2), new ItemStack(flower2, 1, 4), true);
        RecipeHelper.addCrushRecipe(RecipeHelper.asStack(ItemLib.lightBlueDye, 2), new ItemStack(flowers, 1, 4), true);
        RecipeHelper.addCrushRecipe(RecipeHelper.asStack(ItemLib.lightBlueDye, 2), new ItemStack(flower2, 1, 7), true);
        RecipeHelper.addCrushRecipe(RecipeHelper.asStack(ItemLib.magentaDye, 2), new ItemStack(flowers, 1, 7), true);
        RecipeHelper.addCrushRecipe(RecipeHelper.asStack(ItemLib.orangeDye, 2), new ItemStack(flowers, 1, 5), true);
        RecipeHelper.addCrushRecipe(RecipeHelper.asStack(ItemLib.orangeDye, 2), new ItemStack(flower2, 1, 2), true);
    }

    @Override
    public void postInit() {
        return;
    }
}
