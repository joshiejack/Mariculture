package mariculture.factory.blocks;

import java.util.Random;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.TileEnergyHandler;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

public class TileSponge extends TileEnergyHandler {
    private int required;
    boolean posXFound, negXFound, posZFound, negZFound, posYFound = false;
    int posX, negX, posZ, negZ, posY = 0;
    Random rand = new Random();

    public TileSponge() {
        storage = new EnergyStorage(0, 5000, Integer.MAX_VALUE);
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
                if (worldObj.getBlockId(xCoord + i, yCoord, zCoord) == Block.sponge.blockID) {
                    posXFound = true;
                    posX = xCoord + i;
                }
            }

            if(!negXFound) {
                if (worldObj.getBlockId(xCoord - i, yCoord, zCoord) == Block.sponge.blockID) {
                    negXFound = true;
                    negX = xCoord - i;
                }
            }

            if(!posZFound) {
                if (worldObj.getBlockId(xCoord, yCoord, zCoord + i) == Block.sponge.blockID) {
                    posZFound = true;
                    posZ = zCoord + i;
                }
            }

            if(!negZFound) {
                if (worldObj.getBlockId(xCoord, yCoord, zCoord - i) == Block.sponge.blockID) {
                    negZFound = true;
                    negZ = zCoord - i;
                }
            }

            if(!posYFound) {
                if (worldObj.getBlockId(xCoord, yCoord + i, zCoord) == Block.sponge.blockID) {
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
                    if (worldObj.getBlockMaterial(x, y, z).isLiquid() || worldObj.getBlockId(x, y, z) == Block.sponge.blockID) {
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
	                    this.storage.setCapacity(0);
	                }
	            } else {
	            	this.storage.setCapacity(0);
	            }
	        }
    	}
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
    }
}