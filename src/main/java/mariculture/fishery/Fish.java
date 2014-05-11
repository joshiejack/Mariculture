package mariculture.fishery;

import java.util.List;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ILootHandler.LootQuality;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.util.FluidDictionary;
import mariculture.world.WorldPlus;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraftforge.fluids.FluidRegistry;

public class Fish {
	public static void init(ItemStack male, ItemStack female, ItemStack raw, FishSpecies species) {
		species.addFishProducts();
		
		//Adds all fish as loot
		LootQuality quality = species.getLootQuality();
		int catchChance = species.getCatchChance();
		List<EnumBiomeType> catchBiomes = species.getCatchableBiomes();
		if(species.caughtAsRaw()) {
			Fishing.loot.addLoot(quality, new WeightedRandomFishable(raw, catchChance), catchBiomes);
		} else {
			Fishing.loot.addLoot(quality, new WeightedRandomFishable(male, catchChance), catchBiomes);
			Fishing.loot.addLoot(quality, new WeightedRandomFishable(female , catchChance), catchBiomes);
		}
		
		//Add fish oil
		if(species.getFishOilVolume() > 0) {
			RecipeHelper.addMelting(raw, species.getMeltingPoint(),  FluidRegistry.getFluidStack(FluidDictionary.fish_oil, (int) (species.getFishOilVolume() * 1000)), 
										species.getLiquifiedProduct(), species.getLiquifiedProductChance());
		}
		
		//Adds all the basic recipes for each fish
		int fishSize = species.getFishMealSize();
		if(fishSize > 0) {
			ItemStack kelp = Modules.isActive(Modules.worldplus)? new ItemStack(WorldPlus.plantStatic, 1, CoralMeta.KELP): new ItemStack(Items.dye, 1, Dye.GREEN);
			RecipeHelper.addShapedRecipe(new ItemStack(Core.food, (int)Math.ceil(fishSize/1.5), FoodMeta.SUSHI), new Object[] {
				" K ", "KFK", " K ", 'K', kelp, 'F', raw
			});
			
			RecipeHelper.addShapelessRecipe(new ItemStack(Core.food, (int)Math.ceil(fishSize/2), FoodMeta.MISO_SOUP_1), new Object[] {
				Items.bowl, kelp, raw, Blocks.brown_mushroom, Blocks.red_mushroom
			});

			RecipeHelper.addShapelessRecipe(new ItemStack(Core.materials, fishSize, MaterialsMeta.FISH_MEAL), new Object[] { raw });
		}
	}
}
