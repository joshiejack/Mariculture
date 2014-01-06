package mariculture.core.blocks;

import java.util.ArrayList;

import mariculture.core.blocks.base.TileMultiBlock;
import mariculture.core.blocks.base.TileMultiStorageTank;
import mariculture.core.network.Packet113MultiInit;
import mariculture.core.network.Packet117AirCompressorUpdate;
import mariculture.core.network.Packet118FluidUpdate;
import mariculture.core.network.Packets;
import mariculture.diving.TileAirCompressor;
import mariculture.factory.blocks.Tank;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class TileVat extends TileMultiStorageTank {
	public static int max_lrg = 24000;
	public static int max_sml = 6000;
	
	private int machineTick;
	
	public TileVat() {
		tank = new Tank(max_sml);
		inventory = new ItemStack[1];
		needsInit = true;
	}
	
	public boolean onTick(int i) {
		return machineTick % i == 0;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(master == null)
			updateSingle();
	}
		
	//Updating the Multi-Block form
	@Override
	public void updateMaster() {
		if(tank.getCapacity() != max_lrg)
				tank.setCapacity(max_lrg);

		machineTick++;
		if(!isInit() && !worldObj.isRemote) {
			//Init Master
			Packets.updateTile(this, 32, new Packet113MultiInit(xCoord, yCoord, zCoord, master.xCoord, master.yCoord, master.zCoord, facing).build());
			for(MultiPart slave: slaves) {
				TileEntity te = worldObj.getBlockTileEntity(slave.xCoord, slave.yCoord, slave.zCoord);
				if(te != null && te instanceof TileVat) {
					Packets.updateTile(te, 32, new Packet113MultiInit(te.xCoord, te.yCoord, te.zCoord, 
							master.xCoord, master.yCoord, master.zCoord, ((TileMultiBlock)te).facing).build());
				}
			}

			this.setInit(true);
		}
	}
	
	//The Code for when we are updating a single block!
	public void updateSingle() {
		machineTick++;
	}
	
	@Override
	public Class getTEClass() {
		return this.getClass();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
	}
//Tank Logic	
	@Override
	public FluidStack getFluid() {
		if(master != null) {
			return ((TileMultiStorageTank)worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord)).tank.getFluid();
		} else {
			return tank.getFluid();
		}
	}
	
	@Override
	public void setFluid(FluidStack fluid) {
		if(master != null) {
			((TileMultiStorageTank)worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord)).tank.setFluid(fluid);
		} else {
			tank.setFluid(fluid);
		}
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(master != null) {
			TileVat mstr = (TileVat) worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord);
			FluidStack ret = mstr.tank.drain(maxDrain, doDrain);
			if(doDrain)
				Packets.updateTile(mstr, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, mstr.getFluid()).build());
			
			return ret;
		} else {
			FluidStack ret = tank.drain(maxDrain, doDrain);
			if(doDrain)
				Packets.updateTile(this, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid()).build());
			
			return ret;
		}
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(master != null) {
			TileVat mstr = (TileVat) worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord);
			int ret = mstr.tank.fill(resource, doFill);
			if(doFill)
				Packets.updateTile(mstr, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, mstr.getFluid()).build());
			
			return ret;
		} else {
			int ret = tank.fill(resource, doFill);
			if(doFill)
				Packets.updateTile(this, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid()).build());
			
			return ret;
		}
	}
	
//Master Logic	
	@Override
	public void onBlockBreak() {
		if(master != null) {
			TileVat mstr = (TileVat) worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord);
			if(mstr != null) {
				mstr.tank.setCapacity(max_sml);
				if(mstr.tank.getFluidAmount() > max_sml)
					mstr.tank.setFluidAmount(max_sml);
			}
		}
		
		super.onBlockBreak();
	}
	
	@Override
	public boolean isPartnered(int x, int y, int z) {
		TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
		return tile instanceof TileVat?  ((TileVat)tile).master != null : false;
	}
	
	@Override
	public boolean isPart(int x, int y, int z) {
		return worldObj.getBlockTileEntity(x, y, z) instanceof TileVat && !isPartnered(x, y, z);
	}
	
	@Override
	public void onBlockPlaced() {
		onBlockPlaced(xCoord, yCoord, zCoord);
		Packets.updateTile(this, 32, getDescriptionPacket());
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
	
	public void onBlockPlaced(int x, int y, int z) {
		if(isPart(x, y, z) && isPart(x + 1, y, z) && isPart(x, y, z + 1) && isPart(x + 1, y, z + 1)) {
			MultiPart mstr = new MultiPart(x, y, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x + 1, y, z, ForgeDirection.WEST));
			parts.add(setAsSlave(mstr, x + 1, y, z + 1, ForgeDirection.NORTH));
			parts.add(setAsSlave(mstr, x, y, z + 1, ForgeDirection.EAST));
			setAsMaster(mstr, parts, ForgeDirection.SOUTH);
		}
		
		if(isPart(x - 1, y, z) && isPart(x, y, z) && isPart(x, y, z + 1) && isPart(x - 1, y, z + 1)) {
			MultiPart mstr = new MultiPart(x - 1, y, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.WEST));
			parts.add(setAsSlave(mstr, x - 1, y, z + 1, ForgeDirection.EAST));
			parts.add(setAsSlave(mstr, x, y, z + 1, ForgeDirection.NORTH));
			setAsMaster(mstr, parts, ForgeDirection.SOUTH);
		}
		
		if(isPart(x, y, z) && isPart(x - 1, y, z) && isPart(x - 1, y, z - 1) && isPart(x, y, z - 1)) {
			MultiPart mstr = new MultiPart(x - 1, y, z - 1);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.NORTH));
			parts.add(setAsSlave(mstr, x - 1, y, z, ForgeDirection.EAST));
			parts.add(setAsSlave(mstr, x, y, z - 1, ForgeDirection.WEST));
			setAsMaster(mstr, parts, ForgeDirection.SOUTH);
		}
		
		if(isPart(x, y, z) && isPart(x + 1, y, z) && isPart(x, y, z - 1) && isPart(x + 1, y, z - 1)) {
			MultiPart mstr = new MultiPart(x, y, z - 1);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.EAST));
			parts.add(setAsSlave(mstr, x + 1, y, z, ForgeDirection.NORTH));
			parts.add(setAsSlave(mstr, x + 1, y, z - 1, ForgeDirection.WEST));
			setAsMaster(mstr, parts, ForgeDirection.SOUTH);
		}
		
		Packets.updateTile(this, 32, getDescriptionPacket());
	}
}
