package mariculture.diving;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

import mariculture.core.blocks.TileDoubleBlock;
import mariculture.core.lib.PacketIds;
import mariculture.core.lib.PowerStages;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileAirCompressorPower extends TileDoubleBlock {
	private static boolean isPumping;
	public int currentCharge;
	private int storageCharge;
	private int MAX_STORAGE = 50000;
	private int ACTIVE_CHARGE = 100;
	public boolean animate;
	private double wheelAngle = 0;
	private Random rand = new Random();

	public TileAirCompressorPower() {
		currentCharge = 0;
		storageCharge = 0;
		animate = false;
	}

	public double getWheelAngle() {
		return wheelAngle;
	}

	public int getStage() {
		TileAirCompressorPower compressor = (TileAirCompressorPower) this.getMasterBlock();

		if (compressor != null) {
			if (compressor.currentCharge > 0 && compressor.currentCharge <= 200) {
				return PowerStages.RED;
			}

			if (compressor.currentCharge > 200 && compressor.currentCharge <= 500) {
				return PowerStages.ORANGE;
			}

			if (compressor.currentCharge > 500 && compressor.currentCharge <= 850) {
				return PowerStages.YELLOW;
			}

			if (compressor.currentCharge > 850) {
				return PowerStages.GREEN;
			}
		}

		return PowerStages.EMPTY;
	}

	private boolean canBePumping() {
		if (this.getMasterBlock() != null && this.getOtherBlock() != null) {
			final TileAirCompressorPower here = (TileAirCompressorPower) this.getMasterBlock();
			final TileAirCompressorPower there = (TileAirCompressorPower) this.getOtherBlock();

			if (here != null && there != null) {
				if (this.worldObj.getBlockTileEntity(here.xCoord, here.yCoord - 1, here.zCoord) instanceof TileAirCompressor
						&& this.worldObj.getBlockTileEntity(there.xCoord, there.yCoord - 1, there.zCoord) instanceof TileAirCompressor) {
					final TileAirCompressor compressorHere = (TileAirCompressor) this.worldObj.getBlockTileEntity(here.xCoord,
							here.yCoord - 1, here.zCoord);
					final TileAirCompressor compressorThere = (TileAirCompressor) this.worldObj.getBlockTileEntity(there.xCoord,
							there.yCoord - 1, there.zCoord);
					if (compressorHere != null && compressorThere != null) {
						if (compressorHere.currentAir < compressorHere.MAX_AIR) {
							if (this.currentCharge >= ACTIVE_CHARGE) {
								return true;
							}
						}
					}
				}
			}

		}

		return false;
	}

	private void sendAnimatePacket() {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		final DataOutputStream os = new DataOutputStream(bos);
		try {
			os.writeInt(PacketIds.AIR_PRESS_POWER_UPDATE_ANIMATE);
			os.writeInt(this.xCoord);
			os.writeInt(this.yCoord);
			os.writeInt(this.zCoord);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		final Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, 30, this.worldObj.provider.dimensionId,
				packet);
	}

	@Override
	public void updateEntity() {
		if (this.getMasterBlock() != null && this.getOtherBlock() != null && this.getMasterBlock() == this) {
			if (animate) {
				this.wheelAngle = this.wheelAngle + 0.1;

				if (this.wheelAngle > 6.2198) {
					this.wheelAngle = 0;
					animate = false;
				}

				this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
			}

			if (!this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)
					&& !this.worldObj.isBlockIndirectlyGettingPowered(this.getOtherBlock().xCoord, this.getOtherBlock().yCoord,
							this.getOtherBlock().zCoord)) {
				if (!this.worldObj.isRemote) {
					if (canBePumping()) {
						if (rand.nextInt(60) == 1) {
							this.currentCharge--;

							this.sendAnimatePacket();
						}
					} else if (this.storageCharge >= 100) {
						this.currentCharge = this.currentCharge + 100;
						this.storageCharge = this.storageCharge - 100;

						if (this.storageCharge > 500) {
							this.currentCharge = this.currentCharge + 500;
							this.storageCharge = this.storageCharge - 500;

							if (this.storageCharge > 1000) {
								this.currentCharge = this.currentCharge + 1000;
								this.storageCharge = this.storageCharge - 1000;
							}
						}
					}
				}

				final boolean check = isPumping;
				isPumping = isPumping();
				if (isPumping != check) {
					this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
				}
			}
		}
	}

	public boolean isPumping() {
		return canBePumping();
	}

	@Override
	public void readFromNBT(final NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		storageCharge = tagCompound.getInteger("storageCharge");
		currentCharge = tagCompound.getInteger("currentCharge");
	}

	@Override
	public void writeToNBT(final NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setInteger("storageCharge", this.storageCharge);
		tagCompound.setInteger("currentCharge", this.currentCharge);
	}

	public void addPower(final int i) {
		this.storageCharge = this.storageCharge + i;
		if (this.storageCharge > MAX_STORAGE) {
			this.storageCharge = MAX_STORAGE;
		}
	}
}
