package mariculture.factory.blocks;

import mariculture.core.blocks.base.TileStorage;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.network.Packets;
import mariculture.core.util.IHasGUI;
import mariculture.core.util.IMachine;
import mariculture.fishery.Fishery;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileFishSorter extends TileStorage implements ISidedInventory, IHasGUI, IMachine {
	public int default_side = 0;
	public int side = -1;
	public int[] sides= new int[23];
	
	public TileFishSorter() {
		this.inventory = new ItemStack[23];
	}
	
	public int getDefault() {
		return this.default_side * 18;
	}
	
	public int getSide(int i) {
		return (this.sides[i]) * 18;
	}
	
	public void increaseDFT() {
		int cur = default_side + 1;
		if(cur > 5) {
			cur = 0;
		}
		
		this.default_side = cur;
	}
	
	public void increaseSide(int slot) {
		if(sides == null || sides.length < 22)
			return;
		
		int cur = sides[slot] + 1;
		if(cur > 5) {
			cur = 0;
		}
		
		this.sides[slot] = cur;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if(slot == 21) {
			this.side = this.getSide(stack);
		}
		
		this.inventory[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
        	stack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
	}
	
	private int getSide(ItemStack stack) {
		if(stack != null && stack.getItem() instanceof ItemFishy) {
			for(int i = 0; i < 21; i++) {
				if(inventory[i] != null) {
					if(inventory[i].getItem() instanceof ItemFishy) {
						if(Fishery.species.getDNA(stack).equals(Fishery.species.getDNA(inventory[i])))
							return sides[i];
					}
					
					if(ItemStack.areItemStacksEqual(inventory[i], stack)) {
						return sides[i];
					}
				}
			}
		}
		
		return -1;
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 21 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return slot == 21;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		return slot == 21 && (side == this.side || (side == this.default_side && this.side == -1));
	}

	@Override
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		for(int i = 0; i < 21; i++) {
			Packets.updateGUI(player, container, i, this.sides[i]);
		}
		
		Packets.updateGUI(player, container, 21, this.side);
		Packets.updateGUI(player, container, 22, this.default_side);
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 21) {
			this.side = value;
		} else if (id == 22) {
			this.default_side = value;
		} else {
			this.sides[id] = value;
		} 
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		this.default_side = tagCompound.getInteger("DefaultSide");
		this.side = tagCompound.getInteger("MainSide");
		this.sides = (tagCompound.getIntArray("AllSides").length == 22)? tagCompound.getIntArray("AllSides"): new int[22];
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setInteger("DefaultSide", this.default_side);
		tagCompound.setInteger("MainSide", this.side);
		tagCompound.setIntArray("AllSides", this.sides);
	}
	
	@Override
	public ItemStack[] getInventory() {
		return inventory;
	}
}

