package mariculture.factory.blocks;

import mariculture.core.lib.Extra;
import mariculture.core.network.Packets;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileTurbineHand extends TileTurbineBase {		
	public int cooldown = 0;

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
	public void updateEntity() {
		updateTurbine();
	}

	@Override
	public boolean canOperate() {
		return true;
	}

	@Override
	public void addPower() {
		if(cooldown > 0) {
			cooldown--;
		}
		
		if(isAnimating && cooldown == 0) {
			isAnimating = false;
		}
	}
}