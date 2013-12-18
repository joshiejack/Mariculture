package mariculture.core.lib.config;

public class Comment {

	public static final String HARDCORE = "This causes your air to drop quicker, the higher the faster it will drop, set to 0 to turn off";
	public static final String REFRESH = "This is how many ticks before updates are sent to the client";
	public static final String PEARL_CHANCE = "This is the chance, that after the tick count is finished, oysters will generate a pearl." + 
														" It's a X in this many chance, so lower = better chance";
	public static final String PEARL_RATE = "This is how many ticks before an oyster will attempt to generate a Pearl";
	public static final String BAIT = "Chance of catching something in an autofisher with this bait - Lower = Better chance";
	public static final String METAL = "This is how many mB a Nugget is worth, the default value is the same as Tinker's Construct";
	public static final String FLUDD = "Whether a server will tell the client to display the fludd animations";
	public static final String CORAL_SPREAD = "This is how fast coral will spread, lower = faster";
	public static final String KELP_SPREAD = "This is how fast Kelp will spread moss to neaby blocks, lower = faster";
	public static final String KELP_GROWTH = "This is the speed that kelp will grow, lower = faster";
	public static final String ENDERDRAGON = "This is whether players can Spawn the Ender Dragon with the Dragon Spawn Egg";
	public static final String RIVER = "This is a list of biome ids, that Mariculture considers to be 'river' biomes (affects where limestone/rutile can spawn)";
	public static final String OCEAN = "This is a list of biome ids, that Mariculture considers to be 'ocean' biomes; (affects where natural gas/coral/kelp can spawn) Take note that Mariculture automatically asumes that BiomesOPlenty ocean types are indeed oceans";
	public static final String ORE = "This section let's you adjust where your ores will spawn and how much. Please note when it comes to 'chance', that LOWER = more common, as it's a x in this many chance.";
	public static final String PUMP_MANUAL = "Whether or not the Air Pump can be activated by right clicking on it";
	public static final String PUMP_REDSTONE = "Whether or not giving the Air Pump a Redstone Signal will cause it to provide air";
	public static final String PUMP_RF = "Whether an Air Pump can be powered by Redstone Flux";
    public static final String LEGACY = "Legacy Blocks, Kept for compatibility between v1.2+ and 1.13a-(Keep as zero if a new world, these WILL be removed in the future)";
	public static final String BLACKLIST = "Place Ore Dictionary Names of stuff you do not wish to be able to convert with the dictionary block";
	public static final String EXCEPTIONS = "Place names that are practically equivalent and should be converted between each other by dictionary, format - item1: item2";
}
