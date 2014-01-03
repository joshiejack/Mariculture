package mariculture.world;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;

import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.handlers.LogHandler;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.BlockIds;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.GroundMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.Module;
import mariculture.core.lib.WorldGeneration;
import mariculture.factory.Factory;
import mariculture.fishery.Fishery;
import mariculture.world.terrain.BiomeGenSandyOcean;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
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

	@Override
	public void registerHandlers() {
		GameRegistry.registerWorldGenerator(new WorldGen());
		MinecraftForge.TERRAIN_GEN_BUS.register(new WorldEvents());
	}

	@Override
	public void registerBlocks() {
		coral = new BlockCoral(BlockIds.coral).setStepSound(Block.soundGrassFootstep).setResistance(0.1F).setUnlocalizedName("coral");
		Item.itemsList[BlockIds.coral] = new ItemCoral(BlockIds.coral - 256, coral).setUnlocalizedName("coral");
		OreDictionary.registerOre("plantKelp", new ItemStack(coral, 1, CoralMeta.KELP));
		RegistryHelper.register(new Object[] { coral });
	}

	@Override
	public void registerItems() {
		OreDictionary.registerOre("dyeYellow", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_YELLOW));
		OreDictionary.registerOre("dyeRed", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_RED));
		OreDictionary.registerOre("dyeBrown", new ItemStack(Core.materials, 1, MaterialsMeta.DYE_BROWN));
	}
	
	@Override
	public void registerOther() {
		RegistryHelper.registerCoral(new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_BLUE), "LightBlue");
		RegistryHelper.registerCoral(new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_BRAIN), "Yellow");
		RegistryHelper.registerCoral(new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_CANDYCANE), "Magenta");
		RegistryHelper.registerCoral(new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_CUCUMBER), "Brown");
		RegistryHelper.registerCoral(new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_ORANGE), "Orange");
		RegistryHelper.registerCoral(new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_PINK), "Pink");
		RegistryHelper.registerCoral(new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_PURPLE), "Purple");
		RegistryHelper.registerCoral(new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_RED), "Red");  
		
		if(WorldGeneration.DEEP_OCEAN) {
			addDeepOcean();
		}
	}
	
	private void addDeepOcean() {
		try {
			Field field = BiomeGenBase.class.getField("ocean");
			Object newValue = (new BiomeGenSandyOcean(0)).setColor(112).setBiomeName("Ocean").setMinMaxHeight(-1.85F, 0.4F);
		    field.setAccessible(true);

		    Field modifiersField = Field.class.getDeclaredField("modifiers");
		    modifiersField.setAccessible(true);
		    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		    field.set(null, newValue);
		} catch (Exception e) {
			LogHandler.log(Level.INFO, "Mariculture failed to adjust the ocean depth");
		}
	}

	@Override
	public void addRecipes() {
		// Coral > Dye Recipes
		CraftingManager.getInstance().getRecipeList()
				.add(new ShapelessOreRecipe(new ItemStack(Core.materials, 1, MaterialsMeta.DYE_BROWN), new Object[] { "coralBrown" }));
		CraftingManager.getInstance().getRecipeList()
				.add(new ShapelessOreRecipe(new ItemStack(Core.materials, 1, MaterialsMeta.DYE_RED), new Object[] { "coralRed" }));
		CraftingManager.getInstance().getRecipeList()
				.add(new ShapelessOreRecipe(new ItemStack(Core.materials, 1, MaterialsMeta.DYE_YELLOW), new Object[] { "coralYellow" }));
		CraftingManager.getInstance().getRecipeList()
				.add(new ShapelessOreRecipe(new ItemStack(Item.dyePowder, 1, Dye.LIGHT_BLUE), new Object[] { "coralLightBlue" }));
		CraftingManager.getInstance().getRecipeList()
				.add(new ShapelessOreRecipe(new ItemStack(Item.dyePowder, 1, Dye.MAGENTA), new Object[] { "coralMagenta" }));
		CraftingManager.getInstance().getRecipeList()
				.add(new ShapelessOreRecipe(new ItemStack(Item.dyePowder, 1, Dye.ORANGE), new Object[] { "coralOrange" }));
		CraftingManager.getInstance().getRecipeList()
				.add(new ShapelessOreRecipe(new ItemStack(Item.dyePowder, 1, Dye.PINK), new Object[] { "coralPink" }));
		CraftingManager.getInstance().getRecipeList()
				.add(new ShapelessOreRecipe(new ItemStack(Item.dyePowder, 1, Dye.PURPLE), new Object[] { "coralPurple" }));

		addOceanChestLoot();
	}

	private void addOceanChestLoot() {
		ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Item.bone, 7, 0), 5, 10, 25));
		ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Item.skull, 1, 0), 5, 5, 5));
		ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Item.skull, 1, 1), 2, 3, 3));
		ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Block.tnt), 2, 4, 4));
		ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Item.paper, 3, 0), 2, 3, 20));
		ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Item.book, 1, 0), 2, 3, 15));
		ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Item.expBottle, 1, 0), 5, 10, 20));
		ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Item.writableBook, 1, 0), 1, 4, 4));
		ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Item.map, 1, 0), 1, 1, 5));
		ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Item.ingotGold, 1, 0), 10, 20, 10));
		ChestGenHooks.addItem(WorldPlus.OCEAN_CHEST, new WeightedRandomChestContent(new ItemStack(Item.diamond, 1, 0), 1, 2, 3));
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
