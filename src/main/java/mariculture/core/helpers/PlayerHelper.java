package mariculture.core.helpers;

import java.util.UUID;

import mariculture.core.lib.ArmorSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

import com.mojang.authlib.GameProfile;

public class PlayerHelper {
    public static ItemStack getArmor(EntityPlayer player, int slot, Item item) {
        ItemStack[] armor = player.inventory.armorInventory;
        if (slot > -1 && slot < 4) {
            if (armor[slot] != null) {
                if (armor[slot].getItem() == item) {
                    return armor[slot];
                }
            }
        }
        return null;
    }

    public static ItemStack getArmorStack(EntityPlayer player, int slot) {
        ItemStack[] armor = player.inventory.armorInventory;

        if (slot > -1 && slot < 4) if (armor[slot] != null) return armor[slot];

        return null;
    }

    public static Item getArmor(EntityPlayer player, int slot) {
        return getArmorStack(player, slot) != null ? getArmorStack(player, slot).getItem() : null;
    }

    public static boolean hasArmor(EntityPlayer player, int slot, Item item) {
        return getArmor(player, slot, item) != null;
    }

    public static int hasItem(EntityPlayer player, ItemStack match, boolean damageable) {
        for (int i = 0; i < player.inventory.mainInventory.length; i++) {
            ItemStack stack = player.inventory.mainInventory[i];
            if (stack != null) {
                if (stack.getItem() == match.getItem() && (stack.getItemDamage() == match.getItemDamage() || damageable)) return i;
            }
        }

        return -1;
    }

    public static FakePlayer getFakePlayer(World world) {
        return new FakePlayer((WorldServer) world, new GameProfile(UUID.fromString("A932BC89-AA6D-3B61-5A76-9823A5D89C4B"), "Mariculture"));
    }

    public static boolean isFake(EntityPlayer player) {
        return player instanceof FakePlayer || player.getDisplayName().equals("[CoFH]") ? true : false;
    }

    //Player can be null, returns a stick otherwise
    public static Item getHelmet(EntityPlayer player) {
        if (player == null) return Items.stick;
        else {
            Item helmet = getArmor(player, ArmorSlot.HAT);
            return helmet == null ? Items.stick : helmet;
        }
    }
}
