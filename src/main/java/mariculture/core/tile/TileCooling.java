package mariculture.core.tile;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting;
import mariculture.api.core.Environment.Temperature;
import mariculture.core.config.Machines;
import mariculture.core.config.Machines.MachineSettings;
import mariculture.core.network.PacketHandler;
import mariculture.core.tile.base.TileStorageTank;
import mariculture.core.util.Tank;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public abstract class TileCooling extends TileStorageTank implements ISidedInventory {
    public boolean canWork;
    public int freezeTick;
    private int cooling;

    public TileCooling() {
        inventory = new ItemStack[getInventorySize()];
        tank = new Tank(getTankSize());
    }

    public abstract int getInventorySize();

    public abstract int getTankSize();

    public abstract int getTime();

    public abstract RecipeCasting getResult();

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            //Setup the cooling 
            if (cooling <= 0) {
                cooling = Math.max(1, Temperature.getCoolingSpeed(MaricultureHandlers.environment.getBiomeTemperature(worldObj, xCoord, yCoord, zCoord)));
            }

            if (MachineSettings.COOLING_KICK_UP_BUTT > 0) {
                if (worldObj.getTotalWorldTime() % MachineSettings.COOLING_KICK_UP_BUTT == 1) {
                    canWork = canWork();
                }
            }

            if (canWork) {
                freezeTick += cooling;
                if (freezeTick >= getTime()) {
                    RecipeCasting result = getResult();
                    if (result != null) {
                        for (int i = 0; i < inventory.length; i++)
                            if (inventory[i] == null && tank.getFluidAmount() >= result.fluid.amount) {
                                drain(ForgeDirection.UP, result.fluid.amount, true);
                                setInventorySlotContents(i, result.output.copy());
                                if (!canWork) {
                                    break;
                                }
                            }
                    }

                    freezeTick = 0;
                }
            }
        }
    }

    public boolean canWork() {
        return !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && hasRoom() && canFreeze();
    }

    public boolean canFreeze() {
        return getResult() != null;
    }

    public boolean hasRoom() {
        for (ItemStack element : inventory) {
            if (element == null) return true;
        }

        return false;
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (!worldObj.isRemote) {
            canWork = canWork();
            PacketHandler.syncInventory(this, inventory);
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
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int amount = tank.fill(resource, doFill);
        if (doFill) {
            if (amount > 0 && !worldObj.isRemote) {
                PacketHandler.syncFluids(this, getFluid());
                freezeTick = 0;
            }

            canWork = canWork();
        }

        return amount;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        FluidStack amount = tank.drain(maxDrain, doDrain);
        if (doDrain) {
            if (amount != null && !worldObj.isRemote) {
                PacketHandler.syncFluids(this, getFluid());
            }

            canWork = canWork();
        }

        return amount;
    }

    @Override
    public abstract int[] getAccessibleSlotsFromSide(int side);

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        freezeTick = nbt.getInteger("FreezeTick");
        cooling = nbt.getInteger("CoolingSpeed");
        canWork = nbt.getBoolean("CanWork");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("FreezeTick", freezeTick);
        nbt.setInteger("CoolingSpeed", cooling);
        nbt.setBoolean("CanWork", canWork);
    }
}
