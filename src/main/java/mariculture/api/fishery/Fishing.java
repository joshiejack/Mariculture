package mariculture.api.fishery;

import mariculture.api.fishery.handlers.IFishFoodHandler;
import mariculture.api.fishery.handlers.ISifterHandler;
import mariculture.api.fishery.interfaces.IFishHelper;
import mariculture.api.fishery.interfaces.IFishing;
import mariculture.api.fishery.interfaces.IMutation;

public class Fishing {
    public static IFishFoodHandler food;
    public static IFishHelper fishHelper;
    public static IMutation mutation;
    public static IFishing fishing;
    public static ISifterHandler sifter;
}
