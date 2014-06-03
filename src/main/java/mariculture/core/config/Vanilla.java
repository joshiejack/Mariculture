package mariculture.core.config;

import static mariculture.core.helpers.ConfigHelper.getBoolean;
import static mariculture.core.helpers.ConfigHelper.setCategory;
import static mariculture.core.helpers.ConfigHelper.setConfig;
import mariculture.core.lib.Modules;
import net.minecraftforge.common.config.Configuration;

public class Vanilla {
	public static boolean VANILLA_STATS;
	public static boolean VANILLA_POOR;
	public static boolean VANILLA_TEXTURES;
	public static boolean VANILLA_LOOT;
	public static boolean VANILLA_FORCE;

	public static void init(Configuration config) {
		setConfig(config);
		setCategory("Settings", "These settings are only valid if the Fishery Module is enabled.");
		Vanilla.VANILLA_STATS = getBoolean("Use Vanilla stats for fish", false);
		Vanilla.VANILLA_POOR = getBoolean("Vanilla rods are not as good without bait", true);
		Vanilla.VANILLA_LOOT = getBoolean("Vanilla rods catch vanilla loot", true);
		Vanilla.VANILLA_FORCE = getBoolean("Vanilla rods need bait to work", false);
		Vanilla.VANILLA_TEXTURES = getBoolean("Use Vanilla textures for Fish", false);
		if (!Modules.isActive(Modules.fishery)) {
			Vanilla.VANILLA_STATS = true;
			Vanilla.VANILLA_LOOT = true;
			Vanilla.VANILLA_POOR = false;
			Vanilla.VANILLA_FORCE = false;
			Vanilla.VANILLA_TEXTURES = true;
		}
	}
}
