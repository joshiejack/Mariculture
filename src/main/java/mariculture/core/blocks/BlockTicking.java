package mariculture.core.blocks;

import java.util.List;
import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ILootHandler.LootQuality;
import mariculture.core.blocks.base.BlockFunctional;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.WaterMeta;
import mariculture.core.util.Rand;
import mariculture.fishery.Fishery;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTicking extends BlockFunctional {	
	public BlockTicking() {
		super(Material.cloth);
		setTickRandomly(true);
	}

	@Override
	public String getToolType(int meta) {
		return null;
	}

	@Override
	public int getToolLevel(int meta) {
		return 0;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return 0.05F;
	}
	
	@Override
	public int getRenderType() {
		return RenderIds.RENDER_ALL;
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
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return false;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.015625F, 1.0F);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getAABBPool().getAABB((double) x + this.minX, (double) y + this.minY,		
			(double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return null;
	}
	
	@Override
	public boolean doesDrop(int meta) {
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if(!BlockHelper.isFishable(world, x, y - 1, z)) {
			SpawnItemHelper.spawnItem(world, x, y, z, new ItemStack(Fishery.net));
			world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public boolean onBlockDropped(World world, int x, int y, int z) { 
		SpawnItemHelper.spawnItem(world, x, y, z, new ItemStack(Fishery.net));
		world.setBlockToAir(x, y, z);
		return false;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {		
		if(Rand.nextInt(MachineSpeeds.getNetSpeed())) {
			ItemStack loot = Fishing.loot.getCatch(null, MaricultureHandlers.biomeType.getBiomeType(world.getWorldChunkManager().getBiomeGenAt(x, z)), rand, LootQuality.FISH);
			if (loot != null && loot.getItem() instanceof ItemFishy) {
				SpawnItemHelper.spawnItem(world, x, y, z, loot, true, OreDictionary.WILDCARD_VALUE);
			} else SpawnItemHelper.spawnItem(world, x, y, z, new ItemStack(Items.fish, 1, Fishery.cod.fishID), true, OreDictionary.WILDCARD_VALUE);
		}
	}
	
	@Override
	public int getMetaCount() {
		return WaterMeta.COUNT;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return new ItemStack(Fishery.net, 1, 0);
    }

	@Override
	public boolean isActive(int meta) {
		return Modules.isActive(Modules.fishery);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creative, List list) {
		return;
	}
}