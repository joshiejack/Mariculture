package joshie.mariculture.modules.diving;

import joshie.mariculture.api.diving.AirPumpTicker;
import joshie.mariculture.api.diving.WaterBreathingChecker;
import joshie.mariculture.core.util.annotation.MCApiImpl;
import joshie.mariculture.modules.diving.tile.TileAirPump;

import java.util.HashSet;
import java.util.Set;

@MCApiImpl("diving")
public class DivingAPI implements joshie.mariculture.api.diving.Diving {
    public static final DivingAPI INSTANCE = new DivingAPI();

    private final Set<WaterBreathingChecker> listeners = new HashSet<>();

    public Set<WaterBreathingChecker> getListeners() {
        return listeners;
    }

    @Override
    public void registerWaterbreathingListener(WaterBreathingChecker listener) {
        listeners.add(listener);
    }

    @Override
    public void registerAirPumpTicker(AirPumpTicker ticker) {
        TileAirPump.tickers.add(ticker);
    }
}
