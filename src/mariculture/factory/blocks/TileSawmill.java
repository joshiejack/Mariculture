package mariculture.factory.blocks;

import java.util.Random;

import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.PlansMeta;
import mariculture.core.util.PacketIntegerUpdate;
import mariculture.factory.gui.ContainerSawmill;
import mariculture.factory.items.ItemPlan;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileSawmill extends TileEntity implements IInventory, ISidedInventory, IUpgradable {
	private ItemStack[] inventory = new ItemStack[11];

	public static int TOP = 5;
	public static int NORTH = 6;
	public static int SOUTH = 8;
	public static int WEST = 9;
	public static int EAST = 7;
	public static int BOTTOM = 10;

	private int furnaceCookTime = 0;
	private int tick = 0;
	private int speed = MachineSpeeds.getSawmillSpeed();
	private double bonus = 1D;
	private int chance = 1000;
	Random rand = new Random();

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			tick++;

			if (tick > 20) {
				tick = 0;
				updateUpgrades();
			}

		}

		boolean updated = false;

		if (!this.worldObj.isRemote) {
			if (this.canSmelt()) {
				this.furnaceCookTime = this.furnaceCookTime + 1;

				if (this.furnaceCookTime >= speed) {
					this.furnaceCookTime = 0;
					this.smeltItem();
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

	private void updateUpgrades() {
		int storageCount = MaricultureHandlers.upgrades.getData("storage", this);
		int purityCount = MaricultureHandlers.upgrades.getData("purity", this);
		int heatAmount = MaricultureHandlers.upgrades.getData("temp", this);

		int chance = (256 - (storageCount * 16));
		this.chance = (chance >= 1) ? chance : 1;
		double bonus = 1D + (0.25D * purityCount);
		if (purityCount < 0) {
			bonus = 1D - (0.25D * purityCount);
		}
		this.bonus = (bonus > 0D) ? bonus : 0.1D;

		if (heatAmount > 0) {
			this.speed = MachineSpeeds.getSawmillSpeed() / heatAmount;
		} else {
			this.speed = MachineSpeeds.getSawmillSpeed();
		}
	}

	public int getProcessedScaled(int par1) {
		return (furnaceCookTime * par1) / speed;
	}

	private boolean canSmelt() {
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
		result.stackSize = (int) ((result.stackSize * this.bonus >= 1) ? result.stackSize * this.bonus : 1);

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

	private void smeltItem() {
		if (this.canSmelt()) {
			ItemStack result = PlansMeta.getBlockStack(PlansMeta.getType(inventory[0]));
			result.setTagCompound(new NBTTagCompound());

			int[] ids = new int[] { getID(BOTTOM), getID(TOP), getID(NORTH), getID(SOUTH), getID(WEST), getID(EAST) };
			int[] metas = new int[] { getMeta(BOTTOM), getMeta(TOP), getMeta(NORTH), getMeta(SOUTH), getMeta(WEST),	getMeta(EAST) };

			String name = inventory[TOP].getDisplayName() + " - " + result.getDisplayName();

			result.stackTagCompound.setIntArray("BlockIDs", ids);
			result.stackTagCompound.setIntArray("BlockMetas", metas);
			result.stackTagCompound.setString("Name", name);

			result.stackSize = ((ItemPlan) inventory[0].getItem()).getStackSize(inventory[0]);
			result.stackSize = (int) ((result.stackSize * this.bonus >= 1) ? result.stackSize * this.bonus : 1);

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

			// Take a chance to damage the plans
			if (rand.nextInt(this.chance) != 0) {
				this.inventory[0].attemptDamageItem(1, new Random());
				if (this.inventory[0].getItemDamage() > this.inventory[0].getMaxDamage()) {
					this.inventory[0] = null;
				}
			}
		}
	}

	public void getGUINetworkData(final int i, final int j) {
		switch (i) {
		case 1:
			furnaceCookTime = j;
			break;
		case 2:
			speed = j;
			break;
		}
	}

	public void sendGUINetworkData(final ContainerSawmill containerSawmill, final EntityPlayer player) {
		PacketIntegerUpdate.send(containerSawmill, 1, this.furnaceCookTime, player);
		PacketIntegerUpdate.send(containerSawmill, 2, this.speed, player);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		final NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		this.inventory = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			final NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			final byte var5 = var4.getByte("Slot");

			if (var5 >= 0 && var5 < this.inventory.length) {
				this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}

		this.furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
		this.speed = par1NBTTagCompound.getShort("Speed");
		this.chance = par1NBTTagCompound.getShort("Chance");
		this.bonus = par1NBTTagCompound.getDouble("Bonus");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("CookTime", (short) this.furnaceCookTime);
		par1NBTTagCompound.setShort("Speed", (short) this.speed);
		par1NBTTagCompound.setShort("Chance", (short) this.chance);
		par1NBTTagCompound.setDouble("Bonus", this.bonus);
		final NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.inventory.length; ++var3) {
			if (this.inventory[var3] != null) {
				final NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				this.inventory[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		par1NBTTagCompound.setTag("Items", var2);
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(final int slotIndex) {
		return inventory[slotIndex];
	}

	@Override
	public ItemStack decrStackSize(final int slotIndex, final int amount) {
		ItemStack stack = getStackInSlot(slotIndex);

		if (stack != null) {
			if (stack.stackSize <= amount) {
				setInventorySlotContents(slotIndex, null);
			}

			else {
				stack = stack.splitStack(amount);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slotIndex, null);
				}
			}
		}

		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int slotIndex) {
		final ItemStack stack = getStackInSlot(slotIndex);

		if (stack != null) {
			setInventorySlotContents(slotIndex, null);
		}

		return stack;
	}

	@Override
	public void setInventorySlotContents(final int slot, final ItemStack stack) {
		inventory[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "TileEntitySettler";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
				&& player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
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

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

}