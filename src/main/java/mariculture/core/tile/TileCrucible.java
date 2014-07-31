package mariculture.core.tile;

import java.util.ArrayList;

import mariculture.api.core.Environment.Temperature;
import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter;
import mariculture.core.config.Machines.MachineSettings;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.lib.MachineMultiMeta;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.MetalRates;
import mariculture.core.network.PacketHandler;
import mariculture.core.tile.base.TileMultiMachineTank;
import mariculture.core.util.IHasNotification;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileCrucible extends TileMultiMachineTank implements IHasNotification {
    private static final int MAX_TEMP = 25000;
    private int temp;
    private boolean canFuel;
    private int cooling;
    private double melting_modifier = 1.0D;

    public TileCrucible() {
        max = MachineSpeeds.getCrucibleSpeed();
        inventory = new ItemStack[9];
        needsInit = true;
    }

    private static final int liquid_in = 3;
    private static final int liquid_out = 4;
    private static final int[] in = new int[] { 5, 6 };
    private static final int fuel = 7;
    private static final int out = 8;

    @Override
    public int[] getInputSlots() {
        return in;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 3, 4, 5, 6, 7, 8 };
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        super.setInventorySlotContents(slot, stack);
        canWork = canWork();
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        if (FluidHelper.isFluidOrEmpty(stack)) return slot == liquid_in;
        if (MaricultureHandlers.crucible.getFuelInfo(stack) != null) return slot == fuel;
        return slot == 5 || slot == 6;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot == out || slot == liquid_out;
    }

    @Override
    public EjectSetting getEjectType() {
        return EjectSetting.ITEMNFLUID;
    }

    @Override
    public boolean isNotificationVisible(NotificationType type) {
        return false;
    }

    @Override
    public boolean canWork() {
        return hasTemperature() && hasItem() && rsAllowsWork() && hasRoom();
    }

    private boolean hasTemperature() {
        return temp > 0;
    }

    private boolean hasItem() {
        return inventory[in[0]] != null || inventory[in[1]] != null;
    }

    private boolean hasRoom() {
        return canMelt(0) || canMelt(1);
    }

    private boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage();
    }

    @Override
    public void updateMasterMachine() {
        if (!worldObj.isRemote) {
            heatUp();
            coolDown();

            if (canWork) {
                processed += speed * 50 * melting_modifier;
                if (processed >= max) {
                    processed = 0;
                    if (canWork()) {
                        if (canMelt(0)) {
                            melt(0);
                        }
                        if (canMelt(1)) {
                            melt(1);
                        }
                    }

                    canWork = canWork();
                }
            } else {
                processed = 0;
            }

            if (processed <= 0) {
                processed = 0;
            }

            if (onTick(100) && tank.getFluidAmount() > 0 && RedstoneMode.canWork(this, mode) && EjectSetting.canEject(setting, EjectSetting.FLUID)) {
                helper.ejectFluid(new int[] { 5000, MetalRates.BLOCK, 1000, MetalRates.ORE, MetalRates.INGOT, MetalRates.NUGGET, 1 });
            }
        }
    }

    @Override
    public void updateSlaveMachine() {
        if (onTick(100)) {
            TileCrucible mstr = (TileCrucible) getMaster();
            if (mstr != null && mstr.tank.getFluidAmount() > 0 && RedstoneMode.canWork(this, mstr.mode) && EjectSetting.canEject(mstr.setting, EjectSetting.FLUID)) {
                helper.ejectFluid(new int[] { 5000, MetalRates.BLOCK, 1000, MetalRates.ORE, MetalRates.INGOT, MetalRates.NUGGET, 1 });
            }
        }
    }

    private class FuelHandler {
        private int usedHeat;
        private int tick;
        private FuelInfo info;

        private void read(NBTTagCompound nbt) {
            if (nbt.getBoolean("HasHandler")) {
                info = new FuelInfo();
                info.read(nbt);
            }
        }

        private void write(NBTTagCompound nbt) {
            if (info != null) {
                nbt.setBoolean("HasHandler", true);
                info.write(nbt);
            }
        }

        private void set(FuelInfo info) {
            this.info = info;
            tick = 0;
            usedHeat = 0;
        }

        private int tick(int temp, boolean ethereal) {
            int realUsed = usedHeat * 2000 / MAX_TEMP;
            int realTemp = temp * 2000 / MAX_TEMP;

            tick++;

            if (realUsed < info.maxTempPer && realTemp < info.maxTemp) {
                temp += heat / 3 + 1;
                usedHeat += heat / 3 + 1;
            }

            if (realUsed >= info.maxTempPer && !ethereal) {
                info = null;
                if (canFuel()) {
                    fuelHandler.set(getInfo());
                } else {
                    fuelHandler.set(null);
                }
            } else if (tick >= info.ticksPer) {
                info = null;
                if (canFuel()) {
                    fuelHandler.set(getInfo());
                } else {
                    fuelHandler.set(null);
                }
            }

            return temp;
        }
    }

    private FuelHandler fuelHandler;

    private boolean canFuel() {
        if (fuelHandler.info != null) return false;
        if (!rsAllowsWork()) return false;
        if (MaricultureHandlers.crucible.getFuelInfo(inventory[fuel]) != null) return true;
        if (worldObj.getTileEntity(xCoord, yCoord - 1, zCoord) instanceof IFluidHandler) {
            IFluidHandler handler = (IFluidHandler) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
            FluidTankInfo[] info = handler.getTankInfo(ForgeDirection.UP);
            if (info != null && info[0].fluid != null && info[0].fluid.amount >= 10) return MaricultureHandlers.crucible.getFuelInfo(info[0].fluid) != null;
        }

        return false;
    }

    private void heatUp() {
        if (fuelHandler == null) {
            fuelHandler = new FuelHandler();
        }

        if (onTick(20)) {
            canFuel = canFuel();
        }

        if (canFuel) {
            fuelHandler.set(getInfo());
            canFuel = false;
        }

        if (fuelHandler.info != null) {
            temp = Math.min(MAX_TEMP, fuelHandler.tick(temp, MaricultureHandlers.upgrades.hasUpgrade("ethereal", this)));
            if (temp >= max) {
                temp = max;
            }
        }
    }

    private void coolDown() {
        if (cooling <= 0) {
            cooling = Math.max(1, Temperature.getCoolingSpeed(MaricultureHandlers.environment.getBiomeTemperature(worldObj, xCoord, yCoord, zCoord)));
        }

        if (onTick(20)) {
            temp -= cooling;
            if (temp <= 0) {
                temp = 0;
            }
        }
    }

    public FuelInfo getInfo() {
        FuelInfo info = MaricultureHandlers.crucible.getFuelInfo(inventory[fuel]);
        if (info == null) {
            IFluidHandler handler = (IFluidHandler) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
            FluidTankInfo[] tank = handler.getTankInfo(ForgeDirection.UP);
            if (tank.length > 0 && tank[0] != null && tank[0].fluid != null) {
                info = MaricultureHandlers.crucible.getFuelInfo(tank[0].fluid);
                handler.drain(ForgeDirection.UP, new FluidStack(tank[0].fluid.fluidID, 10), true);
            }
        } else {
            decrStackSize(fuel, 1);
        }

        return info;
    }

    private boolean canMelt(int slot) {
        int other = slot == 0 ? 1 : 0;
        RecipeSmelter recipe = MaricultureHandlers.crucible.getResult(inventory[in[slot]], inventory[in[other]], getTemperatureScaled(2000));
        if (recipe == null) return false;
        int fluidAmount = getFluidAmount(recipe.input, recipe.fluid.amount);
        FluidStack fluid = recipe.fluid.copy();
        fluid.amount = fluidAmount;
        if (tank.fill(fluid, false) < fluid.amount) return false;
        boolean ret = recipe.output == null || setting.canEject(EjectSetting.ITEM);
        if (ret == false) {
            ret = inventory[out] == null || areStacksEqual(inventory[out], recipe.output) && inventory[out].stackSize + recipe.output.stackSize <= inventory[out].getMaxStackSize();
        }

        if (ret == true) {
            int realTemp = temp * 2000 / MAX_TEMP;
            int max_temp = 2000 - recipe.temp;
            melting_modifier = 1.0D + Math.min(realTemp, max_temp) * 3.333333D / 2000;
            return true;
        } else return false;
    }

    private void melt(int slot) {
        int other = slot == 0 ? 1 : 0;
        RecipeSmelter recipe = MaricultureHandlers.crucible.getResult(inventory[in[slot]], inventory[in[other]], getTemperatureScaled(2000));
        if (recipe == null) return;
        decrStackSize(in[slot], recipe.input.stackSize);
        int fluidAmount = getFluidAmount(recipe.input, recipe.fluid.amount);
        FluidStack fluid = recipe.fluid.copy();
        fluid.amount = fluidAmount;
        tank.fill(fluid, true);
        if (recipe.output != null && recipe.chance > 0) if (worldObj.rand.nextInt(recipe.chance) == 0) {
            helper.insertStack(recipe.output.copy(), new int[] { out });
        }

    }

    private int getFluidAmount(ItemStack stack, int amount) {
        if(MachineSettings.ENABLE_PURITY_IN_CRUCIBLE) {
            if (OreDicHelper.isInDictionary(stack)) {
                String name = OreDicHelper.getDictionaryName(stack);
                if (name.startsWith("ore")) {
                    amount += purity * MetalRates.NUGGET * MachineSettings.PURITY;
                }
            }
        }
        
        return amount;
    }

    // Gui Data
    @Override
    public void getGUINetworkData(int id, int value) {
        super.getGUINetworkData(id, value);
        int realID = id - offset;
        switch (realID) {
            case 0:
                temp = value;
            case 1:
                burnHeight = value;
        }
    }

    private int burnHeight = 0;

    public int getBurnTimeRemainingScaled() {
        return burnHeight;
    }

    @Override
    public void sendGUINetworkData(ContainerMariculture container, ICrafting crafting) {
        super.sendGUINetworkData(container, crafting);
        crafting.sendProgressBarUpdate(container, 0 + offset, temp);
        if (fuelHandler.info != null) {
            burnHeight = 11 - fuelHandler.tick * 12 / fuelHandler.info.ticksPer;
            crafting.sendProgressBarUpdate(container, 1 + offset, burnHeight);
        } else {
            crafting.sendProgressBarUpdate(container, 1 + offset, 0);
        }
    }

    public int getTemperatureScaled(int i) {
        return temp * i / MAX_TEMP;
    }

    public String getRealTemperature() {
        return "" + temp * 2000 / MAX_TEMP;
    }

    public int getFluidAmount(String name, int amount) {
        if (name.startsWith("ore")) {
            amount += purity * MetalRates.NUGGET * MachineSettings.PURITY;
        }

        return amount;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        temp = nbt.getInteger("Temperature");
        canFuel = nbt.getBoolean("CanFuel");
        fuelHandler = new FuelHandler();
        fuelHandler.read(nbt);
        cooling = nbt.getInteger("CoolingSpeed");
        melting_modifier = nbt.getDouble("MeltingModifier");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("CoolingSpeed", cooling);
        nbt.setInteger("Temperature", temp);
        nbt.setDouble("MeltingModifier", melting_modifier);
        nbt.setBoolean("CanFuel", canFuel);
        if (fuelHandler != null) {
            fuelHandler.write(nbt);
        }
    }

    // Master stuff
    @Override
    public void onBlockPlaced() {
        onBlockPlaced(xCoord, yCoord, zCoord);
        PacketHandler.updateRender(this);
    }

    private void onBlockPlaced(int x, int y, int z) {
        if (isPart(xCoord, yCoord + 1, zCoord) && !isPart(xCoord, yCoord - 1, zCoord) && !isPart(xCoord, yCoord + 2, zCoord)) {
            MultiPart mstr = new MultiPart(xCoord, yCoord, zCoord);
            ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
            parts.add(setAsSlave(mstr, xCoord, yCoord + 1, zCoord));
            setAsMaster(mstr, parts);
        }

        if (isPart(xCoord, yCoord - 1, zCoord) && !isPart(xCoord, yCoord + 1, zCoord) && !isPart(xCoord, yCoord - 2, zCoord)) {
            MultiPart mstr = new MultiPart(xCoord, yCoord - 1, zCoord);
            ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
            parts.add(setAsSlave(mstr, xCoord, yCoord, zCoord));
            setAsMaster(mstr, parts);
        }
    }

    @Override
    public boolean isPart(int x, int y, int z) {
        return worldObj.getBlock(x, y, z) == getBlockType() && worldObj.getBlockMetadata(x, y, z) == MachineMultiMeta.CRUCIBLE & !isPartnered(x, y, z);
    }
}