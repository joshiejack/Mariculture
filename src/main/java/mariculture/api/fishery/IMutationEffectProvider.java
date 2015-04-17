package mariculture.api.fishery;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IMutationEffectProvider {
    public List<IMutationEffect> getEffects(ItemStack stack);
}
