package mariculture.factory.tile;

import mariculture.core.gui.ContainerMariculture;
import mariculture.core.gui.feature.Feature;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.handlers.OreDicHandler;
import mariculture.core.helpers.BlockTransferHelper;
import mariculture.core.helpers.cofh.InventoryHelper;
import mariculture.core.network.Packets;
import mariculture.core.tile.base.TileStorage;
import mariculture.core.util.IEjectable;
import mariculture.core.util.IItemDropBlacklist;
import mariculture.core.util.IMachine;
import mariculture.factory.items.ItemFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileDictionaryItem extends TileStorage implements IItemDropBlacklist, IMachine, ISidedInventory, IEjectable {
	protected BlockTransferHelper helper;
	private EjectSetting setting;

	public TileDictionaryItem() {
		inventory = new ItemStack[29];
		setting = EjectSetting.ITEM;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return(i < 9)? false: true;
	}
	
	public static final int[] filter = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 };
	public static final int[] in = new int[] { 24 };
	public static final int[] out = new int[] { 25, 26, 27, 28 };

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 24, 25, 26, 27, 28 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack stack, int j) {
		return i == 24;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		return i > 24;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		this.inventory[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
        	stack.stackSize = this.getInventoryStackLimit();
        }
        
        if(helper == null) helper = new BlockTransferHelper(this);
        if(canInsertItem(slot, stack, 0) && hasRoom() && stack != null) {
        	int size = stack.stackSize;
	        for(int i = 0; i < size; i ++) {
	        	if(stack != null) swap();
	        }
        }

        this.markDirty();
	}
	
	private boolean hasRoom() {
		if(setting.canEject(EjectSetting.ITEM))
			return true;
		for(int i: in) {
			if(getStackInSlot(i) != null) {
				ItemStack orig = getStackInSlot(i).copy();
				orig.stackSize = 1;
				ItemStack stack = convert(orig).copy();
				stack.stackSize = 1;
				if(InventoryHelper.canAddItemStackToInventory(inventory, stack, out))
					return true;
			}
		}
		
		return false;
	}
	
	private boolean hasItem() {
		for(int i: in) {
			if(getStackInSlot(i) != null)
				return true;
		}
		
		return false;
	}
	
	private void swap() {
		for(int i: in) {
			if(getStackInSlot(i) != null) {
				ItemStack orig = getStackInSlot(i).copy();
				orig.stackSize = 1;
				ItemStack stack = convert(orig).copy();
				stack.stackSize = 1;
				helper.insertStack(stack, out);
				decrStackSize(i, 1);
			}
		}
	}
	
	private ItemStack convert(ItemStack stack) {
		for(int i: filter) {
			if(getStackInSlot(i) != null) {
				ItemStack filtered = getStackInSlot(i);
				if(filtered.getItem() instanceof ItemFilter && filtered.hasTagCompound()) {
					NBTTagList tagList = filtered.stackTagCompound.getTagList("Inventory", 10);
					if (tagList != null) {
						for(int j = 0; j < tagList.tagCount(); j++) {
							NBTTagCompound nbt = (NBTTagCompound) tagList.getCompoundTagAt(j);
							byte byte0 = nbt.getByte("Slot");
							if (byte0 >= 0 && byte0 < ItemFilter.SIZE) {
								ItemStack aStack = ItemStack.loadItemStackFromNBT(nbt);
								if(OreDicHandler.areEqual(aStack, stack)) {
									ItemStack stack2 = aStack.copy();
									if(stack2.hasTagCompound() && stack2.stackTagCompound.hasKey("OreDictionaryDisplay")) {
										stack2.stackTagCompound.removeTag("OreDictionaryDisplay");
										if(stack2.stackTagCompound.hasNoTags()) stack2.setTagCompound(null);
									}
									
									return stack2;
								}
							}
						}
					}
				} else {
					if(OreDicHandler.areEqual(filtered, stack)) {
						ItemStack stack2 = filtered.copy();
						if(stack2.hasTagCompound() && stack2.stackTagCompound.hasKey("OreDictionaryDisplay")) {
							stack2.stackTagCompound.removeTag("OreDictionaryDisplay");
							if(stack2.stackTagCompound.hasNoTags()) stack2.setTagCompound(null);
						}
						
						return stack2;
					}
				}
			}
		}
		
		return stack;
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0) setting = EjectSetting.values()[value];
	}

	@Override
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		Packets.updateGUI(player, container, 0, setting.ordinal());
	}

	@Override
	public boolean doesDrop(int slot) {
		return slot > 23;
	}

	@Override
	public ItemStack[] getInventory() {
		return inventory;
	}
	
	@Override
	public void handleButtonClick(int id) {
		if(id == Feature.EJECT) setEjectSetting(EjectSetting.toggle(getEjectType(), getEjectSetting()));
	}
	
	@Override
	public EjectSetting getEjectType() {
		return EjectSetting.ITEM;
	}
	
	@Override
	public EjectSetting getEjectSetting() {
		return setting != null? setting: EjectSetting.NONE;
	}

	@Override
	public void setEjectSetting(EjectSetting setting) {
		this.setting = setting;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		setting = EjectSetting.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		EjectSetting.writeToNBT(nbt, setting);
	}
}
