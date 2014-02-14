package mariculture.core.blocks;

import static net.minecraftforge.common.ForgeDirection.NORTH;

import java.util.ArrayList;
import java.util.Random;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.lib.AirMeta;
import mariculture.core.util.Rand;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAir extends BlockDecorative {	
	public BlockAir(int i) {
		super(i, Material.air);
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
	public boolean isAirBlock(World world, int x, int y, int z){
        return true;
    }
	
	public void setFire(World world, int x, int y, int z) {
		if(world.isAirBlock(x, y + 1, z)) {
			world.setBlock(x, y + 1, z, Block.fire.blockID);
		} else if (world.isAirBlock(x, y - 1, z)) {
			world.setBlock(x, y - 1, z, Block.fire.blockID);
		} else if (world.isAirBlock(x - 1, y, z)) {
			world.setBlock(x - 1, y, z, Block.fire.blockID);
		} else if (world.isAirBlock(x, y, z - 1)) {
			world.setBlock(x, y, z - 1, Block.fire.blockID);
		} else if (world.isAirBlock(x + 1, y, z)) {
			world.setBlock(x + 1, y, z, Block.fire.blockID);
		} else if (world.isAirBlock(x, y, z + 1)) {
			world.setBlock(x, y, z + 1, Block.fire.blockID);
		}
	}
	
	@Override
	public boolean isBlockReplaceable(World world, int x, int y, int z) {
		if(Rand.nextInt(4)) {
			if(world.getBlockLightValue(x, y, z) > 8) {
				setFire(world, x, y, z);
			}
		}
		
		return true;
	}
	
	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta) {
		return false;
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}
	
	@Override
	public boolean canCollideCheck(int meta, boolean par2) {
        return false;
    }
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)  {
		return isNaturalGas(world, x, y, z) ? false : (side == 1 ? true : super.shouldSideBeRendered(world, x, y, z, side));
    }
	
	private boolean isNaturalGas(IBlockAccess world, int x, int y, int z) {
		return world.getBlockId(x, y, z) == Core.airBlocks.blockID && world.getBlockMetadata(x, y, z) == AirMeta.NATURAL_GAS;
	}
	
	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side) {
		return false;
    }
	
	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		if(Rand.nextInt(4)) {
			if(world.getBlockLightValue(x, y, z) > 8) {
				setFire(world, x, y, z);
			}
		}
		
		return super.isBlockSolidOnSide(world, x, y, z, side);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if(entity instanceof EntityLivingBase) {
			EntityLivingBase living = (EntityLivingBase) entity;
			living.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 5, 2, true));
			living.addPotionEffect(new PotionEffect(Potion.weakness.id, 5, 0, true));
		}
	}
	
	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
        return true;
    }
	
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, int metadata, ForgeDirection face) {
        return 300;
    }
	
	@Override
	public int getFireSpreadSpeed(World world, int x, int y, int z, int metadata, ForgeDirection face){
		return 1000;
	}

	@Override
	public int getMetaCount() {
		return AirMeta.COUNT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon(Mariculture.modid + ":air");
	}
	
	@Override
	public int idDropped(int i, Random random, int j) {
		return 0;
	}
	
	@Override
	public boolean isActive(int meta) {
		return false;
	}
}
