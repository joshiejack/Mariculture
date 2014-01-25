package mariculture.plugins.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import mariculture.Mariculture;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.items.ItemWorked;
import mariculture.core.lib.Text;
import mariculture.factory.blocks.TileFishSorter;
import mariculture.fishery.items.ItemFishy;
import mariculture.fishery.items.ItemFishyFood;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;

public class NEIFishBreedingMutationHandler extends NEIBase {	
	public class CachedBreedingRecipe extends CachedRecipe {
		double chance;
		PositionedStack mother;
		PositionedStack father;
		PositionedStack baby;

		public CachedBreedingRecipe(ItemStack mother, ItemStack father, ItemStack baby, int chance) {
			this.mother = new PositionedStack(father, 11, 16);
			this.father = new PositionedStack(mother, 67, 16);
			this.baby = new PositionedStack(baby, 133, 16);
			this.chance = ((double)chance)/10;
		}

		@Override
		public PositionedStack getResult() {
			return baby;
		}

		@Override
		public List<PositionedStack> getIngredients() {
            ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
            stacks.add(mother);
            stacks.add(father);
            return stacks;
        }
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("fishbreeding") && getClass() == NEIFishBreedingMutationHandler.class) {
			HashMap<List<FishSpecies>, FishSpecies> mutations = Fishing.mutation.getMutations();
			for(Entry<List<FishSpecies>, FishSpecies> fish: mutations.entrySet()) {
				int chance = Fishing.mutation.getMutationChance(fish.getKey().get(0), fish.getKey().get(1));
				ItemStack mother = Fishing.fishHelper.makePureFish(fish.getKey().get(0));
				ItemStack father = Fishing.fishHelper.makePureFish(fish.getKey().get(1));
				ItemStack baby = Fishing.fishHelper.makePureFish(fish.getValue());
				arecipes.add(new CachedBreedingRecipe(mother, father, baby, chance));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		HashMap<List<FishSpecies>, FishSpecies> mutations = Fishing.mutation.getMutations();
		for(Entry<List<FishSpecies>, FishSpecies> fish: mutations.entrySet()) {
			if(isAFish(result, fish.getValue().fishID)) {
				ItemStack baby = Fishing.fishHelper.makePureFish(fish.getValue());
				int chance = Fishing.mutation.getMutationChance(fish.getKey().get(0), fish.getKey().get(1));
				ItemStack mother = Fishing.fishHelper.makePureFish(fish.getKey().get(0));
				ItemStack father = Fishing.fishHelper.makePureFish(fish.getKey().get(1));
				arecipes.add(new CachedBreedingRecipe(mother, father, baby, chance));
			}
		}
	}
	
	public static boolean isAFish(ItemStack stack, int id) {
		if(stack.getItem() instanceof ItemFishyFood && stack.getItemDamage() == id)
			return true;
		if(stack.getItem() instanceof ItemFishy && stack.hasTagCompound() 
				&& TileFishSorter.hasSameFishDNA(stack, Fishing.fishHelper.makePureFish(FishSpecies.speciesList.get(id)))) {
			return true;
		}
		
		return false;
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if(ingredient.getItem() instanceof ItemWorked) {
			return;
		}
		
		HashMap<List<FishSpecies>, FishSpecies> mutations = Fishing.mutation.getMutations();
		for(Entry<List<FishSpecies>, FishSpecies> fish: mutations.entrySet()) {
			FishSpecies fishM = fish.getKey().get(0);
			FishSpecies fishF = fish.getKey().get(1);
			ItemStack mother = Fishing.fishHelper.makePureFish(fishM);
			ItemStack father = Fishing.fishHelper.makePureFish(fishF);
			if(isAFish(ingredient, fishM.fishID) || isAFish(ingredient, fishF.fishID)) {
				int chance = Fishing.mutation.getMutationChance(fishM, fishF);
				ItemStack baby = Fishing.fishHelper.makePureFish(fish.getValue());
				arecipes.add(new CachedBreedingRecipe(mother, father, baby, chance));
			}
		}
	}

	@Override
	public void drawExtras(int id) {
		CachedBreedingRecipe cache = (CachedBreedingRecipe) arecipes.get(id);
		Minecraft.getMinecraft().fontRenderer.drawString(Text.GREY + cache.chance + "%", 94, 38, 0);
	}
	
	@Override
	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(95, 16, 22, 16), "fishbreeding"));
	}

	@Override
	public String getRecipeName() {
		return "Fish Breeding";
	}

	@Override
	public String getGuiTexture() {
		return new ResourceLocation(Mariculture.modid, "textures/gui/nei/breeding.png").toString();
	}

	@Override
	public String getOverlayIdentifier() {
		return "fishbreeding";
	}
	
	@Override
	public boolean isOverItem(GuiRecipe gui, int id) {
		return false;
	}
}
