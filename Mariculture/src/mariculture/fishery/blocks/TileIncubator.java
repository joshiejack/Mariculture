package mariculture.fishery.blocks;

import java.util.Random;

import mariculture.api.fishery.Fishing;
import mariculture.core.Core;
import mariculture.core.helpers.AverageHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.UtilMeta;
import mariculture.fishery.FishHelper;
import mariculture.fishery.Fishery;
import mariculture.fishery.gui.ContainerIncubator;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileIncubator extends TileEntity implements IInventory, ISidedInventory {

	private final ItemStack[] inventory;

	private int incubatorLength = MachineSpeeds.getIncubatorSpeed();
	private int currentIncubatingTime;
	private int incubatingTime;
	private boolean isIncubating;

	public TileIncubator() {
		inventory = new ItemStack[18];
		incubatingTime = 0;
		currentIncubatingTime = 0;
	}

	public TileIncubator master() {
		if (isBase(xCoord, yCoord, zCoord)) {
			if (isTop(xCoord, yCoord + 1, zCoord) && isTop(xCoord, yCoord + 2, zCoord)) {
				if (!isTop(xCoord, yCoord + 3, zCoord)) {
					return this;
				}
			}
		}

		if (isBase(xCoord, yCoord - 1, zCoord)) {
			if (isTop(xCoord, yCoord, zCoord) && isTop(xCoord, yCoord + 1, zCoord)) {
				if (!isTop(xCoord, yCoord + 2, zCoord)) {
					return (TileIncubator) worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);
				}
			}
		}

		if (isBase(xCoord, yCoord - 2, zCoord)) {
			if (isTop(xCoord, yCoord, zCoord) && isTop(xCoord, yCoord - 1, zCoord)) {
				if (!isTop(xCoord, yCoord + 1, zCoord)) {
					return (TileIncubator) worldObj.getBlockTileEntity(xCoord, yCoord - 2, zCoord);
				}
			}
		}

		return null;
	}

	public boolean isBase(int x, int y, int z) {
		return worldObj.getBlockId(x, y, z) == Core.utilBlocks.blockID
				&& worldObj.getBlockMetadata(x, y, z) == UtilMeta.INCUBATOR_BASE;
	}

	public boolean isTop(int x, int y, int z) {
		return worldObj.getBlockId(x, y, z) == Core.utilBlocks.blockID
				&& worldObj.getBlockMetadata(x, y, z) == UtilMeta.INCUBATOR_TOP;
	}

	@Override
	public int getSizeInventory() {
		return master() != null ? master().inventory.length : 0;
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return master() != null ? master().inventory[slotIndex] : null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (master() != null) {
			master().inventory[slot] = stack;
			if (stack != null && stack.stackSize > getInventoryStackLimit()) {
				stack.stackSize = getInventoryStackLimit();
			}
		}
	}

	public boolean isBuilt() {
		return master() != null;
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int amount) {
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
		ItemStack stack = getStackInSlot(slotIndex);

		if (stack != null) {
			setInventorySlotContents(slotIndex, null);
		}

		return stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	private int nextSlot() {
		for (int i = 0; i < 9; i++) {
			if (inventory[i] != null) {
				return i;
			}
		}

		return -1;
	}

	private int nextSlotRight() {
		for (int i = 9; i < 18; i++) {
			if (inventory[i] == null) {
				return i;
			}
		}

		return -1;
	}

	private void openEgg(int id) {
		Random rand = new Random();

		if (inventory[id].getItem() instanceof ItemFishy) {
			int[] fertility = inventory[id].stackTagCompound.getIntArray(Fishery.fertility.getEggString());
			int[] lifes = inventory[id].stackTagCompound.getIntArray(Fishery.lifespan.getEggString());

			if (inventory[id].getTagCompound().hasKey("SpeciesList")) {
				int birthChance = AverageHelper.getMode(fertility);
				int eggLife = (AverageHelper.getMode(lifes)/20/60) * 10;

				if (inventory[id].getTagCompound().getInteger("currentFertility") == -1) {
					inventory[id].getTagCompound().setInteger("currentFertility", eggLife);
					inventory[id].getTagCompound().setInteger("malesGenerated", 0);
					inventory[id].getTagCompound().setInteger("femalesGenerated", 0);
				}

				inventory[id].getTagCompound().setInteger("currentFertility",
						inventory[id].getTagCompound().getInteger("currentFertility") - 1);

				int chance = rand.nextInt(birthChance);

				if (chance == 0) {
					if (nextSlotRight() != -1) {
						ItemStack fish = Fishing.fishHelper.makeBredFish(inventory[id], rand);
						int dna = Fishery.gender.getDNA(fish);
						inventory[nextSlotRight()] = fish;

						if (dna == FishHelper.MALE) {
							inventory[id].getTagCompound().setInteger("malesGenerated",
									inventory[id].getTagCompound().getInteger("malesGenerated") + 1);
						} else if (dna == FishHelper.FEMALE) {
							inventory[id].getTagCompound().setInteger("femalesGenerated",
									inventory[id].getTagCompound().getInteger("femalesGenerated") + 1);
						}
					}
				}

				if (inventory[id].getTagCompound().getInteger("currentFertility") == 0) {
					ItemStack fish = Fishing.fishHelper.makeBredFish(inventory[id], rand);
					// If no males were generated create one
					if (inventory[id].getTagCompound().getInteger("malesGenerated") <= 0) {
						inventory[nextSlotRight()] = Fishery.gender.addDNA(fish.copy(), FishHelper.MALE);
					}

					// If no females were generated create one
					if (inventory[id].getTagCompound().getInteger("femalesGenerated") <= 0) {
						inventory[nextSlotRight()] = Fishery.gender.addDNA(fish.copy(), FishHelper.FEMALE);
					}

					decrStackSize(id, 1);
				}
			}
		} else if (inventory[id].itemID == Item.egg.itemID) {
			if (rand.nextInt(16) == 0) {
				inventory[nextSlotRight()] = new ItemStack(383, 1, 93);
			}

			if(rand.nextInt(2) == 0) {
				decrStackSize(id, 1);
			}
		} else if(inventory[id].itemID == Block.dragonEgg.blockID) {
			if(rand.nextInt(64000) == 0) {
				inventory[nextSlotRight()] = new ItemStack(Core.craftingItem, 1, CraftingMeta.DRAGON_EGG);
			}
			
			if(rand.nextInt(10) == 0) {
				decrStackSize(id, 1);
			}
		}
	}

	@Override
	public void updateEntity() {
		if (isBuilt() && master() == this) {
			boolean var1 = this.currentIncubatingTime > 0;
			boolean var2 = false;

			if (this.incubatingTime > 0) {
				--this.incubatingTime;
			}

			if (!this.worldObj.isRemote) {
				if (incubatingTime == 0 && isBuilt()) {
					if (nextSlot() > -1 && nextSlotRight() > -1) {
						incubatingTime = incubatorLength;
					}
				}

				if (isIncubating() && nextSlot() > -1 && nextSlotRight() > -1) {
					++currentIncubatingTime;

					if (this.currentIncubatingTime >= incubatorLength) {

						openEgg(nextSlot());

						this.currentIncubatingTime = 0;
						this.incubatingTime = 0;
					}
				}

				else {
					this.currentIncubatingTime = 0;

				}

				if (var1 != this.currentIncubatingTime > 0) {
					var2 = true;
				}
			}

			boolean check = isIncubating;
			isIncubating = isIncubating();
			if (isIncubating != check) {
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			}

			if (var2) {
				this.onInventoryChanged();
			}
		}
	}

	public boolean isIncubating() {
		return incubatingTime > 0;
	}

	@Override
	public void readFromNBT(final NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		final NBTTagList tagList = tagCompound.getTagList("Inventory");

		for (int i = 0; i < tagList.tagCount(); i++) {
			final NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

			final byte slot = tag.getByte("Slot");

			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}

		incubatingTime = tagCompound.getInteger("IncubatingTime");
		currentIncubatingTime = tagCompound.getInteger("CurrentIncubatingTime");
	}

	@Override
	public void writeToNBT(final NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setInteger("IncubatingTime", this.incubatingTime);
		tagCompound.setInteger("CurrentIncubatingTime", this.currentIncubatingTime);

		final NBTTagList itemList = new NBTTagList();

		for (int i = 0; i < inventory.length; i++) {
			final ItemStack stack = inventory[i];

			if (stack != null) {
				final NBTTagCompound tag = new NBTTagCompound();

				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}

		tagCompound.setTag("Inventory", itemList);
	}

	public int getCatchTimeRemainingScaled(final int par1) {
		return (currentIncubatingTime * par1) / incubatorLength;
	}

	public void getGUINetworkData(final int i, final int j) {
		switch (i) {
		case 0:
			incubatingTime = j;
			break;
		case 1:
			currentIncubatingTime = j;
			break;
		}
	}

	public void sendGUINetworkData(final ContainerIncubator incubator, final ICrafting iCrafting) {
		iCrafting.sendProgressBarUpdate(incubator, 0, incubatingTime);
		iCrafting.sendProgressBarUpdate(incubator, 1, currentIncubatingTime);
	}

	@Override
	public String getInvName() {
		return "TileEntityAutofisher";
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
				&& player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	private static final int[] slots = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		if (master() != null) {
			return master().isItemValidForSlot(slot, stack);
		}

		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		if (master() != null) {
			return slot > 8;
		}

		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if (slot < 9) {
			if (stack.hasTagCompound()) {
				if (Fishing.fishHelper.isEgg(stack)) {
					return true;
				}
			}

			if (stack.itemID == Item.egg.itemID) {
				return true;
			}
			
			if(stack.itemID == Block.dragonEgg.blockID) {
				return true;
			}
		}

		return false;
	}
}
