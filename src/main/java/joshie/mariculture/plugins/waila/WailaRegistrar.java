package joshie.mariculture.plugins.waila;

import joshie.mariculture.core.blocks.BlockRenderedMachine;
import joshie.mariculture.core.blocks.BlockRenderedMachineMulti;
import joshie.mariculture.core.blocks.BlockTank;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaRegistrar {
    public static void register(IWailaRegistrar registrar) {
        registrar.registerBodyProvider(new CasterDataProvider(), BlockRenderedMachine.class);
        registrar.registerBodyProvider(new CopperTankDataProvider(), BlockTank.class);
        registrar.registerBodyProvider(new VatDataProvider(), BlockRenderedMachineMulti.class);
        registrar.registerSyncedNBTKey("TimeNeeded", BlockRenderedMachineMulti.class);
        registrar.registerSyncedNBTKey("TimeRemaining", BlockRenderedMachineMulti.class);
    }
}
