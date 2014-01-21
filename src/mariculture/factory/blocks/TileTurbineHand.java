package mariculture.factory.blocks;

import mariculture.core.lib.Extra;
import mariculture.core.network.Packets;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileTurbineHand extends TileTurbineBase {	
	public int beingCharged;
	
	@Override
	public int getTankCapacity(int count) {
		return 0;
	}
	
	@Override
	public int getRFCapacity() {
		return 1600;
	}

	@Override
	public int maxEnergyExtracted() {
		return 5;
	}
	
	@Override
	public boolean canWork() {
		return false;
	}
	
	@Override
	public boolean canDrain() {
		return false;
	}

	@Override
	public int getEnergyGenerated() {
		return 20;
	}

	@Override
	public boolean canUseFluid() {
		return true;
	}

	@Override
	public boolean canUseRotor() {
		return true;
	}
}