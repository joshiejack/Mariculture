package mariculture.core.blocks;

import java.util.ArrayList;
import java.util.Random;

import mariculture.Mariculture;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RecipeSifter;
import mariculture.core.Core;
import mariculture.core.blocks.base.BlockFunctional;
import mariculture.core.helpers.DirectionHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.helpers.cofh.ItemHelper;
import mariculture.core.lib.GuiIds;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.MaricultureDamage;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.network.Packets;
import mariculture.core.tile.TileAirPump;
import mariculture.core.tile.TileAirPump.Type;
import mariculture.core.tile.TileAnvil;
import mariculture.core.tile.TileIngotCaster;
import mariculture.core.util.Rand;
import mariculture.factory.Factory;
import mariculture.factory.items.ItemArmorFLUDD;
import mariculture.factory.tile.TileFLUDDStand;
import mariculture.factory.tile.TileGeyser;
import mariculture.factory.tile.TileTurbineBase;
import mariculture.factory.tile.TileTurbineGas;
import mariculture.factory.tile.TileTurbineHand;
import mariculture.factory.tile.TileTurbineWater;
import mariculture.fishery.tile.TileFeeder;
import mariculture.fishery.tile.TileSift;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRenderedMachine extends BlockFunctional {
	public BlockRenderedMachine() {
		super(Material.piston);
	}
	
	@Override
	public String getToolType(int meta) {
		if(meta == MachineRenderedMeta.SIFTER) return "axe";
		if(meta == MachineRenderedMeta.TURBINE_HAND) return "axe";
		return (meta == MachineRenderedMeta.FISH_FEEDER)? null: "pickaxe";
	}

	@Override
	public int getToolLevel(int meta) {
		switch(meta) {
			case MachineRenderedMeta.AIR_PUMP:		return 1;
			case MachineRenderedMeta.GEYSER:		return 1;
			case MachineRenderedMeta.TURBINE_GAS:	return 2;
			case MachineRenderedMeta.TURBINE_WATER:	return 1;
			default:								return 0;
		}
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch (world.getBlockMetadata(x, y, z)) {
			case MachineRenderedMeta.AIR_PUMP: 		return 4F;
			case MachineRenderedMeta.FISH_FEEDER: 	return 0.5F;
			case MachineRenderedMeta.FLUDD_STAND:	return 1F;
			case MachineRenderedMeta.GEYSER: 		return 0.85F;
			case MachineRenderedMeta.INGOT_CASTER: 	return 1.5F;
			case MachineRenderedMeta.SIFTER:		return 1.5F;
			case MachineRenderedMeta.TURBINE_GAS: 	return 10F;
			case MachineRenderedMeta.TURBINE_HAND: 	return 2F;
			case MachineRenderedMeta.TURBINE_WATER:	return 5F;
			default:								return 1.5F;
		}
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIds.RENDER_ALL;
	}
	
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof TileTurbineBase) {
				return ((TileTurbineBase) tile).orientation.getOpposite() == side;
			}
		}
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof TileFLUDDStand) {
				TileFLUDDStand fludd = (TileFLUDDStand) tile;
				fludd.setFacing(ForgeDirection.getOrientation(BlockPistonBase.determineOrientation(world, x, y, z, entity)));
				
				int water = 0;
				if (stack.hasTagCompound()) {
					water = stack.stackTagCompound.getInteger("water");
				}

				fludd.tank.setCapacity(ItemArmorFLUDD.STORAGE);
				fludd.tank.setFluidID(Core.highPressureWater.getID());
				fludd.tank.setFluidAmount(water);
				Packets.updateRender(fludd);
			}
			
			if(tile instanceof TileGeyser) ((TileGeyser) tile).setFacing(ForgeDirection.getOrientation(BlockPistonBase.determineOrientation(world, x, y, z, entity)));
			if(tile instanceof TileAirPump) ((TileAirPump) tile).setFacing(DirectionHelper.getFacingFromEntity(entity));
			if(tile instanceof TileAnvil) ((TileAnvil) tile).setFacing(DirectionHelper.getFacingFromEntity(entity));
			if (tile instanceof TileSift) {
				int facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
				if (facing == 1 || facing == 3)
					((TileSift)tile).setFacing(ForgeDirection.EAST);
				else if (facing == 0 || facing == 2)
					((TileSift)tile).setFacing(ForgeDirection.NORTH);
			}
		}
	}
	
	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int side) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof TileTurbineBase) {
				TileTurbineBase turbine = (TileTurbineBase) tile;
				turbine.orientation = ForgeDirection.UP;
				turbine.rotate();
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null || (player.isSneaking() && !world.isRemote)) {
			return false;
		}
		
		//Activate the air pump on right click
		if (tile instanceof TileAirPump) {	
			TileAirPump pump = (TileAirPump) tile;
			if (pump.animate == false) {
				if(Modules.isActive(Modules.diving)) {
					if(pump.updateAirArea(Type.CHECK)) {
						if(!world.isRemote) { 
							pump.doPoweredPump(false, 300, 64.0D, 128.0D, 64.0D);
						}
						pump.animate = true;
					}
				}
				
				if(pump.suckUpGas(1024)) {
					pump.animate = true;
				}
			}
			
			if(world.isRemote && player.isSneaking())
				((TileAirPump) tile).updateAirArea(Type.DISPLAY);
			return true;
		}
		
		if(player.isSneaking())
			return false;
		
		//Activate the Manual Turbine, taking off food as you go
		if(tile instanceof TileTurbineHand) {
			if(PlayerHelper.isFake(player)) return false;
            TileTurbineHand turbine = (TileTurbineHand)tile;
			turbine.energyStorage.modifyEnergyStored(((TileTurbineHand)tile).getEnergyGenerated());
			turbine.isCreatingPower = true;
			turbine.cooldown = 5;

            player.getFoodStats().addStats(0, (float)-world.difficultySetting.getDifficultyId() * 1.5F);

            if(turbine.produced >= 1200) {
                player.attackEntityFrom(MaricultureDamage.turbine, world.difficultySetting.getDifficultyId());
            }

			return true;
		}

		//Update the tank size of the feeder then open the gui
		if (tile instanceof TileFeeder) {
			((TileFeeder) tile).updateTankSize();
			player.openGui(Mariculture.instance, GuiIds.FEEDER, world, x, y, z);
			return true;
		}
		
		//Update the anvil inventory
		if(tile instanceof TileAnvil) {
			if(PlayerHelper.isFake(player)) return false;
			TileAnvil anvil = (TileAnvil) tile;
			if(anvil.getStackInSlot(0) != null) {
				if(!world.isRemote) {
					Packets.syncInventory(anvil, anvil.getInventory());
				}
				
				SpawnItemHelper.addToPlayerInventory(player, world, x, y + 1, z, anvil.getStackInSlot(0));
				anvil.setInventorySlotContents(0, null);
			} else if(player.getCurrentEquippedItem() != null) {
				ItemStack stack = player.getCurrentEquippedItem().copy();
				stack.stackSize = 1;
				anvil.setInventorySlotContents(0, stack);
				if(!player.capabilities.isCreativeMode) {
					player.inventory.decrStackSize(player.inventory.currentItem, 1);
				}
			}
			
			
			return true;
		}
		
		//Update the ingot caster inventory
		if(tile instanceof TileIngotCaster) {
			if (!world.isRemote) {
				TileIngotCaster caster = (TileIngotCaster) tile;
				for(int i = 0; i < caster.getSizeInventory(); i++) {
					if(caster.getStackInSlot(i) != null) {
						SpawnItemHelper.spawnItem(world, x, y + 1, z, caster.getStackInSlot(i));
						caster.setInventorySlotContents(i, null);
						caster.markDirty();
					}
				}
			}
			
			return FluidHelper.handleFillOrDrain((IFluidHandler) world.getTileEntity(x, y, z), player, ForgeDirection.UP);
		}
		
		//Fill the geyser
		if(tile instanceof TileGeyser) {
			return FluidHelper.handleFillOrDrain((IFluidHandler) world.getTileEntity(x, y, z), player, ForgeDirection.UP);
		}
		
		if(tile instanceof TileSift) {
			TileSift sifter = (TileSift) tile;
			if (player.getCurrentEquippedItem() != null) {
				ItemStack stack = player.getCurrentEquippedItem();
				if(stack.getItem() == Core.upgrade) {
					if(stack.getItemDamage() == UpgradeMeta.BASIC_STORAGE) {
						if(!sifter.hasInventory) {
							sifter.hasInventory = true;
							//TODO: Sifter Packet for updating the hasinventory
							Packets.updateRender(sifter);
							player.inventory.decrStackSize(player.inventory.currentItem, 1);
							return false;
						}
					}
				}
				
				boolean played = false;
				if(Fishing.sifter.getResult(stack) != null) {
					if(!world.isRemote) {
						ArrayList<RecipeSifter> recipe = Fishing.sifter.getResult(stack);
						int stackSize = stack.stackSize;
						for (int j = 0; j <= stackSize; j++) {
							if(stack.stackSize > 0) {
								for(RecipeSifter bait: recipe) {
									int chance = Rand.rand.nextInt(100);
									if(chance < bait.chance) {
										ItemStack result = bait.bait.copy();
										result.stackSize = bait.minCount + Rand.rand.nextInt((bait.maxCount + 1) - bait.minCount);
										spawnItem(result, world, x, y, z);
									}
								}
								
								if(!played) {
									world.playSoundAtEntity(player, Mariculture.modid + ":sift", 1.5F, 1.0F);
									played = true;
								}
							}
							
							if(!player.capabilities.isCreativeMode)
								player.inventory.decrStackSize(player.inventory.currentItem, 1);
						}
					}
					
					return true;
				}
			}	

			if (((TileSift)tile).hasInventory) {
				player.openGui(Mariculture.instance, GuiIds.SIFT, world, x, y, z);
				return true;
			}
		}

		//Return to doing the rest
		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}
	
	//Called by the sifter
	private void spawnItem(ItemStack item, World world, int x, int y, int z) {
		boolean done = false;
		TileSift sift = (TileSift) world.getTileEntity(x, y, z);
		if (sift.hasInventory) {
			if (sift.getSuitableSlot(item) != 10) {
				int slot = sift.getSuitableSlot(item);
				ItemStack newStack = item;
				if (sift.getStackInSlot(slot) != null) {
					newStack.stackSize = newStack.stackSize + sift.getStackInSlot(slot).stackSize;
				}

				sift.setInventorySlotContents(slot, newStack);

				done = true;
			}
		}

		if (done == false) {
			Random rand = new Random();
			float rx = rand.nextFloat() * 0.6F + 0.1F;
			float ry = rand.nextFloat() * 0.6F + 0.1F;
			float rz = rand.nextFloat() * 0.6F + 0.1F;

			EntityItem dropped = new EntityItem(world, x + rx, y + ry + 0.5F, z + rz, item);
			world.spawnEntityInWorld(dropped);
		}
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileAnvil && ItemHelper.isPlayerHoldingItem(Core.hammer, player)) {
			if(player.getDisplayName().equals("[CoFH]"))
				return;
			if(player instanceof FakePlayer)
				return;
			ItemStack hammer = player.getCurrentEquippedItem();
			if (((TileAnvil)tile).workItem(player, hammer)) {
				if(hammer.attemptDamageItem(1, Rand.rand))
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
			}
		}
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
		int meta = block.getBlockMetadata(x, y, z);
		ForgeDirection facing;

		switch (meta) {
		case MachineRenderedMeta.AIR_PUMP:
			setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.9F, 0.8F);
			break;
		case MachineRenderedMeta.GEYSER:
			TileGeyser geyser = (TileGeyser)block.getTileEntity(x, y, z);
			if(geyser.orientation == ForgeDirection.UP)
				setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.25F, 0.9F);
			if(geyser.orientation == ForgeDirection.DOWN)
				setBlockBounds(0.1F, 0.75F, 0.1F, 0.9F, 1.0F, 0.9F);
			if(geyser.orientation == ForgeDirection.EAST)
				setBlockBounds(0.0F, 0.1F, 0.1F, 0.25F, 0.9F, 0.9F);
			if(geyser.orientation == ForgeDirection.WEST)
				setBlockBounds(0.75F, 0.1F, 0.1F, 1F, 0.9F, 0.9F);
			if(geyser.orientation == ForgeDirection.SOUTH)
				setBlockBounds(0.1F, 0.1F, 0.0F, 0.9F, 0.9F, 0.25F);
			if(geyser.orientation == ForgeDirection.NORTH)
				setBlockBounds(0.1F, 0.1F, 0.75F, 0.9F, 0.9F, 1.0F);
			break;
		case MachineRenderedMeta.ANVIL:
			TileAnvil anvil = (TileAnvil)block.getTileEntity(x, y, z);
			if(anvil.getFacing() == ForgeDirection.EAST)
				setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
			if(anvil.getFacing() == ForgeDirection.NORTH)
				setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
			if(anvil.getFacing() == ForgeDirection.WEST)
				setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
			if(anvil.getFacing() == ForgeDirection.SOUTH)
				setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
			break;
		case MachineRenderedMeta.SIFTER:
			TileSift sift = (TileSift)block.getTileEntity(x, y, z);
			if(sift.getFacing() == ForgeDirection.EAST)
				setBlockBounds(-0.3F, 0F, -0.085F, 1.3F, 0.8F, 1.085F);
			else
				setBlockBounds(-0.05F, 0F, -0.15F, 1.15F, 0.8F, 1.45F);
			break;
		default:
			setBlockBounds(0F, 0F, 0F, 1F, 0.95F, 1F);
		}
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == MachineRenderedMeta.GEYSER || meta == MachineRenderedMeta.ANVIL) {
			return AxisAlignedBB.getAABBPool().getAABB((double) x + this.minX, (double) y + this.minY,
					(double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
		} else if (meta == MachineRenderedMeta.SIFTER) {
			float dif = 0.0625F;
			return AxisAlignedBB.getAABBPool().getAABB(x + dif, y, z + dif, x + 1 - dif, y + 1 - dif, z + 1 - dif);
		}

		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(world.getBlockMetadata(x, y, z) == MachineRenderedMeta.SIFTER) {
			if (entity instanceof EntityItem && !world.isRemote) {
				Random random = new Random();
				EntityItem entityitem = (EntityItem) entity;
				ItemStack item = entityitem.getEntityItem();
				boolean played = false;
				
				if(!world.isRemote && Fishing.sifter.getResult(item) != null) {
					ArrayList<RecipeSifter> recipe = Fishing.sifter.getResult(item);
					for (int j = 0; j < item.stackSize; j++) {
						for(RecipeSifter bait: recipe) {
							int chance = Rand.rand.nextInt(100);
							if(chance < bait.chance) {
								ItemStack result = bait.bait.copy();
								result.stackSize = bait.minCount + Rand.rand.nextInt((bait.maxCount + 1) - bait.minCount);
								spawnItem(result, world, x, y, z);
							}
						}
						
						if(!played) {
							world.playSoundAtEntity(entity, Mariculture.modid + ":sift", 1.5F, 1.0F);
							played = true;
						}
						
						entityitem.setDead();
					}
				}
			}
		}
	}
	
	@Override
	public boolean getBlocksMovement(IBlockAccess block, int x, int y, int z) {
		return block.getBlockMetadata(x, y, z) != MachineRenderedMeta.SIFTER;
	}
	
	@Override
	public boolean doesDrop(int meta) {
		if(meta == MachineRenderedMeta.SIFTER) return false;
		if(meta == MachineRenderedMeta.FLUDD_STAND) return false;
				
		return true;
	}

	@Override
	public boolean onBlockDropped(World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileSift) {
			if(((TileSift)tile).hasInventory) {
				SpawnItemHelper.spawnItem(world, x, y, z, new ItemStack(this, 1, MachineRenderedMeta.SIFTER));
				SpawnItemHelper.spawnItem(world, x, y, z, new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_STORAGE));
			} else {
				SpawnItemHelper.spawnItem(world, x, y, z, new ItemStack(this, 1, MachineRenderedMeta.SIFTER));
			}
			
			return world.setBlockToAir(x, y, z);
		}
		
		if(tile instanceof TileFLUDDStand) {
			TileFLUDDStand stand = (TileFLUDDStand) tile;
			ItemStack fludd = new ItemStack(Factory.fludd);
			fludd.setTagCompound(new NBTTagCompound());
			fludd.stackTagCompound.setInteger("water", stand.tank.getFluidAmount());
			SpawnItemHelper.spawnItem(world, x, y, z, fludd);
			
			return world.setBlockToAir(x, y, z);
		}
		
		return super.onBlockDropped(world, x, y, z);
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		switch (meta) {
			case MachineRenderedMeta.AIR_PUMP: 		return new TileAirPump();
			case MachineRenderedMeta.FISH_FEEDER: 	return new TileFeeder();
			case MachineRenderedMeta.FLUDD_STAND:	return new TileFLUDDStand();
			case MachineRenderedMeta.GEYSER: 		return new TileGeyser();
			case MachineRenderedMeta.INGOT_CASTER: 	return new TileIngotCaster();
			case MachineRenderedMeta.SIFTER:		return new TileSift();
			case MachineRenderedMeta.TURBINE_GAS: 	return new TileTurbineGas();
			case MachineRenderedMeta.TURBINE_HAND: 	return new TileTurbineHand();
			case MachineRenderedMeta.TURBINE_WATER:	return new TileTurbineWater();
			default:								return new TileAnvil();
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if(meta == MachineRenderedMeta.GEYSER)
			return Blocks.hopper.getIcon(0, 0);
		if(meta == MachineRenderedMeta.INGOT_CASTER)
			return super.getIcon(side, meta);
		if(meta >= MachineRenderedMeta.ANVIL)
			return super.getIcon(side, MachineRenderedMeta.INGOT_CASTER);
		
		return icons[meta];
	}
	
	@Override
	public boolean isActive(int meta) {
		switch (meta) {
			case MachineRenderedMeta.FISH_FEEDER: 	return Modules.isActive(Modules.fishery);
			case MachineRenderedMeta.GEYSER: 		return Modules.isActive(Modules.factory);
			case MachineRenderedMeta.SIFTER:		return Modules.isActive(Modules.fishery);
			case MachineRenderedMeta.TURBINE_GAS: 	return Modules.isActive(Modules.factory);
			case MachineRenderedMeta.TURBINE_HAND: 	return Modules.isActive(Modules.factory);
			case MachineRenderedMeta.TURBINE_WATER:	return Modules.isActive(Modules.factory);
			case MachineRenderedMeta.FLUDD_STAND:	return false;
			default:								return true;
		}
	}

	@Override
	public int getMetaCount() {
		return MachineRenderedMeta.COUNT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		String name = prefix != null? prefix: "";
		icons = new IIcon[getMetaCount()];
		for (int i = 0; i < icons.length; i++) {
			if(i != MachineRenderedMeta.ANVIL) {
				icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + name + getName(i));
			}
		}
	}
}
