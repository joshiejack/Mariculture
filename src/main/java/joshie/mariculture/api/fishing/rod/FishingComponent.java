package joshie.mariculture.api.fishing.rod;

import joshie.mariculture.api.fishing.FishingTrait;
import net.minecraft.util.ResourceLocation;

public class FishingComponent {
    private final ResourceLocation resource;
    private final String prefix;
    private FishingTrait[] traits;

    protected FishingComponent(ResourceLocation resource, String prefix) {
        this.resource = resource;
        this.prefix = prefix;
        this.traits = new FishingTrait[0];
    }

    public FishingComponent setTraits(FishingTrait... traits) {
        this.traits = traits;
        return this;
    }

    public ResourceLocation getResource() {
        return resource;
    }

    @Override
    public String toString() {
        return getResource().toString();
    }

    public String getPrefix() {
        return prefix;
    }

    public FishingTrait[] getTraits() {
        return traits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FishingComponent that = (FishingComponent) o;
        return resource != null ? resource.equals(that.resource) : that.resource == null;
    }

    @Override
    public int hashCode() {
        return resource != null ? resource.hashCode() : 0;
    }
}
