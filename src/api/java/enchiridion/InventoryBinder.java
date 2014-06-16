package enchiridion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;

public class InventoryBinder implements IInventory {
	private World world;
	private EntityPlayer player;
	private ItemStack binder;
	private ItemStack[] inventory;
	public InventoryBinder(EntityPlayer player) {
		this.player = player;
		this.world = player.worldObj;
		this.binder = player.getCurrentEquippedItem();
		if(this.inventory == null) {
			this.inventory = load(21);
		}
	}
	
	public InventoryBinder(World world, ItemStack stack) {
		this.world = world;
		this.binder = stack;
		if(this.inventory == null) {
			this.inventory = load(21);
		}
	}
	
	public String getName() {
		return "Book Binder";
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
				markDirty();
				return itemstack;
			}
			ItemStack itemstack1 = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0) {
				inventory[i] = null;
			}
			markDirty();
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

		markDirty();
	}

	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal(binder.getUnlocalizedName());
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {		
		save();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openInventory() {
		//onInventoryChanged();
	}

	@Override
	public void closeInventory() {
		//onInventoryChanged();
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return getStackInSlot(var1);
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}
	
	public void getGUINetworkData(int i, int j) {
		return;
	}

	public void sendGUINetworkData(ContainerBinder container, ICrafting iCrafting) {
		return;
	}
	
	public ItemStack[] load(int size)  {
		inventory = new ItemStack[21];
		if(!world.isRemote) {
			ItemStack binder = player != null? player.getCurrentEquippedItem(): this.binder;
			if(binder != null && binder.getItem() instanceof ItemEnchiridion && binder.getItemDamage() == ItemEnchiridion.BINDER) {
				NBTTagCompound loader = binder.hasTagCompound() ? binder.stackTagCompound: new NBTTagCompound();
				NBTTagList nbttaglist = loader.getTagList("Inventory", 10);

				if (nbttaglist != null) {
					ItemStack[] inventory = new ItemStack[size];
					for (int i = 0; i < nbttaglist.tagCount(); i++) {
						NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
						byte byte0 = nbttagcompound1.getByte("Slot");
						if (byte0 >= 0 && byte0 < inventory.length) {
							inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
						}
					}

					return inventory;
				}
				
				return new ItemStack[size];
			}
		}
		
		return new ItemStack[size];
	}
	
	public void save() {
		if (!world.isRemote) {
			ItemStack binder = player != null? player.getCurrentEquippedItem(): this.binder;
			if(binder != null && binder.getItem() instanceof ItemEnchiridion && binder.getItemDamage() == ItemEnchiridion.BINDER) {
				try {
					NBTTagList nbttaglist = new NBTTagList();
					for (int i = 0; i < inventory.length; i++) {
						if (inventory[i] != null) {
							NBTTagCompound nbttagcompound1 = new NBTTagCompound();
							nbttagcompound1.setByte("Slot", (byte) i);
							inventory[i].writeToNBT(nbttagcompound1);
							nbttaglist.appendTag(nbttagcompound1);
						}
					}
					if (!binder.hasTagCompound()) {
						binder.setTagCompound(new NBTTagCompound());
					}
					
					binder.stackTagCompound.setTag("Inventory", nbttaglist);

				} catch (Exception e) {
					BookLogHandler.log(Level.ERROR, "Enchiridion had an issue with saving the book binders contents");
				}
			}
		}
	}
}