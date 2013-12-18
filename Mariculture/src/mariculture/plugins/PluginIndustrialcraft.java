package mariculture.plugins;

import ic2.api.item.Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import mariculture.core.Core;
import mariculture.core.RecipesSmelting;
import mariculture.core.items.ItemBattery;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules;
import mariculture.core.lib.UtilMeta;
import mariculture.core.util.FluidDictionary;
import mariculture.core.util.RecipeRemover;
import mariculture.fishery.Fishery;
import mariculture.world.WorldPlus;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class PluginIndustrialcraft {
	public static void init() {
		ItemStack bronzeChest = Items.getItem("bronzeChestplate");
		ItemStack bronzeLegs = Items.getItem("bronzeLeggings");
		ItemStack bronzeBoots = Items.getItem("bronzeBoots");
		ItemStack bronzeHelm = Items.getItem("bronzeHelmet");
		ItemStack bronzePick = Items.getItem("bronzePickaxe");
		ItemStack bronzeAxe = Items.getItem("bronzeAxe");
		ItemStack bronzeShovel = Items.getItem("bronzeShovel");
		ItemStack bronzeSword = Items.getItem("bronzeSword");
		ItemStack bronzeHoe = Items.getItem("bronzeHoe");
		ItemStack rubber = Items.getItem("rubber");
			
		RecipesSmelting.addRecipe(FluidDictionary.bronze, MetalRates.TOOLS, new Object[] { 
				bronzePick, bronzeShovel, bronzeAxe, bronzeSword, bronzeHoe }, RecipesSmelting.bronze, new ItemStack(Item.stick), 1);
			
		RecipesSmelting.addRecipe(FluidDictionary.bronze, MetalRates.ARMOR, new Object[] { 
				bronzeHelm, bronzeChest, bronzeLegs, bronzeBoots }, RecipesSmelting.bronze, null, 0);

		Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(Fishery.fishy, 1, OreDictionary.WILDCARD_VALUE)),
				null, new ItemStack(Core.materials, 2, MaterialsMeta.FISH_MEAL));
			
		RecipeRemover.remove(new ItemStack(Core.craftingItem, 1, CraftingMeta.NEOPRENE));
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.NEOPRENE), true, new Object[] {
					"IPI", "PEP", "IPI", 
					Character.valueOf('I'), "itemRubber", 
					Character.valueOf('P'), new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE), 
					Character.valueOf('E'), new ItemStack(Core.liquidContainers, 1, FluidContainerMeta.BOTTLE_GAS)}));

		if (Modules.world.isActive()) {
			Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(WorldPlus.coral, 1,
					CoralMeta.CORAL_BLUE)), null,
					new ItemStack(Item.dyePowder, 2, Dye.LIGHT_BLUE));
			Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(WorldPlus.coral, 1,
					CoralMeta.CORAL_BRAIN)), null,
					new ItemStack(Core.materials, 2, MaterialsMeta.DYE_YELLOW));
			Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(WorldPlus.coral, 1,
					CoralMeta.CORAL_CANDYCANE)), null,
					new ItemStack(Item.dyePowder, 2, Dye.MAGENTA));
			Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(WorldPlus.coral, 1,
					CoralMeta.CORAL_CUCUMBER)), null,
					new ItemStack(Core.materials, 2, MaterialsMeta.DYE_BROWN));
			Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(WorldPlus.coral, 1,
					CoralMeta.CORAL_ORANGE)), null,
					new ItemStack(Item.dyePowder, 2, Dye.ORANGE));
			Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(WorldPlus.coral, 1,
					CoralMeta.CORAL_PINK)), null,
					new ItemStack(Item.dyePowder, 2, Dye.PINK));
			Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(WorldPlus.coral, 1,
					CoralMeta.CORAL_PURPLE)), null,
					new ItemStack(Item.dyePowder, 2, Dye.PURPLE));
			Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(WorldPlus.coral, 1,
					CoralMeta.CORAL_RED)), null,
					new ItemStack(Item.dyePowder, 2, Dye.RED));
		}
	}
}
