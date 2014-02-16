package mariculture.factory.blocks;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.blocks.base.TileMachine;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.PlansMeta;
import mariculture.core.network.Packets;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.IProgressable;
import mariculture.core.util.Rand;
import mariculture.factory.items.ItemPlan;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileSawmill extends TileMachine implements IHasNotification, IProgressable {
	
	//Vars	
	public int selected = 3;
	
	public TileSawmill() {
		max = MachineSpeeds.getSawmillSpeed();
		inventory = new ItemStack[13];
	}

	public static final int[] PLANS = new int[] { 3, 4, 5 };
	public static final int TOP = 6;
	public static final int NORTH = 7;
	public static final int SOUTH = 8;
	public static final int WEST = 9;
	public static final int EAST = 10;
	public static final int BOTTOM = 11;
	public static final int OUT = 12;
	
	//Sided Inventory
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		switch (side) {
		case 0:
			return new int[] { BOTTOM, OUT };
		case 1:
			return new int[] { TOP, OUT };
		case 2:
			return new int[] { NORTH, OUT };
		case 3:
			return new int[] { SOUTH, OUT };
		case 4:
			return new int[] { WEST, OUT };
		case 5:
			return new int[] { EAST, OUT };
		}

		return null;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		if(slot == OUT || slot < TOP)
			return false;
		if (stack.getItem() instanceof BlockItemCustom || stack.getItem() instanceof BlockItemCustomSlabBase) {
			return false;
		}

		return stack.getItem() instanceof ItemBlock || stack.getItem() == Items.feather;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == OUT;
	}

	@Override
	public EjectSetting getEjectType() {
		return EjectSetting.ITEM;
	}

	@Override
	public void updateMachine() {
		if(canWork) {
			processed+=speed;
			if(processed >= max && canWork()) {
				processed = 0;
				saw();
			}
		} else {
			processed = 0;
		}
	}
	
	@Override
	public boolean canWork() {
		return hasPlanSelected() && allSidesFilled() && RedstoneMode.canWork(this, mode) && outputHasRoom();
	}
	
	//Boolean checkers
	private boolean hasPlanSelected() {
		return inventory[selected] != null && inventory[selected].getItem() instanceof ItemPlan;
	}
	
	private boolean allSidesFilled() {
		return inventory[TOP] != null && inventory[BOTTOM] != null && inventory[NORTH] != null &&
				inventory[SOUTH] != null && inventory[EAST] != null && inventory[WEST] != null;
	}
	
	private boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
		if(!stack1.isItemEqual(stack2))
			return false;
		if(stack1.hasTagCompound() && stack2.hasTagCompound())
			return PlansMeta.isSame(stack1, stack2);
		return stack1.stackTagCompound == null && stack2.stackTagCompound == null;
	}
	
	private boolean outputHasRoom()  {
		if(setting.canEject(EjectSetting.ITEM))
			return true;
		ItemStack result = getResult();
		return inventory[OUT] == null ||  (areStacksEqual(inventory[OUT], result) 
						&& inventory[OUT].stackSize + result.stackSize < inventory[OUT].getMaxStackSize());
	}
	
	//Process the stuffs!
	public void saw() {
		ItemStack result = getResult();
		helper.insertStack(result, new int[] { OUT });

		for (int i = TOP; i < TOP + 6; i++) {
			--this.inventory[i].stackSize;

			if (this.inventory[i].stackSize == 0) {
				Item var2 = this.inventory[i].getItem().getContainerItem();
				this.inventory[i] = var2 == null ? null : new ItemStack(var2);
			}
		}
		
		this.inventory[selected].attemptDamageItem(1, Rand.rand);
		if (this.inventory[selected].getItemDamage() > this.inventory[selected].getMaxDamage()) {
			this.inventory[selected] = null;
		}
		
		canWork = canWork();
	}
	
	public ItemStack getResult() {
		ItemStack stack = PlansMeta.getBlockStack(PlansMeta.getType(inventory[selected]));
		stack.setTagCompound(new NBTTagCompound());
		
		int[] ids = new int[] { getID(BOTTOM), getID(TOP), getID(NORTH), getID(SOUTH), getID(WEST), getID(EAST) };
		int[] metas = new int[] { getMeta(BOTTOM), getMeta(TOP), getMeta(NORTH), getMeta(SOUTH), getMeta(WEST),	getMeta(EAST) };

		String name = BlockHelper.getName(inventory[TOP]) + " - " + stack.getDisplayName();

		stack.stackTagCompound.setIntArray("BlockIDs", ids);
		stack.stackTagCompound.setIntArray("BlockMetas", metas);
		stack.stackTagCompound.setIntArray("BlockSides", new int[] { 0, 1, 2, 3, 4, 5 });
		stack.stackTagCompound.setString("Name", name);
		stack.stackSize = ((ItemPlan) inventory[selected].getItem()).getStackSize(inventory[selected]);
		if(MaricultureHandlers.upgrades.hasUpgrade("ethereal", this))
			stack.stackSize*=2;
		return stack;
	}
	
	private int getID(int slot) {
		if (inventory[slot].getItem() == Items.feather.itemID) {
			return Core.airBlocks.blockID;
		}

		return inventory[slot].itemID;
	}

	private int getMeta(int slot) {
		if (inventory[slot].itemID == Items.feather.itemID) {
			return AirMeta.FAKE_AIR;
		}

		return inventory[slot].getItemDamage();
	}

//Gui Stuff
	@Override
	public void getGUINetworkData(int id, int value) {
		super.getGUINetworkData(id, value);
		if(id - offset == 0)
			selected = value;
	}
	
	@Override
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		super.sendGUINetworkData(container, player);
		Packets.updateGUI(player, container, 0 + offset, selected);
	}

	@Override
	public boolean isNotificationVisible(NotificationType type) {
		switch(type) {
			case NO_PLAN:
				return !hasPlanSelected();
			case MISSING_SIDE:
				return !allSidesFilled();
			default:
				return false;
		}
	}
	
	//Read/Write
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		selected = nbt.getInteger("PlanSelected");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("PlanSelected", selected);
	}
}
