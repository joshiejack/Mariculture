package mariculture.factory.blocks;

import mariculture.core.blocks.base.TileTank;
import mariculture.core.handlers.FluidDicHandler;
import mariculture.core.util.Tank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public class TileDictionaryFluid extends TileTank {
	public TileDictionaryFluid() {
		tank = new Tank(1);
	}
	
	public double getCapacity() {
		return tank.getCapacity();
	}
	
	//RETURNS THE AMOUNT OF LIQUID THAT HAS BEEN TAKEN AWAYS!!!
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(FluidDicHandler.areSameDicNames(resource, tank.getFluid())) {
			int newAmount = FluidDicHandler.getValue(tank.getFluid().getFluid().getName());
			int oldAmount = FluidDicHandler.getValue(resource.getFluid().getName());
			
			//Setup the coordinates
			int x = xCoord + from.offsetX;
			int y = yCoord + from.offsetY;
			int z = zCoord + from.offsetZ;
			//Finding a tile Entity to drain to
			if(worldObj.getTileEntity(x, y, z) instanceof IFluidHandler && resource.amount >= oldAmount) {
				int drainAmount = Math.min(resource.amount, oldAmount);
				IFluidHandler handler = (IFluidHandler) worldObj.getTileEntity(x, y, z);
				FluidStack output = new FluidStack(tank.getFluid().getFluid(), newAmount);
				if(handler.fill(from.getOpposite(), output, false) == newAmount) {
					if(doFill) {
						handler.fill(from, output, true);
					}
					
					return drainAmount;
				} else return 0;
			} else return 0;
		} else return 0;
	}
	
	@Override
	public Packet getDescriptionPacket()  {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
    }
}
