package joshie.maritech.items;

import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.api.fishery.fish.FishDNABase;
import joshie.mariculture.core.items.ItemMCDamageable;
import joshie.maritech.extensions.modules.ExtensionFishery;
import joshie.maritech.lib.MTModInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemFishDNA extends ItemMCDamageable {
    public ItemFishDNA() {
        super(MTModInfo.MODPATH, MaricultureTab.tabFishery, 32);
    }

    public static ItemStack create(FishDNABase dna, int value) {
        ItemStack stack = new ItemStack(ExtensionFishery.dna);
        stack.setTagCompound(new NBTTagCompound());
        stack.stackTagCompound.setString("DNAType", dna.getHigherString());
        stack.stackTagCompound.setInteger("DNAValue", value);
        return stack;
    }
}
