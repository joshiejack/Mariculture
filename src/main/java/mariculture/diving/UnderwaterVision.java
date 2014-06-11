package mariculture.diving;

import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class UnderwaterVision {
    protected EntityPlayer player;

    public UnderwaterVision onUpdate(EntityPlayer thePlayer) {
        player = thePlayer;

        if (getGamma(PlayerHelper.getArmor(player, ArmorSlot.HAT)) > 0F) {
            if (player.isInsideOfMaterial(Material.water)) {
                activate();
            } else {
                ItemStack mask = player.inventory.armorItemInSlot(ArmorSlot.HAT);
                if (mask != null) {
                    if (mask.hasTagCompound() && mask.stackTagCompound.getBoolean("ScubaMaskOnOutOfWater") == true) {
                        activate();
                    } else {
                        deactivate();
                    }

                    return this;
                }

                deactivate();
            }
        } else {
            deactivate();
        }

        return this;
    }

    private float getGamma(Item item) {
        if (item == null) return 0F;
        if (item instanceof ItemArmorScuba) return 10F;
        if (item instanceof ItemArmorSnorkel) return 3.5F;

        return 0F;
    }

    protected void activate() {
        if (Minecraft.getMinecraft().thePlayer == player) {
            Minecraft.getMinecraft().entityRenderer.fogColorBuffer.clear();
            float gamma = Minecraft.getMinecraft().gameSettings.gammaSetting;
            if (gamma <= 1) {
                ItemStack mask = player.inventory.armorItemInSlot(ArmorSlot.HAT);
                if (mask != null) {
                    if (!mask.hasTagCompound()) {
                        mask.setTagCompound(new NBTTagCompound());
                    }

                    mask.stackTagCompound.setFloat("gamma", gamma);

                }
            }

            float newGamma = getGamma(PlayerHelper.getArmor(player, ArmorSlot.HAT));
            Minecraft.getMinecraft().gameSettings.gammaSetting = newGamma;
        }
    }

    protected void deactivate() {
        if (Minecraft.getMinecraft().thePlayer == player) if (Minecraft.getMinecraft().gameSettings.gammaSetting > 1F) {
            float gamma = 0.75F;
            ItemStack mask = player.inventory.armorItemInSlot(ArmorSlot.HAT);
            if (mask != null) if (mask.hasTagCompound()) {
                gamma = mask.stackTagCompound.getFloat("gamma");
            }

            Minecraft.getMinecraft().gameSettings.gammaSetting = gamma;
        }
    }
}
