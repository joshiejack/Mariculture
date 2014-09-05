package joshie.mariculture.core.blocks;

import java.util.List;
import java.util.Random;

import joshie.lib.helpers.ItemHelper;
import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.core.blocks.base.BlockFunctional;
import joshie.mariculture.core.helpers.BlockHelper;
import joshie.mariculture.core.lib.MachineSpeeds;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.core.lib.RenderIds;
import joshie.mariculture.core.lib.WaterMeta;
import joshie.mariculture.fishery.Fish;
import joshie.mariculture.fishery.Fishery;
import joshie.mariculture.fishery.items.ItemFishy;
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
        return AxisAlignedBB.getBoundingBox(x + minX, y + minY, z + minZ, x + maxX, y + maxY, z + maxZ);
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
        if (!BlockHelper.isFishable(world, x, y - 1, z)) {
            ItemHelper.spawnItem(world, x, y, z, new ItemStack(Fishery.net));
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public boolean destroyBlock(World world, int x, int y, int z) {
        ItemHelper.spawnItem(world, x, y, z, new ItemStack(Fishery.net));
        world.setBlockToAir(x, y, z);
        return false;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.rand.nextInt(MachineSpeeds.getNetSpeed()) == 0) {
            ItemStack loot = Fishing.fishing.getCatch(world, x, y, z, null, null);
            if (loot != null && loot.getItem() instanceof ItemFishy) {
                ItemHelper.spawnItem(world, x, y, z, loot, true, OreDictionary.WILDCARD_VALUE);
            } else {
                ItemHelper.spawnItem(world, x, y, z, new ItemStack(Items.fish, 1, Fish.cod.getID()), true, OreDictionary.WILDCARD_VALUE);
            }
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