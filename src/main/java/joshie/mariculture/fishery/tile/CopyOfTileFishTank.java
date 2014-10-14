package joshie.mariculture.fishery.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import joshie.mariculture.Mariculture;
import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import joshie.mariculture.core.util.IHasClickableButton;
import joshie.mariculture.core.util.IMachine;
import joshie.mariculture.fishery.gui.FishTankData;
import joshie.mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class CopyOfTileFishTank extends TileEntity implements IInventory, IHasClickableButton, IMachine {
    public ForgeDirection orientation = ForgeDirection.UNKNOWN;
    public static final int MAX_PAGES = 10;
    public FishTankData[] fishies = new FishTankData[MAX_PAGES * 54];
    public FishTankData[] backup;
    public int previous = -2;
    public int next = -1;
    public int thePage = 0;

    public class Alphabet implements Comparator<FishTankData> {
        @Override
        public int compare(FishTankData o1, FishTankData o2) {
            if(o1 == null || o2 == null) return 0;
            ItemStack fish1 = o1.make(1);
            ItemStack fish2 = o2.make(1);
            if (!fish1.hasTagCompound() || !fish2.hasTagCompound()) return 0;
            FishSpecies s1 = Fishing.fishHelper.getSpecies(fish1);
            FishSpecies s2 = Fishing.fishHelper.getSpecies(fish2);
            if (s1 == null || s2 == null) return 0;
            return s1.getName().compareTo(s2.getName());
        }
    }

    public void alphabetise() {
        ArrayList<FishTankData> data = new ArrayList();
        for (FishTankData d : fishies) {
            if(d != null) {
                data.add(d);
            }
        }

        Collections.sort(data, new Alphabet());
        setFishies(data);
    }
    
    public void males() {
        ArrayList<FishTankData> data = new ArrayList();
        for (FishTankData d : fishies) {
            if(d != null && Fishing.fishHelper.isMale(d.make())) {
                data.add(d);
            }
        }
        
        setFishies(data);
    }
    
    public void setFishies(ArrayList<FishTankData> data) {
        for(int i = 0; i < fishies.length; i++) {
            if(i < data.size()) {
                fishies[i] = data.get(i);
            } else fishies[i] = null;
        }
    }

    public int getSlotForFish(ItemStack stack) {
        for (int i = 0; i < fishies.length; i++) {
            if (fishies[i] != null) {
                if (fishies[i].matches(stack)) return i;
            }
        }

        for (int i = 0; i < fishies.length; i++) {
            if (fishies[i] == null) {
                fishies[i] = new FishTankData(stack.stackTagCompound, 0);
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSizeInventory() {
        return fishies.length;
    }

    public int getSlot(int slot) {
        int adjusted = ((54 * thePage) + slot);
        if (adjusted >= fishies.length) {
            adjusted = fishies.length - 1;
        }

        return adjusted;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        FishTankData data = fishies[getSlot(slot)];
        if (data != null) {
            return data.make();
        } else return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        FishTankData data = fishies[getSlot(slot)];
        if (data != null) {
            int original = data.getCount();
            data.remove(amount);
            if (data.getCount() <= 0) {
                fishies[slot] = null;
                return data.make(original);
            } else return data.make(amount);
        } else return null;
    }

    @Override
    public int getInventoryStackLimit() {
        return 4096;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return getStackInSlot(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (stack != null) {
            int setSlot = getSlot(slot);
            FishTankData data = fishies[setSlot];
            if (data != null && data.matches(stack)) {
                data.set(stack.stackSize);
            } else fishies[setSlot] = new FishTankData(stack.stackTagCompound, stack.stackSize);
        } else {
            fishies[getSlot(slot)] = null;
        }
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
        for (int i = 0; i < fishies.length; i++) {
            if (nbt.hasKey("Slot" + i)) {
                NBTTagCompound tag = nbt.getCompoundTag("Slot" + i);
                FishTankData data = new FishTankData();
                data.readFromNBT(tag);
                fishies[i] = data;
            }
        }

        orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        for (int i = 0; i < fishies.length; i++) {
            if (fishies[i] != null) {
                NBTTagCompound tag = new NBTTagCompound();
                fishies[i].writeToNBT(tag);
                nbt.setTag("Slot" + i, tag);
            }
        }

        nbt.setInteger("Orientation", orientation.ordinal());
    }
}
