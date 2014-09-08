package joshie.mariculture.api.core.interfaces;

import net.minecraft.item.ItemStack;

public interface IUpgradable {
    /** Return an array, three long of the slots with the upgrades **/
    public ItemStack[] getUpgrades();

    public void updateUpgrades();
}
