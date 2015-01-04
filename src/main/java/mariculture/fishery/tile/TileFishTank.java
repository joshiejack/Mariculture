package mariculture.fishery.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mariculture.Mariculture;
import mariculture.core.util.IHasClickableButton;
import mariculture.core.util.IMachine;
import mariculture.fishery.gui.FishTankData;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.player.EntityPlayer;
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
    public static final int MAX_PAGES = 10;
    public int previous = -2;
    public int next = -1;
    public int thePage = 0;

    public List<FishTankData> storage = new ArrayList();
    public List<FishTankData> visible = new ArrayList();
    
    //Visible Checkings
    private boolean malesOnly;
    private boolean femalesOnly;
    private boolean pureOnly;

    public List<FishTankData> getVisible() {
        return visible.size() > 0 ? visible : storage;
    }

    public void addFish(ItemStack stack) {
        for (FishTankData data : storage) {
            if (data.matches(stack)) {
                data.add(stack.stackSize);
                return;
            }
        }

        storage.add(new FishTankData(stack.stackTagCompound, stack.stackSize));
        updateVisible();
    }

    public void updateVisible() {
        visible = new ArrayList();
        for (FishTankData fish : storage) {
            visible.add(fish);
        }
    }

    @Override
    public int getSizeInventory() {
        return storage.size();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return null;
    }

    @Override
    public int getInventoryStackLimit() {
        return 4096;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        return;
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
    public boolean isUseableByPlayer(EntityPlayer plaer) {
        return true;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean canUpdate() {
        return false;
    }

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
    public ArrayList<Integer> getGUIData() {
        return new ArrayList(Arrays.asList(new Integer[] { thePage }));
    }

    @Override
    public void setGUIData(int id, int value) {
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

        storage = new ArrayList();
        NBTTagList list = nbt.getTagList("FishData", 10);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            FishTankData data = new FishTankData();
            data.readFromNBT(tag);
            storage.add(data);
        }

        orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
        updateVisible();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList list = new NBTTagList();
        for (FishTankData data : storage) {
            NBTTagCompound tag = new NBTTagCompound();
            data.writeToNBT(tag);
            list.appendTag(tag);
        }

        nbt.setTag("FishData", list);
        nbt.setInteger("Orientation", orientation.ordinal());
    }
}
