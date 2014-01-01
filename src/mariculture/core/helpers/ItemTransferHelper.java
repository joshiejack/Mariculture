package mariculture.core.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mariculture.core.blocks.base.TileMulti;
import mariculture.core.blocks.base.TileMulti.Cached;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.helpers.cofh.InventoryHelper;
import mariculture.core.lib.PlansMeta;
import mariculture.core.util.IEjectable;
import mariculture.core.util.IMachine;
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
	
	public ItemStack insertOnly(ItemStack stack, int[] slots) {
		if(inventory instanceof IEjectable) {
			IEjectable eject = (IEjectable) inventory;
			if(EjectSetting.canEject(eject.getEjectSetting(), EjectSetting.ITEM))
				stack = ejectToSides(stack);
		}
				
		return stack;
	}
	
	public boolean insertStack(ItemStack stack, int[] slots) {
		if(inventory instanceof TileMulti) {
			boolean inserted;
			TileMulti tile = (TileMulti) inventory;
			ArrayList<Cached> cords = tile.getCache();
			Collections.shuffle(cords);
			for(Cached cord: cords) {
				ItemTransferHelper helper = new ItemTransferHelper((IInventory) world.getBlockTileEntity(cord.x, cord.y, cord.z));
				stack = helper.insertOnly(stack, slots);
			}
		} else {
			if(inventory instanceof IEjectable) {
				IEjectable eject = (IEjectable) inventory;
				if(EjectSetting.canEject(eject.getEjectSetting(), EjectSetting.ITEM))
					stack = ejectToSides(stack);
			}
		}

		boolean ejected = false;
		ejected = InventoryHelper.addItemStackToInventory(((IMachine)inventory).getInventory(), stack, slots);
		if(!ejected) {
			ejected = true;
			spawnItem(world, x, y, z, stack, true);
		}
		
		return ejected;
	}
	
	private ItemStack ejectToSides(ItemStack stack) {
		List<Integer> sides = addSides(new ArrayList<Integer>());
		Collections.shuffle(sides);
		
		for(Integer side: sides) {
			ForgeDirection dir = ForgeDirection.getOrientation(side);
			stack = eject(dir, stack);
		}
		
		return stack;
	}
	
	private ItemStack eject(ForgeDirection dir, ItemStack stack) {
		TileEntity tile = world.getBlockTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
		if(tile != null && inventory != null) {
			if(tile.getClass().equals(inventory.getClass()))
				return stack;
		}

		if(tile instanceof IInventory && !(tile instanceof TileEntityHopper)) {
			return InventoryHelper.insertItemStackIntoInventory((IInventory)tile, stack, dir.ordinal());
		}
		
		return stack;
	}
	
	private List<Integer> addSides(List list) {
		for(int i = 0; i < 6; i++) {
			list.add(i);
		}
		
		return list;
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
}
