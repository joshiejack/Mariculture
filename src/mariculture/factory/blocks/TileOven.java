package mariculture.factory.blocks;

import net.minecraft.item.ItemStack;
import mariculture.core.blocks.base.TileMachineTank;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;

public class TileOven extends TileMachineTank {

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EjectSetting getEjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canWork() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateMachine() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getProcess() {
		// TODO Auto-generated method stub
		return null;
	}

}
