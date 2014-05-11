package mariculture.plugins.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import mariculture.Mariculture;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.items.ItemWorked;
import mariculture.core.lib.Text;
import mariculture.factory.blocks.TileFishSorter;
import mariculture.fishery.Fish;
import mariculture.fishery.FishHelper;
import mariculture.fishery.FishMutationHandler.Mutation;
import mariculture.fishery.items.ItemFishy;
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

		public CachedBreedingRecipe(ItemStack mother, ItemStack father, ItemStack baby, double chance) {
			this.mother = new PositionedStack(father, 11, 16);
			this.father = new PositionedStack(mother, 67, 16);
			this.baby = new PositionedStack(baby, 133, 16);
			this.chance = ((double)chance);
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
			ArrayList<Mutation> mutations = Fishing.mutation.getMutations();
			for(Mutation mute: mutations) {
				ItemStack baby = Fishing.fishHelper.makePureFish(Fishing.fishHelper.getSpecies(mute.baby));
				ItemStack father = Fish.gender.addDNA(Fishing.fishHelper.makePureFish(Fishing.fishHelper.getSpecies(mute.father)), FishHelper.MALE);
				ItemStack mother = Fish.gender.addDNA(Fishing.fishHelper.makePureFish(Fishing.fishHelper.getSpecies(mute.mother)), FishHelper.FEMALE);
				arecipes.add(new CachedBreedingRecipe(mother, father, baby, mute.chance));
			}
		} else {
			super.loadCraftingRecipes(outputId, results);
		}
	}

	@Override
	public void loadCraftingRecipes(ItemStack result) {
		ArrayList<Mutation> mutations = Fishing.mutation.getMutations();
		for(Mutation mute: mutations) {
			FishSpecies species = Fishing.fishHelper.getSpecies(mute.baby);
			if(isAFish(result, species)) {
				ItemStack baby = Fishing.fishHelper.makePureFish(Fishing.fishHelper.getSpecies(mute.baby));
				ItemStack father = Fish.gender.addDNA(Fishing.fishHelper.makePureFish(Fishing.fishHelper.getSpecies(mute.father)), FishHelper.MALE);
				ItemStack mother = Fish.gender.addDNA(Fishing.fishHelper.makePureFish(Fishing.fishHelper.getSpecies(mute.mother)), FishHelper.FEMALE);
				arecipes.add(new CachedBreedingRecipe(mother, father, baby, mute.chance));
			}
		}
	}
	
	public static boolean isAFish(ItemStack stack, FishSpecies fish) {
		if(stack.getItem() instanceof ItemFishy && stack.hasTagCompound() && TileFishSorter.hasSameFishDNA(stack, Fishing.fishHelper.makePureFish(fish))) {
			return true;
		}
		
		return false;
	}

	@Override
	public void loadUsageRecipes(ItemStack ingredient) {
		if(ingredient.getItem() instanceof ItemWorked) {
			return;
		}
		
		ArrayList<Mutation> mutations = Fishing.mutation.getMutations();
		for(Mutation mute: mutations) {
			FishSpecies fSpecies = Fishing.fishHelper.getSpecies(mute.father);
			FishSpecies mSpecies = Fishing.fishHelper.getSpecies(mute.mother);
			if(isAFish(ingredient, fSpecies) || isAFish(ingredient, mSpecies)) {
				ItemStack baby = Fishing.fishHelper.makePureFish(Fishing.fishHelper.getSpecies(mute.baby));
				ItemStack father = Fish.gender.addDNA(Fishing.fishHelper.makePureFish(Fishing.fishHelper.getSpecies(mute.father)), FishHelper.MALE);
				ItemStack mother = Fish.gender.addDNA(Fishing.fishHelper.makePureFish(Fishing.fishHelper.getSpecies(mute.mother)), FishHelper.FEMALE);
				arecipes.add(new CachedBreedingRecipe(mother, father, baby, mute.chance));
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
