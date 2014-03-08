package mariculture.plugins;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.MaricultureHandlers;
import mariculture.factory.OreDicHandler;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import biomesoplenty.api.BOPBiomeHelper;
import biomesoplenty.api.BOPBlockHelper;

public class PluginBiomesOPlenty extends Plugin {
	public static enum Biome {
		KELP, CORAL
	}
	
	@Override
	public void registerWildcards() {		
		OreDicHandler.registerWildcard(new ItemStack(BOPBlockHelper.get("planks")), new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 13, 14 });
		OreDicHandler.registerWildcard(new ItemStack(BOPBlockHelper.get("saplings")), new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 });
		OreDicHandler.registerWildcard(new ItemStack(BOPBlockHelper.get("colorizedSaplings")), new Integer[] { 0, 1, 2, 3, 4, 5, 6 });
		OreDicHandler.registerWildcard(new ItemStack(BOPBlockHelper.get("woodenSingleSlab1")), new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7 });
		OreDicHandler.registerWildcard(new ItemStack(BOPBlockHelper.get("woodenSingleSlab2")), new Integer[] { 0, 1, 2, 3, 4 });
		OreDicHandler.registerWildcard(new ItemStack(BOPBlockHelper.get("colorizedLeaves1")), new Integer[] { 0, 1, 2, 3 });
		OreDicHandler.registerWildcard(new ItemStack(BOPBlockHelper.get("colorizedLeaves2")), new Integer[] { 0, 1, 2 });
		OreDicHandler.registerWildcard(new ItemStack(BOPBlockHelper.get("leaves1")), new Integer[] { 0, 1, 2, 3 });
		OreDicHandler.registerWildcard(new ItemStack(BOPBlockHelper.get("leaves2")), new Integer[] { 0, 1, 2, 3 });
		OreDicHandler.registerWildcard(new ItemStack(BOPBlockHelper.get("leaves3")), new Integer[] { 0, 1, 2, 3 });
		OreDicHandler.registerWildcard(new ItemStack(BOPBlockHelper.get("leaves4")), new Integer[] { 0, 1 });
		OreDicHandler.registerWildcard(new ItemStack(BOPBlockHelper.get("appleLeaves")), new Integer[] { 0 });
		OreDicHandler.registerWildcard(new ItemStack(BOPBlockHelper.get("persimmonLeaves")), new Integer[] { 0 });
	}
		
	@Override
	public void preInit() {
		
	}

	private static void addBiome(String name, EnumBiomeType type) {
		BiomeGenBase biome = BOPBiomeHelper.get(name);
		if(biome != null) {
			MaricultureHandlers.biomeType.addBiome(biome, type);
		}
	}

	@Override
	public void init() {
		
	}

	@Override
	public void postInit() {
		
	}
	
	//BiomesOP Coral + Kelp Support
	public static boolean isBiome(World world, int x, int z, Biome biome) {
		return true;
	}
}
