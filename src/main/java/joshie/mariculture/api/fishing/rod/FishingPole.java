package joshie.mariculture.api.fishing.rod;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

import java.util.HashMap;
import java.util.Map;

public class FishingPole extends FishingComponent {
    public static final Map<ResourceLocation, FishingPole> POLES = new HashMap<>();
    private final int durability;
    private final int strength;

    private FishingPole(ResourceLocation resource, int durability, int strength) {
        super(resource, "pole");
        this.durability = durability;
        this.strength = strength;
        POLES.put(resource, this);
    }

    public static FishingPole create(String name, int durability, int strength) {
        return new FishingPole(new ResourceLocation(Loader.instance().activeModContainer().getModId(), name), durability, strength);
    }

    int getStrength() {
        return strength;
    }

    int getDurability() {
        return durability;
    }
}
