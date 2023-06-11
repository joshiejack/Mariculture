package mariculture.core.helpers;

import java.util.HashMap;
import java.util.List;

import mariculture.core.Core;
import mariculture.core.blocks.base.BlockFluid;
import mariculture.core.lib.BottleMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules;
import mariculture.core.util.FluidMari;
import mariculture.core.util.Fluids;
import mariculture.core.util.MCTranslate;
import mariculture.core.util.Tank;
import mariculture.fishery.FishFoodHandler;
import mariculture.fishery.Fishery;
import mariculture.fishery.items.ItemDroplet;
import mariculture.lib.helpers.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

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
        return stack != null && stack.getItem() == Core.bottles && stack.getItemDamage() == BottleMeta.VOID;
    }

    public static boolean isIContainer(ItemStack stack) {
        return stack != null && stack.getItem() instanceof IFluidContainerItem;
    }

    public static ItemStack getFluidResult(Tank tank, ItemStack top, ItemStack bottom) {
        if (top != null) {
            if (isVoid(top)) return doVoid(tank, top, bottom);
            if (isEmpty(top)) return doFill(tank, top, bottom);
            if (isFilled(top)) return doEmpty(tank, top, bottom);
            if (isIContainer(top)) return doIContainer(tank, top, bottom);
            if (FishFoodHandler.isFishFood(top)) return addFishFood(tank, top);
        }

        return null;
    }

    private static ItemStack addFishFood(Tank tank, ItemStack stack) {
        int increase = FishFoodHandler.getValue(stack);
        int fill = tank.fill(Fluids.getFluidStack("fish_food", increase), false);
        if (fill >= increase) {
            tank.fill(Fluids.getFluidStack("fish_food", increase), true);
            return new ItemStack(Core.air);
        }

        return null;
    }

    public static HashMap<String, ItemStack> empties = new HashMap();

    public static void registerException(ItemStack filled, ItemStack empty) {
        empties.put(Item.itemRegistry.getNameForObject(filled.getItem()) + ":" + filled.getItemDamage(), empty);
    }

    public static ItemStack getEmptyContainerForFilledItem(ItemStack stack) {
        if (stack == null || stack.getItem() == null || empties == null) return null;
        ItemStack result = empties.get(Item.itemRegistry.getNameForObject(stack.getItem()) + ":" + stack.getItemDamage());
        return result != null ? result : stack.getItem().hasContainerItem(stack) ? stack.getItem().getContainerItem(stack) : null;
    }

    private static ItemStack doEmpty(Tank tank, ItemStack top, ItemStack bottom) {
        ItemStack result = getEmptyContainerForFilledItem(top);
        FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(top);
        if (top.getItem() == Fishery.droplet) {
            result = new ItemStack(Core.air);
        }

        if (result != null && tank.fill(fluid, false) == fluid.amount) {
            if (matches(top, bottom, result)) {
                tank.fill(fluid, true);
                return result;
            }
        }

        return null;
    }

    private static ItemStack doFill(Tank tank, ItemStack top, ItemStack bottom) {
        ItemStack result = FluidContainerRegistry.fillFluidContainer(tank.drain(100000, false), top);
        if (result != null) {
            if (matches(top, bottom, result)) {
                FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(result);
                tank.drain(fluid.amount, true);
                return result;
            }
        }

        return null;
    }

    private static ItemStack doIContainer(Tank tank, ItemStack top, ItemStack bottom) {
        if (bottom != null) return null;
        IFluidContainerItem container = (IFluidContainerItem) top.getItem();
        FluidStack fluid = container.getFluid(top);
        if (fluid != null) {
            FluidStack stack = container.drain(top, 1000000, false);
            if (stack != null) {
                stack.amount = tank.fill(stack, false);
                if (stack.amount > 0) {
                    container.drain(top, stack.amount, true);
                    tank.fill(stack, true);
                } else return null;
            }
        } else {
            FluidStack stack = tank.drain(MetalRates.INGOT, true);
            if (stack != null && stack.amount > 0) {
                container.fill(top, stack, true);
            } else return null;
        }

        return top;
    }

    public static ItemStack doVoid(Tank tank, ItemStack top, ItemStack bottom) {
        if (matches(top, bottom, new ItemStack(Core.bottles, 1, BottleMeta.EMPTY))) {
            FluidStack fluid = tank.drain(OreDictionary.WILDCARD_VALUE, false);
            if (fluid == null || fluid != null && fluid.amount <= 0) return null;

            tank.drain(OreDictionary.WILDCARD_VALUE, true);
            return new ItemStack(Core.bottles, 1, BottleMeta.EMPTY);
        }

        return null;
    }

    public static void process(Tank tank, IInventory invent, int in, int out) {
        ItemStack result = getFluidResult(tank, invent.getStackInSlot(in), invent.getStackInSlot(out));
        if (result != null) {
            invent.decrStackSize(in, 1);
            if (result.getItem() != Item.getItemFromBlock(Core.air)) if (invent.getStackInSlot(out) == null) {
                invent.setInventorySlotContents(out, result.copy());
            } else if (invent.getStackInSlot(out).getItem() == result.getItem()) {
                ItemStack stack = invent.getStackInSlot(out);
                ++stack.stackSize;
                invent.setInventorySlotContents(out, stack);
            }
        }
    }

    /** End Fluid Containers stuff **/

    public static int getRequiredVolumeForBlock(Fluid fluid) {
        FluidContainerData[] data = FluidContainerRegistry.getRegisteredFluidContainerData();
        int highest = -1;

        for (FluidContainerData element : data) {
            if (element.fluid.getFluid() == fluid) {
                if (element.fluid.amount > highest) {
                    highest = element.fluid.amount;
                }

                if (element.filledContainer.getItem() instanceof ItemDroplet) {
                    continue;
                }

                if (element.emptyContainer.getItem() == Items.bucket) return element.fluid.amount;
            }
        }

        return highest;
    }

    public static boolean matches(ItemStack top, ItemStack bottom, ItemStack result) {
        if (bottom == null) return true;
        return bottom.isItemEqual(result) && bottom.stackSize < 64 && bottom.stackSize < bottom.getMaxStackSize();
    }

    public static String getName(FluidStack fluidStack) {
        if (fluidStack == null || fluidStack.getFluid() == null) return null;

        if (fluidStack.getFluid().getID() > 0) {
            String name = fluidStack.getLocalizedName();
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

        if (newHeld != null) if (newHeld.stackSize == 0) {
            newHeld.stackSize = 1;
        }

        if (liquid != null) {
            int amount = tank.fill(ForgeDirection.UNKNOWN, liquid, false);
            if (amount >= liquid.amount) {
                tank.fill(ForgeDirection.UNKNOWN, liquid, true);
                if (!player.capabilities.isCreativeMode) if (heldItem.stackSize == 1) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, newHeld);
                } else {
                    player.inventory.decrStackSize(player.inventory.currentItem, 1);
                    if (!player.inventory.addItemStackToInventory(newHeld)) {
                        player.dropPlayerItemWithRandomChoice(newHeld, true);
                    }
                }
                return true;
            } else return true;
        } else if (FluidContainerRegistry.isEmptyContainer(heldItem)) {
            ItemStack result = FluidContainerRegistry.fillFluidContainer(tank.drain(ForgeDirection.UNKNOWN, 100000, false), heldItem);
            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(result);
            if (result != null) {
                tank.drain(side, fluid.amount, true);
                if (!player.capabilities.isCreativeMode) if (heldItem.stackSize == 1) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, result);
                } else {
                    player.inventory.decrStackSize(player.inventory.currentItem, 1);

                    if (!player.inventory.addItemStackToInventory(result)) {
                        player.dropPlayerItemWithRandomChoice(result, true);
                    }
                }

                return true;
            }
        }

        return false;
    }

    public static String getFluidName(FluidStack fluid) {
        if (fluid == null || fluid.getFluid() == null || fluid.amount <= 0) return mariculture.lib.util.Text.WHITE + MCTranslate.translate("empty");
        return FluidHelper.getFluidName(fluid.getFluid());
    }

    public static String getFluidName(Fluid fluid) {
        String fluidName = "";
        if (fluid.getRarity() == EnumRarity.uncommon) {
            fluidName += mariculture.lib.util.Text.YELLOW;
        } else if (fluid.getRarity() == EnumRarity.rare) {
            fluidName += mariculture.lib.util.Text.AQUA;
        } else if (fluid.getRarity() == EnumRarity.epic) {
            fluidName += mariculture.lib.util.Text.PINK;
        }
        fluidName += fluid.getLocalizedName() + mariculture.lib.util.Text.END;

        return fluidName;
    }

    public static List getFluidQty(List tooltip, FluidStack fluid, int max) {
        if (fluid == null || fluid.getFluid() == null) {
            tooltip.add(mariculture.lib.util.Text.GREY + "" + 0 + (max > 0 ? "/" + max + MCTranslate.translate("mb") : MCTranslate.translate("mb")));
        } else if (Modules.isActive(Modules.fishery) && fluid.getFluid() == Fluids.getFluid("fish_food")) {
            tooltip.add(mariculture.lib.util.Text.GREY + "" + fluid.amount + (max > 0 ? "/" + max + " " + MCTranslate.translate("pieces") : " " + MCTranslate.translate("pieces")));
        } else if (fluid.getFluid().getName().contains("glass") || fluid.getFluid().getName().contains("salt") || fluid.getFluid().getName().contains("dirt")) {
            tooltip.add(mariculture.lib.util.Text.GREY + "" + fluid.amount + (max > 0 ? "/" + max + MCTranslate.translate("mb") : MCTranslate.translate("mb")));
        } else if (fluid.getFluid().getName().contains("molten")) {
            int ingots = fluid.amount / MetalRates.INGOT;
            if (ingots > 0) {
                tooltip.add(mariculture.lib.util.Text.GREY + MCTranslate.translate("ingots") + ": " + ingots);
            }
            int mB = fluid.amount % MetalRates.INGOT;
            if (mB > 0) {
                int nuggets = mB / MetalRates.NUGGET;
                int junk = mB % MetalRates.NUGGET;
                if (nuggets > 0) {
                    tooltip.add(mariculture.lib.util.Text.GREY + MCTranslate.translate("nuggets") + ": " + nuggets);
                }
                if (junk > 0) {
                    tooltip.add(mariculture.lib.util.Text.GREY + MCTranslate.translate("mb") + ": " + junk);
                }
            }

            if (max > 0) {
                tooltip.add("");
                tooltip.add(mariculture.lib.util.Text.GREY + MCTranslate.translate("outof"));
                tooltip.add(mariculture.lib.util.Text.GREY + max / MetalRates.INGOT + " " + MCTranslate.translate("ingots") + " & " + max % MetalRates.INGOT + MCTranslate.translate("mb"));
            }
        } else {
            tooltip.add(mariculture.lib.util.Text.GREY + "" + fluid.amount + (max > 0 ? "/" + max + MCTranslate.translate("mb") : MCTranslate.translate("mb")));
        }

        return tooltip;
    }

    public static void addGas(String field, String name, int volume, int bottleMeta) {
        addFluid(field, name, volume, bottleMeta, true, 1000);
    }

    public static void addFluid(String field, String name, int volume, int bottleMeta, int balance) {
        addFluid(field, name, volume, bottleMeta, false, balance);
    }

    public static void addFluid(String field, String name, int volume, int bottleMeta, boolean isGas, int balance) {
    	Fluid fluid = new FluidMari(name, bottleMeta).setUnlocalizedName(name).setGaseous(isGas);
    	if (Fluids.add(field, fluid, balance)) {
            FluidRegistry.registerFluid(Fluids.getFluid(field));
        }
        
        fluid = Fluids.getFluid(field);

        if (volume != -1) {
            FluidHelper.registerHeatBottle(fluid, volume, bottleMeta);
        }
    }

    public static void registerHeatBottle(Fluid fluid, int vol, int meta) {
        FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid, vol), new ItemStack(Core.bottles, 1, meta), new ItemStack(Core.bottles, 1, BottleMeta.EMPTY));
    }

    public static void registerVanillaBottle(Fluid fluid, int vol, int meta) {
        FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid, vol), new ItemStack(Core.bottles, 1, meta), new ItemStack(Items.glass_bottle));
    }

    public static void addFluid(String name, int volume, int meta, int balance) {
        addFluid(name, name, volume, meta, balance);
    }

    public static void registerBucket(Fluid fluid, int vol, int meta) {
        FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid, vol), new ItemStack(Core.buckets, 1, meta), new ItemStack(Items.bucket));
    }

    public static boolean areEqual(FluidStack fluid1, FluidStack fluid2) {
        if (fluid1 == null || fluid2 == null || fluid1.getFluid() == null || fluid2.getFluid() == null) return false;
        return fluid1.getFluid().getName().equals(fluid2.getFluid().getName());
    }

    public static void setBlock(Fluid fluid, Block block) {
        if (fluid.getBlock() == null) fluid.setBlock(block);
    }

    public static void addFluid(String string, int balance) {
        addFluid(string, -1, -1, balance);
    }

    public static boolean setBlock(Class<? extends BlockFluid> clazz, String fluid) {
        return setBlock(clazz, fluid, fluid);
    }

    public static boolean setBlock(Class<? extends BlockFluid> clazz, String fName, String name) {
        Fluid fluid = FluidRegistry.getFluid(Fluids.getFluidName(fName));
        if (fluid.getBlock() != null) {
            return Fluids.setBlock(fName, fluid.getBlock());
        } else {
            try {
                Block block = ((Block) clazz.getDeclaredConstructor(Fluid.class, Material.class).newInstance(fluid, Material.water)).setBlockName(name);
                RegistryHelper.registerBlocks(new Block[] { block });
                return Fluids.setBlock(fName, block);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
