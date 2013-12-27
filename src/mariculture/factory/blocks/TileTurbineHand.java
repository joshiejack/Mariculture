package mariculture.factory.blocks;

import mariculture.core.lib.Extra;
import mariculture.core.network.Packets;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileTurbineHand extends TileTurbineBase {	
	public int charge;
	public int beingCharged;
	
	public TileTurbineHand() {
		this.inventory = new ItemStack[1];
	}
	
	@Override
	public int getTankCapacity(int count) {
		return 0;
	}
	
	@Override
	public int maxEnergyStored() {
		return 1600;
	}

	@Override
	public int maxEnergyExtracted() {
		return 5;
	}
	
	@Override
	public void updateUpgrades() {		
		if(beingCharged > 0)
			beingCharged--;
	}
	
	@Override
	public boolean isPowered() {
		return beingCharged <= 0;
	}
	
	@Override
	public boolean hasFuel() {
		return true;
	}
	
	@Override
	public void updateMachine() {		
		this.isActive = this.isActive();
		
		if(!worldObj.isRemote) {
			if(isActive()) {
				transferPower();
			}
			
			if (onTick(Extra.REFRESH_CLIENT_RATE)) {
				Packets.updateTile(this, 32, getDescriptionPacket());
			}
			
			energyStage = computeEnergyStage();
		}
		
		animate();
	}
	
	@Override
	public void generatePower() {
		storage.modifyEnergyStored(20);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.charge = tagCompound.getInteger("WindupCharge");
		storage.readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("WindupCharge", this.charge);
	}
}