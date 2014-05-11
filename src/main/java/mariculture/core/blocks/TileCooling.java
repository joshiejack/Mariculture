package mariculture.core.blocks;

import mariculture.api.core.Environment.Temperature;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting;
import mariculture.core.blocks.base.TileStorageTank;
import mariculture.core.network.Packet118FluidUpdate;
import mariculture.core.network.Packet120ItemSync;
import mariculture.core.network.Packets;
import mariculture.core.util.Tank;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public abstract class TileCooling extends TileStorageTank implements ISidedInventory {
	public boolean canWork;
	public int freezeTick;
	private int cooling;
	
	public TileCooling() {
		inventory = new ItemStack[getInventorySize()];
		tank = new Tank(getTankSize());
	}
	
	public abstract int getInventorySize();
	public abstract int getTankSize();
	public abstract int getTime();
	public abstract RecipeCasting getResult();
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			//Setup the cooling 
			if(cooling <= 0) {
				cooling = Math.max(1, Temperature.getCoolingSpeed(MaricultureHandlers.environment.getBiomeTemperature(worldObj, xCoord, yCoord, zCoord)));
			}
			
			if(canWork) {
				freezeTick+=cooling;
				if(freezeTick >= getTime()) {
					RecipeCasting result = getResult();
					if(result != null) {
						for(int i = 0; i < inventory.length; i++) {
							if(inventory[i] == null && tank.getFluidAmount() >= result.fluid.amount) {
								drain(ForgeDirection.UP, result.fluid.amount, true);
								setInventorySlotContents(i, result.output.copy());
								canWork = canWork();
								if(!canWork) break;
							}
						}
					}
					
					freezeTick = 0;
				}
			}
		}
	}
	
	public boolean canWork() {
		return !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && hasRoom() && canFreeze();
	}
	
	public boolean canFreeze() {
		return getResult() != null;
	}
	
	public boolean hasRoom() {
		for(int i = 0; i < inventory.length; i++) {
			if(inventory[i] == null) return true;
		}
		
		return false;
	}
	
	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		
		if(!worldObj.isRemote) {
			Packets.updateTile(this, new Packet120ItemSync(xCoord, yCoord, zCoord, inventory).build());
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		int amount =  tank.fill(resource, doFill);
        if (amount > 0 && doFill) {
        	Packets.updateTile(this, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid()).build());
        	freezeTick = 0;
        	canWork = canWork();
        }
        return amount;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack amount = tank.drain(maxDrain, doDrain);
        if (amount != null && doDrain) {
        	Packets.updateTile(this, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid()).build());
        	canWork = canWork();
        }
        return amount;
	}

	@Override
	public abstract int[] getAccessibleSlotsFromSide(int side);

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		freezeTick = nbt.getInteger("FreezeTick");
		cooling = nbt.getInteger("CoolingSpeed");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("FreezeTick", freezeTick);
		nbt.setInteger("CoolingSpeed", cooling);
	}
}
