package mariculture.factory.blocks;

import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.Extra;
import mariculture.core.network.Packets;
import mariculture.factory.blocks.TileTurbineBase.EnergyStage;
import mariculture.factory.items.ItemRotor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileTurbineHand extends TileTurbineBase {		
	public int cooldown = 0;
    public int produced = 0;

	@Override
	public int getTankCapacity() {
		return 0;
	}
	
	@Override
	public int getRFCapacity() {
		return 1600;
	}

	@Override
	public int getEnergyGenerated() {
		return 20;
	}

	@Override
	public int getEnergyTransferMax() {
		return 10;
	}

	@Override
	public boolean canOperate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		updateTurbine();
	}

	@Override
	public void addPower() {
		if(cooldown > 0) {
			cooldown--;
            produced++;
		}
		
		if(isCreatingPower && cooldown == 0) {
			isCreatingPower = false;
		}

        if(!isCreatingPower) {
            produced-=40;
        }
	}

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] {} ;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
        return false;
    }
}