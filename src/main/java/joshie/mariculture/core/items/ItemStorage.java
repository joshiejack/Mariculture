package joshie.mariculture.core.items;

import java.util.ArrayList;
import java.util.List;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.core.MaricultureTab;
import joshie.mariculture.core.gui.ContainerStorage;
import joshie.mariculture.core.gui.GuiStorage;
import joshie.mariculture.core.gui.InventoryStorage;
import joshie.mariculture.core.gui.feature.Feature;
import joshie.mariculture.core.handlers.LogHandler;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemStorage extends Item {

    public int size;
    public String gui;
    public static final int GUI_ID = 16;

    public ItemStorage(int storage, String gui) {
        maxStackSize = 1;
        size = storage;
        this.gui = gui;
        setCreativeTab(MaricultureTab.tabCore);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack != null) {
            if (!player.isSneaking()) {
                player.openGui(Mariculture.instance, ItemStorage.GUI_ID, world, 0, 0, 0);
            }

            return stack;
        }

        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + getUnlocalizedName().substring(5));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creative, List list) {
        list.add(new ItemStack(item, 1, 0));
    }

    public Slot getSlot(InventoryStorage storage, int i) {
        switch (i) {
            case 0:
                return new Slot(storage, i, 43, 25);
        }

        return new Slot(storage, i, 100, 100);
    }

    public int getX(ItemStack stack) {
        return 66;
    }

    public boolean isItemValid(ItemStack stack) {
        return true;
    }

    public ItemStack[] load(EntityPlayer player, ItemStack stack, int size) {
        NBTTagCompound loader = stack.hasTagCompound() ? stack.stackTagCompound : new NBTTagCompound();
        NBTTagList nbttaglist = loader.getTagList("Inventory", 10);

        if (nbttaglist != null) {
            ItemStack[] inventory = new ItemStack[size];
            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                byte byte0 = nbttagcompound1.getByte("Slot");
                if (byte0 >= 0 && byte0 < inventory.length) {
                    inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
                }
            }

            return inventory;
        }

        return new ItemStack[size];
    }

    public void save(EntityPlayer player, ItemStack[] inventory) {
        try {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < inventory.length; i++)
                if (inventory[i] != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    inventory[i].writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }

            if (!player.getCurrentEquippedItem().hasTagCompound()) {
                player.getCurrentEquippedItem().setTagCompound(new NBTTagCompound());
            }

            player.getCurrentEquippedItem().stackTagCompound.setTag("Inventory", nbttaglist);

        } catch (Exception e) {
            e.printStackTrace();
            LogHandler.log(Level.WARN, "Trouble saving an items inventory");
        }
    }

    public void addFeatures(ArrayList<Feature> list) {

    }

    public Object getGUIContainer(EntityPlayer player) {
        return new ContainerStorage(player.inventory, new InventoryStorage(player, size), player.worldObj, 0);
    }

    public Object getGUIElement(EntityPlayer player) {
        return new GuiStorage(player.inventory, new InventoryStorage(player, size), player.worldObj, gui, 0);
    }
}