package mariculture.core.gui;

import mariculture.core.util.IMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;

public class ContainerMachine extends ContainerMariculture {
	protected IMachine tile;

	public ContainerMachine(IMachine machine) {
		super((TileEntity) machine);
		this.tile = machine;
	}
	
	public int getSizeInventory() {
		return ((IInventory) tile).getSizeInventory();
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return ((IInventory)tile).isUseableByPlayer(player);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++) {
			tile.sendGUINetworkData(this, (EntityPlayer) crafters.get(i));
		}
	}

	@Override
	public void updateProgressBar(int par1, int par2) {
		tile.getGUINetworkData(par1, par2);
	}
}
