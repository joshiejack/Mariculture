package mariculture.factory.gui;

import java.util.ArrayList;

import mariculture.core.gui.SlotFake;
import mariculture.core.helpers.DictionaryHelper;
import mariculture.core.lib.Compatibility;
import mariculture.factory.items.ItemFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SlotDictionary extends SlotFake {
	public SlotDictionary(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return DictionaryHelper.isWhitelisted(stack) || stack.getItem() instanceof ItemFilter;
	}

	@Override
	public ItemStack decrStackSize(int par1) {
		if (this.getHasStack()) {
			Math.min(par1, this.getStack().stackSize);
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

	public ItemStack handle(EntityPlayer player, int mouseButton, Slot slot) {
		boolean isFilter = false;

		ItemStack filterCheck = player.inventory.getItemStack();
		if (filterCheck != null) {
			if (filterCheck.getItem() instanceof ItemFilter) {
				isFilter = true;
			}
		}

		if (mouseButton > 0) {
			slot.putStack(null);
		} else if (mouseButton == 0) {
			ItemStack stack;
			InventoryPlayer playerInv = player.inventory;
			slot.onSlotChanged();
			ItemStack stackSlot = slot.getStack();
			ItemStack stackHeld = playerInv.getItemStack();

			if (stackSlot == null && stackHeld != null) {
				if (isFilter || DictionaryHelper.isInDictionary(stackHeld)) {
					if (isFilter || DictionaryHelper.isWhitelisted(stackHeld)) {
						ItemStack copy = stackHeld.copy();
						copy.stackSize = 1;
						slot.putStack(copy);
					}
				}
			}

			if (stackSlot != null && stackHeld == null) {
				if (DictionaryHelper.isInDictionary(stackSlot)) {
					if (DictionaryHelper.isWhitelisted(stackSlot)) {
						String name = DictionaryHelper.getDictionaryName(stackSlot);
						int id = getOrePos(stackSlot);
						if (OreDictionary.getOres(name).size() > 0) {
							id++;

							if (id >= OreDictionary.getOres(name).size()) {
								ItemStack check = checkException(stackSlot, name);
								stackSlot = (check != null) ? check : stackSlot;

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

	// Gets the Ore position
	private int getOrePos(ItemStack input) {
		int id = 0;
		String name = DictionaryHelper.getDictionaryName(input);
		ArrayList<ItemStack> ores = OreDictionary.getOres(name);
		for (ItemStack item : ores) {
			if (OreDictionary.itemMatches(item, input, true)) {
				return id;
			}

			id++;
		}

		return id;
	}

	// Returns the Exception to the rule if something exists!
	private ItemStack checkException(ItemStack stack, String name) {
		if (!DictionaryHelper.isWhitelisted(stack)) {
			return null;
		}

		for (int i = 0; i < Compatibility.EXCEPTIONS.length; i++) {
			String[] names = Compatibility.EXCEPTIONS[i].split("\\s*:\\s*");
			if (names[0].equals(name)) {
				return (OreDictionary.getOres(names[1]).size() > 0) ? OreDictionary.getOres(names[1]).get(0) : null;
			} else if (names[1].equals(name)) {
				return (OreDictionary.getOres(names[0]).size() > 0) ? OreDictionary.getOres(names[0]).get(0) : null;
			}
		}

		return null;
	}
}