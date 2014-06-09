package mariculture.fishery.tile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mariculture.Mariculture;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.util.IHasClickableButton;
import mariculture.core.util.IMachine;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileFishTank extends TileEntity implements IInventory, IHasClickableButton, IMachine {

    public ForgeDirection orientation = ForgeDirection.UNKNOWN;
    public HashMap<Integer, ItemStack> fish;
    public static final int MAX_PAGES = 250;

    public int previous = -2;
    public int next = -1;
    public int thePage = 0;

    public TileFishTank() {
        fish = new HashMap();
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return fish.size();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (fish.containsKey(slot)) return fish.get(slot);
        return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (fish.containsKey(slot)) {
            ItemStack stack = fish.get(slot);
            fish.put(slot, null);
            markDirty();

            return stack;
        }

        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        fish.put(slot, stack);
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return stack.getItem() instanceof ItemFishy;
    }

    @Override
    public void handleButtonClick(int id) {
        int page = thePage;
        if (id == next) {
            page += 1;
            if (page >= MAX_PAGES) {
                page = 0;
            }
        } else if (id == previous) {
            page -= 1;
            if (page < 0) {
                page = MAX_PAGES - 1;
            }
        }

        thePage = page;
        markDirty();

        if (id >= 0) {
            EntityPlayer player = (EntityPlayer) worldObj.getEntityByID(id);
            player.openGui(Mariculture.instance, -1, worldObj, xCoord, yCoord, zCoord);
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
    }

    @Override
    public void sendGUINetworkData(ContainerMariculture container, ICrafting crafting) {
        crafting.sendProgressBarUpdate(container, 0, thePage);
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (id == 0) {
            thePage = value;
        }
    }

    @Override
    public ItemStack[] getInventory() {
        return null;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        NBTTagList tagList = nbt.getTagList("FishList", 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            ItemStack stack = ItemStack.loadItemStackFromNBT(tag);
            fish.put(tag.getInteger("Key"), stack);
        }

        orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        NBTTagList itemList = new NBTTagList();
        Iterator it = fish.entrySet().iterator();
        while (it.hasNext()) {
            NBTTagCompound tag = new NBTTagCompound();
            Map.Entry pairs = (Map.Entry) it.next();
            tag.setInteger("Key", (Integer) pairs.getKey());
            ItemStack stack = (ItemStack) pairs.getValue();
            if (stack != null) {
                stack.writeToNBT(tag);
            }

            itemList.appendTag(tag);
        }

        nbt.setTag("FishList", itemList);
        nbt.setInteger("Orientation", orientation.ordinal());
    }
}
