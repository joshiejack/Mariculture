package joshie.maritech.items;

import java.util.List;

import joshie.lib.base.ItemBaseEnergy;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.util.MCTranslate;
import joshie.maritech.lib.MTModInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBattery extends ItemBaseEnergy {
    public ItemBattery(int capacity, int maxReceive, int maxExtract) {
        super(MTModInfo.TEXPATH, MaricultureTab.tabCore, capacity, maxReceive, maxExtract);
    }

    public static ItemStack make(ItemStack stack, int power) {
        if (stack.stackTagCompound == null) {
            stack.setTagCompound(new NBTTagCompound());
        }

        stack.stackTagCompound.setInteger("Energy", power);

        return stack.copy();
    }

    @Override
    public int getDisplayDamage(ItemStack stack) {
        if (stack.stackTagCompound == null) return 1 + capacity;

        return 1 + capacity - stack.stackTagCompound.getInteger("Energy");
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 1 + capacity;
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (stack.stackTagCompound == null) return;
        list.add(MCTranslate.translate("charge") + ": " + stack.stackTagCompound.getInteger("Energy") + " / " + capacity + " " + MCTranslate.translate("rf"));
        list.add(MCTranslate.translate("transfer") + ": " + maxExtract + " " + MCTranslate.translate("rft"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creative, List list) {
        ItemStack battery = new ItemStack(item, 1, 0);
        list.add(make(battery, 0));
        list.add(make(battery, capacity));
    }
}
