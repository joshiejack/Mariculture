package mariculture.factory.blocks;

import java.util.Random;

import mariculture.api.core.IBlacklisted;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.blocks.TileTankMachine;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.FluidInventoryHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.util.PacketIntegerUpdate;
import mariculture.factory.gui.ContainerSluice;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidHandler;

public class TileSluice extends TileTankMachine implements IBlacklisted, ISidedInventory {
	private short facing;
	private short cycles;
	private int tick = 0;
	private int height;
	private int transfer;
	private int speedModifier;

	Random rand = new Random();

	public TileSluice() {
		super.setInventorySize(6);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.facing = tagCompound.getShort("Facing");
		this.cycles = tagCompound.getShort("Cycles");
		this.transfer = tagCompound.getInteger("Transfer");
		this.speedModifier = tagCompound.getInteger("Speed");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("Facing", this.facing);
		tagCompound.setShort("Cycles", this.cycles);
		tagCompound.setInteger("Transfer", this.transfer);
		tagCompound.setInteger("Speed", this.speedModifier);
	}

	@Override
	protected int getMaxCalculation(int count) {
		return ((FluidContainerRegistry.BUCKET_VOLUME / 4) + (count * 64));
	}

	private void moveFluids() {
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		int x2 = x;
		int y2 = y;
		int z2 = z;
		ForgeDirection direction = ForgeDirection.UP;

		switch (facing) {
		case 0:
			z++;
			z2--;
			direction = ForgeDirection.SOUTH;
			break;
		case 1:
			z--;
			z2++;
			direction = ForgeDirection.NORTH;
			break;
		case 2:
			x++;
			x2--;
			direction = ForgeDirection.EAST;
			break;
		case 3:
			x--;
			x2++;
			direction = ForgeDirection.WEST;
			break;
		}

		if (this.worldObj.getBlockTileEntity(x, y, z) != null
				&& this.worldObj.getBlockTileEntity(x, y, z) instanceof IFluidHandler) {
			pullFromTank(direction, this.worldObj, (IFluidHandler) this.worldObj.getBlockTileEntity(x, y, z), x2, y2,
					z2);
		}

		if (this.worldObj.getBlockTileEntity(x2, y2, z2) != null
				&& this.worldObj.getBlockTileEntity(x2, y2, z2) instanceof IFluidHandler) {
			if(!pushToTank(direction, this.worldObj, (IFluidHandler) this.worldObj.getBlockTileEntity(x2, y2, z2), x, y, z)) {
				if(height < 2) {
					moveForward(this.worldObj, x2, y2, z2, x, y, z);
				}
			}
		} else if(height < 2) {
			moveForward(this.worldObj, x2, y2, z2, x, y, z);
		}
	}

	private void moveForward(World world, int x2, int y2, int z2, int x, int y, int z) {
		if(world.isAirBlock(x2, y2, z2)) {
			int id = world.getBlockId(x, y, z);
			if(Block.blocksList[id] != null) {
				if(Block.blocksList[id] instanceof IFluidBlock || Block.blocksList[id] instanceof BlockFluid) {
					if (Block.blocksList[id] instanceof BlockFluidClassic) {
						BlockFluidClassic block = (BlockFluidClassic) Block.blocksList[id];
						if(block.drain(world, x, y, z, false) == null) {
							return;
						}
					}
					
					if(Block.blocksList[id] instanceof BlockFluid) {
						if(world.getBlockMetadata(x, y, z) != 0) {
							return;
						}
					}
					
					world.setBlock(x2, y2, z2, world.getBlockId(x, y, z), world.getBlockMetadata(x, y, z), 2);
					world.setBlockToAir(x, y, z);
				}
			}
		}
	}

	private void pullFromTank(ForgeDirection direction, World world, IFluidHandler tank, int x, int y, int z) {
		FluidTankInfo[] info = tank.getTankInfo(direction);
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i] != null) {
					if (info[i].fluid != null) {
						Fluid fluid = FluidRegistry.getFluid(info[i].fluid.fluidID);

						if(fluid == null) {
							return;
						}
						
						if (fluid.getBlockID() == Core.highPressureWaterBlock.blockID) {
							return;
						}

						if (fluid.canBePlacedInWorld()) {
							int drain = FluidHelper.getRequiredVolumeForBlock(fluid);
							if (tank.drain(direction, drain, false) == null) {
								return;
							}
							if (tank.drain(direction, drain, false).amount == drain) {
								int id = fluid.getBlockID();
								if (Block.blocksList[id] != null) {
									Block block = Block.blocksList[id];
									if (block instanceof BlockFluidFinite) {
										if (world.isAirBlock(x, y, z)) {
											world.setBlock(x, y, z, id, 0, 2);
										} else {
											int meta = world.getBlockMetadata(x, y, z) + 1;
											if (meta < 7 && world.getBlockId(x, y, z) == id) {
												world.setBlockMetadataWithNotify(x, y, z, meta, 2);
											} else {
												return;
											}
										}

										tank.drain(direction, new FluidStack(fluid.getID(), drain), true);
									} else if (world.isAirBlock(x, y, z)) {
										world.setBlock(x, y, z, id);
										tank.drain(direction, new FluidStack(fluid.getID(), drain), true);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean pushToTank(ForgeDirection direction, World world, IFluidHandler tank, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		if (Block.blocksList[id] != null && (Block.blocksList[id] instanceof IFluidBlock || Block.blocksList[id] instanceof BlockFluid)) {
			Fluid fluid = null;
			IFluidBlock block = null;

			if (id > 20) {
				block = (IFluidBlock) Block.blocksList[id];
				fluid = block.getFluid();
			} else {
				if (id == Block.waterStill.blockID) {
					fluid = FluidRegistry.WATER;
				} else {
					fluid = FluidRegistry.LAVA;
				}
			}
			
			if(Block.blocksList[id] instanceof BlockFluid) {
				if(world.getBlockMetadata(x, y, z) != 0) {
					return false;
				}
			}
			
			if(block != null) {
				if(block instanceof BlockFluidClassic) {
					FluidStack fill = block.drain(world, x, y, z, false);
					if(fill != null) {
						int id2 = fluid.getID();
						if(id2 == Core.highPressureWater.getID()) {
							id2 = FluidRegistry.WATER.getID();
						}
						
						if (tank.fill(direction, new FluidStack(id2, fill.amount), false) >= fill.amount) {
							tank.fill(direction, new FluidStack(id2, fill.amount), true);
							block.drain(world, x, y, z, true);
							return true;
						}
					}
					
					return false;
				}
			}

			int fill = FluidHelper.getRequiredVolumeForBlock(fluid);
			if (tank.fill(direction, new FluidStack(fluid.getID(), fill), false) == fill) {
				tank.fill(direction, new FluidStack(fluid.getID(), fill), true);
				if (block != null && block instanceof BlockFluidFinite) {
					int meta = world.getBlockMetadata(x, y, z) - 1;
					if (meta >= 0) {
						world.setBlockMetadataWithNotify(x, y, z, meta, 2);
					}
				}

				world.setBlockToAir(x, y, z);
				return true;
			}
		}
		
		return false;
	}

	private boolean waterIsThere() {
		boolean result;
		int x = (facing == 2) ? this.xCoord + 1 : (facing == 3) ? this.xCoord - 1 : this.xCoord;
		int z = (facing == 0) ? this.zCoord + 1 : (facing == 1) ? this.zCoord - 1 : this.zCoord;

		int x2 = (facing == 2) ? this.xCoord + 1 : (facing == 3) ? this.xCoord - 1 : this.xCoord;
		int z2 = (facing == 0) ? this.zCoord + 1 : (facing == 1) ? this.zCoord - 1 : this.zCoord;
		
		return (BlockHelper.isWater(worldObj, x, yCoord, z) && BlockHelper.isWater(worldObj, x2, yCoord, z2));
	}

	public int getHeight() {
		int x = (facing == 2) ? this.xCoord + 1 : (facing == 3) ? this.xCoord - 1 : this.xCoord;
		int y = this.yCoord;
		int z = (facing == 0) ? this.zCoord + 1 : (facing == 1) ? this.zCoord - 1 : this.zCoord;
		int topY = y;
		for (topY = y; worldObj.getBlockMaterial(x, topY, z) == Material.water; topY++) {
			;
		}

		return topY - y;
	}

	@Override
	protected void updateUpgrades() {
		super.updateUpgrades();
		int purityCount = MaricultureHandlers.upgrades.getData("purity", this);
		int heatCount = MaricultureHandlers.upgrades.getData("temp", this);
		this.transfer = (purityCount + 1) * 75;
		this.speedModifier = ((heatCount * 2) * 500);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!this.worldObj.isRemote) {
			if (tick % 32 == 0) {
				updatePlacedBlock();
				processContainers();
			}

			if (!this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)) {
				tick++;
				if(tick %16 == 0) {
					transfer();
				}
				
				if (tick % 32 == 0) {
					this.height = getHeight();
					if(cycles == 0) {
						moveFluids();
					}
				}

				if (canWork() && tick % 64 == 0) {
					if (waterIsThere()) {
						fillSluice();
						if (cycles >= 250) {
							cycles = 0;
							decrStackSize(0, 1);
						}
					}
				}
			}
		}
	}

	private void processContainers() {
		ItemStack result = FluidHelper.getFluidResult(this, inventory[1], inventory[2]);
		if (result != null) {
			decrStackSize(1, 1);
			if (this.inventory[2] == null) {
				this.inventory[2] = result.copy();
			} else if (this.inventory[2].itemID == result.itemID) {
				++this.inventory[2].stackSize;
			}
		}
	}

	private boolean canWork() {
		if (getStackInSlot(0) != null) {
			return getStackInSlot(0).itemID == Core.craftingItem.itemID
					&& getStackInSlot(0).getItemDamage() == CraftingMeta.WHEEL;
		}

		return false;
	}

	private void transfer() {
		if (facing == 2 || facing == 3) {
			if (rand.nextInt(2) == 0) {
				FluidInventoryHelper.transferTo(this.xCoord, this.yCoord, this.zCoord - 1, this);
			} else {
				FluidInventoryHelper.transferTo(this.xCoord, this.yCoord, this.zCoord + 1, this);
			}
		}

		if (facing == 0 || facing == 1) {
			if (rand.nextInt(2) == 0) {
				FluidInventoryHelper.transferTo(this.xCoord - 1, this.yCoord, this.zCoord, this);
			} else {
				FluidInventoryHelper.transferTo(this.xCoord + 1, this.yCoord, this.zCoord, this);
			}
		}
	}
	
	@Override
	public int getTransferRate() {
		return this.transfer;
	}

	private void fillSluice() {
		float fill = 1F;
		for (int i = 0; i < height; i++) {
			fill += (0.0845F * i);
		}

		fill *= 1 + (speedModifier / 1000);

		if (fill(ForgeDirection.UP, new FluidStack(Core.highPressureWater.getID(), (int) fill), false) >= (int) fill) {
			fill(ForgeDirection.UP, new FluidStack(Core.highPressureWater.getID(), (int) fill), true);
			this.cycles++;
		} else if (fill(ForgeDirection.UP, new FluidStack(Core.highPressureWater.getID(), 1), false) >= 1) {
			fill(ForgeDirection.UP, new FluidStack(Core.highPressureWater.getID(), 1), true);
			this.cycles++;
		}
	}

	private void updatePlacedBlock() {
		int x = (facing == 2) ? this.xCoord - 1 : (facing == 3) ? this.xCoord + 1 : this.xCoord;
		int y = this.yCoord;
		int z = (facing == 0) ? this.zCoord - 1 : (facing == 1) ? this.zCoord + 1 : this.zCoord;
		if (this.worldObj.getBlockId(x, y, z) == Core.highPressureWaterBlock.blockID) {
			if (height <= 1 || worldObj.isBlockIndirectlyGettingPowered(x, y, z)) {
				worldObj.setBlockToAir(x, y, z);
			} else if (height < 10) {
				worldObj.setBlock(x, y, z, Block.waterStill.blockID);
			}
		} else if (worldObj.isAirBlock(x, y, z) && waterIsThere()) {
			if (height >= 10) {
				worldObj.setBlock(x, y, z, Core.highPressureWaterBlock.blockID);
			} else {
				worldObj.setBlock(x, y, z, Block.waterStill.blockID);
			}
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound tagCompound = new NBTTagCompound();
		this.writeToNBT(tagCompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, tagCompound);
	}

	private int getTopWaterBlock(int x, int y, int z) {
		int i = 0;
		while (isWater(this.worldObj.getBlockId(x, y + i, z))) {
			if (this.worldObj.getBlockId(x, y + i, z) == Core.highPressureWaterBlock.blockID) {
				i++;
			}
		}

		return ((y + i) - 1);
	}

	@Override
	public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	}

	public void getGUINetworkData(int i, int j) {
		super.getGUINetworkData(i, j);

		switch (i) {
		case 3:
			height = j;
			break;
		}
	}

	public void sendGUINetworkData(ContainerSluice container, EntityPlayer player) {
		super.sendGUINetworkData(container, player);
		PacketIntegerUpdate.send(container, 3, this.height, player);
	}

	public int getPressure() {
		int x = this.xCoord;
		final int y = this.yCoord;
		int z = this.zCoord;

		switch (facing) {
		case 0:
			z++;
			break;
		case 1:
			z--;
			break;
		case 2:
			x++;
			break;
		case 3:
			x--;
			break;
		}

		return ((getTopWaterBlock(x, y, z) - y));
	}

	private boolean isWater(int blockId) {
		return (blockId == Block.waterStill.blockID) ? true : false;
	}

	public int getFacing() {
		return this.facing;
	}

	public void setFacing(int i) {
		this.facing = (short) i;
	}

	@Override
	public ItemStack[] getUpgrades() {
		return new ItemStack[] { inventory[3], inventory[4], inventory[5] };
	}

	@Override
	public boolean isBlacklisted(World world, int x, int y, int z) {
		return true;
	}
	
	private static final int[] slots_top = new int[] { 0, 1 };
	private static final int[] slots_bottom = new int[] { 0, 2 };
	private static final int[] slots_sides = new int[] { 0 };

	@Override
	public int[] getAccessibleSlotsFromSide(final int side) {
		return side == 0 ? slots_bottom : (side == 1 ? slots_top : slots_sides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 2;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) {
			return stack.itemID == Core.craftingItem.itemID && stack.getItemDamage() == CraftingMeta.WHEEL;
		}
		
		if(slot == 1) {
			return FluidHelper.isFluidOrEmpty(stack);
		}

		return false;
	}
}
