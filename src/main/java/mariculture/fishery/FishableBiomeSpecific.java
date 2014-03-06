package mariculture.fishery;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomFishable;

public class FishableBiomeSpecific extends WeightedRandomFishable {
	public FishableBiomeSpecific(ItemStack stack, int weight) {
		super(stack, weight);
	}

}
