package mariculture.core.tile;

import java.util.ArrayList;
import java.util.List;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeVat;
import mariculture.core.helpers.cofh.CoFhItemHelper;
import mariculture.core.network.PacketHandler;
import mariculture.core.tile.base.TileMultiBlock;
import mariculture.core.tile.base.TileMultiStorage;
import mariculture.core.util.ITank;
import mariculture.core.util.Tank;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileVat extends TileMultiStorage implements ISidedInventory, IFluidHandler, ITank {
    public static final int max_lrg = 30000;
    public static final int max_sml = 6000;
    public Tank tank;
    public Tank tank2;
    public Tank tank3;

    public int timeNeeded;
    public int timeRemaining;
    public boolean canWork;
    private int machineTick;

    public TileVat() {
        tank = new Tank(max_sml);
        tank2 = new Tank(max_sml);
        tank3 = new Tank(max_sml);
        inventory = new ItemStack[2];
        needsInit = true;
    }

    public boolean onTick(int i) {
        return machineTick % i == 0;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (master == null) {
            updateSingle();
        }
    }

    //Updating the Multi-Block form
    @Override
    public void updateMaster() {
        if (tank.getCapacity() != max_lrg) {
            tank.setCapacity(max_lrg);
        }
        if (tank2.getCapacity() != max_lrg) {
            tank2.setCapacity(max_lrg);
        }
        if (tank3.getCapacity() != max_lrg) {
            tank3.setCapacity(max_lrg);
        }

        machineTick++;
        if (!isInit() && !worldObj.isRemote) {
            //Init Master
            PacketHandler.syncMultiBlock(getMaster(), this, facing);
            for (MultiPart slave : slaves) {
                TileEntity te = worldObj.getTileEntity(slave.xCoord, slave.yCoord, slave.zCoord);
                if (te != null && te instanceof TileVat) {
                    PacketHandler.syncMultiBlock(getMaster(), te, ((TileMultiBlock) te).facing);
                }
            }

            setInit(true);
        }

        if (!worldObj.isRemote) if (canWork && onTick(20)) {
            timeRemaining++;
        }

        if (worldObj.isRemote && canWork) {
            worldObj.spawnParticle("smoke", xCoord + 0.5D + +worldObj.rand.nextFloat() - worldObj.rand.nextFloat() / 2, yCoord + 0.8D + worldObj.rand.nextFloat() - worldObj.rand.nextFloat() / 2, zCoord + 0.5D + +worldObj.rand.nextFloat() - worldObj.rand.nextFloat() / 2, 0, 0, 0);
        }

        updateAll();
    }

    //The Code for when we are updating a single block!
    public void updateSingle() {
        machineTick++;
        if (!worldObj.isRemote) if (canWork) if (onTick(30)) {
            timeRemaining++;
        }

        updateAll();

        if (worldObj.isRemote && canWork) {
            worldObj.spawnParticle("smoke", xCoord + 0.5D + +worldObj.rand.nextFloat() - worldObj.rand.nextFloat() / 2, yCoord + 0.8D + worldObj.rand.nextFloat() - worldObj.rand.nextFloat() / 2, zCoord + 0.5D + +worldObj.rand.nextFloat() - worldObj.rand.nextFloat() / 2, 0, 0, 0);
        }
    }

    public void updateAll() {
        if (onTick(20)) {
            canWork = canWork();
        }

        if (canWork && !worldObj.isRemote) {
            if (timeNeeded == 0) {
                RecipeVat recipe = (RecipeVat) getResult()[0];
                if (recipe != null) {
                    timeNeeded = recipe.processTime;
                }
                timeRemaining = 0;
            }

            if (timeRemaining >= timeNeeded) {
                Object[] result = getResult();
                RecipeVat recipe = (RecipeVat) result[0];
                byte tankNum = (Byte) result[1];
                if (recipe != null) {
                    createResult(recipe, tankNum);
                }
                timeRemaining = 0;
                timeNeeded = 0;
                canWork = canWork();
            }
        }
    }

    private FluidStack drain(byte id, FluidStack input, FluidStack output, boolean doDrain) {
        int drain = input.copy().amount;
        if (input.isFluidEqual(output)) {
            drain -= output.amount;
            output = null;
        }

        if (doDrain) if (id == (byte) 1) {
            tank.drain(drain, true);
        } else {
            tank2.drain(drain, true);
        }

        return output;
    }

    private void createResult(RecipeVat recipe, byte tankNum) {
        //Drain out the fluid1
        FluidStack outputFluid = null;
        if (recipe.outputFluid != null) {
            outputFluid = recipe.outputFluid.copy();
        }

        if (tankNum == 1) {
            if (recipe.inputFluid1 != null) {
                outputFluid = drain((byte) 1, recipe.inputFluid1, outputFluid, true);
            }
            if (recipe.inputFluid2 != null) {
                outputFluid = drain((byte) 2, recipe.inputFluid2, outputFluid, true);
            }
        } else {
            if (recipe.inputFluid1 != null) {
                outputFluid = drain((byte) 2, recipe.inputFluid1, outputFluid, true);
            }
            if (recipe.inputFluid2 != null) {
                outputFluid = drain((byte) 1, recipe.inputFluid2, outputFluid, true);
            }
        }

        //Decrease the StackSize of the input, by this much if it's valid
        if (recipe.inputItem != null) {
            decrStackSize(0, recipe.inputItem instanceof ItemStack ? (((ItemStack) recipe.inputItem).stackSize) : 1);
        }

        //Add the new Fluid
        if (outputFluid != null && outputFluid.amount > 0) {
            tank3.fill(recipe.outputFluid.copy(), true);
        }

        //Add the new Item
        if (recipe.outputItem != null) {
            ItemStack output = recipe.outputItem.copy();
            if (inventory[1] != null) {
                output.stackSize += inventory[1].stackSize;
            }

            setInventorySlotContents(1, output);
        }

        PacketHandler.syncFluidTank(this, getFluid((byte) 1), (byte) 1);
        PacketHandler.syncFluidTank(this, getFluid((byte) 2), (byte) 2);
        PacketHandler.syncFluidTank(this, getFluid((byte) 3), (byte) 3);
    }

    public boolean canWork() {
        if (tank.getFluidAmount() <= 0 && tank2.getFluidAmount() <= 0) return false;
        RecipeVat res = (RecipeVat) getResult()[0];
        return res != null;
    }

    public Object[] getResult() {
        byte tankNum = 1;
        RecipeVat result = MaricultureHandlers.vat.getResult(tank.getFluid(), tank2.getFluid(), inventory[0]);
        result = result == null || !hasRoom(result.outputItem, result.outputFluid) ? null : result;
        if (result == null) {
            tankNum = 2;
            result = MaricultureHandlers.vat.getResult(tank2.getFluid(), tank.getFluid(), inventory[0]);
            result = result == null || !hasRoom(result.outputItem, result.outputFluid) ? null : result;
        }

        return new Object[] { result, tankNum };
    }

    private boolean hasRoom(ItemStack stack, FluidStack newFluid) {
        if (tank3.getFluid() != null && newFluid != null) if (newFluid != tank3.getFluid() || newFluid.amount + tank3.getFluidAmount() > tank3.getCapacity()) return false;

        return stack == null || inventory[1] == null || CoFhItemHelper.areItemStackEqualNoNull(stack, inventory[1]) && inventory[1].stackSize + stack.stackSize <= inventory[1].getMaxStackSize();
    }

    @Override
    public Class getTEClass() {
        return this.getClass();
    }

    //ISided
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 0, 1 };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return slot == 0;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        if (slot == 0) return side != ForgeDirection.DOWN.ordinal();

        return true;
    }

    //Inventory Logic
    @Override
    public ItemStack getStackInSlot(int slot) {
        TileVat vat = master != null ? (TileVat) worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord) : this;
        if (vat == null) return null;
        return vat.inventory[slot];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        TileVat vat = master != null ? (TileVat) worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord) : this;
        if (vat == null) return null;
        if (vat.inventory[slot] != null) {
            ItemStack stack = vat.inventory[slot];
            vat.inventory[slot] = null;
            return stack;
        }

        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        TileVat vat = master != null ? (TileVat) worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord) : this;
        if (vat == null) return;

        vat.inventory[slot] = stack;
        if (stack != null && stack.stackSize > vat.getInventoryStackLimit()) {
            stack.stackSize = vat.getInventoryStackLimit();
        }

        vat.markDirty();
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        TileVat vat = master != null ? (TileVat) worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord) : this;
        if (vat == null) return null;
        if (vat.inventory[slot] != null) {
            ItemStack stack;

            if (vat.inventory[slot].stackSize <= amount) {
                stack = vat.inventory[slot];
                vat.inventory[slot] = null;
                vat.markDirty();
                return stack;
            } else {
                stack = vat.inventory[slot].splitStack(amount);

                if (vat.inventory[slot].stackSize == 0) {
                    vat.inventory[slot] = null;
                }

                vat.markDirty();
                return stack;
            }
        }

        return null;
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (!worldObj.isRemote) {
            PacketHandler.syncInventory(this, inventory);
        }
    }

    //Tank Logic
    @Override
    public FluidStack getFluid(int transfer) {
        TileVat vat = master != null ? (TileVat) worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord) : this;
        if (vat == null) return null;
        if (vat.tank.getFluid() == null) return null;
        if (vat.tank.getFluidAmount() - transfer < 0) return null;

        return new FluidStack(vat.tank.getFluid(), transfer);
    }

    @Override
    public FluidStack getFluid() {
        return getFluid((byte) 1);
    }

    @Override
    public FluidStack getFluid(byte tank) {
        TileVat vat = master != null ? (TileVat) worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord) : this;
        if (vat == null) return null;
        if (tank == 1) return vat.tank.getFluid();
        else if (tank == 2) return vat.tank2.getFluid();
        else if (tank == 3) return vat.tank3.getFluid();
        return null;
    }

    @Override
    public void setFluid(FluidStack fluid) {
        setFluid(fluid, (byte) 1);
    }

    @Override
    public void setFluid(FluidStack fluid, byte tank) {
        TileVat vat = master != null ? (TileVat) worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord) : this;
        if (vat == null) return;

        if (tank == (byte) 1) {
            vat.tank.setFluid(fluid);
        } else if (tank == (byte) 2) {
            vat.tank2.setFluid(fluid);
        } else if (tank == (byte) 3) {
            vat.tank3.setFluid(fluid);
        }
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        TileVat vat = master != null ? (TileVat) worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord) : this;
        if (vat == null) return null;
        //If the draining from tank3 didn't fail, send packet update, otherwise try for tank2, then tank1
        FluidStack ret = vat.tank3.drain(maxDrain, doDrain);
        if (ret != null) {
            if (doDrain) {
                PacketHandler.syncFluidTank(this, getFluid((byte) 3), (byte) 3);
            }
        } else {
            ret = vat.tank2.drain(maxDrain, doDrain);
            if (ret != null) {
                if (doDrain) {
                    PacketHandler.syncFluidTank(this, getFluid((byte) 2), (byte) 2);
                }
            } else {
                ret = vat.tank.drain(maxDrain, doDrain);
                if (ret != null) if (doDrain) {
                    PacketHandler.syncFluidTank(this, getFluid((byte) 1), (byte) 1);
                }
            }
        }

        return ret;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        TileVat vat = master != null ? (TileVat) worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord) : this;
        if (vat == null) return 0;
        
        int ret = vat.tank.fill(resource, doFill, vat.tank2);
        if (ret > 0) {
            if (doFill) {
                PacketHandler.syncFluidTank(this, getFluid((byte) 1), (byte) 1);
            }
        } else {
            ret = vat.tank2.fill(resource, doFill, vat.tank);
            if (ret > 0) if (doFill) {
                PacketHandler.syncFluidTank(this, getFluid((byte) 2), (byte) 2);
            }
        }

        return ret;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        TileVat vat = master != null ? (TileVat) worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord) : this;
        if (vat == null) return null;

        return new FluidTankInfo[] { vat.tank.getInfo(), vat.tank2.getInfo(), vat.tank3.getInfo() };
    }

    /** End Tank Stuffs**/

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList tagList = nbt.getTagList("Tanks", 10);
        for (int i = 0; i < 3; i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            byte tank = tag.getByte("Tank");
            getTank(i).readFromNBT(tag);
        }

        timeNeeded = nbt.getInteger("TimeNeeded");
        timeRemaining = nbt.getInteger("TimeRemaining");
        canWork = nbt.getBoolean("CanWork");
    }

    public Tank getTank(int i) {
        if (i == 0) return tank;
        if (i == 1) return tank2;
        return tank3;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList tankList = new NBTTagList();
        for (int i = 0; i < 3; i++) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("Tank", (byte) i);
            getTank(i).writeToNBT(tag);
            tankList.appendTag(tag);
        }

        nbt.setTag("Tanks", tankList);
        nbt.setInteger("TimeNeeded", timeNeeded);
        nbt.setInteger("TimeRemaining", timeRemaining);
        nbt.setBoolean("CanWork", canWork);
    }

    //Master Logic	
    @Override
    public void onBlockBreak() {
        if (master != null) {
            TileVat mstr = (TileVat) worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord);
            if (mstr != null) {
                //Set all three tanks back to small size
                mstr.tank.setCapacity(max_sml);
                if (mstr.tank.getFluidAmount() > max_sml) {
                    mstr.tank.setFluidAmount(max_sml);
                }

                mstr.tank2.setCapacity(max_sml);
                if (mstr.tank2.getFluidAmount() > max_sml) {
                    mstr.tank2.setFluidAmount(max_sml);
                }

                mstr.tank3.setCapacity(max_sml);
                if (mstr.tank3.getFluidAmount() > max_sml) {
                    mstr.tank3.setFluidAmount(max_sml);
                }
            }
        }

        super.onBlockBreak();
    }
    
    @Override
    public boolean isPartnered(int x, int y, int z) {
        TileEntity tile = worldObj.getTileEntity(x, y, z);
        return tile instanceof TileVat ? ((TileVat) tile).master != null : false;
    }

    @Override
    public boolean isPart(int x, int y, int z) {
        return worldObj.getTileEntity(x, y, z) instanceof TileVat && !isPartnered(x, y, z);
    }

    @Override
    public void onBlockPlaced() {
        onBlockPlaced(xCoord, yCoord, zCoord);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public void onBlockPlaced(int x, int y, int z) {
        if (isPart(x, y, z) && isPart(x + 1, y, z) && isPart(x, y, z + 1) && isPart(x + 1, y, z + 1)) {
            MultiPart mstr = new MultiPart(x, y, z);
            ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
            parts.add(setAsSlave(mstr, x + 1, y, z, ForgeDirection.WEST));
            parts.add(setAsSlave(mstr, x + 1, y, z + 1, ForgeDirection.NORTH));
            parts.add(setAsSlave(mstr, x, y, z + 1, ForgeDirection.EAST));
            setAsMaster(mstr, parts, ForgeDirection.SOUTH);
        }

        if (isPart(x - 1, y, z) && isPart(x, y, z) && isPart(x, y, z + 1) && isPart(x - 1, y, z + 1)) {
            MultiPart mstr = new MultiPart(x - 1, y, z);
            ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
            parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.WEST));
            parts.add(setAsSlave(mstr, x - 1, y, z + 1, ForgeDirection.EAST));
            parts.add(setAsSlave(mstr, x, y, z + 1, ForgeDirection.NORTH));
            setAsMaster(mstr, parts, ForgeDirection.SOUTH);
        }

        if (isPart(x, y, z) && isPart(x - 1, y, z) && isPart(x - 1, y, z - 1) && isPart(x, y, z - 1)) {
            MultiPart mstr = new MultiPart(x - 1, y, z - 1);
            ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
            parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.NORTH));
            parts.add(setAsSlave(mstr, x - 1, y, z, ForgeDirection.EAST));
            parts.add(setAsSlave(mstr, x, y, z - 1, ForgeDirection.WEST));
            setAsMaster(mstr, parts, ForgeDirection.SOUTH);
        }

        if (isPart(x, y, z) && isPart(x + 1, y, z) && isPart(x, y, z - 1) && isPart(x + 1, y, z - 1)) {
            MultiPart mstr = new MultiPart(x, y, z - 1);
            ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
            parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.EAST));
            parts.add(setAsSlave(mstr, x + 1, y, z, ForgeDirection.NORTH));
            parts.add(setAsSlave(mstr, x + 1, y, z - 1, ForgeDirection.WEST));
            setAsMaster(mstr, parts, ForgeDirection.SOUTH);
        }

        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return drain(from, resource.amount, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public int getTankScaled(int i) {
        return 0;
    }

    @Override
    public String getFluidName() {
        return null;
    }

    @Override
    public List getFluidQty(List tooltip) {
        return null;
    }
}
