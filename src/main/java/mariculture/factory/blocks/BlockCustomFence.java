package mariculture.factory.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mariculture.api.core.MaricultureRegistry;
import mariculture.core.lib.PlansMeta;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockCustomFence extends BlockFence implements IItemRegistry {

	public BlockCustomFence(int i) {
		super(i, "customFence", Material.rock);
		setCreativeTab(null);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
    {
		return Block.stone.getIcon(side, meta);
    }

	@Override
	public Icon getBlockTexture(IBlockAccess block, int x, int y, int z, int side) {
		return BlockCustomHelper.getBlockTexture(block, x, y, z, side);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		BlockCustomHelper.onBlockPlacedBy(world, x, y, z, entity, stack);
	}

	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
		return BlockCustomHelper.rotateBlock(world, x, y, z, axis);
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return BlockCustomHelper.getBlockHardness(world, x, y, z);
	}

	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		return BlockCustomHelper.getExplosionResistance(entity, world, x, y, z, explosionX, explosionY, explosionZ);
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z) {
		return false;
	}

	@Override
	public int quantityDropped(Random rand) {
		return 0;
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		return BlockCustomHelper.removeBlockByPlayer(world, player, x, y, z, getID());
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return BlockCustomHelper.getPickBlock(target, world, x, y, z, getID());
    }
	
	private int getID() {
		return PlansMeta.FENCE;
	}

	@Override
	public boolean hasTileEntity(int meta) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return new TileCustom();
	}
	
	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(getName(new ItemStack(this.blockID, 1, j)), new ItemStack(this.blockID, 1, j));
		}
	}

	@Override
	public int getMetaCount() {
		return 1;
	}
	
	@Override 
	public String getName(ItemStack stack) {
		return "customFence";
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister) { }
}
