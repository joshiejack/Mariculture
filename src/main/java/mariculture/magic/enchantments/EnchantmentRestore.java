package mariculture.magic.enchantments;

import mariculture.core.config.Enchantments;
import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnchantmentRestore extends EnchantmentJewelry {
    private static int ticker;

    public EnchantmentRestore(int i, int weight, EnumEnchantmentType type) {
        super(i, weight, type);
        setName("restore");
        minLevel = 40;
        maxLevel = 55;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public static void activate(EntityPlayer player) {
        ticker++;
        if (ticker % Enchantments.TICK_REPAIR != 0) return;
        //Repair Handheld
        World world = player.worldObj;
        ItemStack hand = player.getCurrentEquippedItem();
        if (hand != null) if (hand.isItemStackDamageable() && hand.isItemDamaged() && hand.getItem().isRepairable()) if (hand.getItemDamage() > 0) {
            hand.setItemDamage(player.getCurrentEquippedItem().getItemDamage() - 1);
            if (!world.isRemote) {
                EnchantHelper.damageItems(Magic.repair, player, 1);
            }
        }

        int strength = EnchantHelper.getEnchantStrength(Magic.repair, player);

        //Repair Armor
        if (strength > 2) {
            for (int i = 0; i < EnchantHelper.getEnchantStrength(Magic.repair, player) - 1; i++) {
                for (int j = 0; j < 4; j++) {
                    ItemStack armor = player.getCurrentArmor(j);
                    if (armor != null) if (armor.isItemStackDamageable() && armor.isItemDamaged() && armor.getItem().isRepairable()) if (armor.getItemDamage() > 0) {
                        armor.setItemDamage(armor.getItemDamage() - 1);
                        if (!world.isRemote) {
                            EnchantHelper.damageItems(Magic.repair, player, 1);
                        }
                    }
                }
            }
        }

        //Repair the Hotbar
        if (strength > 1) {
            for (int i = 0; i < 9; i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (stack != null) if (stack.isItemStackDamageable() && stack.isItemDamaged() && stack.getItem().isRepairable()) if (stack.getItemDamage() > 0) {
                    stack.setItemDamage(stack.getItemDamage() - 1);
                    if (!world.isRemote) {
                        EnchantHelper.damageItems(Magic.repair, player, 1);
                    }
                }
            }
        }
    }
}
