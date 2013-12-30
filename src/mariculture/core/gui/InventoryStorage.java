package mariculture.core.gui;

import mariculture.core.items.ItemStorage;
import mariculture.core.util.Rand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class InventoryStorage implements IInventory {
	private ItemStack[] inventory;
	public EntityPlayer player;
	public long seed;

	public InventoryStorage(EntityPlayer player, int size) {
		this.player = player;
		if(this.inventory == null)
			this.inventory = load(player, size);
	}
	
	public ItemStack getHeldItem() {
		return player != null? player.getCurrentEquippedItem(): null;
	}
	
	public String getName() {
		return "Default Storage Item";
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (inventory[i] != null) {
			if (inventory[i].stackSize <= j) {
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				onInventoryChanged();
				return itemstack;
			}
			ItemStack itemstack1 = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0) {
				inventory[i] = null;
			}
			onInventoryChanged();
			return itemstack1;
		} else {
			return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;

		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}

		onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return StatCollector.translateToLocal(this.player.getCurrentEquippedItem().getUnlocalizedName());
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() {		
		seed = Rand.rand.nextLong();
		save(player, inventory);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {
		//onInventoryChanged();
	}

	@Override
	public void closeChest() {
		//onInventoryChanged();
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return getStackInSlot(var1);
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}
	
	public void getGUINetworkData(int i, int j) {
		return;
	}

	public void sendGUINetworkData(ContainerStorage container, ICrafting iCrafting) {
		return;
	}
	
	public ItemStack[] load(EntityPlayer player, int size)  {
		if(!player.worldObj.isRemote) {
			ItemStack stack = player.getCurrentEquippedItem();
			if(stack != null && stack.getItem() instanceof ItemStorage) {
				ItemStorage storage = (ItemStorage) stack.getItem();
				return storage.load(player, stack, size);
			}
		}
		
		return new ItemStack[size];
	}
	
	public void save(EntityPlayer player, ItemStack[] mirrorContents) {
		if (!player.worldObj.isRemote) {
			if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemStorage) {
				ItemStorage storage = (ItemStorage) player.getCurrentEquippedItem().getItem();
				storage.save(player, mirrorContents);
			}
		}
	}
}