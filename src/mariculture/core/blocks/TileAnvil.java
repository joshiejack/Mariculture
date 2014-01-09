package mariculture.core.blocks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import mariculture.api.core.IAnvilHandler;
import mariculture.core.Core;
import mariculture.core.blocks.base.TileStorage;
import mariculture.core.helpers.DictionaryHelper;
import mariculture.core.items.ItemWorked;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.OresMeta;
import mariculture.core.network.Packet120ItemSync;
import mariculture.core.network.Packets;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraftforge.common.ForgeDirection;

public class TileAnvil extends TileStorage implements ISidedInventory, IAnvilHandler {
	private static final Map recipes = new HashMap();
	
	public TileAnvil() {
		inventory = new ItemStack[1];
	}
	
	@Override
	public void addRecipe(RecipeAnvil recipe) {
		recipes.put(DictionaryHelper.convert(recipe.input), recipe);
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		
		if(!worldObj.isRemote) {
			 Packets.updateTile(this, 64, new Packet120ItemSync(xCoord, yCoord, zCoord, inventory).build());
		}
	}
	
	public boolean canBeWorked(ItemStack stack) {
		if(stack.getItem() instanceof ItemWorked)
			return true;
		RecipeAnvil result = (RecipeAnvil) recipes.get(DictionaryHelper.convert(stack));
		return result != null;
	}
	
	public boolean workItem(ItemStack hammer) {
		if (hammer == null)
			return false;
		ItemStack stack = getStackInSlot(0);
		if(stack == null)
			return false;
		if(!canBeWorked(stack))
			return false;
		if(!(stack.getItem() instanceof ItemWorked)) {
			RecipeAnvil recipe = ((RecipeAnvil) recipes.get(DictionaryHelper.convert(stack)));
			setInventorySlotContents(0, createWorkedItem(recipe.output, recipe.hits));
			return true;
		} else {
			int workedVal = stack.stackTagCompound.getInteger("Worked") + 1;
			stack.stackTagCompound.setInteger("Worked", workedVal);
			if(workedVal >= stack.stackTagCompound.getInteger("Required")) {
				ItemStack result = ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("WorkedItem"));
				setInventorySlotContents(0, result);
			}
			
			return true;
		}
	}
	
	@Override
	public ItemStack createWorkedItem(ItemStack output, int hits) {
		ItemStack worked = new ItemStack(Core.worked);
		worked.setTagCompound(new NBTTagCompound());
		worked.stackTagCompound.setInteger("Worked", 0);
		worked.stackTagCompound.setInteger("Required", hits);
		worked.stackTagCompound.setCompoundTag("WorkedItem", output.writeToNBT(new NBTTagCompound()));
		return worked;
	}

	@Override
	public Packet getDescriptionPacket() {		
		return new Packet120ItemSync(xCoord, yCoord, zCoord, inventory).build();
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return side == ForgeDirection.UP.ordinal() && inventory[0] == null;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		if(stack.getItem() instanceof ItemWorked)
			return side == ForgeDirection.UP.ordinal();
		return true;
	}
}
