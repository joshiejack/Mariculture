package mariculture.core.lib.config;

public class Comment {

	public static final String HARDCORE = "This causes your air to drop quicker, the higher the faster it will drop, set to 0 to turn off";
	public static final String REFRESH = "This is how many ticks before updates are sent to the client";
	public static final String PEARL_CHANCE = "This is the chance, that after the tick count is finished, oysters will generate a pearl." + 
														" It's a X in this many chance, so lower = better chance";
	public static final String BAIT = "Chance of catching something in an autofisher with this bait - Lower = Better chance";
	public static final String METAL = "This is how many mB a Nugget is worth, the default value is the same as Tinker's Construct";
	public static final String FLUDD = "Whether a server will tell the client to display the fludd animations";
	public static final String CORAL_SPREAD = "This is how fast coral will spread, lower = faster";
	public static final String KELP_SPREAD = "This is how fast Kelp will spread moss to neaby blocks, lower = faster";
	public static final String KELP_GROWTH = "This is the speed that kelp will grow, lower = faster";
	public static final String ENDERDRAGON = "This is whether players can Spawn the Ender Dragon with the Dragon Spawn Egg";
	public static final String RIVER = "This is a list of biome ids, that Mariculture considers to be 'river' biomes (affects where limestone/rutile can spawn)";
	public static final String OCEAN = "This is a list of biome ids, that Mariculture considers to be 'ocean' biomes; (affects where natural gas/coral/kelp can spawn) Take note that Mariculture automatically asumes that BiomesOPlenty ocean types are indeed oceans";
	public static final String BIOMESOP_CORAL = "If enabled this will force this to only generate in the corresponding BOP Biomes if they exist in a world, If disabled, will generate in all ocean biomes";
	public static final String RETRO_KEY = "This key is what stops, retro gen from generating in chunks over and over, if you want to retro gen again, change the key to any other integer";
	
	public static final String KELP_FOREST_CHEST_CHANCE = "The higher the number the less common a chest will be, this roughly works out to this 1 chest per this many blocks of a forest.";
	public static final String GEN_START = "The higher the number, the rarer the generation will be, this number defines the chance of this gen spawning";
	public static final String GEN_END = "The higher the number, the larger the generation will be, this number defines the chance of this gen no longer spawning";
}
