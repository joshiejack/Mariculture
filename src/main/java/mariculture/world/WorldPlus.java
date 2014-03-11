package mariculture.world;

import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.ReflectionHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.Module;
import mariculture.factory.Factory;
import mariculture.fishery.Fishery;
import mariculture.world.terrain.BiomeGenSandyBeach;
import mariculture.world.terrain.BiomeGenSandyOcean;
import mariculture.world.terrain.BiomeGenSandyRiver;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenRiver;
import net.minecraft.world.biome.BiomeGenBase.Height;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class WorldPlus extends Module {
	public static boolean isActive;
	
	@Override
	public boolean isActive() {
		return this.isActive;
	}
	
	@Override
	public void setActive(boolean active) {
		isActive = active;
	}
	
	public static final String OCEAN_CHEST = "oceanFloorChest";

	public static Block coral;
	public static Block plantGrowable;
	public static Block plantStatic;

	@Override
	public void registerHandlers() {
		GameRegistry.registerWorldGenerator(new WorldGen(), 2);
		if(!Loader.isModLoaded("ATG")) {
			MinecraftForge.TERRAIN_GEN_BUS.register(new WorldEvents());
		}
	}

	@Override
	public void registerBlocks() {
		coral = new BlockCoral().setStepSound(Block.soundTypeGrass).setResistance(0.1F).setBlockName("coral");
		RegistryHelper.register(new Object[] { coral });
	}

	@Override
	public void registerItems() {
		OreDictionary.registerOre("dyeYellow", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_YELLOW));
		OreDictionary.registerOre("dyeRed", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_RED));
		OreDictionary.registerOre("dyeBrown", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_BROWN));
		OreDictionary.registerOre("dyeGreen", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_GREEN));
		OreDictionary.registerOre("dyeWhite", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_WHITE));
		//OreDictionary.registerOre("dyeBlack", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_BLACK));
	}
	
	@Override
	public void registerOther() {
		RegistryHelper.registerCoral(new ItemStack(coral, 1, CoralMeta.CORAL_BLUE), "LightBlue");
		RegistryHelper.registerCoral(new ItemStack(coral, 1, CoralMeta.CORAL_BRAIN), "Yellow");
		RegistryHelper.registerCoral(new ItemStack(coral, 1, CoralMeta.CORAL_CANDYCANE), "Magenta");
		RegistryHelper.registerCoral(new ItemStack(coral, 1, CoralMeta.CORAL_CUCUMBER), "Brown");
		RegistryHelper.registerCoral(new ItemStack(coral, 1, CoralMeta.CORAL_ORANGE), "Orange");
		RegistryHelper.registerCoral(new ItemStack(coral, 1, CoralMeta.CORAL_PINK), "Pink");
		RegistryHelper.registerCoral(new ItemStack(coral, 1, CoralMeta.CORAL_PURPLE), "Purple");
		RegistryHelper.registerCoral(new ItemStack(coral, 1, CoralMeta.CORAL_RED), "Red");  
		OreDictionary.registerOre("coralWhite", new ItemStack(coral, 1, CoralMeta.CORAL_WHITE));
		OreDictionary.registerOre("coralGray", new ItemStack(coral, 1, CoralMeta.CORAL_GREY));
		OreDictionary.registerOre("coralLightGray", new ItemStack(coral, 1, CoralMeta.CORAL_LIGHT_GREY));
		OreDictionary.registerOre("plantKelp", new ItemStack(coral, 1, CoralMeta.KELP));
		
		ReflectionHelper.setFinalStatic(BiomeGenBase.class, "ocean", "field_76771_b", (new BiomeGenSandyOcean(0)).setColor(112).setBiomeName("Ocean").setHeight(new Height(-1.4F, 0.25F)));
		ReflectionHelper.setFinalStatic(BiomeGenBase.class, "deepOcean", "", (new BiomeGenSandyOcean(24)).setColor(48).setBiomeName("Deep Ocean").setHeight(new Height(-1.95F, 0F)));
		ReflectionHelper.setFinalStatic(BiomeGenBase.class, "beach", "", (new BiomeGenSandyBeach(16)).setColor(16440917).setBiomeName("Beach").setTemperatureRainfall(0.8F, 0.4F).setHeight(new Height(0.0F, 0.025F)));
		ReflectionHelper.setFinalStatic(BiomeGenBase.class, "coldBeach", "", (new BiomeGenSandyBeach(26)).setColor(16445632).setBiomeName("Cold Beach").setTemperatureRainfall(0.05F, 0.3F).setHeight(new Height(0.0F, 0.025F)).setEnableSnow());
		ReflectionHelper.setFinalStatic(BiomeGenBase.class, "frozenRiver", "", (new BiomeGenSandyRiver(11)).setColor(10526975).setBiomeName("FrozenRiver").setEnableSnow().setHeight(new Height(-1.2F, 0.0F)).setTemperatureRainfall(0.0F, 0.5F));
		ReflectionHelper.setFinalStatic(BiomeGenBase.class, "river", "", (new BiomeGenSandyRiver(7)).setColor(255).setBiomeName("River").setHeight(new Height(-1F, 0.0F)));
	}

	@Override
	public void addRecipes() {
		// Coral > Dye Recipes
		RecipeHelper.addCrushRecipe(new ItemStack(Core.materials, 1, MaterialsMeta.DYE_BROWN), "coralBrown", false);
		RecipeHelper.addCrushRecipe(new ItemStack(Core.materials, 1, MaterialsMeta.DYE_RED), "coralRed", false);
		RecipeHelper.addCrushRecipe(new ItemStack(Core.materials, 1, MaterialsMeta.DYE_YELLOW), "coralYellow", false);
		RecipeHelper.addCrushRecipe(new ItemStack(Items.dye, 1, Dye.LIGHT_BLUE), "coralLightBlue", false);
		RecipeHelper.addCrushRecipe(new ItemStack(Items.dye, 1, Dye.MAGENTA), "coralMagenta", false);
		RecipeHelper.addCrushRecipe(new ItemStack(Items.dye, 1, Dye.ORANGE), "coralOrange", false);
		RecipeHelper.addCrushRecipe(new ItemStack(Items.dye, 1, Dye.PINK), "coralPink", false);
		RecipeHelper.addCrushRecipe(new ItemStack(Items.dye, 1, Dye.PURPLE), "coralPurple", false);
		RecipeHelper.addCrushRecipe(new ItemStack(Items.dye, 1, Dye.GREY), "coralGray", false);
		RecipeHelper.addCrushRecipe(new ItemStack(Items.dye, 1, Dye.LIGHT_GREY), "coralLightGray", false);
		RecipeHelper.addCrushRecipe(new ItemStack(Core.materials, 1, MaterialsMeta.DYE_WHITE), "coralWhite", false);
		RecipeHelper.addCrushRecipe(new ItemStack(Core.materials, 1, MaterialsMeta.DYE_GREEN), "plantKelp", true);
		
		RecipeHelper.addBleachRecipe(new ItemStack(coral, 1, CoralMeta.CORAL_BLUE), new ItemStack(coral, 1, CoralMeta.CORAL_GREY), 5);
		RecipeHelper.addBleachRecipe(new ItemStack(coral, 1, CoralMeta.CORAL_BRAIN), new ItemStack(coral, 1, CoralMeta.CORAL_GREY), 5);
		RecipeHelper.addBleachRecipe(new ItemStack(coral, 1, CoralMeta.CORAL_CANDYCANE), new ItemStack(coral, 1, CoralMeta.CORAL_GREY), 5);
		RecipeHelper.addBleachRecipe(new ItemStack(coral, 1, CoralMeta.CORAL_CUCUMBER), new ItemStack(coral, 1, CoralMeta.CORAL_GREY), 5);
		RecipeHelper.addBleachRecipe(new ItemStack(coral, 1, CoralMeta.CORAL_ORANGE), new ItemStack(coral, 1, CoralMeta.CORAL_GREY), 5);
		RecipeHelper.addBleachRecipe(new ItemStack(coral, 1, CoralMeta.CORAL_PINK), new ItemStack(coral, 1, CoralMeta.CORAL_GREY), 5);
		RecipeHelper.addBleachRecipe(new ItemStack(coral, 1, CoralMeta.CORAL_PURPLE), new ItemStack(coral, 1, CoralMeta.CORAL_GREY), 5);
		RecipeHelper.addBleachRecipe(new ItemStack(coral, 1, CoralMeta.CORAL_RED), new ItemStack(coral, 1, CoralMeta.CORAL_GREY), 5);
		RecipeHelper.addBleachRecipe(new ItemStack(coral, 1, CoralMeta.CORAL_GREY), new ItemStack(coral, 1, CoralMeta.CORAL_LIGHT_GREY), 5);
		RecipeHelper.addBleachRecipe(new ItemStack(coral, 1, CoralMeta.CORAL_LIGHT_GREY), new ItemStack(coral, 1, CoralMeta.CORAL_WHITE), 5);
		
		//Kelp Wrap Recipe
		RecipeHelper.add9x9Recipe(new ItemStack(Core.food, 1, FoodMeta.KELP_WRAP), "plantKelp");
		
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

		if (Modules.factory.isActive()) {
			ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Factory.fludd), 1, 2, 1));
		}

		if (Modules.fishery.isActive()) {
			ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Fishery.rodReed), 1, 2, 6));
			ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Fishery.rodWood), 1, 2, 4));
			ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Fishery.rodTitanium), 1, 1, 2));

			for (int i = 0; i < FishSpecies.speciesList.size(); i++) {
				int[] fishRarity = FishSpecies.speciesList.get(i).getChestGenChance();
				if (fishRarity != null && fishRarity.length == 3) {
					ItemStack fish = new ItemStack(Fishery.fishyFood, 1, FishSpecies.speciesList.get(i).fishID);
					ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(fish, fishRarity[0], fishRarity[1], fishRarity[2]));
				}
			}
		}
	}
}
