package mariculture.factory.blocks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.blocks.TileTankMachine;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.MaricultureDamage;
import mariculture.core.lib.PacketIds;
import mariculture.factory.gui.ContainerFLUDDStand;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileFLUDDStand extends TileTankMachine implements ISidedInventory {
	public ForgeDirection orientation = ForgeDirection.UP;
	private int tick = 0;
	private int distanceDo = 6;
	private int damageAmount = 0;
	private int blockBreakChance = 0;
	private boolean ethereal = false;

	private Random rand = new Random();

	public TileFLUDDStand() {
		super.setInventorySize(5);
	}

	private void doSquirt(World world, int distance, ForgeDirection direction, int baseX, int baseY, int baseZ, int tick) {
		double x = baseX + direction.offsetX;
		double y = baseY + direction.offsetY;
		double z = baseZ + direction.offsetZ;
		float zPlus = 0F;
		float angleOfDecent = 1F / distance;

		if (direction == ForgeDirection.UP || direction == ForgeDirection.DOWN) {
			zPlus = 0.3F;
		}
		for (int count = 0; count < distance; count++) {
			List list = this.worldObj.getEntitiesWithinAABB(
					Entity.class,
					this.getBlockType().getCollisionBoundingBoxFromPool(this.worldObj,
							(int) (x + (direction.offsetX * count)), (int) (y + (direction.offsetY * count)),
							(int) (z + (direction.offsetZ * count))));

			if (count < distance - 1) {
				for (Object i : list) {
					if (!ethereal) {
						((Entity) i).addVelocity(this.orientation.offsetX * 0.15, this.orientation.offsetY * 0.042,
								this.orientation.offsetZ * 0.15);

						if (((Entity) i) instanceof EntityItem) {
							EntityItem item = (EntityItem) ((Entity) i);
							item.motionX = this.orientation.offsetX * 0.15;
							item.motionZ = this.orientation.offsetZ * 0.15;
							item.motionY = this.orientation.offsetY * 0.042;
						}
					} else {
						((Entity) i).addVelocity((-this.orientation.offsetX) * 0.15,
								(-this.orientation.offsetY) * 0.15, (-this.orientation.offsetZ) * 0.15);
					}
					if (direction == ForgeDirection.UP) {
						((Entity) i).fallDistance = 0F;
					}
					if (((Entity) i) instanceof EntityLivingBase) {
						if (this.damageAmount > 0) {
							((Entity) i).attackEntityFrom(MaricultureDamage.scald, this.damageAmount / 2);
						}

					}
				}
			}

			if (!this.worldObj.isRemote && count == 0) {
				for (Object i : list) {
					if (((Entity) i) instanceof EntityItem) {
						EntityItem item = (EntityItem) ((Entity) i);
						int itemX = (int) Math.ceil(item.posX);
						if ((this.xCoord + this.orientation.offsetX) + 1 == (int) Math.ceil(item.posX)) {
							if ((this.zCoord + this.orientation.offsetZ) + 1 == (int) Math.ceil(item.posZ)) {
								if ((this.yCoord + this.orientation.offsetY) + 1 == (int) Math.ceil(item.posY)) {
									ItemStack stack = item.getEntityItem();
									if (InventoryHelper.addToInventory(0, this.worldObj, this.xCoord, this.yCoord,
											this.zCoord, stack, null)) {
										item.setDead();
									}
								}

							}
						}
					}
				}
			}

			for (float half = -0.5F; half < 0.5F; half = half + 0.25F) {
				if (this.worldObj.isAirBlock((int) (x + (direction.offsetX * count)),
						(int) (y + (direction.offsetY * count)), (int) (z + (direction.offsetZ * count)))) {
					if (tick == 0) {
						this.worldObj.spawnParticle("cloud", x + (direction.offsetX * count) + 0.5F
								+ (half * direction.offsetX), y + (direction.offsetY * count) + 0.8F
								- (count * angleOfDecent) + (half * direction.offsetY), z + (direction.offsetZ * count)
								+ 0.5F + (half * direction.offsetZ) + zPlus, 0, 0, 0);
					}

					if (!this.worldObj.isAirBlock((int) (x + (direction.offsetX * (count + 1))),
							(int) (y + (direction.offsetY * (count + 1))),
							(int) (z + (direction.offsetZ * (count + 1))))) {
						this.worldObj.spawnParticle("splash", x + (direction.offsetX * count) + 0.5F, y
								+ (direction.offsetY * count) + 0.8F - (count * 0.1F), z + (direction.offsetZ * count)
								+ 0.5F + zPlus, 0, 0, 0);

						if (this.blockBreakChance > 0) {

							double strength = blockBreakChance * 0.5;
							int chance = ((45 - blockBreakChance) + 25) * 15;

							if (chance <= 1) {
								chance = 1;
							}

							if (rand.nextInt(chance) == 0) {
								int x2 = (int) (x + (direction.offsetX * (count + 1)));
								int y2 = (int) (y + (direction.offsetY * (count + 1)));
								int z2 = (int) (z + (direction.offsetZ * (count + 1)));

								if (Block.blocksList[world.getBlockId(x2, y2, z2)] != null) {

									Block block = Block.blocksList[world.getBlockId(x2, y2, z2)];
									float hardness = block.getBlockHardness(this.worldObj, x2, y2, z2);
									if (strength >= hardness && hardness >= 0) {
										this.worldObj.destroyBlock(x2, y2, z2, true);
									}
								}
							}
						}
					}
				} else {
					count = distance;
				}
			}
		}
	}

	private void processContainers() {
		ItemStack result = FluidHelper.getFluidResult(this, inventory[3], inventory[4]);
		if (result != null) {
			decrStackSize(3, 1);
			if (this.inventory[4] == null) {
				this.inventory[4] = result.copy();
			} else if (this.inventory[4].itemID == result.itemID) {
				++this.inventory[4].stackSize;
			}
		}
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		tick++;

		if (tick > 20) {
			tick = 0;
			processContainers();
		}

		if (this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord) && tank.getFluidAmount() > 0) {

			doSquirt(this.worldObj, distanceDo, orientation, this.xCoord, this.yCoord, this.zCoord, tick % 4);

			if (tick == 10) {
				this.drain(new FluidStack(tank.getFluidID(), distanceDo/6), true);

				if (tank.getFluidAmount() == 0) {
					updateFLUDDStats();
				}
			}
		}
	}

	@Override
	protected int getMaxCalculation(int count) {
		return ((FluidContainerRegistry.BUCKET_VOLUME * 20) + (count * (FluidContainerRegistry.BUCKET_VOLUME * 4)));
	}

	@Override
	protected void updateUpgrades() {
		super.updateUpgrades();
		// Max Liquid Volume
		int purityCount = MaricultureHandlers.upgrades.getData("purity", this);
		int heatAmount = MaricultureHandlers.upgrades.getData("temp", this);

		this.ethereal = MaricultureHandlers.upgrades.hasUpgrade("ethereal", this);
		
		if(heatAmount > 0) {
			blockBreakChance = 0;
			damageAmount = (heatAmount * 2);
		} else if(heatAmount < 0) {
			damageAmount = 0;
			blockBreakChance = -heatAmount;
			if(blockBreakChance <= 0) {
				blockBreakChance = 1;
			}
		} else {
			damageAmount = 0;
			blockBreakChance = 0;
		}

		distanceDo = 6 + (purityCount * 5);
		if (distanceDo <= 0) {
			distanceDo = 1;
		}

		updateFLUDDStats();
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.orientation = ForgeDirection.getOrientation(tagCompound.getInteger("Orientation"));
		this.distanceDo = tagCompound.getInteger("distanceDo");
		this.damageAmount = tagCompound.getInteger("damageAmount");
		this.blockBreakChance = tagCompound.getInteger("blockBreakChance");
		this.ethereal = tagCompound.getBoolean("ethereal");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("Orientation", orientation.ordinal());
		tagCompound.setInteger("distanceDo", this.distanceDo);
		tagCompound.setInteger("damageAmount", this.damageAmount);
		tagCompound.setInteger("blockBreakChance", this.blockBreakChance);
		tagCompound.setBoolean("ethereal", this.ethereal);
	}
	
	public void updateFLUDDStats() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream os = new DataOutputStream(bos);
		try {
			os.writeInt(PacketIds.FLUDD_STAND);
			os.writeInt(xCoord);
			os.writeInt(yCoord);
			os.writeInt(zCoord);
			os.writeInt(tank.getFluidAmount());
			os.writeInt(orientation.ordinal());
			os.writeInt(distanceDo);
			os.writeInt(tank.getCapacity());
			os.writeInt(damageAmount);
			os.writeInt(blockBreakChance);
			os.writeBoolean(ethereal);
			os.writeInt(tank.getFluidID());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		final Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 25, worldObj.provider.dimensionId, packet);
	}

	public static void handleLiquidQtyUpdate(Packet250CustomPayload packet, World world) {
		final DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		int id;
		int x;
		int y;
		int z;
		int liquidQty;
		int orientation;
		int distance;
		int max;
		int damage;
		int blockBreak;
		boolean ethereal;
		int liquidId;

		try {
			id = inputStream.readInt();
			x = inputStream.readInt();
			y = inputStream.readInt();
			z = inputStream.readInt();
			liquidQty = inputStream.readInt();
			orientation = inputStream.readInt();
			distance = inputStream.readInt();
			max = inputStream.readInt();
			damage = inputStream.readInt();
			blockBreak = inputStream.readInt();
			ethereal = inputStream.readBoolean();
			liquidId = inputStream.readInt();

		} catch (final IOException e) {
			e.printStackTrace(System.err);
			return;
		}

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileFLUDDStand) {
			((TileFLUDDStand) tile).damageAmount = damage;
			((TileFLUDDStand) tile).blockBreakChance = blockBreak;
			((TileFLUDDStand) tile).distanceDo = distance;
			((TileFLUDDStand) tile).tank.setCapacity(max);
			((TileFLUDDStand) tile).tank.setFluidAmount(liquidQty);
			((TileFLUDDStand) tile).orientation = ForgeDirection.getOrientation(orientation);
			((TileFLUDDStand) tile).ethereal = ethereal;
			((TileFLUDDStand) tile).tank.setFluidID(liquidId);
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		this.writeToNBT(tagCompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, tagCompound);
	}

	@Override
	public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	}

	public void getGUINetworkData(final int i, final int j) {
		super.getGUINetworkData(i, j);
	}

	public void sendGUINetworkData(ContainerFLUDDStand container, EntityPlayer player) {
		super.sendGUINetworkData(container, player);
	}

	/** Upgrade Stuff **/
	@Override
	public ItemStack[] getUpgrades() {
		return new ItemStack[] { inventory[0], inventory[1], inventory[2] };
	}

	private static final int[] slots_top = new int[] { 3 };
	private static final int[] slots_bottom = new int[] { 4 };
	private static final int[] slots_sides = new int[] { 3, 4 };

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slots_bottom : (side == 1 ? slots_top : slots_sides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 4;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot == 3 && FluidHelper.isFluidOrEmpty(stack);
	}
}
