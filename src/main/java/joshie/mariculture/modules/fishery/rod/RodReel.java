package joshie.mariculture.modules.fishery.rod;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class RodReel extends RodComponent<RodReel> {
    public static final Map<ResourceLocation, RodReel> REELS = new HashMap<>();
    private final float heightModifier;
    private final float distanceBonus;
    private final double retractSpeed;

    public RodReel(ResourceLocation resource, float heightModifier, float distanceBonus, double retractSpeed) {
        super(resource, "reel");
        this.heightModifier = heightModifier;
        this.distanceBonus = distanceBonus;
        this.retractSpeed = retractSpeed;
        REELS.put(resource, this);
    }


    float getHeightModifier() {
        return heightModifier;
    }

    float getDistanceBonus() {
        return distanceBonus;
    }

    double getRetractSpeed() {
        return retractSpeed;
    }
}
