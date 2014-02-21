package mariculture.factory.blocks;

import mariculture.core.blocks.base.TileStorage;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockTransferHelper;
import mariculture.core.helpers.OreDicHelper;
import mariculture.core.helpers.cofh.InventoryHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.network.old.Packets;
import mariculture.core.util.IEjectable;
import mariculture.core.util.IItemDropBlacklist;
import mariculture.core.util.IMachine;
import mariculture.core.util.IProgressable;
import mariculture.core.util.IRedstoneControlled;
import mariculture.factory.items.ItemFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileDictionary extends TileStorage implements IItemDropBlacklist, IMachine, ISidedInventory, IRedstoneControlled, IEjectable, IProgressable {
	protected BlockTransferHelper helper;
	private EjectSetting setting;
	private RedstoneMode mode;
	private boolean canWork;
	private int machineTick = 0;
	private int processed = 0;
	private int max;

	public TileDictionary() {
		inventory = new ItemStack[21];
		max = MachineSpeeds.getDictionarySpeed();
		mode = RedstoneMode.LOW;
		setting = EjectSetting.ITEM;
	}
	
	public boolean onTick(int i) {
		return machineTick % i == 0;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return(i < 9)? false: true;
	}
	
	public static final int[] filter = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	public static final int[] in = new int[] { 9, 10, 11, 12, 13, 14 };
	public static final int[] out = new int[] { 15, 16, 17, 18, 19, 20 };

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return i > 8 && i < 15 && j < 2;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i > 14 && j > 1;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}

	@Override
	public void updateEntity() {	
		if(helper == null)
			helper = new BlockTransferHelper(this);
		
		if(!worldObj.isRemote) {
			machineTick++;
			
			if(onTick(Extra.CAN_WORK_TICK)) {
				canWork = canWork();
			}
			
			if(canWork) {
				processed++;
				if(processed >= max) {
					processed = 0;
					swap();
				}
			} else {
				processed = 0;
			}
		}
	}
	
	private boolean canWork() {
		return RedstoneMode.canWork(this, mode) && hasItem() && hasRoom();
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
		
		canWork = canWork();
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
								if(OreDicHelper.areEqual(aStack, stack)) {
									return aStack;
								}
							}
						}
					}
				} else {
					if(OreDicHelper.areEqual(filtered, stack)) {
						return filtered;
					}
				}
			}
		}
		
		return stack;
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		switch (id) {
		case 0:
			mode = RedstoneMode.values()[value];
			break;
		case 1:
			setting = EjectSetting.values()[value];
			break;
		case 2:
			processed = value;
			break;
		}
	}

	@Override
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		Packets.updateGUI(player, container, 0, mode.ordinal());
		Packets.updateGUI(player, container, 1, setting.ordinal());
		Packets.updateGUI(player, container, 2, processed);
	}

	@Override
	public boolean doesDrop(int slot) {
		return slot > 8;
	}

	@Override
	public ItemStack[] getInventory() {
		return inventory;
	}
	
	@Override
	public RedstoneMode getRSMode() {
		return mode != null? mode: RedstoneMode.DISABLED;
	}
	
	@Override
	public void setRSMode(RedstoneMode mode) {
		this.mode = mode;
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
	public int getProgressScaled(int i) {
		return (processed * i) / max;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		setting = EjectSetting.readFromNBT(nbt);
		mode = RedstoneMode.readFromNBT(nbt);
		processed = nbt.getInteger("Processed");
		canWork = nbt.getBoolean("CanWork");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		EjectSetting.writeToNBT(nbt, setting);
		RedstoneMode.writeToNBT(nbt, mode);
		nbt.setInteger("Processed", processed);
		nbt.setBoolean("CanWork", canWork);
	}
}
