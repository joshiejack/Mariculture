package mariculture.api.fishery.interfaces;

import net.minecraft.item.ItemStack;

//Blocks that handle fish eggs should implement this, if they are using the helper method in IFishHelper
public interface IIncubator {
    int getBirthChanceBoost();

    void eject(ItemStack fish);
}
