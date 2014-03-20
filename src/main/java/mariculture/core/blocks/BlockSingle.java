package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.blocks.TileAirPump.Type;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.DirectionHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.helpers.cofh.ItemHelper;
import mariculture.core.lib.GuiIds;
import mariculture.core.lib.MaricultureDamage;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.SingleMeta;
import mariculture.core.network.PacketOrientationSync;
import mariculture.core.network.Packets;
import mariculture.core.util.Rand;
import mariculture.factory.Factory;
import mariculture.factory.blocks.TileFLUDDStand;
import mariculture.factory.blocks.TileGeyser;
import mariculture.factory.blocks.TileTurbineBase;
import mariculture.factory.blocks.TileTurbineGas;
import mariculture.factory.blocks.TileTurbineHand;
import mariculture.factory.blocks.TileTurbineWater;
import mariculture.factory.items.ItemArmorFLUDD;
import mariculture.fishery.blocks.TileFeeder;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
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

public class BlockSingle extends BlockFunctional {
	public BlockSingle() {
		super(Material.piston);
	}
	
	@Override
	public String getToolType(int meta) {
		return meta == SingleMeta.FISH_FEEDER || meta == SingleMeta.TURBINE_HAND ? "axe": "pickaxe";
	}

	@Override
	public int getToolLevel(int meta) {
		switch(meta) {
			case SingleMeta.TURBINE_GAS:
				return 2;
			case SingleMeta.FISH_FEEDER:
				return 0;
			case SingleMeta.TURBINE_HAND:
				return 0;
			default:
				return 1;
		}
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch (world.getBlockMetadata(x, y, z)) {
		case SingleMeta.AIR_PUMP:
			return 4F;
		case SingleMeta.FISH_FEEDER:
			return 0.5F;
		case SingleMeta.TURBINE_WATER:
			return 2.5F;
		case SingleMeta.FLUDD_STAND:
			return 0.25F;
		case SingleMeta.TURBINE_GAS:
			return 5F;
		case SingleMeta.GEYSER:
			return 1F;
		case SingleMeta.ANVIL_1:
			return 6F;
		case SingleMeta.ANVIL_2:
			return 6F;
		case SingleMeta.ANVIL_3:
			return 6F;
		case SingleMeta.ANVIL_4:
			return 6F;
		case SingleMeta.INGOT_CASTER:
			return 1F;
		default:
			return 1F;
		}
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof TileTurbineBase) {
				return ((TileTurbineBase) tile).direction.getOpposite() == side;
			}
		}
		return false;
	}

	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
		super.rotateBlock(world, x, y, z, axis);
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof TileTurbineBase) {
				return ((TileTurbineBase) tile).switchOrientation();
			}
		}
		return false;
	}
	
	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int side) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof TileTurbineBase) {
				TileTurbineBase turbine = (TileTurbineBase) tile;
				turbine.direction = ForgeDirection.UP;
				turbine.switchOrientation();
			}
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntity tile = world.getTileEntity(x, y, z);
		int facing = BlockPistonBase.determineOrientation(world, x, y, z, entity);
		if (tile != null) {
			if (tile instanceof TileFLUDDStand) {
				TileFLUDDStand fludd = (TileFLUDDStand) tile;
				fludd.orientation = ForgeDirection.getOrientation(facing);
				int water = 0;
				if (stack.hasTagCompound()) {
					water = stack.stackTagCompound.getInteger("water");
				}

				fludd.tank.setCapacity(ItemArmorFLUDD.STORAGE);
				fludd.tank.setFluidID(Core.highPressureWater.getID());
				fludd.tank.setFluidAmount(water);
				world.markBlockForUpdate(x, y, z);
			}
			
			if(tile instanceof TileGeyser) {
				TileGeyser geyser = (TileGeyser) tile;
				geyser.orientation = ForgeDirection.getOrientation(facing);
				Packets.updateAround(geyser, new PacketOrientationSync(geyser.xCoord, geyser.yCoord, geyser.zCoord, geyser.orientation));
			}
			
			if(tile instanceof TileAirPump) ((TileAirPump)world.getTileEntity(x, y, z)).orientation = DirectionHelper.getFacingFromEntity(entity);
		}
		
		int meta = stack.getItemDamage();
		if(meta >= SingleMeta.ANVIL_1 && meta <= SingleMeta.ANVIL_4) {
	        int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
	        int i1 = world.getBlockMetadata(x, y, z) >> 2;
	        ++l;
	        l %= 4;
	
	        if (l == 0)
	        {
	            world.setBlockMetadataWithNotify(x, y, z, SingleMeta.ANVIL_3, 2);
	        }
	
	        if (l == 1)
	        {
	            world.setBlockMetadataWithNotify(x, y, z, SingleMeta.ANVIL_4, 2);
	        }
	
	        if (l == 2)
	        {
	            world.setBlockMetadataWithNotify(x, y, z, SingleMeta.ANVIL_1, 2);
	        }
	
	        if (l == 3)
	        {
	            world.setBlockMetadataWithNotify(x, y, z, SingleMeta.ANVIL_2, 2);
	        }
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float f, float g, float t) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null || (player.isSneaking() && !world.isRemote)) {
			return false;
		}
		
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

		if (tile instanceof TileFLUDDStand) {
			player.openGui(Mariculture.instance, GuiIds.FLUDD_BLOCK, world, x, y, z);
			return true;
		}

		if (tile instanceof TileTurbineWater) {
			player.openGui(Mariculture.instance, GuiIds.TURBINE, world, x, y, z);
			return true;
		}
		
		if (tile instanceof TileTurbineGas) {
			player.openGui(Mariculture.instance, GuiIds.TURBINE_GAS, world, x, y, z);
			return true;
		}

		if (tile instanceof TileFeeder) {
			((TileFeeder) tile).updateTankSize();
			player.openGui(Mariculture.instance, GuiIds.FEEDER, world, x, y, z);
			return true;
		}
		
		if(tile instanceof TileAnvil) {
			if(PlayerHelper.isFake(player)) return false;
			TileAnvil anvil = (TileAnvil) tile;
			if(anvil.getStackInSlot(0) != null) {
				Packets.syncInventory(anvil, anvil.getInventory());
				if (!player.inventory.addItemStackToInventory(anvil.getStackInSlot(0))) {
					if(!world.isRemote) {
						SpawnItemHelper.spawnItem(world, x, y + 1, z, anvil.getStackInSlot(0));
					}
				}
					
				anvil.setInventorySlotContents(0, null);
			} else if(player.getCurrentEquippedItem() != null) {
				ItemStack stack = player.getCurrentEquippedItem().copy();
				stack.stackSize = 1;
				anvil.setInventorySlotContents(0, stack);
				player.inventory.decrStackSize(player.inventory.currentItem, 1);
			}
			
			
			return true;
		}
		
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
		
		if(tile instanceof TileGeyser) {
			return FluidHelper.handleFillOrDrain((IFluidHandler) world.getTileEntity(x, y, z), player, ForgeDirection.UP);
		}

		return false;
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
		case SingleMeta.AIR_PUMP:
			setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.9F, 0.8F);
			break;
		case SingleMeta.GEYSER:
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
		case SingleMeta.ANVIL_1:
			setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
			break;
		case SingleMeta.ANVIL_2:
			setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
			break;
		case SingleMeta.ANVIL_3:
			setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
			break;
		case SingleMeta.ANVIL_4:
			setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
		default:
			setBlockBounds(0F, 0F, 0F, 1F, 0.95F, 1F);
		}
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == SingleMeta.GEYSER || (meta >= SingleMeta.ANVIL_1 && meta <= SingleMeta.ANVIL_4)) {
			return AxisAlignedBB.getAABBPool().getAABB((double) x + this.minX, (double) y + this.minY,
					(double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
		}

		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		switch (meta) {
		case SingleMeta.AIR_PUMP:
			return new TileAirPump();
		case SingleMeta.FISH_FEEDER:
			return new TileFeeder();
		case SingleMeta.TURBINE_WATER:
			return new TileTurbineWater();
		case SingleMeta.FLUDD_STAND:
			return new TileFLUDDStand();
		case SingleMeta.TURBINE_GAS:
			return new TileTurbineGas();
		case SingleMeta.TURBINE_HAND:
			return new TileTurbineHand();
		case SingleMeta.GEYSER:
			return new TileGeyser();
		case SingleMeta.INGOT_CASTER:
			return new TileIngotCaster();
		}
		
		if(meta >= SingleMeta.ANVIL_1 && meta <= SingleMeta.ANVIL_4)
			return new TileAnvil();

		return null;
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
		return RenderIds.BLOCK_SINGLE;
	}
	
	@Override
	public boolean doesDrop(int meta) {
		return meta != SingleMeta.FLUDD_STAND;
	}
	
	@Override
	public boolean onBlockDropped(World world, int x, int y, int z) { 
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == SingleMeta.FLUDD_STAND) {
			TileFLUDDStand tile = (TileFLUDDStand) world.getTileEntity(x, y, z);
			ItemStack fludd = new ItemStack(Factory.fludd);
			fludd.setTagCompound(new NBTTagCompound());
			fludd.stackTagCompound.setInteger("water", tile.tank.getFluidAmount());
			SpawnItemHelper.spawnItem(world, x, y, z, fludd);
		}
		
		return super.onBlockDropped(world, x, y, z);
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if(meta == SingleMeta.GEYSER)
			return Blocks.hopper.getIcon(0, 0);
		if(meta == SingleMeta.INGOT_CASTER)
			return super.getIcon(side, meta);
		if(meta >= SingleMeta.ANVIL_1 && meta <= SingleMeta.ANVIL_4)
			return super.getIcon(side, SingleMeta.INGOT_CASTER);
		
		return icons[meta];
	}

	@Override
	public int damageDropped(int i) {
		if(i >= SingleMeta.ANVIL_1 && i <= SingleMeta.ANVIL_4)
			return SingleMeta.ANVIL_1;
		return i;
	}

	@Override
	public boolean isActive(int meta) {
		switch (meta) {
		case SingleMeta.FISH_FEEDER:
			return Modules.isActive(Modules.fishery);
		case SingleMeta.TURBINE_WATER:
			return Modules.isActive(Modules.factory);
		case SingleMeta.FLUDD_STAND:
			return false;
		case SingleMeta.TURBINE_GAS:
			return Modules.isActive(Modules.factory);
		case SingleMeta.GEYSER:
			return Modules.isActive(Modules.factory);
		case SingleMeta.TURBINE_HAND:
			return Modules.isActive(Modules.factory);
		case SingleMeta.ANVIL_2:
			return false;
		case SingleMeta.ANVIL_3:
			return false;
		case SingleMeta.ANVIL_4:
			return false;
		default:
			return true;
		}
	}

	@Override
	public int getMetaCount() {
		return SingleMeta.COUNT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		String name = prefix != null? prefix: "";
		
		icons = new IIcon[getMetaCount()];
		for (int i = 0; i < icons.length; i++) {
			if(i <= SingleMeta.ANVIL_1 || i > SingleMeta.ANVIL_4) {
				icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + name + getName(i));
			}
		}
	}
}
