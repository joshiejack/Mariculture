package mariculture.factory.gui;

import java.util.ArrayList;

import mariculture.core.helpers.DictionaryHelper;
import mariculture.core.lib.Compatibility;
import mariculture.core.util.ContainerInteger;
import mariculture.factory.blocks.TileDictionary;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ContainerDictionary extends ContainerInteger {
	private TileDictionary tile;

	public ContainerDictionary(TileDictionary tile, InventoryPlayer playerInventory) {
		this.tile = tile;

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new SlotDictionary(tile, i, 8 + (i * 18), 18));
		}

		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 3; i++) {
				addSlotToContainer(new Slot(tile, 9 + (i + (j * 3)), 12 + (i * 18), 42 + (j * 18)));
			}
		}
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 3; i++) {
				addSlotToContainer(new Slot(tile, 15 + (i + (j * 3)), 112 + (i * 18), 42 + (j * 18)));
			}
		}

		bindPlayerInventory(playerInventory);
	}

	private void bindPlayerInventory(InventoryPlayer playerInventory) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < crafters.size(); i++) {
			tile.sendGUINetworkData(this, (EntityPlayer) crafters.get(i));
		}

	}

	@Override
	public void updateProgressBar(int par1, int par2) {
		tile.getGUINetworkData(par1, par2);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tile.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		ItemStack stack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			stack = itemstack1.copy();

			if (par2 < 21) {
				if (!this.mergeItemStack(itemstack1, 21, 57, true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 9, 15, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == stack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return stack;
	}
	
	//Gets the Ore position
	private int getOrePos(ItemStack input) {
		int id = 0;
    	String name = DictionaryHelper.getDictionaryName(input);
    	ArrayList<ItemStack> ores = OreDictionary.getOres(name);
    	for(ItemStack item: ores) {
    		if(OreDictionary.itemMatches(item, input, true)) {
    			return id;
    		}
    		
    		id++;
    	}
    	
    	return id;
	}

	@Override
    public ItemStack slotClick(int slotID, int mouseButton, int modifier, EntityPlayer player) {
		Slot slot = (slotID < 0 || slotID > this.inventorySlots.size())? null: (Slot)this.inventorySlots.get(slotID);

		if(slot instanceof SlotDictionary) {
			if (mouseButton > 0) {
	        	slot.putStack(null);
	        } else if (mouseButton == 0) {
	        		ItemStack stack;
	                InventoryPlayer playerInv = player.inventory;
	                slot.onSlotChanged();
	                ItemStack stackSlot = slot.getStack();
	                ItemStack stackHeld = playerInv.getItemStack();
	                if(stackSlot == null && stackHeld != null) {
	                	if(DictionaryHelper.isInDictionary(stackHeld)) {
		                	if(!isBlacklisted(stackHeld)) {
		                		slot.putStack(new ItemStack(stackHeld.itemID, 1, stackHeld.getItemDamage()));
		                	}
	                	}
	                }
	                
	                if(stackSlot != null && stackHeld == null) {
	                	if(DictionaryHelper.isInDictionary(stackSlot)) {
		                	if(!isBlacklisted(stackSlot)) {
			                	String name = DictionaryHelper.getDictionaryName(stackSlot);
			                	int id = getOrePos(stackSlot);
			                	if(OreDictionary.getOres(name).size() > 0) {
			                		id++;
			                		
			                		if(id >= OreDictionary.getOres(name).size()) {
			                			ItemStack check = checkException(stackSlot, name);
			                			stackSlot = (check != null)? check: stackSlot;
			                			
			                			id = 0;
			                		}
			                	}
			                	
			                	stack = OreDictionary.getOres(DictionaryHelper.getDictionaryName(stackSlot)).get(id);
			                	slot.putStack(stack);
		                	}
	                	}
	                }
	        }
			
			return null;
		}

        return super.slotClick(slotID, mouseButton, modifier, player);
    }
	
	//Returns the Exception to the rule if something exists!
	private ItemStack checkException(ItemStack stack, String name) {
		if(isBlacklisted(stack)) {
			return null;
		}
		
		for(int i = 0; i < Compatibility.EXCEPTIONS.length; i++) {
			String[] names = Compatibility.EXCEPTIONS[i].split("\\s*:\\s*");
			if(names[0].equals(name)) {
				return (OreDictionary.getOres(names[1]).size() > 0)? OreDictionary.getOres(names[1]).get(0): null;
			} else if (names[1].equals(name)) {
				return (OreDictionary.getOres(names[0]).size() > 0)? OreDictionary.getOres(names[0]).get(0): null;
			}
		}
		
		return null;
	}
	
	private boolean isBlacklisted(ItemStack stack) {
		if(DictionaryHelper.isInDictionary(stack)) {
			String name = DictionaryHelper.getDictionaryName(stack);
			for (int j = 0; j < Compatibility.BLACKLIST.length; j++) {
				if (Compatibility.BLACKLIST[j].equals(name)) {
					return true;
				}
			}
		}
		
		return false;
	}
	public class SlotDictionary extends Slot {
		private EntityPlayer thePlayer;
		private int field_75228_b;

		public SlotDictionary(IInventory inv, int id, int x, int y) {
			super(inv, id, x, y);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return !isBlacklisted(stack);
		}

		@Override
		public ItemStack decrStackSize(final int par1) {
			if (this.getHasStack()) {
				this.field_75228_b += Math.min(par1, this.getStack().stackSize);
			}

			return super.decrStackSize(par1);
		}

		public int getSlotStackLimit() {
			return 1;
		}
		
		@Override
	    public boolean canTakeStack(EntityPlayer player) {
	        return false;
	    }
	}
}
