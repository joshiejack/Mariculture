package mariculture.factory.tile;

import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.util.Fluids;
import mariculture.core.util.Rand;
import mariculture.factory.items.ItemRotor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileTurbineWater extends TileTurbineBase {
    @Override
    public int getTankCapacity() {
        return FluidContainerRegistry.BUCKET_VOLUME * 4 + storage * FluidContainerRegistry.BUCKET_VOLUME * 2;
    }

    @Override
    public int getRFCapacity() {
        return 20000 + rf;
    }

    @Override
    public int getEnergyGenerated() {
        return (int) (5 + (speed * 4.625));
    }

    @Override
    public int getEnergyTransferMax() {
        return 75;
    }

    @Override
    public boolean canOperate() {
        if (inventory[6] == null) return false;
        if (!RedstoneMode.canWork(this, mode)) return false;
        ItemStack rotor = inventory[6];
        if (rotor.getItem() instanceof ItemRotor) return ((ItemRotor) inventory[6].getItem()).isTier(2);

        return false;
    }

    @Override
    public void addPower() {
        FluidStack fluid = tank.getFluid();
        if (fluid != null && energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored()) {
            String name = fluid.getFluid().getName();
            if (name.equals(Fluids.hp_water)) {
                if (onTick(200)) if (inventory[6].attemptDamageItem(1, Rand.rand)) {
                    inventory[6] = null;
                    return;
                }

                isCreatingPower = true;
                if (Rand.rand.nextInt(purity * 10 >= 1 ? purity * 10 : 1) < 1) {
                    tank.drain(speed, true);
                }
                energyStorage.modifyEnergyStored(getEnergyGenerated());
            } else {
                isCreatingPower = false;
            }
        } else {
            isCreatingPower = false;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 3, 4, 6 };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return slot == 3 && FluidHelper.isFluidOrEmpty(stack) || slot == 6 && stack.getItem() instanceof ItemRotor;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
        return slot == 4;
    }
}