package joshie.mariculture.diving;

import java.util.List;

import joshie.mariculture.Mariculture;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.items.ItemMCBaseArmor;
import joshie.mariculture.core.lib.CraftingMeta;
import joshie.mariculture.diving.render.ModelFlippers;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArmorScuba extends ItemMCBaseArmor {
    public ItemArmorScuba(ArmorMaterial material, int j, int k) {
        super(material, j, k);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (stack.getItem() == Diving.swimfin) return "mariculture:textures/armor/flippers.png";
        if (stack.getItem() == Diving.scubaSuit) return "mariculture:" + "textures/armor/scuba" + "_2.png";

        return "mariculture:" + "textures/armor/scuba" + "_1.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, int armorSlot) {
        return stack.getItem() == Diving.swimfin ? new ModelFlippers() : null;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (stack.getItem() == Diving.scubaMask) if (stack.hasTagCompound()) {
            final boolean landLightOn = stack.getTagCompound().getBoolean("ScubaMaskOnOutOfWater");

            if (landLightOn) {
                list.add(StatCollector.translateToLocal("mariculture.string.landlights") + ": " + joshie.lib.util.Text.DARK_GREEN + StatCollector.translateToLocal("mariculture.string.on"));
            } else {
                list.add(StatCollector.translateToLocal("mariculture.string.landlights") + ": " + joshie.lib.util.Text.RED + StatCollector.translateToLocal("mariculture.string.off"));
            }
        } else {
            list.add(StatCollector.translateToLocal("mariculture.string.landlights") + ": " + joshie.lib.util.Text.RED + StatCollector.translateToLocal("mariculture.string.off"));
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        if (stack1.getItem() != Diving.scubaTank) return stack2.getItem() == Core.crafting && stack2.getItemDamage() == CraftingMeta.NEOPRENE;
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) if (stack.getItem() == Diving.scubaMask) {
            if (!stack.hasTagCompound()) {
                stack.setTagCompound(new NBTTagCompound());
            }

            if (player.isSneaking()) {
                stack.stackTagCompound.setBoolean("ScubaMaskOnOutOfWater", true);
            } else {
                stack.stackTagCompound.setBoolean("ScubaMaskOnOutOfWater", false);
            }
        }

        return stack;
    }
}