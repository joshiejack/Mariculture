package mariculture.fishery.tile;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.IIncubator;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.network.PacketHandler;
import mariculture.core.network.PacketParticle;
import mariculture.core.network.PacketParticle.Particle;
import mariculture.core.tile.base.TileStorage;
import mariculture.fishery.Fishery;
import mariculture.lib.helpers.ItemHelper;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileHatchery extends TileStorage implements ISidedInventory, IIncubator {
    protected static final int MAX = MachineSpeeds.getHatcherySpeed();

    public static final int[] IN = new int[] { 0 };
    public static final int[] OUT = new int[] { 1, 2 };

    private boolean isInit = false;
    private boolean canWork;
    private int processed;
    private int speed;

    public TileHatchery() {
        inventory = new ItemStack[3];
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public void onInventoryChange(int slot) {
        updateCanWork();

        if (!worldObj.isRemote) {
            PacketHandler.syncInventory(this, getInventory());
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side != ForgeDirection.DOWN.ordinal() ? IN : OUT;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return stack.getItem() == Fishery.fishEggs && inventory[0] == null && stack.stackSize == 1 && side != ForgeDirection.DOWN.ordinal();
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return side == ForgeDirection.DOWN.ordinal() && slot > 0;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    public boolean onTick(int i) {
        return worldObj.getTotalWorldTime() % i == 0;
    }

    private void updateCanWork() {
        canWork = inventory[0] != null && Fishing.fishHelper.isEgg(inventory[0]) && (inventory[1] == null || inventory[2] == null);
    }

    public int isWater(int x, int y, int z, int count) {
        if (BlockHelper.isWater(worldObj, x, y, z)) {
            return count + 1;
        } else return count;
    }

    public int isHatchery(int x, int y, int z, int count) {
        if (worldObj.getTileEntity(x, y, z) == this) {
            return count + 1;
        } else return count;
    }

    public void updateSurrounding() {
        int hatcheryCount = 0;
        int waterCount = 0;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    hatcheryCount = isHatchery(xCoord + x, yCoord + y, zCoord + z, hatcheryCount);
                    waterCount = isWater(xCoord + x, yCoord + y, zCoord + z, waterCount);
                }
            }
        }

        int difference = hatcheryCount >= waterCount ? hatcheryCount - waterCount : waterCount - hatcheryCount;
        int multiplier = 27 - difference;
        speed = Math.max(1, (multiplier * hatcheryCount));
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (!isInit) {
                isInit = true;
                updateCanWork();
                updateSurrounding();
            }

            if (canWork) {
                if (onTick(20)) {
                    PacketHandler.sendAround(new PacketParticle(Particle.SPLASH, 8, xCoord, yCoord - 0.05, zCoord), this);
                }

                processed += speed;

                if (processed >= MAX) {
                    inventory[0] = Fishing.fishHelper.attemptToHatchEgg(inventory[0], worldObj.rand, 1.0D, this);
                    updateCanWork();
                    processed = 0;
                }
            } else {
                processed = 0;
            }
        }
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    @Override
    public int getBirthChanceBoost() {
        return 19;
    }

    @Override
    public void eject(ItemStack fish) {
        if (inventory[1] == null) setInventorySlotContents(1, fish);
        else if (inventory[2] == null) setInventorySlotContents(2, fish);
        else ItemHelper.spawnItem(worldObj, xCoord, yCoord + 1, zCoord, fish);
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
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        canWork = nbt.getBoolean("CanWork");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("CanWork", canWork);
    }
}
