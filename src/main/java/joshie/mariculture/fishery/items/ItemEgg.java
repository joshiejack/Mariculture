package joshie.mariculture.fishery.items;

import java.util.List;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.core.util.Translate;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEgg extends Item {
    public ItemEgg() {
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (Fishing.fishHelper.isEgg(stack)) if (stack.stackTagCompound.getInteger("currentFertility") > 0) {
            list.add(stack.stackTagCompound.getInteger("currentFertility") + " " + Translate.translate("eggsRemaining"));
        } else {
            list.add(Translate.translate("undetermined") + " " + Translate.translate("eggsRemaining"));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + "fish/egg");
    }
}
