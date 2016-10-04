package joshie.mariculture.api.fishing.rod;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

import java.util.HashMap;
import java.util.Map;

public class FishingString extends FishingComponent {
    public static final Map<ResourceLocation, FishingString> STRINGS = new HashMap<>();
    private final float strengthModifier;

    private FishingString(ResourceLocation resource, float strengthModifier) {
        super(resource, "string");
        this.strengthModifier = strengthModifier;
        STRINGS.put(resource, this);
    }

    public static FishingString create(String name, float strengthModifier) {
        return new FishingString(new ResourceLocation(Loader.instance().activeModContainer().getModId(), name), strengthModifier);
    }

    public float getStrengthModifier() {
        return strengthModifier;
    }
}
