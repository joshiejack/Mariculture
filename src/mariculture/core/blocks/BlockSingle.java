package mariculture.core.blocks;

import java.util.ArrayList;
import java.util.Random;

import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.Mariculture;
import mariculture.core.blocks.TileAirPump.Type;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GuiIds;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.SingleMeta;
import mariculture.core.network.Packets;
import mariculture.factory.Factory;
import mariculture.factory.blocks.TileFLUDDStand;
import mariculture.factory.blocks.TileGeyser;
import mariculture.factory.blocks.TileTurbineBase;
import mariculture.factory.blocks.TileTurbineGas;
import mariculture.factory.blocks.TileTurbineHand;
import mariculture.factory.blocks.TileTurbineWater;
import mariculture.factory.items.ItemArmorFLUDD;
import mariculture.fishery.Fishery;
import mariculture.fishery.TankHelper;
import mariculture.fishery.blocks.TileFeeder;
import mariculture.fishery.blocks.TileNet;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.FakePlayer;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSingle extends BlockMachine {
	private Icon[] icons;

	public BlockSingle(int i) {
		super(i, Material.piston);
		this.setCreativeTab(MaricultureTab.tabMariculture);
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		super.onBlockAdded(par1World, par2, par3, par4);
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch (world.getBlockMetadata(x, y, z)) {
		case SingleMeta.AIR_PUMP:
			return 4F;
		case SingleMeta.FISH_FEEDER:
			return 0.5F;
		case SingleMeta.NET:
			return 0.05F;
		case SingleMeta.TURBINE_WATER:
			return 2.5F;
		case SingleMeta.FLUDD_STAND:
			return 3F;
		case SingleMeta.TURBINE_GAS:
			return 5F;
		case SingleMeta.GEYSER:
			return 1F;
		case SingleMeta.ANVIL:
			return 6F;
		case SingleMeta.BUCKET:
			return 3F;
		case SingleMeta.VOID_BOTTLE:
			return 1F;
		}

		return 1F;
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof TileTurbineBase) {
				return ((TileTurbineBase) tile).direction.getOpposite() == side;
			}
		}
		return false;
	}

	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof TileTurbineBase) {
				return ((TileTurbineBase) tile).switchOrientation();
			}
			
			if(tile instanceof TileAirPump) {
				return ((TileAirPump) tile).updateAirArea(Type.DISPLAY);
			}
		}
		return false;
	}

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int side) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
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
		TileEntity tile = world.getBlockTileEntity(x, y, z);
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
				Packets.updateTile(fludd, 32, fludd.getDescriptionPacket());
			}
			
			if(tile instanceof TileGeyser) {
				((TileGeyser)tile).orientation = ForgeDirection.getOrientation(facing);
				Packets.updateTile(((TileGeyser)tile), 32, ((TileGeyser)tile).getDescriptionPacket());
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float f, float g, float t) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile == null || player.isSneaking()) {
			return false;
		}
		
		if(tile instanceof TileTurbineHand) {
			if(player instanceof FakePlayer) {
				return false;
			}
			
			((TileTurbineHand)tile).generatePower();
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
			ArrayList<String> array = TankHelper.getSurroundingArray(tile);

			if (TankHelper.getTankSize(array) > 0) {
				player.openGui(Mariculture.instance, GuiIds.FEEDER, world, x, y, z);
				TileFeeder feeder = (TileFeeder) tile;
				feeder.setTankSize();
				return true;
			}
		}

		if(Modules.diving.isActive()) {
			if (tile instanceof TileAirPump) {				
				if (!world.isRemote && ((TileAirPump) tile).animate == false && Extra.ACTIVATE_PUMP) {
					if(((TileAirPump)tile).updateAirArea(Type.CHECK)) {
						((TileAirPump) tile).supplyWithAir(300, 15.0D, 20.0D, 15.0D);
						((TileAirPump) tile).animate = true;
					}
					
					((TileAirPump) tile).suckUpGas(1024);
				} else {
					if(((TileAirPump)tile).updateAirArea(Type.CHECK)) {
						((TileAirPump) tile).animate = true;
					}
				}
				
				return true;
			}
		}

		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
		int meta = block.getBlockMetadata(x, y, z);
		ForgeDirection facing;

		switch (meta) {
		case SingleMeta.AIR_PUMP:
			setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 0.9F, 0.8F);
			break;
		case SingleMeta.NET:
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.015625F, 1.0F);
			break;
		case SingleMeta.GEYSER:
			TileGeyser geyser = (TileGeyser)block.getBlockTileEntity(x, y, z);
			if(geyser.orientation == ForgeDirection.UP)
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
			if(geyser.orientation == ForgeDirection.DOWN)
				setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
			if(geyser.orientation == ForgeDirection.EAST)
				setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
			if(geyser.orientation == ForgeDirection.WEST)
				setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			if(geyser.orientation == ForgeDirection.SOUTH)
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
			if(geyser.orientation == ForgeDirection.NORTH)
				setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
			break;
		case SingleMeta.BUCKET:
			setBlockBounds(0F, 0F, 0F, 1F, 0.5F, 1F);
		default:
			setBlockBounds(0F, 0F, 0F, 1F, 0.95F, 1F);
		}
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta == SingleMeta.NET || meta == SingleMeta.GEYSER) {
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
		case SingleMeta.NET:
			return new TileNet();
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
		case SingleMeta.BUCKET:
			return new TileBucket();
		}

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
	public void breakBlock(World world, int x, int y, int z, int i, int j) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			if(tile instanceof TileAirPump) {
				((TileAirPump) tile).updateAirArea(Type.CLEAR);
			}
		}
		InventoryHelper.dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, i, j);
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		if (!world.isRemote) {
			if (world.getBlockMetadata(x, y, z) == SingleMeta.FLUDD_STAND) {
				if (!player.capabilities.isCreativeMode) {
					if (world.getBlockTileEntity(x, y, z) instanceof TileFLUDDStand) {
						dropFLUDD(world, x, y, z);
					}
				}
			}
		}

		return world.setBlockToAir(x, y, z);
	}

	private void dropFLUDD(World world, int x, int y, int z) {
		TileFLUDDStand tile = (TileFLUDDStand) world.getBlockTileEntity(x, y, z);
		ItemStack drop = new ItemStack(Factory.fludd);

		if (!drop.hasTagCompound()) {
			drop.setTagCompound(new NBTTagCompound());
		}

		if (tile != null) {
			drop.stackTagCompound.setInteger("water", tile.tank.getFluidAmount());
		}

		EntityItem entityitem = new EntityItem(world, (x), (float) y + 1, (z), new ItemStack(drop.itemID, 1,
				drop.getItemDamage()));

		if (drop.hasTagCompound()) {
			entityitem.getEntityItem().setTagCompound((NBTTagCompound) drop.getTagCompound().copy());
		}

		world.spawnEntityInWorld(entityitem);
	}

	@Override
	public int idDropped(int i, Random random, int j) {
		if (i == SingleMeta.NET) {
			return Fishery.net.itemID;
		}

		if (i == SingleMeta.FLUDD_STAND) {
			return 0;
		}

		return this.blockID;
	}

	@Override
	public int damageDropped(int i) {
		return (i == SingleMeta.NET)? 0: i;
	}

	@Override
	public boolean isActive(int meta) {
		switch (meta) {
		case SingleMeta.AIR_PUMP:
			return true;
		case SingleMeta.FISH_FEEDER:
			return (Modules.fishery.isActive());
		case SingleMeta.NET:
			return false;
		case SingleMeta.TURBINE_WATER:
			return (Modules.factory.isActive());
		case SingleMeta.FLUDD_STAND:
			return false;
		case SingleMeta.TURBINE_GAS:
			return (Modules.factory.isActive());
		case SingleMeta.GEYSER:
			return (Modules.factory.isActive());
		case SingleMeta.TURBINE_HAND:
			return (Modules.factory.isActive());
		default:
			return true;
		}
	}

	@Override
	public int getMetaCount() {
		return SingleMeta.COUNT;
	}
}
