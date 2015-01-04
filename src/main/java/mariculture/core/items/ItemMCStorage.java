package mariculture.core.items;

import java.util.ArrayList;

import mariculture.Mariculture;
import mariculture.core.gui.ContainerStorage;
import mariculture.core.gui.GuiStorage;
import mariculture.core.gui.InventoryStorage;
import mariculture.core.gui.feature.Feature;
import mariculture.core.handlers.LogHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

public class ItemMCStorage extends ItemMCBaseSingle {
    public static final int GUI_ID = 16;
    public int size;
    public String gui;

    public ItemMCStorage(int storage, String gui) {
        size = storage;
        this.gui = gui;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack != null) {
            if (!player.isSneaking()) {
                player.openGui(Mariculture.instance, ItemMCStorage.GUI_ID, world, 0, 0, 0);
            }

            return stack;
        }

        return null;
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