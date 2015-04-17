package mariculture.api.fishery;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IMutationEffect {
    public ItemStack adjustFishStack(ItemStack egg, ItemStack fish);
}
