package mariculture.plugins;

import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.RecipesSmelting;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.OresMeta;
import mariculture.core.util.FluidDictionary;
import mariculture.core.util.RecipeRemover;
import mariculture.plugins.Plugins.Plugin;
import mods.railcraft.api.crafting.RailcraftCraftingManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class PluginRailcraft extends Plugin {
	public void init() {
		String id = "Railcraft";

		RecipeRemover.remove(new ItemStack(Core.craftingItem, 3, CraftingMeta.ALUMINUM_SHEET));
		RecipeRemover.remove(new ItemStack(Core.craftingItem, 3, CraftingMeta.TITANIUM_SHEET));

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

		MaricultureHandlers.smelter.addFuel(coalCokeBlock, new FuelInfo(2000, 576, 32400));
		MaricultureHandlers.smelter.addFuel(coalCoke, new FuelInfo(2000, 64, 3600));

		ItemStack input = new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE);
		ItemStack output = new ItemStack(Item.dyePowder, 1, Dye.BONE);

		RailcraftCraftingManager.rockCrusher.createNewRecipe(input, true, false).addOutput(output, 0.1F);
	}

	@Override
	public void preInit() {
		
	}

	@Override
	public void postInit() {
		for(ItemStack ingot: OreDictionary.getOres("ingotAluminum")) {
			RailcraftCraftingManager.rollingMachine.addRecipe(new ItemStack(Core.craftingItem, 3,
					CraftingMeta.ALUMINUM_SHEET),
					new Object[] { "## ", "## ", Character.valueOf('#'),
							(ingot) });
		}
		
		for(ItemStack ingot: OreDictionary.getOres("ingotTitanium")) {
			RailcraftCraftingManager.rollingMachine.addRecipe(new ItemStack(Core.craftingItem, 3,
					CraftingMeta.TITANIUM_SHEET),
					new Object[] { "## ", "## ", Character.valueOf('#'),
							(ingot) });
		}
	}
}
