package maritech.extensions.config;

import static mariculture.core.helpers.ConfigHelper.getBoolean;
import static mariculture.core.helpers.ConfigHelper.getDouble;
import static mariculture.core.helpers.ConfigHelper.setCategory;
import static mariculture.core.helpers.ConfigHelper.setConfig;
import maritech.util.IConfigExtension;
import net.minecraftforge.common.config.Configuration;

public class ExtensionGeneralStuff implements IConfigExtension {
    public static double SPEEDBOAT_VERTICAL_MODIFIER;
    public static boolean FLUDD_WATER_ON;

    @Override
    public String getName() {
        return "GeneralStuff";
    }

    @Override
    public void init(Configuration config) {
        setConfig(config);
        setCategory("Stuff");
        FLUDD_WATER_ON = getBoolean("Enable FLUDD Animations", true, "Whether a server will tell the client to display the fludd animations");
        SPEEDBOAT_VERTICAL_MODIFIER = getDouble("Speedboat Vertical Modifier", 2.0D, "This changes the speed modifier of a speedboat moving upwards in water when the speedboat is at least 90% covered in water.");
    }
}
