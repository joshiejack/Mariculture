package mariculture.factory.items;

import java.util.List;

import mariculture.core.gui.InventoryStorage;
import mariculture.core.items.ItemStorage;
import mariculture.core.lib.Text;
import mariculture.factory.gui.SlotDictionary;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ItemFilter extends ItemStorage {
	public static final int SIZE = 9;
	
	public ItemFilter(int i) {
		super(i, SIZE, "filter");
	}
	
	@Override
	public int getX(ItemStack stack) {
		return 65;
	}
	
	@Override
	public Slot getSlot(InventoryStorage storage, int i) {
		switch(i) {
			case 0: return new SlotDictionary(storage, i, 44, 26);
			case 1: return new SlotDictionary(storage, i, 62, 26);
			case 2: return new SlotDictionary(storage, i, 80, 26);
			case 3: return new SlotDictionary(storage, i, 98, 26);
			case 4: return new SlotDictionary(storage, i, 116, 26);
			case 5: return new SlotDictionary(storage, i, 53, 44);
			case 6: return new SlotDictionary(storage, i, 71, 44);
			case 7: return new SlotDictionary(storage, i, 89, 44);
			case 8: return new SlotDictionary(storage, i, 107, 44);
		}
		
		return new Slot(storage, i, 100, 100);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		if(stack.hasTagCompound()) {
			NBTTagList nbttaglist = stack.stackTagCompound.getTagList("Inventory");
			if (nbttaglist != null) {
				ItemStack[] inventory = new ItemStack[ItemFilter.SIZE];
				for (int i = 0; i < nbttaglist.tagCount(); i++) {
					NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
					byte byte0 = nbttagcompound1.getByte("Slot");
					if (byte0 >= 0 && byte0 < inventory.length) {
						ItemStack item = ItemStack.loadItemStackFromNBT(nbttagcompound1);
						list.add(Text.ORANGE + "+ " + item.getDisplayName());
					}
				}
			}
		}
	}
}
