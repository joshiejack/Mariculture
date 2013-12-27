package mariculture.core.helpers;

import java.util.Random;

import mariculture.api.core.IBlacklisted;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.util.IEjectable;
import mariculture.core.util.ITank;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

public class FluidTransferHelper {
	IFluidHandler tank;
	World world;
	int x, y, z;
	
	public FluidTransferHelper(IFluidHandler tank) {
		this.tank = tank;
		
		if(tank instanceof TileEntity) {
			this.world = ((TileEntity)tank).worldObj;
			this.x = ((TileEntity)tank).xCoord;
			this.y = ((TileEntity)tank).yCoord;
			this.z = ((TileEntity)tank).zCoord;
		}
	}
	
	public boolean isSameBlock(TileEntity tile1, TileEntity tile2) {
		return tile1.getBlockType().blockID == tile2.getBlockType().blockID && tile1.getBlockMetadata() == tile2.getBlockMetadata();
	}
	
	public boolean transfer(ForgeDirection from, int[] vals) {
		TileEntity theTile = world.getBlockTileEntity(x + from.offsetX, y + from.offsetY, z + from.offsetZ);
		if(theTile instanceof IFluidHandler) {
			IFluidHandler handler = (IFluidHandler) theTile;
			if(isSameBlock(theTile, (TileEntity) tank)) {
				return false;
			}
						
			if(handler instanceof IBlacklisted && handler instanceof TileEntity) {
				TileEntity tile = (TileEntity) handler;
				((IBlacklisted)handler).isBlacklisted(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
				return false;
			}
			
			for(int max: vals) {
				if(transfer(handler, from, max)) {
					return true;
				}
			}
		}
		
		return false;
	}
			
	public boolean transfer(IFluidHandler handler, ForgeDirection from, int transfer) {
		//If tank has ejectable controls and the eject setting isn't permissible...
		//Cancel the transfer
		if(tank instanceof IEjectable) {
			IEjectable e = (IEjectable) tank;
			if(!EjectSetting.canEject(e.getEjectSetting(), EjectSetting.FLUID))
				return false;
		}
		
		if (tank instanceof ITank) {
			ITank machine = (ITank) tank;
			if(machine.getFluid(transfer) != null) {
				if(handler.fill(from.getOpposite(), machine.getFluid(transfer), false) >= transfer) {
					handler.fill(from.getOpposite(), machine.getFluid(transfer), true);
					tank.drain(from, machine.getFluid(transfer), true);
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean transfer(Random rand, int[] rate) {
		ForgeDirection dir = ForgeDirection.getOrientation(rand.nextInt(6));
		return transfer(dir, rate);
	}
}
