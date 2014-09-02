package joshie.mariculture.magic.enchantments;

import joshie.mariculture.api.core.MaricultureHandlers;
import joshie.mariculture.core.config.Enchantments.Jewelry;
import joshie.mariculture.core.helpers.EnchantHelper;
import joshie.mariculture.magic.Magic;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class EnchantmentResurrection extends EnchantmentJewelry {
    public static final String spawnX = "ResurrectionSpawnX";
    public static final String spawnY = "ResurrectionSpawnY";
    public static final String spawnZ = "ResurrectionSpawnZ";
    public static final String armor = "ResurrectionArmorList";
    public static final String inventory = "ResurrectionInventoryList";
    public static final String resistTime = "ResurrectionResistTime";

    public EnchantmentResurrection(int i, int weight, EnumEnchantmentType type) {
        super(i, weight, type);
        setName("resurrection");
        minLevel = 50;
        maxLevel = 55;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public static void activate(LivingDeathEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;

            if (EnchantHelper.hasEnchantment(Magic.resurrection, player) && !player.worldObj.isRemote) {
                if (!player.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
                    player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
                }

                if (!event.source.isFireDamage() && !event.source.canHarmInCreative() && EnchantHelper.getEnchantStrength(Magic.resurrection, player) > 1) {
                    player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setInteger(spawnX, (int) player.posX);
                    player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setInteger(spawnY, (int) player.posY);
                    player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setInteger(spawnZ, (int) player.posZ);
                }

                if (EnchantHelper.getEnchantStrength(Magic.resurrection, player) > 2) {
                    int modifier = EnchantHelper.getEnchantStrength(Magic.resurrection, player);
                    player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setInteger(resistTime, 50 * modifier);
                }

                /* Save Main Inventory */
                ItemStack[] inv = player.inventory.mainInventory;
                NBTTagList nbttaglist = new NBTTagList();
                for (int i = 0; i < inv.length; i++)
                    if (inv[i] != null) {
                        final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                        nbttagcompound1.setByte("Slot", (byte) i);
                        inv[i].writeToNBT(nbttagcompound1);
                        nbttaglist.appendTag(nbttagcompound1);
                    }

                player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setTag(inventory, nbttaglist);

                // Save Armor
                ItemStack[] arm = player.inventory.armorInventory;
                NBTTagList armorList = new NBTTagList();
                for (int i = 0; i < arm.length; i++)
                    if (arm[i] != null) {
                        final NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                        nbttagcompound1.setByte("Slot", (byte) i);
                        arm[i].writeToNBT(nbttagcompound1);
                        armorList.appendTag(nbttagcompound1);
                    }

                player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setTag(armor, armorList);

                event.setCanceled(true);
            } else if (Jewelry.DROP_JEWELRY && player.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory") == false) {
                MaricultureHandlers.mirror.dropItems(player, player.worldObj, player.posX, player.posY, player.posZ);
            }
        }
    }
}
