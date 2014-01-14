package mariculture.fishery.gui;

import mariculture.core.gui.ContainerMariculture;
import mariculture.fishery.TileFishTank;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFishTank extends ContainerMariculture {
	public int page = 1;
	
	public TileFishTank tile;

	public ContainerFishTank(TileFishTank tile, InventoryPlayer playerInventory) {
		super(tile);

		this.tile = tile;
		
		int i = (3 - 4) * 18;
		int j;
		int k;
				
		tile.loadFish(page);

		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				addSlotToContainer(new SlotFishTank(tile, (k + j * 9), 8 + k * 18, 22 + j * 18));
			}
		}

		bindPlayerInventory(playerInventory, 10);
	}
	
	public int getSizeInventory() {
		return ((IInventory) tile).getSizeInventory();
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return ((IInventory)tile).isUseableByPlayer(player);
	}

	public class SlotFishTank extends Slot {
		TileFishTank tank;
		int slot;

		public SlotFishTank(TileFishTank invent, int slot, int x, int y) {
			super(invent, slot, x, y);

			tank = invent;
			this.slot = slot;
		}
		
		@Override
		public void putStack(ItemStack stack) {
			tile.fish.add(stack);
			tank.setSlot(slot, stack);
	        this.onSlotChanged();
	    }
		
		@Override
		public ItemStack decrStackSize(int amount)  {
			if(tile.getStackInSlot(slot) != null)
				tile.fish.remove(tile.getStackInSlot(slot));
	        return this.inventory.decrStackSize(slot, amount);
	    }
		
		@Override
		public boolean isItemValid(ItemStack stack) {
	        return stack.getItem() instanceof ItemFishy;
	    }
	}
}
