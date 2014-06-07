package mariculture.core.helpers;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import mariculture.core.Core;
import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.BottleMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules;
import mariculture.core.util.FluidMari;
import mariculture.core.util.Fluids;
import mariculture.core.util.Text;
import mariculture.fishery.FishFoodHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;

public class FluidHelper {
	public static boolean isFluidOrEmpty(ItemStack stack) {
		return isEmpty(stack) || isFilled(stack) || isVoid(stack) || FishFoodHandler.isFishFood(stack);
	}
	
	public static boolean isEmpty(ItemStack stack) {
		return FluidContainerRegistry.isEmptyContainer(stack);
	}
	
	public static boolean isFilled(ItemStack stack) {
		return FluidContainerRegistry.isFilledContainer(stack);
	}

	public static boolean isVoid(ItemStack stack) {
		return (stack != null && stack.getItem() == Core.bottles && stack.getItemDamage() == BottleMeta.VOID);
	}
	
	public static boolean isIContainer(ItemStack stack) {
		return stack != null && stack.getItem() instanceof IFluidContainerItem;
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
			
			if(isIContainer(top)) {
				return doIContainer(tile, top, bottom);
			}
			
			if(FishFoodHandler.isFishFood(top)) {
				return addFishFood(tile, top);
			}
		}

		return null;
	}

	private static ItemStack addFishFood(IFluidHandler tile, ItemStack stack) {
		int increase = FishFoodHandler.getValue(stack);
		int fill = tile.fill(ForgeDirection.UP, FluidRegistry.getFluidStack(Fluids.fish_food, increase), false);
		if(fill >= increase) {
			tile.fill(ForgeDirection.UP, FluidRegistry.getFluidStack(Fluids.fish_food, increase), true);
			return new ItemStack(Core.air);
		}

		return null;
	}
	
	public static HashMap<String, ItemStack> empties = new HashMap();
	public static void setup() {
		FluidContainerData[] data = FluidContainerRegistry.getRegisteredFluidContainerData();
		for(FluidContainerData container: data) {
			ItemStack filled = container.filledContainer;
			ItemStack empty = container.emptyContainer;
			if(filled != null && empty != null) {
				empties.put(Item.itemRegistry.getNameForObject(filled.getItem()) + ":" + filled.getItemDamage(), empty);
			}
		}
	}
	
	public static ItemStack getEmptyContainerForFilledItem(ItemStack stack) {
		if(stack == null) return null;
		return empties.get(Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + stack.getItemDamage());
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
	
	private static ItemStack doIContainer(IFluidHandler tile, ItemStack top, ItemStack bottom) {
		if(bottom != null)
			return null;
		IFluidContainerItem container = (IFluidContainerItem) top.getItem();
		FluidStack fluid = container.getFluid(top);
		if(fluid != null) {
			FluidStack stack = container.drain(top, 1000000, false);
			if(stack != null) {
				stack.amount = tile.fill(ForgeDirection.UNKNOWN, stack, false);
				if(stack.amount > 0) {
					container.drain(top, stack.amount, true);
					tile.fill(ForgeDirection.UNKNOWN, stack, true);
				} else {
					return null;
				}
			}
		} else {
			FluidStack stack = tile.drain(ForgeDirection.UNKNOWN, MetalRates.INGOT, true);
			if(stack != null && stack.amount > 0)
				container.fill(top, stack, true);
			else
				return null;
		}
			
		return top;
	}

	public static ItemStack doVoid(IFluidHandler tile, ItemStack top, ItemStack bottom) {
		if (matches(top, bottom, new ItemStack(Core.bottles, 1, BottleMeta.EMPTY))) {
			FluidStack fluid = tile.drain(ForgeDirection.UNKNOWN, OreDictionary.WILDCARD_VALUE, false);
			if(fluid == null || fluid != null && fluid.amount <= 0)
				return null;
			
			tile.drain(ForgeDirection.UNKNOWN, OreDictionary.WILDCARD_VALUE, true);
			return new ItemStack(Core.bottles, 1, BottleMeta.EMPTY);
		}
		
		return null;
	}
	
	public static void process(IInventory invent, int in, int out) {
		ItemStack result = getFluidResult((IFluidHandler) invent, invent.getStackInSlot(in), invent.getStackInSlot(out));
		if (result != null) {
			invent.decrStackSize(in, 1);
			if(result.getItem() != Item.getItemFromBlock(Core.air)) {
				if (invent.getStackInSlot(out) == null) {
					invent.setInventorySlotContents(out, result.copy());
				} else if (invent.getStackInSlot(out).getItem() == result.getItem()) {
					ItemStack stack = invent.getStackInSlot(out);
					++stack.stackSize;
					invent.setInventorySlotContents(out, stack);
				}
			}
		}
	}
	
	/** End Fluid Containers stuff **/
	
	public static int getRequiredVolumeForBlock(Fluid fluid) {
		FluidContainerData[] data = FluidContainerRegistry.getRegisteredFluidContainerData();
		int highest = -1;

		for (int j = 0; j < data.length; j++) {
			if (data[j].fluid.fluidID == fluid.getID()) {
				if(data[j].fluid.amount > highest)
					highest = data[j].fluid.amount;
				if(data[j].emptyContainer.getItem() == Items.bucket)
					return data[j].fluid.amount;
			}
		}
		
		return highest;
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

	public static boolean handleFillOrDrain(IFluidHandler tank, EntityPlayer player, ForgeDirection side) {
		ItemStack heldItem = player.inventory.getCurrentItem();
		FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(heldItem);
		ItemStack newHeld = FluidHelper.getEmptyContainerForFilledItem(heldItem);
		
		if(newHeld != null) {
			if(newHeld.stackSize == 0)
				newHeld.stackSize = 1;
		}
		
		if (liquid != null) {
			int amount = tank.fill(ForgeDirection.UNKNOWN, liquid, false);
			if (amount >= liquid.amount) {
				tank.fill(ForgeDirection.UNKNOWN, liquid, true);
				if (!player.capabilities.isCreativeMode) {
					if(heldItem.stackSize == 1) {
						player.inventory.setInventorySlotContents(player.inventory.currentItem, newHeld);
					} else {
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
						if (!player.inventory.addItemStackToInventory(newHeld))
							player.dropPlayerItemWithRandomChoice(newHeld, true);
					}
				}
				return true;
			} else {
				return true;
			}
		} else if (FluidContainerRegistry.isEmptyContainer(heldItem)) {
			ItemStack result = FluidContainerRegistry.fillFluidContainer(tank.drain(ForgeDirection.UNKNOWN, 100000, false), heldItem);
			FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(result);
			if (result != null) {
				tank.drain(side, fluid.amount, true);
				if (!player.capabilities.isCreativeMode) {
					if (heldItem.stackSize == 1) {
						player.inventory.setInventorySlotContents(player.inventory.currentItem, result);
					} else {
						player.inventory.decrStackSize(player.inventory.currentItem, 1);

						if (!player.inventory.addItemStackToInventory(result))
							player.dropPlayerItemWithRandomChoice(result, true);
					}
				}

				return true;
			}
		}
		
		return false;
	}

	public static String getFluidName(FluidStack fluid) {
		if(fluid == null || fluid.getFluid() == null || fluid.amount <= 0)
			return Text.WHITE + StatCollector.translateToLocal("mariculture.string.empty");
		return FluidHelper.getFluidName(fluid.getFluid());
	}
	
	public static String getFluidName(Fluid fluid) {
		
		String fluidName = "";
		if (fluid.getRarity() == EnumRarity.uncommon) {
			fluidName += Text.YELLOW;
		} else if (fluid.getRarity() == EnumRarity.rare) {
			fluidName += Text.AQUA;
		} else if (fluid.getRarity() == EnumRarity.epic) {
			fluidName += Text.PINK;
		} 
		fluidName += fluid.getLocalizedName() + Text.END;
	
		return fluidName;
	}

	public static List getFluidQty(List tooltip, FluidStack fluid, int max) {	
		if(fluid == null || fluid.getFluid() == null) {
			tooltip.add(Text.GREY + "" + 0 + ((max > 0)? "/" + max + Text.translate("mb"): Text.translate("mb")));
		} else if(Modules.isActive(Modules.fishery) && fluid.fluidID == FluidRegistry.getFluidID(Fluids.fish_food))
			tooltip.add(Text.GREY + "" + fluid.amount + ((max > 0) ?"/" + max + " " + StatCollector.translateToLocal("mariculture.string.pieces"): " " + StatCollector.translateToLocal("mariculture.string.pieces")));
		else if(fluid.getFluid().getName().contains("glass") || fluid.getFluid().getName().contains("salt") || fluid.getFluid().getName().contains("dirt"))
			tooltip.add(Text.GREY + "" + fluid.amount + ((max > 0)? "/" + max + Text.translate("mb"): Text.translate("mb")));
		else if(fluid.getFluid().getName().contains("molten")) {
			int ingots = fluid.amount / MetalRates.INGOT;
	        if (ingots > 0)
	            tooltip.add(Text.GREY + StatCollector.translateToLocal("mariculture.string.ingots") + ": " + ingots);
	        int mB = fluid.amount % MetalRates.INGOT;
	        if (mB > 0)  {
	            int nuggets = mB / MetalRates.NUGGET;
	            int junk = (mB % MetalRates.NUGGET);
	            if (nuggets > 0)
	                tooltip.add(Text.GREY + StatCollector.translateToLocal("mariculture.string.nuggets") + ": " + nuggets);
	            if (junk > 0)
	                tooltip.add(Text.GREY + Text.translate("mb") + ": " + junk);
	        }
	        
	        if(max > 0) {
	            tooltip.add("");
	            tooltip.add(Text.GREY + StatCollector.translateToLocal("mariculture.string.outof"));
	            tooltip.add(Text.GREY + (int)max/MetalRates.INGOT + " " + StatCollector.translateToLocal("mariculture.string.ingots") + " & " 
	            			+ max%MetalRates.INGOT + Text.translate("mb"));
	        }
		} else {
			tooltip.add(Text.GREY + "" + fluid.amount + ((max > 0)? "/" + max + Text.translate("mb"): Text.translate("mb")));
		}
		
		return tooltip;
	}

	public static Fluid addFluid(String field, String name, int volume, int bottleMeta) {
		Fluid fluid = null;
		if (!Fluids.instance.fluidExists(name)) {
			fluid = new FluidMari(name, bottleMeta).setUnlocalizedName(name);
			FluidRegistry.registerFluid(fluid);
			Fluids.instance.addFluid(name, fluid);
		} else {
			fluid = Fluids.getFluid(name);
		}

		if(volume != -1) {
			FluidHelper.registerHeatBottle(fluid, volume, bottleMeta);
		}
		
		//Set the values of the field in Fluids
		try {
			Field f = Fluids.class.getField(field);
			f.set(null, fluid.getName());
		} catch (Exception e) {
			LogHandler.log(Level.INFO, "Failed to set a Fluid in Fluids.java : " + field + " - " + name);
		}
		
		return fluid;
	}

	public static void registerHeatBottle(Fluid fluid, int vol, int meta) {
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid, vol), new ItemStack(Core.bottles, 1, meta), new ItemStack(Core.bottles, 1, BottleMeta.EMPTY));
	}

	public static void registerVanillaBottle(Fluid fluid, int vol, int meta) {
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid, vol), new ItemStack(Core.bottles, 1, meta), new ItemStack(Items.glass_bottle));
	}

	public static Fluid addFluid(String name, int volume, int meta) {
		return addFluid(name, name, volume, meta);
	}

	public static void registerBucket(Fluid fluid, int vol, int meta) {
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid, vol), new ItemStack(Core.buckets, 1, meta), new ItemStack(Items.bucket));
	}

	public static boolean areEqual(FluidStack fluid1, FluidStack fluid2) {
		if(fluid1 == null || fluid2 == null || fluid1.getFluid() == null || fluid2.getFluid() == null) return false;
		return fluid1.getFluid().getName().equals(fluid2.getFluid().getName());
	}
}
