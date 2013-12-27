package mariculture.core.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.lib.PlansMeta;
import mariculture.core.util.IEjectable;
import mariculture.factory.blocks.BlockItemCustom;
import mariculture.factory.blocks.BlockItemCustomSlabBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemTransferHelper {
	IInventory inventory;
	World world;
	int x, y, z;
	
	public ItemTransferHelper(IInventory inventory) {
		this.inventory = inventory;
		
		if(inventory instanceof TileEntity) {
			this.world = ((TileEntity)inventory).worldObj;
			this.x = ((TileEntity)inventory).xCoord;
			this.y = ((TileEntity)inventory).yCoord;
			this.z = ((TileEntity)inventory).zCoord;
		}
	}
	
	public boolean insertStack(ItemStack stack, int[] slots) {
		boolean ejected = false;
		if(inventory instanceof IEjectable) {
			IEjectable eject = (IEjectable) inventory;
			if(EjectSetting.canEject(eject.getEjectSetting(), EjectSetting.ITEM))
				ejected = ejectToSides(stack);
		}
		
		if(!ejected) {
			ejected = addToInventory(stack, slots);
		}
		
		if(!ejected) {
			spawnItem(world, x, y, z, stack, true);
		}
		
		return ejected;
	}
	
	private List<Integer> addSides(List list) {
		for(int i = 0; i < 6; i++) {
			list.add(i);
		}
		
		return list;
	}

	private boolean ejectToSides(ItemStack stack) {
		List<Integer> sides = addSides(new ArrayList<Integer>());
		Collections.shuffle(sides);
		
		for(Integer side: sides) {
			ForgeDirection dir = ForgeDirection.getOrientation(side);
			if(eject(dir, stack))
				return true;
		}
		
		return false;
	}
	
	public void spawnItem(World world, int x, int y, int z, ItemStack stack, boolean random) {
		if (!world.isRemote) {
            float f = 0.7F;
            double d0 = (random)? (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D: 0.5D;
            double d1 = (random)? (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D: 0.5D;
            double d2 = (random)? (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D: 0.5D;
            EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, stack);
            entityitem.motionX = world.rand.nextFloat() * f * 0.25D;
            entityitem.motionY = world.rand.nextFloat() * f * 0.25D;
            entityitem.motionZ = world.rand.nextFloat() * f * 0.25D;
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
	}
	
	private static boolean areItemStacksEqualItem(ItemStack stack1, ItemStack stack2) {
        return stack1.itemID != stack2.itemID ? false : (stack1.getItemDamage() != stack2.getItemDamage() ? false : (stack1.stackSize > stack1.getMaxStackSize() ? false : ItemStack.areItemStackTagsEqual(stack1, stack2)));
    }
	
	private boolean isCustomBlock(ItemStack stack) {
		return stack.getItem() instanceof BlockItemCustom || stack.getItem() instanceof BlockItemCustomSlabBase;
	}
	
	private boolean addToInventory(ItemStack stack1, int[] slots) {
		for(int i: slots) {
			ItemStack stack2 = inventory.getStackInSlot(i);
			if (stack2 == null) {
				inventory.setInventorySlotContents(i, stack1);
				return true;
			} else if(isCustomBlock(stack1) && isCustomBlock(stack2)) {
				if(PlansMeta.isSame(stack1, stack2)) {
					stack1.stackSize+= stack2.stackSize;
					inventory.setInventorySlotContents(i, stack1);
					return true;
				}
			} else if (areItemStacksEqualItem(stack1, stack2) && stack1.stackSize + stack2.stackSize <= stack1.getMaxStackSize()) {
				stack1.stackSize+= stack2.stackSize;
				inventory.setInventorySlotContents(i, stack1);
				return true;
			}
		}
		
		return false;
	}

	private boolean eject(ForgeDirection dir, ItemStack stack) {
		TileEntity tile = world.getBlockTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
		if(tile instanceof IInventory && !(tile instanceof TileEntityHopper)) {
			IInventory invent = (IInventory) tile;
			ISidedInventory sided = null;
			if(invent instanceof ISidedInventory) {
				sided = (ISidedInventory) invent;
			}
			
			for(int i = 0; i < invent.getSizeInventory(); i++) {
				if(sided == null || (sided != null && sided.canInsertItem(i, stack, dir.ordinal()))) {
					ItemStack stackInSlot = invent.getStackInSlot(i);
					if(stackInSlot == null || (ItemStack.areItemStacksEqual(stackInSlot, stack) 
							&& stackInSlot.stackSize + stack.stackSize < stackInSlot.getMaxStackSize())) {
						ItemStack newStack = stack;
						if(stackInSlot != null) {
							newStack.stackSize+= stackInSlot.stackSize;
						}
						
						invent.setInventorySlotContents(i, newStack);
						return true;
					}
				}
			}
		}
		
		return false;
	}
}
