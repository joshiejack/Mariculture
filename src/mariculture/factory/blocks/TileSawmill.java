package mariculture.factory.blocks;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.blocks.base.TileMachine;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.ItemTransferHelper;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.PlansMeta;
import mariculture.core.network.Packets;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.IProgressable;
import mariculture.core.util.Rand;
import mariculture.factory.items.ItemPlan;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileSawmill extends TileMachine implements IHasNotification, IProgressable {
	
	//Vars	
	public int selected = 3;
	
	public TileSawmill() {
		MAX = MachineSpeeds.getSawmillSpeed();
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

		return stack.getItem() instanceof ItemBlock || stack.getItem().itemID == Item.feather.itemID;
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
			if(processed >= MAX && canWork()) {
				processed = 0;
				saw();
			}
		} else {
			processed = 0;
		}
	}
	
	@Override
	public boolean canWork() {
		return hasPlanSelected() && allSidesFilled() && RedstoneMode.canWork(this, mode) && outputIsEmptyOrMatches();
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
	
	private boolean outputIsEmptyOrMatches()  {
		if(setting.canEject(EjectSetting.ITEM))
			return true;
		ItemStack result = getResult();
		return inventory[OUT] == null ||  (areStacksEqual(inventory[OUT], result) 
						&& inventory[OUT].stackSize + result.stackSize < inventory[OUT].getMaxStackSize());
	}
	
	//Process the stuffs!
	public void saw() {
		ItemStack result = getResult();
		if(setting.canEject(EjectSetting.ITEM)) {
			new ItemTransferHelper(this).insertStack(result, new int[] { OUT });
		} else {
			if (this.inventory[OUT] == null) {
				this.inventory[OUT] = result.copy();
			} else if (this.inventory[OUT].isItemEqual(result)) {
				inventory[OUT].stackSize += result.stackSize;
			}
		}

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
	public String getProcess() {
		return "sawed";
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
	/*
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
		result.stackTagCompound.setIntArray("BlockSides", new int[] { 0, 1, 2, 3, 4, 5 });
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
			result.stackTagCompound.setIntArray("BlockSides", new int[] { 0, 1, 2, 3, 4, 5 });
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
} */