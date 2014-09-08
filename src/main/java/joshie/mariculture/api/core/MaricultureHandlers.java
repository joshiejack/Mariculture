package joshie.mariculture.api.core;

import joshie.mariculture.api.core.handlers.IAnvilHandler;
import joshie.mariculture.api.core.handlers.ICastingHandler;
import joshie.mariculture.api.core.handlers.ICrucibleHandler;
import joshie.mariculture.api.core.handlers.IEnvironmentHandler;
import joshie.mariculture.api.core.handlers.IMirrorHandler;
import joshie.mariculture.api.core.handlers.IUpgradeHandler;
import joshie.mariculture.api.core.handlers.IVatHandler;

public class MaricultureHandlers {
    public static boolean HIGH_TECH_ENABLED = false;
    
    public static IAnvilHandler anvil;
    public static ICastingHandler casting;
    public static IEnvironmentHandler environment;
    public static ICrucibleHandler crucible;
    public static IUpgradeHandler upgrades;
    public static IVatHandler vat;

    public static IMirrorHandler mirror;
}
