package joshie.mariculture.magic;

import java.util.ArrayList;
import java.util.HashMap;

import joshie.lib.helpers.ClientHelper;
import joshie.mariculture.core.config.Enchantments.Jewelry;
import joshie.mariculture.core.helpers.EnchantHelper;
import joshie.mariculture.magic.enchantments.EnchantmentFlight;
import joshie.mariculture.magic.enchantments.EnchantmentGlide;
import joshie.mariculture.magic.enchantments.EnchantmentJump;
import joshie.mariculture.magic.enchantments.EnchantmentSpeed;
import joshie.mariculture.magic.enchantments.EnchantmentSpider;
import joshie.mariculture.magic.enchantments.EnchantmentStepUp;
import joshie.mariculture.magic.jewelry.parts.JewelryMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MirrorHelper {
    public static String world = "ls43llsds#232423d";
    public static HashMap<String, ItemStack[]> inventories = new HashMap();
    public static ItemStack[] inventory;

    //This is the 'cached' list of the materials the jewelry piece is made up of
    public static HashMap<String, JewelryMaterial[]> materialList = new HashMap();
    public static JewelryMaterial[] materials;

    private static int updateTick;

    public static MirrorSavedData getData(EntityPlayer player) {
        return getData(player.worldObj, player.getCommandSenderName());
    }

    public static MirrorSavedData getData(World world, String name) {
        String check = MirrorSavedData.name + "-" + (Jewelry.JEWELRY_OFFLINE ? "PlayerOffline" : name);
        MirrorSavedData data = (MirrorSavedData) world.loadItemData(MirrorSavedData.class, check);
        if (data == null) {
            data = new MirrorSavedData(check);
            world.setItemData(check, data);
        }

        return data;
    }

    public static boolean isSameWorld(EntityPlayer player) {
        String check = player.worldObj.getWorldInfo().getWorldName();
        if (check.equals(world)) return true;
        else {
            world = check;
            return false;
        }
    }

    /* Cache of the Jewelry Material */
    public static JewelryMaterial[] getMaterials(EntityPlayer player) {
        return player.worldObj.isRemote ? getColorsForClient() : getColorsForPlayer(player);
    }

    public static JewelryMaterial[] getColorsForPlayer(EntityPlayer player) {
        String key = player.getCommandSenderName();
        if (materialList.get(key) != null && isSameWorld(player)) return materialList.get(key);
        else {
            JewelryMaterial[] arr = fetchMaterials(player);
            materialList.put(key, arr);
            return arr;
        }
    }

    @SideOnly(value = Side.CLIENT)
    private static JewelryMaterial[] getColorsForClient() {
        EntityPlayer player = ClientHelper.getPlayer();
        if (materials != null && isSameWorld(player)) return materials;
        else {
            materials = fetchMaterials(player);
            return materials;
        }
    }

    private static JewelryMaterial[] fetchMaterials(EntityPlayer player) {
        ArrayList<JewelryMaterial> array = new ArrayList();
        ItemStack[] mirror = MirrorHelper.getInventory(player);
        for (ItemStack element : mirror)
            if (element != null && element.hasTagCompound()) {
                array.add(JewelryMaterial.list.get(element.stackTagCompound.getString(JewelryMaterial.nbt)));
            }

        return array.toArray(new JewelryMaterial[array.size()]);
    }

    /* Inventory Functions */
    public static ItemStack[] getInventory(EntityPlayer player) {
        return player.worldObj.isRemote ? getInventoryForClient() : getInventoryForPlayer(player);
    }

    public static ItemStack[] getInventoryForPlayer(EntityPlayer player) {
        if (inventories.get(player.getCommandSenderName()) != null && isSameWorld(player)) return inventories.get(player.getCommandSenderName());

        ItemStack[] contents = null;
        MirrorSavedData data = getData(player);
        if (data.getJewelry() == null) {
            contents = data.setJewelry(new ItemStack[4]);
        } else {
            contents = data.getJewelry();
        }

        inventories.put(player.getCommandSenderName(), contents);
        return contents;
    }

    @SideOnly(value = Side.CLIENT)
    public static ItemStack[] getInventoryForClient() {
        if (inventory != null && isSameWorld(ClientHelper.getPlayer())) return inventory;

        MirrorSavedData data = getData(ClientHelper.getPlayer());
        if (data.getJewelry() == null) {
            inventory = data.setJewelry(new ItemStack[4]);
        } else {
            inventory = data.getJewelry();
        }
        return inventory;
    }

    public static boolean save(EntityPlayer player, ItemStack[] invent) {
        return player.worldObj.isRemote ? saveClient(ClientHelper.getPlayer(), invent) : saveServer(player, invent);
    }

    public static boolean saveClient(EntityPlayer player, ItemStack[] invent) {
        inventory = getData(player).setJewelry(invent);
        //Reset the color
        materials = null;

        //Setup the speeds after initially saving client data
        if (EnchantHelper.exists(Magic.speed)) {
            EnchantmentSpeed.set(EnchantHelper.getEnchantStrength(Magic.speed, player));
        }
        if (EnchantHelper.exists(Magic.jump)) {
            EnchantmentJump.set(EnchantHelper.getEnchantStrength(Magic.jump, player));
        }
        if (EnchantHelper.exists(Magic.glide)) {
            EnchantmentGlide.set(EnchantHelper.getEnchantStrength(Magic.glide, player));
        }
        if (EnchantHelper.exists(Magic.spider)) {
            EnchantmentSpider.set(EnchantHelper.hasEnchantment(Magic.spider, player));
        }
        if (EnchantHelper.exists(Magic.flight)) {
            EnchantmentFlight.set(EnchantHelper.getEnchantStrength(Magic.flight, player), player);
        }
        if (EnchantHelper.exists(Magic.stepUp)) {
            EnchantmentStepUp.set(EnchantHelper.getEnchantStrength(Magic.stepUp, player), player);
        }

        return true;
    }

    public static boolean saveServer(EntityPlayer player, ItemStack[] invent) {
        String key = player.getCommandSenderName();
        inventories.put(key, getData(player).setJewelry(invent));
        //Reset the color
        if (materialList.containsKey(key)) {
            materialList.remove(key);
        }

        return true;
    }
}
