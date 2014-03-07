package mariculture.core.gui;

import mariculture.api.core.IUpgradable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cofh.api.energy.IEnergyHandler;

public class ContainerMariculture extends Container {	
	public TileEntity tile;
	
	public ContainerMariculture() {}
	public ContainerMariculture(TileEntity tile) {
		this.tile = tile;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return false;
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventory) {
		bindPlayerInventory(inventory, 0);
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventory, int yOffset) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, (84 + i * 18) + yOffset));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142 + yOffset));
		}
	}
	
	protected void addUpgradeSlots(IUpgradable tile) {
		for (int i = 0; i < 3; i++) {
			addSlotToContainer(new SlotUpgrade((IInventory) tile, i, 179, 14 + (i * 18)));
		}
	}
	
	protected void addPowerSlot(IEnergyHandler tile) {
		addSlotToContainer(new Slot((IInventory) tile, 3, 8, 62));
	}
	
	@Override
    public ItemStack slotClick(int slotID, int mouseButton, int modifier, EntityPlayer player) {
		Slot slot = (slotID < 0 || slotID > this.inventorySlots.size())? null: (Slot)this.inventorySlots.get(slotID);
		return (slot instanceof SlotFake && ((SlotFake)slot).handle(player, mouseButton, slot) == null)? null: super.slotClick(slotID, mouseButton, modifier, player);
    }
}
