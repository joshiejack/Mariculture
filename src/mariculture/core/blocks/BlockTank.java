package mariculture.core.blocks;

import java.util.Random;

import mariculture.core.Core;
import mariculture.core.Mariculture;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.GuiIds;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.TankMeta;
import mariculture.core.network.Packet118FluidUpdate;
import mariculture.core.network.Packets;
import mariculture.fishery.TileFishTank;
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
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTank extends BlockDecorative {

	public BlockTank(int i) {
		super(i, Material.piston);
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
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return world.getBlockId(x, y, z) == this.blockID ? false : super.shouldSideBeRendered(world, x, y, z, side);
	}

	@Override
	public int getRenderType() {
		return RenderIds.BLOCK_TANKS;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			return false;
		}
		
		if(player.isSneaking() && tile instanceof TileFishTank) {
			player.openGui(Mariculture.instance, -1, world, x, y, z);
			return true;
		}
			
		return FluidHelper.handleFillOrDrain((IFluidHandler) world.getBlockTileEntity(x, y, z), player, ForgeDirection.UP);
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
	
	@Override
	public int idDropped(int meta, Random random, int j) {
		switch(meta) {
			case TankMeta.TANK:
				return 0;
			case TankMeta.BOTTLE:
				return Core.liquidContainers.itemID;
			default:
				return this.blockID;
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
		if(stack.hasTagCompound()) {
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if (tile != null && world.getBlockMetadata(x, y, z) == TankMeta.TANK) {
				if (tile instanceof TileTankBlock) {
					TileTankBlock tank = (TileTankBlock) tile;
					tank.setFluid(FluidStack.loadFluidStackFromNBT(stack.stackTagCompound));
					if(!world.isRemote)
						Packets.updateTile(tank, 64, new Packet118FluidUpdate(x, y, z, tank.getFluid()).build());
				}
			}
		}
	}
	
	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		if (!world.isRemote) {
			if (world.getBlockMetadata(x, y, z) == TankMeta.TANK) {
				if (!player.capabilities.isCreativeMode) {
					ItemStack drop = new ItemStack(Core.tankBlocks, 1, TankMeta.TANK);
					
					TileTankBlock tank = (TileTankBlock) world.getBlockTileEntity(x, y, z);
					if(tank != null && tank.getFluid() != null) {
						if (!drop.hasTagCompound()) {
							drop.setTagCompound(new NBTTagCompound());
						}
						
						tank.getFluid().writeToNBT(drop.stackTagCompound);
					}

					EntityItem entity = new EntityItem(world, (x), (float) y + 1, (z), new ItemStack(drop.itemID, 1, drop.getItemDamage()));
					if (drop.hasTagCompound()) {
						entity.getEntityItem().setTagCompound((NBTTagCompound) drop.getTagCompound().copy());
					}

					world.spawnEntityInWorld(entity);
				}
			}
		}

		return world.setBlockToAir(x, y, z);
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
	public void breakBlock(World world, int x, int y, int z, int i, int j) {
		BlockHelper.dropFish(world, x, y, z);
		super.breakBlock(world, x, y, z, i, j);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":"
					+ getName(new ItemStack(this.blockID, 1, i)) + "Tank");
		}
	}
}
