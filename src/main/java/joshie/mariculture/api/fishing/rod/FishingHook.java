package joshie.mariculture.api.fishing.rod;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

import java.util.HashMap;
import java.util.Map;

public class FishingHook extends FishingComponent {
    public static final Map<ResourceLocation, FishingHook> HOOKS = new HashMap<>();

    private FishingHook (ResourceLocation resource) {
        super(resource, "hook");
        HOOKS.put(resource, this);
    }

    public static FishingHook create(String name) {
        return new FishingHook(new ResourceLocation(Loader.instance().activeModContainer().getModId(), name));
    }
}
