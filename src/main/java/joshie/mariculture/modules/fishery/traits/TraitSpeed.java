package joshie.mariculture.modules.fishery.traits;

import joshie.mariculture.api.fishing.FishingTrait;

public class TraitSpeed extends FishingTrait {
    public TraitSpeed() {
        super("speed");
    }

    @Override
    public int modifySpeed(int original) {
        return original * 2;
    }
}
