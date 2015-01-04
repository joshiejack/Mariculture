package maritech.items;

import java.util.List;

import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.items.ItemMCBaseArmor;
import mariculture.core.lib.CraftingMeta;
import mariculture.lib.util.Text;
import maritech.extensions.modules.ExtensionDiving;
import maritech.handlers.ScubaMask;
import maritech.lib.MTModInfo;
import maritech.model.ModelFlippers;
import maritech.util.MTTranslate;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemArmorScuba extends ItemMCBaseArmor {
    public ItemArmorScuba(ArmorMaterial material, int j, int k) {
        super(MTModInfo.MODPATH, MaricultureTab.tabWorld, material, j, k);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (stack.getItem() == ExtensionDiving.swimfin) return MTModInfo.MODPATH + ":textures/armor/flippers.png";
        if (stack.getItem() == ExtensionDiving.scubaSuit) return MTModInfo.MODPATH + ":" + "textures/armor/scuba" + "_2.png";

        return MTModInfo.MODPATH + ":" + "textures/armor/scuba" + "_1.png";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, int armorSlot) {
        return stack.getItem() == ExtensionDiving.swimfin ? new ModelFlippers() : null;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (stack.getItem() == ExtensionDiving.scubaMask) {
            if (stack.hasTagCompound()) {
                String display = MTTranslate.translate("landlights.format");
                display = display.replace("%L", MTTranslate.translate("landlights"));
                boolean landLightOn = stack.getTagCompound().getBoolean("ScubaMaskOnOutOfWater");
                if (landLightOn) {
                    display = display.replace("%O", Text.DARK_GREEN + MTTranslate.translate("on"));
                } else {
                    display = display.replace("%O", Text.RED + MTTranslate.translate("off"));
                }

                list.add(display);
            } else {
                String display = MTTranslate.translate("landlights.format");
                display = display.replace("%L", MTTranslate.translate("landlights"));
                display = display.replace("%O", Text.RED + MTTranslate.translate("off"));
                list.add(display);
            }
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
        if (stack1.getItem() != ExtensionDiving.scubaTank) return stack2.getItem() == Core.crafting && stack2.getItemDamage() == CraftingMeta.NEOPRENE;
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) if (stack.getItem() == ExtensionDiving.scubaMask) {
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

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        if (!world.isRemote) {
            if (stack.getItem() == ExtensionDiving.scubaMask && world.getWorldTime() % 5 == 0) {
                ScubaMask.activate(player, stack);
            }
        }
    }
}