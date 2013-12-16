package mariculture.magic.gui;

import java.util.Random;

import mariculture.magic.ItemMirror;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InventoryMirror implements IInventory {
	private ItemStack[] mirrorContents;
	private ItemStack mirror;
	private EntityPlayer player;
	int lvl1;
	int lvl2;
	int lvl3;
	long nameSeed;
	private final Random rand = new Random();
	private final World worldPointer;

	public InventoryMirror(ItemStack mirror, EntityPlayer player, World world) {
		this.worldPointer = world;
		this.mirrorContents = ItemMirror.loadMirrorContents(mirror, 1, player);
		this.mirror = mirror;
		this.player = player;
	}
	
	public String getName() {
		if(mirror != null) {
			return mirror.getDisplayName();
		}
		
		return "";
	}

	@Override
	public int getSizeInventory() {
		return ItemMirror.mirrorSize;
	}

	boolean hasItemForEnchanting() {
		if (mirrorContents[3] != null) {
			if ((mirrorContents[3].isItemEnchantable() && !mirrorContents[3].isItemEnchanted())
					|| (mirrorContents[3].getItem() instanceof ItemJewelry && !mirrorContents[3].isItemEnchanted())) {
				if (mirrorContents[3].hasTagCompound() && mirrorContents[3].stackTagCompound.hasKey("EnchantmentID")) {
					return false;
				}

				loadEnchantments();
				return true;
			}
		}

		return false;
	}

	private int getInventorySlotContainItem(int id, int meta) {
		for (int var2 = 0; var2 < ItemMirror.mirrorSize; ++var2) {
			if (this.getStackInSlot(var2) != null && this.getStackInSlot(var2).itemID == id
					&& this.getStackInSlot(var2).getItemDamage() == meta) {
				return var2;
			}
		}

		return -1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return mirrorContents[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (mirrorContents[i] != null) {
			if (mirrorContents[i].stackSize <= j) {
				final ItemStack itemstack = mirrorContents[i];
				mirrorContents[i] = null;
				onInventoryChanged();
				return itemstack;
			}
			final ItemStack itemstack1 = mirrorContents[i].splitStack(j);
			if (mirrorContents[i].stackSize == 0) {
				mirrorContents[i] = null;
			}
			onInventoryChanged();
			return itemstack1;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		mirrorContents[i] = itemstack;

		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}

		onInventoryChanged();
	}

	public boolean isMagical() {
		if (mirror.hasTagCompound()) {
			if (mirror.stackTagCompound.getBoolean("magic") == true) {
				return true;
			}
		}

		return false;
	}

	private void loadEnchantments() {
		if (this.mirror.hasTagCompound()) {
			// Sending data to the GUI to let it know it needs to update
			if (this.mirror.stackTagCompound.getBoolean("magic") == true) {
				lvl1 = mirror.stackTagCompound.getInteger("charge") - 2;
				lvl2 = mirror.stackTagCompound.getInteger("charge") - 1;
				lvl3 = mirror.stackTagCompound.getInteger("charge");
			}
		}
	}

	public void getGUINetworkData(final int i, final int j) {
		switch (i) {
		case 0:
			lvl1 = j;
			break;
		case 1:
			lvl2 = j;
			break;
		case 2:
			lvl3 = j;
			break;
		}
	}

	public void sendGUINetworkData(ContainerMirror mirror, ICrafting iCrafting) {
		iCrafting.sendProgressBarUpdate(mirror, 0, lvl1);
		iCrafting.sendProgressBarUpdate(mirror, 1, lvl2);
		iCrafting.sendProgressBarUpdate(mirror, 2, lvl3);
	}

	@Override
	public String getInvName() {
		return "Mirror";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void onInventoryChanged() {
		this.nameSeed = this.rand.nextLong();
		ItemMirror.saveMirrorContents(mirror, mirrorContents, player);
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
		onInventoryChanged();
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int var1) {
		return getStackInSlot(var1);
	}

	public int getLevelCap() {
		if (mirror.hasTagCompound()) {
			if (mirror.stackTagCompound.getBoolean("magic") == true) {
				if (mirror.stackTagCompound.getInteger("charge") != 0) {
					return mirror.stackTagCompound.getInteger("charge");
				}
			}
		}
		return 0;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}
}