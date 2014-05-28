package mariculture.core.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mariculture.api.core.IBlacklisted;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.helpers.cofh.InventoryHelper;
import mariculture.core.tile.base.TileMultiBlock;
import mariculture.core.tile.base.TileMultiBlock.MultiPart;
import mariculture.core.tile.base.TileMultiStorage;
import mariculture.core.tile.base.TileMultiStorageTank;
import mariculture.core.util.IEjectable;
import mariculture.core.util.IMachine;
import mariculture.core.util.ITank;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

public class BlockTransferHelper {
	public static List<Integer> sides;
	static {
		sides = new ArrayList<Integer>();
		for(int i = 0; i < 6; i++) {
			sides.add(i);
		}
	}
	
	public TileEntity thisTile;
	public IFluidHandler handler;
	public IInventory inventory;
	public World world;
	int x, y, z;
	
	public BlockTransferHelper(TileEntity tile) {
		this.thisTile = tile;
		this.world = tile.getWorldObj();
		this.x = tile.xCoord;
		this.y = tile.yCoord;
		this.z = tile.zCoord;
		
		if(tile instanceof IInventory)
			this.inventory = (IInventory) tile;
		
		if(inventory instanceof IFluidHandler)
			this.handler = (IFluidHandler) tile;
	}
	
	public boolean ejectFluid(int[] rate) {
		boolean canEject = true;
		if(handler instanceof IEjectable) {
			IEjectable ejectable = (IEjectable) handler;
			if(ejectable instanceof TileMultiStorageTank) {
				TileMultiStorageTank tile = (TileMultiStorageTank) handler;
				if(tile.getMaster() != null) {
					ejectable = (IEjectable) tile.getMaster();
				}
			}
				
			canEject = EjectSetting.canEject(ejectable.getEjectSetting(), EjectSetting.FLUID);
		}
			
		if(canEject) {
			Collections.shuffle(sides);
			for(Integer side: sides) {
				ForgeDirection dir = ForgeDirection.getOrientation(side);
				TileEntity tile = world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
					
				if(isSameBlock(tile))
					continue;
				if(tile instanceof IFluidHandler) {
					IFluidHandler tank = (IFluidHandler) tile;
					if(tank instanceof IBlacklisted) {
						if(((IBlacklisted)tank).isBlacklisted(world, x, y, z))
							continue;
					}
						
					for(int drain: rate) {
						if(transfer(tank, dir.getOpposite(), drain)) {
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private boolean transfer(IFluidHandler tank, ForgeDirection from, int transfer) {
		if (handler instanceof ITank) {
			ITank machine = (ITank) handler;
			
			//If a MultiBlock Tank, then get the master to drain from instead :)
			if(handler instanceof TileMultiStorageTank) {
				TileMultiStorageTank tile = (TileMultiStorageTank) handler;
				if(tile.getMaster() != null) {
					machine = (ITank) tile.getMaster();
				}
			}
			
			if(machine.getFluid(transfer) != null) {
				if(tank.fill(from, machine.getFluid(transfer), false) >= transfer) {
					tank.fill(from, machine.getFluid(transfer), true);
					((IFluidHandler)machine).drain(from, machine.getFluid(transfer), true);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public ItemStack insertStack(ItemStack stack, int[] slots) {
		//If the Block is instance of ejectable, try to insert in to nearby inventories OR throw the stack out
		if(inventory instanceof IEjectable) {
			IEjectable ejectable = (IEjectable) inventory;
			if(ejectable instanceof TileMultiStorage) {
				TileMultiStorage tile = (TileMultiStorage) inventory;
				if(tile.getMaster() != null) {
					ejectable = (IEjectable) tile.getMaster();
				}
			}
		
			if(ejectable != null && EjectSetting.canEject(ejectable.getEjectSetting(), EjectSetting.ITEM)) {
				if(inventory instanceof TileMultiBlock) {					
					TileMultiBlock tile = (TileMultiBlock) inventory;
					if(tile.getMaster() != null) {
						TileMultiBlock master = tile.getMaster();
						ArrayList<MultiPart> cords = master.slaves;
												
						Collections.shuffle(cords);
						for(MultiPart cord: cords) {
							if(world.getTileEntity(cord.xCoord, cord.yCoord, cord.zCoord) != null) {
								BlockTransferHelper helper = new BlockTransferHelper(world.getTileEntity(cord.xCoord, cord.yCoord, cord.zCoord));
								stack = helper.ejectToSides(stack);
							}
						}
						
						stack = new BlockTransferHelper(master).ejectToSides(stack);
					}
				} else {
					stack = ejectToSides(stack);
				}
			}
		}
		
		if(!InventoryHelper.addItemStackToInventory(((IMachine)inventory).getInventory(), stack, slots))
			return SpawnItemHelper.spawnItem(this, stack);
		
		return null;
	}

	private ItemStack ejectToSides(ItemStack stack) {
		Collections.shuffle(sides);
		for(Integer side: sides) {
			ForgeDirection dir = ForgeDirection.getOrientation(side);
			TileEntity tile = world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
			if(tile instanceof IInventory && !(tile instanceof TileEntityHopper) && !isSameBlock(tile)) {
				stack = InventoryHelper.insertItemStackIntoInventory((IInventory)tile, stack, dir.getOpposite().ordinal());
			}
		}
		
		return stack;
	}
	
	private boolean isSameBlock(TileEntity tile) {
		if(tile != null && inventory != null) {
			if(tile.getClass().equals(thisTile.getClass()))
				return true;
		}
		
		return false;
	}
}
