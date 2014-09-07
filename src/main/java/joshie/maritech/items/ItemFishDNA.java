package joshie.maritech.items;

import java.util.List;

import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.api.fishery.fish.FishDNABase;
import joshie.mariculture.core.items.ItemMCDamageable;
import joshie.maritech.extensions.modules.ExtensionFishery;
import joshie.maritech.lib.MTModInfo;
import joshie.maritech.util.MTTranslate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemFishDNA extends ItemMCDamageable {
    public ItemFishDNA() {
        super(MTModInfo.MODPATH, MaricultureTab.tabFishery, 32);
    }
    
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if(stack.hasTagCompound()) {
            for(FishDNABase dna: FishDNABase.DNAParts) {
                if(dna.getHigherString().equals(stack.stackTagCompound.getString("DNAType"))) {
                    String format = MTTranslate.translate("dna.format");
                    format = format.replace("%D", MTTranslate.translate("dna"));
                    return format.replace("%N", dna.getScannedDisplay(stack)[0]);
                }
            }
        } return MTTranslate.translate("dna.corrupt");
    }
    
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if(stack.hasTagCompound()) {
            for(FishDNABase dna: FishDNABase.DNAParts) {
                if(dna.getHigherString().equals(stack.stackTagCompound.getString("DNAType"))) {
                    list.add(MTTranslate.translate("dna.name") + ": " +dna.getScannedDisplay(stack)[1]);
                    list.add(MTTranslate.translate("dna.value") + ": " + stack.stackTagCompound.getInteger("DNAValue"));
                }
            }
        }
    }

    public static ItemStack create(FishDNABase dna, int value) {
        ItemStack stack = new ItemStack(ExtensionFishery.dna);
        stack.setTagCompound(new NBTTagCompound());
        stack.stackTagCompound.setString("DNAType", dna.getHigherString());
        stack.stackTagCompound.setInteger("DNAValue", value);
        return stack;
    }

    public static ItemStack add(ItemStack dnaStack, ItemStack fish) {
        ItemStack ret = fish.copy();
        String type = dnaStack.stackTagCompound.getString("DNAType");
        for (FishDNABase dna : FishDNABase.DNAParts) {
            if (type.equals(dna.getHigherString())) {
                //Found the matching dna
                dna.addDNA(ret, dnaStack.stackTagCompound.getInteger("DNAValue"));
                dna.addLowerDNA(ret, dnaStack.stackTagCompound.getInteger("DNAValue"));
                return ret;
            }
        }

        return ret;
    }
}
