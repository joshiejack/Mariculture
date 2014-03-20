package mariculture.factory.blocks;

import mariculture.core.lib.Modules;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.TileEnergyHandler;

public class TileSponge extends TileEnergyHandler {
    boolean posXFound, negXFound, posZFound, negZFound, posYFound = false;
    int posX, negX, posZ, negZ, posY = 0;

    public TileSponge() {
        storage = new EnergyStorage(0, 5000, Integer.MAX_VALUE);
    }
    
    public boolean isBlockSponge(int x, int y, int z) {
    	if(Modules.isActive(Modules.worldplus))
    		return worldObj.getBlock(x, y, z) == Blocks.sponge;
    	return worldObj.getBlock(x, y, z) == Blocks.wool && worldObj.getBlockMetadata(x, y, z) == 4;
    }

    private int tick;
    public void updateCoords() {
        posXFound = false;
        negXFound = false;
        posZFound = false;
        negZFound = false;
        posYFound = false;
        for (int i = 0; i < 48; i++) {
            if(!posXFound) {
                if (isBlockSponge(xCoord + i, yCoord, zCoord)) {
                    posXFound = true;
                    posX = xCoord + i;
                }
            }

            if(!negXFound) {
                if (isBlockSponge(xCoord - i, yCoord, zCoord)) {
                    negXFound = true;
                    negX = xCoord - i;
                }
            }

            if(!posZFound) {
                if (isBlockSponge(xCoord, yCoord, zCoord + i)) {
                    posZFound = true;
                    posZ = zCoord + i;
                }
            }

            if(!negZFound) {
                if (isBlockSponge(xCoord, yCoord, zCoord - i)) {
                    negZFound = true;
                    negZ = zCoord - i;
                }
            }

            if(!posYFound) {
                if (isBlockSponge(xCoord, yCoord + i, zCoord)) {
                    posYFound = true;
                    posY = yCoord + i;
                }
            }
        }
    }

    public void clearWater() {
    	for (int x = negX; x <= posX; x++) {
        	for (int z = negZ; z <= posZ; z++) {
            	for (int y = yCoord; y <= posY; y++) {
                 	// Now that we are looping through each block
                    if (worldObj.getBlock(x, y, z).getMaterial().isLiquid() || worldObj.getBlock(x, y, z) == Blocks.sponge) {
                    	worldObj.setBlockToAir(x, y, z);
                    }
                }
        	}
    	}
    }

    @Override
    public void updateEntity() {
    	if(!worldObj.isRemote) {	
	        if(tick %50 == 0) {
	            updateCoords();
	        }
	
	        tick++;
	
	        if(tick %200 == 0) {
	            if(posXFound && negXFound && posZFound && negZFound && posYFound) {
	                int powerX = Math.max(posX, negX) - Math.min(posX, negX);
	                int powerZ = Math.max(posZ, negZ) - Math.min(posZ, negZ);
	                int powerY = Math.max(yCoord, posY) - Math.min(yCoord, posY);
	                int power = (powerX * powerZ * powerY) * 40;
	                this.storage.setCapacity(power);
	                if (storage.extractEnergy(power, true) >= power) {
	                    clearWater();
	                    storage.extractEnergy(power, false);
	                    storage.setCapacity(0);
	                }
	            } else {
	            	storage.setCapacity(0);
	            }
	        }
    	}
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        storage.readFromNBT(tagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        storage.writeToNBT(tagCompound);
    }
}