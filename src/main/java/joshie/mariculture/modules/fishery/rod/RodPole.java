package joshie.mariculture.modules.fishery.rod;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class RodPole extends RodComponent<RodPole> {
    public static final Map<ResourceLocation, RodPole> POLES = new HashMap<>();
    private final int durability;
    private final int strength;

    public RodPole(ResourceLocation resource, int durability, int strength) {
        super(resource, "pole");
        this.durability = durability;
        this.strength = strength;
        POLES.put(resource, this);
    }

    int getStrength() {
        return strength;
    }

    int getDurability() {
        return durability;
    }
}
