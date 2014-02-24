package mariculture.core.blocks;

import java.util.ArrayList;
import java.util.Random;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.blocks.base.BlockConnected;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.TankMeta;
import mariculture.fishery.blocks.TileFishTank;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTank extends BlockConnected {
	private static IIcon[] fishTank = new IIcon[47];
	
	public BlockTank() {
		super(Material.piston);
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIds.BLOCK_TANKS;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null) {
			return false;
		}
		
		if(tile instanceof TileFishTank) {
			if(player.isSneaking())
				return false;
			player.openGui(Mariculture.instance, -1, world, x, y, z);
			return true;
		}
			
		return FluidHelper.handleFillOrDrain((IFluidHandler) world.getTileEntity(x, y, z), player, ForgeDirection.UP);
	}

	@Override
	public boolean hasTileEntity(int meta) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		switch(meta) {
		case TankMeta.BOTTLE:
			return new TileVoidBottle();
		case TankMeta.TANK:
			return new TileTankBlock();
		case TankMeta.FISH:
			return new TileFishTank();
		}
		
		return new TileTankBlock();
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileTankBlock) {
			TileTankBlock tank = (TileTankBlock) tile;
			FluidStack fluid = tank.getFluid();
			if(fluid != null) {
				return fluid.getFluid().getLuminosity();
			}
		}

		return 0;
    }

	@Override
	public int getMetaCount() {
		return TankMeta.COUNT;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
		if(block.getBlockMetadata(x, y, z) != TankMeta.BOTTLE)
			setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
		else
			setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z) == TankMeta.BOTTLE? null: super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	public Item getItemDropped(int meta, Random random, int j) {
		switch(meta) {
			case TankMeta.TANK:
				return null;
			case TankMeta.BOTTLE:
				return Core.liquidContainers;
			default:
				return super.getItemDropped(meta, random, j);
		}
    }
	
	@Override
	public int damageDropped(int meta) {
		switch(meta) {
			case TankMeta.BOTTLE:
				return FluidContainerMeta.BOTTLE_VOID;
			default:
				return meta;
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int facing = BlockPistonBase.determineOrientation(world, x, y, z, entity);
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile == null)
			return;
		if(tile instanceof TileFishTank) {
			((TileFishTank) tile).orientation = ForgeDirection.getOrientation(facing);
		}
		
		if(stack.hasTagCompound()) {
			if (world.getBlockMetadata(x, y, z) == TankMeta.TANK) {
				if (tile instanceof TileTankBlock) {
					TileTankBlock tank = (TileTankBlock) tile;
					tank.setFluid(FluidStack.loadFluidStackFromNBT(stack.stackTagCompound));
					//TODO: PACKET Add Packet Sending to update Fluids in the Tank for display purposes
					/*if(!world.isRemote)
						Packets.updateTile(tank, 64, new Packet118FluidUpdate(x, y, z, tank.getFluid()).build());*/
				}
			}
		}
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		if(world.getBlockMetadata(x, y, z) == TankMeta.TANK) {
			ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
			ItemStack drop = new ItemStack(Core.tankBlocks, 1, TankMeta.TANK);
			TileTankBlock tank = (TileTankBlock) world.getTileEntity(x, y, z);
			if(tank != null && tank.getFluid() != null) {
				if (!drop.hasTagCompound()) {
					drop.setTagCompound(new NBTTagCompound());
				}
					
				tank.getFluid().writeToNBT(drop.stackTagCompound);
			}
			
			ret.add(drop);
			return ret;
		} else {
			return super.getDrops(world, x, y, z, metadata, fortune);
		}
    }
	
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == TankMeta.BOTTLE) {
			for (int j = 0; j < 2; ++j)  {
				float f = rand.nextFloat() - rand.nextFloat();
				float f1 = rand.nextFloat() - rand.nextFloat();
			    float f2 = rand.nextFloat() - rand.nextFloat();
			    world.spawnParticle("magicCrit", x + 0.5D + (double)f, y + 0.5D + (double)f1, z + 0.5D + (double)f2, 0, 0, 0);
			    world.spawnParticle("witchMagic", x + 0.5D, y + 0.5D, z + 0.5D, 0, 0, 0);
			}
		}
	}

	@Override
	public boolean isActive(int meta) {
		if(meta == TankMeta.FISH)
			return Modules.fishery.isActive();
		return meta != TankMeta.BOTTLE;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int j) {
		BlockHelper.dropFish(world, x, y, z);
		super.breakBlock(world, x, y, z, block, j);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons = new IIcon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this, 1, i)) + "Tank");
		}
		
		registerConnectedTextures(iconRegister);
	}

	@Override
	public IIcon[] getTexture(int meta) {
		return meta == TankMeta.FISH? fishTank: null;
	}

	@Override
	public void registerConnectedTextures(IIconRegister iconRegister) {
		for (int i = 0; i < 47; i++) {
			fishTank[i] = iconRegister.registerIcon(Mariculture.modid + ":fishTank/" + (i + 1));
		}
	}
}
