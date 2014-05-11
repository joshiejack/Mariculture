package mariculture.plugins;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.lib.WorldGeneration;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.oredict.OreDictionary;
import biomesoplenty.api.Biomes;
import biomesoplenty.api.Blocks;

import com.google.common.base.Optional;

public class PluginBiomesOPlenty extends Plugin {
	//TODO: Readd BOP Biomes
	/* private void addBiome(Optional<? extends BiomeGenBase> biome, Temperature temp, Salinity salt) {
		if (biome.isPresent()) {
			MaricultureHandlers.biomeType.addEnvironment(biome.get(), new Environment(temp, salt));
		}
	} */
	
	private void addBiome(Optional<? extends BiomeGenBase> biome, int temp, Salinity salt) {
		if(biome.isPresent()) {
			MaricultureHandlers.environment.addEnvironment(biome.get(), salt, temp);
		}
	}
	
	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		addBiome(Biomes.alps, -5, Salinity.FRESH);
		addBiome(Biomes.alpsForest, -1, Salinity.FRESH);
		addBiome(Biomes.alpsBase, 0, Salinity.FRESH);
		addBiome(Biomes.arctic, -1, Salinity.SALINE);
		addBiome(Biomes.badlands, 35, Salinity.FRESH);
		addBiome(Biomes.bambooForest, 20, Salinity.FRESH);
		addBiome(Biomes.bayou, 15, Salinity.FRESH);
		addBiome(Biomes.beachGravel, 20, Salinity.BRACKISH);
		addBiome(Biomes.beachOvergrown, 26, Salinity.BRACKISH);
		addBiome(Biomes.birchForest, 10, Salinity.FRESH);
		addBiome(Biomes.bog, 7, Salinity.FRESH);
		addBiome(Biomes.brushland, 30, Salinity.FRESH);
		addBiome(Biomes.canyon, 40, Salinity.FRESH);
		addBiome(Biomes.canyonRavine, 40, Salinity.FRESH);
		addBiome(Biomes.chaparral, 6, Salinity.FRESH);
		addBiome(Biomes.cherryBlossomGrove, 12, Salinity.FRESH);
		addBiome(Biomes.coniferousForest, 5, Salinity.FRESH);
		addBiome(Biomes.coniferousForestSnow, 2, Salinity.FRESH);
		addBiome(Biomes.crag, 40, Salinity.FRESH);
		addBiome(Biomes.deadForest, 40, Salinity.FRESH);
		addBiome(Biomes.deadForestSnow, 5, Salinity.FRESH);
		addBiome(Biomes.deadSwamp, 7, Salinity.FRESH);
		addBiome(Biomes.deadlands, 65, Salinity.FRESH);
		addBiome(Biomes.deciduousForest, 8, Salinity.FRESH);
		addBiome(Biomes.dunes, 50, Salinity.FRESH);
		addBiome(Biomes.fen, 12, Salinity.FRESH);
		addBiome(Biomes.field, 11, Salinity.FRESH);
		addBiome(Biomes.fieldForest, 10, Salinity.FRESH);
		addBiome(Biomes.frostForest, 3, Salinity.FRESH);
		addBiome(Biomes.fungiForest, 6, Salinity.FRESH);
		addBiome(Biomes.grassland, 10, Salinity.FRESH);
		addBiome(Biomes.glacier, 0, Salinity.FRESH);
		addBiome(Biomes.grove, 10, Salinity.FRESH);
		addBiome(Biomes.heathland, 7, Salinity.FRESH);
		addBiome(Biomes.highland, 6, Salinity.FRESH);
		addBiome(Biomes.hotSprings, 14, Salinity.FRESH);
		addBiome(Biomes.icyHills, 2, Salinity.FRESH);
		addBiome(Biomes.jadeCliffs, 13, Salinity.BRACKISH);
		addBiome(Biomes.lushDesert, 37, Salinity.FRESH);
		addBiome(Biomes.lushSwamp, 8, Salinity.FRESH);
		addBiome(Biomes.mangrove, 20, Salinity.FRESH);
		addBiome(Biomes.mapleWoods, 12, Salinity.FRESH);
		addBiome(Biomes.marsh, 7, Salinity.FRESH);
		addBiome(Biomes.meadow, 10, Salinity.FRESH);
		addBiome(Biomes.meadowForest, 9, Salinity.FRESH);
		addBiome(Biomes.mesa, 36, Salinity.FRESH);
		addBiome(Biomes.moor, 3, Salinity.FRESH);
		addBiome(Biomes.mountain, 0, Salinity.FRESH);
		addBiome(Biomes.mysticGrove, 10, Salinity.FRESH);
		addBiome(Biomes.netherBase, 80, Salinity.FRESH);
		addBiome(Biomes.netherGarden, 78, Salinity.FRESH);
		addBiome(Biomes.netherDesert, 90, Salinity.FRESH);
		addBiome(Biomes.netherLava, 100, Salinity.FRESH);
		addBiome(Biomes.netherBone, 79, Salinity.FRESH);
		addBiome(Biomes.oasis, 22, Salinity.FRESH);
		addBiome(Biomes.oceanCoral, 26, Salinity.SALINE);
		addBiome(Biomes.oceanKelp, 24, Salinity.SALINE);
		addBiome(Biomes.ominousWoods, 8, Salinity.FRESH);
		addBiome(Biomes.ominousWoodsThick, 7, Salinity.FRESH);
		addBiome(Biomes.orchard, 10, Salinity.FRESH);
		addBiome(Biomes.originValley, 11, Salinity.FRESH);
		addBiome(Biomes.outback, 24, Salinity.FRESH);
		addBiome(Biomes.overgrownGreens, 8, Salinity.FRESH);
		addBiome(Biomes.pasture, 10, Salinity.FRESH);
		addBiome(Biomes.pastureMeadow, 11, Salinity.FRESH);
		addBiome(Biomes.pastureThin, 9, Salinity.FRESH);
		addBiome(Biomes.polar, -1, Salinity.SALINE);
		addBiome(Biomes.prairie, 20, Salinity.FRESH);
		addBiome(Biomes.promisedLandForest, 9, Salinity.FRESH);
		addBiome(Biomes.promisedLandPlains, 10, Salinity.FRESH);
		addBiome(Biomes.promisedLandSwamp, 8, Salinity.FRESH);
		addBiome(Biomes.quagmire, 9, Salinity.FRESH);
		addBiome(Biomes.rainforest, 25, Salinity.FRESH);
		addBiome(Biomes.redwoodForest, 5, Salinity.FRESH);
		addBiome(Biomes.sacredSprings, 14, Salinity.FRESH);
		addBiome(Biomes.savanna, 28, Salinity.FRESH);
		addBiome(Biomes.savannaPlateau, 30, Salinity.FRESH);
		addBiome(Biomes.scrubland, 32, Salinity.FRESH);
		addBiome(Biomes.seasonalForest, 10, Salinity.FRESH);
		addBiome(Biomes.seasonalSpruceForest, 7, Salinity.FRESH);
		addBiome(Biomes.shield, 8, Salinity.FRESH);
		addBiome(Biomes.shore, 4, Salinity.BRACKISH);
		addBiome(Biomes.shrubland, 17, Salinity.FRESH);
		addBiome(Biomes.shrublandForest, 15, Salinity.FRESH);
		addBiome(Biomes.silkglades, 20, Salinity.FRESH);
		addBiome(Biomes.sludgepit, 15, Salinity.FRESH);
		addBiome(Biomes.spruceWoods, 8, Salinity.FRESH);
		addBiome(Biomes.steppe, 20, Salinity.FRESH);
		addBiome(Biomes.temperateRainforest, 15, Salinity.FRESH);
		addBiome(Biomes.thicket, 10, Salinity.FRESH);
		addBiome(Biomes.timber, 7, Salinity.FRESH);
		addBiome(Biomes.timberThin, 6, Salinity.FRESH);
		addBiome(Biomes.tropicalRainforest, 28, Salinity.FRESH);
		addBiome(Biomes.tropics, 27, Salinity.FRESH);
		addBiome(Biomes.tundra, 1, Salinity.FRESH);
		addBiome(Biomes.volcano, 75, Salinity.FRESH);
		addBiome(Biomes.wasteland, 40, Salinity.FRESH);
		addBiome(Biomes.wetland, 8, Salinity.FRESH);
		addBiome(Biomes.woodland, 10, Salinity.FRESH);
		addBiome(Biomes.plainsNew, 10, Salinity.FRESH);
		addBiome(Biomes.desertNew, 45, Salinity.FRESH);
		addBiome(Biomes.extremeHillsNew, 5, Salinity.FRESH);
		addBiome(Biomes.forestNew, 10, Salinity.FRESH);
		addBiome(Biomes.taigaNew, 1, Salinity.FRESH);
		addBiome(Biomes.taigaHillsNew, 0, Salinity.FRESH);
		addBiome(Biomes.swamplandNew, 8, Salinity.FRESH);
		addBiome(Biomes.jungleNew, 25, Salinity.FRESH);
		addBiome(Biomes.jungleHillsNew, 24, Salinity.FRESH);
		addBiome(Biomes.autumnHills, 5, Salinity.FRESH);
		addBiome(Biomes.lavenderFields, 10, Salinity.FRESH);
		addBiome(Biomes.oceanAbyss, 1, Salinity.SALINE);
		addBiome(Biomes.tropicsMountain, 20, Salinity.FRESH);
		
		if(Blocks.coral.isPresent()) {
			int id = Blocks.coral.get().blockID;
			OreDictionary.registerOre("plantKelp", new ItemStack(id, 1, 3));
			OreDictionary.registerOre("coralPink", new ItemStack(id, 1, 4));
			OreDictionary.registerOre("coralOrange", new ItemStack(id, 1, 5));
			OreDictionary.registerOre("coralLightBlue", new ItemStack(id, 1, 6));
			OreDictionary.registerOre("coralPurple", new ItemStack(id, 1, 7));
		}
	}

	@Override
	public void postInit() {		
		WorldGeneration.CORAL_CHANCE /= 5;
		WorldGeneration.KELP_HEIGHT *= 2.5;
		WorldGeneration.KELP_CHANCE /= 3;
	}
	
	public static enum Biome {
		KELP, CORAL
	}

	public static boolean isBiome(World world, int x, int z, Biome biome) {
		WorldType worldType = world.getWorldInfo().getTerrainType();
		boolean isVanilla = true;
		if(biome.equals(Biome.CORAL)) {
			for(String type: WorldGeneration.CORAL_BIOMESOP_TYPES) {
				if(worldType == WorldType.parseWorldType(type)) {
					isVanilla = false;
					break;
				}
			}
		}
		
		if(biome.equals(Biome.KELP)) {
			for(String type: WorldGeneration.KELP_BIOMESOP_TYPES) {
				if(worldType == WorldType.parseWorldType(type)) {
					isVanilla = false;
					break;
				}
			}
		}

		if(isVanilla)
			return true;
		
		if(biome.equals(Biome.KELP) && !WorldGeneration.KELP_BIOMESOP)
			return true;
		if(biome.equals(Biome.CORAL) && !WorldGeneration.CORAL_BIOMESOP)
			return true;
		
		Optional<? extends BiomeGenBase> biomeType = (biome.equals(Biome.KELP))? Biomes.oceanKelp: Biomes.oceanCoral;
		
		if(biomeType.isPresent()) {
			if(world.getWorldChunkManager().getBiomeGenAt(x, z) != biomeType.get()) {
				return false;
			}
		}

		return true;
	}
}
