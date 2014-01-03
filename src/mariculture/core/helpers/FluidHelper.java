package mariculture.core.helpers;

import mariculture.core.Core;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.util.FluidDictionary;
import mariculture.fishery.FishFoodHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

public class FluidHelper {
	public static void process(IInventory invent, int in, int out) {
		ItemStack result = FluidHelper.getFluidResult((IFluidHandler) invent, invent.getStackInSlot(in), invent.getStackInSlot(out));
		if (result != null) {
			invent.decrStackSize(in, 1);
			if(result.itemID != Core.airBlocks.blockID) {
				if (invent.getStackInSlot(out) == null) {
					invent.setInventorySlotContents(out, result.copy());
				} else if (invent.getStackInSlot(out).itemID == result.itemID) {
					ItemStack stack = invent.getStackInSlot(out);
					++stack.stackSize;
					invent.setInventorySlotContents(out, stack);
				}
			}
		}
	}
	
	public static boolean isFluidOrEmpty(ItemStack stack) {
		return isEmpty(stack) || isFilled(stack) || isVoid(stack);
	}
	
	public static boolean isEmpty(ItemStack stack) {
		return FluidContainerRegistry.isEmptyContainer(stack);
	}
	
	public static boolean isFilled(ItemStack stack) {
		return FluidContainerRegistry.isFilledContainer(stack);
	}

	public static boolean isVoid(ItemStack stack) {
		return (stack.getItem().itemID == Core.liquidContainers.itemID && stack.getItemDamage() == FluidContainerMeta.BOTTLE_VOID);
	}

	public static ItemStack getFluidResult(IFluidHandler tile, ItemStack top, ItemStack bottom) {
		if (top != null) {
			if (isVoid(top)) {
				return doVoid(tile, top, bottom);
			}
			
			if(isEmpty(top)) {
				return doFill(tile, top, bottom);
			}
			
			if(isFilled(top)) {				
				return doEmpty(tile, top, bottom);
			}		
			
			if(FishFoodHandler.isFishFood(top)) {
				return addFishFood(tile, top);
			}
		}

		return null;
	}
	
	private static ItemStack addFishFood(IFluidHandler tile, ItemStack stack) {
		if (FishFoodHandler.isFishFood(stack)) {
			int increase = FishFoodHandler.getValue(stack);
			int fill = tile.fill(ForgeDirection.UP, FluidRegistry.getFluidStack(FluidDictionary.fish_food, increase), false);
			if(fill > 0) {
				tile.fill(ForgeDirection.UP, FluidRegistry.getFluidStack(FluidDictionary.fish_food, increase), true);
			}
		}

		return new ItemStack(Core.airBlocks);
	}
	
	private static ItemStack getEmptyContainerForFilledItem(ItemStack filledContainer) {
		FluidContainerData[] data = FluidContainerRegistry.getRegisteredFluidContainerData();

		for (int i = 0; i < data.length; i++) {
			if (data[i].filledContainer.itemID == filledContainer.itemID
					&& data[i].filledContainer.getItemDamage() == filledContainer.getItemDamage()) {
				return data[i].emptyContainer;
			}
		}

		return null;
	}
	
	public static int getRequiredVolumeForBlock(Fluid fluid) {
		FluidContainerData[] data = FluidContainerRegistry.getRegisteredFluidContainerData();

		for (int j = 0; j < data.length; j++) {
			if (data[j].fluid.fluidID == fluid.getID()) {
				return data[j].fluid.amount;
			}
		}
		
		return -1;
	}

	private static ItemStack doEmpty(IFluidHandler tile, ItemStack top, ItemStack bottom) {
		ItemStack result = getEmptyContainerForFilledItem(top);
		FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(top);
				
		if(result != null && tile.fill(ForgeDirection.UNKNOWN, fluid, false) == fluid.amount) {
			if(matches(top, bottom, result)) {
				tile.fill(ForgeDirection.UNKNOWN, fluid, true);
				return result;
			}
		}
		
		return null;
	}

	private static ItemStack doFill(IFluidHandler tile, ItemStack top, ItemStack bottom) {
		ItemStack result = FluidContainerRegistry.fillFluidContainer(tile.drain(ForgeDirection.UNKNOWN, 100000, false), top);
		if(result != null) {
			if(matches(top, bottom, result)) {
				FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(result);
				tile.drain(ForgeDirection.UNKNOWN, fluid.amount, true);
				return result;
			}
		}
		
		return null;
	}

	public static ItemStack doVoid(IFluidHandler tile, ItemStack top, ItemStack bottom) {
		if (matches(top, bottom, new ItemStack(Item.glassBottle))) {
			if(tile.getTankInfo(ForgeDirection.UNKNOWN) != null) {
				if(tile.getTankInfo(ForgeDirection.UNKNOWN)[0].fluid == null) {
					return null;
				}
			}
			
			tile.drain(ForgeDirection.UNKNOWN, OreDictionary.WILDCARD_VALUE, true);
			return new ItemStack(Item.glassBottle);
		}
		
		return null;
	}

	public static boolean matches(ItemStack top, ItemStack bottom, ItemStack result) {
		if (bottom == null) {
			return true;
		}

		return bottom.isItemEqual(result) && bottom.stackSize < 64 && bottom.stackSize < bottom.getMaxStackSize();
	}
	
	public static String getName(Fluid fluid) {
		if(fluid == null) {
			return null;
		}
		
		if (fluid.getID() > 0) {
			String name = fluid.getLocalizedName();
			if (name.startsWith("fluid.")) {
				name = name.substring(6);
				if (name.startsWith("tile.")) {
					name = name.substring(5);
				}
			}

			return name.substring(0, 1).toUpperCase() + name.substring(1);
		}

		return null;
	}
}
