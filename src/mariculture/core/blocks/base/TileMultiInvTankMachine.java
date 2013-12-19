package mariculture.core.blocks.base;

import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.network.Packets;
import mariculture.core.util.IMachine;
import mariculture.factory.blocks.Tank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;

public abstract class TileMultiInvTankMachine extends TileMultiInvTank implements IUpgradable, IMachine {
	private int machineTick = 0;
	protected int purity = 0;
	protected int heat = 0;
	
	public TileMultiInvTankMachine() {
		this.tank = new Tank(getTankCapacity(0));
	}
	
	public boolean onTick(int i) {
		return machineTick % i == 0;
	}
	
	@Override
	public ItemStack[] getUpgrades() {
		return null;
	}
	
	@Override
	public void updateUpgrades() {
		tank.setCapacity(getTankCapacity(MaricultureHandlers.upgrades.getData("storage", this)));
		purity = MaricultureHandlers.upgrades.getData("purity", this);
		heat = MaricultureHandlers.upgrades.getData("temp", this);
	}
	
	public int getTankCapacity(int storage) {
		int tankRate = FluidContainerRegistry.BUCKET_VOLUME;
		return (tankRate * 20) + (storage * tankRate);
	}

	public void updateEntity() {
		super.updateEntity();
		
		machineTick++;
		if(machineTick %20 == 0) {
			updateUpgrades();
		}
	}
	
	public void getGUINetworkData(int i, int j) {
		switch (i) {
		case 0:
			tank.setFluidID(j);;
			break;
		case 1:
			tank.setFluidAmount(j);
			break;
		case 2:
			tank.setCapacity(j);;
			break;
		}
	}
	
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		Packets.updateGUI(player, container, 0, tank.getFluidID());
		Packets.updateGUI(player, container, 1, tank.getFluidAmount());
		Packets.updateGUI(player, container, 2, tank.getCapacity());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		purity = tagCompound.getInteger("Purity");
		heat = tagCompound.getInteger("Heat");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("Purity", purity);
		tagCompound.setInteger("Heat", heat);
	}
}
