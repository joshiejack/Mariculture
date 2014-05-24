package mariculture.plugins;

import static mariculture.core.lib.ItemLib.stick;
import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.RecipesSmelting;
import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.MetalRates;
import mariculture.core.util.Fluids;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.item.ItemStack;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.registry.GameRegistry;

public class PluginRailcraft extends Plugin {

	@Override
	public void preInit() {
		
	}
	
	private void addSteelMelting() {
		try {
			ItemStack steelBoots = new ItemStack(GameRegistry.findItem("Railcraft", "armor.steel.boots"));
			ItemStack steelHelm = new ItemStack(GameRegistry.findItem("Railcraft", "armor.steel.helmet"));
			ItemStack steelPants = new ItemStack(GameRegistry.findItem("Railcraft", "armor.steel.legs"));
			ItemStack steelChest = new ItemStack(GameRegistry.findItem("Railcraft", "armor.steel.plate"));
			ItemStack steelPick = new ItemStack(GameRegistry.findItem("Railcraft", "tool.steel.pickaxe"));
			ItemStack steelShovel = new ItemStack(GameRegistry.findItem("Railcraft", "tool.steel.shovel"));
			ItemStack steelAxe = new ItemStack(GameRegistry.findItem("Railcraft", "tool.steel.axe"));
			ItemStack steelSword = new ItemStack(GameRegistry.findItem("Railcraft", "tool.steel.sword"));
			ItemStack steelHoe = new ItemStack(GameRegistry.findItem("Railcraft", "tool.steel.hoe"));
			RecipesSmelting.addRecipe(Fluids.steel, MetalRates.TOOLS, new Object[] {steelPick, steelShovel, steelAxe, steelSword, steelHoe}, RecipesSmelting.steel, new ItemStack(stick), 1);
			RecipesSmelting.addRecipe(Fluids.steel, MetalRates.ARMOR, new Object[] {steelHelm, steelChest, steelPants, steelBoots}, RecipesSmelting.steel, null, 0);
		} catch (Exception e) {
			LogHandler.log(Level.INFO, "Failed to add railcraft steel items to the melting list");
		}
	}

	@Override
	public void init() {
		addSteelMelting();
		ItemStack coalCoke = GameRegistry.findItemStack("Railcraft", "fuel.coke", 1);
		ItemStack coalCokeBlock = GameRegistry.findItemStack("Railcraft", "cube.coke", 1);
		MaricultureHandlers.crucible.addFuel(coalCokeBlock, new FuelInfo(2000, 576, 32400));
		MaricultureHandlers.crucible.addFuel(coalCoke, new FuelInfo(2000, 64, 3600));
	}

	@Override
	public void postInit() {
		return;
	}
}
