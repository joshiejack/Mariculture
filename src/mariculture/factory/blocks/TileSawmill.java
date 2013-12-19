package mariculture.factory.blocks;

import java.util.Random;

import mariculture.core.Core;
import mariculture.core.blocks.base.TileMachine;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.PlansMeta;
import mariculture.core.network.Packets;
import mariculture.core.util.IHasGUI;
import mariculture.factory.items.ItemPlan;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileSawmill extends TileMachine implements ISidedInventory, IHasGUI {
	public static int TOP = 5;
	public static int NORTH = 6;
	public static int SOUTH = 8;
	public static int WEST = 9;
	public static int EAST = 7;
	public static int BOTTOM = 10;

	private final int speed = MachineSpeeds.getSawmillSpeed();
	
	private int furnaceCookTime = 0;
	Random rand = new Random();
	
	public TileSawmill() {
		inventory = new ItemStack[11];
	}

	@Override
	public void updateMachine() {
		boolean updated = false;

		if (!this.worldObj.isRemote) {
			if (this.canSaw()) {
				this.furnaceCookTime = this.furnaceCookTime + heat + 1;

				if (this.furnaceCookTime >= speed) {
					this.furnaceCookTime = 0;
					this.sawItem();
					updated = true;
				}
			} else {
				this.furnaceCookTime = 0;
			}
		}

		if (updated) {
			this.onInventoryChanged();
		}
	}

	public int getProcessedScaled(int par1) {
		return (furnaceCookTime * par1) / speed;
	}

	private boolean canSaw() {
		ItemStack result = null;

		if (inventory[0] == null) {
			return false;
		}

		for (int i = 0; i < 6; i++) {
			if (inventory[i + 5] == null) {
				return false;
			}

			try {
				if (inventory[i + 5].itemID != Item.feather.itemID) {
					if (Block.blocksList[inventory[i + 5].itemID] == null) {
						return false;
					}
				}
			} catch (Exception e) {
				return false;
			}
		}

		if (!(inventory[0].getItem() instanceof ItemPlan)) {
			return false;
		}

		if (inventory[1] == null) {
			return true;
		}

		result = PlansMeta.getBlockStack(PlansMeta.getType(inventory[0]));
		result.setTagCompound(new NBTTagCompound());

		int[] ids = new int[] { inventory[BOTTOM].itemID, inventory[TOP].itemID, inventory[NORTH].itemID,
				inventory[SOUTH].itemID, inventory[WEST].itemID, inventory[EAST].itemID };
		int[] metas = new int[] { inventory[BOTTOM].getItemDamage(), inventory[TOP].getItemDamage(),
				inventory[NORTH].getItemDamage(), inventory[SOUTH].getItemDamage(), inventory[WEST].getItemDamage(),
				inventory[EAST].getItemDamage() };

		String name = BlockHelper.getName(inventory[TOP]) + " - " + result.getDisplayName();

		result.stackTagCompound.setIntArray("BlockIDs", ids);
		result.stackTagCompound.setIntArray("BlockMetas", metas);
		result.stackTagCompound.setString("Name", name);

		result.stackSize = ((ItemPlan) inventory[0].getItem()).getStackSize(inventory[0]);
		double bonus = (purity < 0)? 1D - (0.25D * purity) : 1D + (0.25D * purity);
		bonus = (bonus > 0D) ? bonus : 0.1D;
		
		result.stackSize = (int) ((result.stackSize * bonus >= 1) ? result.stackSize * bonus : 1);

		if (inventory[1].stackSize + result.stackSize > 64) {
			return false;
		}

		if (!inventory[1].isItemEqual(result)) {
			return false;
		}

		if (PlansMeta.isSame(inventory[1], result)) {
			if ((inventory[1].stackSize < getInventoryStackLimit() && inventory[1].stackSize < inventory[1]
					.getMaxStackSize())) {
				return true;
			}
		}

		return false;
	}

	private int getID(int slot) {
		if (inventory[slot].itemID == Item.feather.itemID) {
			return Core.airBlocks.blockID;
		}

		return inventory[slot].itemID;
	}

	private int getMeta(int slot) {
		if (inventory[slot].itemID == Item.feather.itemID) {
			return AirMeta.FAKE_AIR;
		}

		return inventory[slot].getItemDamage();
	}

	private void sawItem() {
		if (this.canSaw()) {
			ItemStack result = PlansMeta.getBlockStack(PlansMeta.getType(inventory[0]));
			result.setTagCompound(new NBTTagCompound());

			int[] ids = new int[] { getID(BOTTOM), getID(TOP), getID(NORTH), getID(SOUTH), getID(WEST), getID(EAST) };
			int[] metas = new int[] { getMeta(BOTTOM), getMeta(TOP), getMeta(NORTH), getMeta(SOUTH), getMeta(WEST),	getMeta(EAST) };

			String name = inventory[TOP].getItem().getItemDisplayName(inventory[TOP]) + " - " + result.getDisplayName();

			result.stackTagCompound.setIntArray("BlockIDs", ids);
			result.stackTagCompound.setIntArray("BlockMetas", metas);
			result.stackTagCompound.setString("Name", name);

			result.stackSize = ((ItemPlan) inventory[0].getItem()).getStackSize(inventory[0]);
			double bonus = (purity < 0)? 1D - (0.25D * purity) : 1D + (0.25D * purity);
			bonus = (bonus > 0D) ? bonus : 0.1D;
			
			result.stackSize = (int) ((result.stackSize * bonus >= 1) ? result.stackSize * bonus : 1);

			if (this.inventory[1] == null) {
				this.inventory[1] = result.copy();
			} else if (this.inventory[1].isItemEqual(result)) {
				inventory[1].stackSize += result.stackSize;
			}

			for (int i = 0; i < 6; i++) {
				--this.inventory[i + 5].stackSize;

				if (this.inventory[i + 5].stackSize == 0) {
					final Item var2 = this.inventory[i + 5].getItem().getContainerItem();
					this.inventory[i + 5] = var2 == null ? null : new ItemStack(var2);
				}
			}

			int chance = (256 - (storage * 16) >= 1)? 256 - (storage * 16): 1;
			// Take a chance to damage the plans
			if (rand.nextInt(chance) != 0) {
				this.inventory[0].attemptDamageItem(1, new Random());
				if (this.inventory[0].getItemDamage() > this.inventory[0].getMaxDamage()) {
					this.inventory[0] = null;
				}
			}
		}
	}

	public void getGUINetworkData(int i, int j) {
		switch (i) {
		case 0:
			furnaceCookTime = j;
			break;
		case 1:
			heat = j;
			break;
		}
	}

	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		Packets.updateGUI(player, container, 0, this.furnaceCookTime);
		Packets.updateGUI(player, container, 1, this.heat);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.furnaceCookTime = nbt.getShort("CookTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setShort("CookTime", (short) this.furnaceCookTime);
	}

	@Override
	public ItemStack[] getUpgrades() {
		return new ItemStack[] { inventory[2], inventory[3], inventory[4] };
	}

	private static final int[] slots_top = new int[] { TOP };
	private static final int[] slots_bottom = new int[] { BOTTOM, 0 };
	private static final int[] slots_north = new int[] { NORTH, 1 };
	private static final int[] slots_east = new int[] { EAST, 1 };
	private static final int[] slots_south = new int[] { SOUTH, 1 };
	private static final int[] slots_west = new int[] { WEST, 1 };

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		switch (side) {
		case 0:
			return slots_bottom;
		case 1:
			return slots_top;
		case 2:
			return slots_north;
		case 3:
			return slots_south;
		case 4:
			return slots_west;
		case 5:
			return slots_east;
		}

		return null;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot == 0) {
			return stack.getItem() instanceof ItemPlan;
		}

		if (stack.getItem() instanceof BlockItemCustom || stack.getItem() instanceof BlockItemCustomSlabBase) {
			return false;
		}

		return stack.getItem() instanceof ItemBlock || stack.getItem().itemID == Item.feather.itemID;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int j) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
		return slot == 1;
	}
}