package mariculture.magic;

import mariculture.core.gui.ContainerStorage;
import mariculture.core.gui.InventoryStorage;
import mariculture.core.util.Rand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class InventoryMirror extends InventoryStorage {
    private ItemStack[] inventory;

    public InventoryMirror(EntityPlayer player) {
        this.player = player;
        inventory = MirrorHelper.getInventory(player);
    }

    @Override
    public ItemStack getHeldItem() {
        return player != null ? player.getCurrentEquippedItem() : null;
    }

    @Override
    public String getName() {
        return "Default Storage Item";
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (inventory[i] != null) {
            if (inventory[i].stackSize <= j) {
                ItemStack itemstack = inventory[i];
                inventory[i] = null;
                markDirty();
                return itemstack;
            }
            ItemStack itemstack1 = inventory[i].splitStack(j);
            if (inventory[i].stackSize == 0) {
                inventory[i] = null;
            }
            markDirty();
            return itemstack1;
        } else return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory[i] = itemstack;

        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
            itemstack.stackSize = getInventoryStackLimit();
        }

        markDirty();
    }

    @Override
    public String getInventoryName() {
        return StatCollector.translateToLocal(player.getCurrentEquippedItem().getUnlocalizedName());
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        seed = Rand.rand.nextLong();
        MirrorHelper.save(player, inventory);
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public void openInventory() {
        //onInventoryChanged();
    }

    @Override
    public void closeInventory() {
        //onInventoryChanged();
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return getStackInSlot(var1);
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return false;
    }

    @Override
    public void getGUINetworkData(int i, int j) {
        return;
    }

    @Override
    public void sendGUINetworkData(ContainerStorage container, ICrafting iCrafting) {
        return;
    }
}