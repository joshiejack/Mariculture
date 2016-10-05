package joshie.mariculture.modules.fishery.rod;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class RodString extends RodComponent<RodString> {
    public static final Map<ResourceLocation, RodString> STRINGS = new HashMap<>();
    private final float strengthModifier;

    public RodString(ResourceLocation resource, float strengthModifier) {
        super(resource, "string");
        this.strengthModifier = strengthModifier;
        STRINGS.put(resource, this);
    }

    float getStrengthModifier() {
        return strengthModifier;
    }
}
