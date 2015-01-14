package maritech.plugins;

import mariculture.core.util.MCTranslate;
import WayofTime.alchemicalWizardry.api.rituals.Rituals;

public class BloodMagic {
    public static void init() {
        try {
            Rituals.registerRitual("MARIBLOODRIVER", 1, 50000, new RitualOfTheBloodRiver(), MCTranslate.translate("ritual"));
        } catch (Exception e) {}
    }
}
