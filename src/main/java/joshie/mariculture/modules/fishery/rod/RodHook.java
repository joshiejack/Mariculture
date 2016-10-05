package joshie.mariculture.modules.fishery.rod;

import joshie.mariculture.api.fishing.Fishing.Size;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class RodHook extends RodComponent<RodHook> {
    public static final Map<ResourceLocation, RodHook> HOOKS = new HashMap<>();
    private final Size[] bestSizes;
    private final int catchSpeed;

    public RodHook(ResourceLocation resource, int catchSpeed, Size... bestSizes) {
        super(resource, "hook");
        this.catchSpeed = catchSpeed;
        this.bestSizes = bestSizes;
        HOOKS.put(resource, this);
    }

    int getCatchSpeed() {
        return catchSpeed;
    }

    Size[] getBestSize() {
        return bestSizes;
    }
}
