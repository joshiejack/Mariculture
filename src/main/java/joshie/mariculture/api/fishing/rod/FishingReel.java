package joshie.mariculture.api.fishing.rod;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

import java.util.HashMap;
import java.util.Map;

public class FishingReel extends FishingComponent {
    public static final Map<ResourceLocation, FishingReel> REELS = new HashMap<>();

    private FishingReel(ResourceLocation resource) {
        super(resource, "reel");
        REELS.put(resource, this);
    }

    public static FishingReel create(String name) {
        return new FishingReel(new ResourceLocation(Loader.instance().activeModContainer().getModId(), name));
    }
}
