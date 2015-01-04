package mariculture.magic;

import mariculture.api.core.handlers.IMirrorHandler;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.network.PacketDamageJewelry;
import mariculture.core.network.PacketHandler;
import mariculture.core.network.PacketSyncMirror;
import mariculture.magic.jewelry.ItemJewelry;
import mariculture.magic.jewelry.parts.JewelryMaterial;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MirrorHandler implements IMirrorHandler {
    @Override
    public boolean containsEnchantedItems(EntityPlayer player) {
        // Mirror
        ItemStack[] mirror = MirrorHelper.getInventory(player);
        if (mirror != null) {
            for (int i = 0; i < 3; i++)
                if (mirror[i] != null) if (mirror[i].isItemEnchanted() && !EnchantHelper.isBroken(mirror[i])) return true;
        }

        return false;
    }

    @Override
    public void dropItems(EntityPlayer player, World world, double posX, double posY, double posZ) {
        ItemStack[] mirror = MirrorHelper.getInventory(player);
        for (int i = 0; i < mirror.length; ++i)
            if (mirror[i] != null) {
                player.dropPlayerItemWithRandomChoice(mirror[i], true);
                mirror[i] = null;
            }

        MirrorHelper.save(player, mirror);
    }

    @Override
    public int getEnchantmentStrength(EntityPlayer player, int enchant) {
        ItemStack[] mirror = MirrorHelper.getInventory(player);
        int total = 0;
        for (int i = 0; i < 3; i++)
            if (mirror[i] != null && !EnchantHelper.isBroken(mirror[i])) {
                total = total + EnchantHelper.getLevel(enchant, mirror[i]);
            }

        return total;
    }

    @Override
    public boolean hasEnchantment(EntityPlayer player, int enchant) {
        ItemStack[] mirror = MirrorHelper.getInventory(player);
        for (int i = 0; i < 3; i++)
            if (mirror[i] != null) if (EnchantHelper.hasEnchantment(enchant, mirror[i])) return true;

        return false;
    }

    @Override
    public ItemStack[] getMirrorContents(EntityPlayer player) {
        return MirrorHelper.getInventory(player);
    }

    @Override
    public void setMirrorContents(EntityPlayer player, ItemStack[] inventory) {
        MirrorHelper.save(player, inventory);
    }

    @Override
    public void damageItemsWithEnchantment(EntityPlayer player, int enchant, int amount) {
        if (player.worldObj.isRemote && player instanceof EntityClientPlayerMP) {
            PacketHandler.sendToServer(new PacketDamageJewelry(enchant, amount));
        } else {
            handleDamage(player, enchant, amount);
        }
    }

    public static void handleDamage(EntityPlayer player, int enchant, int amount) {
        // Mirror
        //Set the amount of damage to 1 if the enchantment is the elemental enchant
        int matId = amount;
        amount = EnchantHelper.exists(Magic.elemental) && enchant == Magic.elemental.effectId ? 1 : amount;
        ItemStack[] mirror = MirrorHelper.getInventory(player);
        for (int damaged = 0; damaged < amount; damaged++) {
            for (int i = 0; i < 3; i++)
                if (mirror[i] != null) if (EnchantHelper.hasEnchantment(enchant, mirror[i])) if (EnchantHelper.exists(Magic.elemental) && enchant == Magic.elemental.effectId) {
                    JewelryMaterial material = JewelryHandler.getMaterial(mirror[i]);
                    if (material.id == matId) if (mirror[i].attemptDamageItem(1, player.worldObj.rand)) {
                        PacketHandler.sendToClient(new PacketSyncMirror(MirrorHelper.getInventoryForPlayer(player)), (EntityPlayerMP) player);
                    }
                } else if (mirror[i].attemptDamageItem(1, player.worldObj.rand)) {
                    PacketHandler.sendToClient(new PacketSyncMirror(MirrorHelper.getInventoryForPlayer(player)), (EntityPlayerMP) player);
                }
        }

        MirrorHelper.save(player, mirror);
    }

    @Override
    public boolean isJewelry(ItemStack stack) {
        return stack != null && stack.getItem() instanceof ItemJewelry;
    }
}
