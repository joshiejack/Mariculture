package mariculture.fishery.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.fishery.Fishing;
import mariculture.core.items.ItemMCBaseSingle;
import mariculture.core.util.MCTranslate;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEgg extends ItemMCBaseSingle {
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (Fishing.fishHelper.isEgg(stack)) if (stack.stackTagCompound.getInteger("currentFertility") > 0) {
            list.add(stack.stackTagCompound.getInteger("currentFertility") + " " + MCTranslate.translate("eggsRemaining"));
        } else {
            list.add(MCTranslate.translate("undetermined") + " " + MCTranslate.translate("eggsRemaining"));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + "fish/egg");
    }
}
