package mariculture.plugins;


import mariculture.api.core.MaricultureHandlers;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Plugincarbonization extends Plugin {
	public Plugincarbonization(String name) {
		super(name);
	}
	
	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		MaricultureHandlers.smelter.removeFuel(new ItemStack(Item.coal, 1, 0));
		MaricultureHandlers.smelter.removeFuel(new ItemStack(Item.coal, 1, 1));
		MaricultureHandlers.smelter.removeFuel(new ItemStack(Block.coalBlock, 1, 0));
		MaricultureHandlers.smelter.removeFuel("logWood");
		MaricultureHandlers.smelter.removeFuel("plankWood");

		MaricultureHandlers.smelter.addFuel("brickAnthracite", 56, 2000);
		MaricultureHandlers.smelter.addFuel(new ItemStack(Item.coal, 1, 0), 48, 1600);
		MaricultureHandlers.smelter.addFuel(new ItemStack(Block.coalBlock, 1, 0), 432, 1600);
		MaricultureHandlers.smelter.addFuel("brickBituminous", 32, 1400);
		MaricultureHandlers.smelter.addFuel(new ItemStack(Item.coal, 1, 1), 28, 1200);
		MaricultureHandlers.smelter.addFuel("brickSubBituminous", 24, 1000);
		MaricultureHandlers.smelter.addFuel("brickLignite", 16, 800);
		MaricultureHandlers.smelter.addFuel("brickPeat", 12, 600);
		MaricultureHandlers.smelter.addFuel("logWood", 8, 300);
		MaricultureHandlers.smelter.addFuel("plankWood", 4, 150);
	}

	@Override
	public void postInit() {
		
	}
}
