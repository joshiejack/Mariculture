package joshie.mariculture.modules.diving;

import joshie.mariculture.api.diving.WaterBreathingChecker;
import joshie.mariculture.modules.EventAPIContainer;

import java.util.HashSet;
import java.util.Set;

@EventAPIContainer(modules = "diving")
public class DivingAPI implements joshie.mariculture.api.diving.Diving {
    private static final Set<WaterBreathingChecker> listeners = new HashSet<>();

    public static Set<WaterBreathingChecker> getListeners() {
        return listeners;
    }

    @Override
    public void registerWaterbreathingListener(WaterBreathingChecker listener) {
        listeners.add(listener);
    }
}
