package joshie.mariculture.api.fishery;

import joshie.mariculture.api.fishery.handlers.IFishFoodHandler;
import joshie.mariculture.api.fishery.handlers.ISifterHandler;
import joshie.mariculture.api.fishery.interfaces.IFishHelper;
import joshie.mariculture.api.fishery.interfaces.IFishing;
import joshie.mariculture.api.fishery.interfaces.IMutation;

public class Fishing {
    public static IFishFoodHandler food;
    public static IFishHelper fishHelper;
    public static IMutation mutation;
    public static IFishing fishing;
    public static ISifterHandler sifter;
}
