package joshie.mariculture.world;

import static joshie.mariculture.core.helpers.RecipeHelper.addBleachRecipe;
import static joshie.mariculture.core.helpers.RecipeHelper.addCrushRecipe;
import static joshie.mariculture.core.helpers.RecipeHelper.addSoakRecipe;
import joshie.mariculture.api.core.CoralRegistry;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.helpers.RecipeHelper;
import joshie.mariculture.core.lib.CoralMeta;
import joshie.mariculture.core.lib.CraftingMeta;
import joshie.mariculture.core.lib.Dye;
import joshie.mariculture.core.lib.FoodMeta;
import joshie.mariculture.core.lib.GlassMeta;
import joshie.mariculture.core.lib.GroundMeta;
import joshie.mariculture.core.lib.MaterialsMeta;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.core.lib.Modules.RegistrationModule;
import joshie.mariculture.fishery.Fishery;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class WorldPlus extends RegistrationModule {
    public static final String OCEAN_CHEST = "oceanFloorChest";
    public static Block plantGrowable;
    public static Block plantStatic;

    @Override
    public void registerHandlers() {
        GameRegistry.registerWorldGenerator(new GenerationHandler(), 2);
    }

    @Override
    public void registerBlocks() {
        plantGrowable = new BlockCoral(true, "plant_growable_").setStepSound(Block.soundTypeGrass).setResistance(0.1F).setBlockName("plant.growable");
        plantStatic = new BlockCoral(false, "plant_static_").setStepSound(Block.soundTypeGrass).setResistance(0.1F).setBlockName("plant.static");
    }

    @Override
    public void registerItems() {
        return;
    }

    @Override
    public void registerOther() {
        if (MaricultureTab.tabWorld != null) {
            MaricultureTab.tabWorld.setIcon(new ItemStack(plantStatic, 1, 7), false);
        }

        WorldPlus.registerCoral(new ItemStack(plantStatic, 1, CoralMeta.LIGHT_BLUE), "LightBlue");
        WorldPlus.registerCoral(new ItemStack(plantStatic, 1, CoralMeta.YELLOW), "Yellow");
        WorldPlus.registerCoral(new ItemStack(plantStatic, 1, CoralMeta.MAGENTA), "Magenta");
        WorldPlus.registerCoral(new ItemStack(plantStatic, 1, CoralMeta.BROWN), "Brown");
        WorldPlus.registerCoral(new ItemStack(plantStatic, 1, CoralMeta.ORANGE), "Orange");
        WorldPlus.registerCoral(new ItemStack(plantStatic, 1, CoralMeta.PINK), "Pink");
        WorldPlus.registerCoral(new ItemStack(plantStatic, 1, CoralMeta.PURPLE), "Purple");
        WorldPlus.registerCoral(new ItemStack(plantStatic, 1, CoralMeta.RED), "Red");
        OreDictionary.registerOre("coralWhite", new ItemStack(plantStatic, 1, CoralMeta.WHITE));
        OreDictionary.registerOre("coralGray", new ItemStack(plantStatic, 1, CoralMeta.GREY));
        OreDictionary.registerOre("coralLightGray", new ItemStack(plantStatic, 1, CoralMeta.LIGHT_GREY));
        OreDictionary.registerOre("plantKelp", new ItemStack(plantStatic, 1, CoralMeta.KELP));
    }

    @Override
    public void registerRecipes() {
        // Coral > Dye Recipes
        addCrushRecipe(new ItemStack(Core.materials, 1, MaterialsMeta.DYE_BROWN), "coralBrown", false);
        addCrushRecipe(new ItemStack(Core.materials, 1, MaterialsMeta.DYE_RED), "coralRed", false);
        addCrushRecipe(new ItemStack(Core.materials, 1, MaterialsMeta.DYE_YELLOW), "coralYellow", false);
        addCrushRecipe(new ItemStack(Items.dye, 1, Dye.LIGHT_BLUE), "coralLightBlue", false);
        addCrushRecipe(new ItemStack(Items.dye, 1, Dye.MAGENTA), "coralMagenta", false);
        addCrushRecipe(new ItemStack(Items.dye, 1, Dye.ORANGE), "coralOrange", false);
        addCrushRecipe(new ItemStack(Items.dye, 1, Dye.PINK), "coralPink", false);
        addCrushRecipe(new ItemStack(Items.dye, 1, Dye.PURPLE), "coralPurple", false);
        addCrushRecipe(new ItemStack(Items.dye, 1, Dye.GREY), "coralGray", false);
        addCrushRecipe(new ItemStack(Items.dye, 1, Dye.LIGHT_GREY), "coralLightGray", false);
        addCrushRecipe(new ItemStack(Core.materials, 1, MaterialsMeta.DYE_WHITE), "coralWhite", false);
        addCrushRecipe(new ItemStack(Core.materials, 1, MaterialsMeta.DYE_GREEN), "plantKelp", true);

        addBleachRecipe(new ItemStack(plantStatic, 1, CoralMeta.LIGHT_BLUE), new ItemStack(plantStatic, 1, CoralMeta.GREY), 5);
        addBleachRecipe(new ItemStack(plantStatic, 1, CoralMeta.YELLOW), new ItemStack(plantStatic, 1, CoralMeta.GREY), 5);
        addBleachRecipe(new ItemStack(plantStatic, 1, CoralMeta.MAGENTA), new ItemStack(plantStatic, 1, CoralMeta.GREY), 5);
        addBleachRecipe(new ItemStack(plantStatic, 1, CoralMeta.BROWN), new ItemStack(plantStatic, 1, CoralMeta.GREY), 5);
        addBleachRecipe(new ItemStack(plantStatic, 1, CoralMeta.ORANGE), new ItemStack(plantStatic, 1, CoralMeta.GREY), 5);
        addBleachRecipe(new ItemStack(plantStatic, 1, CoralMeta.PINK), new ItemStack(plantStatic, 1, CoralMeta.GREY), 5);
        addBleachRecipe(new ItemStack(plantStatic, 1, CoralMeta.PURPLE), new ItemStack(plantStatic, 1, CoralMeta.GREY), 5);
        addBleachRecipe(new ItemStack(plantStatic, 1, CoralMeta.RED), new ItemStack(plantStatic, 1, CoralMeta.GREY), 5);
        addBleachRecipe(new ItemStack(plantStatic, 1, CoralMeta.GREY), new ItemStack(plantStatic, 1, CoralMeta.LIGHT_GREY), 5);
        addBleachRecipe(new ItemStack(plantStatic, 1, CoralMeta.LIGHT_GREY), new ItemStack(plantStatic, 1, CoralMeta.WHITE), 5);

        addSoakRecipe(new ItemStack(plantStatic, 1, CoralMeta.LIGHT_BLUE), new ItemStack(plantGrowable, 1, CoralMeta.LIGHT_BLUE), 1);
        addSoakRecipe(new ItemStack(plantStatic, 1, CoralMeta.YELLOW), new ItemStack(plantGrowable, 1, CoralMeta.YELLOW), 1);
        addSoakRecipe(new ItemStack(plantStatic, 1, CoralMeta.MAGENTA), new ItemStack(plantGrowable, 1, CoralMeta.MAGENTA), 1);
        addSoakRecipe(new ItemStack(plantStatic, 1, CoralMeta.BROWN), new ItemStack(plantGrowable, 1, CoralMeta.BROWN), 1);
        addSoakRecipe(new ItemStack(plantStatic, 1, CoralMeta.ORANGE), new ItemStack(plantGrowable, 1, CoralMeta.ORANGE), 1);
        addSoakRecipe(new ItemStack(plantStatic, 1, CoralMeta.PINK), new ItemStack(plantGrowable, 1, CoralMeta.PINK), 1);
        addSoakRecipe(new ItemStack(plantStatic, 1, CoralMeta.PURPLE), new ItemStack(plantGrowable, 1, CoralMeta.PURPLE), 1);
        addSoakRecipe(new ItemStack(plantStatic, 1, CoralMeta.RED), new ItemStack(plantGrowable, 1, CoralMeta.RED), 1);
        addSoakRecipe(new ItemStack(plantStatic, 1, CoralMeta.GREY), new ItemStack(plantGrowable, 1, CoralMeta.GREY), 5);
        addSoakRecipe(new ItemStack(plantStatic, 1, CoralMeta.LIGHT_GREY), new ItemStack(plantGrowable, 1, CoralMeta.LIGHT_GREY), 1);
        addSoakRecipe(new ItemStack(plantStatic, 1, CoralMeta.WHITE), new ItemStack(plantGrowable, 1, CoralMeta.WHITE), 1);

        //Kelp Wrap Recipe
        RecipeHelper.add9x9Recipe(new ItemStack(Core.food, 1, FoodMeta.KELP_WRAP), "plantKelp");
        RecipeHelper.addShapeless(new ItemStack(Core.crafting, 1, CraftingMeta.SEEDS_KELP), new Object[] { new ItemStack(plantStatic, 1, CoralMeta.KELP) });
        RecipeHelper.addSmelting(new ItemStack(Core.glass, 1, GlassMeta.HEAT), new ItemStack(Core.sands, 1, GroundMeta.ANCIENT), 0.025F);

        addOceanChestLoot();
    }

    private void addOceanChestLoot() {
        ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Items.bone, 7, 0), 5, 10, 25));
        ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Items.skull, 1, 0), 5, 5, 5));
        ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Items.skull, 1, 1), 2, 3, 3));
        ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Blocks.tnt), 2, 4, 4));
        ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Items.paper, 3, 0), 2, 3, 20));
        ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Items.book, 1, 0), 2, 3, 15));
        ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Items.experience_bottle, 1, 0), 5, 10, 20));
        ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Items.writable_book, 1, 0), 1, 4, 4));
        ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Items.map, 1, 0), 1, 1, 5));
        ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Items.gold_ingot, 1, 0), 10, 20, 10));
        ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Items.diamond, 1, 0), 1, 2, 3));
        ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_TITANIUM), 1, 3, 4));

        if (Modules.isActive(Modules.fishery)) {
            ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Fishery.rodReed), 1, 2, 6));
            ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Fishery.rodWood), 1, 2, 4));
            ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Fishery.rodTitanium), 1, 1, 2));
        } else {
            ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Items.fish, 1, 0), 5, 5, 5));
            ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Items.fish, 1, 1), 5, 5, 5));
            ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Items.fish, 1, 2), 5, 5, 5));
            ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Items.fish, 1, 3), 5, 5, 5));
        }
    }

    public static void registerCoral(ItemStack stack, String color) {
        CoralRegistry.register(stack);
        OreDictionary.registerOre("coral" + color, stack);
    }
}
