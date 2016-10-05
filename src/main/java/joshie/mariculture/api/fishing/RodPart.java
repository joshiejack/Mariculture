package joshie.mariculture.api.fishing;

public interface RodPart<C extends RodPart> {
    /** Set the fishing traits for this rod part
     * @param traits    any fishing traits you want to add */
    C setTraits(FishingTrait... traits);
}
