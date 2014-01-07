package mariculture.factory.blocks;

import java.util.HashMap;

import mariculture.api.fishery.Fishing;
import mariculture.core.blocks.base.TileStorage;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.DictionaryHelper;
import mariculture.core.network.Packets;
import mariculture.core.util.IHasClickableButton;
import mariculture.core.util.IItemDropBlacklist;
import mariculture.core.util.IMachine;
import mariculture.core.util.IRedstoneControlled;
import mariculture.fishery.Fishery;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileFishSorter extends TileStorage implements IItemDropBlacklist, IMachine, ISidedInventory, IRedstoneControlled, IHasClickableButton {

	private int dft_side;
	private HashMap<Integer, Integer> sorting = new HashMap();
	private RedstoneMode mode;
	
	public TileFishSorter() {
		inventory = new ItemStack[22];
		mode = RedstoneMode.LOW;
	}
	
	public static final int input = 21;

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { input };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return RedstoneMode.canWork(this, mode) && slot == input;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		if(!RedstoneMode.canWork(this, mode))
			return false;
		if (slot < 21)
			return false;
		int stored = getSlotForStack(stack);
		if(stored == -1)
			return side == dft_side;
		if(sorting.containsKey(stored))
			return sorting.get(stored) == side;
		return side == dft_side;
	}
	
	public boolean hasSameFishDNA(ItemStack fish1, ItemStack fish2) {
		if(Fishery.species.getDNA(fish1).equals(Fishery.species.getDNA(fish2)) &&
				Fishery.species.getLowerDNA(fish1).equals(Fishery.species.getLowerDNA(fish2))) {
			return true;
		}
		
		if(Fishery.species.getDNA(fish1).equals(Fishery.species.getLowerDNA(fish1)) &&
				Fishery.species.getLowerDNA(fish1).equals(Fishery.species.getDNA(fish2))) {
			return true;
		}
		
		if(Fishing.fishHelper.isEgg(fish1) && Fishing.fishHelper.isEgg(fish2)) {
			return true;
		}
		
		return false;
	}
	
	public int getSlotForStack(ItemStack stack) {
		for(int i = 0; i < input; i++) {
			if(getStackInSlot(i) != null) {
				ItemStack item = getStackInSlot(i);
				if(item.getItem() instanceof ItemFishy && stack.getItem() instanceof ItemFishy) {
					if(hasSameFishDNA(item, stack))
						return i;
				}
				
				if(DictionaryHelper.areEqual(stack, item)) {
					return i;
				}
			}
		}
		
		return -1;
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		switch (id) {
		case 22:
			dft_side = value;
			break;
		case 21:
			mode = RedstoneMode.values()[value];
			break;
		default:
			sorting.put(id, value);
			break;
		}
	}
	
	@Override
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		for(int i = 0; i < input; i++) {
			Packets.updateGUI(player, container, i, (sorting.containsKey(i))? sorting.get(i): 0);
		}
		
		Packets.updateGUI(player, container, 21, mode.ordinal());
		Packets.updateGUI(player, container, 22, dft_side);
	}

	@Override
	public ItemStack[] getInventory() {
		return inventory;
	}

	@Override
	public boolean doesDrop(int slot) {
		return slot == input;
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		mode = RedstoneMode.readFromNBT(nbt);
		for(int i = 0; i < input; i++) {
			sorting.put(i, nbt.getInteger("SideSettingForSlot" + i));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		RedstoneMode.writeToNBT(nbt, mode);
		for(int i = 0; i < input; i ++) {
			int val =  (sorting.containsKey(i))? sorting.get(i): 0;
			nbt.setInteger("SideSettingForSlot" + i, val);
		}
	}
	
	public int getSide(int slot) {
		if(sorting.containsKey(slot)) {
			return sorting.get(slot);
		} else {
			return 0;
		}
	}

	public void swapSide(int slot) {
		if(sorting.containsKey(slot)) {
			int side = sorting.get(slot);
			side = (side + 1 < 6)? side + 1: 0;
			sorting.put(slot, side);
		} else {
			sorting.put(slot, 1);
		}
	}

	public int getDefaultSide() {
		return dft_side;
	}
	
	public static final int DFT_SWITCH = 0;

	@Override
	public void handleButtonClick(int id) {
		if(id == DFT_SWITCH) {
			dft_side = (dft_side + 1 < 6)? dft_side + 1: 0;
		}
	}
}
