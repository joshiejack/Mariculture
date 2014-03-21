package mariculture.core.tile;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeIngotCasting;
import mariculture.core.lib.MetalRates;
import mariculture.core.network.Packets;
import mariculture.core.tile.base.TileStorageTank;
import mariculture.core.util.Tank;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class TileIngotCaster extends TileStorageTank implements ISidedInventory {
	public int machineTick;
	public boolean canWork;
	public int freezeTick;
	private EnumBiomeType biome;
	
	public TileIngotCaster() {
		inventory = new ItemStack[4];
		tank = new Tank(MetalRates.INGOT * 4);
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	public boolean onTick(int i) {
		return machineTick %i == 0;
	}
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			if(biome == null)
				biome = MaricultureHandlers.biomeType.getBiomeType(worldObj.getWorldChunkManager().getBiomeGenAt(xCoord, zCoord));
			
			machineTick++;
			
			if(onTick(30)) {
				canWork = canWork();
			}
			
			if(canWork) {
				freezeTick+=biome.getCoolingSpeed();
				if(freezeTick >= 800) {
					RecipeIngotCasting result = MaricultureHandlers.casting.getResult(tank.getFluid());
					if(result != null) {
						for(int i = 0; i < inventory.length; i++) {
							if(inventory[i] == null && tank.getFluidAmount() >= MetalRates.INGOT) {
								drain(ForgeDirection.UP, result.fluid.copy(), true);
								setInventorySlotContents(i, result.output.copy());
							}
						}
					}
					
					freezeTick = 0;
					canWork = canWork();
				}
			}
		}
	}
	
	public boolean canWork() {
		return hasRoom() && canFreeze();
	}
	
	public boolean canFreeze() {
		return MaricultureHandlers.casting.getResult(tank.getFluid()) != null;
	}
	
	public boolean hasRoom() {
		return inventory[0] == null || inventory[1] == null || inventory[2] == null || inventory[3] == null;
	}
	
	@Override
	public void markDirty() {
		super.markDirty();
		
		if(!worldObj.isRemote) {
			Packets.syncInventory(this, inventory);
		}
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
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		int amount =  tank.fill(resource, doFill);
        if (amount > 0 && doFill) {
        	canWork = canWork();
        	Packets.syncFluids(this, getFluid());
        }
        
        return amount;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack amount = tank.drain(maxDrain, doDrain);
        if (amount != null && doDrain) {
        	canWork = canWork();
        	Packets.syncFluids(this, getFluid());
        }
        return amount;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3 };
	}

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
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("FreezeTick", freezeTick);
	}
}
