package mariculture.diving;

import mariculture.core.blocks.core.TileMulti;
import mariculture.core.lib.PowerStages;
import net.minecraft.nbt.NBTTagCompound;

public class TileAirCompressor extends TileMulti {

	final int MAX_AIR = 480;
	public int currentAir;
	private boolean isCompressing;
	private int compressingTime;
	private int currentCompressingTime;
	private int compressingLength = 360;

	public TileAirCompressor() {
		compressingTime = 0;
		currentCompressingTime = 0;
		currentAir = 0;
	}


	private boolean canCompress() {
		//TODO: REDO COMPRESSED CAN
		/*
		if (this.getMasterBlock() != null && this.getOtherBlock() != null) {
			TileAirCompressor here = (TileAirCompressor) this.getMasterBlock();
			TileAirCompressor there = (TileAirCompressor) this.getOtherBlock();

			if (here != null && there != null) {
				if (this.worldObj.getBlockTileEntity(here.xCoord, here.yCoord + 1, here.zCoord) instanceof TileAirCompressorPower
						&& this.worldObj.getBlockTileEntity(there.xCoord, there.yCoord + 1, there.zCoord) instanceof TileAirCompressorPower) {
					TileAirCompressorPower powerHere = (TileAirCompressorPower) this.worldObj.getBlockTileEntity(
							here.xCoord, here.yCoord + 1, here.zCoord);
					TileAirCompressorPower powerThere = (TileAirCompressorPower) this.worldObj.getBlockTileEntity(
							there.xCoord, there.yCoord + 1, there.zCoord);
					if (powerHere != null && powerThere != null) {
						if (powerHere.isPumping()) {
							if (this.currentAir < this.MAX_AIR) {
								return true;

							}
						}
					}
				}
			}

		} */

		return false;
	}

	public int getBarLength() {
		return (this.currentAir * 7) / this.MAX_AIR;
	}
	
	@Override
	public void updateMaster() {
		if (this.currentAir < this.MAX_AIR) {
			boolean var1 = this.currentCompressingTime > 0;
			boolean var2 = false;

			if (this.compressingTime > 0) {
				--this.compressingTime;
			}

			if (!this.worldObj.isRemote) {
				if (compressingTime == 0) {
					if (canCompress()) {
						compressingTime = compressingLength;
					}
				}

				if (isCompressing()) {
					++currentCompressingTime;

					if (this.currentCompressingTime >= compressingLength) {
						this.currentCompressingTime = 0;
						this.compressingTime = 0;

						if (this.currentAir < this.MAX_AIR) {
							this.currentAir++;

							TileAirCompressorPower power = (TileAirCompressorPower) this.worldObj.getBlockTileEntity(
									this.xCoord, this.yCoord + 1, this.zCoord);
							//TileAirCompressorPower masterPower = (TileAirCompressorPower) power.getMasterBlock();

							/*if (masterPower != null) {
								switch (masterPower.getStage()) {
								case PowerStages.ORANGE:
									this.currentAir++;
									break;
								case PowerStages.YELLOW:
									this.currentAir = this.currentAir + 2;
									break;
								case PowerStages.GREEN:
									this.currentAir = this.currentAir + 3;
									break;
								}
							} */

							if (this.currentAir > this.MAX_AIR) {
								this.currentAir = this.MAX_AIR;
							}
						}
					}
				}

				else {
					this.currentCompressingTime = 0;

				}

				if (var1 != this.currentCompressingTime > 0) {
					var2 = true;
				}
			}

			boolean check = isCompressing;
			isCompressing = isCompressing();
			if (isCompressing != check) {
				this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
			}
		}
	}

	@Override
	public void readFromNBT(final NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		compressingTime = tagCompound.getInteger("compressTime");
		currentCompressingTime = tagCompound.getInteger("currentCompressTime");
		currentAir = tagCompound.getInteger("currentAir");
	}

	@Override
	public void writeToNBT(final NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setInteger("compressTime", this.compressingTime);
		tagCompound.setInteger("currentCompressTime", this.currentCompressingTime);
		tagCompound.setInteger("currentAir", this.currentAir);
	}

	public boolean isCompressing() {
		return compressingTime > 0;
	}

}
