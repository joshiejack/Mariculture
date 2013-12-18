package mariculture.plugins;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.RecipesSmelting;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.OresMeta;
import mariculture.core.util.FluidDictionary;
import mariculture.core.util.RecipeRemover;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class PluginRailcraft {
	public static void init() {
		final String id = "Railcraft";

		RecipeRemover.remove(new ItemStack(Core.craftingItem, 3, CraftingMeta.ALUMINUM_SHEET));

		ItemStack railcraftGauge = GameRegistry.findItemStack(id, "machine.beta.tank.iron.gauge", 1);
		ItemStack steelBoots = GameRegistry.findItemStack(id, "armor.steel.boots", 1);
		ItemStack steelHelm = GameRegistry.findItemStack(id, "armor.steel.helmet", 1);
		ItemStack steelPants = GameRegistry.findItemStack(id, "armor.steel.legs", 1);
		ItemStack steelChest = GameRegistry.findItemStack(id, "armor.steel.plate", 1);
		ItemStack steelPick = GameRegistry.findItemStack(id, "tool.steel.pickaxe", 1);
		ItemStack steelShovel = GameRegistry.findItemStack(id, "tool.steel.shovel", 1);
		ItemStack steelAxe = GameRegistry.findItemStack(id, "tool.steel.axe", 1);
		ItemStack steelSword = GameRegistry.findItemStack(id, "tool.steel.sword", 1);
		ItemStack steelHoe = GameRegistry.findItemStack(id, "tool.steel.hoe", 1);
		ItemStack coalCoke = GameRegistry.findItemStack(id, "fuel.coke", 1);
		ItemStack coalCokeBlock = GameRegistry.findItemStack(id, "cube.coke", 1);

		RecipesSmelting.addRecipe(FluidDictionary.steel, MetalRates.TOOLS, new Object[] { 
				steelPick, steelShovel, steelAxe, steelSword, steelHoe }, RecipesSmelting.steel, new ItemStack(Item.stick), 1);
			
		RecipesSmelting.addRecipe(FluidDictionary.steel, MetalRates.ARMOR, new Object[] { 
				steelHelm, steelChest, steelPants, steelBoots }, RecipesSmelting.steel, null, 0);

		MaricultureHandlers.smelter.addFuel(coalCoke, 64, 2000);
		MaricultureHandlers.smelter.addFuel(coalCokeBlock, 576, 2000);
		RailcraftCraftingManager.rollingMachine.addRecipe(new ItemStack(Core.craftingItem, 3,
				CraftingMeta.ALUMINUM_SHEET),
				new Object[] { "## ", "## ", Character.valueOf('#'),
						(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_ALUMINUM)) });

		ItemStack input = new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE);
		ItemStack output = new ItemStack(Item.dyePowder, 1, Dye.BONE);

		RailcraftCraftingManager.rockCrusher.createNewRecipe(input, true, false).addOutput(output, 0.1F);
	}
}
