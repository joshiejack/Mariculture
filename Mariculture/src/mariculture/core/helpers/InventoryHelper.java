package mariculture.core.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cofh.api.transport.IItemConduit;

import mariculture.core.util.IItemDropBlacklist;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.core.Position;
import buildcraft.api.transport.IPipeTile;
import buildcraft.api.transport.IPipeTile.PipeType;

public class InventoryHelper {
	public static void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

		if (!(tile_entity instanceof IInventory)) {
			return;
		}

		IInventory inventory = (IInventory) tile_entity;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			boolean drop = true;
			if(tile_entity instanceof IItemDropBlacklist) {
				drop = ((IItemDropBlacklist)tile_entity).doesDrop(i);
			}
			
			if(drop) {
				ItemStack item = inventory.getStackInSlot(i);
	
				if (item != null && item.stackSize > 0) {
					float rx = rand.nextFloat() * 0.6F + 0.1F;
					float ry = rand.nextFloat() * 0.6F + 0.1F;
					float rz = rand.nextFloat() * 0.6F + 0.1F;
	
					EntityItem entity_item = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.itemID,
							item.stackSize, item.getItemDamage()));
	
					if (item.hasTagCompound()) {
						entity_item.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
					}
	
					float factor = 0.05F;
	
					entity_item.motionX = rand.nextGaussian() * factor;
					entity_item.motionY = rand.nextGaussian() * factor + 0.2F;
					entity_item.motionZ = rand.nextGaussian() * factor;
					world.spawnEntityInWorld(entity_item);
					item.stackSize = 0;
				}
			}
		}
	}
	
	public static void spawnItem(World world, int x, int y, int z, ItemStack stack) {
        if (!world.isRemote) {
            float f = 0.7F;
            double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, stack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }

	public static int addToRandomPipeAround(World world, int x, int y, int z, ForgeDirection from, ItemStack stack, int[] sides) {
		List<IPipeTile> possiblePipes = new ArrayList<IPipeTile>();
		List<ForgeDirection> pipeDirections = new ArrayList<ForgeDirection>();
	
		for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
			if (from.getOpposite() == side)
				continue;
	
			boolean carryOn = false;
			for (int i = 0; i < sides.length; i++) {
				if (side.ordinal() == sides[i]) {
					carryOn = true;
				}
			}
	
			if (carryOn) {
				Position pos = new Position(x, y, z, side);
	
				pos.moveForwards(1.0);
	
				TileEntity tile = world.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);
	
				if (tile instanceof IPipeTile) {
					IPipeTile pipe = (IPipeTile) tile;
					if (pipe.getPipeType() != PipeType.ITEM)
						continue;
					if (!pipe.isPipeConnected(side.getOpposite()))
						continue;
	
					possiblePipes.add(pipe);
					pipeDirections.add(side.getOpposite());
				}
			}
		}
	
		if (possiblePipes.size() > 0) {
			int choice = new Random().nextInt(possiblePipes.size());
	
			IPipeTile pipeEntry = possiblePipes.get(choice);
	
			return pipeEntry.injectItem(stack, true, pipeDirections.get(choice));
		}
		return 0;
	}

	public static boolean addToInventory(int size, World world, int x, int y, int z, ItemStack stack, int[] sides) {
		boolean placed = false;
	
		if (sides == null) {
			sides = new int[] { 0, 1, 2, 3, 4, 5 };
		}
		
		stack = addToRandomConduit(world, x, y, z, ForgeDirection.UP, stack, sides);
		if(stack == null) {
			placed = true;
		}
		
		if(!placed) {
			stack.stackSize -= addToRandomPipeAround(world, x, y, z, ForgeDirection.UP, stack, sides);
			if (stack.stackSize <= 0) {
				placed = true;
			}
		}
	
		if (!placed) {
			IInventory chest = InventoryHelper.getNextInventory(size, world, x, y, z, stack);
			int side = InventoryHelper.getSide(size, world, x, y, z, stack);
			if (chest != null && side != -1) {
				boolean carryOn = true;
	
				int chestSlot = InventoryHelper.getSlot(chest, stack, side);
				if (chestSlot != -1) {
					if (chest.getStackInSlot(chestSlot) != null) {
						chest.getStackInSlot(chestSlot).stackSize += stack.stackSize;
					} else {
						chest.setInventorySlotContents(chestSlot, stack);
					}
	
					placed = true;
				}
			}
		}
	
		return placed;
	}

	private static ItemStack addToRandomConduit(World world, int x, int y, int z, ForgeDirection from, ItemStack stack, int[] sides) {
		List<IItemConduit> possiblePipes = new ArrayList<IItemConduit>();
		List<ForgeDirection> pipeDirections = new ArrayList<ForgeDirection>();
		
			for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
				if (from.getOpposite() == side)
					continue;
		
				boolean carryOn = false;
				for (int i = 0; i < sides.length; i++) {
					if (side.ordinal() == sides[i]) {
						carryOn = true;
					}
				}
		
				if (carryOn) {
					Position pos = new Position(x, y, z, side);
					pos.moveForwards(1.0);
					TileEntity tile = world.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);
		
					if (tile instanceof IItemConduit) {
						IItemConduit pipe = (IItemConduit) tile;		
						possiblePipes.add(pipe);
						pipeDirections.add(side.getOpposite());
					}
				}
			}
		
			if (possiblePipes.size() > 0) {
				int choice = new Random().nextInt(possiblePipes.size());
		
				IItemConduit pipeEntry = possiblePipes.get(choice);
		
				return pipeEntry.sendItems(stack, pipeDirections.get(choice));
			}
			
			return stack;
	}

	public static int getSide(int distance, World world, int x, int y, int z, ItemStack stack) {
		distance = distance + 1;
	
		if (world.getBlockTileEntity(x + distance, y, z) instanceof IInventory) {
			IInventory chest = (IInventory) world.getBlockTileEntity(x + distance, y, z);
			if (InventoryHelper.getSlot(chest, stack, 5) > -1) {
				return 5;
			}
		}
	
		if (world.getBlockTileEntity(x - distance, y, z) instanceof IInventory) {
			IInventory chest = (IInventory) world.getBlockTileEntity(x - distance, y, z);
			if (InventoryHelper.getSlot(chest, stack, 3) > -1) {
				return 3;
			}
		}
	
		if (world.getBlockTileEntity(x, y, z + distance) instanceof IInventory) {
			IInventory chest = (IInventory) world.getBlockTileEntity(x, y, z + distance);
			if (InventoryHelper.getSlot(chest, stack, 2) > -1) {
				return 2;
			}
		}
	
		if (world.getBlockTileEntity(x, y, z - distance) instanceof IInventory) {
			IInventory chest = (IInventory) world.getBlockTileEntity(x, y, z - distance);
			if (InventoryHelper.getSlot(chest, stack, 4) > -1) {
				return 4;
			}
		}
	
		if (distance == 1) {
			if (world.getBlockTileEntity(x, y - distance, z) instanceof IInventory) {
				IInventory chest = (IInventory) world.getBlockTileEntity(x, y - distance, z);
				if (InventoryHelper.getSlot(chest, stack, 0) > -1) {
					return 0;
				}
			}
	
			if (world.getBlockTileEntity(x, y + distance, z) instanceof IInventory) {
				IInventory chest = (IInventory) world.getBlockTileEntity(x, y + distance, z);
				if (InventoryHelper.getSlot(chest, stack, 1) > -1) {
					return 1;
				}
			}
		}
	
		return -1;
	}

	public static IInventory getNextInventory(int distance, World world, int x, int y, int z, ItemStack stack) {
		distance = distance + 1;
	
		if (world.getBlockTileEntity(x + distance, y, z) instanceof IInventory) {
			IInventory chest = (IInventory) world.getBlockTileEntity(x + distance, y, z);
			if (InventoryHelper.getSlot(chest, stack, 5) > -1) {
				return (IInventory) world.getBlockTileEntity(x + distance, y, z);
			}
		}
	
		if (world.getBlockTileEntity(x - distance, y, z) instanceof IInventory) {
			IInventory chest = (IInventory) world.getBlockTileEntity(x - distance, y, z);
			if (InventoryHelper.getSlot(chest, stack, 3) > -1) {
				return (IInventory) world.getBlockTileEntity(x - distance, y, z);
			}
		}
	
		if (world.getBlockTileEntity(x, y, z + distance) instanceof IInventory) {
			IInventory chest = (IInventory) world.getBlockTileEntity(x, y, z + distance);
			if (InventoryHelper.getSlot(chest, stack, 2) > -1) {
				return (IInventory) world.getBlockTileEntity(x, y, z + distance);
			}
		}
	
		if (world.getBlockTileEntity(x, y, z - distance) instanceof IInventory) {
			IInventory chest = (IInventory) world.getBlockTileEntity(x, y, z - distance);
			if (InventoryHelper.getSlot(chest, stack, 4) > -1) {
				return (IInventory) world.getBlockTileEntity(x, y, z - distance);
			}
		}
	
		if (distance == 1) {
			if (world.getBlockTileEntity(x, y - distance, z) instanceof IInventory) {
				IInventory chest = (IInventory) world.getBlockTileEntity(x, y - distance, z);
				if (InventoryHelper.getSlot(chest, stack, 0) > -1) {
					return (IInventory) world.getBlockTileEntity(x, y - distance, z);
				}
			}
	
			if (world.getBlockTileEntity(x, y + distance, z) instanceof IInventory) {
				IInventory chest = (IInventory) world.getBlockTileEntity(x, y + distance, z);
				if (InventoryHelper.getSlot(chest, stack, 1) > -1) {
					return (IInventory) world.getBlockTileEntity(x, y + distance, z);
				}
			}
		}
	
		return null;
	}

	public static int getSlot(IInventory chest, ItemStack stack, int side) {
		for (int i = 0; i < chest.getSizeInventory(); i++) {
			if (chest.getStackInSlot(i) != null) {
				if (chest.getStackInSlot(i).isItemEqual(stack)
						&& chest.getStackInSlot(i).stackSize < chest.getStackInSlot(i).getMaxStackSize()) {
					boolean carryOn = true;
					if (chest instanceof ISidedInventory) {
						carryOn = false;
						ISidedInventory sided = (ISidedInventory) chest;
						if (sided.canInsertItem(i, stack, side)) {
							carryOn = true;
						}
					}
	
					if (carryOn) {
						return i;
					}
				}
			}
		}
		
		// Now check if the slot is null instead
		for (int i = 0; i < chest.getSizeInventory(); i++) {
			if (chest.getStackInSlot(i) == null) {
				boolean carryOn = true;
				if (chest instanceof ISidedInventory) {
					carryOn = false;
					ISidedInventory sided = (ISidedInventory) chest;
					if (sided.canInsertItem(i, stack, side)) {
						carryOn = true;
					}
				}
				if (carryOn) {
					return i;
				}
			}
		}
	
		return -1;
	}

	public static String getName(TileEntity tile) {
		if(tile == null) {
			return "";
		}
		
		int id = tile.getBlockType().blockID;
		if(Item.itemsList[id] != null) {
			Item block = Item.itemsList[id];
			return block.getItemDisplayName(new ItemStack(id, 1, tile.getBlockMetadata()));
		}
		
		return "";
	}
}
